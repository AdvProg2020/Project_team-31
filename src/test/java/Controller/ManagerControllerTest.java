package Controller;

import Model.User;
import mockit.*;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;
@RunWith(JMockit.class)
public class ManagerControllerTest {
    ManagerController managerController = ManagerController.getInstance();
    @Injectable
    User user;
    @Test
    public void getInstance() {
        Assert.assertNotNull(ManagerController.getInstance());
    }

    @Test
    public void showUsers() {
        new MockUp<User>(){
              @Mock
            public ArrayList<User> getAllUsers(){
                  ArrayList<User> allUsers = new ArrayList<>();
                  allUsers.add(user);
                  return allUsers;
              }
        };
        new MockUp<User>(){
            @Mock
            public String[] getPersonalInformation(){
                String[] information = new String[6];
                information[0] = "hamed";
                information[1] = "rashidpour";
                return information;
            }
        };
        new Expectations(){
            {
                User.getAllUsers();
                user.getPersonalInformation();
                user.getUsername(); result = "pink";
            }

        };
        ArrayList<String> sample = new ArrayList<>();
        sample.add("userName: " + "pink" + ", firstName:" + "hamed" + ", lastName:" + "rashidpour");

        Assert.assertEquals(sample ,managerController.showUsers());
    }

    @Test
    public void deleteUser() {
        new MockUp<Object>(){
            //  @Mock
        };
        new Expectations(){
            {

            }
        };


    }

    @Test
    public void createDiscountCode() {
        new MockUp<Object>(){
            //  @Mock
        };
        new Expectations(){
            {

            }
        };


    }

    @Test
    public void showAllDiscountCodes() {
        new MockUp<Object>(){
            //  @Mock
        };
        new Expectations(){
            {

            }
        };


    }

    @Test
    public void showDiscount() {
        new MockUp<Object>(){
            //  @Mock
        };
        new Expectations(){
            {

            }
        };


    }

    @Test
    public void getDiscountById() {
        new MockUp<Object>(){
            //  @Mock
        };
        new Expectations(){
            {

            }
        };


    }

    @Test
    public void editDiscountCode() {
        new MockUp<Object>(){
            //  @Mock
        };
        new Expectations(){
            {

            }
        };


    }

    @Test
    public void removeDiscountCode() {
        new MockUp<Object>(){
            //  @Mock
        };
        new Expectations(){
            {

            }
        };


    }

    @Test
    public void showAllRequests() {
        new MockUp<Object>(){
            //  @Mock
        };
        new Expectations(){
            {

            }
        };


    }

    @Test
    public void showRequestDetails() {
        new MockUp<Object>(){
            //  @Mock
        };
        new Expectations(){
            {

            }
        };


    }

    @Test
    public void acceptRequest() {
        new MockUp<Object>(){
            //  @Mock
        };
        new Expectations(){
            {

            }
        };


    }

    @Test
    public void declineRequest() {
        new MockUp<Object>(){
            //  @Mock
        };
        new Expectations(){
            {

            }
        };


    }

    @Test
    public void showAllCategories() {
        new MockUp<Object>(){
            //  @Mock
        };
        new Expectations(){
            {

            }
        };


    }

    @Test
    public void addCategory() {
        new MockUp<Object>(){
            //  @Mock
        };
        new Expectations(){
            {

            }
        };


    }

    @Test
    public void removeCategory() {
        new MockUp<Object>(){
            //  @Mock
        };
        new Expectations(){
            {

            }
        };


    }

    @Test
    public void editCategory() {
        new MockUp<Object>(){
            //  @Mock
        };
        new Expectations(){
            {

            }
        };


    }

    @Test
    public void changeFeatureOfCategory() {
        new MockUp<Object>(){
            //  @Mock
        };
        new Expectations(){
            {

            }
        };


    }

    @Test
    public void getCategoryByName() {
        new MockUp<Object>(){
            //  @Mock
        };
        new Expectations(){
            {

            }
        };


    }

    @Test
    public void getCategoryFeaturesOfAProduct() {
        new MockUp<Object>(){
            //  @Mock
        };
        new Expectations(){
            {

            }
        };


    }
}