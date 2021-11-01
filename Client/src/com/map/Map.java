package com.map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;


public class Map{

    class Connection {

        Socket socket;
        BufferedWriter writer;
        BufferedReader reader;
        int distance;

        Connection(){
            try {
                socket = new Socket("localhost", 6000);
                writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                writer.write("findDistance");
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
                    System.out.println(eachLine);
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

    }

    public class DrawCanvas extends JPanel{
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Toolkit t=Toolkit.getDefaultToolkit();

//            lake
            int lakex[] = {712, 690, 665, 631, 621, 621, 621, 650, 685, 708, 753, 800, 804, 769, 750};
            int lakey[] = {185, 208, 208, 252, 252, 268, 291, 300, 321, 300, 311, 295, 244, 228, 198};
            int lakeNoPoints = 15;
            Polygon lakePolygon = new Polygon(lakex, lakey, lakeNoPoints);
            g.setColor(Color.blue);
            g.drawPolygon(lakePolygon);
            g.fillPolygon(lakePolygon);

//            resort top
            int resorttopx[] = {203, 120, 156, 168, 193, 210, 225, 245, 253, 285};
            int resorttopy[] = {356, 443, 425, 443, 421, 443, 425, 443, 425, 443};
            int resortTopNoPoints = 10;
            Polygon resortPolygon = new Polygon(resorttopx, resorttopy, resortTopNoPoints);
            g.setColor(Color.PINK);
            g.drawPolygon(resortPolygon);
            g.fillPolygon(resortPolygon);

//            resort bottom
            int resortBottomx[] = {156, 168, 193, 210, 225, 245, 253, 253, 154};
            int resortBottomy[] = {425, 443, 421, 443, 425, 443, 425, 532, 532};
            int resortBottomNoPoints = 9;
            Polygon resortBottom = new Polygon(resortBottomx, resortBottomy, resortBottomNoPoints);
            g.setColor(Color.GRAY);
            g.drawPolygon(resortBottom);
            g.fillPolygon(resortBottom);

//            elephant sanctuary
            Image elephant = t.getImage("D:\\sem 05\\Assignment Presentation\\Java\\R&D\\elephant.png");
            g.drawImage(elephant, 379, 125, this);

//            tiger reserver
//            g.fillOval(738,53,207,79);

            Image tiger=t.getImage("D:\\sem 05\\Assignment Presentation\\Java\\R&D\\tiger.png");
            g.drawImage(tiger, 738,53,this);

//            fire camp
            g.setColor(Color.red);
            g.fillOval(963,241,146,95);

//            meadow
            int meadowx[] = {920, 920, 1091, 1222, 1222, 1075};
            int meadowy[] = {391, 478, 516, 497, 372, 360};
            int meadowNoPoints = 6;
            Polygon meadow = new Polygon(meadowx, meadowy, meadowNoPoints);
            g.setColor(Color.green);
            g.drawPolygon(meadow);
            g.fillPolygon(meadow);

//            dense forest
            int densex[] = {0,0, 230, 230};
            int densey[] = {0,186, 186, 0};
            int denseNoPoints = 4;
            Polygon dense = new Polygon(densex, densey, denseNoPoints);
            g.setColor(Color.GREEN);
            g.drawPolygon(dense);
            g.fillPolygon(dense);

//            string
            g.setColor(Color.black);
            g.drawString("dense forest", 46,77);
            g.drawString("elephant santuary", 379,111);
            g.drawString("resort", 178, 350);
            g.drawString(" lake", 683,241);
            g.drawString("tiger reserve",754,37);
            g.drawString("fire camp",995,273);
            g.drawString("meadow",1036,425);

            g.drawLine(277,346,338,259);

        }
    }

    public Map(JPanel mapPanel){
        DrawCanvas canvas = new DrawCanvas();
        canvas.setPreferredSize(new Dimension(1400, 600));
        mapPanel.add(canvas);
        new DrawCanvas();

        JButton findOptimalButton = new JButton("Find Optimal");
        findOptimalButton.setSize(128,27);

        findOptimalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JPanel mimDisPanel = new JPanel(new GridLayout(3,2));
                JLabel fromLabel = new JLabel("From");
                JTextField fromPlace = new JTextField();
                JLabel toLabel = new JLabel("To");
                JTextField toPlace = new JTextField();
                JButton button = new JButton("search");
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(null, "Distance: 50");
                    }
                });
                mimDisPanel.add(fromLabel);
                mimDisPanel.add(fromPlace);
                mimDisPanel.add(toLabel);
                mimDisPanel.add(toPlace);
                mimDisPanel.add(button);

                JOptionPane.showMessageDialog(null, mimDisPanel);
            }
        });

        mapPanel.add(findOptimalButton);
    }

}
