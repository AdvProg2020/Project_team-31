package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DiscountCode {
    public static ArrayList<DiscountCode> allDiscountCodes = new ArrayList<>();
    private String discountCode;
    private Date beginTime;
    private Date endTime;
    private Double discountPercent;
    private Double maximumDiscount;
    private HashMap<Customer , Integer> discountTimesForEachCustomer;

    public DiscountCode(String discountCode, Date beginTime, Date endTime, Customer customer , Double discountPercent , double maximumDiscount ) {
        this.discountCode = discountCode;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.discountPercent = discountPercent;
        this.maximumDiscount = maximumDiscount;
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

    public Double getDiscountPercent() {
        return discountPercent;
    }

    public Double getMaximumDiscount() {
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
}
