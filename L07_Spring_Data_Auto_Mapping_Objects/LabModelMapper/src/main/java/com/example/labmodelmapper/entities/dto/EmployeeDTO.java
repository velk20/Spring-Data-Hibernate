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
    private String lastName;
    private BigDecimal salary;

}
