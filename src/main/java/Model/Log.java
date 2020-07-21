package Model;

import Controller.LoginController;

import java.io.Serializable;
import java.util.Date;

public class Log implements Serializable {
    protected String logId;
    protected Date date;
    protected String customerId;
    protected DeliveryStatus deliveryStatus;


    public Log(String logId, Date date, Customer customer) {
        this.logId = logId;
        this.date = date;
        customerId = customer.getUsername();
        this.deliveryStatus = DeliveryStatus.READY;
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

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }
}
enum DeliveryStatus{
    SENT, READY
}
