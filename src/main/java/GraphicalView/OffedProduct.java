package GraphicalView;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Date;

public class OffedProduct {
    private ProductInTable product;
    private String name;
    private int view;
    private int price;
    private String  rate;
    private String time;
    private int percent;

    public OffedProduct(ProductInTable product, String endTime, int price, int percent) {
        this.product = product;
        name = product.getName();
        view = product.getViews();
        this.price = price;
        this.percent = percent;
        rate = product.getRate();
        time = endTime;
    }

    public ProductInTable getProduct() {
        return product;
    }

    public String getName() {
        return name;
    }

    public int getView() {
        return view;
    }

    public int getPrice() {
        return price;
    }

    public String getRate() {
        return rate;
    }

    public String getTime() {
        return time;
    }

    public int getPercent() {
        return percent;
    }
}
