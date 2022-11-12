package com.example.modelmapperexercise.services;

import com.example.modelmapperexercise.models.dtos.ShoppingCart;
import com.example.modelmapperexercise.models.entities.Game;
import com.example.modelmapperexercise.models.entities.Order;
import com.example.modelmapperexercise.models.entities.User;
import com.example.modelmapperexercise.repositories.GameRepository;
import com.example.modelmapperexercise.repositories.OrderRepository;
import com.example.modelmapperexercise.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final ModelMapper mapper;
    private final UserService userService;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private ShoppingCart shoppingCarts;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper mapper, UserService userService, GameRepository gameRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.mapper = mapper;
        this.userService = userService;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.shoppingCarts = new ShoppingCart();
    }

    @Override
    public Game addItem(String gameTitle) {
        User user = userService.getLoggedUser();
        Game game = this.gameRepository.findByTitle(gameTitle);
        if (game == null) {
            throw new IllegalArgumentException("No such game");
        }
        boolean gameAlreadyBought = user.getGames().stream().anyMatch(g -> g.getTitle().equals(gameTitle));
        if (gameAlreadyBought) {
            throw new IllegalArgumentException("Game was already purchased");
        }

        if (this.shoppingCarts.getGames().contains(game)) {
            throw new IllegalArgumentException("Game is already in the shopping card!");
        }

        if (this.shoppingCarts.getUser() == null) {
            this.shoppingCarts.setUser(user);
        }
        this.shoppingCarts.addItem(game);

        return game;
    }

    @Override
    public Game removeItem(String gameTitle) {
        if (this.shoppingCarts.getGames().isEmpty()) {
            throw new IllegalArgumentException("Shopping cart is empty");
        }

        Game game = this.shoppingCarts.getGames().stream()
                .filter(g -> g.getTitle().equals(gameTitle))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        this.shoppingCarts.removeItem(game);

        return game;
    }

    @Override
    public Set<Game> BuyItem() {
        User user = this.shoppingCarts.getUser();
        Set<Game> games = this.shoppingCarts.getGames();
        if (games.isEmpty()) {
            throw new IllegalArgumentException("Shopping cart is empty!");
        }
        user.addAllGames(games);

        Order order = new Order(user, games);

        this.userRepository.save(user);
        this.orderRepository.save(order);

        this.shoppingCarts = new ShoppingCart();
        return games;
    }
}
