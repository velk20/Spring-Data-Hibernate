package com.example.labmodelmapper.repositories;

import com.example.labmodelmapper.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
