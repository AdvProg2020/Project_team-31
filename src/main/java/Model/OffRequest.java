package Model;

import java.util.ArrayList;
import java.util.Date;

public class OffRequest extends Request {
    private Off off;
    private Date beginTime;
    private Date endTime;
    private Double offAmount;
    private ArrayList<Product> onSaleProducts;
    private boolean isEditing;

    public OffRequest(Off off , boolean isEditing) {
        super("offRequest" + allRequests.size()+1);
        this.off = off;
        this.isEditing = isEditing;
        allRequests.add(this);
    }

    public Off getOff() {
        return off;
    }

    public void setOff(Date beginTime , Date endTime , Double offAmount , ArrayList<Product> onSaleProducts){
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.offAmount = offAmount;
        this.onSaleProducts = onSaleProducts;
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

    public boolean getIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean editing) {
        isEditing = editing;
    }
}
