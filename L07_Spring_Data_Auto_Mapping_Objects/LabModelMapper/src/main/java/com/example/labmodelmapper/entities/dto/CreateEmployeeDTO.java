package com.example.labmodelmapper.entities.dto;

import com.google.gson.annotations.Expose;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CreateEmployeeDTO {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private BigDecimal salary;
    @Expose
    private LocalDate birthday;
    @Expose
    private AddressDTO address;

    public CreateEmployeeDTO(String firstName, String lastName, BigDecimal salary, LocalDate birthday, AddressDTO address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.birthday = birthday;
        this.address = address;
    }
}
