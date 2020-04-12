package Model;

import java.util.HashMap;

public class DiscountCode {
    private String discountCode;
    private int beginTime;
    private int endTime;
    private Double discountPercent;
    private Double maximumDiscount;
    private HashMap<Customer , Integer> discountTimesForEachCustomer;

    public DiscountCode(String discountCode, int beginTime, int endTime, Customer customer ) {
        this.discountCode = discountCode;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }
}
