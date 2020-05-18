import Controller.LoginController;
import Controller.ManagerController;
import Model.Card;
import Model.Seller;
import Model.SellerRequest;
import org.junit.Assert;
import org.junit.Test;

public class RequestTest {
    ManagerController managerController = ManagerController.getInstance();
    LoginController loginController = LoginController.getInstance();

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

    public void addProductRequest() throws Exception{
        acceptRequestAndLoginSeller();
    }


}
