package com.example.labmodelmapper.entities.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ManagerDTO {
    private String firstName;
    private String lastName;
    private List<EmployeeDTO> workers;

    public ManagerDTO() {
        this.workers = new ArrayList<>();
    }

    public ManagerDTO(String firstName, String lastName, List<EmployeeDTO> workers) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.workers = workers;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s %s | Employees: %d", firstName, lastName, this.workers.size()));
        sb.append(System.lineSeparator());
        for (EmployeeDTO worker : workers) {
            sb.append(String.format("\t- %s %s %.2f", worker.getFirstName(), worker.getLastName(), worker.getSalary()));
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
