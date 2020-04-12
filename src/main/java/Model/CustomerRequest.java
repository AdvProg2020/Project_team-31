package Model;

public class CustomerRequest extends Request {
    private Seller seller;
    private SellerRequestTitle sellerRequestTitle;

    public CustomerRequest(Manager manager, Seller seller, SellerRequestTitle sellerRequestTitle) {
        super(manager);
        this.seller = seller;
        this.sellerRequestTitle = sellerRequestTitle;
    }
}
enum SellerRequestTitle{

}