package View;

public class OffMenu extends Menu {
    public static OffMenu instance = null;

    private OffMenu() {
        super();
    }

    public static OffMenu getInstance() {
        if (instance == null)
            instance = new OffMenu();
        return instance;
    }

    @Override
    public void run() {
        showOffProducts();
        String command;
        while (true) {
            command = scanner.nextLine().trim();
            if (getMatcher("^(?i)filtering$", command).find())
                filtering();
            else if (getMatcher("^(?i)sorting$", command).find())
                sorting();
            else if (getMatcher("^(?i)show\\s+product\\s+(\\S+)$", command).find())
                showProduct();
            else if (getMatcher("^(?i)login$", command).find())
                loginAndLogOut(true);
            else if (getMatcher("^(?i)logout$", command).find())
                loginAndLogOut(false);
            else if (getMatcher("^(?i)back$", command).find())
                break;
            else System.out.println("invalid command");

        }
    }

    private void filtering() {
    }

    private void sorting() {
    }

    private void showOffProducts() {

    }

    private void showProduct() {
    }

}
