package Controller;

import Model.Seller;
import Model.User;
import mockit.Injectable;
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
    public void addSellerToProduct() {
    }

    @Test
    public void showSalesHistory() {
    }

    @Test
    public void showBuyersOfThisProduct() {
    }

    @Test
    public void removeProductFromUser() {
    }

    @Test
    public void showProductsOfThisSeller() {
    }

    @Test
    public void getCategoryFeatures() {
    }

    @Test
    public void addProduct() {
    }

    @Test
    public void editProduct() {
    }

    @Test
    public void removeProduct() {
    }

    @Test
    public void showAllOffs() {
    }

    @Test
    public void showOff() {
    }

    @Test
    public void addOff() {
    }

    @Test
    public void checkTimeOfOffs() {
    }

    @Test
    public void editOff() {
    }

    @Test
    public void getOffById() {
    }

    @Test
    public void showBalanceOfSeller() {
    }

    @Test
    public void getOffProducts() {
    }
}