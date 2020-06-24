package Model;

import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
@RunWith(JMockit.class)
public class SellerRequestTest {
    @Test
    public void getUsername() {
        String[] information = new String[2];
        information[0] = "ali";
        information[1] = "lili";
        SellerRequest sellerRequest = new SellerRequest("sellerRequest" , "hamed" , information);
        assertEquals("hamed" , sellerRequest.getUsername());
    }

    @Test
    public void getInformation() {
        String[] information = new String[2];
        information[0] = "ali";
        information[1] = "lili";
        SellerRequest sellerRequest = new SellerRequest("sellerRequest" , "hamed" , information);
        assertEquals(information , sellerRequest.getInformation());
    }
}