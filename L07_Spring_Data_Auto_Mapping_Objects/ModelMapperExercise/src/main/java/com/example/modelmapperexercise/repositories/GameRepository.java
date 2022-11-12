package com.example.modelmapperexercise.repositories;

import com.example.modelmapperexercise.models.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Game findById(long id);

    Game findByTitle(String title);
}
