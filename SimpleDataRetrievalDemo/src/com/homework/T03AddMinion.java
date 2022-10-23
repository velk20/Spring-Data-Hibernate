package com.homework;

import com.homework.utils.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class T03AddMinion {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        final Connection connection = Utils.getSQLConnection();

        System.out.print("Minion: ");
        String[] minion = scanner.nextLine().split("\\s+");
        System.out.print("Villain: ");
        String villainName = scanner.nextLine();

        String minionName = minion[0];
        String minionAge = minion[1];
        String minionTown = minion[2];

        if(!isMinionTownExist(connection,minionTown).next()){
            addMinionTownToDB(connection,minionTown);
        }

        if (!isVillainExist(connection, villainName).next()) {
            addVillainToDB(connection, villainName);
        }

        setMinionToTheVillain(connection, villainName, minionName,minionAge,minionTown);

    }

    private static void setMinionToTheVillain(Connection connection, String villainName, String minionName,String minionAge, String minionTown) throws SQLException {
        addMinionToDB(connection, minionName, minionAge, minionTown);
        ResultSet minionExistByName = isMinionExistByName(connection, minionName);
        int minionId = minionExistByName.next()? minionExistByName.getInt("id"):0;
        addMinionToVillain(connection, villainName, minionId);
    }

    private static void addMinionToVillain(Connection connection, String villainName, int minionId) throws SQLException {
        ResultSet villainExist = isVillainExist(connection, villainName);

        int villainId = villainExist.next()? villainExist.getInt("id"):0;

        String sql = String.format("" +
                "insert into minions_villains (minion_id,villain_id) " +
                "values(%s,%s)",minionId,villainId
        );
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int rs = preparedStatement.executeUpdate();
        ResultSet minionExistById = isMinionExistById(connection, minionId);

        String name = minionExistById.next()?minionExistById.getString("name"):"";
        System.out.printf("Successfully added %s to be minion of %s\n",
                name,
                villainName);
    }

    private static void addMinionToDB(Connection connection, String minionName, String minionAge, String minionTown) throws SQLException {
        ResultSet townResultSet = isMinionTownExist(connection, minionTown);

        int town_id = townResultSet.next() ? townResultSet.getInt("id") : 0;

        String sqlAddVillain = String.format("" +
                "insert into minions (`name`,age,town_id)\n" +
                "values('%s',%s,%d)",minionName,minionAge,town_id);
        PreparedStatement preparedStatement = connection.prepareStatement(sqlAddVillain);
        preparedStatement.executeUpdate();

    }

    private static void addVillainToDB(Connection connection, String villainName) throws SQLException {
        String sqlAddVillain = String.format("" +
                "insert into villains (`name`,evilness_factor)\n" +
                "values('%s','evil')",villainName);
        PreparedStatement preparedStatement = connection.prepareStatement(sqlAddVillain);
        int rs = preparedStatement.executeUpdate();

        System.out.printf("Villain %s was added to the database.\n",villainName);
    }

    private static ResultSet isVillainExist(Connection connection, String villainName) throws SQLException {
        String sqlForMinionsTown =
                String.format("select * from villains where `name` = '%s'", villainName);
        PreparedStatement preparedStatement = connection.prepareStatement(sqlForMinionsTown);

        return preparedStatement.executeQuery();
    }

    private static ResultSet isMinionExistById(Connection connection, int minionId) throws SQLException {
        String sqlForMinionsTown =
                String.format("select * from minions where `id` = %s", minionId);
        PreparedStatement preparedStatement = connection.prepareStatement(sqlForMinionsTown);

        return preparedStatement.executeQuery();
    }

    private static ResultSet isMinionExistByName(Connection connection, String minionName) throws SQLException {
        String sqlForMinionsTown =
                String.format("select * from minions where `name` = '%s'", minionName);
        PreparedStatement preparedStatement = connection.prepareStatement(sqlForMinionsTown);

        return preparedStatement.executeQuery();
    }

    private static void addMinionTownToDB(Connection connection, String minionTown) throws SQLException {
        String sqlAddTown =
                String.format("insert into towns (`name`)\n" +
                "values('%s')",minionTown);
        PreparedStatement preparedStatement = connection.prepareStatement(sqlAddTown);
        int rs = preparedStatement.executeUpdate();

        System.out.printf("Town %s was added to the database.\n",minionTown);

    }

    public static ResultSet isMinionTownExist(Connection connection, String minionTown) throws SQLException {
        String sqlForMinionsTown =
                String.format("select * from towns where `name` = '%s'", minionTown);
        PreparedStatement preparedStatement = connection.prepareStatement(sqlForMinionsTown);

        return preparedStatement.executeQuery();
    }
}
