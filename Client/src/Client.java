import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

import com.home.Home;
import com.login.Login;
import com.map.Map;
import com.gallery.Gallery;

public class Client {
    public static void main(String[] args) {

        JFrame mainFrame = new JFrame("Wildlife Tourism");
        mainFrame.setBackground(Color.LIGHT_GRAY);
        mainFrame.setSize(1600, 800);
        mainFrame.setLayout(null);
        Image titleBarIcon = Toolkit.getDefaultToolkit().getImage("D:\\sem 05\\Assignment Presentation\\Java\\Client\\src\\icons\\logo1.png");
        mainFrame.setIconImage(titleBarIcon);
        JPanel homePanel = new JPanel();
        new Home(homePanel);
        JPanel galleryPanel = new JPanel();
        new Gallery(galleryPanel);
        JPanel loginPanel = new JPanel();
        try {
            new Login(loginPanel);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        JPanel mapPanel = new JPanel();
        Map newMap = new Map(mapPanel);

        JMenuBar menuBar = new JMenuBar();

        JMenu helpMenu = new JMenu("Help");
        JMenuItem contactItem = new JMenuItem("Contact");
        contactItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleContact();
            }
        });

        JMenuItem docItem = new JMenuItem("Doc");
        docItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDoc();
            }
        });

        helpMenu.add(contactItem);
        helpMenu.add(docItem);
        menuBar.add(helpMenu);

        JMenu alertMenu = new JMenu("Alert");
        JMenuItem fireItem = new JMenuItem("Fire");
        fireItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                handleAlert("fire");
            }
        });
        JMenuItem floodItem = new JMenuItem("Flood");
        floodItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAlert("flood");
            }
        });
        JMenuItem animalItem = new JMenuItem("Animal");
        animalItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAlert("animal");
            }
        });
        JMenuItem otherItem = new JMenuItem("Other");
        otherItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String alertFrom = JOptionPane.showInputDialog("What is your alert?");
                handleAlert(alertFrom);
            }
        });
        alertMenu.add(fireItem);
        alertMenu.add(floodItem);
        alertMenu.add(animalItem);
        alertMenu.add(otherItem);

        menuBar.add(alertMenu);

        JTabbedPane mainTabbedPane = new JTabbedPane();
        mainTabbedPane.setLocation(10,0);
        mainTabbedPane.setSize(1490,700);

        mainTabbedPane.setBackground(Color.ORANGE);
        mainTabbedPane.setForeground(Color.darkGray);
        mainTabbedPane.addTab("Home", homePanel);
        mainTabbedPane.addTab("Gallery", galleryPanel);
        mainTabbedPane.addTab("Login", loginPanel);
        mainTabbedPane.addTab("Map", mapPanel);

        mainFrame.add(mainTabbedPane);
        mainFrame.setVisible(true);
        mainFrame.setJMenuBar(menuBar);
        mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private static void handleAlert(String alertFrom){
        String result = JOptionPane.showInputDialog("Are you sure there is a " + alertFrom + " in you area?");
        result = result.toLowerCase();
        if (result.equals("yes") || result.equals("y")){

        }
    }

    private static void handleContact(){
        JFrame contactFrame = new JFrame("Contact");
        contactFrame.setLayout(null);
        contactFrame.setSize(600,600);

        JPanel newContactPanel = new JPanel();
        newContactPanel.setSize(600,600);

        Connection newConnection = new Connection(newContactPanel, "contact");
        newConnection.run();

        JTextArea home = newConnection.getHomeContent();
        home.setEditable(false);
        newContactPanel.add(home);
        newConnection.terminateConnection();

        contactFrame.add(newContactPanel);
        contactFrame.setVisible(true);
    }
    private static void handleDoc(){
        JFrame docFrame = new JFrame("Doc");
        docFrame.setLayout(null);
        docFrame.setSize(600,600);

        JPanel newDocPanel = new JPanel();
        newDocPanel.setSize(600,600);

        Connection newConnection = new Connection(newDocPanel, "getDoc");
        newConnection.run();

        JTextArea home = newConnection.getHomeContent();
        home.setEditable(false);
        newDocPanel.add(home);
        newConnection.terminateConnection();

        docFrame.add(newDocPanel);
        docFrame.setVisible(true);
    }

    static class Connection {

        Socket socket;
        BufferedWriter writer;
        BufferedReader reader;
        JTextArea textArea = new JTextArea(10,50);

        Connection(JPanel panel, String cmd){
            try {
                socket = new Socket("localhost", 6000);
                writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                writer.write(cmd);
                writer.write("\r\n");
                writer.flush();

            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(panel, "Connection Refused");
                return;
            }
        }
        public void run() {
            String eachLine = "";

            try{
                while((eachLine = reader.readLine()) != null){
                    this.textArea.append(eachLine);
                    this.textArea.append("\n");
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
            return this.textArea;
        }
    }
}

