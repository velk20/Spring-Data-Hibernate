package com.example.labmodelmapper.services;

import com.example.labmodelmapper.entities.Address;
import com.example.labmodelmapper.entities.dto.AddressDTO;
import com.example.labmodelmapper.repositories.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService{
    private final AddressRepository addressRepository;
    private final ModelMapper mapper;
    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, ModelMapper mapper) {
        this.addressRepository = addressRepository;
        this.mapper = mapper;
    }

    @Override
    public Address create(AddressDTO data) {
        ModelMapper mapper = new ModelMapper();
        Address address = mapper.map(data, Address.class);

        return this.addressRepository.save(address);
    }
}
