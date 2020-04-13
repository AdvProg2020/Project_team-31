package View;

public class LoginMenu extends Menu {
    public static LoginMenu instance = null;
    boolean loginSuccessful = false;

    private LoginMenu() {
    }

    public static LoginMenu getInstance() {
        if (instance != null)
            instance = new LoginMenu();
        return instance;
    }

    @Override
    public void run() {
        String command;
        while (!loginSuccessful) {
            command = scanner.nextLine().trim();
            if (getMatcher("^(?i)create\\s+account\\s+(customer|seller|manager)\\s+(\\S+)$", command).find())
                register();
            else if (getMatcher("^(?i)login\\s+(\\S+)$", command).find())
                login();
            else System.out.println("invalid command");
        }
    }

    public void register() {

    }

    public void login() {

    }
}
