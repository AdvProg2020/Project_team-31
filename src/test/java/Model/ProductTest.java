package Model;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
@RunWith(JMockit.class)
public class ProductTest {
    @Injectable
    Category category;
    @Injectable
    Seller seller;
    @Injectable
    Seller seller2;
    @Injectable
    Off off;
    @Injectable
    Comment comment;
    @Test
    public void getAllProducts() {
        HashMap<Seller , Integer> sellersOfThisProduct = new HashMap<>();
        sellersOfThisProduct.put(seller , 10);
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        Product product = new Product("product" , "mobile" , "sum" , category , "is perfect" , 5 ,sellersOfThisProduct , specialPropertiesRelatedToCategory );
    }

    @Test
    public void getNumberOfProductCreated() {
        HashMap<Seller , Integer> sellersOfThisProduct = new HashMap<>();
        sellersOfThisProduct.put(seller , 10);
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        Product product = new Product("product" , "mobile" , "sum" , category , "is perfect" , 5 ,sellersOfThisProduct , specialPropertiesRelatedToCategory );

    }

    @Test
    public void getAndSetMinimumPrice() {
        HashMap<Seller , Integer> sellersOfThisProduct = new HashMap<>();
        sellersOfThisProduct.put(seller , 10);
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        Product product = new Product("product" , "mobile" , "sum" , category , "is perfect" , 5 ,sellersOfThisProduct , specialPropertiesRelatedToCategory );
        product.setMinimumPrice(10);
        assertEquals(10 , product.getMinimumPrice());
    }

    @Test
    public void getInformation() {
        HashMap<Seller , Integer> sellersOfThisProduct = new HashMap<>();
        sellersOfThisProduct.put(seller , 10);
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        Product product = new Product("product" , "mobile" , "sum" , category , "is perfect" , 5 ,sellersOfThisProduct , specialPropertiesRelatedToCategory );
        assertEquals("is perfect" , product.getInformation());
    }

    @Test
    public void getAndSetOffs() {
        HashMap<Seller , Integer> sellersOfThisProduct = new HashMap<>();
        sellersOfThisProduct.put(seller , 10);
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        Product product = new Product("product" , "mobile" , "sum" , category , "is perfect" , 5 ,sellersOfThisProduct , specialPropertiesRelatedToCategory );
        ArrayList<Off> offs = new ArrayList<>();
        offs.add(off);
        product.setOffs(offs);
        assertEquals(offs, product.getOffs());
    }

    @Test
    public void addAndRemoveOff() {
        HashMap<Seller , Integer> sellersOfThisProduct = new HashMap<>();
        sellersOfThisProduct.put(seller , 10);
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        Product product = new Product("product" , "mobile" , "sum" , category , "is perfect" , 5 ,sellersOfThisProduct , specialPropertiesRelatedToCategory );
        ArrayList<Off> offs = new ArrayList<>();
        product.setOffs(offs);
        product.addOff(off);
        product.removeOff(off);
        assertEquals(offs, product.getOffs());
    }

    @Test
    public void removeSeller() {
        HashMap<Seller , Integer> sellersOfThisProduct = new HashMap<>();
        sellersOfThisProduct.put(seller , 10);
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        Product product = new Product("product" , "mobile" , "sum" , category , "is perfect" , 5 ,sellersOfThisProduct , specialPropertiesRelatedToCategory );
        product.removeSeller(seller);
        assertEquals(new HashSet<>() , product.getSellersOfThisProduct().keySet());
    }

    @Test
    public void addSeller() {
        HashMap<Seller , Integer> sellersOfThisProduct = new HashMap<>();
        sellersOfThisProduct.put(seller , 10);
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        Product product = new Product("product" , "mobile" , "sum" , category , "is perfect" , 5 ,sellersOfThisProduct , specialPropertiesRelatedToCategory );
        product.addSeller(seller2 , 20);
        Set<Seller> sample = new HashSet<>();
        sample.add(seller);sample.add(seller2);
        assertEquals(sample , product.getSellersOfThisProduct().keySet());
    }

    @Test
    public void getSpecialPropertiesRelatedToCategory() {
        HashMap<Seller , Integer> sellersOfThisProduct = new HashMap<>();
        sellersOfThisProduct.put(seller , 10);
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        Product product = new Product("product" , "mobile" , "sum" , category , "is perfect" , 5 ,sellersOfThisProduct , specialPropertiesRelatedToCategory );
        assertEquals(specialPropertiesRelatedToCategory , product.getSpecialPropertiesRelatedToCategory());
    }

