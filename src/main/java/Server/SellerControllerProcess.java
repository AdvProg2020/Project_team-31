package Server;

import Controller.SellerController;
import Model.User;
import com.google.gson.JsonObject;

public class SellerControllerProcess {
    SellerController sellerController = SellerController.getInstance();
    private static SellerControllerProcess instance;

    public static SellerControllerProcess getInstance() {
        if (instance == null)
            instance = new SellerControllerProcess();
        return instance;
    }

    private SellerControllerProcess() {
    }

    public JsonObject showCompanyInformation(JsonObject jsonObject, User user) {
        try {
            JsonObject data = new JsonObject();
            data.addProperty("company", sellerController.showCompanyInformation(user));
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
