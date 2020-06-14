package Model;

import Controller.CustomerController;
import Controller.ProductController;
import Controller.SellerController;

public class ProductInCartInGui {
    private String name;
    private int number;
    private int price;
    private int totalPrice;
    private Product product;

    public ProductInCartInGui(ProductInCard productInCard) {
        this.product = productInCard.getProduct();
        name = product.getName();
        number = productInCard.getNumber();
        int percent = 100;
        SellerController.getInstance().checkTimeOfOffs();
        for (Off off : product.getOffs()) {
            if(off.getSeller().equals(productInCard.getSeller())) {
                percent = 100 - off.getOffPercent();
            }
        }
        price = (product.getSellersOfThisProduct().get(productInCard.getSeller()) * (percent) /100);
        totalPrice = price * number;
    }

    public Product getProduct() {
        return product;
    }
}