    @Test
    public void removeSpecialFeature() {
        HashMap<Seller , Integer> sellersOfThisProduct = new HashMap<>();
        sellersOfThisProduct.put(seller , 10);
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        Product product = new Product("product" , "mobile" , "sum" , category , "is perfect" , 5 ,sellersOfThisProduct , specialPropertiesRelatedToCategory );

    }

    @Test
    public void addSpecialFeature() {
        HashMap<Seller , Integer> sellersOfThisProduct = new HashMap<>();
        sellersOfThisProduct.put(seller , 10);
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        Product product = new Product("product" , "mobile" , "sum" , category , "is perfect" , 5 ,sellersOfThisProduct , specialPropertiesRelatedToCategory );

    }

    @Test
    public void getCompany() {
        HashMap<Seller , Integer> sellersOfThisProduct = new HashMap<>();
        sellersOfThisProduct.put(seller , 10);
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        Product product = new Product("product" , "mobile" , "sum" , category , "is perfect" , 5 ,sellersOfThisProduct , specialPropertiesRelatedToCategory );
        assertEquals("sum" , product.getCompany());
    }

    @Test
    public void getViews() {
        HashMap<Seller , Integer> sellersOfThisProduct = new HashMap<>();
        sellersOfThisProduct.put(seller , 10);
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        Product product = new Product("product" , "mobile" , "sum" , category , "is perfect" , 5 ,sellersOfThisProduct , specialPropertiesRelatedToCategory );
        assertEquals(0 , product.getViews());
    }

    @Test
    public void addView() {
        HashMap<Seller , Integer> sellersOfThisProduct = new HashMap<>();
        sellersOfThisProduct.put(seller , 10);
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        Product product = new Product("product" , "mobile" , "sum" , category , "is perfect" , 5 ,sellersOfThisProduct , specialPropertiesRelatedToCategory );
        product.addView();
        assertEquals(1 , product.getViews());
    }

    @Test
    public void getProductId() {
        HashMap<Seller , Integer> sellersOfThisProduct = new HashMap<>();
        sellersOfThisProduct.put(seller , 10);
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        Product product = new Product("product" , "mobile" , "sum" , category , "is perfect" , 5 ,sellersOfThisProduct , specialPropertiesRelatedToCategory );
        assertEquals("product" , product.getProductId());
    }

    @Test
    public void getName() {
        HashMap<Seller , Integer> sellersOfThisProduct = new HashMap<>();
        sellersOfThisProduct.put(seller , 10);
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        Product product = new Product("product" , "mobile" , "sum" , category , "is perfect" , 5 ,sellersOfThisProduct , specialPropertiesRelatedToCategory );
        assertEquals("mobile" , product.getName());
    }

    @Test
    public void getSellersOfThisProduct() {
        HashMap<Seller , Integer> sellersOfThisProduct = new HashMap<>();
        sellersOfThisProduct.put(seller , 10);
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        Product product = new Product("product" , "mobile" , "sum" , category , "is perfect" , 5 ,sellersOfThisProduct , specialPropertiesRelatedToCategory );
        assertEquals(sellersOfThisProduct , product.getSellersOfThisProduct());
    }

    @Test
    public void getAvailable() {
        HashMap<Seller , Integer> sellersOfThisProduct = new HashMap<>();
        sellersOfThisProduct.put(seller , 10);
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        Product product = new Product("product" , "mobile" , "sum" , category , "is perfect" , 5 ,sellersOfThisProduct , specialPropertiesRelatedToCategory );
        assertEquals(5 , product.getAvailable());
    }

    @Test
    public void decreaseNumberOfProduct() {
        HashMap<Seller , Integer> sellersOfThisProduct = new HashMap<>();
        sellersOfThisProduct.put(seller , 10);
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        Product product = new Product("product" , "mobile" , "sum" , category , "is perfect" , 5 ,sellersOfThisProduct , specialPropertiesRelatedToCategory );
        product.decreaseNumberOfProduct(2);
        assertEquals(3 , product.getAvailable());
    }

    @Test
    public void getSumOfCustomersRate() {
        HashMap<Seller , Integer> sellersOfThisProduct = new HashMap<>();
        sellersOfThisProduct.put(seller , 10);
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        Product product = new Product("product" , "mobile" , "sum" , category , "is perfect" , 5 ,sellersOfThisProduct , specialPropertiesRelatedToCategory );
        assertEquals(0 , product.getSumOfCustomersRate());
    }

