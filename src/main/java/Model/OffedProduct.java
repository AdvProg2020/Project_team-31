package Model;

import javafx.scene.image.ImageView;

import java.util.Date;

public class OffedProduct {
    private Product product;
    private ImageView image;
    private String name;
    private int view;
    private int price;
    private String  rate;
    private Date time;
    private int percent;

    public OffedProduct(Product product,Date endTime, int price, int percent) {
        this.product = product;
        image = product.getImageViewSmall();
        name = product.getName();
        view = product.getViews();
        this.price = price;
        this.percent = percent;
        rate = product.getRate();
        time = endTime;
    }

    public Product getProduct() {
        return product;
    }
}
