import Controller.LoginController;
import Controller.ManagerController;
import Model.Card;
import Model.Customer;
import Model.Manager;
import Model.User;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Currency;

public class FirstTest {
    LoginController loginController = LoginController.getInstance();
    ManagerController managerController = ManagerController.getInstance();
    Manager mainManager;
    Customer firstCustomer;

    @Test
    public void loginTest() throws Exception {
        String[] personalInformation = new String[6];
        personalInformation[0] = "ali";
        personalInformation[1] = "kaka";
        personalInformation[2] = "email";
        personalInformation[3] = "09180000";
        personalInformation[4] = "1234abcd";
        Assert.assertTrue(loginController.IsUsernameFree("ali"));
        loginController.register("ali","customer", personalInformation);
        User user = loginController.login("ali","1234abcd", new Card());
        for (int i = 0; i < 2 ; i++) {
            Assert.assertEquals(personalInformation[i], user.getPersonalInformation()[i]);
        }
        Assert.assertEquals("ali", user.getUsername());
        for (int i = 2; i < 5; i++) {
            Assert.assertEquals(personalInformation[i], user.getPersonalInformation()[i+1]);
        }
        firstCustomer = (Customer) user;
    }

    @Test
    public void registerManager() {
        String[] personalInformation = new String[6];
        personalInformation[0] = "mainManager";
        personalInformation[1] = "aaa";
        personalInformation[2] = "email";
        personalInformation[3] = "09180000";
        personalInformation[4] = "1234abcd";
        Assert.assertTrue(loginController.IsUsernameFree("modir"));
        Assert.assertFalse(loginController.isThereAnyManager());
        try {
            loginController.register("modir","mmmm", personalInformation);
        } catch (Exception e) {
            Assert.assertEquals("role is invalid", e.getMessage());
        }
        try {
            loginController.register("modir","manager", personalInformation);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void loginManager() {
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
}
