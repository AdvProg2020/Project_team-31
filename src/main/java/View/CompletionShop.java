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
            discountCode(buyingLog);
            payment(buyingLog);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private BuyingLog getInformation() throws Exception {
        String[] data = new String[2];
        System.out.println("please enter your address : ");
        data[0] = scanner.nextLine();
        if(data[0].equalsIgnoreCase("back"))
            throw new Exception("cancel");
        System.out.println("please enter your phone number : ");
        data[1] = scanner.nextLine();
        if(data[0].equalsIgnoreCase("back"))
            throw new Exception("cancel");
        return customerController.createBuyingLog(user, data);
    }

    private void discountCode(BuyingLog buyingLog) {
        System.out.println("please enter your discount code : ");
        String discountCode = scanner.nextLine();
        try {
            customerController.putDiscount(user, buyingLog, discountCode);
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

    private void payment(BuyingLog buyingLog) {


    }
}
