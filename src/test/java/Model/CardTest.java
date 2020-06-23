package Model;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.junit.Assert.*;
@RunWith(JMockit.class)
public class CardTest {
    @Injectable
     ProductInCard productInCard;
    Card card = new Card();
    @Injectable
    Product product;
    @Test
    public void addProductToCard() {
        new Expectations(){
            {
                productInCard.getProduct(); result =product;
                card.getProductsInThisCard().put(product , productInCard);
            }
        };
        card.addProductToCard(productInCard);
    }

    @Test
    public void getProductsInThisCard() {
        HashMap<Product , ProductInCard> sample = new HashMap<>();
        Assert.assertEquals(sample , card.getProductsInThisCard());
    }

    @Test
    public void removeProductFromCard() {
        new Expectations(){
            {
                card.getProductsInThisCard().remove(product);
            }
        };
        card.removeProductFromCard(product);
    }
}