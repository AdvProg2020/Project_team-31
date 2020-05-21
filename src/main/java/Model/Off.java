package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class Off implements Serializable {
    private String offId;
    private Date beginTime;
    private Date endTime;
    private int offPercent;
    private ArrayList<Product> onSaleProducts;
    private Seller seller;
    private ProductAndOffStatus offStatus;
    private static ArrayList<Off> allOffs = new ArrayList<>();
    private static int numberOfOffsCreated = 0;

    public Off(Seller seller, String offId, Date beginTime, Date endTime, int offAmount, ArrayList<Product> products) {
        numberOfOffsCreated++;
        this.seller = seller;
        this.offId = offId;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.offPercent = offAmount;
        this.offStatus = ProductAndOffStatus.CREATING;
        this.onSaleProducts = new ArrayList<>();
        this.onSaleProducts = products;
        allOffs.add(this);
    }

    public static int getNumberOfOffsCreated() {
        return numberOfOffsCreated;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public int getOffPercent() {
        return offPercent;
    }

    public ArrayList<Product> getOnSaleProducts() {
        return onSaleProducts;
    }

    public void removeOff() {
        allOffs.remove(this);
    }

    public String getOffId() {
        return offId;
    }

    public Seller getSeller() {
        return seller;
    }

    public static ArrayList<Off> getAllOffs() {
        return allOffs;
    }

    public ProductAndOffStatus getOffStatus() {
        return offStatus;
    }

    public void setOffStatus(ProductAndOffStatus offStatus) {
        this.offStatus = offStatus;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setOffPercent(int offAmount) {
        this.offPercent = offAmount;
    }

    public void setOnSaleProducts(ArrayList<Product> onSaleProducts) {
        this.onSaleProducts = onSaleProducts;
    }
    public static void logToFile(){
        try{
            FileOutputStream file = new FileOutputStream("F:\\universe\\AP\\project files\\allOffs.txt");
            ObjectOutputStream allOffs = new ObjectOutputStream(file);

            allOffs.writeObject(getAllOffs());
            allOffs.flush();
            allOffs.close();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void fileToLog(){
        try{
            FileInputStream file = new FileInputStream("F:\\universe\\AP\\project files\\allOffs.txt");
            ObjectInputStream allOffs = new ObjectInputStream(file);

            Off.allOffs = (ArrayList<Off>) allOffs.readObject();
            allOffs.close();
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
