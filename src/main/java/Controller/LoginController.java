package Controller;

import Model.*;
import java.lang.String;

public class LoginController {
    private static LoginController loginControllerInstance = new LoginController();

    private LoginController() {
    }

    public static LoginController getInstance() {
        return loginControllerInstance;
    }

    public void register(String username, String role, String[] information) {
        if (role.equals("customer")) {
            new Customer(information[0], information[1], username, information[2], information[3], information[4]);
        } else if (role.equals("seller")) {
            new SellerRequest(username, information);
        } else {
            new Manager(information[0], information[1], username, information[2], information[3], information[4]);
        }
    }

    public boolean isThereAnyManager() {
        return Manager.getAllManagers().size() > 0;
    }

    public boolean IsUsernameFree(String username) {
        for (User user : User.getAllUsers())
            if (user.getUsername().equals(username))
                return false;
        return true;
    }

    public User login(String username, String password) throws Exception {
        for (User user : User.getAllUsers()) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password))
                    return user;
                else
                    throw new Exception("Password is incorrect");
            }
        }
        throw new Exception("There is already a user with this username");
    }

    public String[] showPersonalInformation(User user) {
        return user.getPersonalInformation();
    }

    public void editPersonalInformation(User user, String[] newInformation) {
        user.setNewInformation(newInformation[0], newInformation[1], newInformation[2], newInformation[3], newInformation[4]);
        if (user instanceof Seller) {
            ((Seller) user).setCompanyName(newInformation[5]);
        }
    }

   public static User getUserByUsername(String username) {
        for (User user : User.getAllUsers()) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }
}



