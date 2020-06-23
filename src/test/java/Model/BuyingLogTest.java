package Model;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.*;
@RunWith(JMockit.class)
public class BuyingLogTest {
    @Injectable
     Customer customer;
    HashMap<Product, ProductInCard> buyingProducts = new HashMap<>();
    String[] hamed = new String[5];
    BuyingLog buyingLog = new BuyingLog("first" , 20 , customer , buyingProducts , hamed);
    @Test
    public void finishBuying() {
        new Expectations(){
            {
                buyingLog.date = new Date(2000 , 2 , 20);
            }
        };
        buyingLog.finishBuying(new Date(2000 , 2 , 20));
    }

    @Test
    public void setAndGetDiscountAmount() {
        buyingLog.setDiscountAmount(50);
        Assert.assertEquals(50 , buyingLog.getDiscountAmount());
    }

    @Test
    public void getTotalPrice() {
        Assert.assertEquals(20 , buyingLog.getTotalPrice());

    }

    @Test
    public void getBuyingProducts() {
        Assert.assertEquals(buyingProducts , buyingLog.getBuyingProducts());
    }
}