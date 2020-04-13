package View;

public class LoginMenu {
    public static LoginMenu instance = null;

    private LoginMenu() {
    }

    public static LoginMenu getInstance() {
        if (instance != null)
            instance = new LoginMenu();
        return instance;
    }

    public void register() {

    }

    public void login() {

    }
}
