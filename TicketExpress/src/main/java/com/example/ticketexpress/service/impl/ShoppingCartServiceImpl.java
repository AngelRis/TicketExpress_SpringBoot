package com.example.ticketexpress.service.impl;

import com.example.ticketexpress.dto.TicketDTO;
import com.example.ticketexpress.enumeration.ShoppingCartStatus;
import com.example.ticketexpress.enumeration.TicketStatus;
import com.example.ticketexpress.exception.FullBusException;
import com.example.ticketexpress.exception.TicketNotFoundException;
import com.example.ticketexpress.mapper.TicketMapper;
import com.example.ticketexpress.model.ShoppingCart;
import com.example.ticketexpress.model.Ticket;
import com.example.ticketexpress.model.User;
import com.example.ticketexpress.repository.ShoppingCartRepository;
import com.example.ticketexpress.repository.TicketRepository;
import com.example.ticketexpress.repository.UserRepository;
import com.example.ticketexpress.service.ShoppingCartService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    @Autowired
    private JavaMailSender emailSender;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, UserRepository userRepository, TicketRepository ticketRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public ShoppingCart getActiveShoppingCart() {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        return user.getShoppingCarts().stream()
                .filter(s->s.getStatus()== ShoppingCartStatus.ACTIVE)
                .findFirst().get();
    }

    @Override
    public void removeTicket(Long ticketId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart();
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(()->new TicketNotFoundException(ticketId));
        shoppingCart.getTickets().remove(ticket);
        shoppingCart.setTotalPrice(shoppingCart.getTotalPrice() - ticket.getPrice());
        shoppingCartRepository.save(shoppingCart);
        ticketRepository.delete(ticket);
    }

    @Override
    public void checkout(Long cartId) throws MessagingException, WriterException, IOException {
        ShoppingCart shoppingCart = this.getActiveShoppingCart();
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        for (Ticket ticket : shoppingCart.getTickets()) {

            if (ticketRepository.findAllByStatusAndDateAndTerm(TicketStatus.CONFIRMED,ticket.getDate(),ticket.getTerm()).size()<ticket.getTerm().getBus().getNumberOfSeats())
            {
                ticket.setSeatNumber(ticketRepository.findAllByStatusAndDateAndTerm(TicketStatus.CONFIRMED,ticket.getDate(),ticket.getTerm()).size()+1);
                ticket.setStatus(TicketStatus.CONFIRMED);
                ticketRepository.save(ticket);
            }else{
                for (Ticket ticket1 : shoppingCart.getTickets()){
                    ticket1.setSeatNumber(0);
                    ticket1.setStatus(TicketStatus.RESERVED);
                    ticketRepository.save(ticket1);
                }
                throw new FullBusException("Билетот со ID "+ticket.getId()+" неможе да се наплати, бидеќи автобусот се пополни, ве молиме отсранете го билетот за да може да продолжите со наплата на други билети.");
            }
        }
        for (Ticket ticket : shoppingCart.getTickets()) {
            QRCodeWriter barcodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = barcodeWriter.encode(ticket.getId().toString(), BarcodeFormat.QR_CODE, 200, 200);
            // Креирање на BufferedImage од BitMatrix
            BufferedImage qrImage = toBufferedImage(bitMatrix);

            // Конвертирање на BufferedImage во byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "png", baos);
            byte[] imageBytes = baos.toByteArray();
            TicketDTO ticketDTO= TicketMapper.mapToTicketDTO(ticket);
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = getMimeMessageHelper(message, ticketDTO, user);
            helper.addAttachment("QR.png", new ByteArrayResource(imageBytes), "image/png");
            emailSender.send(message);
        }
        shoppingCart.setStatus(ShoppingCartStatus.COMPLETED);
        ShoppingCart newShoppingCart=ShoppingCart.builder()
                .user(user)
                .totalPrice(0.0)
                .status(ShoppingCartStatus.ACTIVE)
                .build();
        shoppingCartRepository.save(newShoppingCart);
        user.getShoppingCarts().add(newShoppingCart);
        userRepository.save(user);
    }

    private static MimeMessageHelper getMimeMessageHelper(MimeMessage message, TicketDTO ticketDTO, User user) throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        String tripType = ticketDTO.isRoundTripTicket() ? "Повратен" : "Во еден правец";
        helper.setFrom("ticketexpress2025@gmail.com");
        helper.setTo(user.getEmail());
        helper.setSubject("Успешно купен билет - TicketExpress");

        String emailContent = """
        <html>
        <head>
            <style>
                body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                .ticket-details { background-color: #f0f0f0; padding: 15px; border-radius: 5px; }
                .ticket-details h3 { margin-top: 0; color: #0066cc; }
                ul { list-style-type: none; padding-left: 0; }
                li { margin-bottom: 10px; }
                .footer { margin-top: 20px; font-size: 0.9em; color: #666; }
            </style>
        </head>
        <body>
            <div class="container">
                <h4>Почитуван корисник,</h4>
                <p>Ви благодариме за вашата нарачка. Вашиот билет е успешно резервиран.</p>
                <div class="ticket-details">
                    <h3>Детали за билетот:</h3>
                    <ul>
                        <li><strong>Релација:</strong> %s - %s</li>
                        <li><strong>Тип:</strong> %s</li>
                        <li><strong>Датум и време:</strong> %s %s</li>
                        <li><strong>Број на седиште:</strong> %s</li>
                        <li><strong>Превозник:</strong> %s</li>
                    </ul>
                </div>
                <p>Ве молиме зачувајте го QR кодот во продолжение. Истиот ќе треба да го покажете на кондукторот при влез во автобусот.</p>
                <p>Ви посакуваме пријатно патување!</p>
                <div class="footer">
                    <p>Со почит,<br>Тимот на TicketExpress</p>
                </div>
            </div>
        </body>
        </html>
    """.formatted(
                ticketDTO.getStartingCity(),
                ticketDTO.getFinalCity(),
                tripType,
                ticketDTO.getDate(),
                ticketDTO.getStartingTime(),
                ticketDTO.getSeatNumber(),
                ticketDTO.getBusOperatorName()
        );

        helper.setText(emailContent, true);
        return helper;
    }


    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }
}
