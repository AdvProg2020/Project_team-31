package Model;

import java.util.ArrayList;

public class SellerRequest extends Request {
    private String username;
    private String[] information;

    public SellerRequest(String id, String username, String[] information) {
        super(id);
        this.information = information;
        this.username = username;
        allRequests.add(this);
    }

    @Override
    public String showDetail() {
        return "request to register a seller with username: " + username + " ,name: " + information[0] + " ,lastName: " + information[1];
    }

    public String getUsername() {
        return username;
    }

    public String[] getInformation() {
        return information;
    }
}