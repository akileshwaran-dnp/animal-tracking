package com.homePage;

import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;

public class HomePage {
  public HomePage(BufferedWriter writer){
    File homePageContent = new File("D:\\sem 05\\Assignment Presentation\\Java\\Server\\src\\com\\homePage\\HomePage.txt");
    try{
      BufferedReader bufferedReader = new BufferedReader(new FileReader(homePageContent));
      String eachLine;
      while ((eachLine = bufferedReader.readLine()) != null){
          writer.write(eachLine);
          writer.write("\r\n");
          writer.flush();
      }
      writer.close();
    } catch (IOException ioException){
      ioException.printStackTrace();
    }
  }
}
