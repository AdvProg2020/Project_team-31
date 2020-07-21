package GraphicalView;

public class ManageBuyingLogsModel {
    String id;
    String address;
    String buyer;

    public ManageBuyingLogsModel(String id, String address, String buyer) {
        this.id = id;
        this.address = address;
        this.buyer = buyer;
    }

    public String getBuyer() {
        return buyer;
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }
}
