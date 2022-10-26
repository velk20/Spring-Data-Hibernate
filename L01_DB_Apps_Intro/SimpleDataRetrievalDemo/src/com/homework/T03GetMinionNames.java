package com.homework;

import com.homework.utils.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class T03GetMinionNames {
    private static final String NO_VILLAIN_FOUND_ERROR = "No villain with ID %d exists in the database.";
    private static final String GET_MINIONS_NAMES =
            "SELECT v.`name` as v_name,m.`name` as m_name,m.age\n" +
            "FROM  villains as v\n" +
            "join minions_villains as mv\n" +
            "on v.id =mv.villain_id\n" +
            "join minions as m\n" +
            "on mv.minion_id = m.id\n" +
            "where v.id = ?\n";

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        final Connection connection = Utils.getSQLConnection();

        final PreparedStatement preparedStatement =
                connection.prepareStatement(GET_MINIONS_NAMES);

        System.out.print("Enter the villain id: ");
        int v_id = Integer.parseInt(scanner.nextLine());
        preparedStatement.setInt(1, v_id);
        ResultSet rs = preparedStatement.executeQuery();

        if (!rs.next()) {
            System.out.printf(NO_VILLAIN_FOUND_ERROR,v_id);
        } else{
            int count = 1;
            while(rs.next()){
                if (count == 1) {
                    System.out.println("Villain: "+ rs.getString("v_name"));
                }
                System.out.println(
                        count++ +". "+
                                rs.getString("m_name") +
                                " " + rs.getString("age"));
            }
        }
        connection.close();
    }
}
