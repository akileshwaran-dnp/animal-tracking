package com.shareFocus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.*;
import java.net.*;

public class ShareFocus implements Runnable, ActionListener{

    JPanel shareFocusPanel;
    JButton sendButton;
    JTextField messageTextField;
    static JTextArea chatArea;
    String userName;

    BufferedWriter writer;
    BufferedReader reader;

    public ShareFocus(JPanel shareFocusPanel, String userName){
        this.shareFocusPanel = shareFocusPanel;
        this.userName = userName;
    }

    public void ShareFocusActivate(){

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{400};
        gridBagLayout.rowHeights = new int[]{10, 250, 30, 10};
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        shareFocusPanel.setLayout(gridBagLayout);

        JLabel groupNameLabel = new JLabel("Wildlife Tourism");
        groupNameLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        groupNameLabel.setForeground(Color.blue);
        groupNameLabel.setBounds(60, 15, 400, 10);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        shareFocusPanel.add(groupNameLabel, gridBagConstraints);


        chatArea = new JTextArea(20,10);
        chatArea.setBounds(60, 55, 400, 250);
        chatArea.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setBackground(Color.PINK);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;

        JScrollPane userChatScrollPane = new JScrollPane(chatArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        shareFocusPanel.add(userChatScrollPane, gridBagConstraints);

        messageTextField = new JTextField();
        messageTextField.setBounds(60, 340, 400, 30);
        messageTextField.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        shareFocusPanel.add(messageTextField, gridBagConstraints);

        sendButton = new JButton("Send");
        sendButton.setBounds(60, 360, 400, 10);
        sendButton.setBackground(new Color(7, 94, 84));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        sendButton.addActionListener(this);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        shareFocusPanel.add(sendButton, gridBagConstraints);

        try{
            Socket socketClient = new Socket("localhost", 6000);
            writer = new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            String intro = this.userName + " joined";
            writer.write(intro);
            writer.write("\r\n");
            writer.flush();
        }catch(Exception e){
            System.out.println(e);
        }

        // mainFrame.setUndecorated(true);

    }

    public void actionPerformed(ActionEvent e) {
        String str = this.userName + ": " + messageTextField.getText();
        System.out.println(str);
        try{
            writer.write(str);
            writer.write("\r\n");
            writer.flush();
        }catch(Exception exception){
            exception.printStackTrace();
        }
        messageTextField.setText("");
    }


    public void run(){
        try{
            String msg = "";
            while((msg = reader.readLine()) != null){
                if (msg.startsWith(this.userName)){
                    msg = msg.replaceAll(this.userName, "you");
                    chatArea.append(msg + "\n");
                } else{
                    chatArea.append(msg + "\n");
                }
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

}

