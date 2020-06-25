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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.*;
@RunWith(JMockit.class)
public class SellerControllerTest {
    SellerController sellerController = SellerController.getInstance();
    @Injectable
    Seller seller;
    @Injectable
    Seller seller2;
    @Injectable
    User user;
    @Injectable
    Product product;
    @Injectable
    Customer customer;
    @Injectable
    SellingLog sellingLog;
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
        ArrayList<SellingLog> sellingLogs = new ArrayList<>();
        sellingLogs.add(sellingLog);
        new Expectations(){
            {
                seller.getAllSellingLogs();result = sellingLogs;
                sellingLog.getLogId();result = "sellingLog";
                sellingLog.getDate();result = new Date(2020 , 2 ,20);
                sellingLog.getTotalPriceArrived();result = 50;
                sellingLog.getCustomer();result = customer;
                customer.getUsername();result = "customer";

            }
        };
        ArrayList<String> sample = new ArrayList<>();
        sample.add("Id: " + "sellingLog" + ", Date: " + new Date(2020 , 2 ,20) + ", Price: " + 50 + ", CustomerId: " + "customer");
        assertEquals(sample , sellerController.showSalesHistory(seller));
    }

    @Test
    public void showBuyersOfThisProduct() {
        try {
            sellerController.showBuyersOfThisProduct(user , "product");
        } catch (Exception e) {
            assertEquals( "You aren't a seller", e.getMessage());
        }
    }
    @Test
    public void showBuyersOfThisProduct2() {
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
            sellerController.showBuyersOfThisProduct(seller , "product");
        } catch (Exception e) {
            assertEquals( "This product doesn't Exist", e.getMessage());
        }
    }
    @Test
    public void showBuyersOfThisProduct3() {
        new MockUp<ProductController>(){
            @Mock
            public Product getProductById(String Id){
                return product;
            }
        };
        ArrayList<Product> products = new ArrayList<>();
        new Expectations(){
            {
                ProductController.getProductById("product");
                seller.getOnSaleProducts();result = products;
                ProductController.getProductById("product");
            }
        };
        try {
            sellerController.showBuyersOfThisProduct(seller , "product");
        } catch (Exception e) {
            assertEquals( "Seller does'nt have this product", e.getMessage());
        }
    }
    @Test
    public void showBuyersOfThisProduct4() {
        new MockUp<ProductController>(){
            @Mock
            public Product getProductById(String Id){
                return product;
            }
        };
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        ArrayList<SellingLog> sellingLogs = new ArrayList<>();
        sellingLogs.add(sellingLog);
        new Expectations(){
            {
                ProductController.getProductById("product");
                seller.getOnSaleProducts();result = products;
                ProductController.getProductById("product");
                seller.getAllSellingLogs();result = sellingLogs;
                sellingLog.getBuyingProducts();result = product;
                product.getProductId();result = "product";
                sellingLog.getCustomer();result = customer;
                customer.getUsername();result = "customer";
            }
        };
        ArrayList<String> sample = new ArrayList<>();
        sample.add("customer");
        try {
            assertEquals(sample ,sellerController.showBuyersOfThisProduct(seller , "product") );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void removeProductFromUser() {
        try {
            sellerController.removeProductFromUser(user , "product");
        } catch (Exception e) {
            assertEquals( "You aren't seller", e.getMessage());
        }
    }
    @Test
    public void removeProductFromUser2() {
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
            sellerController.removeProductFromUser(seller , "product");
        } catch (Exception e) {
            assertEquals( "There is not this product", e.getMessage());
        }
    }
    @Test
    public void removeProductFromUser3() {
        new MockUp<ProductController>(){
            @Mock
            public Product getProductById(String Id){
                return product;
            }
        };
        ArrayList<Product> products = new ArrayList<>();
        new Expectations(){
            {
                ProductController.getProductById("product");
                seller.getOnSaleProducts();result = products;
            }
        };
        try {
            sellerController.removeProductFromUser(seller , "product");
        } catch (Exception e) {
            assertEquals( "Seller does'nt have this product", e.getMessage());
        }
    }
    @Test
    public void removeProductFromUser4() throws Exception {
        new MockUp<ProductController>(){
            @Mock
            public Product getProductById(String Id){
                return product;
            }
        };
        new MockUp<SellerController>(){
            @Mock
            public void removeProduct(String product){
            }
        };
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        HashMap<Seller , Integer> sellers = new HashMap<>();
        sellers.put(seller , 10);
        new Expectations(){
            {
                ProductController.getProductById("product");
                seller.getOnSaleProducts();result = products;
                product.getSellersOfThisProduct();result = sellers;
                sellerController.removeProduct("product");
            }
        };
        try {
            sellerController.removeProductFromUser(seller , "product");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void removeProductFromUser5() {
        new MockUp<ProductController>(){
            @Mock
            public Product getProductById(String Id){
                return product;
            }
        };
        new MockUp<SellerController>(){
            @Mock
            public void removeProduct(String product){
            }
        };
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        HashMap<Seller , Integer> sellers = new HashMap<>();
        sellers.put(seller , 10);
        sellers.put(seller2 , 20);
        new Expectations(){
            {
                ProductController.getProductById("product");
                seller.getOnSaleProducts();result = products;
                product.getSellersOfThisProduct();result = sellers;
                seller.removeProduct(product);
                product.removeSeller(seller);
            }
        };
        try {
            sellerController.removeProductFromUser(seller , "product");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void showProductsOfThisSeller() {
        new MockUp<Object>(){
            //@Mock
        };
        ArrayList<Product> onSaleProducts = new ArrayList<>();
        onSaleProducts.add(product);
        HashMap<Seller , Integer> sellers = new HashMap<>();
        sellers.put(seller , 10);
        sellers.put(seller2 , 20);
        new Expectations(){
            {
                seller.getOnSaleProducts();result = onSaleProducts;
                product.getName();result = "product";
                product.getSellersOfThisProduct();result = sellers;
                product.getSumOfCustomersRate();result = 50;
                product.getCustomersWhoRated();result = 5;
                product.getProductStatus();result =ProductAndOffStatus.ACCEPTED;
            }
        };
        ArrayList<String> products = new ArrayList<>();
        products.add("name=" + "product" + ", price=" + 10 + ", rate=" + (1.0 * 10) + ", status=" + ProductAndOffStatus.ACCEPTED);
        assertEquals(products , sellerController.showProductsOfThisSeller(seller));
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