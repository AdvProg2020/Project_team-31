package Controller;

import Model.*;

public class SaveAndLoadFiles {
    public void start() {
        User.fileToLog();
        Customer.fileToLog();
        Manager.fileToLog();
        Seller.fileToLog();
        Category.fileToLog();
        DiscountCode.fileToLog();
        Off.fileToLog();
        Product.fileToLog();
        Request.fileToLog();
    }

    public void end() throws Exception{
        User.logToFile();
        Customer.logToFile();
        Seller.logToFile();
        Manager.logToFile();
        Category.logToFile();
        DiscountCode.logToFile();
        Off.logToFile();
        Product.logToFile();
        Request.logToFile();
    }
}
