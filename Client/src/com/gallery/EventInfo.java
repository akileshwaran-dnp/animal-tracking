package com.gallery;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class EventInfo {
    public EventInfo(JPanel eventInfoPanel){
        JTextArea eventContent = new JTextArea();
        File file = new File("D:\\Projects\\WildLife Tourism\\src\\com\\gallery\\humanInfo.txt");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String eachLine = "";

            while((eachLine = bufferedReader.readLine()) != null){
                eventContent.append(eachLine);
                eventContent.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        eventContent.setBackground(Color.YELLOW);
        eventInfoPanel.add(eventContent);
    }
}
