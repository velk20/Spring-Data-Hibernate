package com.homework;

import com.homework.utils.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class T07PrintAllMinionNames {
    private static final String GET_MINIONS_NAMES ="select `name` from minions where id = ? limit 1";
    private static final String GET_MINIONS_COUNT = "select count(*) from minions";

    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getSQLConnection();

        ResultSet resultSet = connection.prepareStatement(GET_MINIONS_COUNT).executeQuery();
        resultSet.next();
        int count = resultSet.getInt(1);

        final PreparedStatement preparedStatement =
                connection.prepareStatement(GET_MINIONS_NAMES);

        //first Name
        preparedStatement.setInt(1, 1);
        ResultSet resultSet1 = preparedStatement.executeQuery();
        resultSet1.next();
        System.out.println(resultSet1.getString("name"));

        //last Name
        preparedStatement.setInt(1, count);
        ResultSet resultSet2 = preparedStatement.executeQuery();
        resultSet2.next();
        System.out.println(resultSet2.getString("name"));

        //Rest of the names
        for (int i = 1; i < count / 2; i++) {
            preparedStatement.setInt(1, 1 + i);
            ResultSet resultSetFirst= preparedStatement.executeQuery();
            resultSetFirst.next();
            System.out.println(resultSetFirst.getString("name"));

            preparedStatement.setInt(1, count - i);
            ResultSet resultSetLast =  preparedStatement.executeQuery();
            resultSetLast.next();
            System.out.println(resultSetLast.getString("name"));
        }
    }
}
