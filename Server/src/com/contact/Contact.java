package com.contact;

import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.*;

public class Contact {
    public Contact(BufferedWriter writer){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wildlife", "root", "KiNgmakeRS#01");
            Statement statement = connection.createStatement();
            String queryStatement;
            queryStatement = "select officerName, officerContactNumber from officer";
            ResultSet resultSet = statement.executeQuery(queryStatement);
            if (!resultSet.next()){
                writer.write("null");
            } else{
                writer.write(resultSet.getString(1));
                writer.write("\t");
                writer.write(resultSet.getString(2));
                writer.write("\n");
                while (resultSet.next()){
                    writer.write(resultSet.getString(1));
                    writer.write("\t");
                    writer.write(resultSet.getString(2));
                    writer.write("\n");
                }
            }
            writer.flush();
            writer.close();

        } catch (SQLException | ClassNotFoundException | IOException exception) {
            System.err.println(exception);
        }
    }
}
