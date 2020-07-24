package Controller;

import Model.Auction;
import Model.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;

public class SaveAndLoadFiles {
    public static void start() {
        User.fileToLog();
        Customer.fileToLog();
        Manager.fileToLog();
        Seller.fileToLog();
        Category.fileToLog();
        DiscountCode.fileToLog();
        Off.fileToLog();
        Product.fileToLog();
        Request.fileToLog();
        Supporter.fileToLog();
        Auction.fileToLog();
        try {
            FileInputStream file = new FileInputStream("src/project files/information.txt");
            DataInputStream in = new DataInputStream(file);
            String input = in.readUTF();
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(input);
            Manager.minInventory = jsonObject.get("min").getAsInt();
            Manager.wagePercent = jsonObject.get("percent").getAsInt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void end() {
        User.logToFile();
        Customer.logToFile();
        Seller.logToFile();
        Manager.logToFile();
        Category.logToFile();
        DiscountCode.logToFile();
        Off.logToFile();
        Product.logToFile();
        Request.logToFile();
        Supporter.logToFile();
        Auction.logToFile();
        try {
            FileOutputStream file = new FileOutputStream("src/project files/information.txt");
            DataOutputStream out = new DataOutputStream(file);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("min", Manager.minInventory);
            jsonObject.addProperty("percent", Manager.wagePercent);
            out.writeUTF(jsonObject.toString());
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
