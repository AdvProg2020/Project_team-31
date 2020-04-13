package Controller;

import Model.Customer;
import Model.Manager;
import Model.Seller;
import Model.User;
import java.lang.String;

import Model.Manager.*;

import java.util.ArrayList;

public class LoginController {
    private static LoginController loginControllerInstance = new LoginController();

    private LoginController() {
    }

    public static LoginController getInstance() {
        return loginControllerInstance;
    }

    public void register(String username, String role, String[] information) throws canNotRegisterManager, thereIsAnotherUserWithThisName {
        if (role.equals("manager") && Manager.allManagers.size() > 0) {
            throw new canNotRegisterManager("manager is registered before");
        }
        for (User user : User.allUsers) {
            if (user.getUsername().equals(username))
                throw new thereIsAnotherUserWithThisName("There is another person with this name");
        }
        if (role.equals("customer")) {
            new Customer(information[0], information[1], username, information[2], information[3], information[4]);
        } else if (role.equals("seller")) {
            new Seller(information[0], information[1], username, information[2], information[3], information[4], information[5]);
        } else {
            new Manager(information[0], information[1], username, information[2], information[3], information[4]);
        }
    }

    public User login(String username, String password) throws incorrectPassword, thereIsNotThisUser{
        for (User user : User.allUsers) {
            if (user.getUsername().equals(username)){
                if(user.getPassword().equals(password))
                    return user;
                else
                    throw new incorrectPassword("Password is incorrect");
            }
        }
        throw new thereIsNotThisUser("There is any user with this username");
    }

    public String[] showPersonalInformation(User user) {
        return user.getPersonalInformation();
    }

    public void editPersonalInformation(User user, String[] newInformation) {
        user.setNewInformation(newInformation[0], newInformation[1], newInformation[2], newInformation[3], newInformation[4]);
        if(user instanceof Seller){
            ((Seller)user).setCompanyName(newInformation[5]);
        }
    }
}

class canNotRegisterManager extends Exception {
    public canNotRegisterManager(String message) {
        super(message);
    }
}

class thereIsAnotherUserWithThisName extends Exception {
    public thereIsAnotherUserWithThisName(String message) {
        super(message);
    }
}

class incorrectPassword extends Exception {
    public incorrectPassword(String message) {
        super(message);
    }
}

class thereIsNotThisUser extends Exception {
    public thereIsNotThisUser(String message) {
        super(message);
    }
}

