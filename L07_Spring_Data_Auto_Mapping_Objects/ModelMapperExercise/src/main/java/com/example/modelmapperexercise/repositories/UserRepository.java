package com.example.modelmapperexercise.repositories;

import com.example.modelmapperexercise.models.dtos.RegisterDTO;
import com.example.modelmapperexercise.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
