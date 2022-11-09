package com.example.labmodelmapper.services;

import com.example.labmodelmapper.entities.Address;
import com.example.labmodelmapper.entities.dto.AddressDTO;

public interface AddressService {
    Address create(AddressDTO data);
}
