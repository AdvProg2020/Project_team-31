package Model;

import Controller.LoginController;
import Controller.ProductController;

public class ProductInCard {
    private String productId;
    private int number;
    private String sellerId;

    public ProductInCard(Product product, Seller seller) {
        productId = product.getProductId();
        sellerId = seller.getUsername();
        number = 1;
    }

    public Product getProduct() {
        return ProductController.getProductById(productId);
    }

    public void changeNumberOfProduct(int i) {
        number += i;
    }

    public int getNumber() {
        return number;
    }

    public Seller getSeller() {
        return (Seller) LoginController.getUserByUsername(sellerId);
    }
}
