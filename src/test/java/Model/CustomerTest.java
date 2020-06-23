package Model;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
@RunWith(JMockit.class)
public class CustomerTest {
    Customer customer = new Customer("hamed" , "kaka" , "mamad" , "hamedrashid2222@gmail.com" , "09183445678" , "1234");
    @Injectable
    BuyingLog buyingLog;
    @Injectable
    Product product;
    @Injectable
    DiscountCode discountCode;
    @Test
    public void getAllDiscountCodes() {
        ArrayList<DiscountCode> sample = new ArrayList<>();
        assertEquals(sample , customer.getAllDiscountCodes());
    }

    @Test
    public void addBuyingLog() {
        new Expectations(){
            {
                customer.getAllBuyingLogs().add(buyingLog);
            }
        };
        customer.addBuyingLog(buyingLog);
    }

    @Test
    public void addRecentShoppingProducts() {
        Set<Product> products = new HashSet<>();
        products.add(product);
        new Expectations(){
            {
                customer.getRecentShoppingProducts().add(product);
            }
        };
        customer.addRecentShoppingProducts(products);
    }

    @Test
    public void getAllBuyingLogs() {

        ArrayList<BuyingLog> buyingLogs = new ArrayList<>();
        assertEquals(buyingLogs , customer.getAllBuyingLogs());
    }

    @Test
    public void getRecentShoppingProducts() {
        ArrayList<Product> products = new ArrayList<>();
        assertEquals(products , customer.getRecentShoppingProducts());
    }

    @Test
    public void deleteCustomer() {
        new Expectations(){
            {
                Customer.getAllCustomers().remove(customer);
            }
        };
        customer.deleteCustomer();
    }

    @Test
    public void addDiscountCode() {
        new Expectations(){
            {
                customer.getAllDiscountCodes().add(discountCode);
            }
        };
        customer.addDiscountCode(discountCode);
    }

    @Test
    public void removeDiscountCode() {
        new Expectations(){
            {
                customer.getAllDiscountCodes().remove(discountCode);
            }
        };
        customer.removeDiscountCode(discountCode);
    }

    @Test
    public void logToFile() {
    }

    @Test
    public void fileToLog() {
    }
}