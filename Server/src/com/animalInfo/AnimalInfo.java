package com.animalInfo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.*;

public class AnimalInfo {
  public AnimalInfo(BufferedWriter writer, String animalName){
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wildlife", "root", "KiNgmakeRS#01");
      Statement statement = connection.createStatement();
      String queryStatement;
      String name = "\"" + animalName + "\"";
      queryStatement = "select * from animal where animalName = ";
      queryStatement += name;
      ResultSet resultSet = statement.executeQuery(queryStatement);
      boolean isNext = resultSet.next();
      if (!isNext){
        writer.write("null");
      } else{
        writer.write(resultSet.getString(1));
        writer.write("\n");
        writer.write(resultSet.getString(2));
        writer.write("\n");
        writer.write(resultSet.getString(3));
        writer.write("\n");
        writer.write(resultSet.getString(4));
        writer.write("\n");
        writer.write(resultSet.getString(5));
      }
      writer.flush();
      writer.close();

    } catch (SQLException | ClassNotFoundException | IOException exception) {
      System.err.println(exception);
    }
  }
}
