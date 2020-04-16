package Controller;

import Model.Manager;

import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.lang.String;

public class ManagerController {
    private static ManagerController managerControllerInstance = new ManagerController();

    public ManagerController() {
    }

    public static ManagerController getInstance() {
        return managerControllerInstance;
    }

    public String showUsers() {
        return null;
    }

    public void deleteUser(String username) {

    }

    public void createManager(String username, String password) {

    }

    public void removeProduct(String productId) {

    }

    public void createDiscountCode(String[] information) {

    }

    public String showAllDiscountCodes() {
        return null;
    }

    public String showDiscount(String discount) {
        return null;
    }

    public void editDiscountCode(String discountCode, String[] information) {

    }

    public void removeDiscountCode(String DiscountCode) {

    }

    public String showAllRequests() {
        return null;
    }

    public String showRequestDetails(String requestId) {
        return null;
    }

    public void acceptRequest(String requestId) {

    }

    public void declineRequest(String requestId) {

    }

    public String showAllCategories() {
        return null;
    }

    public void addCategory(String name, ArrayList<String> features) {

    }

    public void removeCategory(String name){

    }

    public void editCategory(String name, ArrayList<String> newFeatures){
    }

    private class isThereDiscountCodeWithThisName extends Exception {
        public isThereDiscountCodeWithThisName(String message) {
            super(message);
        }
    }
}

