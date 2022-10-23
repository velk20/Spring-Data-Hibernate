package com.homework;

import com.homework.utils.Utils;

import java.sql.*;

public class T01GetVillainsNames {
    private static final  String GET_VILLAINS_NAME_SQL =
            "select `name` ,count(distinct mv.minion_id) as `counted`\n" +
                    "from villains as v\n" +
                    "join minions_villains as mv\n" +
                    "on v.id = mv.villain_id\n" +
                    "group by v.id\n" +
                    "having `counted` > 15\n" +
                    "order by `counted` desc";

    public static void main(String[] args) throws SQLException {

        final Connection connection = Utils.getSQLConnection();

        final PreparedStatement preparedStatement =
                connection.prepareStatement(GET_VILLAINS_NAME_SQL);

        final ResultSet rs = preparedStatement.executeQuery();

        while(rs.next()){
            System.out.println(rs.getString("name") +
                            " " + rs.getString("counted"));
        }
        connection.close();
    }
}
