package Controller;

import Model.Category;
import Model.Product;
import Model.User;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;
@RunWith(JMockit.class)
public class ProductControllerTest {
    @Injectable
    User user;
    @Injectable
    Product product;
    @Injectable
    Category category;
    ProductController productController = ProductController.getInstance();
    @Test
    public void getInstance() {
        assertNotNull(ProductController.getInstance());
    }

    @Test
    public void showProducts() {
        new MockUp<Product>(){
            @Mock
            public ArrayList<Product> getAllProducts(){
                ArrayList<Product> products = new ArrayList<>();
                products.add(product);
                return products;
            }
        };
        HashMap<String , String> filters = new HashMap<>();
        filters.put("salam" , "hamed");
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        filters.put("keyset" , "hamed");
        new Expectations(){
            {
                Product.getAllProducts();
                user.getFilters();result = filters;
                product.getSpecialPropertiesRelatedToCategory();result = specialPropertiesRelatedToCategory;
                product.getViews();result = 20;
                product.getName();result = "mobile";
                product.getMinimumPrice(); result = 5;
                product.getSumOfCustomersRate();result = 20;
                product.getCustomersWhoRated();result = 10;


  //              product.getCompany(); result = "sun";
            }
        };
        ArrayList<String> sample = new ArrayList<>();
        sample.add("name=" + "mobile" + ", price=" + 5 + ", rate=" + 2);
        try {
            assertEquals(sample , productController.showProducts(user , null , "good"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void showProducts2() {
        new MockUp<Product>(){
            @Mock
            public ArrayList<Product> getAllProducts(){
                ArrayList<Product> products = new ArrayList<>();
                return products;
            }
        };
        HashMap<String , String> filters = new HashMap<>();
        filters.put("salam" , "hamed");
        new Expectations(){
            {
                Product.getAllProducts();
            }
        };
        try {
            productController.showProducts(user , null , "good");
        } catch (Exception e) {
            assertEquals("There is no product with these filters" , e.getMessage());
        }
    }
    @Test
    public void showProducts3() {
        new MockUp<ManagerController>(){
            @Mock
            public Category getCategoryByName(String name){
                return category;
            }
        };
        HashMap<String , String> filters = new HashMap<>();
        filters.put("salam" , "hamed");
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        filters.put("keyset" , "hamed");
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        new Expectations(){
            {
                ManagerController.getCategoryByName("category");
                category.getProducts();result = products;
                user.getFilters();result = filters;
                product.getSpecialPropertiesRelatedToCategory();result = specialPropertiesRelatedToCategory;
                category.getSpecialProperties();result = specialPropertiesRelatedToCategory;times = 2;
                product.getViews();result = 20;
                product.getName();result = "mobile";
                product.getMinimumPrice(); result = 5;
                product.getSumOfCustomersRate();result = 20;
                product.getCustomersWhoRated();result = 10;
            }
        };
        ArrayList<String> sample = new ArrayList<>();
        sample.add("name=" + "mobile" + ", price=" + 5 + ", rate=" + 2);
        try {
            assertEquals(sample , productController.showProducts(user , "category" , null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void showDigestOfProduct() {
        new MockUp<ManagerController>(){
            @Mock
            public Category getCategoryByName(String name){
                return category;
            }
        };
        new Expectations(){
            {

            }
        };
    }

    @Test
    public void showAttributesOfProduct() {
        new MockUp<Object>(){
            // @Mock
        };
        new Expectations(){
            {

            }
        };
    }

    @Test
    public void showAvailableFiltersForUser() {
        new MockUp<Object>(){
            // @Mock
        };
        new Expectations(){
            {

            }
        };
    }

    @Test
    public void addFilterForUser() {
        new MockUp<Object>(){
            // @Mock
        };
        new Expectations(){
            {

            }
        };
    }

    @Test
    public void showOffProduct() {
        new MockUp<Object>(){
            // @Mock
        };
        new Expectations(){
            {

            }
        };
    }

    @Test
    public void clearFilters() {
        new MockUp<Object>(){
            // @Mock
        };
        new Expectations(){
            {

            }
        };
    }

    @Test
    public void showCurrentFilters() {
        new MockUp<Object>(){
            // @Mock
        };
        new Expectations(){
            {

            }
        };
    }

    @Test
    public void disableFilterForUser() {
        new MockUp<Object>(){
            // @Mock
        };
        new Expectations(){
            {

            }
        };
    }

    @Test
    public void compareTwoProduct() {
        new MockUp<Object>(){
            // @Mock
        };
        new Expectations(){
            {

            }
        };
    }

    @Test
    public void showCommentAboutProduct() {
        new MockUp<Object>(){
            // @Mock
        };
        new Expectations(){
            {

            }
        };
    }

    @Test
    public void addComment() {
        new MockUp<Object>(){
            // @Mock
        };
        new Expectations(){
            {

            }
        };
    }

    @Test
    public void getProductById() {
        new MockUp<Object>(){
            // @Mock
        };
        new Expectations(){
            {

            }
        };
    }
}