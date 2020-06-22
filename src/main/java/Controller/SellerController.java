package Controller;

import Model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

public class SellerController {
    private static SellerController sellerControllerInstance = new SellerController();

    private SellerController() {
    }

    public static SellerController getInstance() {
        return sellerControllerInstance;
    }

    public String showCompanyInformation(User user) throws Exception {
        if (user instanceof Seller)
            return ((Seller) user).getCompanyName();
        throw new Exception("You aren't seller");
    }

    public void addSellerToProduct(User user, String productId, int price) throws Exception {
        if (!(user instanceof Seller)) {
            throw new Exception("You can't be a seller");
        }
        Product product = ProductController.getProductById(productId);
        if (product == null) {
            throw new Exception("productId is invalid");
        }
        if (product.getSellersOfThisProduct().containsKey(user)) {
            throw new Exception("seller has product");
        }
        new SellerOfProductRequest("SellerOfProductRequest" + (Request.getNumberOfRequestCreated() + 1), (Seller) user, product, price);
    }

    public ArrayList<String> showSalesHistory(User user) {
        ArrayList<String> allSellingLogs = new ArrayList<>();
        for (SellingLog log : ((Seller) user).getAllSellingLogs()) {
            allSellingLogs.add("Id: " + log.getLogId() + ", Date: " + log.getDate() + ", Price: " + log.getTotalPriceArrived() + ", CustomerId: " + log.getCustomer().getUsername());
        }
        return allSellingLogs;
    }
    public ArrayList<SellingLog> showSalesHistoryByList(User user) {
        return  ((Seller) user).getAllSellingLogs();
    }

    public ArrayList<String> showBuyersOfThisProduct(User user, String productId) throws Exception {
        if(!(user instanceof Seller)) {
            throw new Exception("You aren't a seller");
        }
        if (ProductController.getProductById(productId) == null) {
            throw new Exception("This product doesn't Exist");
        }
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
        if(!(user instanceof Seller)) {
            throw new Exception("You aren't seller");
        }
        Product product = ProductController.getProductById(productId);
        if (product == null) {
            throw new Exception("There is not this product");
        }
        if (!((Seller) user).getOnSaleProducts().contains(product)) {
            throw new Exception("Seller does'nt have this product");
        }
        if (product.getSellersOfThisProduct().size() == 1) {
            removeProduct(productId);
        } else {
            ((Seller) user).removeProduct(product);
            product.removeSeller((Seller) user);
        }
    }

    public ArrayList<String> showProductsOfThisSeller(User user) {
        ArrayList<String> products = new ArrayList<>();
        for (Product product : ((Seller) user).getOnSaleProducts()) {
            products.add("name=" + product.getName() + ", price=" + product.getSellersOfThisProduct().get((Seller) user) + ", rate=" + (1.0 * product.getSumOfCustomersRate() / product.getCustomersWhoRated()) + ", status=" + product.getProductStatus());
        }
        return products;
    }

    public ArrayList<String> getCategoryFeatures(String categoryName) throws Exception{
        if(ManagerController.getCategoryByName(categoryName) == null ) {
            throw new  Exception("Invalid categoryName");
        }
        return ManagerController.getCategoryByName(categoryName).getSpecialProperties();
    }


    public void addProduct(String[] productGeneralInformation, User user, HashMap<String, String> specialInformationRelatedToCategory) throws Exception {
        HashMap<Seller, Integer> sellers = new HashMap<>();
        sellers.put((Seller) user, Integer.parseInt(productGeneralInformation[2]));
        if(ManagerController.getCategoryByName(productGeneralInformation[3]) == null) {
            throw new Exception("invalid categoryName");
        }
        Product newProduct = new Product("Product" + (Product.getNumberOfProductCreated() + 1), productGeneralInformation[0], productGeneralInformation[1], ManagerController.getCategoryByName(productGeneralInformation[3]), productGeneralInformation[4],Integer.parseInt(productGeneralInformation[5]), sellers, specialInformationRelatedToCategory);
        newProduct.setMinimumPrice(Integer.parseInt(productGeneralInformation[2]));
        new ProductRequest("ProductRequest" + (Request.getNumberOfRequestCreated() + 1), newProduct, false);
        ManagerController.getCategoryByName(productGeneralInformation[3]).addProduct(newProduct);
        ((Seller) user).addProduct(newProduct);
    }

    public void editProduct(User user, String productId, int price, int available, String information, HashMap<String, String> specialInformationRelatedToCategory) throws Exception {
        Product product = ProductController.getProductById(productId);
        if (product == null)
            throw new Exception("There is'nt this Product");
        if (!product.getSellersOfThisProduct().keySet().contains((Seller) user))
            throw new Exception("Seller does'nt have this product");
        product.setProductStatus(ProductAndOffStatus.EDITING);
        (new ProductRequest("ProductRequest" + (Request.getNumberOfRequestCreated() + 1), product, true)).newProductFeatures((Seller) user, price, available, information, specialInformationRelatedToCategory);
    }

