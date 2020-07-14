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

    public User createTempUser() {
        return new User();
    }

    public void register(String username, String role, String[] information) throws Exception {
        if (role.equals("customer")) {
            new Customer(information[0], information[1], username, information[2], information[3], information[4]);
        } else if (role.equals("seller")) {
            new SellerRequest("Request" + (Request.getNumberOfRequestCreated() + 1), username, information);
        } else if (role.equals("manager")) {
            new Manager(information[0], information[1], username, information[2], information[3], information[4]);
        } else {
            throw new Exception("role is invalid");
        }
    }

    public boolean isThereAnyManager() {
        return Manager.getAllManagers().size() > 0;
    }

    public boolean isUsernameFree(String username) {
        for (User user : User.getAllUsers())
            if (user.getUsername().equals(username))
                return false;
        return true;
    }

    public User login(String username, String password, Card card) throws Exception {
        User user = getUserByUsername(username);
        if (user == null) {
            throw new Exception("There is not user with this username");
        }
        if (user.getPassword().equals(password)) {
            if (card.getProductsInThisCard().size() > 0) {
                user.setCard(card);
            } else if (user.getCard() == null) {
                user.setCard(new Card());
            }
            return user;
        } else
            throw new Exception("Password is incorrect");

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
        System.out.println("the users number   " + User.getAllUsers().size());
        for (User user : User.getAllUsers()) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }
}



