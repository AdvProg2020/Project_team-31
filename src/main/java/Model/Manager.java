package Model;

import java.io.*;
import java.util.ArrayList;

public class Manager extends User implements Serializable {
    private static ArrayList<Manager> allManagers = new ArrayList<>();
    public static int wagePercent ;

    public Manager(String name, String lastName, String username, String emailAddress, String phoneNumber, String password) {
        super(name, lastName, username, emailAddress, phoneNumber, password);
        allManagers.add(this);
    }

    public void deleteManager() {
        allManagers.remove(this);
    }

    public static ArrayList<Manager> getAllManagers() {
        return allManagers;
    }

    public static void logToFile() {
        try {
            FileOutputStream file = new FileOutputStream("src/project files/allManagers.txt");
            ObjectOutputStream allManagers = new ObjectOutputStream(file);

            allManagers.writeObject(getAllManagers());
            allManagers.flush();
            allManagers.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fileToLog() {
        try {
            FileInputStream file = new FileInputStream("src/project files/allManagers.txt");
            ObjectInputStream allManagers = new ObjectInputStream(file);

            Manager.allManagers = (ArrayList<Manager>) allManagers.readObject();
            allManagers.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
