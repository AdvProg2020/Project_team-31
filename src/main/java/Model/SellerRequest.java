package Model;

public class SellerRequest extends Request {
    private Seller seller;
    private SellerReguestCooment sellerReguestCooment;

    public SellerRequest(Manager manager, Seller seller, SellerReguestCooment sellerReguestCooment) {
        super(manager);
        this.seller = seller;
        this.sellerReguestCooment = sellerReguestCooment;
    }
}
enum SellerReguestCooment{

}