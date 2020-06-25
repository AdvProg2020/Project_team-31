package Controller;

import Model.*;
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
public class CustomerControllerTest {

    @Injectable
    User user;
    CustomerController customerController = CustomerController.getInstance();
    @Injectable
    Product product;
    @Mocked
    Card card;
    @Mocked
    ProductInCard productInCard;
    @Mocked
    Seller seller1;
    @Injectable
    Off off;
    @Injectable
    Customer customer;
    @Injectable
    BuyingLog buyingLog;
    @Injectable
    DiscountCode discountCode;
    @Test
    public void addCredit() {
        new Expectations(){
            {
                user.getMoney(50);
            }
        };
        customerController.addCredit(user ,50);
    }

    @Test
    public void getInstance() {
        assertNotNull(CustomerController.getInstance());
    }

    @Test
    public void createCard() {
        new Expectations(){
            {
                new Card();times = 1;
            }
        };
        assertNotNull( customerController.createCard());
    }

    @Test
    public void showCard() {
        new MockUp<CustomerController>(){
            @Mock
            public int showTotalPrice(Card card){
                return 10;
            }
        };
        new MockUp<User>(){
            @Mock
            public String getUsername(){
                return "seller";
            }
        };
        HashMap<Product , ProductInCard> productsInCard = new HashMap<>();
        productsInCard.put(product , productInCard);
        HashMap<Seller , Integer> sellers = new HashMap<>();
        sellers.put(seller1 , 10);
        new Expectations(){
            {
                user.getCard();result = card;
                card.getProductsInThisCard();result = productsInCard;times = 2;
                product.getProductId();result = "id";
                product.getName();result = "product";
                product.getSellersOfThisProduct();result = sellers;
                productInCard.getSeller();result = seller1;
                productInCard.getNumber();result = 3;times = 1;
                productInCard.getSeller();result = seller1;times =1;
                seller1.getUsername();
                customerController.showTotalPrice(card);times = 1;
            }
        };
        ArrayList<String> sample = new ArrayList<>();
        sample.add(1 + "productId: " + "id" + ", name: " + "product" + ", price: " + 10 + ", numberOfProduct: " + 3 + ", seller" + "seller");
        sample.add("totalPrice: " + 10);
        assertEquals(sample , customerController.showCard(user ,card ));
    }

