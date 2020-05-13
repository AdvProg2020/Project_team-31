package Model;

import com.google.gson.Gson;

import java.util.HashMap;

public class Card {
    private HashMap<Product, ProductInCard> productsInThisCard;

    public Card() {
        productsInThisCard = new HashMap<>();
    }

    public void addProductToCard(ProductInCard productInCard) {
        productsInThisCard.put(productInCard.getProduct(), productInCard);
    }

    public HashMap<Product, ProductInCard> getProductsInThisCard() {
        return productsInThisCard;
    }
}
