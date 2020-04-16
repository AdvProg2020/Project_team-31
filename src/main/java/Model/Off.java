package Model;

import java.util.ArrayList;
import java.util.Date;

public class Off {
    private String offId;
    private Date beginTime;
    private Date endTime;
    private Double offAmount;
    private ArrayList<Product> onSaleProduct;
    private OffStatus offStatus;

    public Off(String offId, Date beginTime, Date endTime, Double offAmount, ArrayList<Product> products) {
        this.offId = offId;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.offAmount = offAmount;
        this.offStatus = OffStatus.creating;
        onSaleProduct = products;
    }

}
enum OffStatus {
    accepted, creating, editing

}