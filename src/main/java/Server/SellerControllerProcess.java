package Server;

public class SellerControllerProcess {
    private static SellerControllerProcess instance;

    public static SellerControllerProcess getInstance() {
        if (instance == null)
            instance = new SellerControllerProcess();
        return instance;
    }

    private SellerControllerProcess() {
    }
}
