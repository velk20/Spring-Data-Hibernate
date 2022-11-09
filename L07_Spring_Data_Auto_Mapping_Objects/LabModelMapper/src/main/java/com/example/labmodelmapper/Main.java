package com.example.labmodelmapper;

import com.example.labmodelmapper.entities.Address;
import com.example.labmodelmapper.entities.Employee;
import com.example.labmodelmapper.entities.dto.AddressDTO;
import com.example.labmodelmapper.entities.dto.CreateEmployeeDTO;
import com.example.labmodelmapper.entities.dto.EmployeeDTO;
import com.example.labmodelmapper.services.AddressService;
import com.example.labmodelmapper.services.EmployeeService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

@Component
public class Main implements CommandLineRunner {
    private final AddressService addressService;
    private final EmployeeService employeeService;
    private final Scanner scanner;
    
    @Autowired
    public Main(AddressService addressService, EmployeeService employeeService, Scanner scanner) {
        this.addressService = addressService;
        this.employeeService = employeeService;
        this.scanner = scanner;
    }

    @Override
    public void run(String... args) throws Exception {

//        createAddress();
        createEmployee();
    }

    private void createEmployee() {
        String firstName = scanner.nextLine();
        BigDecimal salary = new BigDecimal(scanner.nextLine());
        LocalDate birthday = LocalDate.parse(scanner.nextLine());

        String country = scanner.nextLine();
        String city = scanner.nextLine();

        AddressDTO data = new AddressDTO(country, city);

        CreateEmployeeDTO employeeDTO = new CreateEmployeeDTO(firstName, null, salary, birthday, data);
        this.employeeService.create(employeeDTO);
        System.out.println(employeeDTO);

    }

    private void createAddress() {
        String country = scanner.nextLine();
        String city = scanner.nextLine();

        AddressDTO data = new AddressDTO(country, city);

        Address address = addressService.create(data);

        System.out.println(address);
    }

    private void demoMapper(){
        Address address = new Address("Bulgaria", "Sofia");
        Employee employee = new Employee("Angel", BigDecimal.TEN, address);

        //Basic mapping
        ModelMapper mapper = new ModelMapper();
        EmployeeDTO employeeDTO = mapper.map(employee, EmployeeDTO.class);

       // Advanced mapping
        PropertyMap<Employee, EmployeeDTO> propertyMap = new PropertyMap<Employee, EmployeeDTO>() {
            @Override
            protected void configure() {
                map().setAddressCity(source.getAddress().getCountry() + "MAPPED");
            }
        };
        mapper.addMappings(propertyMap);
        EmployeeDTO employeeDTO1 = mapper.map(employee, EmployeeDTO.class);

        //Advanced mapping after Java 8
        TypeMap<Employee, EmployeeDTO> typeMap = mapper.createTypeMap(Employee.class, EmployeeDTO.class);
        typeMap.validate();
        typeMap.addMappings(mapping -> mapping.map(source -> source.getAddress().getCity(),
                EmployeeDTO::setAddressCity));
        EmployeeDTO employeeDTO2 = typeMap.map(employee);



        System.out.println(employeeDTO);
        System.out.println(employeeDTO1);
        System.out.println(employeeDTO2);
    }
}
