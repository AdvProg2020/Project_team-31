package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Auction {
    private String id;
    private String seller;
    private String productId;
    private ArrayList<String> messages = new ArrayList<>();
    private HashMap<String, Integer> offeredPrices = new HashMap<>();
    private Date beginTime;
    private Date endTime;
    private String status;    //created, started, finished
    public static ArrayList<Auction> allAuctions = new ArrayList<>();

    public Auction(String seller, String productId, Date beginTime, Date endTime) {
        this.seller = seller;
        status = "created";
        this.productId = productId;
        this.beginTime = beginTime;
        this.endTime = endTime;
        id = "Auction" + (getNumberOfAuctionCreated() + 1);
        allAuctions.add(this);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public static int getNumberOfAuctionCreated() {
        if (allAuctions.size() == 0)
            return 0;
        return Integer.parseInt(allAuctions.get(allAuctions.size() - 1).getId().substring(7));
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

    public static void logToFile() {
        try {
            FileOutputStream file = new FileOutputStream("src/project files/allAuctions.txt");
            ObjectOutputStream all = new ObjectOutputStream(file);

            all.writeObject(allAuctions);
            all.flush();
            all.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fileToLog() {
        try {
            FileInputStream file = new FileInputStream("src/project files/allAuctions.txt");
            ObjectInputStream all = new ObjectInputStream(file);

            allAuctions = (ArrayList<Auction>) all.readObject();
            all.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
