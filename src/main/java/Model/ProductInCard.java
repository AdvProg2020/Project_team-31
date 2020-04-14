package Model;

public class ProductInCard {
    private Product product;
    private int number;
    private Seller seller;

    public ProductInCard(Product product, Seller seller) {
        this.product = product;
        this.seller = seller;
        number = 1;
    }

    public Product getProduct() {
        return product;
    }

    public void changeNumberOfProduct(int i) {
        number += i;
    }

    public int getNumber() {
        return number;
    }

    public Seller getSeller() {
        return seller;
    }
}
