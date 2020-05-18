import Controller.LoginController;
import Controller.ManagerController;
import Controller.SellerController;
import Model.*;
import org.junit.Assert;
import org.junit.Test;

public class FirstTest {
    LoginController loginController = LoginController.getInstance();
    ManagerController managerController = ManagerController.getInstance();
    SellerController sellerController = SellerController.getInstance();
    Manager mainManager;
    Customer firstCustomer;
    Seller firstSeller;


    public void registerManager() {
        String[] personalInformation = new String[6];
        personalInformation[0] = "ali";
        personalInformation[1] = "kaka";
        personalInformation[2] = "email";
        personalInformation[3] = "09180000";
        personalInformation[4] = "1234abcd";
        try {
            loginController.register("modir", "manager", personalInformation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void loginManager() throws Exception{
        mainManager = (Manager) loginController.login("modir", "1234abcd", new Card());
    }
    public void registerAndLoginCustomer() {
        String[] personalInformation = new String[6];
        personalInformation[0] = "buyer";
        personalInformation[1] = "all";
        personalInformation[2] = "email";
        personalInformation[3] = "09180000";
        personalInformation[4] = "1234abcd";
        try {
            loginController.register("buyer", "customer", personalInformation);
            firstCustomer = (Customer) loginController.login("buyer", "1234abcd", new Card());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerAndLoginSeller() {
        String[] personalInformation = new String[6];
        personalInformation[0] = "buyer";
        personalInformation[1] = "all";
        personalInformation[2] = "email";
        personalInformation[3] = "09180000";
        personalInformation[4] = "1234abcd";
        personalInformation[5] = "google";
        try {
            loginController.register("sell", "seller", personalInformation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void RegisterTest() throws Exception {
        String[] personalInformation = new String[6];
        personalInformation[0] = "ali";
        personalInformation[1] = "kaka";
        personalInformation[2] = "email";
        personalInformation[3] = "09180000";
        personalInformation[4] = "1234abcd";
        Assert.assertTrue(loginController.isUsernameFree("ali"));
        try {
            loginController.register("ali", "mmm", personalInformation);
        } catch (Exception e) {
            Assert.assertEquals("role is invalid", e.getMessage());
        }
        loginController.register("ali","customer", personalInformation);
        User user = loginController.login("ali","1234abcd", new Card());
        for (int i = 0; i < 2 ; i++) {
            Assert.assertEquals(personalInformation[i], loginController.showPersonalInformation(user)[i]);
        }
        Assert.assertEquals("ali", user.getUsername());
        for (int i = 2; i < 5; i++) {
            Assert.assertEquals(personalInformation[i], loginController.showPersonalInformation(user)[i+1]);
        }
        firstCustomer = (Customer) user;
    }

    @Test
    public void registerManagerTest() {
        registerManager();
        Assert.assertTrue(loginController.isThereAnyManager());
        Assert.assertFalse(loginController.isUsernameFree("modir"));
    }

    @Test
    public void loginManagerTest() {
        registerManager();
        User user;
        try {
            user = loginController.login("modir", "1234", new Card());
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "Password is incorrect");
        }
        try {
            user = loginController.login("hi", "1234", new Card());
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "There is not user with this username");
        }
        try {
            mainManager = (Manager) loginController.login("modir", "1234abcd", new Card());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void editPersonalInformation() throws Exception {
        registerManager();
        loginManager();
        String[] personalInformation = new String[6];
        personalInformation[0] = "Hi";
        personalInformation[1] = "me";
        personalInformation[2] = "email";
        personalInformation[3] = "09180000";
        personalInformation[4] = "1234abcd";
        loginController.editPersonalInformation(mainManager, personalInformation);
        Assert.assertEquals("Hi", loginController.showPersonalInformation(mainManager)[0]);
        Assert.assertEquals("me", loginController.showPersonalInformation(mainManager)[1]);
    }
}
