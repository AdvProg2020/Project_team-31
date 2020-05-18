package View;

import java.util.regex.Matcher;

public class LoginMenu extends Menu {
    public static LoginMenu instance = null;
    boolean loginSuccessful = false;

    private LoginMenu() {
    }

    public static LoginMenu getInstance() {
        if (instance == null)
            instance = new LoginMenu();
        return instance;
    }

    @Override
    public void run() {
        String command;
        while (!loginSuccessful) {
            command = scanner.nextLine().trim();
            Matcher matcher = safeGetMatcher("^(?i)create\\s+account\\s+(customer|seller|manager)\\s+(\\S+)$", command);
            Matcher login = safeGetMatcher("^(?i)login\\s+(\\S+)$", command);
            if (matcher.find())
                register(matcher.group(1), matcher.group(2), false);
            else if (login.find())
                login(login.group(1));
            else if (safeGetMatcher("^(?i)help$", command).find())
                help();
            else if (safeGetMatcher("^(?i)logout$", command).find())
                loginAndLogOut(false);
            else if (safeGetMatcher("^(?i)back$", command).find())
                break;
            else System.out.println("invalid command");
        }
    }

    private void help() {
        System.out.println("///////////////////////help////////////////////");
        System.out.println("create account (customer or seller or manager) [username]\nlogin [username]\nhelp\nback");
        System.out.println("///////////////////////help////////////////////");
    }

    public void register(String type, String username, boolean managerCommand) {
        if (!typeCheck(type)) {
            System.out.println("please enter a valid role");
            return;
        }
        if (loginController.isThereAnyManager() && !managerCommand) {
            System.out.println("there is already a manager!");
            return;
        } else if (!loginController.isUsernameFree(username)) {
            System.out.println("this username have been taken!");
            return;
        }
        String password = "";
        System.out.println("please enter your password:" +
                "Password must be between 4 and 8 digits long and include at least one numeric digit.");
        while (!password.matches("^(?=.*\\d).{4,8}$")) {
            password = scanner.nextLine();
            if (!password.matches("^(?=.*\\d).{4,8}$"))
                System.out.println("unacceptable password");
        }
        String[] information = new String[6];
        System.out.println("please enter your firstname:");
        information[0] = scanner.nextLine().trim();
        System.out.println("please enter your lastname:");
        information[1] = scanner.nextLine().trim();
        while (information[2] == null || !information[2].matches("^(.+)@(.+)$")) {
            System.out.println("please enter your email address:");
            information[2] = scanner.nextLine().trim();
            if (!information[2].matches("^(.+)@(.+)$"))
                System.out.println("incorrect email address");
        }
        while (information[3] == null || !information[3].matches("^[0-9]{6,14}$")) {
            System.out.println("please enter your phone number:");
            information[3] = scanner.nextLine().trim();
            if (!information[3].matches("^[0-9]{6,14}$"))
                System.out.println("incorrect phone number");
        }
        information[4] = password;
        if (type.equalsIgnoreCase("seller")) {
            System.out.println("please enter your company name:");
            information[5] = scanner.nextLine().trim();
        }
        try {
            loginController.register(username, type, information);
            System.out.println("you registered successfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean typeCheck(String type) {
        if (!type.equalsIgnoreCase("manager"))
            if (!type.equalsIgnoreCase("seller"))
                return type.equalsIgnoreCase("customer");
        return true;
    }

    public void login(String username) {
        if (user != null) {
            System.out.println("you have already logged in!");
            return;
        }
        System.out.println("please enter your password:");
        String password = scanner.nextLine();
        try {
            loginController.login(username, password, card);
            System.out.println("you logged in successfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void logout() {
        user = null;
        card = null;
        System.out.println("you logged out successfully.");
    }

}
