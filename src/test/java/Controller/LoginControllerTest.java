package Controller;

import Model.*;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static org.junit.Assert.*;
@RunWith(JMockit.class)
public class LoginControllerTest {
    LoginController loginController = LoginController.getInstance();
    @Injectable
    Manager manager;
    @Injectable
    Card card;
    @Injectable
    User user;
    @Injectable
    ProductInCard productInCard;
    @Injectable
    Product product;
    @Test
    public void getInstance() {
        Assert.assertNotNull(LoginController.getInstance());
    }

    @Test
    public void createTempUser() {
        Assert.assertNotNull(loginController.createTempUser());
    }

    @Test
    public void register() {
        new Expectations(){
            {

            }
        };
        String [] information = new String[5];
        information[0] = "";
        information[1] =  "";
        information[2] = "";
        information[3] = "";
        information[4] = "";
        try {
            loginController.register("hamed" , "customer" , information);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void register2() {
        new Expectations(){
            {

            }
        };
        String [] information = new String[5];
        information[0] = "";
        information[1] =  "";
        information[2] = "";
        information[3] = "";
        information[4] = "";
        try {
            loginController.register("hamed" , "seller" , information);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void register3() {
        new Expectations(){
            {

            }
        };
        String [] information = new String[5];
        information[0] = "";
        information[1] =  "";
        information[2] = "";
        information[3] = "";
        information[4] = "";
        try {
            loginController.register("hamed" , "seller" , information);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void register4() {
        new Expectations(){
            {

            }
        };
        String [] information = new String[5];
        information[0] = "";
        information[1] =  "";
        information[2] = "";
        information[3] = "";
        information[4] = "";
        try {
            loginController.register("hamed" , "noOne" , information);
        } catch (Exception e) {
            Assert.assertEquals("role is invalid" , e.getMessage());
        }
    }

    @Test
    public void isThereAnyManager() {
        Assert.assertFalse(loginController.isThereAnyManager());
    }

    @Test
    public void isUsernameFree() {
        Assert.assertTrue(loginController.isUsernameFree("mamad"));
    }

    @Test
    public void login() {
        new MockUp<LoginController>() {
            @Mock
            public User getUserByUsername(String userName) {
                return null;
            }
        };
        try {
            loginController.login("hamed", "1234", card);
        } catch (Exception e) {
            Assert.assertEquals("There is not user with this username", e.getMessage());
        }
    }
    @Test
    public void login2() {
        new MockUp<LoginController>() {
            @Mock
            public User getUserByUsername(String userName) {
                return user;
            }
        };
        new Expectations(){
            {
                user.getPassword(); result = "234";
            }
        };
        try {
            loginController.login("hamed", "1234", card);
        } catch (Exception e) {
            Assert.assertEquals("Password is incorrect", e.getMessage());
        }
    }
    @Test
    public void login3() {
        new MockUp<LoginController>() {
            @Mock
            public User getUserByUsername(String userName) {
                return user;
            }
        };
        new MockUp<Card>(){
            @Mock
            public HashMap<Product , ProductInCard> getProductsInThisCard(){
                HashMap<Product , ProductInCard> allProduct = new HashMap<>();
                allProduct.put(product , productInCard);
                return allProduct;
            }
        };
        new Expectations(){
            {
                user.getPassword(); result = "1234";
                user.setCard(card);
            }
        };
        try {
            Assert.assertNotNull(loginController.login("hamed", "1234", card));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void login4() {
        new MockUp<LoginController>() {
            @Mock
            public User getUserByUsername(String userName) {
                return user;
            }
        };
        new MockUp<Card>(){
            @Mock
            public HashMap<Product , ProductInCard> getProductsInThisCard(){
                HashMap<Product , ProductInCard> allProduct = new HashMap<>();
                return allProduct;
            }
        };
        new MockUp<User>(){
            @Mock
            public void setCard(Card card){
                card = new Card();
            }
        };
        new Expectations(){
            {
                user.getPassword(); result = "1234";
                user.getCard(); result = null;
                user.setCard(null);
            }
        };
        try {
            Assert.assertNotNull(loginController.login("hamed", "1234", card));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void showPersonalInformation() {
        new MockUp<User>(){
            @Mock
            public String[] getPersonalInformation(){
                String[] personalInformation = new String[7];
                return  personalInformation;
            }
        };
        new Expectations(){
            {
                user.getPersonalInformation();
            }
        };
        loginController.showPersonalInformation(user);
    }

    @Test
    public void editPersonalInformation() {
        new MockUp<User>(){
            @Mock
            public void setNewInformation(String name, String lastName, String emailAddress, String  phoneNumber, String password){

            }
        };
        new Expectations(){
            {
                user.setNewInformation("","","","","");
            }
        };
        String[] personalInformation = new String[7];
        personalInformation[0] = "";
        personalInformation[1] = "";
        personalInformation[2] = "";
        personalInformation[3] = "";
        personalInformation[4] = "";
        personalInformation[5] = "";
        personalInformation[6] = "";
        loginController.editPersonalInformation(user , personalInformation);

    }

    @Test
    public void getUserByUsername() {
    }
}