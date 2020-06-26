package Model;

import Controller.ProductController;

import java.io.Serializable;
import java.util.HashMap;

public class Card implements Serializable {
    private HashMap<String, ProductInCard> productsInThisCard;

    public Card() {
        this.productsInThisCard = new HashMap<>();
    }

    public void addProductToCard(ProductInCard productInCard) {
        productsInThisCard.put(productInCard.getProduct().getProductId(), productInCard);
    }

    public HashMap<Product, ProductInCard> getProductsInThisCard() {
        HashMap<Product, ProductInCard> output = new HashMap<>();
        for (String s : productsInThisCard.keySet()) {
            output.put(ProductController.getProductById(s), productsInThisCard.get(s));
        }
        return output;
    }

    public void removeProductFromCard(Product product) {
        productsInThisCard.remove(product.getProductId());
    }
}
