package Model;

import Controller.ProductController;
import Controller.SellerController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class OffRequest extends Request implements Serializable {
    private String offId;
    private Date beginTime;
    private Date endTime;
    private int offPercent;
    private ArrayList<String> onSaleProducts;
    private boolean isEditing;

    public OffRequest(String id, Off off , boolean isEditing) {
        super(id);
        this.offId = off.getOffId();
        this.isEditing = isEditing;
    }

    public Off getOff() {
        return SellerController.getInstance().getOffById(offId);
    }

    public void setOff(Date beginTime , Date endTime , int offPercent , ArrayList<Product> onSaleProducts){
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.offPercent = offPercent;
        this.onSaleProducts = new ArrayList<>();
        for (Product product : onSaleProducts) {
            this.onSaleProducts.add(product.getProductId());
        }
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
        ArrayList<Product> output = new ArrayList<>();
        for (String onSaleProduct : onSaleProducts) {
            output.add(ProductController.getProductById(onSaleProduct));
        }
        return output;
    }

    public boolean getIsEditing() {
        return isEditing;
    }
}
