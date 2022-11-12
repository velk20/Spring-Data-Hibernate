package com.example.modelmapperexercise.services;

import com.example.modelmapperexercise.models.dtos.AddGameDTO;
import com.example.modelmapperexercise.models.entities.Game;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface GameService {
    Game addGame(AddGameDTO addGameDTO) throws IllegalAccessException;

    Game editGame(long id, String... args) throws IllegalAccessException;

    Game deleteGame(long id) throws IllegalAccessException;

    List<Game> findAllGames();
    Game findByTitle(String title);

}
