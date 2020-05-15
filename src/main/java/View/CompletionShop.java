package View;

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
        try {
            getInformation();
            discountCode();
            payment();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void getInformation() {
        String[] data=new String[2];
        System.out.println("please enter your address : ");
        data[0]=scanner.nextLine();
        System.out.println("please enter your phone number : ");
        data[1]=scanner.nextLine();
        customerController.createBuyingLog(user,data);
    }

    private void discountCode() {

    }

    private void payment() {

    }
}
