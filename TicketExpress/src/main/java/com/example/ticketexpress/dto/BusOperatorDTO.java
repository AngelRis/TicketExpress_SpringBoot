package com.example.ticketexpress.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BusOperatorDTO {
    private Long id;
    private String name;
    private String logoUrl;
    private String phone;
}
