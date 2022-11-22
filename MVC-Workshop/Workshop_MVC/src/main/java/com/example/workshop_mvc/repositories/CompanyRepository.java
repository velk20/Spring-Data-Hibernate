package com.example.workshop_mvc.repositories;

import com.example.workshop_mvc.models.entitiy.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {
}