    @Test
    public void addSumOfCustomersRate() {
        HashMap<Seller , Integer> sellersOfThisProduct = new HashMap<>();
        sellersOfThisProduct.put(seller , 10);
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        Product product = new Product("product" , "mobile" , "sum" , category , "is perfect" , 5 ,sellersOfThisProduct , specialPropertiesRelatedToCategory );
        product.addSumOfCustomersRate(456);
        assertEquals(456 , product.getSumOfCustomersRate());
    }

    @Test
    public void getCustomersWhoRated() {
        HashMap<Seller , Integer> sellersOfThisProduct = new HashMap<>();
        sellersOfThisProduct.put(seller , 10);
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        Product product = new Product("product" , "mobile" , "sum" , category , "is perfect" , 5 ,sellersOfThisProduct , specialPropertiesRelatedToCategory );
        assertEquals(1 , product.getCustomersWhoRated());
    }

    @Test
    public void addNumberOfCustomerWhoRated() {
        HashMap<Seller , Integer> sellersOfThisProduct = new HashMap<>();
        sellersOfThisProduct.put(seller , 10);
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        Product product = new Product("product" , "mobile" , "sum" , category , "is perfect" , 5 ,sellersOfThisProduct , specialPropertiesRelatedToCategory );
        product.addNumberOfCustomerWhoRated();
        assertEquals(1 , product.getCustomersWhoRated());
    }

    @Test
    public void getProductStatus() {
        HashMap<Seller , Integer> sellersOfThisProduct = new HashMap<>();
        sellersOfThisProduct.put(seller , 10);
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        Product product = new Product("product" , "mobile" , "sum" , category , "is perfect" , 5 ,sellersOfThisProduct , specialPropertiesRelatedToCategory );
        assertEquals(ProductAndOffStatus.CREATING , product.getProductStatus());
    }

    @Test
    public void setProductStatus() {
        HashMap<Seller , Integer> sellersOfThisProduct = new HashMap<>();
        sellersOfThisProduct.put(seller , 10);
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        Product product = new Product("product" , "mobile" , "sum" , category , "is perfect" , 5 ,sellersOfThisProduct , specialPropertiesRelatedToCategory );
        product.setProductStatus(ProductAndOffStatus.ACCEPTED);
        assertEquals(ProductAndOffStatus.ACCEPTED , product.getProductStatus());
    }

    @Test
    public void getCategory() {
        HashMap<Seller , Integer> sellersOfThisProduct = new HashMap<>();
        sellersOfThisProduct.put(seller , 10);
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        Product product = new Product("product" , "mobile" , "sum" , category , "is perfect" , 5 ,sellersOfThisProduct , specialPropertiesRelatedToCategory );
        assertEquals(category , product.getCategory());
    }

    @Test
    public void getAllCommentsAndAddComment() {
        HashMap<Seller , Integer> sellersOfThisProduct = new HashMap<>();
        sellersOfThisProduct.put(seller , 10);
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        Product product = new Product("product" , "mobile" , "sum" , category , "is perfect" , 5 ,sellersOfThisProduct , specialPropertiesRelatedToCategory );
        product.addComment(comment);
        ArrayList<Comment> comments = new ArrayList<>();
        comments.add(comment);
        assertEquals(comments , product.getAllComments());
    }

    @Test
    public void setAvailable() {
        HashMap<Seller , Integer> sellersOfThisProduct = new HashMap<>();
        sellersOfThisProduct.put(seller , 10);
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        Product product = new Product("product" , "mobile" , "sum" , category , "is perfect" , 5 ,sellersOfThisProduct , specialPropertiesRelatedToCategory );
        product.setAvailable(4);
        assertEquals(4 , product.getAvailable());
    }

    @Test
    public void setInformation() {
        HashMap<Seller , Integer> sellersOfThisProduct = new HashMap<>();
        sellersOfThisProduct.put(seller , 10);
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        Product product = new Product("product" , "mobile" , "sum" , category , "is perfect" , 5 ,sellersOfThisProduct , specialPropertiesRelatedToCategory );
        product.setInformation("so bad");
        assertEquals("so bad" , product.getInformation());
    }

    @Test
    public void setSpecialPropertiesRelatedToCategory() {
        HashMap<Seller , Integer> sellersOfThisProduct = new HashMap<>();
        sellersOfThisProduct.put(seller , 10);
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        Product product = new Product("product" , "mobile" , "sum" , category , "is perfect" , 5 ,sellersOfThisProduct , specialPropertiesRelatedToCategory );

    }

    @Test
    public void logToFile() {
    }

    @Test
    public void fileToLog() {
    }
}