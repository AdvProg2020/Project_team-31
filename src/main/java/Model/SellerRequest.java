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

    public String getUsername() {
        return username;
    }

    public String[] getInformation() {
        return information;
    }
}