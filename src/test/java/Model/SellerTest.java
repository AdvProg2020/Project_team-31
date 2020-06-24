package Model;

import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import mockit.Injectable;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SellerTest {
    Seller seller = new Seller("hamed" ,"kaka" , "mamad" , "hamedrashid3456@gmail.com" , "09123456789" , "1234" ,"AlicaKeys" );
    @Injectable
    Off off;
    @Injectable
    Off off2;
    @Injectable
    SellingLog sellingLog;
    @Injectable
    Product product;
    @Test
    public void getAndSetSellerOffs() {
        ArrayList<Off> offs = new ArrayList<>();
        offs.add(off);
        seller.setSellerOffs(offs);
        assertEquals(offs , seller.getSellerOffs());
    }

    @Test
    public void addOffToThisSeller() {
        ArrayList<Off> offs = new ArrayList<>();
        offs.add(off);
        seller.addOffToThisSeller(off);
        assertEquals(offs , seller.getSellerOffs());
    }

    @Test
    public void removeOffFromThisSeller() {
        ArrayList<Off> offs = new ArrayList<>();
        offs.add(off);
        seller.setSellerOffs(offs);
        seller.removeOffFromThisSeller(off);
        assertEquals(new ArrayList<Off>() , seller.getSellerOffs());
    }

    @Test
    public void setAndGetCompanyName() {
        seller.setCompanyName("bia bekhar");
        assertEquals("bia bekhar" , seller.getCompanyName());
    }

    @Test
    public void addAndGetSellingLog() {
        ArrayList<SellingLog> sellingLogs = new ArrayList<>();
        sellingLogs.add(sellingLog);
        seller.addSellingLog(sellingLog);
        assertEquals(sellingLogs , seller.getAllSellingLogs());
    }

    @Test
    public void removeAndGetAndAddProduct() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        seller.addProduct(product);
        assertEquals(products , seller.getOnSaleProducts());
        seller.removeProduct(product);
        assertEquals(new ArrayList<Product>() , seller.getOnSaleProducts());
    }

    @Test
    public void getCompanyName() {
        assertEquals("AlicaKeys" , seller.getCompanyName());
    }

    @Test
    public void logToFile() {
    }

    @Test
    public void fileToLog() {
    }
}