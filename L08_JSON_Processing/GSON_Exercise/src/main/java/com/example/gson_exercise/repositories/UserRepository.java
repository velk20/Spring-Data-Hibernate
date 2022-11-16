package com.example.gson_exercise.repositories;

import com.example.gson_exercise.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from " +
            "User as u " +
            "join u.soldProducts as p " +
            "where u.soldProducts.size >= 1 and p.buyer is not null " +
            "order by u.lastName,u.firstName")
    List<User> getAllSuccessfullySoldProducts();

    @Query("select u from User as u " +
            "where u.soldProducts.size > 0" +
            " order by u.soldProducts.size desc," +
            " u.lastName")
    List<User> getUsersAndProducts();
}
