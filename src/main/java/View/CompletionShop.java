package View;

import Model.BuyingLog;

public class CompletionShop extends Menu {
    public static CompletionShop instance = null;

    private CompletionShop() {
        super();
    }

    public static CompletionShop getInstance() {
        if (instance == null)
            instance = new CompletionShop();
        return instance;
    }

    @Override
    public void run() {
        BuyingLog buyingLog;
        try {
            buyingLog = getInformation();
            loginMassage();
            discountCode(buyingLog);
            loginMassage();
            payment(buyingLog);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void loginMassage() {
        if (user == null) {
            String command;
            System.out.println("do you want to log in?(yes/no)");
            while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("no")) {
                if (command.equalsIgnoreCase("yes"))
                    loginAndLogOut(true);
                else System.out.println("invalid command");
            }
        }
    }

    private BuyingLog getInformation() throws Exception {
        String[] data = new String[2];
        System.out.println("please enter your address : ");
        data[0] = scanner.nextLine().trim();
        if (data[0].equalsIgnoreCase("back"))
            throw new Exception("canceled!");
        System.out.println("please enter your phone number : ");
        data[1] = scanner.nextLine().trim();
        if (data[1].equalsIgnoreCase("back"))
            throw new Exception("canceled!");
        return customerController.createBuyingLog(user, data);
    }

    private void discountCode(BuyingLog buyingLog) throws Exception {
        System.out.println("please enter your discount code : ");
        String discountCode = scanner.nextLine().trim();
        if (discountCode.equalsIgnoreCase("back"))
            throw new Exception("canceled!");
        try {
            customerController.putDiscount(user, buyingLog, discountCode);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void payment(BuyingLog buyingLog) throws Exception {
        System.out.println("are you sure about your purchase? (yes/no)");
        String command;
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            if (command.equalsIgnoreCase("no"))
                throw new Exception("canceled!");
            else if (command.equalsIgnoreCase("yes")) {
                customerController.payMoney(user, buyingLog);
                return;
            }
        }


    }
}
