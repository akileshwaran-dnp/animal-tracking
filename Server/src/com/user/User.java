package com.user;

import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.*;

public class User {
    public User(BufferedWriter writer, String touristName, String touristAge, String touristContact, String touristAddress, String touristEmail){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wildlife", "root", "KiNgmakeRS#01");
            Statement statement = connection.createStatement();
            String queryStatement;
            String name = "\"" + touristName + "\"";
            String address = "\"" + touristAddress + "\"";
            String email = "\"" + touristEmail + "\"";

            queryStatement = "insert into touristinfo (touristName, touristAge, touristContact, touristAddress, touristEmail)";
            queryStatement += " values (";
            queryStatement += name + ", ";
            queryStatement += touristAge + ", ";
            queryStatement += touristContact + ", ";
            queryStatement += address + ", ";
            queryStatement += email;
            queryStatement += ")";

            int resultSet = statement.executeUpdate(queryStatement);
            if (resultSet == 1){
                writer.write("success");
            }
            writer.flush();
            writer.close();

        } catch (SQLException | ClassNotFoundException | IOException exception) {
            System.err.println(exception);
        }
    }
}
