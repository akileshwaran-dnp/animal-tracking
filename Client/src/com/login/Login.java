package com.login;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;

import com.admin.AdminHome;
import com.tourist.TouristHome;


class Connection {

    Socket socket;
    BufferedWriter writer;
    BufferedReader reader;
    String dbPassword;

    Connection(JPanel homePanel, String userName, String password, String user){
        try {
            socket = new Socket("localhost", 6000);
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String cmd = "validate " + userName + " " + user;
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
                System.err.println(eachLine);
                dbPassword = eachLine;
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

    public String getDBPassword(){
        return this.dbPassword;
    }
}

public class Login {

    class NoInputProvided extends Exception{
        NoInputProvided(String s){
            super(s);
            JOptionPane.showMessageDialog(loginPanel, "Please fill all the fields");
        }
    }

    class InValidUserName extends Exception{
        InValidUserName(String s){
            super(s);
            JOptionPane.showMessageDialog(loginPanel, "Invalid UserName");

        }
    }

    class InCorrectPassword extends Exception{
        InCorrectPassword(String s){
            super(s);
            JOptionPane.showMessageDialog(loginPanel, "Incorrect Password");
        }
    }

    JPanel mainPanel;
    JPanel loginPanel;
    JTextField userName;
    JTextField userPassword;

    public Login(JPanel newPanel) throws InValidUserName{

        mainPanel = newPanel;
        loginPanel = new JPanel();
        newPanel.add(loginPanel);
        loginPanel.setBounds(500,500,400,300);

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{100, 100};
        gridBagLayout.rowHeights = new int[]{50,50,50};
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        loginPanel.setLayout(gridBagLayout);

        JLabel userNameLabel = new JLabel("USERNAME");
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        loginPanel.add(userNameLabel, gridBagConstraints);

        userName = new JTextField();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        loginPanel.add(userName, gridBagConstraints);

        JLabel userPasswordLabel = new JLabel("PASSWORD");
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        loginPanel.add(userPasswordLabel, gridBagConstraints);

        userPassword = new JTextField();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        loginPanel.add(userPassword, gridBagConstraints);


        JButton touristLoginButton = new JButton("Tourist Login");
        touristLoginButton.setBounds(200,200, 40,40);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        loginPanel.add(touristLoginButton, gridBagConstraints);

        JButton adminLoginButton = new JButton("Admin Login");
        adminLoginButton.setBounds(200,400, 40,40);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        loginPanel.add(adminLoginButton, gridBagConstraints);

        adminLoginButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String name = userName.getText();
                String password = userPassword.getText();
                boolean ifExixts = false;

                try {
                    ifExixts = ifAdminExixts(name, password);
                } catch (NoInputProvided | InValidUserName | InCorrectPassword exception) {
                    exception.printStackTrace();
                }

                if (ifExixts){
                    JPanel adminHomePanel = new JPanel();
                    adminHomePanel.setBounds(10,10,200,200);

                    loginPanel.add(adminHomePanel);
                    loginPanel.remove(userName);
                    loginPanel.remove(userNameLabel);
                    loginPanel.remove(userPassword);
                    loginPanel.remove(userPasswordLabel);
                    loginPanel.remove(touristLoginButton);
                    loginPanel.remove(adminLoginButton);

                    loginPanel.revalidate();
                    AdminHome adminHome = new AdminHome(adminHomePanel, name);
                }
            }
        });
        
        touristLoginButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String name = userName.getText();
                String password = userPassword.getText();
                boolean ifExixts = false;

                try {
                    ifExixts = ifTouristExists(name, password);
                } catch (NoInputProvided | InValidUserName | InCorrectPassword exception) {
                    exception.printStackTrace();
                }

                if (ifExixts){
                    JPanel touristHomePanel = new JPanel();
                    touristHomePanel.setBounds(10,10,200,200);

                    loginPanel.add(touristHomePanel);
                    loginPanel.remove(userName);
                    loginPanel.remove(userNameLabel);
                    loginPanel.remove(userPassword);
                    loginPanel.remove(userPasswordLabel);
                    loginPanel.remove(touristLoginButton);
                    loginPanel.remove(adminLoginButton);

                    loginPanel.revalidate();
                    TouristHome touristHome = new TouristHome(touristHomePanel, name);
                }

            }
        });

        adminLoginButton.setLayout(null);
        touristLoginButton.setLayout(null);

    }

    private boolean ifAdminExixts(String name, String password) throws NoInputProvided, InValidUserName, InCorrectPassword {

        if (name.isEmpty() || password.isEmpty()){
            throw new NoInputProvided("No input");
        }

        String dbPassword="";
        Connection newConnection = new Connection(loginPanel, name, password, "admin");
        newConnection.run();
        dbPassword = newConnection.getDBPassword();

        if (dbPassword.equals("null")) {
            throw new InValidUserName("Invalid UserName");
        }
        
        newConnection.terminateConnection();

        if (dbPassword.equals(password)){
            return true;
        } else {
            throw new InCorrectPassword("InCorrect Password");
        }
    }

    private boolean ifTouristExists(String name, String password) throws InCorrectPassword, InValidUserName, NoInputProvided {

        if (name.isEmpty() || password.isEmpty()){
            throw new NoInputProvided("No input");
        }

        String dbPassword="";
        Connection newConnection = new Connection(loginPanel, name, password, "tourist");
        newConnection.run();
        dbPassword = newConnection.getDBPassword();

        if (dbPassword.equals("null")) {
            throw new InValidUserName("Invalid UserName");
        }

        newConnection.terminateConnection();

        if (dbPassword.equals(password)){
            return true;
        } else {
            throw new InCorrectPassword("InCorrect Password");
        }

    }

}
