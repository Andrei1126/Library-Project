package com.example.library.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegistryDto {

    private String name;

    private Integer overdueDays;

    private Double penaltyPrice;
}
