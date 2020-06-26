package Model;

import Controller.LoginController;
import Controller.ProductController;

import java.io.Serializable;

public class SellerOfProductRequest extends Request implements Serializable {
    private String seller;
    private String product;
    private int price;

    public SellerOfProductRequest(String requestId, Seller seller, Product product, int price) {
        super(requestId);
        this.seller = seller.getUsername();
        this.product = product.getProductId();
        this.price = price;
    }

    public Seller getSeller() {
        return (Seller) LoginController.getUserByUsername(seller);
    }

    public Product getProduct() {
        return ProductController.getProductById(product);
    }

    public int getPrice() {
        return price;
    }
}
