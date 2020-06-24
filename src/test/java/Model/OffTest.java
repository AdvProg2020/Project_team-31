package Model;

import com.sun.prism.shader.Solid_RadialGradient_REFLECT_AlphaTest_Loader;
import mockit.Injectable;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class OffTest {
    @Injectable
    Seller seller;
    @Injectable
    Product product;
    @Test
    public void getNumberOfOffsCreated() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        Off off = new Off( seller , "offId" , new Date(2000, 2 ,20 ) , new Date(2020 , 2 ,20 ) , 10 , products);

    }

    @Test
    public void getBeginTime() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        Off off = new Off( seller , "offId" , new Date(2000, 2 ,20 ) , new Date(2020 , 2 ,20 ) , 10 , products);

    }

    @Test
    public void getEndTime() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        Off off = new Off( seller , "offId" , new Date(2000, 2 ,20 ) , new Date(2020 , 2 ,20 ) , 10 , products);

    }

    @Test
    public void getOffPercent() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        Off off = new Off( seller , "offId" , new Date(2000, 2 ,20 ) , new Date(2020 , 2 ,20 ) , 10 , products);

    }

    @Test
    public void getOnSaleProducts() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        Off off = new Off( seller , "offId" , new Date(2000, 2 ,20 ) , new Date(2020 , 2 ,20 ) , 10 , products);

    }

    @Test
    public void removeOff() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        Off off = new Off( seller , "offId" , new Date(2000, 2 ,20 ) , new Date(2020 , 2 ,20 ) , 10 , products);

    }

    @Test
    public void getOffId() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        Off off = new Off( seller , "offId" , new Date(2000, 2 ,20 ) , new Date(2020 , 2 ,20 ) , 10 , products);

    }

    @Test
    public void getSeller() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        Off off = new Off( seller , "offId" , new Date(2000, 2 ,20 ) , new Date(2020 , 2 ,20 ) , 10 , products);

    }

    @Test
    public void getAllOffs() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        Off off = new Off( seller , "offId" , new Date(2000, 2 ,20 ) , new Date(2020 , 2 ,20 ) , 10 , products);

    }

    @Test
    public void getOffStatus() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        Off off = new Off( seller , "offId" , new Date(2000, 2 ,20 ) , new Date(2020 , 2 ,20 ) , 10 , products);

    }

    @Test
    public void setOffStatus() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        Off off = new Off( seller , "offId" , new Date(2000, 2 ,20 ) , new Date(2020 , 2 ,20 ) , 10 , products);

    }

    @Test
    public void setBeginTime() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        Off off = new Off( seller , "offId" , new Date(2000, 2 ,20 ) , new Date(2020 , 2 ,20 ) , 10 , products);

    }

    @Test
    public void setEndTime() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        Off off = new Off( seller , "offId" , new Date(2000, 2 ,20 ) , new Date(2020 , 2 ,20 ) , 10 , products);

    }

    @Test
    public void setOffPercent() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        Off off = new Off( seller , "offId" , new Date(2000, 2 ,20 ) , new Date(2020 , 2 ,20 ) , 10 , products);

    }

    @Test
    public void setOnSaleProducts() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        Off off = new Off( seller , "offId" , new Date(2000, 2 ,20 ) , new Date(2020 , 2 ,20 ) , 10 , products);

    }

    @Test
    public void logToFile() {

    }

    @Test
    public void fileToLog() {
    }
}