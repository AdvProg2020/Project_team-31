import Controller.LoginController;
import Controller.ManagerController;
import Controller.ProductController;
import Controller.SellerController;
import Model.*;
import org.junit.Assert;
import org.junit.Test;

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
        try {
            managerController.acceptRequest("SellerRequest1");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
            Assert.assertEquals(managerController.showRequestDetails("ProductRequest3"),"request to create or edit product with id: " + "Product1" + ", isEditing: " + false + ", seller: " + "sell" + ", price: 2000");
        } catch (Exception e) {
            System.out.println("this" + e.getMessage());
        }
        managerController.acceptRequest("ProductRequest3");
        Assert.assertEquals(ProductController.getProductById("Product1").getProductStatus(), ProductAndOffStatus.ACCEPTED);
        sellerController.editProduct(FirstTest.firstSeller,"Product1",200,10,"is good",specialInformation);
        managerController.acceptRequest("ProductRequest4");
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
        Assert.assertEquals("code:" + "myDiscount" + ", beginTime:" + date + ", endTime:" + new Date(date.getTime() + 60 * 60* 1000) + ", percent:" + "20", managerController.showAllDiscountCodes().get(0));
        HashMap<String , Integer> discountForUsers = new HashMap<>();
        discountForUsers.put("buyer",1);
        managerController.editDiscountCode("myDiscount",date,new Date(date.getTime() + 60 * 60* 1000), 25, 1000,discountForUsers);
        Assert.assertEquals("code:" + "myDiscount" + ", beginTime:" + date + ", endTime:" + new Date(date.getTime() + 60 * 60* 1000) + ", percent:" + "25", managerController.showDiscount("myDiscount"));
        managerController.removeDiscountCode("myDiscount");
        try {
            managerController.showDiscount("myDiscount");
        } catch (Exception e) {
            Assert.assertEquals("There is no discount with this code", e.getMessage());
        }
    }

    @Test
    public void editAndRemoveCategory() throws Exception{
        HashMap<String, String> newFeatures = new HashMap<>();
        newFeatures.put("hard","memory");
        managerController.changeFeatureOfCategory("myCat", newFeatures);
        Assert.assertEquals(managerController.getCategoryFeaturesOfAProduct("Product1"),Arrays.asList("ram","screen","memory"));
        ArrayList<String> newAttribute = new ArrayList<>();
        newAttribute.add("screen");
        newAttribute.add("memory");
        managerController.editCategory("myCat",newAttribute);
        Assert.assertEquals(managerController.getCategoryFeaturesOfAProduct("Product1"),Arrays.asList("screen","memory"));
        managerController.removeCategory("myCat");
        Assert.assertEquals(ManagerController.getCategoryByName("myCat") , null);
    }

    @Test
    public void createProductAndDeclineIt() throws Exception{
        addCategory();
        HashMap<String, String> specialInformation = new HashMap<>();
        specialInformation.put("hard","20");
        specialInformation.put("ram","40");
        specialInformation.put("screen", "1980");
        sellerController.addProduct(new String[]{"laptop","asus","2000","myCat","this is a good laptop"},FirstTest.firstSeller,specialInformation);
        managerController.declineRequest("ProductRequest7");
        Assert.assertEquals(Product.allProducts.size(),0);
    }

}
