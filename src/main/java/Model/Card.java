package Model;

import java.io.Serializable;
import java.util.HashMap;

public class Card implements Serializable {
    private HashMap<Product, ProductInCard> productsInThisCard;

    public Card() {
        this.productsInThisCard = new HashMap<>();
    }

    public void addProductToCard(ProductInCard productInCard) {
        productsInThisCard.put(productInCard.getProduct(), productInCard);
    }

    public HashMap<Product, ProductInCard> getProductsInThisCard() {
        return productsInThisCard;
    }

    public void removeProductFromCard(Product product) {
        productsInThisCard.remove(product);
    }
}
