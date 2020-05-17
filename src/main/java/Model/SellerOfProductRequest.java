package Model;

public class SellerOfProductRequest extends Request{
    private Seller seller;
    private Product product;
    private int price;

    public SellerOfProductRequest(String requestId, Seller seller, Product product, int price) {
        super(requestId);
        this.seller = seller;
        this.product = product;
        this.price = price;
    }

    public Seller getSeller() {
        return seller;
    }

    public Product getProduct() {
        return product;
    }

    public int getPrice() {
        return price;
    }
}