    @Test
    public void changeNumberOfProductInCard() {
        new MockUp<Object>(){
            // @Mock
        };
        HashMap<Product, ProductInCard> products = new HashMap<>();
        products.put(product , productInCard);
        new Expectations(){
            {
                user.getCard();result = card;
                card.getProductsInThisCard();result = products;
                ProductController.getProductById("product");result = null;
            }
        };
        try {
            customerController.changeNumberOfProductInCard(user , card , "product" , 2);
        } catch (Exception e) {
            assertEquals( "There is not any product with this id", e.getMessage());
        }
    }
    @Test
    public void changeNumberOfProductInCard2() {
        new MockUp<ProductController>(){
             @Mock
            public Product getProductById(String name){
                 return product;
             }
        };
        HashMap<Product, ProductInCard> products = new HashMap<>();
        products.put(product , productInCard);
        new Expectations(){
            {
                user.getCard();result = card;
                card.getProductsInThisCard();result = products;
                product.getAvailable();result = 8;
                productInCard.getNumber();result = 5;times = 2;
                productInCard.changeNumberOfProduct(2);
                productInCard.getNumber();result = 0;
                card.removeProductFromCard(product);
            }
        };
        try {
            customerController.changeNumberOfProductInCard(user , card , "product" , 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addProductToCard(){
        new MockUp<Card>(){
            @Mock
            HashMap<Product , ProductInCard> getProductsInThisCard(){
                HashMap<Product , ProductInCard> hamed = new HashMap<>();
                hamed.put(product , productInCard);
                return hamed;
            }
        };
        new Expectations(){
            {
                user.getCard(); result = card;

                product.getProductStatus(); result = ProductAndOffStatus.ACCEPTED; times = 2;

            }
        };

        try {
            customerController.addProductToCard(user , card , product , "good");
        } catch (Exception e) {
            Assert.assertEquals("You have add this product to card before!" , e.getMessage());
        }
    }

    @Test
    public void addProductToCard2(){
        new MockUp<Card>(){
            @Mock
            HashMap<Product , ProductInCard> getProductsInThisCard(){
                HashMap<Product , ProductInCard> hamed = new HashMap<>();
                hamed.put(null , productInCard);
                return hamed;
            }
        };
        new MockUp<LoginController>(){
            @Mock
            User getUserByUsername(String userName){
                return seller1;
            }
        };
        new MockUp<Product>(){
            @Mock
            HashMap<Seller , Integer> getSellersOfThisProduct(){
                HashMap<Seller , Integer> hamed = new HashMap<>();
                hamed.put(seller1 ,0);
                return hamed;
            }
        };

        new Expectations(){
            {
                user.getCard(); result = card;

                product.getProductStatus(); result = ProductAndOffStatus.ACCEPTED; times = 2;
                product.getAvailable(); result = 0;
            }
        };

        try {
            customerController.addProductToCard(user , card , product , "good");
        } catch (Exception e) {
            Assert.assertEquals("Inventory of product is not enough" , e.getMessage());
        }
    }

    @Test
    public void showTotalPrice() {
        new MockUp<Card>(){
            @Mock
            HashMap<Product , ProductInCard> getProductsInThisCard(){
                HashMap<Product , ProductInCard> hamed = new HashMap<>();
                hamed.put(product , productInCard);
                return hamed;
            }
        };
        new MockUp<Product>(){
            @Mock
            ArrayList<Off> getOffs(){
                ArrayList<Off> offs = new ArrayList<>();
                offs.add(off);
                return offs;
            }
        };
        new MockUp<Product>(){
            @Mock
            HashMap<Seller , Integer> getSellersOfThisProduct(){
                HashMap<Seller , Integer> sellerIntegerHashMap = new HashMap<>();
                sellerIntegerHashMap.put(seller1 , 50);
                return sellerIntegerHashMap;
            }
        };
        new Expectations(){
            {
                productInCard.getProduct(); result = product;
                off.getSeller(); result = seller1;
                productInCard.getSeller(); result = seller1;
                off.getOffPercent(); result = 50;
                productInCard.getProduct(); result = product;
                productInCard.getSeller(); result = seller1;
                productInCard.getNumber(); result = 2;
            }
        };

        Assert.assertEquals(50 ,customerController.showTotalPrice(card));
    }

    @Test
    public void createBuyingLog() {
        new MockUp<Card>(){
            @Mock
            HashMap<Product , ProductInCard> getProductsInThisCard(){
                HashMap<Product , ProductInCard> productsInThisCard = new HashMap<>();
                productsInThisCard.put(product , productInCard);
                return productsInThisCard;
            }
        };
        new Expectations(){
            {
                user.getCard(); result = card;
                product.getAvailable(); result = 5;
                user.getCard(); result = card;
                productInCard.getNumber(); result = 6;
                product.getName(); result = "mobile";


            }
        };
        String[] information = new String[6];
        try {
            customerController.createBuyingLog(user,information);
        } catch (Exception e) {
            Assert.assertEquals("number of mobile is more than it's availability." ,e.getMessage() );
        }
    }
   /* @Test
    public void createBuyingLog2() {
        new MockUp<Card>(){
            @Mock
            HashMap<Product , ProductInCard> getProductsInThisCard(){
                HashMap<Product , ProductInCard> productsInThisCard = new HashMap<>();
                productsInThisCard.put(product , productInCard);
                return productsInThisCard;
            }
        };
        new MockUp<CustomerController>(){
            @Mock
            int showTotalPrice(Card card){
                return 20;
            }
        };
        new Expectations(){
            {
                user.getCard(); result = card;
                product.getAvailable(); result = 5;
                user.getCard(); result = card;
                productInCard.getNumber(); result = 4;
                user.getCard(); result = card;
              //  user.getCard(); result = card; minTimes = 2;

            }
        };
        String[] information = new String[6];
        try {
            Assert.assertNotNull(customerController.createBuyingLog(user , information ));
        } catch (Exception e) {
            Assert.assertEquals("number of mobileis more than it's availability." ,e.getMessage() );
        }
    }*/

    @Test
    public void putDiscount() {
        new MockUp<ManagerController>() {
            @Mock
            DiscountCode getDiscountById(String id) {
                return discountCode;
            }
        };
        new Expectations(){
            {
                discountCode.getBeginTime(); result = new Date(2000 , 3 , 1);
            }
        };
        try {
            customerController.putDiscount(user , buyingLog , "firstOne");
        } catch (Exception e) {
            Assert.assertEquals("DiscountCode is unavailable this time" , e.getMessage());
        }
    }

    @Test
    public void putDiscount2() {
        new MockUp<ManagerController>() {
            @Mock
            DiscountCode getDiscountById(String id) {
                return discountCode;
            }
        };
        new MockUp<DiscountCode>() {
            @Mock
            HashMap<User , Integer> getDiscountTimesForEachCustomer() {
                HashMap<User , Integer> userIntegerHashMap = new HashMap<>();
                userIntegerHashMap.put(user , 0);
                return userIntegerHashMap;            }
        };
        new Expectations(){
            {
                discountCode.getBeginTime(); result = new Date();
                discountCode.getEndTime(); result =  new Date(1000 , 10 , 1);


            }
        };
        try {
            customerController.putDiscount(user , buyingLog , "secondOne");
        } catch (Exception e) {
            Assert.assertEquals("User has used this code before" , e.getMessage());
        }
    }

    /* @Test
     public void putDiscount3() {
         new MockUp<ManagerController>() {
             @Mock
             DiscountCode getDiscountById(String id) {
                 return discountCode;
             }
         };
         new MockUp<DiscountCode>() {
             @Mock
             void getDiscountById(String id) {
                 return discountCode;
             }
         };
         new MockUp<DiscountCode>() {
             @Mock
             HashMap<User , Integer> getDiscountTimesForEachCustomer() {
                 HashMap<User , Integer> userIntegerHashMap = new HashMap<>();
                 userIntegerHashMap.put(user , 2);
                 return userIntegerHashMap;            }
         };
         new Expectations(){
             {
                 discountCode.getBeginTime(); result = new Date();
                 discountCode.getEndTime(); result =  new Date(1000 , 10 , 1);
                 discountCode.getMaximumDiscount(); result = 20;
                 discountCode.getDiscountPercent(); result = 20;
               //  discountCode.decreaseDiscountTimesForEachCustomer((Customer) user);
             }
         };
         try {
             customerController.putDiscount(user , buyingLog , "secondOne");
         } catch (Exception e) {
             e.printStackTrace();
         }

         }*/
    @Test
    public void payMoney() {

    }

    @Test
    public void createSellingLog(){
        new MockUp<BuyingLog>(){
            @Mock
            HashMap<Product , ProductInCard> getBuyingProducts(){
                HashMap<Product , ProductInCard> allProducts = new HashMap<>();
                allProducts.put(product , productInCard);
                return allProducts;
            }
        };
        new MockUp<Product>(){
            @Mock
            HashMap<Seller , Integer> getSellersOfThisProduct(){
                HashMap<Seller , Integer> allProducts = new HashMap<>();
                allProducts.put(seller1 , 2);
                return allProducts;
            }
        };
        new Expectations(){
            {
                productInCard.getSeller(); result = seller1;
                productInCard.getProduct(); result = product;
                product.getOffs(); result = off;
                off.getSeller(); result = seller1;
                off.getOffPercent(); result = 40;
                productInCard.getNumber(); result = 4;
                seller1.getMoney(3);
                productInCard.getNumber(); result= 3;
                product.decreaseNumberOfProduct(3);
            }
        };
        customerController.createSellingLog(buyingLog);
    }

   /* @Test
    public void showOrder() {
        new Expectations(){
            {

            }
        };
        try {
            customerController.showOrder(user , "firstOrder");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    @Test
    public void showAllOrders() {
        new MockUp<Object>(){
            // @Mock
        };
        new Expectations(){
            {

            }
        };
    }

    @Test
    public void showAllOrdersByList() {
        new MockUp<Object>(){
            // @Mock
        };
        new Expectations(){
            {

            }
        };
    }

    @Test
    public void rateProduct() {
        new MockUp<Object>(){
            // @Mock
        };
        new Expectations(){
            {

            }
        };
    }

    @Test
    public void showBalanceForCustomer() {
        new Expectations(){
            {
                user.getCredit();
            }
        };
        customerController.showBalanceForCustomer(user);
    }

    @Test
    public void showDiscountCodes() {
        new MockUp<Object>(){
            // @Mock
        };
        new Expectations(){
            {

            }
        };
    }
}