package Controller;

import Model.*;
import mockit.*;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;
@RunWith(JMockit.class)
public class CustomerControllerTest {

    @Injectable
    User user;
    CustomerController customerController = CustomerController.getInstance();
    @Injectable
    Product product;
    @Injectable
    Card card;
    @Mocked
    ProductInCard productInCard;
    @Mocked
    Seller seller1;
    @Injectable
    Off off;


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
    }

    @Test
    public void showCard() {
    }

    @Test
    public void changeNumberOfProductInCard() {

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
            Assert.assertEquals("number of mobileis more than it's availability." ,e.getMessage() );
        }
    }

    @Test
    public void putDiscount() {
    }

    @Test
    public void payMoney() {
    }

    @Test
    public void showOrder() {
    }

    @Test
    public void showAllOrders() {
    }

    @Test
    public void showAllOrdersByList() {
    }

    @Test
    public void rateProduct() {
    }

    @Test
    public void showBalanceForCustomer() {
    }

    @Test
    public void showDiscountCodes() {
    }
}