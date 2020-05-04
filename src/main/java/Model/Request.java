package Model;

import java.util.ArrayList;

public abstract class Request {
    protected String requestId;
    protected static ArrayList<Request> allRequests = new ArrayList<>();

    public Request(String requestId) {
        this.requestId = requestId;
        allRequests.add(this);
    }

    public static ArrayList<Request> getAllRequest() {
        return allRequests;
    }

    public String showDetail() {
        return null;
    }

    public void deleteRequest() {
        allRequests.remove(this);
    }

    public String getRequestId() {
        return requestId;
    }
}