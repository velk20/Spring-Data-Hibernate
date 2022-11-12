package com.example.modelmapperexercise.models.dtos;

import com.example.modelmapperexercise.models.entities.Game;
import com.example.modelmapperexercise.models.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ShoppingCart {
    private User user;
    private Set<Game> games;

    public ShoppingCart() {
        this.games = new HashSet<>();
    }

    public ShoppingCart(User user) {
        this();
        this.user = user;
        this.games = new HashSet<>();
    }

    public void addItem(Game game) {
        this.games.add(game);
    }

    public void removeItem(Game game) {
        this.games.remove(game);
    }

}
