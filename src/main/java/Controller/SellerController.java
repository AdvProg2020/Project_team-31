package Controller;

import Model.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class SellerController {
    private static SellerController sellerControllerInstance = new SellerController();

    private SellerController() {
    }

    public static SellerController getInstance() {
        return sellerControllerInstance;
    }

    public String showCompanyInformation(User user) {
        return ((Seller)user).getCompanyName();
    }

    public ArrayList<String> showSalesHistory(User user) {
        return null;
    }

    public ArrayList<String> showProductsOfThisSeller(User user) {
        ArrayList<String> products = new ArrayList<>();
        for (Product product : ((Seller) user).getOnSaleProducts()) {
            products.add("name=" + product.getName() + ", price=" + product.getPrice() + ", rate=" + product.getMeanOfCustomersRate());
        }
        return products;
    }

    public ArrayList<String> getCategoryFeatures(String categoryName) {
        return ManagerController.getCategoryByName(categoryName).getSpecialProperties();
    }

    public void addProduct(String[] productGeneralInformation,ArrayList<String> sellerOfThisProduct, HashMap<String, String> specialInformationRelatedToCategory) {
        ArrayList<Seller> sellers = new ArrayList<>();
        for (String s : sellerOfThisProduct) {
            sellers.add((Seller)LoginController.getUserByUsername(s));
        }

        new ProductRequest(new Product("Product" + (Product.allProducts.size()+1) ,productGeneralInformation[0], productGeneralInformation[1], Double.parseDouble(productGeneralInformation[2]),ManagerController.getCategoryByName(productGeneralInformation[3]),productGeneralInformation[4], sellers,specialInformationRelatedToCategory));
    }

    public void editProduct(String[] productGeneralInformation, HashMap<String, String> specialInformationRelatedToCategory) {

    }

    public void removeProduct(String productId){

    }

    public String[] showAllOffs(User user){
        return null;
    }

    public String[] showOff(String offId){
        return null;
    }

    public void addOff(User user, String[] information){

    }

    public int ShowBalanceOfSeller(User user){
        return 0;
    }

}
