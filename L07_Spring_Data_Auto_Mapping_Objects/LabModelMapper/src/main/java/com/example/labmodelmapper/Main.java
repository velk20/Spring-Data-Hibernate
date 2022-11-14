package com.example.labmodelmapper;

import com.example.labmodelmapper.config.LocalDateAdapterDeSerialize;
import com.example.labmodelmapper.config.LocalDateAdapterSerialize;
import com.example.labmodelmapper.entities.Address;
import com.example.labmodelmapper.entities.Employee;
import com.example.labmodelmapper.entities.dto.AddressDTO;
import com.example.labmodelmapper.entities.dto.CreateEmployeeDTO;
import com.example.labmodelmapper.entities.dto.EmployeeDTO;
import com.example.labmodelmapper.entities.dto.ManagerDTO;
import com.example.labmodelmapper.services.AddressService;
import com.example.labmodelmapper.services.EmployeeService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Component
public class Main implements CommandLineRunner {
    private final AddressService addressService;
    private final EmployeeService employeeService;
    private final Scanner scanner;
    private final ModelMapper mapper;

    @Autowired
    public Main(AddressService addressService, EmployeeService employeeService, Scanner scanner, ModelMapper mapper) {
        this.addressService = addressService;
        this.employeeService = employeeService;
        this.scanner = scanner;
        this.mapper = mapper;
    }

    @Override
    public void run(String... args) throws Exception {

    }

    private void json_demo() {
        //    {"country":"bulgaria","city":"plovdiv"}
//    [{"country":"bulgaria","city":"plovdiv"},{"country":"bulgaria","city":"varna"},{"country":"bulgaria","city":"sofia"}]
//    {"firstName":"Angel","lastName":"Mladenov","salary":10,"birthday":"2022-11-14","address":{"country":"bulgaria","city":"plovdiv"}}

        Gson gson = new GsonBuilder()
                //we need TypeAdapter for using dates
                //serialize
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapterSerialize())
                //de-serialize
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapterDeSerialize())
                .excludeFieldsWithoutExposeAnnotation()
//                .setPrettyPrinting()
                .create();

        //From entities to JSON
        AddressDTO addressDTO = new AddressDTO("bulgaria", "plovdiv");
        AddressDTO addressDTO1 = new AddressDTO("bulgaria", "sofia");
        AddressDTO addressDTO2 = new AddressDTO("bulgaria", "varna");
        System.out.println(gson.toJson(addressDTO));


        List<AddressDTO> addressDTOList = List.of(addressDTO, addressDTO2, addressDTO1);
        System.out.println(gson.toJson(addressDTOList));

        CreateEmployeeDTO createEmployeeDTO = new CreateEmployeeDTO(
                "Angel",
                "Mladenov",
                BigDecimal.TEN,
                LocalDate.now(),
                addressDTO);

        System.out.println(gson.toJson(createEmployeeDTO));

        //From json to entity

        //for one object
        String inputObject = scanner.nextLine();
        CreateEmployeeDTO parsedDTO = gson.fromJson(inputObject, CreateEmployeeDTO.class);
        System.out.println(parsedDTO);

        //for collection of objects
        String inputCollection = scanner.nextLine();
        AddressDTO[] parsedListDTO = gson.fromJson(inputCollection, AddressDTO[].class);
        Arrays.stream(parsedListDTO).toList().forEach(System.out::println);
    }

    private void _02_advancedMapping() {
        Address address = new Address("Bulgaria", "Sofia");
        Employee manager = new Employee("My Manager", "Mr.Manager", BigDecimal.TEN, LocalDate.now(), address);

        Employee first = new Employee("First", "Employee", BigDecimal.ZERO, LocalDate.now(), address);
        Employee second = new Employee("SEcond", "Employee", BigDecimal.ONE, LocalDate.now(), address);

        manager.addEmployee(first);
        manager.addEmployee(second);

        ManagerDTO managerDTO = mapper.map(manager, ManagerDTO.class);
        System.out.println(managerDTO);

    }

    private void _01_simpleMapping() {
        Employee employee = new Employee("Angel", "Mladenov", new BigDecimal("69.699"), LocalDate.now(), new Address(
                "Bulgaria", "Sofia"));
        EmployeeDTO employeeDTO = mapper.map(employee, EmployeeDTO.class);
        System.out.println(employeeDTO);

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

//    private void demoMapper(){
//        Address address = new Address("Bulgaria", "Sofia");
//        Employee employee = new Employee("Angel", BigDecimal.TEN, address);
//
//        //Basic mapping
//        ModelMapper mapper = new ModelMapper();
//        EmployeeDTO employeeDTO = mapper.map(employee, EmployeeDTO.class);
//
//       // Advanced mapping
//        PropertyMap<Employee, EmployeeDTO> propertyMap = new PropertyMap<Employee, EmployeeDTO>() {
//            @Override
//            protected void configure() {
//                map().setAddressCity(source.getAddress().getCountry() + "MAPPED");
//            }
//        };
//        mapper.addMappings(propertyMap);
//        EmployeeDTO employeeDTO1 = mapper.map(employee, EmployeeDTO.class);
//
//        //Advanced mapping after Java 8
//        TypeMap<Employee, EmployeeDTO> typeMap = mapper.createTypeMap(Employee.class, EmployeeDTO.class);
//        typeMap.validate();
//        typeMap.addMappings(mapping -> mapping.map(source -> source.getAddress().getCity(),
//                EmployeeDTO::setAddressCity));
//        EmployeeDTO employeeDTO2 = typeMap.map(employee);
//
//
//
//        System.out.println(employeeDTO);
//        System.out.println(employeeDTO1);
//        System.out.println(employeeDTO2);
//    }
}
