package com.home;

import javax.swing.*;

import java.io.*;
import java.net.Socket;

class Connection {

    Socket socket;
    BufferedWriter writer;
    BufferedReader reader;
    JTextArea homeAreaContent = new JTextArea(10,100);

    Connection(JPanel homePanel){
        try {
            socket = new Socket("localhost", 6000);
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            writer.write("getHomePage");
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
                this.homeAreaContent.append(eachLine);
                this.homeAreaContent.append("\n");
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
        return this.homeAreaContent;
    }
}

public class Home{

    public Home(JPanel homePanel){

        Connection newConnection = new Connection(homePanel);
        newConnection.run();

        JTextArea home = newConnection.getHomeContent();
        homePanel.add(home);
        newConnection.terminateConnection();
    }
}



