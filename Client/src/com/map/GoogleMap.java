package com.map;

import com.sun.jdi.connect.Connector;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;

public class GoogleMap {
    public GoogleMap(JPanel mapPanel){
        LayoutManager layout = new FlowLayout();
        mapPanel.setLayout(layout);

        JEditorPane jEditorPane = new JEditorPane();
        jEditorPane.setEditable(false);
        URL url= GoogleMap.class.getResource("index.html");

        try {
            jEditorPane.setPage(url);
        } catch (IOException e) {
            jEditorPane.setContentType("text/html");
            jEditorPane.setText("<html>Page not found.</html>");
        }

        JScrollPane jScrollPane = new JScrollPane(jEditorPane);
        jScrollPane.setPreferredSize(new Dimension(540,400));

        mapPanel.add(jScrollPane);
    }
}
