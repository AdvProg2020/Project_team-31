package Controller;

import Model.*;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
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
    @Injectable
    Seller seller;
    @Injectable
    Product product2;
    @Injectable
    Off off;
    @Injectable
    Customer customer;
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
    public void showProducts4() {
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
                product.getSumOfCustomersRate();result = 20;
                product.getCustomersWhoRated();result = 10;
                product.getName();result = "mobile";
                product.getMinimumPrice(); result = 5;
                product.getSumOfCustomersRate();result = 20;
                product.getCustomersWhoRated();result = 10;
            }
        };
        ArrayList<String> sample = new ArrayList<>();
        sample.add("name=" + "mobile" + ", price=" + 5 + ", rate=" + 2);
        try {
            assertEquals(sample , productController.showProducts(user , "category" , "rate"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void showProducts5() {
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
                product.getMinimumPrice();result = 10;
                product.getName();result = "mobile";
                product.getMinimumPrice(); result = 5;
                product.getSumOfCustomersRate();result = 20;
                product.getCustomersWhoRated();result = 10;
            }
        };
        ArrayList<String> sample = new ArrayList<>();
        sample.add("name=" + "mobile" + ", price=" + 5 + ", rate=" + 2);
        try {
            assertEquals(sample , productController.showProducts(user , "category" , "price"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void showDigestOfProduct() {
        new MockUp<Product>(){
            @Mock
            public HashMap<Seller , Integer> getSellersOfThisProduct(){
                HashMap<Seller ,Integer> sellers = new HashMap<>();
                sellers.put(seller , 10);
                return sellers;
            }
        };
        new Expectations(){
            {
                product.addView();
                product.getName();result = "mobile";
                product.getSumOfCustomersRate();result = 20;
                product.getCustomersWhoRated();result = 10;
                product.getProductStatus(); result = ProductAndOffStatus.ACCEPTED;
                product.getSellersOfThisProduct();
            }
        };
        ArrayList<String> sample = new ArrayList<>();
        sample.add("name=" + "mobile");
        sample.add("rate=" + 2);
        sample.add("status=" + ProductAndOffStatus.ACCEPTED);
        sample.add("seller: " + seller + ", price: " + 10);
        assertEquals(sample , productController.showDigestOfProduct(product , customer) );
    }

    @Test
    public void showAttributesOfProduct() {
        new MockUp<SellerController>(){
            @Mock
            public void checkTimeOfOffs(){

            }
        };
        HashMap<String , String> specialPropertiesRelatedToCategory = new HashMap<>();
        specialPropertiesRelatedToCategory.put("salam" , "hamed");
        HashMap<Seller , Integer> sellers = new HashMap<>();
        sellers.put(seller , 10);
        ArrayList<Off> offs = new ArrayList<>();
        offs.add(off);
        new Expectations(){
            {
                SellerController.getInstance().checkTimeOfOffs();
                product.getProductStatus(); result = ProductAndOffStatus.ACCEPTED;
                product.getInformation(); result = "is good";
                product.getMinimumPrice(); result = 10;
                product.getCategory();result = category;
                category.getName(); result ="category";
                product.getSumOfCustomersRate();result = 20;
                product.getCustomersWhoRated();result= 10;
                product.getAvailable(); result = 5;
                product.getViews();result = 5;
                product.getSpecialPropertiesRelatedToCategory();result = specialPropertiesRelatedToCategory;times = 2;
                product.getSellersOfThisProduct();result = sellers;
                seller.getUsername();result = "seller";
                product.getSellersOfThisProduct();result = sellers;
                product.getOffs();result = offs;
                off.getSeller();result = seller;
                seller.getUsername();result = "seller";
                off.getOffId();result = "off";
                off.getOffPercent();result = 20;
            }
        };
        ArrayList<String> attributes = new ArrayList<>();
        attributes.add("status: " +ProductAndOffStatus.ACCEPTED );
        attributes.add("information: " +"is good" );
        attributes.add("minimumPrice: " +10 );
        attributes.add("category: " + "category");
        attributes.add("rate:" + 2 );
        attributes.add("available:" + 5);
        attributes.add("views:" + 5);
        attributes.add("salam" + ": " + "hamed");
        attributes.add("seller: " + "seller" + ", price: " + 10);
        attributes.add("seller: " + "seller" + ", id: " + "off" + ", percent: " + 20);
        assertEquals(attributes , productController.showAttributesOfProduct(product));
    }

    @Test
    public void showAvailableFiltersForUser() {
        ArrayList<String> sample = new ArrayList<>();
        sample.addAll(Arrays.asList("minimumPrice", "company", "name", "rate", "availability"));
        assertEquals(sample , productController.showAvailableFiltersForUser(user , null));
    }
    @Test
    public void showAvailableFiltersForUse2() {
        new MockUp<ManagerController>(){
             @Mock
            public Category getCategoryByName(String name){
                 return category;
             }
        };
        ArrayList<String> sample2 = new ArrayList<>();
        sample2.addAll(Arrays.asList("salam" , "hamed" , "mamad" , "kaka"));
        new Expectations(){
            {
                ManagerController.getCategoryByName("category");
                category.getSpecialProperties();result = sample2;
            }
        };
        ArrayList<String> sample = new ArrayList<>();
        sample.add("salam");
        sample.add("hamed");
        sample.add("mamad");
        sample.add("kaka");
        sample.addAll(Arrays.asList("minimumPrice", "company", "name", "rate", "availability"));
        assertEquals(sample , productController.showAvailableFiltersForUser(user , "category"));
    }

    @Test
    public void addFilterForUser() {
        new MockUp<Object>(){
            // @Mock
        };
        HashMap<String , String> filters = new HashMap<>();
        filters.put("good" , "perfect");
        new Expectations(){
            {
                user.getFilters();result = filters;
                user.removeFilter("good");
                user.addFilter("good" , "bad");
            }
        };
        productController.addFilterForUser(user , "good" , "bad");
    }

    @Test
    public void showOffProduct() {
        new MockUp<SellerController>(){
            @Mock
            public void checkTimeOfOffs(){

            }
        };
        new MockUp<Product>(){
             @Mock
            public ArrayList<Product> getAllProducts(){
                 ArrayList<Product> products = new ArrayList<>();
                 products.add(product);
                 return products;
             }
        };
        new MockUp<User>(){
            @Mock
            public HashMap<String , String> getFilters(){
                HashMap<String, String> filters = new HashMap<>();
                filters.put("good" , "perfect");
                return filters;
            }
        };
        ArrayList<Off> offs = new ArrayList<>();
        offs.add(off);
        new Expectations(){
            {
                SellerController.getInstance().checkTimeOfOffs();
                Product.getAllProducts();
                product.getOffs();result = offs;
                user.getFilters();
                product.getViews();result = 2;
                product.getOffs();result = new ArrayList<Off>();

            }
        };
        try {
            productController.showOffProduct(user , "nothing");
        } catch (Exception e) {
           assertEquals("there is no off with this filters" , e.getMessage());
        }
    }
    @Test
    public void showOffProduct2() {
        new MockUp<SellerController>(){
            @Mock
            public void checkTimeOfOffs(){

            }
        };
        new MockUp<Product>(){
            @Mock
            public ArrayList<Product> getAllProducts(){
                ArrayList<Product> products = new ArrayList<>();
                products.add(product);
                return products;
            }
        };
        new MockUp<User>(){
            @Mock
            public HashMap<String , String> getFilters(){
                HashMap<String, String> filters = new HashMap<>();
                filters.put("good" , "perfect");
                return filters;
            }
        };
        ArrayList<Off> offs = new ArrayList<>();
        offs.add(off);
        HashMap<Seller , Integer> sellers = new HashMap<>();
        sellers.put(seller , 40);
        new Expectations(){
            {
                SellerController.getInstance().checkTimeOfOffs();
                Product.getAllProducts();
                product.getOffs();result = offs;
                user.getFilters();
                product.getViews();result = 2;
                product.getOffs();result = offs;
                product.getProductId(); result = "product";
                off.getSeller();result = seller;
                seller.getUsername();result = "seller";
                product.getSellersOfThisProduct();result = sellers;
                off.getSeller();result = seller;
                off.getOffPercent();result = 55;
            }
        };
        ArrayList<String> offsArray = new ArrayList<>();
        offsArray.add("productId:" + "product" + ", seller:" +"seller"  + ", original price: " + 40  + ", offPercent: " + 55);

        try {
           assertEquals(offsArray ,productController.showOffProduct(user , "nothing") );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void clearFilters() {
        new MockUp<User>(){
            @Mock
            public HashMap<String , String> getFilters(){
                HashMap<String, String> filters = new HashMap<>();
                filters.put("good" , "perfect");
                return filters;
            }
        };
        new Expectations(){
            {
                user.getFilters();
                user.removeFilter("good");
            }
        };
        productController.clearFilters(user);
    }

    @Test
    public void showCurrentFilters() {
        new MockUp<User>(){
            @Mock
            public HashMap<String , String> getFilters(){
                HashMap<String, String> filters = new HashMap<>();
                filters.put("good" , "perfect");
                return filters;
            }
        };
        new Expectations(){
            {
                user.getFilters();
            }
        };
        HashMap<String, String> sample = new HashMap<>();
        sample.put("good" , "perfect");
        assertEquals(sample , productController.showCurrentFilters(user));
    }

    @Test
    public void disableFilterForUser() {
        new MockUp<User>(){
            @Mock
            public HashMap<String , String> getFilters(){
                HashMap<String, String> filters = new HashMap<>();
                filters.put("good" , "perfect");
                return filters;
            }
        };
        new Expectations(){
            {
                user.getFilters();
            }
        };
        try {
            productController.disableFilterForUser(user , "bad");
        } catch (Exception e) {
            assertEquals("User does not have this filter" , e.getMessage());
        }
    }
    @Test
    public void disableFilterForUser2() {
        new MockUp<User>(){
            @Mock
            public HashMap<String , String> getFilters(){
                HashMap<String, String> filters = new HashMap<>();
                filters.put("good" , "perfect");
                return filters;
            }
        };
        new Expectations(){
            {
                user.getFilters();
                user.removeFilter("good");
            }
        };
        try {
            productController.disableFilterForUser(user , "good");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void compareTwoProduct() {
        new MockUp<ProductController>(){
             @Mock
            public Product getProductById(String Id){
                 return null;
             }
        };
        new Expectations(){
            {
                ProductController.getProductById("product2");
            }
        };
        try {
            productController.compareTwoProduct(product , "Product2");
        } catch (Exception e) {
            assertEquals("second product doesn't exist" ,e.getMessage());
        }
    }

    @Test
    public void compareTwoProduct2() {
        new MockUp<ProductController>(){
            @Mock
            public Product getProductById(String Id){
                return product2;
            }
        };
        HashMap<String , String> filters = new HashMap<>();
        filters.put("new" , "old");
        HashMap<String , String> filters2 = new HashMap<>();
        filters2.put("new" , "older");
        new Expectations(){
            {
                ProductController.getProductById("product2");
                product.getName();result = "product1";
                product2.getName();result = "product2";
                product.getMinimumPrice();result = 20;
                product2.getMinimumPrice();result = 10;
                product.getSumOfCustomersRate();result = 20;
                product.getCustomersWhoRated();result= 10;
                product2.getSumOfCustomersRate();result = 50;
                product2.getCustomersWhoRated();result= 5;
                product.getViews();result = 4;
                product2.getViews();result = 5;
                product.getSpecialPropertiesRelatedToCategory();result = filters;
                product2.getSpecialPropertiesRelatedToCategory();result = filters2;
                product.getSpecialPropertiesRelatedToCategory();result = filters;
                product2.getSpecialPropertiesRelatedToCategory();result = filters2;
            }
        };
        ArrayList<String> information = new ArrayList<>();
        information.add("name:1-" + "product1" + ";2-" + "product2");
        information.add("minimumPrice:1-" + 20 + ";2-" +10 );
        information.add("rate:1-" + 2.0 + ";2-" + 10.0);
        information.add("views:1-" + 4 + ";2-" + 5);
        information.add("new" + ":1-" + "old" + ";2-" + "older");
        try {
            assertEquals(information , productController.compareTwoProduct(product , "Product2"));
        } catch (Exception e) {
            e.printStackTrace();
        }
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