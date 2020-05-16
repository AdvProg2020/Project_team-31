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
            Matcher matcher = getMatcher("^(?i)create\\s+account\\s+(customer|seller|manager)\\s+(\\S+)$", command);
            if (matcher.find())
                register(matcher.group(1), matcher.group(2), false);
            else if (getMatcher("^(?i)login\\s+(\\S+)$", command).find())
                login(matcher.group(1));
            else if (getMatcher("^(?i)back$", command).find())
                break;
            else System.out.println("invalid command");
        }
    }

    public void register(String type, String username, boolean managerCommand) {
        if (!typeCheck(type)) {
            System.out.println("please enter a valid role");
            return;
        }
        if (loginController.isThereAnyManager() && !managerCommand) {
            System.out.println("there is already a manager!");
            return;
        } else if (!loginController.IsUsernameFree(username)) {
            System.out.println("this username have been taken!");
            return;
        }
        String password = "";
        System.out.println("please enter your password:" +
                "Password must be between 4 and 8 digits long and include at least one numeric digit.\n");
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean typeCheck(String type) {
        if (!type.equalsIgnoreCase("manager"))
            if (!type.equalsIgnoreCase("seller"))
                if (!type.equalsIgnoreCase("customer"))
                    return false;
        return true;
    }

    public void login(String username) {
        System.out.println("please enter your password:");
        String password = scanner.nextLine();
        try {
            loginController.login(username, password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void logout() {
        user = null;
        System.out.println("you logged out successfully.");
    }

}
