package com.homework;

import com.homework.utils.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class T09IncreaseAgeStoredProcedure {
    private static final String PRINT_NAME_AGE = "select `name`,age from minions where id = ?";

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter minion ID: ");
        int minion_id = Integer.parseInt(scanner.nextLine());

        final Connection connection = Utils.getSQLConnection();

        PreparedStatement preparedStatementBefore =connection.prepareStatement(PRINT_NAME_AGE);
        preparedStatementBefore.setInt(1, minion_id);
        ResultSet resultSet = preparedStatementBefore.executeQuery();
        resultSet.next();
        System.out.println("Before update: " + resultSet.getString("name") + " " + resultSet.getString("age"));

        String update_age = "call increase_minion_years(?)";

        PreparedStatement preparedStatement = connection.prepareStatement(update_age);
        preparedStatement.setInt(1, minion_id);

        int i = preparedStatement.executeUpdate();
        System.out.println("Executing update...");


        PreparedStatement preparedStatement1 = connection.prepareStatement(PRINT_NAME_AGE);
        preparedStatement1.setInt(1,minion_id);
        ResultSet resultSet1 = preparedStatement1.executeQuery();
        resultSet1.next();
        System.out.println("After update: " + resultSet1.getString("name") + " " + resultSet1.getString("age"));


    }
}
