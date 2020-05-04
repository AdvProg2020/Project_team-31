package Model;

import java.util.ArrayList;
import java.util.Date;

public class OffRequest extends Request {
    private Off off;

    public OffRequest(Off off) {
        super("offRequest" + allRequests.size()+1);
        this.off = off;
        allRequests.add(this);
    }

    @Override
    public String showDetail() {
        return null;
    }

    public Off getOff() {
        return off;
    }
}
