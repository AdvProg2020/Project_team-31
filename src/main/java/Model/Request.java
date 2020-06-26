package Model;

import javafx.fxml.Initializable;

import java.io.*;
import java.util.ArrayList;

public abstract class Request implements Serializable {
    protected String requestId;
    protected static ArrayList<Request> allRequests = new ArrayList<>();

    public Request(String requestId) {
        this.requestId = requestId;
        allRequests.add(this);
    }

    public static int getNumberOfRequestCreated() {
        if(allRequests.size() == 0) {
            return 0;
        }
        return Integer.parseInt(allRequests.get(allRequests.size()-1).getRequestId().substring(7));
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