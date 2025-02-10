package com.example.ticketexpress.mapper;

import com.example.ticketexpress.dto.BusOperatorDTO;
import com.example.ticketexpress.model.BusOperator;

public class BusOperatorMapper {
    public static BusOperator mapToBusOperator(BusOperatorDTO busOperatorDTO) {
       return BusOperator.builder()
                .name(busOperatorDTO.getName())
                .phone(busOperatorDTO.getPhone())
                .logoUrl(busOperatorDTO.getLogoUrl())
                .id(busOperatorDTO.getId())
                .build();

    }
    public static BusOperatorDTO mapToBusOperatorDTO(BusOperator busOperator) {
        return BusOperatorDTO.builder()
                .name(busOperator.getName())
                .phone(busOperator.getPhone())
                .logoUrl(busOperator.getLogoUrl())
                .id(busOperator.getId())
                .build();
    }
}
