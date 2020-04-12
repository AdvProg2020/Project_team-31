package Controller;

import Model.Seller;
import Model.User;

import java.io.Serializable;
import java.util.HashMap;

public class SellerController {
    private static SellerController sellerControllerInstance = new SellerController();

    private SellerController() {
    }

    public static SellerController getInstance() {
        return sellerControllerInstance;
    }

    public String showCompanyInformation(User user) {
        return null;
    }

    public String showSalesHistory(User user) {
        return null;
    }

    public String showProductsOfThisSeller(User user) {
        return null;
    }

    public String showProduct(String productId) {
        return null;
    }

    public String getCategoryFeatures(String categoryName) {
        return null;
    }

    public void addProduct(String[] productGeneralInformation, HashMap<String, String> specialInformationRelatedToCategory) {

    }

    public void editProduct(String[] productGeneralInformation, HashMap<String, String> specialInformationRelatedToCategory) {

    }

    public void removeProduct(String productId){

    }

    public String showAllOffs(User user){
        return null;
    }

    public String showOff(String offId){
        return null;
    }

    public void addOff(User user, String[] information){

    }

    public int ShowBalanceOfSeller(User user){
        return 0;
    }

}
