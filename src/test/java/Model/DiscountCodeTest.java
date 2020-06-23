package Model;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.*;
@RunWith(JMockit.class)
public class DiscountCodeTest {
    DiscountCode discountCode = new DiscountCode("discountCode");
    @Injectable
    Customer customer;
    @Test
    public void getDiscountCode() {
        assertEquals("discountCode" , discountCode.getDiscountCode());
    }

    @Test
    public void getBeginTime() {
        HashMap<Customer, Integer> discountTimesForEachCustomer = new HashMap<>();
        discountTimesForEachCustomer.put(customer , 10);
        discountCode.setDiscountCode(new Date(2000 , 2 , 20) , new Date(2020 , 2 , 20) , 40 , 50 , discountTimesForEachCustomer);
        assertEquals(new Date(2000 , 2 , 20) ,discountCode.getBeginTime());
    }

    @Test
    public void getEndTime() {
        HashMap<Customer, Integer> discountTimesForEachCustomer = new HashMap<>();
        discountTimesForEachCustomer.put(customer , 10);
        discountCode.setDiscountCode(new Date(2000 , 2 , 20) , new Date(2020 , 2 , 20) , 40 , 50 , discountTimesForEachCustomer);
        assertEquals(new Date(2020 , 2 ,20) , discountCode.getEndTime());
    }

    @Test
    public void getDiscountPercent() {
        HashMap<Customer, Integer> discountTimesForEachCustomer = new HashMap<>();
        discountTimesForEachCustomer.put(customer , 10);
        discountCode.setDiscountCode(new Date(2000 , 2 , 20) , new Date(2020 , 2 , 20) , 40 , 50 , discountTimesForEachCustomer);
        assertEquals(40 , discountCode.getDiscountPercent());
    }

    @Test
    public void getMaximumDiscount() {
        HashMap<Customer, Integer> discountTimesForEachCustomer = new HashMap<>();
        discountTimesForEachCustomer.put(customer , 10);
        discountCode.setDiscountCode(new Date(2000 , 2 , 20) , new Date(2020 , 2 , 20) , 40 , 50 , discountTimesForEachCustomer);
        assertEquals(50 , discountCode.getMaximumDiscount());
    }

    @Test
    public void getDiscountTimesForEachCustomer() {
        HashMap<Customer, Integer> discountTimesForEachCustomer = new HashMap<>();
        discountTimesForEachCustomer.put(customer , 10);
        discountCode.setDiscountCode(new Date(2000 , 2 , 20) , new Date(2020 , 2 , 20) , 40 , 50 , discountTimesForEachCustomer);
        assertEquals(50 , discountCode.getMaximumDiscount());
    }

    @Test
    public void decreaseDiscountTimesForEachCustomer() {
        HashMap<Customer, Integer> discountTimesForEachCustomer = new HashMap<>();
        discountTimesForEachCustomer.put(customer , 10);
        discountCode.setDiscountCode(new Date(2000 , 2 , 20) , new Date(2020 , 2 , 20) , 40 , 50 , discountTimesForEachCustomer);
        new Expectations(){
            {
                discountCode.getDiscountTimesForEachCustomer().get(customer);
                discountCode.getDiscountTimesForEachCustomer().remove(customer);
                discountCode.getDiscountTimesForEachCustomer().put(customer, 9);

            }
        };
         discountCode.decreaseDiscountTimesForEachCustomer(customer);
    }

    @Test
    public void removeDiscountCode() {
        new Expectations(){
            {
                DiscountCode.getAllDiscountCodes().remove(discountCode);
            }
        };
        discountCode.removeDiscountCode();
    }

    @Test
    public void setDiscountCode() {
        new Expectations(){
            {
                discountCode.getBeginTime();
                discountCode.getEndTime();
                discountCode.getDiscountPercent();
                discountCode.getMaximumDiscount();
                discountCode.getDiscountTimesForEachCustomer();
            }
        };
        HashMap<Customer, Integer> discountTimesForEachCustomer = new HashMap<>();
        discountCode.setDiscountCode(new Date(2000 , 2 , 20) , new Date(2020 , 2 , 20) , 40 , 50 , discountTimesForEachCustomer);
    }

    @Test
    public void logToFile() {
    }

    @Test
    public void fileToLog() {
    }
}