package com.gallery;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PictureInfo {
    public PictureInfo(JPanel pictureInfoPanel){
        JTextArea pictureContent = new JTextArea();
        File file = new File("D:\\Projects\\WildLife Tourism\\src\\com\\gallery\\humanInfo.txt");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String eachLine = "";

            while((eachLine = bufferedReader.readLine()) != null){
                pictureContent.append(eachLine);
                pictureContent.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        pictureInfoPanel.add(pictureContent);
    }
}
