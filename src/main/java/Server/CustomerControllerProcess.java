package Server;

public class CustomerControllerProcess {
    private static CustomerControllerProcess instance;

    public static CustomerControllerProcess getInstance() {
        if (instance == null)
            instance = new CustomerControllerProcess();
        return instance;
    }

    private CustomerControllerProcess() {
    }

}
