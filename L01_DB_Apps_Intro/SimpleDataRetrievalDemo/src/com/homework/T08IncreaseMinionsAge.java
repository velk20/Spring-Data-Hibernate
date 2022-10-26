package com.homework;

import com.homework.utils.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class T08IncreaseMinionsAge {
    private static final String UPDATE_AGE = "update minions set age = age + 1 where id = ?";
    private static final String UPDATE_NAME = "update minions set `name` = lower(`name`) where id = ?";
    private static final String PRINT_NAME_AGE = "select `name`,age from minions";
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        final Connection connection = Utils.getSQLConnection();

        int[] minionsIds =
                Arrays.stream(scanner.nextLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();

        PreparedStatement preparedStatementAge = connection.prepareStatement(UPDATE_AGE);
        PreparedStatement preparedStatementName = connection.prepareStatement(UPDATE_NAME);

        for (int minionsId : minionsIds) {
            preparedStatementAge.setInt(1, minionsId);
            int i = preparedStatementAge.executeUpdate();

            preparedStatementName.setInt(1,minionsId);
            int i1 = preparedStatementName.executeUpdate();
        }

        PreparedStatement preparedStatement = connection.prepareStatement(PRINT_NAME_AGE);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            System.out.println(resultSet.getString("name") + " " + resultSet.getString("age"));
        }
    }
}
