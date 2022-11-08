package com.example.advquerying;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.repositories.ShampooRepository;
import com.example.advquerying.services.IngredientService;
import com.example.advquerying.services.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class Main implements CommandLineRunner {

    private final ShampooService shampooService;
    private final IngredientService ingredientService;

    @Autowired
    public Main(ShampooService shampooService, IngredientService ingredientService) {
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter all ingredients names you want to set price\nSeparated by comma(,): ");
        String[] names = scanner.nextLine().split(", ");

        System.out.print("Now enter the price you want to set: ");
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(scanner.nextLine()));

        int updatedEntitiesCount = this.ingredientService.setIngredientPrices(Arrays.stream(names).collect(Collectors.toSet()), price);
        System.out.println("Updated entities = " + updatedEntitiesCount);
    }

}
