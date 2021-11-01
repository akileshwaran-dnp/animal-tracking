package com.tourist;

import javax.swing.*;

import com.shareFocus.ShareFocus;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class TouristHome {
    JTextField animalName;
    public TouristHome(JPanel newPanel, String touristName){

        JPanel touristHomePanel = new JPanel();
        newPanel.add(touristHomePanel);

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.rowHeights = new int[]{10, 150, 10};
        gridBagLayout.columnWidths = new int[]{300, 10};
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        touristHomePanel.setLayout(gridBagLayout);

        JTextArea userNameTextArea = new JTextArea();
        userNameTextArea.append(touristName);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        userNameTextArea.setEditable(false);
        touristHomePanel.add(userNameTextArea, gridBagConstraints);

        JPanel animalTrackPanel = new JPanel();

        GridBagLayout gridBagLayout1 = new GridBagLayout();
        gridBagLayout1.columnWidths = new int[]{100,100};
        gridBagLayout1.rowHeights = new int[]{50,50};
        GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
        animalTrackPanel.setLayout(gridBagLayout1);

        JLabel animalNameLabel = new JLabel("Animal Name");
        gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints1.gridx = 0;
        gridBagConstraints1.gridy = 0;
        animalTrackPanel.add(animalNameLabel, gridBagConstraints1);

        animalName = new JTextField();
        gridBagConstraints1.gridx = 1;
        gridBagConstraints1.gridy = 0;
        animalTrackPanel.add(animalName, gridBagConstraints1);

        JButton animalSearchButton = new JButton("Search");
        animalSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSearch();
            }
        });
        gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints1.gridx = 0;
        gridBagConstraints1.gridy = 1;
        gridBagConstraints1.gridwidth = 2;
        animalTrackPanel.add(animalSearchButton, gridBagConstraints1);

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        touristHomePanel.add(animalTrackPanel, gridBagConstraints);


        JButton chatButton = new JButton("Chat");
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        touristHomePanel.add(chatButton, gridBagConstraints);

        chatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                touristHomePanel.remove(userNameTextArea);
                touristHomePanel.remove(animalTrackPanel);
                touristHomePanel.remove(chatButton);
                touristHomePanel.setLayout(null);
                touristHomePanel.revalidate();

                ShareFocus newUser = new ShareFocus(touristHomePanel, touristName);
                newUser.ShareFocusActivate();
                Thread newThread = new Thread(newUser);
                newThread.start();
            }
        });

    }

    public void handleSearch(){
        String name = animalName.getText();
        JFrame searchResultFrame = new JFrame("Search Result");
        searchResultFrame.setSize(600,600);
        searchResultFrame.setLayout(null);

        JPanel searchPanel = new JPanel();
        searchPanel.setSize(600,600);
        searchResultFrame.add(searchPanel);

        Connection newConnection = new Connection(name);
        newConnection.run();

        JTextArea home = newConnection.getHomeContent();
        searchPanel.add(home);
        newConnection.terminateConnection();

        searchResultFrame.setVisible(true);
    }


    class Connection {

        Socket socket;
        BufferedWriter writer;
        BufferedReader reader;
        JTextArea searchContent;

        Connection(String animalName){
            searchContent = new JTextArea(10,50);
            searchContent.setEditable(false);
            try {
                socket = new Socket("localhost", 6000);
                writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String cmd = "searchAnimal " + animalName;
                writer.write(cmd);
                writer.write("\r\n");
                writer.flush();

            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(null, "Connection Refused");
                return;
            }
        }
        public void run() {
            String eachLine = "";

            try{
                while((eachLine = reader.readLine()) != null){
                    this.searchContent.append(eachLine);
                    this.searchContent.append("\n");
                }
            } catch (IOException ioException){
                System.err.println(ioException);
            }
        }

        public void terminateConnection(){
            try {
                this.socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        public JTextArea getHomeContent(){
            return this.searchContent;
        }
    }
}
