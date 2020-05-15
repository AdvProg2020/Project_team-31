package Model;

import java.util.ArrayList;
import java.util.Date;

import static Model.OffStatus.*;

public class Off {
    private String offId;
    private Date beginTime;
    private Date endTime;
    private Double offAmount;
    private ArrayList<Product> onSaleProducts;
    private Seller seller;
    private OffStatus offStatus;
    private static ArrayList<Off> allOffs;

    public Off(Seller seller, String offId, Date beginTime, Date endTime, Double offAmount, ArrayList<Product> products) {
        this.seller = seller;
        this.offId = offId;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.offAmount = offAmount;
        this.offStatus = creating;
        onSaleProducts = products;
        allOffs.add(this);
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Double getOffAmount() {
        return offAmount;
    }

    public ArrayList<Product> getOnSaleProducts() {
        return onSaleProducts;
    }

    public void removeOff() {
        allOffs.remove(this);
    }

    public void acceptedStatus() {
        this.offStatus = accepted;
    }

    public void editingStatus(){this.offStatus = editing;}

    public String getOffId() {
        return offId;
    }

    public Seller getSeller() {
        return seller;
    }

    public static ArrayList<Off> getAllOffs() {
        return allOffs;
    }
}
enum OffStatus {
    accepted, creating, editing
}