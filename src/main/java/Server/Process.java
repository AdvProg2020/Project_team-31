package Server;

import Model.User;
import com.google.gson.JsonObject;

public class Process {
    CustomerControllerProcess customerControllerProcess = CustomerControllerProcess.getInstance();
    LoginControllerProcess loginControllerProcess = LoginControllerProcess.getInstance();
    ManagerControllerProcess managerControllerProcess = ManagerControllerProcess.getInstance();
    ProductControllerProcess productControllerProcess = ProductControllerProcess.getInstance();
    SellerControllerProcess sellerControllerProcess = SellerControllerProcess.getInstance();

    public JsonObject answerClient(JsonObject jsonObject, User user) {
        JsonObject answer = null;
        String controller = jsonObject.get("controller").getAsString();
        switch (controller) {
            case "customer":
                answer = customerControllerProcess(jsonObject, user);
                break;
            case "login":
                answer = loginControllerProcess(jsonObject, user);
                break;
            case "manager":
                answer = managerControllerProcess(jsonObject, user);
                break;
            case "product":
                answer = productControllerProcess(jsonObject, user);
                break;
            case "seller":
                answer = sellerControllerProcess(jsonObject, user);
                break;
            default:
                System.out.println("controller not found!!!");
        }
        return answer;

    }

    private JsonObject customerControllerProcess(JsonObject jsonObject, User user) {
        JsonObject answer = null;
        String command = jsonObject.get("command").getAsString();
        if (command.equals("showDiscountCodes"))
            answer = customerControllerProcess.showDiscountCodes(jsonObject, user);
        else if (command.equals("addCredit"))
            answer = customerControllerProcess.addCredit(jsonObject, user);
        else if (command.equals("showAllOrdersByList"))
            answer = customerControllerProcess.showAllOrdersByList(jsonObject, user);
        else if (command.equals("getSupporters"))
            answer = customerControllerProcess.getSupporters();
        else if (command.equals("addProductToCart"))
            answer = customerControllerProcess.addProductToCart(user, jsonObject);
        else if (command.equals("rateProduct"))
            answer = customerControllerProcess.rateProduct(user, jsonObject);
        else if(command.equals("addComment"))
            answer = customerControllerProcess.addComment(user, jsonObject);
        else if(command.equals("showCart"))
            answer = customerControllerProcess.showCart(user);
        else if(command.equals("changeNumber"))
            answer = customerControllerProcess.changeNumberOfProductInCart(user, jsonObject);
        else if (command.equals("checkAvailableTOPurchase"))
            answer = customerControllerProcess.checkAvailableToPurchase(user);
        return answer;
    }

    /////////////////////////////////////////////////////////////////////////////////
    private JsonObject loginControllerProcess(JsonObject jsonObject, User user) {
        JsonObject answer = null;
        String command = jsonObject.get("command").getAsString();
        if (command.equals("isThereAnyManager"))
            answer = loginControllerProcess.managerStatus();
        else if (command.equals("showPersonalInformation"))
            answer = loginControllerProcess.showPersonalInformation(jsonObject, user);
        else if (command.equals("editPersonalInformation"))
            answer = loginControllerProcess.editPersonalInformation(jsonObject, user);
        return answer;
    }

    /////////////////////////////////////////////////////////////////////////////////
    private JsonObject managerControllerProcess(JsonObject jsonObject, User user) {
        JsonObject answer = null;
        String command = jsonObject.get("command").getAsString();
        if (command.equals("supporter"))
            answer = managerControllerProcess.supporter(jsonObject, user);
        return answer;
    }

    /////////////////////////////////////////////////////////////////////////////////
    private JsonObject productControllerProcess(JsonObject jsonObject, User user) {
        JsonObject answer = null;
        String command = jsonObject.get("command").getAsString();
        if (command.equals("showAllProducts")) {
            answer = productControllerProcess.showAllProducts(user);
        } else if (command.equals("addFilter")) {
            answer = productControllerProcess.addFilter(jsonObject, user);
        } else if (command.equals("removeFilter")) {
            answer = productControllerProcess.removeFilter(jsonObject, user);
        } else if (command.equals("addView")) {
            answer = productControllerProcess.addView(jsonObject);
        } else if(command.endsWith("showAllOffedProducts")) {
            answer = productControllerProcess.showAllOffedProducts(user);
        } else if(command.equals("showProduct")) {
            answer = productControllerProcess.showProduct(jsonObject);
        }
        return answer;
    }

    /////////////////////////////////////////////////////////////////////////////////
    private JsonObject sellerControllerProcess(JsonObject jsonObject, User user) {
        JsonObject answer = null;
        String command = jsonObject.get("command").getAsString();
        if (command.equals("showCompanyInformation"))
            answer = sellerControllerProcess.showCompanyInformation(jsonObject, user);
        return answer;
    }

}
