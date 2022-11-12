package com.example.modelmapperexercise;

import com.example.modelmapperexercise.models.dtos.AddGameDTO;
import com.example.modelmapperexercise.models.dtos.LoginDTO;
import com.example.modelmapperexercise.models.dtos.RegisterDTO;
import com.example.modelmapperexercise.models.entities.Game;
import com.example.modelmapperexercise.models.entities.User;
import com.example.modelmapperexercise.services.GameService;
import com.example.modelmapperexercise.services.OrderService;
import com.example.modelmapperexercise.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

@Component
public class Main implements CommandLineRunner {
    private final OrderService orderService;
    private final UserService userService;
    private final GameService gameService;

    @Autowired
    public Main(OrderService orderService, UserService userService, GameService gameService) {
        this.orderService = orderService;
        this.userService = userService;
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        while (!line.isBlank()) {
            try {
                commandOperations(line);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            line = scanner.nextLine();
        }

    }

    private void commandOperations(String line) throws IllegalAccessException {
        String[] commands = line.split("\\|");
        String generalCommand = commands[0];
        switch (generalCommand) {
            case "RegisterUser":
                registerUser(commands);
                break;
            case "LoginUser":
                loginUser(commands);
                break;
            case "Logout":
                logoutUser();
                break;
            case "AddGame":
                if (userService.getLoggedUser() != null) {
                    addGame(commands);
                } else {
                    noPermission();
                }
                break;
            case "EditGame":
                if (userService.getLoggedUser() != null) {
                    editGame(commands);
                } else {
                    noPermission();
                }
                break;
            case "DeleteGame":
                if (userService.getLoggedUser() != null) {
                    deleteGame(commands);
                } else {
                    noPermission();
                }
                break;
            case "AllGames":
                if (userService.getLoggedUser() != null) {
                    allGames();
                } else {
                    noPermission();
                }
                break;
            case "DetailGame":
                if (userService.getLoggedUser() != null) {
                    detailsGame(commands);
                } else {
                    noPermission();
                }
                break;
            case "OwnedGames":
                if (userService.getLoggedUser() != null) {
                    ownedGames();
                } else {
                    noPermission();
                }
                break;
            case "AddItem":
                if (userService.getLoggedUser() != null) {
                    addItem(commands);
                } else {
                    noPermission();
                }
                break;
            case "RemoveItem":
                if (userService.getLoggedUser() != null) {
                    removeItem(commands);
                } else {
                    noPermission();
                }
                break;
            case "BuyItem":
                if (userService.getLoggedUser() != null) {
                    buyItem();
                } else {
                    noPermission();
                }
                break;
            default:
                System.out.println("Invalid command!");
                break;
        }
    }

    private void buyItem() {
        Set<Game> games = this.orderService.BuyItem();
        StringBuilder sb = new StringBuilder();
        sb.append("Successfully bought games:");
        sb.append(System.lineSeparator());
        for (Game game : games) {
            sb.append("   -").append(game.getTitle());
            sb.append(System.lineSeparator());
        }
        System.out.println(sb.toString().trim());
    }

    private void removeItem(String[] commands) {
        String titleGame = commands[1];
        Game game = this.orderService.removeItem(titleGame);
        System.out.printf("%s removed to cart\n", game.getTitle());
    }

    private void addItem(String[] commands) {
        String titleGame = commands[1];
        Game game = this.orderService.addItem(titleGame);
        System.out.printf("%s added to cart\n", game.getTitle());

    }

    private void ownedGames() {
        Set<Game> gameSet = this.userService.getLoggedUser().getGames();
        if (gameSet.isEmpty()) {
            System.out.println("No games were bought!");

            return;
        }
        for (Game game : gameSet) {
            System.out.println(game.getTitle());
        }
    }

    private void detailsGame(String[] commands) {
        String titleGame = commands[1];
        Game game = this.gameService.findByTitle(titleGame);
        System.out.println(game);
    }

    private void allGames() {
        List<Game> allGames = this.gameService.findAllGames();
        for (Game game : allGames) {
            System.out.printf("%s %.2f\n", game.getTitle(), game.getPrice());
        }
    }

    private void deleteGame(String[] commands) throws IllegalAccessException {
        long id = Long.parseLong(commands[1]);
        Game deleteGame = this.gameService.deleteGame(id);
        System.out.printf("%s was deleted!\n", deleteGame.getTitle());
    }

    private void editGame(String[] commands) throws IllegalAccessException {
        long gameId = Long.parseLong(commands[1]);
        String[] params = Arrays.copyOfRange(commands, 2, commands.length);
        Game editGame = this.gameService.editGame(gameId, params);
        System.out.printf("%s was edited!\n", editGame.getTitle());
    }

    private void addGame(String[] commands) throws IllegalAccessException {
        String title = commands[1];
        BigDecimal price = new BigDecimal(commands[2]);
        double size = Double.parseDouble(commands[3]);
        String trailer = commands[4];
        String url = commands[5];
        String desc = commands[6];

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate releaseDate = LocalDate.parse(commands[7], formatter);

        AddGameDTO addGameDTO = new AddGameDTO(title, price, size, url, trailer, releaseDate, desc);
        Game addGame = this.gameService.addGame(addGameDTO);
        System.out.println(addGame.getTitle() + " game was added!");
    }

    private void logoutUser() {
        String logout = this.userService.logout();
        System.out.printf("User %s successfully logged out%n", logout);

    }

    private void loginUser(String[] commands) throws IllegalAccessException {
        String email = commands[1];
        String password = commands[2];

        LoginDTO loginDTO = new LoginDTO(email, password);
        User user = this.userService.loginUser(loginDTO);
        System.out.println("Successfully logged in " + user.getFullName());
    }

    private void registerUser(String[] commands) throws IllegalAccessException {
        String email = commands[1];
        String password = commands[2];
        String confirmPassword = commands[3];
        String fullName = commands[4];

        RegisterDTO registerDTO = new RegisterDTO(email, password, confirmPassword, fullName);
        User user = this.userService.registerUser(registerDTO);
        System.out.println(user.getFullName() + " was registered");
    }

    private void noPermission() throws IllegalAccessException {
        throw new IllegalAccessException("No permission, try to login first");
    }
}
