package Model;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

public class ModelTest {

    @Test
    public void buyingLogTest(){
        ArrayList<Product> buying = new ArrayList<Product>();
        ArrayList<String> specialProperties = new ArrayList<String>();
        specialProperties.add("length");
        specialProperties.add("price");
        buying.add(new Product("1234" , "pants" , "poma",  2000. , new Category("Dress" ,specialProperties ) , true , ProductStatus.creating , "this is perfect" , new Seller("mmd" , "cheragi" , "kaka" , "mmdkaka2000@gmail.com" , 23456789 , "salam man be to yar ghadimi" , "poma")));
        BuyingLog buyingLog = new BuyingLog( "1234_salam", new Date(),1000.0 , 200.0 , "Pink" , DeliveryStatus.sending , new Customer("hamed" , "Rashidpour" ,"Mr.Pink" , "hamedrashid@gmail.com" , 12345467 , "pinkis18" ) , buying  );
        System.out.println(buyingLog);
    }
}
