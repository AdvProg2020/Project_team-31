package Controller;

import Model.*;
import org.graalvm.compiler.lir.LIRInstruction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
        ArrayList<String> allSellingLogs = new ArrayList<>();
        for (SellingLog log : ((Seller) user).getAllSellingLogs()) {
            allSellingLogs.add("Id: " + log.getLogId() + ", Date: " + log.getDate() + ", Price: " + log.getTotalPriceArrived() + ", CustomerId: " + log.getCustomer().getUsername());
        }
        return allSellingLogs;
    }

    public ArrayList<String> showProductsOfThisSeller(User user) {
        ArrayList<String> products = new ArrayList<>();
        for (Product product : ((Seller) user).getOnSaleProducts()) {
            products.add("name=" + product.getName() + ", price=" + product.getPrice() + ", rate=" + (product.getSumOfCustomersRate()/product.getCustomersWhoRated()));
        }
        return products;
    }

    public ArrayList<String> getCategoryFeatures(String categoryName) {
        return ManagerController.getCategoryByName(categoryName).getSpecialProperties();
    }

    public void addProduct(String[] productGeneralInformation, User user, HashMap<String, String> specialInformationRelatedToCategory) {
        ArrayList<Seller> sellers = new ArrayList<>();
        sellers.add((Seller)user);
        new ProductRequest(new Product("Product" + (Product.allProducts.size()+1) ,productGeneralInformation[0], productGeneralInformation[1], Double.parseDouble(productGeneralInformation[2]),ManagerController.getCategoryByName(productGeneralInformation[3]),productGeneralInformation[4], sellers,specialInformationRelatedToCategory));
    }

    public void editProduct(String[] productGeneralInformation, HashMap<String, String> specialInformationRelatedToCategory) {
        // bayad dar request ha taghir ijad konim
    }

    public void removeProduct(String productId) throws Exception {
        Product product = ProductController.getProductById(productId);
        if(product == null)
            throw new Exception("There is not product with this id");
        for (Seller seller : product.getSellersOfThisProduct()) {
            seller.removeProduct(product);
        }
        product.getCategory().removeProduct(product);
    }

    public String[] showAllOffs(User user){
        return null;
    }

    public String[] showOff(String offId){
        return null;
    }

    public void addOff(User user, ArrayList<String> productsId, Date beginTime, Date endTime, int percent){
        ArrayList<Product> products = new ArrayList<>();
        for (String s : productsId) {
            products.add(ProductController.getProductById(s));
        }
        new OffRequest(new Off("Off"+beginTime, beginTime, endTime, (double) percent, products));
    }

    public Double showBalanceOfSeller(User user){
        return user.getCredit();
    }

}
