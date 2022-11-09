package com.example.labmodelmapper.services;

import com.example.labmodelmapper.entities.Employee;
import com.example.labmodelmapper.entities.dto.CreateEmployeeDTO;
import com.example.labmodelmapper.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeRepository employeeRepository;
    private final ModelMapper mapper;
    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper mapper) {
        this.employeeRepository = employeeRepository;
        this.mapper = mapper;
    }

    @Override
    public Employee create(CreateEmployeeDTO employeeDTO) {
        ModelMapper mapper = new ModelMapper();
        Employee employee = mapper.map(employeeDTO, Employee.class);

        return this.employeeRepository.save(employee);
    }
}
