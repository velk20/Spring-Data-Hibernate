package com.example.gson_lab.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
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
    private Boolean PTO;
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;
    @OneToMany
    private List<Employee> workers;

    public Employee() {
        this.workers = new ArrayList<>();
    }

    public Employee(String firstName, String lastName) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Employee(String firstName, String lastName, BigDecimal salary) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }

    public Employee(String firstName, BigDecimal salary, Address address) {
        this();
        this.firstName = firstName;
        this.salary = salary;
        this.address = address;
    }

    public Employee(String firstName, String lastName, BigDecimal salary, LocalDate birthday, Address address) {
        this();

        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.birthday = birthday;
        this.address = address;
    }

    public Employee(String firstName, String lastName, BigDecimal salary, LocalDate birthday, Employee manager, List<Employee> workers) {
        this();

        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.birthday = birthday;
        this.manager = manager;
        this.workers = workers;
    }

    public void addEmployee(Employee employee) {
        this.workers.add(employee);
    }
}
