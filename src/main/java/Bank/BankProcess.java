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
        String answer;
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
    }

    private String createAccount(String command) {
String[] data=command.split(" ");
if ()
    }

    private String getToken(String command) {
        String[] data = command.split(" ");
        if (!Account.userPassMatch(data[1], data[2]))
            return "invalid username or password";
        Token.deleteTokenOfUser(data[1]);
        Token.allToken.add(new Token(createToken(), data[1], new Date()));
        return createToken();
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

    }

    private String getTransactions(String command) {

    }

    private String pay(String command) {

    }

    private String getBalance(String command) {

    }
}
