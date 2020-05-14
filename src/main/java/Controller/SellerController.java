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

    public ArrayList<String> showBuyersOfThisProduct(User user, String productId) throws Exception {
        ArrayList<String> allBuyers = new ArrayList<>();
        if(!((Seller)user).getOnSaleProducts().contains(ProductController.getProductById(productId)))
            throw  new Exception("Seller does'nt have this product");
        for (SellingLog sellingLog : ((Seller) user).getAllSellingLogs()) {
            Product product = sellingLog.getBuyingProducts();
            if(product.getProductId().equals(productId))
                allBuyers.add(sellingLog.getCustomer().getUsername());
        }
        return allBuyers;
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
        new ProductRequest(new Product("Product" + (Product.allProducts.size()+1) ,productGeneralInformation[0], productGeneralInformation[1], Double.parseDouble(productGeneralInformation[2]),ManagerController.getCategoryByName(productGeneralInformation[3]),productGeneralInformation[4], sellers,specialInformationRelatedToCategory), false);
    }

    public void editProduct(User user, String productId, double price, int available, String information, HashMap<String, String> specialInformationRelatedToCategory) throws Exception {
        Product product = ProductController.getProductById(productId);
        if(product == null)
            throw new Exception("There is'nt this Product");
        if(product.getSellersOfThisProduct().contains((Seller)user))
            throw new Exception("Seller does'nt have this product");
        (new ProductRequest(product, true)).newProductFeatures(price,available,information,specialInformationRelatedToCategory);
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
        new OffRequest(new Off("Off"+beginTime, beginTime, endTime, (double) percent, products), false);
    }

    public void editOff(User user, String offId, ArrayList<String> products, Date beginTime, Date endTime, int percent) {
        // checking seller
    }

    public Double showBalanceOfSeller(User user){
        return user.getCredit();
    }

}
