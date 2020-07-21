package GraphicalView;

public class ManageBuyingLogsModel {
    String id;
    String address;

    public ManageBuyingLogsModel(String id, String address) {
        this.id = id;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }
}
