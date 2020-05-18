package View;

import java.util.regex.Matcher;

public class OffMenu extends Menu {
    public static OffMenu instance = null;
    public static ProductMenu productMenu = ProductMenu.getInstance();

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
        while ((command = scanner.nextLine().trim().trim()).equalsIgnoreCase("back")) {
            Matcher matcher = getMatcher("^(?i)show\\s+product\\s+(\\S+)$", command);
            if (getMatcher("^(?i)filtering$", command).find())
                filtering();
            else if (getMatcher("^(?i)sorting$", command).find())
                sorting();
            else if (matcher.find())
                showProduct(matcher.group(1));
            else if (getMatcher("^(?i)help$", command).find())
                offHelp();
            else System.out.println("invalid command");

        }
    }

    private void offHelp() {
        System.out.println("///////////////////////help////////////////////");
        System.out.println("show product [productId]");
        System.out.println("filtering");
        System.out.println("sorting");
        System.out.println("login");
        System.out.println("logout");
        System.out.println("help");
        System.out.println("back");
        System.out.println("///////////////////////help////////////////////");
    }

    private void filtering() {
        productMenu.filtering();
    }

    private void sorting() {
        productMenu.resetSort();
        productMenu.sorting();
    }

    private void showOffProducts() {
        try {
            productController.showOffProduct(user, productMenu.sort);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void showProduct(String productId) {
        productMenu.showProduct(productId);
    }

}