    public void removeProduct(String productId) throws Exception {
        Product product = ProductController.getProductById(productId);
        if (product == null)
            throw new Exception("There is not product with this id");
        for (Seller seller : product.getSellersOfThisProduct().keySet()) {
            seller.removeProduct(product);
        }
        product.removeProduct();
        product.getCategory().removeProduct(product);
    }

    public String[] showAllOffs(User user) {
        checkTimeOfOffs();
        ArrayList<String> offs = new ArrayList<>();
        for (Off sellerOff : ((Seller) user).getSellerOffs()) {
            offs.add("id: " + sellerOff.getOffId() + ", beginTime: " + sellerOff.getBeginTime() + ",endTime: " + sellerOff.getEndTime() + ", offPercent: " + sellerOff.getOffPercent() + ", status: " + sellerOff.getOffStatus());
        }
        return (String[]) offs.toArray();
    }

    public String[] showOff(User user, String offId) throws Exception {
        Off off = getOffById(offId);
        if (off == null)
            throw new Exception("Id is invalid");
        if (!off.getSeller().equals((Seller) user)) {
            throw new Exception("Seller doesn't have this off");
        }
        ArrayList<String> information = new ArrayList<>();
        information.add(String.valueOf(off.getOffStatus()));
        information.add(String.valueOf(off.getBeginTime()));
        information.add(String.valueOf(off.getEndTime()));
        information.add(String.valueOf(off.getOffPercent()));
        ArrayList<String> products = (ArrayList<String>) off.getOnSaleProducts().stream()
                .map(product -> "id: " + product.getProductId() + ", name: " + product.getName())
                .collect(Collectors.toList());
        information.add(Arrays.toString(products.toArray()));
        return (String[]) information.toArray();
    }

    public void addOff(User user, ArrayList<String> productsId, Date beginTime, Date endTime, int percent) throws Exception {
        checkTimeOfOffs();
        ArrayList<Product> products = new ArrayList<>();
        for (String s : productsId) {
            Product product = ProductController.getProductById(s);
            if (product == null) {
                throw new Exception("some products doesn't exist");
            }
            products.add(product);
        }
        for (Product product : products) {
            if(!product.getSellersOfThisProduct().containsKey((Seller)user)) {
                throw new Exception("seller doesn't have some products");
            }
        }
        Off newOff = new Off((Seller) user, "Off" + (Off.getNumberOfOffsCreated() + 1), beginTime, endTime, percent, products);
        new OffRequest("OffRequest" + (Request.getNumberOfRequestCreated() + 1), newOff, false);
        ((Seller) user).addOffToThisSeller(newOff);
    }

    public void checkTimeOfOffs() {
        ArrayList<Off> allOffs = Off.getAllOffs();
        Date timeNow = new Date();
        for (Off off : allOffs) {
            if (off.getEndTime().before(timeNow) && off.getOffStatus().equals(ProductAndOffStatus.ACCEPTED)) {
                off.removeOff();
                off.getSeller().removeOffFromThisSeller(off);
                for (Product product : off.getOnSaleProducts()) {
                    if (product.getOffs().contains(off))
                        product.removeOff(off);
                }
            } else if (off.getBeginTime().before(timeNow) && off.getOffStatus().equals(ProductAndOffStatus.ACCEPTED)) {
                for (Product product : off.getOnSaleProducts()) {
                    boolean canAdd = true;
                    for (Off productOff : product.getOffs()) {
                        if (productOff.getSeller().equals(off.getSeller())) {
                            canAdd = false;
                            break;
                        }
                    }
                    if (canAdd)
                        product.addOff(off);
                }
            }
        }
    }

    public void editOff(User user, String offId, ArrayList<String> products, Date beginTime, Date endTime, int percent) throws Exception {
        Off off = getOffById(offId);
        if (off == null) {
            throw new Exception("Off doesn't exist");
        }
        if (!off.getSeller().equals((Seller) user)) {
            throw new Exception("Seller does'nt have this off");
        }
        ArrayList<Product> newProducts = (ArrayList<Product>) products.stream()
                .map(ProductController::getProductById)
                .collect(Collectors.toList());
        off.setOffStatus(ProductAndOffStatus.EDITING);
        (new OffRequest("OffRequest" + (Request.getNumberOfRequestCreated() + 1), off, true)).setOff(beginTime, endTime, percent, newProducts);
    }

    public Off getOffById(String id) {
        for (Off off : Off.getAllOffs()) {
            if (off.getOffId().equalsIgnoreCase(id)) {
                return off;
            }
        }
        return null;
    }

    public int showBalanceOfSeller(User user) {
        return user.getCredit();
    }

    public ArrayList<String> getOffProducts(String offId) throws Exception {
        Off off = getOffById(offId);
        if (off == null)
            throw new Exception("there isn'n any off with this Id!");
        ArrayList<String> productId = new ArrayList<>();
        for (Product product : off.getOnSaleProducts())
            productId.add(product.getProductId());
        return productId;
    }

}
