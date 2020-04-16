package Model;

import java.util.ArrayList;
import java.util.Date;

public class OffRequest extends Request {
    private Off off;
    private static ArrayList<OffRequest> allOffRequests = new ArrayList<>();

    public OffRequest(Off off) {
        super("offRequest" + allRequests.size()+1);
        this.off = off;
        allOffRequests.add(this);
    }

    @Override
    public void acceptRequest() {

    }

    @Override
    public void declineRequest() {

    }

    @Override
    public String showDetail() {
        return null;
    }
}
