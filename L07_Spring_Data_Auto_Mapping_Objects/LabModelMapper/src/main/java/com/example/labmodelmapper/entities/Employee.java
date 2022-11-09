package com.example.labmodelmapper.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private BigDecimal salary;
    private LocalDate birthday;
    @ManyToOne(optional = false)
    private Address address;

    public Employee(String firstName, BigDecimal salary, Address address) {
        this.firstName = firstName;
        this.salary = salary;
        this.address = address;
    }
}
