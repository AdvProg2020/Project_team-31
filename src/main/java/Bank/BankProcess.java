package Bank;

import com.google.gson.JsonObject;

import java.util.Date;

public class BankProcess {
    private static BankProcess bankProcess;
    private static final int marketAccountID = 1;

    public static BankProcess getInstance() {
        if (bankProcess == null)
            bankProcess = new BankProcess();
        return bankProcess;
    }

    private BankProcess() {
    }

    public String answer(String command) {
        String answer = null;
        if (command.startsWith("create_account"))
            answer = createAccount(command);
        else if (command.startsWith("get_token"))
            answer = getToken(command);
        else if (command.startsWith("create_receipt"))
            answer = createReceipt(command);
        else if (command.startsWith("get_transactions"))
            answer = getTransactions(command);
        else if (command.startsWith("pay"))
            answer = pay(command);
        else if (command.startsWith("get_balance"))
            answer = getBalance(command);
        return answer;
    }

    private String createAccount(String command) {
        String[] data = command.split(" ");
        Account preAccount = Account.getAccountByUsername(data[3]);
        if (preAccount != null)
            return "username is not available";
        if (!data[4].equals(data[5]))
            return "password do not match";
        Account account = new Account(data[1], data[2], data[3], data[4]);
        return String.valueOf(account.accountNumber);
    }

    private String getToken(String command) {
        String[] data = command.split(" ");
        if (!Account.userPassMatch(data[1], data[2]))
            return "invalid username or password";
        Account account = Account.getAccountByUsername(data[1]);
        account.token = createToken();
        account.tokenDate = new Date();
        return account.token;
    }

