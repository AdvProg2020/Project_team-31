package Model;

import java.util.Date;

public class Log {
    protected String logId;
    protected Date date;
    protected Customer customer;
    protected DeliveryStatus deliveryStatus;

    public Log(String logId, Date date, Customer customer, DeliveryStatus deliveryStatus) {
        this.logId = logId;
        this.date = date;
        this.customer = customer;
        this.deliveryStatus = deliveryStatus;
    }

    public Date getDate() {
        return date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getLogId() {
        return logId;
    }
}
enum DeliveryStatus{

}
