package View;

import Controller.ManagerController;
import Controller.ProductController;
import Model.Product;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class ProductMenu extends Menu {
    public static ProductMenu instance = null;
    ShowProductMenu showProductMenu = ShowProductMenu.getInstance();

    private ProductMenu() {
        super();
    }

    public static ProductMenu getInstance() {
        if (instance == null)
            instance = new ProductMenu();
        return instance;
    }

    @Override
    public void run() {
        String command;
        while ((command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            Matcher matcher = getMatcher("^(?i)show\\s+product\\s+(\\S+)$", command);
            if (getMatcher("^(?i)view\\s+categories$", command).find())
                viewAllCategories();
            else if (getMatcher("^(?i)filtering$", command).find())
                filtering();
            else if (getMatcher("^(?i)sorting$", command).find())
                sorting();
            else if (getMatcher("^(?i)show\\s+products$", command).find())
                showAllProducts();
            else if (matcher.find())
                showProduct(matcher.group(1));
            else System.out.println("invalid command");

        }
    }

    private void viewAllCategories() {
        ArrayList<String> allCategories = managerController.showAllCategories();
        if (allCategories.size() == 0)
            System.out.println("there is not any categoty!");
        else {
            System.out.println("///////////////////////////////////////////");
            for (String category : allCategories)
                System.out.println(category);
            System.out.println("///////////////////////////////////////////");
        }
    }

    private void filtering() {
        String command;
        while ((command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            Matcher addFilter = getMatcher("^(?i)filter\\s+(\\S+)$", command);
            Matcher disableFilter = getMatcher("^(?i)disable\\s+filter\\s+(\\S+)$", command);
            if (getMatcher("^(?i)show\\s+available\\s+filters$", command).find())
                showAvailableFilters();
            else if (addFilter.find())
                addFilter(addFilter.group(1));
            else if (getMatcher("^(?i)current\\s+filters$", command).find())
                showCurrentFilters();
            else if (disableFilter.find())
                disableFilter(disableFilter.group(1));
            else System.out.println("invalid command");
        }
    }

    private void showAvailableFilters() {
        String command;
        ArrayList<String> availableFilters = null;
        System.out.println("please select your category  : (-1 for escape)");
        viewAllCategories();
        while ((command = scanner.nextLine().trim()).equalsIgnoreCase("-1")) {
            if (ManagerController.getCategoryByName(command) != null) {
                availableFilters = productController.showAvailableFiltersForUser(user, null);
                break;
            } else System.out.println("invalid category name!");
        }
        if (availableFilters == null)
            availableFilters = productController.showAvailableFiltersForUser(user, null);
        System.out.println("other filters : ");
        for (String filter : availableFilters) {
            System.out.println(filter);
        }
    }

    private void addFilter(String filter) {
    }

    private void showCurrentFilters() {

    }

    private void disableFilter(String filter) {

    }

    private void sorting() {
    }

    private void showAllProducts() {
    }

    private void showProduct(String productId) {
        Product product = ProductController.getProductById(productId);
        if (product == null) {
            System.out.println("there is not any product with this ID!");
        } else {
            showProductMenu.setProduct(product);
            showProductMenu.run();
        }
    }

}
