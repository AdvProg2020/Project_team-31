package Model;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.junit.Assert.*;
@RunWith(JMockit.class)
public class UserTest {
    User user = new User("hamed" , "kaka" , "mamad" , "hamedrashid3456@gmail.com" , "09125968743" , "1234" );
    @Injectable
    Card card;
    @Test
    public void getAndAddFilters() {
        user.addFilter("salam" , "hamed");
        HashMap<String ,String> filters = new HashMap<>();
        filters.put("salam" , "hamed");
        assertEquals(filters , user.getFilters());
    }

    @Test
    public void removeFilter() {
        user.addFilter("salam" , "hamed");
        HashMap<String ,String> filters = new HashMap<>();
        user.removeFilter("salam");
        assertEquals(filters , user.getFilters());
    }

    @Test
    public void getUsername() {
        assertEquals("mamad" , user.getUsername());
    }

    @Test
    public void getPassword() {
        assertEquals("1234" , user.getPassword());
    }

    @Test
    public void getPersonalInformation() {
        String[] arrayOfInformation = new String[7];
        arrayOfInformation[0] = "hamed";
        arrayOfInformation[1] = "kaka";
        arrayOfInformation[2] = "mamad";
        arrayOfInformation[3] = "hamedrashid3456@gmail.com";
        arrayOfInformation[4] = "09125968743";
        arrayOfInformation[5] = "1234";
        arrayOfInformation[6] = String.valueOf(0);
        assertEquals(arrayOfInformation , user.getPersonalInformation());
    }

    @Test
    public void setNewInformation() {
        String[] arrayOfInformation = new String[7];
        arrayOfInformation[0] = "ali";
        arrayOfInformation[1] = "kaka";
        arrayOfInformation[2] = "mamad";
        arrayOfInformation[3] = "kakamamadhamed@gmail.com";
        arrayOfInformation[4] = "09184563212";
        arrayOfInformation[5] = "15432";
        arrayOfInformation[6] = String.valueOf(0);
        user.setNewInformation("ali" , "kaka" , "kakamamadhamed@gmail.com" , "09184563212" ,"15432");
        assertEquals(arrayOfInformation , user.getPersonalInformation());
    }

    @Test
    public void getAndSetCard() {
        user.setCard(card);
        assertEquals(card , user.getCard());
    }

    @Test
    public void getCreditAndGetMoney() {
        user.getMoney(34);
        assertEquals(34 , user.getCredit());

    }

    @Test
    public void payMoney() {
        user.getMoney(30);
        user.payMoney(20);
        assertEquals(10 , user.getCredit());
    }

    @Test
    public void logToFile() {
    }

    @Test
    public void fileToLog() {
    }
}