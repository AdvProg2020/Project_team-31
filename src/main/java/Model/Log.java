package Model;

import Controller.LoginController;

import java.io.Serializable;
import java.util.Date;

public class Log implements Serializable {
    protected String logId;
    protected Date date;
    protected String customerId;
    protected DeliveryStatus deliveryStatus;
    private static int numberOfLogCreated = 0;

    public Log(String logId, Date date, Customer customer) {
        numberOfLogCreated++;
        this.logId = logId;
        this.date = date;
        customerId = customer.getUsername();
        this.deliveryStatus = DeliveryStatus.ready;
    }

    public static int getNumberOfLogCreated() {
        return numberOfLogCreated;
    }

    public Date getDate() {
        return date;
    }

    public Customer getCustomer() {
        return (Customer) LoginController.getUserByUsername(customerId);
    }

    public String getLogId() {
        return logId;
    }
}
enum DeliveryStatus{
    sending, delivered, ready
}
