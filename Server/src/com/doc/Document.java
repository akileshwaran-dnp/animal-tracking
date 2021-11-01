package com.doc;

import java.io.*;

public class Document {
    public Document(BufferedWriter writer){
        File docContent = new File("D:\\sem 05\\Assignment Presentation\\Java\\Server\\src\\com\\doc\\doc.txt");
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(docContent));
            String eachLine;
            while ((eachLine = bufferedReader.readLine()) != null){
                writer.write(eachLine);
                writer.write("\r\n");
                writer.flush();
            }
            writer.close();
        } catch (IOException ioException){
            ioException.printStackTrace();
        }
    }
}
