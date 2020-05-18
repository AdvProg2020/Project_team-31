import Controller.LoginController;
import Controller.ManagerController;
import Model.Card;
import Model.User;
import org.junit.Assert;
import org.junit.Test;

public class FirstTest {
    LoginController loginController = LoginController.getInstance();
    ManagerController managerController = ManagerController.getInstance();

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
    }

    @Test
    public void registerManager() {
        String[] personalInformation = new String[6];
        personalInformation[0] = "ali";
        personalInformation[1] = "kaka";
        personalInformation[2] = "email";
        personalInformation[3] = "09180000";
        personalInformation[4] = "1234abcd";
        Assert.assertTrue(loginController.IsUsernameFree("ali"));
    }
}
