package Model;

import java.io.*;
import java.util.ArrayList;

public class Supporter extends User {
    private static ArrayList<Supporter> allSupporters = new ArrayList<>();

    public Supporter(String name, String lastName, String username, String emailAddress, String phoneNumber, String password) {
        super(name, lastName, username, emailAddress, phoneNumber, password);
        allSupporters.add(this);
    }

    public static void logToFile(){
        try{
            FileOutputStream file = new FileOutputStream("src/project files/allSupporters.txt");
            ObjectOutputStream allSupporters = new ObjectOutputStream(file);

            allSupporters.writeObject(Supporter.allSupporters);
            allSupporters.flush();
            allSupporters.close();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void fileToLog(){
        try{
            FileInputStream file = new FileInputStream("src/project files/allSupporters.txt");
            ObjectInputStream allSupporters = new ObjectInputStream(file);

            Supporter.allSupporters = (ArrayList<Supporter>) allSupporters.readObject();
            allSupporters.close();
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
