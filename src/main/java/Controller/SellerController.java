package Controller;

import Model.*;

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
        return ((Seller) user).getCompanyName();
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
        if (!((Seller) user).getOnSaleProducts().contains(ProductController.getProductById(productId)))
            throw new Exception("Seller does'nt have this product");
        for (SellingLog sellingLog : ((Seller) user).getAllSellingLogs()) {
            Product product = sellingLog.getBuyingProducts();
            if (product.getProductId().equals(productId))
                allBuyers.add(sellingLog.getCustomer().getUsername());
        }
        return allBuyers;
    }

    public void removeProductFromUser(User user, String productId) throws Exception {
        Product product = ProductController.getProductById(productId);
        if (product == null) {
            throw new Exception("There is not this product");
        }
        if (!((Seller) user).getOnSaleProducts().contains(product)) {
            throw new Exception("Seller does'nt have this product");
        }
        if (product.getSellersOfThisProduct().size() == 1) {
            ManagerController.getInstance().removeProduct(productId);
        } else {
            ((Seller)user).removeProduct(product);
            product.removeSeller((Seller)user);
        }
    }

    public ArrayList<String> showProductsOfThisSeller(User user) {
        ArrayList<String> products = new ArrayList<>();
        for (Product product : ((Seller) user).getOnSaleProducts()) {
            products.add("name=" + product.getName() + ", price=" + product.getPrice() + ", rate=" + (product.getSumOfCustomersRate() / product.getCustomersWhoRated()));
        }
        return products;
    }

    public ArrayList<String> getCategoryFeatures(String categoryName) {
        return ManagerController.getCategoryByName(categoryName).getSpecialProperties();
    }

    public void addProduct(String[] productGeneralInformation, User user, HashMap<String, String> specialInformationRelatedToCategory) {
        ArrayList<Seller> sellers = new ArrayList<>();
        sellers.add((Seller) user);
        Product newProduct = new Product("Product" + (Product.allProducts.size() + 1), productGeneralInformation[0], productGeneralInformation[1], Double.parseDouble(productGeneralInformation[2]), ManagerController.getCategoryByName(productGeneralInformation[3]), productGeneralInformation[4], sellers, specialInformationRelatedToCategory);
        new ProductRequest(newProduct, false);
        ManagerController.getCategoryByName(productGeneralInformation[3]).addProduct(newProduct);
        ((Seller) user).addProduct(newProduct);
    }

    public void editProduct(User user, String productId, double price, int available, String information, HashMap<String, String> specialInformationRelatedToCategory) throws Exception {
        Product product = ProductController.getProductById(productId);
        if (product == null)
            throw new Exception("There is'nt this Product");
        if (product.getSellersOfThisProduct().contains((Seller) user))
            throw new Exception("Seller does'nt have this product");
        (new ProductRequest(product, true)).newProductFeatures(price, available, information, specialInformationRelatedToCategory);
    }

    public void removeProduct(String productId) throws Exception {
        Product product = ProductController.getProductById(productId);
        if (product == null)
            throw new Exception("There is not product with this id");
        for (Seller seller : product.getSellersOfThisProduct()) {
            seller.removeProduct(product);
        }
        product.getCategory().removeProduct(product);
    }

    public String[] showAllOffs(User user) {
        checkTimeOfOffs();
        ArrayList<String> offs = new ArrayList<>();
        for (Off sellerOff : ((Seller) user).getSellerOffs()) {
            offs.add("id: " + sellerOff.getOffId() + ", beginTime: " + sellerOff.getBeginTime() + ",endTime: " + sellerOff.getEndTime() + ", offAmount: " + sellerOff.getOffAmount());
        }
        return (String[]) offs.toArray();
    }

    public String[] showOff(String offId) throws Exception{
        Off off = getOffById(offId);
        if(off == null)
            throw new Exception("Id is invalid");
        ArrayList<String> information = new ArrayList<>();
        information.add(String.valueOf(off.getBeginTime()));
        information.add(String.valueOf(off.getEndTime()));
        information.add(String.valueOf(off.getOffAmount()));
        ArrayList<String> products = (ArrayList<String>) off.getOnSaleProducts().stream()
                .map(product -> "id: " + product.getProductId() + ", name: " + product.getName());
        information.add(String.valueOf(products.toArray()));
        return (String[]) information.toArray();
    }

    public void addOff(User user, ArrayList<String> productsId, Date beginTime, Date endTime, int percent) throws Exception {
        checkTimeOfOffs();
        ArrayList<Product> products = new ArrayList<>();
        for (String s : productsId) {
            products.add(ProductController.getProductById(s));
        }
        for (Product product : products) {
            if (product.getOff() != null)
                throw new Exception("some products are in another off list");
        }
        Off newOff = new Off((Seller) user, "Off" + beginTime, beginTime, endTime, (double) percent, products);
        new OffRequest(newOff, false);
        ((Seller) user).addOffToThisSeller(newOff);
    }

    private void checkTimeOfOffs() {
        ArrayList<Off> allOffs = Off.getAllOffs();
        for (Off off : allOffs) {
            if(off.getEndTime().before(new Date())) {
                off.removeOff();
                off.getSeller().removeOffFromThisSeller(off);
                for (Product product : off.getOnSaleProducts()) {
                    product.setOff(null);
                }
            } else if (off.getBeginTime().before(new Date())) {
                for (Product product : off.getOnSaleProducts()) {
                    product.setOff(off);
                }
            }
        }
    }

    public void editOff(User user, String offId, ArrayList<String> products, Date beginTime, Date endTime, Double percent) throws Exception {
        Off off = getOffById(offId);
        if (!off.getSeller().equals((Seller) user)) {
            throw new Exception("Seller Does'nt have this off");
        }
        ArrayList<Product> newProducts = (ArrayList<Product>) products.stream()
                .map(product -> ProductController.getProductById(product));
        (new OffRequest(off, true)).setOff(beginTime, endTime, percent, newProducts);
    }

    private Off getOffById(String id) {
        for (Off off : Off.getAllOffs()) {
            if (off.getOffId().equalsIgnoreCase(id)) {
                return off;
            }
        }
        return null;
    }

    public Double showBalanceOfSeller(User user) {
        return user.getCredit();
    }

}
