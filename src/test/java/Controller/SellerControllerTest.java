package Controller;

import Model.*;
import mockit.*;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.*;
@RunWith(JMockit.class)
public class SellerControllerTest {
    SellerController sellerController = SellerController.getInstance();
    @Injectable
    Seller seller;
    @Injectable
    Seller seller2;
    @Injectable
    User user;
    @Mocked
    Product product;
    @Injectable
    Customer customer;
    @Injectable
    Category category;
    @Mocked
    ProductRequest productRequest;
    @Injectable
    SellingLog sellingLog;
    @Mocked
    Off off;
    @Test
    public void getInstance() {
        Assert.assertNotNull(SellerController.getInstance());
    }

    @Test
    public void showCompanyInformation() {
        try {
            sellerController.showCompanyInformation(user);
        } catch (Exception e) {
            assertEquals("You aren't seller" ,e.getMessage() );
        }
    }
    @Test
    public void showCompanyInformation2() {
        new MockUp<Object>(){
            //@Mock
        };
        new Expectations(){
            {
                seller.getCompanyName();result ="company";
            }
        };
        try {
            assertEquals("company" , sellerController.showCompanyInformation(seller));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addSellerToProduct() {
        try {
            sellerController.addSellerToProduct(user , "product" , 500);
        } catch (Exception e) {
            assertEquals("You can't be a seller" , e.getMessage());
        }
    }
    @Test
    public void addSellerToProduct2() {
        new MockUp<ProductController>(){
            @Mock
            public Product getProductById(String Id){
                return null;
            }
        };
        new Expectations(){
            {
                ProductController.getProductById("product");
            }
        };
        try {
            sellerController.addSellerToProduct(seller , "product" , 500);
        } catch (Exception e) {
            assertEquals("productId is invalid" , e.getMessage());
        }
    }
    @Test
    public void addSellerToProduct3() {
        new MockUp<ProductController>(){
            @Mock
            public Product getProductById(String Id){
                return product;
            }
        };
        HashMap<Seller  , Integer> sellers = new HashMap<>();
        sellers.put(seller , 10);
        new Expectations(){
            {
                ProductController.getProductById("product");
                product.getSellersOfThisProduct();result = sellers;
            }
        };
        try {
            sellerController.addSellerToProduct(seller , "product" , 500);
        } catch (Exception e) {
            assertEquals("seller has product" , e.getMessage());
        }
    }
    @Test
    public void addSellerToProduct4() {
        new MockUp<ProductController>(){
            @Mock
            public Product getProductById(String Id){
                return product;
            }
        };
        new MockUp<Request>(){
            @Mock
            public int getNumberOfRequestCreated(){
                return 10;
            }
        };
        HashMap<Seller  , Integer> sellers = new HashMap<>();
        new Expectations(){
            {
                ProductController.getProductById("product");
                product.getSellersOfThisProduct();result = sellers;
                Request.getNumberOfRequestCreated();
            }
        };
        try {
            sellerController.addSellerToProduct(seller , "product" , 500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void showSalesHistory() {
        new MockUp<Object>(){
            //@Mock
        };
        ArrayList<SellingLog> sellingLogs = new ArrayList<>();
        sellingLogs.add(sellingLog);
        new Expectations(){
            {
                seller.getAllSellingLogs();result = sellingLogs;
                sellingLog.getLogId();result = "sellingLog";
                sellingLog.getDate();result = new Date(2020 , 2 ,20);
                sellingLog.getTotalPriceArrived();result = 50;
                sellingLog.getCustomer();result = customer;
                customer.getUsername();result = "customer";

            }
        };
        ArrayList<String> sample = new ArrayList<>();
        sample.add("Id: " + "sellingLog" + ", Date: " + new Date(2020 , 2 ,20) + ", Price: " + 50 + ", CustomerId: " + "customer");
        assertEquals(sample , sellerController.showSalesHistory(seller));
    }

    @Test
    public void showBuyersOfThisProduct() {
        try {
            sellerController.showBuyersOfThisProduct(user , "product");
        } catch (Exception e) {
            assertEquals( "You aren't a seller", e.getMessage());
        }
    }
    @Test
    public void showBuyersOfThisProduct2() {
        new MockUp<ProductController>(){
            @Mock
            public Product getProductById(String Id){
                return null;
            }
        };
        new Expectations(){
            {
                ProductController.getProductById("product");
            }
        };
        try {
            sellerController.showBuyersOfThisProduct(seller , "product");
        } catch (Exception e) {
            assertEquals( "This product doesn't Exist", e.getMessage());
        }
    }
    @Test
    public void showBuyersOfThisProduct3() {
        new MockUp<ProductController>(){
            @Mock
            public Product getProductById(String Id){
                return product;
            }
        };
        ArrayList<Product> products = new ArrayList<>();
        new Expectations(){
            {
                ProductController.getProductById("product");
                seller.getOnSaleProducts();result = products;
                ProductController.getProductById("product");
            }
        };
        try {
            sellerController.showBuyersOfThisProduct(seller , "product");
        } catch (Exception e) {
            assertEquals( "Seller does'nt have this product", e.getMessage());
        }
    }
    @Test
    public void showBuyersOfThisProduct4() {
        new MockUp<ProductController>(){
            @Mock
            public Product getProductById(String Id){
                return product;
            }
        };
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        ArrayList<SellingLog> sellingLogs = new ArrayList<>();
        sellingLogs.add(sellingLog);
        new Expectations(){
            {
                ProductController.getProductById("product");
                seller.getOnSaleProducts();result = products;
                ProductController.getProductById("product");
                seller.getAllSellingLogs();result = sellingLogs;
                sellingLog.getBuyingProducts();result = product;
                product.getProductId();result = "product";
                sellingLog.getCustomer();result = customer;
                customer.getUsername();result = "customer";
            }
        };
        ArrayList<String> sample = new ArrayList<>();
        sample.add("customer");
        try {
            assertEquals(sample ,sellerController.showBuyersOfThisProduct(seller , "product") );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void removeProductFromUser() {
        try {
            sellerController.removeProductFromUser(user , "product");
        } catch (Exception e) {
            assertEquals( "You aren't seller", e.getMessage());
        }
    }
    @Test
    public void removeProductFromUser2() {
        new MockUp<ProductController>(){
            @Mock
            public Product getProductById(String Id){
                return null;
            }
        };
        new Expectations(){
            {
                ProductController.getProductById("product");
            }
        };
        try {
            sellerController.removeProductFromUser(seller , "product");
        } catch (Exception e) {
            assertEquals( "There is not this product", e.getMessage());
        }
    }
    @Test
    public void removeProductFromUser3() {
        new MockUp<ProductController>(){
            @Mock
            public Product getProductById(String Id){
                return product;
            }
        };
        ArrayList<Product> products = new ArrayList<>();
        new Expectations(){
            {
                ProductController.getProductById("product");
                seller.getOnSaleProducts();result = products;
            }
        };
        try {
            sellerController.removeProductFromUser(seller , "product");
        } catch (Exception e) {
            assertEquals( "Seller does'nt have this product", e.getMessage());
        }
    }
    @Test
    public void removeProductFromUser4() throws Exception {
        new MockUp<ProductController>(){
            @Mock
            public Product getProductById(String Id){
                return product;
            }
        };
        new MockUp<SellerController>(){
            @Mock
            public void removeProduct(String product){
            }
        };
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        HashMap<Seller , Integer> sellers = new HashMap<>();
        sellers.put(seller , 10);
        new Expectations(){
            {
                ProductController.getProductById("product");
                seller.getOnSaleProducts();result = products;
                product.getSellersOfThisProduct();result = sellers;
                sellerController.removeProduct("product");
            }
        };
        try {
            sellerController.removeProductFromUser(seller , "product");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void removeProductFromUser5() {
        new MockUp<ProductController>(){
            @Mock
            public Product getProductById(String Id){
                return product;
            }
        };
        new MockUp<SellerController>(){
            @Mock
            public void removeProduct(String product){
            }
        };
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        HashMap<Seller , Integer> sellers = new HashMap<>();
        sellers.put(seller , 10);
        sellers.put(seller2 , 20);
        new Expectations(){
            {
                ProductController.getProductById("product");
                seller.getOnSaleProducts();result = products;
                product.getSellersOfThisProduct();result = sellers;
                seller.removeProduct(product);
                product.removeSeller(seller);
            }
        };
        try {
            sellerController.removeProductFromUser(seller , "product");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void showProductsOfThisSeller() {
        ArrayList<Product> onSaleProducts = new ArrayList<>();
        onSaleProducts.add(product);
        HashMap<Seller , Integer> sellers = new HashMap<>();
        sellers.put(seller , 10);
        sellers.put(seller2 , 20);
        new Expectations(){
            {
                seller.getOnSaleProducts();result = onSaleProducts;
                product.getName();result = "product";
                product.getSellersOfThisProduct();result = sellers;
                product.getSumOfCustomersRate();result = 50;
                product.getCustomersWhoRated();result = 5;
                product.getProductStatus();result =ProductAndOffStatus.ACCEPTED;
            }
        };
        ArrayList<String> products = new ArrayList<>();
        products.add("name=" + "product" + ", price=" + 10 + ", rate=" + (1.0 * 10) + ", status=" + ProductAndOffStatus.ACCEPTED);
        assertEquals(products , sellerController.showProductsOfThisSeller(seller));
    }

    @Test
    public void getCategoryFeatures() {
        new MockUp<ManagerController>(){
            @Mock
            public Category getCategoryByName(String name){
                return null;
            }
        };
        new Expectations(){
            {
                ManagerController.getCategoryByName("category");
            }
        };
        try {
            sellerController.getCategoryFeatures("category");
        } catch (Exception e) {
            assertEquals( "Invalid categoryName" , e.getMessage());
        }
    }
    @Test
    public void getCategoryFeatures2() {
        new MockUp<ManagerController>(){
            @Mock
            public Category getCategoryByName(String name){
                return category;
            }
        };
        ArrayList<String> specialProperties = new ArrayList<>();
        specialProperties.add("very well");
        new Expectations(){
            {
                ManagerController.getCategoryByName("category");
                category.getSpecialProperties();result = specialProperties;
                ManagerController.getCategoryByName("category");
            }
        };
        ArrayList<String> sample = new ArrayList<>();
        sample.add("very well");
        try {
            assertEquals(sample , sellerController.getCategoryFeatures("category"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addProduct() {
        new MockUp<ManagerController>(){
            @Mock
            public Category getCategoryByName(String name){
                return null;
            }
        };
        HashMap<String, String> specialInformationRelatedToCategory = new HashMap<>();
        String[] information = new String[4];
        information[2] = "20";
        information[3] = "category";
        try {
            sellerController.addProduct(information , seller , specialInformationRelatedToCategory);
        } catch (Exception e) {
            assertEquals( "invalid categoryName" , e.getMessage());
        }
    }
    @Test
    public void addProduct2() {
        new MockUp<ManagerController>(){
            @Mock
            public Category getCategoryByName(String name){
                return category;
            }
        };
        HashMap<Seller , Integer> sellers = new HashMap<>();
        sellers.put(seller , 20);
        HashMap<String, String> specialInformationRelatedToCategory = new HashMap<>();

        new Expectations(){
            {
                Product.getNumberOfProductCreated();result = 2;
                new Product("Product3", "", "", category, "", 30, sellers,specialInformationRelatedToCategory );
                product.setMinimumPrice(20);
                Request.getNumberOfRequestCreated();result = 5;
                new ProductRequest("ProductRequest" + 6, product, false);
                seller.addProduct(product);times = 1;
            }
        };
        String[] information = new String[7];
        information[0] = "";
        information[1] = "";
        information[4] = "";
        information[5] = "30";
        information[6] = "";
        information[2] = "20";
        information[3] = "category";
        try {
            sellerController.addProduct(information , seller , specialInformationRelatedToCategory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void editProduct() {
        new MockUp<ProductController>(){
            @Mock
            public Product getProductById(String Id){
                return null;
            }
        };
        HashMap<String, String> specialInformationRelatedToCategory = new HashMap<>();
        specialInformationRelatedToCategory.put("good" , "perfect");
        new Expectations(){
            {

            }
        };
        try {
            sellerController.editProduct(seller , "product" , 10 , 200 , "is good" , specialInformationRelatedToCategory);
        } catch (Exception e) {
            assertEquals( "There is'nt this Product", e.getMessage());
        }
    }
    @Test
    public void editProduct2() {
        new MockUp<ProductController>(){
            @Mock
            public Product getProductById(String Id){
                return product;
            }
        };
        HashMap<String, String> specialInformationRelatedToCategory = new HashMap<>();
        specialInformationRelatedToCategory.put("good" , "perfect");
        HashMap<Seller , Integer> sellers = new HashMap<>();
        new Expectations(){
            {
                product.getSellersOfThisProduct();result = sellers;
            }
        };
        try {
            sellerController.editProduct(seller , "product" , 10 , 200 , "is good" , specialInformationRelatedToCategory);
        } catch (Exception e) {
            assertEquals( "Seller does'nt have this product", e.getMessage());
        }
    }
    @Test
    public void editProduct3() {
        new MockUp<ProductController>(){
            @Mock
            public Product getProductById(String Id){
                return product;
            }
        };
        HashMap<String, String> specialInformationRelatedToCategory = new HashMap<>();
        specialInformationRelatedToCategory.put("good" , "perfect");
        HashMap<Seller , Integer> sellers = new HashMap<>();
        sellers.put(seller , 10);
        new Expectations(){
            {
                product.getSellersOfThisProduct();result = sellers;
                product.setProductStatus(ProductAndOffStatus.EDITING);
                Request.getNumberOfRequestCreated();result = 9;
                new ProductRequest("ProductRequest" + 10, product, true).newProductFeatures(seller , 10 , 200 , "is good" , specialInformationRelatedToCategory);times = 1;
            }
        };
        try {
            sellerController.editProduct(seller , "product" , 10 , 200 , "is good" , specialInformationRelatedToCategory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void removeProduct() {
        new MockUp<ProductController>(){
            @Mock
            public Product getProductById(String Id){
                return null;
            }
        };
        try {
            sellerController.removeProduct("product");
        } catch (Exception e) {
            assertEquals("There is not product with this id" , e.getMessage());
        }
    }
    @Test
    public void removeProduct2() {
        new MockUp<ProductController>(){
            @Mock
            public Product getProductById(String Id){
                return product;
            }
        };
        HashMap<Seller , Integer> sellers = new HashMap<>();
        sellers.put(seller , 10);
        new Expectations(){
            {
                product.getSellersOfThisProduct();result = sellers;
                seller.removeProduct(product);
                product.removeProduct();
                product.getCategory();result = category;
                category.removeProduct(product);
            }
        };
        try {
            sellerController.removeProduct("product");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void showAllOffs() {
        new MockUp<SellerController>(){
            @Mock
            public void checkTimeOfOffs(){
            }
        };
        ArrayList<Off> offArrayList = new ArrayList<>();
        offArrayList.add(off);
        new Expectations(){
            {
                seller.getSellerOffs();result = offArrayList;
                off.getOffId();result = "off";
                off.getBeginTime();result = new Date(2000, 2, 20);
                off.getEndTime();result = new Date(2020 , 2 ,20);
                off.getOffPercent();result = 12;
                off.getOffStatus();result = ProductAndOffStatus.CREATING;
            }
        };
        ArrayList<String> sample = new ArrayList<>();
        sample.add("id: " + "off" + ", beginTime: " + new Date(2000, 2, 20) + ",endTime: " + new Date(2020 , 2 ,20) + ", offPercent: " + 12 + ", status: " + ProductAndOffStatus.CREATING);
        assertEquals(sample.toArray() , sellerController.showAllOffs(seller));
    }

    @Test
    public void showOff() {
        new MockUp<SellerController>(){
            @Mock
            public Off getOffById(String name){
                return null;
            }
        };
        try {
            sellerController.showOff(seller , "off");
        } catch (Exception e) {
            assertEquals("Id is invalid" , e.getMessage());
        }
    }
    @Test
    public void showOff2() {
        new MockUp<SellerController>(){
            @Mock
            public Off getOffById(String name){
                return off;
            }
        };
        new Expectations(){
            {
                off.getSeller();result = seller2;
            }
        };
        try {
            sellerController.showOff(seller , "off");
        } catch (Exception e) {
            assertEquals("Seller doesn't have this off" , e.getMessage());
        }
    }
    @Test
    public void showOff3() {
        new MockUp<SellerController>(){
            @Mock
            public Off getOffById(String name){
                return off;
            }
        };
        new Expectations(){
            {
                off.getSeller();result = seller;
                off.getOffStatus();result = ProductAndOffStatus.CREATING;
                off.getBeginTime();result = new Date(2000 , 2 ,20);
                off.getEndTime();result = new Date(2020 , 2 ,20);
                off.getOffPercent();result = 80;
                off.getOnSaleProducts();result = product;
                product.getProductId();result = "product";
                product.getName();result = "mobile";
            }
        };
        ArrayList<String> information = new ArrayList<>();
        information.add(String.valueOf(ProductAndOffStatus.CREATING));
        information.add(String.valueOf(new Date(2000 , 2 ,20)));
        information.add(String.valueOf(new Date(2020 , 2 ,20)));
        information.add(String.valueOf(80));
        information.add("[id: " + "product" + ", name: " + "mobile]");
        try {
            assertEquals(information.toArray() ,sellerController.showOff(seller , "off") );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void addOff() {
        new MockUp<SellerController>(){
            @Mock
            public void checkTimeOfOffs(){
            }
        };
        new MockUp<ProductController>(){
            @Mock
            public Product getProductById(String name){
                return null;
            }
        };
        ArrayList<String> productsId = new ArrayList<>();
        productsId.add("product");
        productsId.add("product2");
        try {
            sellerController.addOff(seller ,productsId , new Date(2000 , 2, 20) , new Date(2020 , 2 ,20) , 30);
        } catch (Exception e) {
            assertEquals( "some products doesn't exist", e.getMessage());
        }
    }
    @Test
    public void addOf2() {
        new MockUp<SellerController>(){
            @Mock
            public void checkTimeOfOffs(){
            }
        };
        new MockUp<ProductController>(){
            @Mock
            public Product getProductById(String name){
                return product;
            }
        };
        HashMap<Seller , Integer> sellers = new HashMap<>();
        new Expectations(){
            {
                product.getSellersOfThisProduct();result = sellers;
            }
        };
        ArrayList<String> productsId = new ArrayList<>();
        productsId.add("product");
        try {
            sellerController.addOff(seller ,productsId , new Date(2000 , 2, 20) , new Date(2020 , 2 ,20) , 30);
        } catch (Exception e) {
            assertEquals( "seller doesn't have some products", e.getMessage());
        }
    }
    @Test
    public void addOf3() {
        new MockUp<SellerController>(){
            @Mock
            public void checkTimeOfOffs(){
            }
        };
        new MockUp<ProductController>(){
            @Mock
            public Product getProductById(String name){
                return product;
            }
        };
        HashMap<Seller , Integer> sellers = new HashMap<>();
        sellers.put(seller , 10);
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        new Expectations(){
            {
                product.getSellersOfThisProduct();result = sellers;
                Off.getNumberOfOffsCreated();result = 7;
                Off sample = new Off(seller, "Off" + 8, new Date(2000 , 2, 20), new Date(2020 , 2 ,20), 30, products);
                Request.getNumberOfRequestCreated();result = 9;
                new OffRequest("OffRequest" + 10, off, false);
                seller.addOffToThisSeller(sample);
            }
        };
        ArrayList<String> productsId = new ArrayList<>();
        productsId.add("product");
        try {
            sellerController.addOff(seller ,productsId , new Date(2000 , 2, 20) , new Date(2020 , 2 ,20) , 30);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkTimeOfOffs() {
        new MockUp<Off>(){
            @Mock
            public ArrayList<Off> getAllOffs(){
                ArrayList<Off> offs = new ArrayList<>();
                offs.add(off);
                return offs;
            }
        };
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        ArrayList<Off> offs = new ArrayList<>();
        offs.add(off);
        new Expectations(){
            {
                off.getEndTime();result = new Date();
                off.getOffStatus();result = ProductAndOffStatus.ACCEPTED;
                off.removeOff();
                off.getSeller();result = seller;
                seller.removeOffFromThisSeller(off);
                off.getOnSaleProducts();result = products;
                product.getOffs();result = offs;
                product.removeOff(off);

            }
        };
        sellerController.checkTimeOfOffs();
    }
    @Test
    public void checkTimeOfOffs2() {
        new MockUp<Off>(){
            @Mock
            public ArrayList<Off> getAllOffs(){
                ArrayList<Off> offs = new ArrayList<>();
                offs.add(off);
                return offs;
            }
        };
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        ArrayList<Off> offs = new ArrayList<>();
        offs.add(off);
        new Expectations(){
            {
                off.getEndTime();result = new Date(2000 , 2 ,20);
                off.getBeginTime();result = new Date();
                off.getOffStatus();result = ProductAndOffStatus.ACCEPTED;
                off.getOnSaleProducts();result = products;
                product.getOffs();result = offs;
                off.getSeller();result = seller;times = 2;
            }
        };
        sellerController.checkTimeOfOffs();
    }

    @Test
    public void editOff() {
        new MockUp<Object>(){
            //@Mock
        };
        new Expectations(){
            {

            }
        };
    }

    @Test
    public void getOffById() {
        new MockUp<Object>(){
            //@Mock
        };
        new Expectations(){
            {

            }
        };
    }

    @Test
    public void showBalanceOfSeller() {
        new MockUp<Object>(){
            //@Mock
        };
        new Expectations(){
            {

            }
        };
    }

    @Test
    public void getOffProducts() {
        new MockUp<Object>(){
            //@Mock
        };
        new Expectations(){
            {

            }
        };
    }
}