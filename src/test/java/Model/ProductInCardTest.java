package Model;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
@RunWith(JMockit.class)
public class ProductInCardTest {
    @Injectable
    Seller seller;
    @Injectable
    Product product;
    @Test
    public void getProduct() {
        ProductInCard productInCard = new ProductInCard(product , seller);
        assertEquals(product , productInCard.getProduct());
    }

    @Test
    public void changeNumberOfProduct() {
        ProductInCard productInCard = new ProductInCard(product , seller);
        productInCard.changeNumberOfProduct(5);
        assertEquals(6 , productInCard.getNumber());
    }

    @Test
    public void getNumber() {
        ProductInCard productInCard = new ProductInCard(product , seller);
        assertEquals(1 , productInCard.getNumber());

    }

    @Test
    public void getSeller() {
        ProductInCard productInCard = new ProductInCard(product , seller);
        assertEquals(seller , productInCard.getSeller());
    }
}