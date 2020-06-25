package Controller;

import Model.Seller;
import Model.User;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
@RunWith(JMockit.class)
public class SellerControllerTest {
    SellerController sellerController = SellerController.getInstance();
    @Injectable
    Seller seller;
    @Injectable
    User user;
    @Test
    public void getInstance() {
        Assert.assertNotNull(SellerController.getInstance());
    }

    @Test
    public void showCompanyInformation() {
        try {
            sellerController.showCompanyInformation(user);
        } catch (Exception e) {
            assertEquals("You aren't seller" ,e.getMessage() );
        }
    }
    @Test
    public void showCompanyInformation2() {
        new MockUp<Object>(){
            //@Mock
        };
        new Expectations(){
            {
                seller.getCompanyName();result ="company";
            }
        };
        try {
            assertEquals("company" , sellerController.showCompanyInformation(seller));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addSellerToProduct() {
        try {
            sellerController.addSellerToProduct(user , "product" , 500);
        } catch (Exception e) {
            assertEquals("You can't be a seller" , e.getMessage());
        }
    }

    @Test
    public void showSalesHistory() {
        new MockUp<Object>(){
            //@Mock
        };
        new Expectations(){
            {

            }
        };
    }

    @Test
    public void showBuyersOfThisProduct() {
        new MockUp<Object>(){
            //@Mock
        };
        new Expectations(){
            {

            }
        };
    }

    @Test
    public void removeProductFromUser() {
        new MockUp<Object>(){
            //@Mock
        };
        new Expectations(){
            {

            }
        };
    }

    @Test
    public void showProductsOfThisSeller() {
        new MockUp<Object>(){
            //@Mock
        };
        new Expectations(){
            {

            }
        };
    }

    @Test
    public void getCategoryFeatures() {
        new MockUp<Object>(){
            //@Mock
        };
        new Expectations(){
            {

            }
        };
    }

    @Test
    public void addProduct() {
        new MockUp<Object>(){
            //@Mock
        };
        new Expectations(){
            {

            }
        };
    }

    @Test
    public void editProduct() {
        new MockUp<Object>(){
            //@Mock
        };
        new Expectations(){
            {

            }
        };
    }

    @Test
    public void removeProduct() {
        new MockUp<Object>(){
            //@Mock
        };
        new Expectations(){
            {

            }
        };
    }

    @Test
    public void showAllOffs() {
        new MockUp<Object>(){
            //@Mock
        };
        new Expectations(){
            {

            }
        };
    }

    @Test
    public void showOff() {
        new MockUp<Object>(){
            //@Mock
        };
        new Expectations(){
            {

            }
        };
    }

    @Test
    public void addOff() {
        new MockUp<Object>(){
            //@Mock
        };
        new Expectations(){
            {

            }
        };
    }

    @Test
    public void checkTimeOfOffs() {
        new MockUp<Object>(){
            //@Mock
        };
        new Expectations(){
            {

            }
        };
    }

    @Test
    public void editOff() {
        new MockUp<Object>(){
            //@Mock
        };
        new Expectations(){
            {

            }
        };
    }

    @Test
    public void getOffById() {
        new MockUp<Object>(){
            //@Mock
        };
        new Expectations(){
            {

            }
        };
    }

    @Test
    public void showBalanceOfSeller() {
        new MockUp<Object>(){
            //@Mock
        };
        new Expectations(){
            {

            }
        };
    }

    @Test
    public void getOffProducts() {
        new MockUp<Object>(){
            //@Mock
        };
        new Expectations(){
            {

            }
        };
    }
}