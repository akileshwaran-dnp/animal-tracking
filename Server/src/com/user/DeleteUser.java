package com.user;

import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteUser {
    public DeleteUser(BufferedWriter writer, String touristName){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wildlife", "root", "KiNgmakeRS#01");
            Statement statement = connection.createStatement();
            String queryStatement;
            String name = "\"" + touristName + "\"";
            queryStatement = "delete from tourist where touristName = ";
            queryStatement += name;
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
