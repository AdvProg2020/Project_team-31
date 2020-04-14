package Model;

import java.util.ArrayList;

public class SellerRequest extends Request {
    private String username;
    private String[] information;
    private static ArrayList<SellerRequest> allSellerRequest = new ArrayList<>();

    public SellerRequest(String username, String[] information) {
        super("sellerRequest" + Request.getAllRequest().size()+1);
        this.information = information;
        this.username = username;
        allSellerRequest.add(this);
    }

    @Override
    public void acceptRequest() {
        new Seller(information[0], information[1], username, information[2], information[3], information[4], information[5]);
        allSellerRequest.remove(this);
    }

    @Override
    public void declineRequest() {
        allSellerRequest.remove(this);
    }

    @Override
    public String showDetail() {
        return "request to register a seller with username: " + username + " ,name: " + information[0] + " ,lastName: " + information[1];
    }
}