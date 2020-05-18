import Controller.LoginController;
import Controller.ManagerController;
import Controller.SellerController;
import Model.Card;
import Model.Request;
import Model.Seller;
import Model.SellerRequest;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class RequestTest {
    ManagerController managerController = ManagerController.getInstance();
    LoginController loginController = LoginController.getInstance();
    SellerController sellerController = SellerController.getInstance();

    @Test
    public void showRequests() throws Exception {
        FirstTest.registerManager();
        FirstTest.loginManager();
        FirstTest.registerSeller();
        Assert.assertEquals(managerController.showRequestDetails("SellerRequest1"), "request to register a seller with username: " + "sell" + ", firstName: " + "buyer" + ", lastName: " + "all" + "phoneNumber: " + "09180000");
        Assert.assertEquals(managerController.showAllRequests().get(0), "RequestId: " + "SellerRequest1" + ", " + "request to register a seller with username: " + "sell" + ", firstName: " + "buyer" + ", lastName: " + "all" + "phoneNumber: " + "09180000");
        try {
            managerController.showRequestDetails("aaa");
        } catch (Exception e) {
            Assert.assertEquals("invalid requestId", e.getMessage());
        }
    }

    public void acceptRequestAndLoginSeller() throws Exception {
        FirstTest.registerManager();
        FirstTest.loginManager();
        FirstTest.registerSeller();
        managerController.acceptRequest("SellerRequest1");
        FirstTest.firstSeller = (Seller) loginController.login("sell", "1234abcd", new Card());
    }

    public void addCategory() throws Exception{
        acceptRequestAndLoginSeller();
        managerController.addCategory("myCat", new ArrayList<>(Arrays.asList("hard", "ram", "screen")));
    }

    @Test
    public void addProductRequest() throws Exception{
        addCategory();
        Assert.assertEquals("name : " + "myCat" + ", specialProperties : " + "[hard, ram, screen]", managerController.showAllCategories().get(0));
        HashMap<String, String> specialInformation = new HashMap<>();
        specialInformation.put("hard","20");
        specialInformation.put("ram","40");
        specialInformation.put("screen", "1980");
        sellerController.addProduct(new String[]{"laptop","asus","2000","myCat","this is a good laptop"},FirstTest.firstSeller,specialInformation);
        try {
            Assert.assertEquals("google", sellerController.showCompanyInformation(FirstTest.firstSeller));
            Assert.assertEquals("name=" + "laptop" + ", price=" + "2000" + ", rate=" + "0.0" + ", status=" + "CREATING", sellerController.showProductsOfThisSeller(FirstTest.firstSeller).get(0));
            //Assert.assertEquals(2, Request.getNumberOfRequestCreated());
            Assert.assertEquals(managerController.showRequestDetails("ProductRequest3"),"request to create or edit product with id: " + "Product1" + ", isEditing: " + false + ", seller: " + "sell" + ", newPrice: 2000");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Date createDiscountCodeTest() throws Exception {
        acceptRequestAndLoginSeller();
        FirstTest.registerAndLoginCustomer();
        Date date = new Date();
        HashMap<String , Integer> discountForUsers = new HashMap<>();
        discountForUsers.put("buyer",2);
        managerController.createDiscountCode("myDiscount", date, new Date(date.getTime() + 60 * 60 * 1000), 20, 10000, discountForUsers);
        Assert.assertEquals("code:" + "myDiscount" + ", beginTime:" + date + ", endTime:" + new Date(date.getTime() + 60 * 60* 1000) + ", percent:" + "20", managerController.showAllDiscountCodes().get(0));
        return date;
    }

    @Test
    public void  editAndRemoveDiscount() throws Exception {
        Date date = createDiscountCodeTest();
        Assert.assertEquals("code:" + "myDiscount" + ", beginTime:" + date + ", endTime:" + new Date(date.getTime() + 60 * 60* 1000) + ", percent:" + "20", managerController.showDiscount("myDiscount"));

    }






}
