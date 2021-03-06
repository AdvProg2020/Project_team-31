package Model;

import java.util.Date;

public class Log {
    protected String logId;
    protected Date date;
    protected Customer customer;
    protected DeliveryStatus deliveryStatus;
    private static int numberOfLogCreated = 0;

    public Log(String logId, Date date, Customer customer) {
        numberOfLogCreated++;
        this.logId = logId;
        this.date = date;
        this.customer = customer;
        this.deliveryStatus = DeliveryStatus.ready;
    }

    public static int getNumberOfLogCreated() {
        return numberOfLogCreated;
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
    sending, delivered, ready
}
