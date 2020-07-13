package Server;

public class ManagerControllerProcess {
    private static ManagerControllerProcess instance;

    public static ManagerControllerProcess getInstance() {
        if (instance == null)
            instance = new ManagerControllerProcess();
        return instance;
    }

    private ManagerControllerProcess() {
    }
}
