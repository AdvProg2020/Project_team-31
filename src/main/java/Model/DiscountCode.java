package Model;

import Controller.LoginController;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DiscountCode implements Serializable{
    private static ArrayList<DiscountCode> allDiscountCodes = new ArrayList<>();
    private String discountCode;
    private Date beginTime;
    private Date endTime;
    private int discountPercent;
    private int maximumDiscount;
    private HashMap<String , Integer> discountTimesForEachCustomer;

    public DiscountCode(String discountCode) {
        this.discountCode = discountCode;
        allDiscountCodes.add(this);
        discountTimesForEachCustomer = new HashMap<>();
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public int getMaximumDiscount() {
        return maximumDiscount;
    }

    public HashMap<Customer, Integer> getDiscountTimesForEachCustomer() {
        HashMap<Customer, Integer> output = new HashMap<>();
        for (String s : discountTimesForEachCustomer.keySet()) {
            output.put((Customer) LoginController.getUserByUsername(s), discountTimesForEachCustomer.get(s));
        }
        return output;
    }

    public void decreaseDiscountTimesForEachCustomer(Customer customer) {
        int i = discountTimesForEachCustomer.get(customer.getUsername());
        discountTimesForEachCustomer.remove(customer.getUsername());
        discountTimesForEachCustomer.put(customer.getUsername(),i-1);
    }

    public static ArrayList<DiscountCode> getAllDiscountCodes() {
        return allDiscountCodes;
    }

    public void removeDiscountCode(){
        allDiscountCodes.remove(this);
    }

    public void setDiscountCode(Date beginTime, Date endTime, int discountPercent, int maximumDiscount, HashMap<Customer, Integer> discountTimesForEachCustomer){
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.discountPercent = discountPercent;
        this.maximumDiscount = maximumDiscount;
        for (Customer customer : discountTimesForEachCustomer.keySet()) {
            this.discountTimesForEachCustomer.put(customer.getUsername(), discountTimesForEachCustomer.get(customer));
        }
    }
    public static void logToFile(){
        try{
            FileOutputStream file = new FileOutputStream("src/project files/allDiscountCodes.txt");
            ObjectOutputStream allDiscountCodes = new ObjectOutputStream(file);

            allDiscountCodes.writeObject(getAllDiscountCodes());
            allDiscountCodes.flush();
            allDiscountCodes.close();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void fileToLog(){
        try{
            FileInputStream file = new FileInputStream("src/project files/allDiscountCodes.txt");
            ObjectInputStream allDiscountCodes = new ObjectInputStream(file);

            DiscountCode.allDiscountCodes = (ArrayList<DiscountCode>) allDiscountCodes.readObject();
            allDiscountCodes.close();
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
