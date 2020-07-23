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
            answer = customerControllerProcess.showAllOrdersByList(user);
        else if (command.equals("getSupporters"))
            answer = customerControllerProcess.getSupporters();
        else if (command.equals("addProductToCart"))
            answer = customerControllerProcess.addProductToCart(user, jsonObject);
        else if (command.equals("rateProduct"))
            answer = customerControllerProcess.rateProduct(user, jsonObject);
        else if (command.equals("addComment"))
            answer = customerControllerProcess.addComment(user, jsonObject);
        else if (command.equals("showCart"))
            answer = customerControllerProcess.showCart(user);
        else if (command.equals("changeNumber"))
            answer = customerControllerProcess.changeNumberOfProductInCart(user, jsonObject);
        else if (command.equals("checkAvailableToPurchase"))
            answer = customerControllerProcess.checkAvailableToPurchase(user);
        else if (command.equals("createBuyingLog"))
            answer = customerControllerProcess.createBuyingLog(user, jsonObject);
        else if (command.equals("putDiscount"))
            answer = customerControllerProcess.putDiscount(user, jsonObject);
        else if (command.equals("payMoney"))
            answer = customerControllerProcess.payMoney(user, jsonObject);
        else if (command.equals("supportMe"))
            answer = customerControllerProcess.supportMe(user, jsonObject);
        return answer;
    }

    /////////////////////////////////////////////////////////////////////////////////
    private JsonObject loginControllerProcess(JsonObject jsonObject, User user) {
        JsonObject answer = null;
        String command = jsonObject.get("command").getAsString();
        if (command.equals("isThereAnyManager"))
            answer = loginControllerProcess.managerStatus();
        else if (command.equals("register"))
            answer = loginControllerProcess.register(jsonObject);
        else if (command.equals("showPersonalInformation"))
            answer = loginControllerProcess.showPersonalInformation(jsonObject, user);
        else if (command.equals("editPersonalInformation"))
            answer = loginControllerProcess.editPersonalInformation(jsonObject, user);
        else if (command.equals("login"))
            answer = loginControllerProcess.login(user, jsonObject);
        return answer;
    }

    /////////////////////////////////////////////////////////////////////////////////
    private JsonObject managerControllerProcess(JsonObject jsonObject, User user) {
        JsonObject answer = null;
        String command = jsonObject.get("command").getAsString();
        if (command.equals("supporter"))
            answer = managerControllerProcess.supporter(jsonObject, user);
        else if (command.equals("addSupporter"))
            answer = managerControllerProcess.addSupporter(jsonObject);
        else if (command.equals("showAllUsers"))
            answer = managerControllerProcess.showAllUsers(user);
        else if (command.equals("deleteUser"))
            answer = managerControllerProcess.deleteUser(jsonObject);
        else if (command.equals("addCategory"))
            answer = managerControllerProcess.addCategory(jsonObject);
        else if (command.equals("changeCategoryFeature"))
            answer = managerControllerProcess.changeCategoryFeature(jsonObject);
        else if (command.equals("addCategoryFeature"))
            answer = managerControllerProcess.addCategoryFeature(jsonObject);
        else if (command.equals("removeCategoryFeature"))
            answer = managerControllerProcess.removeCategoryFeature(jsonObject);
        else if (command.equals("getAllDiscount"))
            answer = managerControllerProcess.getAllDiscount();
        else if (command.equals("getDiscountCode"))
            answer = managerControllerProcess.getDiscountCode(jsonObject);
        else if (command.equals("getAllCustomers"))
            answer = managerControllerProcess.getAllCustomers();
        else if (command.equals("createDiscountCode"))
            answer = managerControllerProcess.createDiscountCode(jsonObject);
        else if (command.equals("editDiscount"))
            answer = managerControllerProcess.editDiscount(jsonObject);
        else if (command.equals("getAllRequests"))
            answer = managerControllerProcess.getAllRequests();
        else if (command.equals("showRequest"))
            answer = managerControllerProcess.showRequest(jsonObject);
        else if (command.equals("acceptRequest"))
            answer = managerControllerProcess.acceptRequest(jsonObject);
        else if (command.equals("declineRequest"))
            answer = managerControllerProcess.declineRequest(jsonObject);
        else if (command.equals("deleteProduct"))
            answer = managerControllerProcess.deleteProduct(jsonObject, user);
        else if (command.equals("getProductList"))
            answer = managerControllerProcess.getProductList(user);
        else if (command.equals("getAllLogs"))
            answer = managerControllerProcess.getAllLogs();
        else if (command.equals("sendProduct"))
            answer = managerControllerProcess.sendProduct(jsonObject);
        else if (command.equals("getAllAuctions"))
            answer = managerControllerProcess.getAllAuctions();
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
        } else if (command.endsWith("showAllOffedProducts")) {
            answer = productControllerProcess.showAllOffedProducts(user);
        } else if (command.equals("showProduct")) {
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
        else if (command.equals("getAllCategories"))
            answer = sellerControllerProcess.getAllCategories();
        else if (command.equals("newProduct"))
            answer = sellerControllerProcess.newProduct(jsonObject, user);
        else if (command.equals("getProductData"))
            answer = sellerControllerProcess.getProductData(jsonObject, user);
        else if (command.equals("editProduct"))
            answer = sellerControllerProcess.editProduct(jsonObject, user);
        else if (command.equals("addOff"))
            answer = sellerControllerProcess.addOff(jsonObject, user);
        else if (command.equals("getOffInfo"))
            answer = sellerControllerProcess.getOffInfo(jsonObject, user);
        else if (command.equals("getAllOffs"))
            answer = sellerControllerProcess.getAllOffs(jsonObject, user);
        else if (command.equals("editOff"))
            answer = sellerControllerProcess.editOff(jsonObject, user);
        else if (command.equals("getOffInfoForEdit"))
            answer = sellerControllerProcess.getOffInfoForEdit(jsonObject, user);
        else if (command.equals("showSalesHistory"))
            answer = sellerControllerProcess.showSalesHistory(user);
        else if (command.equals("addProductToSeller"))
            answer = sellerControllerProcess.addSellerToProduct(user, jsonObject);
        else if (command.equals("createAuction"))
            answer = sellerControllerProcess.createAuction(user, jsonObject);
        return answer;
    }

}
