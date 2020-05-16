package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DiscountCode {
    private static ArrayList<DiscountCode> allDiscountCodes = new ArrayList<>();
    private String discountCode;
    private Date beginTime;
    private Date endTime;
    private int discountPercent;
    private int maximumDiscount;
    private HashMap<Customer , Integer> discountTimesForEachCustomer;

    public DiscountCode(String discountCode) {
        this.discountCode = discountCode;
        allDiscountCodes.add(this);
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
        return discountTimesForEachCustomer;
    }

    public void decreaseDiscountTimesForEachCustomer(Customer customer) {
        int i = discountTimesForEachCustomer.get(customer);
        discountTimesForEachCustomer.remove(customer);
        discountTimesForEachCustomer.put(customer,i-1);
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
        this.discountTimesForEachCustomer = discountTimesForEachCustomer;
    }
}
