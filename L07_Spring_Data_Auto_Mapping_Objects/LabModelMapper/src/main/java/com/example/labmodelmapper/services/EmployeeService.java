package com.example.labmodelmapper.services;

import com.example.labmodelmapper.entities.Employee;
import com.example.labmodelmapper.entities.dto.CreateEmployeeDTO;

public interface EmployeeService {
    Employee create(CreateEmployeeDTO employeeDTO);
}
