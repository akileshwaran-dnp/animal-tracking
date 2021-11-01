import java.net.*;
import java.io.*;
import java.util.*;

import com.animalInfo.AnimalInfo;
import com.doc.Document;
import com.homePage.HomePage;
import com.login.Login;
import com.contact.Contact;
import com.user.DeleteUser;
import com.user.User;
import com.user.UserCred;


public class Server implements Runnable{

    Socket socket;

    public static Vector client = new Vector();

    public Server(Socket socket){
        try{
            this.socket = socket;
        }catch(Exception e){}
    }


    public void run(){
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String cmd = reader.readLine().trim();
            System.out.println(cmd);

            if (cmd.equals("getHomePage")){
                HomePage homePage = new HomePage(writer);
            }else if (cmd.startsWith("validate")){
                String[] credentails = cmd.split(" ");
                Login login = new Login(writer, credentails[1], credentails[2]);
            } else if (cmd.equals("contact")){
                Contact contact = new Contact(writer);
            } else if(cmd.equals("getDoc")){
                Document doc = new Document(writer);
            } else if (cmd.startsWith("createTourist")){
                String[] info = cmd.split(" ");
                User user = new User(writer, info[1], info[2], info[3], info[4], info[5]);
            } else if (cmd.startsWith("createUser")){
                String[] info = cmd.split(" ");
                UserCred userCred = new UserCred(writer, info[1], info[2], info[3]);
            } else if (cmd.startsWith("deleteUser")){
                String[] info = cmd.split(" ");
                DeleteUser deleteUser = new DeleteUser(writer, info[1]);
            } else if (cmd.startsWith("searchAnimal")){
                String[] animalInfo = cmd.split(" ");
                AnimalInfo animalInfo1 = new AnimalInfo(writer, animalInfo[1]);
            } else if (cmd.startsWith("findDistance")){
                String[] info = cmd.split(" ");
            }

            client.add(writer);

            while(true){
                String data = reader.readLine().trim();
                System.out.println("Received "+data);

                for(int i = 0; i < client.size(); i++){
                    try{
                        BufferedWriter bw = (BufferedWriter)client.get(i);
                        bw.write(data);
                        bw.write("\r\n");
                        bw.flush();
                    }catch(Exception e){}
                }

            }
        }catch(Exception e){}

    }

    public static void main(String[] args) throws Exception{
        ServerSocket s = new ServerSocket(6000);
        while(true){
            Socket socket = s.accept();
            Server server = new Server(socket);
            Thread thread = new Thread(server);
            thread.start();
        }
    }
}
