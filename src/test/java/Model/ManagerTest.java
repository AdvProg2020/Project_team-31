package Model;

import mockit.Expectations;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
@RunWith(JMockit.class)
public class ManagerTest {

    Manager manager = new Manager("hamed" , "kaka" , "mamad" , "hamedhamed2222@gmail.com" , "09182345678" , "12345");
    @Test
    public void deleteManager() {
        new Expectations(){
            {
                Manager.getAllManagers().remove(manager);
            }
        };
        manager.deleteManager();
    }

    @Test
    public void getAllManagers() {
    }

    @Test
    public void logToFile() {
    }

    @Test
    public void fileToLog() {
    }
}