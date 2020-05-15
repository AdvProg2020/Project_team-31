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

    }

    private void discountCode() {

    }

    private void payment() {

    }
}
