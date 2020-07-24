package Bank;

import java.io.*;
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


    public static Receipt getReceiptById(int id) {
        for (Receipt receipt : allReceipts) {
            if(receipt.id == id) {
                return receipt;
            }
        }
        return null;
    }

    public static void logToFile() {
        try {
            FileOutputStream file = new FileOutputStream("src/bank data/allReceipts.txt");
            ObjectOutputStream all = new ObjectOutputStream(file);
            all.writeObject(allReceipts);
            all.flush();
            all.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fileToLog() {
        try {
            FileInputStream file = new FileInputStream("src/bank data/allReceipts.txt");
            ObjectInputStream all = new ObjectInputStream(file);
            allReceipts = (ArrayList<Receipt>) all.readObject();
            all.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
