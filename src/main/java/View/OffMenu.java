package View;

import Controller.ProductController;
import Model.Manager;
import Model.Product;

import java.util.regex.Matcher;

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
        while ((command = scanner.nextLine().trim().trim()).equalsIgnoreCase("back")) {
            Matcher matcher = getMatcher("^(?i)show\\s+product\\s+(\\S+)$", command);
            if (getMatcher("^(?i)filtering$", command).find())
                filtering();
            else if (getMatcher("^(?i)sorting$", command).find())
                sorting();
            else if (matcher.find())
                showProduct(matcher.group(1));
            else if (getMatcher("^(?i)login$", command).find())
                loginAndLogOut(true);
            else if (getMatcher("^(?i)logout$", command).find())
                loginAndLogOut(false);
            else System.out.println("invalid command");

        }
    }

    private void filtering() {
    }

    private void sorting() {
    }

    private void showOffProducts() {
    }

    private void showProduct(String productId) {
        Product product = ProductController.getProductById(productId);
        if (product == null) {
            System.out.println("there is not any product with this ID!");
        } else {
            ShowProductMenu.getInstance().setProduct(product);
            ShowProductMenu.getInstance().run();
        }
    }

}
