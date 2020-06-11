package Model;

import java.io.*;
import java.util.ArrayList;

public abstract class Request implements Serializable {
    protected String requestId;
    protected static ArrayList<Request> allRequests = new ArrayList<>();
    private static int numberOfRequestCreated = 0;

    public Request(String requestId) {
        numberOfRequestCreated ++;
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
    public static void logToFile() {
        try{
            FileOutputStream file = new FileOutputStream("src/project files/allRequests.txt");
            ObjectOutputStream allRequests = new ObjectOutputStream(file);

            allRequests.writeObject(getAllRequest());
            allRequests.flush();
            allRequests.close();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void fileToLog(){
        try{
            FileInputStream file = new FileInputStream("src/project files/allRequests.txt");
            ObjectInputStream allRequests = new ObjectInputStream(file);

            Request.allRequests = (ArrayList<Request>) allRequests.readObject();
            allRequests.close();
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}