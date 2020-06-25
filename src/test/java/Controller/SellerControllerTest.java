package Controller;

import Model.Product;
import Model.Request;
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

import java.util.HashMap;

import static org.junit.Assert.*;
@RunWith(JMockit.class)
public class SellerControllerTest {
    SellerController sellerController = SellerController.getInstance();
    @Injectable
    Seller seller;
    @Injectable
    User user;
    @Injectable
    Product product;
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
    public void addSellerToProduct2() {
        new MockUp<ProductController>(){
            @Mock
            public Product getProductById(String Id){
                return null;
            }
        };
        new Expectations(){
            {
                ProductController.getProductById("product");
            }
        };
        try {
            sellerController.addSellerToProduct(seller , "product" , 500);
        } catch (Exception e) {
            assertEquals("productId is invalid" , e.getMessage());
        }
    }
    @Test
    public void addSellerToProduct3() {
        new MockUp<ProductController>(){
            @Mock
            public Product getProductById(String Id){
                return product;
            }
        };
        HashMap<Seller  , Integer> sellers = new HashMap<>();
        sellers.put(seller , 10);
        new Expectations(){
            {
                ProductController.getProductById("product");
                product.getSellersOfThisProduct();result = sellers;
            }
        };
        try {
            sellerController.addSellerToProduct(seller , "product" , 500);
        } catch (Exception e) {
            assertEquals("seller has product" , e.getMessage());
        }
    }
    @Test
    public void addSellerToProduct4() {
        new MockUp<ProductController>(){
            @Mock
            public Product getProductById(String Id){
                return product;
            }
        };
        new MockUp<Request>(){
            @Mock
            public int getNumberOfRequestCreated(){
                return 10;
            }
        };
        HashMap<Seller  , Integer> sellers = new HashMap<>();
        new Expectations(){
            {
                ProductController.getProductById("product");
                product.getSellersOfThisProduct();result = sellers;
                Request.getNumberOfRequestCreated();
            }
        };
        try {
            sellerController.addSellerToProduct(seller , "product" , 500);
        } catch (Exception e) {
            e.printStackTrace();
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