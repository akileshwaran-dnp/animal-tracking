package com.login;

import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.*;

public class Login {
    public Login(BufferedWriter writer, String name, String user){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wildlife", "root", "KiNgmakeRS#01");
            Statement statement = connection.createStatement();
            String queryStatement;
            if (user.equals("admin")){
                queryStatement = "select adminPassword from admin where adminUserName = '" + name + "'";
            } else{
                queryStatement = "select touristPassword from tourist where touristUserName = '" + name + "'";
            }
            ResultSet resultSet = statement.executeQuery(queryStatement);
            if (!resultSet.next()){
                writer.write("null");
            } else{
                writer.write(resultSet.getString(1));
            }
            writer.flush();
            writer.close();

        } catch (SQLException | ClassNotFoundException | IOException exception) {
            System.err.println(exception);
        }
    }
}
