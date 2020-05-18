package View;

import java.util.ArrayList;
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
        productMenu.resetValues();
        showOffProducts();
        String command;
        while (!(command = scanner.nextLine().trim().trim()).equalsIgnoreCase("back")) {
            Matcher matcher = getMatcher("^(?i)show\\s+product\\s+(\\S+)$", command);
            if (safeGetMatcher("^(?i)filtering$", command).find())
                filtering();
            else if (safeGetMatcher("^(?i)sorting$", command).find())
                sorting();
            else if (matcher.find())
                showProduct(matcher.group(1));
            else if (safeGetMatcher("^(?i)help$", command).find())
                offHelp();
            else if (!command.equalsIgnoreCase("login") && !command.equalsIgnoreCase("logout"))
                System.out.println("invalid command");

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
        productMenu.sorting();
    }

    private void showOffProducts() {
        try {
            ArrayList<String> products = productController.showOffProduct(user, productMenu.sort);
            for (String product : products) {
                System.out.println(product);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void showProduct(String productId) {
        productMenu.showProduct(productId);
    }

}
