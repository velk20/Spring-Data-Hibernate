package com.example.modelmapperexercise.validators;

import java.math.BigDecimal;

public abstract class GameValidator {
    public static boolean validateTitle(String title) {
        return Character.isUpperCase(title.charAt(0)) && title.length() >= 3 && title.length() <= 100;
    }

    public static boolean validatePrice(BigDecimal price) {
        return price.doubleValue() > 0;
    }

    public static boolean validateSize(double price) {
        return price > 0;
    }

    public static boolean validateTrailer(String trailer) {
        return trailer.length() == 11;
    }

    public static boolean validateThumbnailURL(String url) {
        return url.startsWith("http://") || url.startsWith("https://");

    }

    public static boolean validateDescription(String description) {
        return description.length() >= 20;
    }
}
