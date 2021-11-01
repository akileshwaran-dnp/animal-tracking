package com.gallery;

import javax.swing.*;
import java.io.*;

public class HumanInfo {
    public HumanInfo(JPanel humanInfoPanel){
        JTextArea humanInfo = new JTextArea(5, 10);
        File file = new File("D:\\Projects\\WildLife Tourism\\src\\com\\gallery\\humanInfo.txt");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String eachLine = "";

            while((eachLine = bufferedReader.readLine()) != null){
                humanInfo.append(eachLine);
                humanInfo.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        humanInfoPanel.add(humanInfo);
    }
}
