package com.example.gson_exercise.models.utils;

public abstract class FilesPaths {
    private static String ROOT_PATH = "src/main/resources/files/";
    public static String USERS_FILE_PATH = ROOT_PATH + "users.json";
    public static String PRODUCTS_FILE_PATH = ROOT_PATH + "products.json";
    public static String CATEGORIES_FILE_PATH = ROOT_PATH + "categories.json";
    public static String CARS_FILE_PATH = ROOT_PATH + "cars.json";
    public static String CUSTOMERS_FILE_PATH = ROOT_PATH + "customers.json";
    public static String PARTS_FILE_PATH = ROOT_PATH + "parts.json";
    public static String SUPPLIERS_FILE_PATH = ROOT_PATH + "suppliers.json";
    public static String PRODUCTS_IN_RANGE_PATH = ROOT_PATH + "outputs/products-in-range.json";
    public static String USERS_SOLD_PRODUCTS_PATH = ROOT_PATH + "outputs/users-sold-products.json";
    public static String CATEGORIES_BY_PRODUCTS_PATH = ROOT_PATH + "outputs/categories-by-products.json";
    public static String USERS_AND_PRODUCTS_PATH = ROOT_PATH + "outputs/users-and-products.json";
}
