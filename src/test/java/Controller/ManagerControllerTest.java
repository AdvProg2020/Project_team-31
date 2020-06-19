package Controller;

import Model.Customer;
import Model.DiscountCode;
import Model.Manager;
import Model.User;
import mockit.*;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.*;
@RunWith(JMockit.class)
public class ManagerControllerTest {
    ManagerController managerController = ManagerController.getInstance();
    @Injectable
    User user;
    @Injectable
    Manager manager;
    @Injectable
    Customer customer;
    @Injectable
    DiscountCode discountCode;
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
        new MockUp<LoginController>(){
              @Mock
            public User getUserByUsername(String userName){
                  return manager;
              }
        };
        new Expectations(){
            {
                LoginController.getUserByUsername("hamed");
                manager.deleteUser();
                manager.deleteManager();
            }
        };
        managerController.deleteUser("hamed");
    }

    @Test
    public void createDiscountCodeAndChangeNameTOCustomer() {
        new MockUp<Object>(){
            //  @Mock
        };
        new Expectations(){
            {
                LoginController.getUserByUsername("hamed");
            }
        };
        HashMap<String , Integer> sample = new HashMap<>();
        sample.put("hamed" , 5);
        try {
            managerController.createDiscountCode("firstDiscountCode" , new Date() , new Date() , 40 , 50 , sample);
        } catch (Exception e) {
            assertEquals("User with userName "+"\"hamed\"" +" doesn't exist" , e.getMessage());
        }
    }
    @Test
    public void createDiscountCodeAndChangeNameTOCustomer2() {
        new MockUp<LoginController>(){
            @Mock
            public User getUserByUsername(String userName){
                return manager;
            }
        };
        new Expectations(){
            {
                LoginController.getUserByUsername("hamed");
            }
        };
        HashMap<String , Integer> sample = new HashMap<>();
        sample.put("hamed" , 5);
        try {
            managerController.createDiscountCode("firstDiscountCode" , new Date() , new Date() , 40 , 50 , sample);
        } catch (Exception e) {
            assertEquals("User with userName \"" + "hamed" + "\" isn't customer", e.getMessage());
        }
    }    @Test
    public void createDiscountCodeAndChangeNameTOCustomer3() {
        new MockUp<LoginController>(){
            @Mock
            public User getUserByUsername(String userName){
                return customer;
            }
        };
        new Expectations(){
            {
                LoginController.getUserByUsername("hamed");
                DiscountCode discountCode = new DiscountCode("firstDiscountCode");
            }
        };
        HashMap<String , Integer> sample = new HashMap<>();
        sample.put("hamed" , 5);
        try {
            managerController.createDiscountCode("firstDiscountCode" , new Date() , new Date() , 40 , 50 , sample);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void showAllDiscountCodes() {
        new MockUp<DiscountCode>(){
              @Mock
            public ArrayList<DiscountCode> getAllDiscountCodes(){
                  ArrayList<DiscountCode> allDiscountCodes = new ArrayList<>();
                  allDiscountCodes.add(discountCode);
                  return allDiscountCodes;
              }
        };
        new Expectations(){
            {
                DiscountCode.getAllDiscountCodes();
                discountCode.getDiscountCode(); result = "fistOne";
                discountCode.getBeginTime(); result = new Date(2000 ,02 , 20);
                discountCode.getEndTime(); result = new Date(2020 , 02 , 20);
                discountCode.getDiscountPercent(); result = 40;

            }
        };
        ArrayList<String> sample = new ArrayList<>();
        sample.add("code:" + "fistOne" + ", beginTime:" +  new Date(2000 ,02 , 20)+ ", endTime:" + new Date(2020 , 02 , 20) + ", percent:" + 40);
        Assert.assertEquals(sample ,managerController.showAllDiscountCodes());
    }

    @Test
    public void showDiscount() throws Exception {
        new MockUp<ManagerController>(){
             @Mock
            public DiscountCode getDiscountById(String discountId){
                 return discountCode;
             }
        };
        new Expectations(){
            {
                managerController.getDiscountById("firstOne");
                discountCode.getDiscountCode(); result = "50";
                discountCode.getBeginTime(); result = new Date(2000 ,02 , 20);
                discountCode.getEndTime(); result = new Date(2020 , 02 , 20);
                discountCode.getDiscountPercent(); result = 40;
            }
        };
        Assert.assertEquals("code:" + "50" + ", beginTime:" +new Date(2000 ,02 , 20) + ", endTime:" + new Date(2020 , 02 , 20) + ", percent:" + 40 , managerController.showDiscount("firstOne"));
    }

    @Test
    public void getDiscountById() {
        new MockUp<DiscountCode>(){
            @Mock
            public ArrayList<DiscountCode> getAllDiscountCodes(){
                ArrayList<DiscountCode> allDiscountCodes = new ArrayList<>();
                allDiscountCodes.add(discountCode);
                return allDiscountCodes;
            }
        };
        new Expectations(){
            {
                DiscountCode.getAllDiscountCodes();
                discountCode.getDiscountCode();result = "firstOne";
            }
        };
        try {
            managerController.getDiscountById("secondOne");
        } catch (Exception e) {
            Assert.assertEquals("There is no discount with this code" , e.getMessage());
        }
    }
    @Test
    public void getDiscountById2() {
        new MockUp<DiscountCode>(){
            @Mock
            public ArrayList<DiscountCode> getAllDiscountCodes(){
                ArrayList<DiscountCode> allDiscountCodes = new ArrayList<>();
                allDiscountCodes.add(discountCode);
                return allDiscountCodes;
            }
        };
        new Expectations(){
            {
                DiscountCode.getAllDiscountCodes();
                discountCode.getDiscountCode();result = "firstOne";
            }
        };
        try {
            Assert.assertEquals(discountCode ,managerController.getDiscountById("firstOne") );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void editDiscountCode() throws Exception {
        new MockUp<ManagerController>(){
            @Mock
            public DiscountCode getDiscountById(String discountId){
                return discountCode;
            }
        };
        new MockUp<ManagerController>(){
            @Mock
            public HashMap<Customer, Integer> changeNameToCustomer(HashMap<String, Integer> discountTimesForEachCustomer)
            {
                HashMap<Customer , Integer> customerIntegerHashMap = new HashMap<>();
                customerIntegerHashMap.put(customer , 5);
                return customerIntegerHashMap;
            }
        };
        HashMap<Customer , Integer> customerIntegerHashMap = new HashMap<>();
        customerIntegerHashMap.put(customer , 5);
        new Expectations(){
            {
                managerController.getDiscountById("firstONe");
                discountCode.setDiscountCode(new Date(2000 , 2 , 20) ,new Date(2020 , 2, 20) , 40 , 50 ,customerIntegerHashMap);
            }
        };
        HashMap<String , Integer> sample = new HashMap<>();
        sample.put("hamed" , 5);
        managerController.editDiscountCode("firstONe" , new Date(2000 , 2 , 20) , new Date(2020 , 2, 20) , 40 , 50 , sample);
    }

    @Test
    public void removeDiscountCode() throws Exception {
        new MockUp<ManagerController>(){
            @Mock
            public DiscountCode getDiscountById(String discountId){
                return discountCode;
            }
        };
        new MockUp<DiscountCode>(){
          @Mock
          public HashMap<Customer , Integer>   getDiscountTimesForEachCustomer(){
              HashMap<Customer , Integer> customerIntegerHashMap = new HashMap<>();
              customerIntegerHashMap.put(customer , 10);
              return customerIntegerHashMap;
          }
        };
        ArrayList<DiscountCode> sample = new ArrayList<>();
        sample.add(discountCode);
        new Expectations(){
            {
                managerController.getDiscountById("firstONe");
                discountCode.removeDiscountCode();
                discountCode.getDiscountTimesForEachCustomer();
                customer.getAllDiscountCodes();result = sample;
                customer.removeDiscountCode(discountCode);
            }
        };
        try {
            managerController.removeDiscountCode("firstONe");
        } catch (Exception e) {
            e.printStackTrace();
        }
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