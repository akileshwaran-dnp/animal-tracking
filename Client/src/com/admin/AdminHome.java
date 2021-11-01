package com.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class AdminHome {
    JPanel adminHomePanel;
    JTextField touristName;
    JTextField touristAge;
    JTextField touristContact;
    JTextField touristAddress;
    JTextField touristEmail;

    public AdminHome(JPanel newPanel, String adminName){
        adminHomePanel = newPanel;
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{100, 100};
        gridBagLayout.rowHeights = new int[]{50, 50, 50, 50, 50, 50, 50};
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        adminHomePanel.setLayout(gridBagLayout);

        JLabel touristNameLabel = new JLabel("Name");
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        adminHomePanel.add(touristNameLabel, gridBagConstraints);

        touristName = new JTextField();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        adminHomePanel.add(touristName, gridBagConstraints);

        JLabel touristAgeLabel = new JLabel("Age");
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        adminHomePanel.add(touristAgeLabel, gridBagConstraints);

        touristAge = new JTextField();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        adminHomePanel.add(touristAge, gridBagConstraints);

        JLabel touristContactLabel = new JLabel("Contact");
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        adminHomePanel.add(touristContactLabel, gridBagConstraints);

        touristContact = new JTextField();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        adminHomePanel.add(touristContact, gridBagConstraints);

        JLabel touristAddressLabel = new JLabel("Address");
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        adminHomePanel.add(touristAddressLabel, gridBagConstraints);

        touristAddress = new JTextField();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        adminHomePanel.add(touristAddress, gridBagConstraints);

        JLabel touristEmailLabel = new JLabel("Email");
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        adminHomePanel.add(touristEmailLabel, gridBagConstraints);

        touristEmail = new JTextField();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        adminHomePanel.add(touristEmail, gridBagConstraints);


        JButton touristLoginButton = new JButton("Create user");
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        adminHomePanel.add(touristLoginButton, gridBagConstraints);

        JButton touristDeleteButton = new JButton("Delete user");
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        adminHomePanel.add(touristDeleteButton, gridBagConstraints);

        touristLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCreateuser();
            }
        });

        touristDeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDeleteUser();
            }
        });
    }

    public void handleDeleteUser(){
        Connection connection = new Connection(adminHomePanel);
        connection.run();
        boolean isSuccess = connection.getIsSuccess();
        if (isSuccess){
            JOptionPane.showMessageDialog(adminHomePanel, "success");
        }
        connection.terminateConnection();
    }

    public void handleCreateuser(){

        String name = touristName.getText();
        String age = touristAge.getText();
        String contact = touristContact.getText();
        String address = touristAddress.getText();
        String mail = touristEmail.getText();
        String pwd = "";

        Connection newConnection = new Connection(adminHomePanel,name, age, contact, address, mail);
        newConnection.run();

        boolean isSuccess = newConnection.getIsSuccess();
        if (isSuccess){
            JOptionPane.showMessageDialog(adminHomePanel, "success");
            pwd = name + "@" + age;
            Connection connection = new Connection(adminHomePanel, name, name, pwd);
            connection.run();
            connection.terminateConnection();
        }
        newConnection.terminateConnection();

    }

    class Connection {

        Socket socket;
        BufferedWriter writer;
        BufferedReader reader;
        boolean isSuccess;

        Connection(JPanel homePanel){
            try {
                socket = new Socket("localhost", 6000);
                writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String name = JOptionPane.showInputDialog(homePanel, "UserName");
                String cmd = "deleteUser " + name;
                writer.write(cmd);
                writer.write("\r\n");
                writer.flush();

            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(homePanel, "Connection Refused");
                return;
            }
        }

        Connection(JPanel homePanel, String touristName, String touristAge, String touristContact, String touristAddress, String touristEmail){
            try {
                socket = new Socket("localhost", 6000);
                writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String cmd = "createTourist " + touristName + " " + touristAge + " " + touristContact + " " + touristAddress + " " + touristEmail;
                writer.write(cmd);
                writer.write("\r\n");
                writer.flush();

            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(homePanel, "Connection Refused");
                return;
            }
        }

        Connection(JPanel homePanel, String touristName, String touristUserName, String touristPassword){
            try {
                socket = new Socket("localhost", 6000);
                writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String cmd = "createUser " + touristName + " " + touristUserName + " " + touristPassword ;
                writer.write(cmd);
                writer.write("\r\n");
                writer.flush();

            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(homePanel, "Connection Refused");
                return;
            }
        }

        public void run() {
            String eachLine = "";

            try{
                while((eachLine = reader.readLine()) != null){
                    isSuccess  =(eachLine.equals("success")? true: false);
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

        public boolean getIsSuccess(){ return this.isSuccess; }
    }
}

