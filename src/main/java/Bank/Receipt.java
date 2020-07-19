package Bank;

import java.util.ArrayList;

public class Receipt {
    String type;
    int money;
    int sourceId;
    int destId;
    String description;
    int id;
    static ArrayList<Receipt> allReceipts = new ArrayList<>();
    static int receiptID = 1;
    static ArrayList<Receipt> finished = new ArrayList<>();

    public Receipt(String type, int money, int sourceId, int destId, String description) {
        this.type = type;
        this.money = money;
        this.sourceId = sourceId;
        this.destId = destId;
        this.description = description;
        id = receiptID++;
        allReceipts.add(this);
    }

//    public static boolean isThereAnyReceipt(String id) {
//        allReceipts
//    }
}
