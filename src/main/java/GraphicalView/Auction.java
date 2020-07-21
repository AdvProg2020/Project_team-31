package GraphicalView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Auction {
    private String seller;
    private String productId;
    private ArrayList<String> messages = new ArrayList<>();
    private HashMap<String, Integer> offeredPrices = new HashMap<>();
    private Date beginTime;
    private Date endTime;
    public static ArrayList<Auction> allAuctions = new ArrayList<>();

    public Auction(String seller, String productId, Date beginTime, Date endTime) {
        this.seller = seller;
        this.productId = productId;
        this.beginTime = beginTime;
        this.endTime = endTime;
        allAuctions.add(this);
    }

    public String getSeller() {
        return seller;
    }

    public String getProductId() {
        return productId;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public HashMap<String, Integer> getOfferedPrices() {
        return offeredPrices;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void addMessage(String newMessage) {
        messages.add(newMessage);
    }

    public void addPrice(String customer, int price) {
        offeredPrices.put(customer, price);
    }
}
