package Model;

import java.util.ArrayList;

public class Supporter extends User {
    private static ArrayList<Supporter> allSupporters = new ArrayList<>();

    public Supporter(String name, String lastName, String username, String emailAddress, String phoneNumber, String password) {
        super(name, lastName, username, emailAddress, phoneNumber, password);
        allSupporters.add(this);
    }
}
