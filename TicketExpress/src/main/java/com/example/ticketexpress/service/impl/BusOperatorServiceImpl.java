package com.example.ticketexpress.service.impl;

import com.example.ticketexpress.dto.BusOperatorDTO;
import com.example.ticketexpress.exception.BusOperatorNotFoundException;
import com.example.ticketexpress.exception.FileStorageException;
import com.example.ticketexpress.mapper.BusOperatorMapper;
import com.example.ticketexpress.model.BusOperator;
import com.example.ticketexpress.repository.BusOperatorRepository;
import com.example.ticketexpress.service.BusOperatorService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BusOperatorServiceImpl implements BusOperatorService {

    private final BusOperatorRepository busOperatorRepository;

    public BusOperatorServiceImpl(BusOperatorRepository busOperatorRepository) {
        this.busOperatorRepository = busOperatorRepository;
    }

    @Override
    public List<BusOperatorDTO> getAllBusOperators() {
        return busOperatorRepository.findAll()
                .stream().map(BusOperatorMapper::mapToBusOperatorDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<BusOperatorDTO> findBusOperatorById(Long id) {
        return busOperatorRepository.findById(id).map(BusOperatorMapper::mapToBusOperatorDTO);
    }

    @Override
    public void deleteBusOperator(Long id) {
         busOperatorRepository.deleteById(id);
    }

    @Override
    public void updateBusOperator(BusOperatorDTO busOperatorDTO, MultipartFile logo, Long id) {
         BusOperator busOperator=busOperatorRepository.findById(id).orElseThrow(()->new BusOperatorNotFoundException(id));
         if(logo!=null && !logo.isEmpty()) {
             try {
                 String logoFileName=logo.getOriginalFilename();
                 String uploadDir="src/main/resources/static/images/";
                 Path path= Paths.get(uploadDir+'/'+logoFileName);
                 Files.copy(logo.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
                 busOperator.setLogoUrl("/images/"+logoFileName);
             }catch (IOException e){
                 throw new FileStorageException(logo.getOriginalFilename(),e);
             }
         }
         busOperator.setName(busOperatorDTO.getName());
         busOperator.setPhone(busOperatorDTO.getPhone());
         busOperatorRepository.save(busOperator);
    }

    @Override
    public BusOperator saveBusOperator(BusOperatorDTO busOperatorDTO,MultipartFile logo) {
        BusOperator busOperator=BusOperatorMapper.mapToBusOperator(busOperatorDTO);
        if(logo!=null && !logo.isEmpty()) {
            try {
                String logoFileName=logo.getOriginalFilename();
                String uploadDir="src/main/resources/static/images/";
                Path path= Paths.get(uploadDir+'/'+logoFileName);
                Files.copy(logo.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
                busOperator.setLogoUrl("/images/"+logoFileName);
            }catch (IOException e){
                throw new FileStorageException(logo.getOriginalFilename(),e);
            }
        }
        return busOperatorRepository.save(busOperator);
    }

    @Override
    public List<BusOperatorDTO> searchBusOperators(String text) {
        if (text == null || text.isEmpty()) {
            return busOperatorRepository.findAll().stream().map(BusOperatorMapper::mapToBusOperatorDTO).collect(Collectors.toList());
        }
        return busOperatorRepository.findBusOperatorsByNameContainingIgnoreCase(text)
                .stream().map(BusOperatorMapper::mapToBusOperatorDTO).
                collect(Collectors.toList());
    }
}
