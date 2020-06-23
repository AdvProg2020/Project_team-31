package Model;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.junit.Assert.*;
@RunWith(JMockit.class)
public class LogTest {
    @Injectable
    Customer customer;

    @Test
    public void getDate() {
        Log log = new Log("logId" , new Date(2000 , 2 , 20) , customer);
        assertEquals(new Date(2000 , 2 , 20)  , log.getDate());
    }

    @Test
    public void getCustomer() {
        Log log = new Log("logId" , new Date(2000 , 2 , 20) , customer);
        assertEquals(customer , log.getCustomer());
    }

    @Test
    public void getLogId() {
        Log log = new Log("logId" , new Date(2000 , 2 , 20) , customer);
        assertEquals("logId" , log.getLogId());
    }

    @Test
    public void getNumberOfLogCreated() {
        Log log = new Log("logId" , new Date(2000 , 2 , 20) , customer);
        assertEquals(3 , Log.getNumberOfLogCreated());
    }
}