package Bank;

import java.util.Date;

public class BankProcess {
    private static BankProcess bankProcess;

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
        if (!Account.freeUsername(data[3]))
            return "username is not available";
        if (!data[4].equals(data[5]))
            return "password do not match";
        new Account(data[1], data[2], data[3], data[4], Account.totalAccountNumber++);
        return String.valueOf(Account.totalAccountNumber);
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
        else if (data[2].equals("withdraw")) //baadasht
            return withdraw(data);
        else if (data[2].equals("move"))     //enteghal
            return move(data);
        else return "invalid receipt type";
    }

    private String deposit(String[] data) {
        if (!data[3].matches("\\d+") || Integer.parseInt(data[3]) <= 0)
            return "invalid money";
        if (data.length != 7)
            return "invalid parameters passed";
        Account to = Account.getAccountByNumber(Integer.parseInt(data[5]));
        if (to != null && !to.token.equals(data[1]))
            return "token is invalid";
        if (to != null && !to.validityOfToken())
            return "token expired";
        if (to == null)
            return "dest account id is invalid";
        if (data[4].equals(data[5]))
            return "equal source and dest account";
        if (!data[5].equals("-1"))
            return "invalid account id";
        if (!data[6].matches("\"[a-zA-Z0-9]*\""))
            return "your input contains invalid characters";
        new Receipt("deposit", Integer.parseInt(data[3]), Integer.parseInt(data[4]), Integer.parseInt(data[5]), data[6]);
        return String.valueOf(Receipt.receiptID);
    }

    private String withdraw(String[] data) {
        if (!data[3].matches("\\d+") || Integer.parseInt(data[3]) <= 0)
            return "invalid money";
        if (data.length != 7)
            return "invalid parameters passed";
        Account from = Account.getAccountByNumber(Integer.parseInt(data[4]));
        if (from != null && !from.token.equals(data[1]))
            return "token is invalid";
        if (from != null && !from.validityOfToken())
            return "token expired";
        if (from == null)
            return "source account id is invalid";
        if (!data[5].equals("-1"))
            return "dest account id is invalid";
        if (data[4].equals(data[5]))
            return "equal source and dest account";
        if (!data[6].matches("\"[a-zA-Z0-9]*\""))
            return "your input contains invalid characters";
        new Receipt("withdraw", Integer.parseInt(data[3]), Integer.parseInt(data[4]), Integer.parseInt(data[5]), data[6]);
        return String.valueOf(Receipt.receiptID);
    }

    private String move(String[] data) {
        if (!data[3].matches("\\d+") || Integer.parseInt(data[3]) <= 0)
            return "invalid money";
        if (data.length != 7)
            return "invalid parameters passed";
        Account from = Account.getAccountByNumber(Integer.parseInt(data[4]));
        if (from != null && !from.token.equals(data[1]))
            return "token is invalid";
        if (from != null && !from.validityOfToken())
            return "token expired";
        if (from == null)
            return "source account id is invalid";
        Account to = Account.getAccountByNumber(Integer.parseInt(data[5]));
        if (to == null)
            return "dest account id is invalid";
        if (from.accountNumber == to.accountNumber)
            return "equal source and dest account";
        if (data[5].equals("-1") || data[4].equals("-1")) //useless condition
            return "invalid account id";
        if (!data[6].matches("\"[a-zA-Z0-9]*\""))
            return "your input contains invalid characters";
        new Receipt("move", Integer.parseInt(data[3]), Integer.parseInt(data[4]), Integer.parseInt(data[5]), data[6]);
        return String.valueOf(Receipt.receiptID);
    }

    private String getTransactions(String command) {
        String[] data = command.split(" ");
        if (!Account.getAccountByToken(data[1]).validityOfToken())
            return "token expired";
        if (data[2].equals("+"))
            return increaseHistory(data);
        if (data[2].equals("-"))
            return decreaseHistory(data);
        if (data[2].equals("*"))
            return allHistory(data);
        else Receipt.isThereAnyReceipt(data[2]);
    }

    private String increaseHistory(String[] data) {
    }

    private String decreaseHistory(String[] data) {
    }

    private String allHistory(String[] data) {
    }

    private String pay(String command) {

    }

    private String getBalance(String command) {

    }
}
