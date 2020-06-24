package Model;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;
import sun.misc.ASCIICaseInsensitiveComparator;

import java.util.HashMap;

import static org.junit.Assert.*;
@RunWith(JMockit.class)
public class ProductRequestTest {
    @Injectable
    Product product;
    @Injectable
    Seller seller;
    @Test
    public void newProductFeatures() {
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        ProductRequest productRequest = new ProductRequest("productRequest" , product , true);
        productRequest.newProductFeatures(seller,20,5,"very good" , specialPropertiesRelatedToCategory);
    }

    @Test
    public void getSeller() {
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        ProductRequest productRequest = new ProductRequest("productRequest" , product , true);
        productRequest.newProductFeatures(seller,20,5,"very good" , specialPropertiesRelatedToCategory);
        assertEquals(seller , productRequest.getSeller());
    }

    @Test
    public void getPrice() {
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        ProductRequest productRequest = new ProductRequest("productRequest" , product , true);
        productRequest.newProductFeatures(seller,20,5,"very good" , specialPropertiesRelatedToCategory);
        assertEquals(20 , productRequest.getPrice());

    }

    @Test
    public void getAvailable() {
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        ProductRequest productRequest = new ProductRequest("productRequest" , product , true);
        productRequest.newProductFeatures(seller,20,5,"very good" , specialPropertiesRelatedToCategory);
        assertEquals(5 , productRequest.getAvailable());
    }

    @Test
    public void getInformation() {
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        ProductRequest productRequest = new ProductRequest("productRequest" , product , true);
        productRequest.newProductFeatures(seller,20,5,"very good" , specialPropertiesRelatedToCategory);
        assertEquals("very good" , productRequest.getInformation());
    }

    @Test
    public void getSpecialPropertiesRelatedToCategory() {
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        ProductRequest productRequest = new ProductRequest("productRequest" , product , true);
        productRequest.newProductFeatures(seller,20,5,"very good" , specialPropertiesRelatedToCategory);
        assertEquals(specialPropertiesRelatedToCategory , productRequest.getSpecialPropertiesRelatedToCategory());
    }

    @Test
    public void isEditing() {
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        ProductRequest productRequest = new ProductRequest("productRequest" , product , true);
        productRequest.newProductFeatures(seller,20,5,"very good" , specialPropertiesRelatedToCategory);
        assertEquals(true , productRequest.isEditing());
    }

    @Test
    public void getProduct() {
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        ProductRequest productRequest = new ProductRequest("productRequest" , product , true);
        productRequest.newProductFeatures(seller,20,5,"very good" , specialPropertiesRelatedToCategory);
        assertEquals(product , productRequest.getProduct());
    }
}