    private String createToken() {
        int n = 30;
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    private String createReceipt(String command) {
        String[] data = command.split(" ");
        if (data[2].equals("deposit"))       //variz
            return deposit(data);
        else if (data[2].equals("withdraw")) //bardasht
            return withdraw(data);
        else if (data[2].equals("move"))     //enteghal
            return move(data);
        else return "invalid receipt type";
    }

    private String deposit(String[] data) {
        if (data.length != 7)
            return "invalid parameters passed";
        if (!data[3].matches("\\d+") || Integer.parseInt(data[3]) <= 0)
            return "invalid money";
        if (data[5].equals("-1"))
            return "invalid account id";
        Account to = Account.getAccountByNumber(Integer.parseInt(data[5]));
        if (to == null)
            return "dest account id is invalid";
        if (!to.token.equals(data[1]))
            return "token is invalid";
        if (to.validityOfToken())
            return "token expired";
        if (data[4].equals(data[5]))
            return "equal source and dest account";
        if (!data[4].equals("-1"))
            return "source account id is invalid";
        if (!data[6].matches("\"[a-zA-Z0-9]*\""))
            return "your input contains invalid characters";
        Receipt receipt = new Receipt("deposit", Integer.parseInt(data[3]), Integer.parseInt(data[4]), Integer.parseInt(data[5]), data[6]);
        return String.valueOf(receipt.id);
    }

    private String withdraw(String[] data) {
        if (data.length != 7)
            return "invalid parameters passed";
        if (!data[3].matches("\\d+") || Integer.parseInt(data[3]) <= 0)
            return "invalid money";
        if (data[4].equals("-1"))
            return "invalid account id";
        Account from = Account.getAccountByNumber(Integer.parseInt(data[4]));
        if (from == null)
            return "source account id is invalid";
        if (!from.token.equals(data[1]))
            return "token is invalid";
        if (from.validityOfToken())
            return "token expired";
        if (!data[5].equals("-1"))
            return "dest account id is invalid";
        if (data[4].equals(data[5]))
            return "equal source and dest account";
        if (!data[6].matches("\"[a-zA-Z0-9]*\""))
            return "your input contains invalid characters";
        Receipt receipt = new Receipt("withdraw", Integer.parseInt(data[3]), Integer.parseInt(data[4]), Integer.parseInt(data[5]), data[6]);
        return String.valueOf(receipt.id);
    }

    private String move(String[] data) {
        if (data.length != 7)
            return "invalid parameters passed";
        if (!data[3].matches("\\d+") || Integer.parseInt(data[3]) <= 0)
            return "invalid money";
        if (data[5].equals("-1") || data[4].equals("-1"))
            return "invalid account id";
        Account from = Account.getAccountByNumber(Integer.parseInt(data[4]));
        if (from == null)
            return "source account id is invalid";
        if (!from.token.equals(data[1]))
            return "token is invalid";
        if (from.validityOfToken())
            return "token expired";
        Account to = Account.getAccountByNumber(Integer.parseInt(data[5]));
        if (to == null)
            return "dest account id is invalid";
        if (from.accountNumber == to.accountNumber)
            return "equal source and dest account";
        if (!data[6].matches("\"[a-zA-Z0-9]*\""))
            return "your input contains invalid characters";
        Receipt receipt = new Receipt("move", Integer.parseInt(data[3]), Integer.parseInt(data[4]), Integer.parseInt(data[5]), data[6]);
        return String.valueOf(receipt.id);
    }

    private String getTransactions(String command) {
        String[] data = command.split(" ");
        Account account = Account.getAccountByToken(data[1]);
        if (account == null)
            return "token is invalid";
        if (account.validityOfToken())
            return "token expired";
        if (data[2].equals("+"))
            return increaseHistory(account);
        if (data[2].equals("-"))
            return decreaseHistory(account);
        if (data[2].equals("*"))
            return allHistory(account);
        Receipt receipt = Receipt.getReceiptById(Integer.parseInt(data[2]));
        if (receipt == null)
            return "invalid receipt id";
        if (receipt.destId != account.accountNumber && receipt.sourceId != account.accountNumber)
            return "invalid receipt id";
        return receiptInfo(receipt);
    }

    private String increaseHistory(Account account) {
        StringBuilder output = new StringBuilder();
        for (Receipt receipt : Receipt.allReceipts) {
            if (receipt.destId == account.accountNumber) {
                output.append(receiptInfo(receipt)).append("*");
            }
        }
        return output.toString();
    }

    private String decreaseHistory(Account account) {
        StringBuilder output = new StringBuilder();
        for (Receipt receipt : Receipt.allReceipts) {
            if (receipt.sourceId == account.accountNumber) {
                output.append(receiptInfo(receipt)).append("*");
            }
        }
        return output.toString();
    }

    private String allHistory(Account account) {
        StringBuilder output = new StringBuilder();
        output.append(decreaseHistory(account)).append(increaseHistory(account));
        return output.toString();
    }

    private String receiptInfo(Receipt receipt) {
        JsonObject output = new JsonObject();
        output.addProperty("receiptType", receipt.type);
        output.addProperty("money", receipt.money);
        output.addProperty("sourceAccountID", receipt.sourceId);
        output.addProperty("destAccountID", receipt.destId);
        output.addProperty("description", receipt.description);
        output.addProperty("id", receipt.id);
        output.addProperty("paid", receipt.paid);
        return output.toString();
    }

    private String pay(String command) {
        String[] data = command.split(" ");
        Receipt receipt = Receipt.getReceiptById(Integer.parseInt(data[1]));
        if (receipt == null)
            return "invalid receipt id";
        if (receipt.paid == 1)
            return "receipt is paid before";
        int sourceId = receipt.sourceId;
        if (sourceId == -1) {
            sourceId = marketAccountID;
        }
        int destId = receipt.destId;
        if (destId == -1) {
            destId = marketAccountID;
        }
        Account from = Account.getAccountByNumber(sourceId);
        Account to = Account.getAccountByNumber(destId);
        if (from == null || to == null || from.equals(to))
            return "invalid account id";
        if(from.inventory < receipt.money)
            return "source account does not have enough money";
        from.inventory -= receipt.money;
        to.inventory += receipt.money;
        receipt.paid = 1;
        return "done successfully";
    }

    private String getBalance(String command) {
        String[] data = command.split(" ");
        Account account = Account.getAccountByToken(data[1]);
        if (account == null)
            return "token is invalid";
        if (account.validityOfToken())
            return "token expired";
        return String.valueOf(account.inventory);

    }
}
