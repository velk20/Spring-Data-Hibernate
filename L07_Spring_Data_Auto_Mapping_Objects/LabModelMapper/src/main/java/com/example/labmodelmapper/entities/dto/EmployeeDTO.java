package com.example.labmodelmapper.entities.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeDTO {
    private String firstName;
    private BigDecimal salary;
    private String addressCity;


}
