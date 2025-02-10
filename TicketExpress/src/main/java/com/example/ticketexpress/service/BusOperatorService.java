package com.example.ticketexpress.service;

import com.example.ticketexpress.dto.BusOperatorDTO;
import com.example.ticketexpress.model.BusOperator;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface BusOperatorService {
    List<BusOperatorDTO> getAllBusOperators();
    Optional<BusOperatorDTO> findBusOperatorById(Long id);
    void deleteBusOperator(Long id);
    void updateBusOperator(BusOperatorDTO busOperatorDTO, MultipartFile logo, Long id);
    BusOperator saveBusOperator(BusOperatorDTO busOperatorDTO,MultipartFile logo);
    List<BusOperatorDTO> searchBusOperators(String text);
}
