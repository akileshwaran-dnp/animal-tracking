package com.gallery;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class AnimalInfo {
    public AnimalInfo(JPanel animalInfoPanel){
        JTextArea animalContent = new JTextArea();
        File file = new File("D:\\Projects\\WildLife Tourism\\src\\com\\gallery\\humanInfo.txt");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String eachLine = "";

            while((eachLine = bufferedReader.readLine()) != null){
                animalContent.append(eachLine);
                animalContent.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        animalContent.setBackground(Color.cyan);
        animalInfoPanel.add(animalContent);
    }
}
