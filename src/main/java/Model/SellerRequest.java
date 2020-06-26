package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class SellerRequest extends Request implements Serializable {
    private String username;
    private String[] information;

    public SellerRequest(String id, String username, String[] information) {
        super(id);
        this.information = information;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String[] getInformation() {
        return information;
    }
}