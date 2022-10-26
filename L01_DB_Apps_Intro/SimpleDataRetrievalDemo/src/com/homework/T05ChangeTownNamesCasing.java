package com.homework;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class T05ChangeTownNamesCasing {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "root");

        String input = scanner.nextLine().trim();

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", properties);

        PreparedStatement preparedStatement = connection.prepareStatement(
                "update `towns` " +
                "set `name` = upper(`name`) where `country` = ?");
        preparedStatement.setString(1,input);

        int resultSet = preparedStatement.executeUpdate();

        if (resultSet == 0) {
            System.out.println("No town names were affected.");
        }else{
            PreparedStatement preparedStatement2 = connection.prepareStatement(
                    "select `name` " +
                            "from towns where country = ?");
            preparedStatement2.setString(1,input);

            ResultSet resultSet1 = preparedStatement2.executeQuery();
            StringBuilder stringBuilder = new StringBuilder();
            System.out.println(String.format("%d town names were affected.", resultSet));
            List<String> towns = new ArrayList<>();
            while (resultSet1.next()) {
                towns.add(resultSet1.getString("name"));
            }
            String townsString = String.join(", ", towns);
            System.out.println("["+townsString+"]");
        }
    }
}
