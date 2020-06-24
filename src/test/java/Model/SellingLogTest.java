package Model;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.junit.Assert.*;
@RunWith(JMockit.class)
public class SellingLogTest {
    @Injectable
    Product product;
    @Injectable
    Customer customer;
    @Test
    public void getTotalPriceArrived() {
        SellingLog sellingLog = new SellingLog("sellingLog" , new Date(2020 ,2 ,20) , 10 , 2 , product ,customer );
        assertEquals(10 , sellingLog.getTotalPriceArrived());
    }

    @Test
    public void getAmountOfOff() {
        SellingLog sellingLog = new SellingLog("sellingLog" , new Date(2020 ,2 ,20) , 10 , 2 , product ,customer );
        assertEquals(2 , sellingLog.getAmountOfOff());

    }

    @Test
    public void getBuyingProducts() {
        SellingLog sellingLog = new SellingLog("sellingLog" , new Date(2020 ,2 ,20) , 10 , 2 , product ,customer );
        assertEquals(product , sellingLog.getBuyingProducts());
    }
}