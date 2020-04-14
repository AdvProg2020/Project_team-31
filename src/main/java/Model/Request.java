package Model;

import java.util.ArrayList;

public abstract class Request {
    private String requestId;
    private static ArrayList<Request> allRequests = new ArrayList<>();

    public Request(String requestId) {
        this.requestId = requestId;
        allRequests.add(this);
    }

    public static ArrayList<Request> getAllRequest() {
        return allRequests;
    }

    public void acceptRequest() {}

    public void declineRequest(){}

    public String showDetail() {
        return null;
    }

    public void deleteRequest() {
        allRequests.remove(this);
    }

}
