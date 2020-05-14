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
    private OffStatus offStatus;

    public Off(String offId, Date beginTime, Date endTime, Double offAmount, ArrayList<Product> products) {
        this.offId = offId;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.offAmount = offAmount;
        this.offStatus = creating;
        onSaleProducts = products;
    }

    public void acceptedStatus() {
        this.offStatus = accepted;
    }

    public void editingStatus(){this.offStatus = editing;}
}
enum OffStatus {
    accepted, creating, editing
}