package Server;

public class ProductControllerProcess {
    private static ProductControllerProcess instance;

    public static ProductControllerProcess getInstance() {
        if (instance == null)
            instance = new ProductControllerProcess();
        return instance;
    }

    private ProductControllerProcess() {
    }
}
