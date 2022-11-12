package com.example.modelmapperexercise.services;

import com.example.modelmapperexercise.models.dtos.AddGameDTO;
import com.example.modelmapperexercise.models.entities.Game;
import com.example.modelmapperexercise.repositories.GameRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Component
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final UserService userService;
    private final ModelMapper mapper;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, UserService userService, ModelMapper mapper) {
        this.gameRepository = gameRepository;
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    public Game addGame(AddGameDTO addGameDTO) throws IllegalAccessException {
        checkForAdminPermissions();

        Game game = mapper.map(addGameDTO, Game.class);
        return this.gameRepository.save(game);
    }

    private void checkForAdminPermissions() throws IllegalAccessException {
        if (!this.userService.getLoggedUser().isAdmin()) {
            throw new IllegalAccessException("Permission denied: User is no admin!");
        }
    }
    @Override
    @Modifying
    public Game editGame(long id, String... args) throws IllegalAccessException {
        checkForAdminPermissions();

        Game gameById = this.gameRepository.findById(id);
        if (gameById == null) {
            throw new NoSuchElementException("No such game was found!");
        }

        for (String arg : args) {
            String[] values = arg.split("=");
            String variable = values[0];
            switch (variable) {
                case "title":
                    gameById.setTitle(values[1]);
                    break;
                case "price":
                    gameById.setPrice(new BigDecimal(values[1]));
                    break;
                case "size":
                    gameById.setSize(Double.parseDouble(values[1]));
                    break;
                case "trailer":
                    gameById.setTrailer(values[1]);
                    break;
                case "thumbnailURL":
                    gameById.setImageUrl(values[1]);
                    break;
                case "description":
                    gameById.setDescription(values[1]);
                    break;
            }
        }

        return this.gameRepository.save(gameById);
    }

    @Override
    @Modifying
    public Game deleteGame(long id) throws IllegalAccessException {
        checkForAdminPermissions();

        Game gameById = this.gameRepository.findById(id);
        if (gameById == null) {
            throw new NoSuchElementException("No such game was found!");
        }

        this.gameRepository.deleteById(id);
        return gameById;
    }

    @Override
    public List<Game> findAllGames() {
        return this.gameRepository.findAll();
    }

    @Override
    public Game findByTitle(String title) {
        return this.gameRepository.findByTitle(title);
    }

}
