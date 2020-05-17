package Model;

import java.util.ArrayList;

public abstract class Request {
    protected String requestId;
    protected static ArrayList<Request> allRequests = new ArrayList<>();
    private static int numberOfRequestCreated;

    public Request(String requestId) {
        numberOfRequestCreated++;
        this.requestId = requestId;
        allRequests.add(this);
    }

    public static int getNumberOfRequestCreated() {
        return numberOfRequestCreated;
    }

    public static ArrayList<Request> getAllRequest() {
        return allRequests;
    }

    public void deleteRequest() {
        allRequests.remove(this);
    }

    public String getRequestId() {
        return requestId;
    }
}