package Bank;

import java.util.ArrayList;

public class Receipt {
    String type;
    int money;
    int sourceId;
    int destId;
    String description;
    int id;
    int paid;
    static ArrayList<Receipt> allReceipts = new ArrayList<>();

    public Receipt(String type, int money, int sourceId, int destId, String description) {
        this.type = type;
        this.money = money;
        this.sourceId = sourceId;
        this.destId = destId;
        this.description = description;
        this.paid = 0;
        id = (allReceipts.size() + 1);
        allReceipts.add(this);
    }

//    public static boolean isThereAnyReceipt(String id) {
//        allReceipts
//    }

    public static Receipt getReceiptById(int id) {
        for (Receipt receipt : allReceipts) {
            if(receipt.id == id) {
                return receipt;
            }
        }
        return null;
    }
}
