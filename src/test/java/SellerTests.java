import Controller.LoginController;
import Controller.ManagerController;
import Controller.SellerController;
import Model.*;
import org.junit.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class SellerTests {
    SellerController sellerController = SellerController.getInstance();
    ManagerController managerController = ManagerController.getInstance();
    LoginController loginController = LoginController.getInstance();
    Manager modir;    // username: modir
    Seller seller1;
    Seller seller2;

    public void createManager() throws Exception{
        String[] personalInformation = new String[6];
        personalInformation[0] = "ali";
        personalInformation[1] = "kaka";
        personalInformation[2] = "email";
        personalInformation[3] = "09180000";
        personalInformation[4] = "1234abcd";
        loginController.register("modir", "manager", personalInformation);
        modir = (Manager) loginController.login("modir" , "1234abcd", new Card());
    }

    public void createTwoSeller() throws Exception{
        String[] personalInformation = new String[6];
        personalInformation[0] = "ali";
        personalInformation[1] = "kaka";
        personalInformation[2] = "email";
        personalInformation[3] = "09180000";
        personalInformation[4] = "1234abcd";
        try {
            System.out.println(seller1.getUsername());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(Request.getNumberOfRequestCreated());
        System.out.println(Request.getAllRequest());

        loginController.register("seller1", "seller", personalInformation);
        loginController.register("seller2", "seller", personalInformation);
        try {
            managerController.acceptRequest("SellerRequest1");
            managerController.acceptRequest("SellerRequest2");
        } catch (Exception e) {
            e.getMessage();
        }

        seller1 = (Seller) loginController.login("seller1", "1234abcd", new Card());
        seller2 = (Seller) loginController.login("seller2", "1234abcd", new Card());
    }

    public void createCategory() throws Exception {
        createTwoSeller();
        new Category("Books",new ArrayList<>(Arrays.asList("age","type")));
        HashMap<String, String> specialInformation = new HashMap<>();
        specialInformation.put("age","50");
        specialInformation.put("type","soft");
        sellerController.addProduct(new String[]{"math","google","20","Books", "book with math name","5"},seller1,specialInformation);
        specialInformation.replace("age", "60");
        specialInformation.replace("type","hard");
        sellerController.addProduct(new String[]{"astronomy","microsoft","30","Books","book with name astronomy","4"},seller2,specialInformation);
        try {
            managerController.acceptRequest("ProductRequest3");
            managerController.acceptRequest("ProductRequest4");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("first book" + Product.getNumberOfProductCreated() + "request" + Request.getNumberOfRequestCreated());
        sellerController.addSellerToProduct(seller1,"Product2",25);
        try {
            managerController.acceptRequest("SellerOfProductRequest5");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        sellerController.removeProductFromUser(seller1,"Product2");
    }
    @Test
    public void addSellerToProduct() throws Exception{
        createCategory();
        try {
            sellerController.addSellerToProduct(modir,"123",40);
        } catch (Exception e) {
            Assert.assertEquals("You can't be a seller", e.getMessage());
        }
        try {
            sellerController.addSellerToProduct(seller1, "Product0", 40);
        } catch (Exception e) {
            Assert.assertEquals("productId is invalid", e.getMessage());
        }
        try {
            sellerController.addSellerToProduct(seller1,"Product1", 30);
        } catch (Exception e) {
            Assert.assertEquals("seller has product", e.getMessage());
        }
        try {
            sellerController.removeProductFromUser(modir,"123");
        } catch (Exception e) {
            Assert.assertEquals("You aren't seller", e.getMessage());
        }
        try {
            sellerController.removeProductFromUser(seller1,"Product0");
        } catch (Exception e) {
            Assert.assertEquals("There is not this product", e.getMessage());
        }
        try {
            sellerController.removeProductFromUser(seller1,"Product2");
        } catch (Exception e) {
            Assert.assertEquals("Seller does'nt have this product", e.getMessage());
        }
    }
    @Test
    public void addOffAndEdit() throws Exception{
        createCategory();
        ArrayList<String> offCustomers = new ArrayList<>();
        Date date = new Date();
        offCustomers.add("Product1");
        sellerController.addOff(seller1,offCustomers, date, new Date(date.getTime() + 60 * 60 * 1000), 10);
        System.out.println("addOffAndEdit");
    }
}
