package Model;

import java.util.ArrayList;

public class Manager extends User {
    public static ArrayList<Manager> allManagers = new ArrayList<Manager>();
    public static Manager mainManager;

    public Manager(String name, String lastName, String username, String emailAddress, int phoneNumber, String password) {
        super(name, lastName, username, emailAddress, phoneNumber, password);
        if (allManagers.size() == 0){
            mainManager = this;
            allManagers.add(this);
        }
    }

    public void setMainManager(Manager mainManager) {
        mainManager = mainManager;
    }

    public boolean isThereMainManger(){
        return mainManager != null;
    }

    public Manager getMainManager() {
        return mainManager;
    }
}
