package Model;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
@RunWith(JMockit.class)
public class SellerOfProductRequestTest {
    @Injectable
    Seller seller;
    @Injectable
    Product product;
    @Test
    public void getSeller() {
        SellerOfProductRequest sellerOfProductRequest = new SellerOfProductRequest("request" , seller , product , 10);
        assertEquals(seller , sellerOfProductRequest.getSeller());
    }

    @Test
    public void getProduct() {
        SellerOfProductRequest sellerOfProductRequest = new SellerOfProductRequest("request" , seller , product , 10);
        assertEquals(product , sellerOfProductRequest.getProduct());
    }

    @Test
    public void getPrice() {
        SellerOfProductRequest sellerOfProductRequest = new SellerOfProductRequest("request" , seller , product , 10);
        assertEquals(10 , sellerOfProductRequest.getPrice());
    }
}