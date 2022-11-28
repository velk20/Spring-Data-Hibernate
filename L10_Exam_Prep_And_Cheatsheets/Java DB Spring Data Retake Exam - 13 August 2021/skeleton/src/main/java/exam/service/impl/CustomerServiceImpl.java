package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dtos.ImportCustomerDTO;
import exam.model.entities.Customer;
import exam.model.entities.Town;
import exam.repository.CustomerRepository;
import exam.repository.TownRepository;
import exam.service.CustomerService;
import exam.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static exam.util.ErrorMessages.INVALID_CUSTOMER;
import static exam.util.FilesPaths.CUSTOMERS_PATH;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final Gson gson;
    private final CustomerRepository customerRepository;
    private final TownRepository townRepository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public CustomerServiceImpl(Gson gson, CustomerRepository customerRepository, TownRepository townRepository, ModelMapper mapper, ValidationUtil validationUtil) {
        this.gson = gson;
        this.customerRepository = customerRepository;
        this.townRepository = townRepository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.customerRepository.count() > 0;
    }

    @Override
    public String readCustomersFileContent() throws IOException {
        return Files.readString(CUSTOMERS_PATH);
    }

    @Override
    public String importCustomers() throws IOException {
        ImportCustomerDTO[] importCustomerDTO = gson.fromJson(readCustomersFileContent(), ImportCustomerDTO[].class);

        return Arrays.stream(importCustomerDTO)
                .map(this::importCustomer)
                .collect(Collectors.joining("\n"));
    }

    private String importCustomer(ImportCustomerDTO importCustomerDTO) {
        if(!validationUtil.isValid(importCustomerDTO)) return INVALID_CUSTOMER;

        Optional<Customer> customerByEmail =
                this.customerRepository.findByEmail(importCustomerDTO.getEmail());
        if (customerByEmail.isPresent()) {
            return INVALID_CUSTOMER;
        }

        Optional<Town> townByName = townRepository.findByName(importCustomerDTO.getTown().getName());
        if (townByName.isEmpty()) return INVALID_CUSTOMER;

        Customer customer = mapper.map(importCustomerDTO, Customer.class);
        customer.setTown(townByName.get());

        this.customerRepository.save(customer);
        return String.format("Successfully imported Customer %s %s - %s", customer.getFirstName(),
                customer.getLastName(), customer.getEmail());
    }
}
