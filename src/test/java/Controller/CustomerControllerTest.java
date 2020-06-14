package Controller;

import Model.*;
import mockit.*;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.jws.soap.SOAPBinding;
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
        Assert.assertNotNull(productInCard);
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
    }

    @Test
    public void showTotalPrice() {
    }

    @Test
    public void createBuyingLog() {
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