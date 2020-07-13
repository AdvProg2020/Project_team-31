package Server;

public class LoginControllerProcess {
    private static LoginControllerProcess instance;

    public static LoginControllerProcess getInstance() {
        if (instance == null)
            instance = new LoginControllerProcess();
        return instance;
    }

    private LoginControllerProcess() {
    }
}
