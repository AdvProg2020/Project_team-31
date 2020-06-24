package Model;

import com.sun.xml.internal.ws.api.model.JavaMethod;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;
@RunWith(JMockit.class)
public class OffRequestTest {
    @Injectable
    Off off;
    @Injectable
    Product product;
    @Test
    public void getOff() {
        OffRequest offRequest = new OffRequest("offRequest" , off , true);
        assertEquals(off , offRequest.getOff());
    }

    @Test
    public void setOff() {
        OffRequest offRequest = new OffRequest("offRequest" , off , true);
        new Expectations(){
            {
               offRequest.getEndTime();
               offRequest.getEndTime();
               offRequest.getOffPercent();
               offRequest.getOnSaleProducts();
            }
        };
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        offRequest.setOff(new Date(2000, 2 , 20) , new Date(2020 , 2 , 20) , 10 ,products );
    }

    @Test
    public void getBeginTime() {
        OffRequest offRequest = new OffRequest("offRequest" , off , true);
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        offRequest.setOff(new Date(2000, 2 , 20) , new Date(2020 , 2 , 20) , 10 ,products );
        assertEquals(new Date(2000 , 2 ,20 ) , offRequest.getBeginTime());
    }

    @Test
    public void getEndTime() {
        OffRequest offRequest = new OffRequest("offRequest" , off , true);
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        offRequest.setOff(new Date(2000, 2 , 20) , new Date(2020 , 2 , 20) , 10 ,products );
        assertEquals(new Date(2020 , 2 , 20), offRequest.getEndTime());


    }

    @Test
    public void getOffPercent() {
        OffRequest offRequest = new OffRequest("offRequest" , off , true);
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        offRequest.setOff(new Date(2000, 2 , 20) , new Date(2020 , 2 , 20) , 10 ,products );
        assertEquals(10 , offRequest.getOffPercent());
    }

    @Test
    public void getOnSaleProducts() {
        OffRequest offRequest = new OffRequest("offRequest" , off , true);
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        offRequest.setOff(new Date(2000, 2 , 20) , new Date(2020 , 2 , 20) , 10 ,products );
        assertEquals(products , offRequest.getOnSaleProducts());
    }

    @Test
    public void getIsEditing() {
        OffRequest offRequest = new OffRequest("offRequest" , off , true);
        assertEquals(true , offRequest.getIsEditing());
    }
}