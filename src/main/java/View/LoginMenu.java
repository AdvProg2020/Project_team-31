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
                register(matcher.group(1), matcher.group(2));
            else if (getMatcher("^(?i)login\\s+(\\S+)$", command).find())
                login();
            else if (getMatcher("^(?i)back$", command).find())
                break;
            else System.out.println("invalid command");
        }
    }

    public void register(String type, String username) {

        if (type.equalsIgnoreCase("customer"))
            customerRegister(type, username);
        if (type.equalsIgnoreCase("manager"))
            managerRegister(type, username);
        if (type.equalsIgnoreCase("seller"))
            sellerRegister(type, username);

    }

    private void customerRegister(String type, String username) {
    }

    private void managerRegister(String type, String username) {
    }

    private void sellerRegister(String type, String username) {

    }

    public void login() {

    }
}
