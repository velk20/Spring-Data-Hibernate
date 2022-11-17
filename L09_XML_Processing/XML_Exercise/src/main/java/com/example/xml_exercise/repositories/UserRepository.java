package com.example.xml_exercise.repositories;

import com.example.xml_exercise.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User as u " +
            "join u.soldProducts as p " +
            "where u.soldProducts.size>0 and p.buyer is not null " +
            "group by u " +
            "order by u.lastName,u.firstName")
    List<User> usersAndSoldProducts();

    @Query("select u from User as u " +
            "where u.soldProducts.size>0 " +
            "order by u.soldProducts.size desc , u.lastName")
    List<User> usersAndProducts();
}
