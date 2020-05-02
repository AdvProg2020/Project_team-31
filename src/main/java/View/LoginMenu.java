package View;

import Controller.*;

import java.util.regex.Matcher;

public class LoginMenu extends Menu {
    public static LoginMenu instance = null;
    boolean loginSuccessful = false;
    LoginController loginController = LoginController.getInstance();

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
                register(matcher.group(1), matcher.group(2));
            else if (getMatcher("^(?i)login\\s+(\\S+)$", command).find())
                login(matcher.group(1));
            else if (getMatcher("^(?i)back$", command).find())
                break;
            else System.out.println("invalid command");
        }
    }

    public void register(String type, String username) {
        if (loginController.isThereAnyManager() || !loginController.IsUsernameFree(username))
            return;
        String password = "";
        System.out.println("please enter your password:" +
                "Password must be between 4 and 8 digits long and include at least one numeric digit.\n");
        while (!password.matches("^(?=.*\\d).{4,8}$")) {
            password = scanner.nextLine();
            if (!password.matches("^(?=.*\\d).{4,8}$"))
                System.out.println("unacceptable password");
        }
        String[] information = new String[5];
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
        while (information[3] == null || !information[2].matches("^[0-9]{6,14}$")) {
            System.out.println("please enter your phone number:");
            information[3] = scanner.nextLine().trim();
            if (!information[3].matches("^[0-9]{6,14}$"))
                System.out.println("incorrect phone number");
        }
        if (type.equalsIgnoreCase("seller")) {
            System.out.println("please enter your company name:");
            information[4] = scanner.nextLine().trim();
        }
        loginController.register(username, password, information);
    }

    public void login(String username) {
        System.out.println("please enter your password:");
        String password=scanner.nextLine();
//        try {
//            loginController.login(username,password);
//        } catch ()

    }
}
