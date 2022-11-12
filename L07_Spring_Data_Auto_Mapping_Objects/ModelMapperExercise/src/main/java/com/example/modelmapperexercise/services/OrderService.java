package com.example.modelmapperexercise.services;

import com.example.modelmapperexercise.models.entities.Game;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface OrderService {
    Game addItem(String gameTitle);

    Game removeItem(String gameTitle);

    Set<Game> BuyItem();
}
