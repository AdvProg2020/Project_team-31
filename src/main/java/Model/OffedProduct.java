package Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Date;

public class OffedProduct {
    private Product product;
    private Image image;
    private String name;
    private int view;
    private int price;
    private String  rate;
    private Date time;
    private int percent;

    public OffedProduct(Product product,Date endTime, int price, int percent) {
        this.product = product;
        image = product.getImage();
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

    public ImageView getImageViewSmall() {
        ImageView imageView = new ImageView(image);
        if (product.getAvailable() == 0)
            imageView.setOpacity(0.5);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        return imageView;
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

    public Date getTime() {
        return time;
    }

    public int getPercent() {
        return percent;
    }
}
