package View;

import Controller.ManagerController;
import Controller.ProductController;
import Model.Product;

import java.util.*;
import java.util.regex.Matcher;

public class ProductMenu extends Menu {
    public static ProductMenu instance = null;
    ShowProductMenu showProductMenu = ShowProductMenu.getInstance();
    String category = null;
    String sort = null;

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
        category = null;
        String command;
        sort = null;
        while ((command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            Matcher matcher = getMatcher("^(?i)show\\s+product\\s+(\\S+)$", command);
            if (getMatcher("^(?i)view\\s+categories$", command).find())
                viewAllCategories();
            else if (getMatcher("^(?i)filtering$", command).find())
                filtering();
            else if (getMatcher("^(?i)sorting$", command).find())
                sorting();
            else if (getMatcher("^(?i)help$", command).find())
                productMenuHelp();
            else if (getMatcher("^(?i)show\\s+products$", command).find())
                showProducts();
            else if (matcher.find())
                showProduct(matcher.group(1));
            else System.out.println("invalid command");

        }
    }

    private void productMenuHelp() {
        System.out.println("///////////////////////help////////////////////");
        System.out.println("show peoduct [productId]");
        System.out.println("view categories");
        System.out.println("filtering");
        System.out.println("sorting");
        System.out.println("show products");
        System.out.println("login");
        System.out.println("logout");
        System.out.println("help");
        System.out.println("back");
        System.out.println("///////////////////////help////////////////////");
    }


    public void resetSort() {
        sort = null;
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

    public void filtering() {
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
        viewAllCategories();
        System.out.println("please select your category  : (-1 for escape)");
        while ((command = scanner.nextLine().trim()).equalsIgnoreCase("-1")) {
            if (ManagerController.getCategoryByName(command) != null) {
                availableFilters = productController.showAvailableFiltersForUser(user, null);
                category = command;
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
        System.out.println("please enter you filter value\n" +
                "(for range filter use format [number-number] for example [2-100]");
        String filterValue = scanner.nextLine().trim();
        try {
            productController.addFilterForUser(user, filter, filterValue);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void showCurrentFilters() {
        HashMap<String, String> filters = productController.ShowCurrentFilters(user);
        System.out.println("current filters : ");
        for (Map.Entry<String, String> entry : filters.entrySet()) {
            System.out.println(entry.getKey() + "      " + entry.getValue());
        }

    }

    private void disableFilter(String filter) {
        try {
            productController.disableFilterForUser(user, filter);
            System.out.println("disabled successfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void sorting() {
        String command;
        while ((command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            Matcher sort = getMatcher("^(?i)sort\\s+(\\S+)$", command);
            if (sort.find())
                sort(sort.group(1));
            else if (getMatcher("^(?i)show\\s+available\\s+sorts", command).find())
                showAvailableSorts();
            else if (getMatcher("^(?i)current\\s+sort", command).find())
                showCurrentSort();
            else if (getMatcher("^(?i)disable\\s+filter$", command).find())
                disableSort();
            else System.out.println("invalid command");
        }
    }

    private void showAvailableSorts() {
        System.out.println("available sorts : \"price\"     \"rate\"    \"view\"");
    }

    private void sort(String newSort) {
        if (newSort.equals("price") || newSort.equals("rate") || newSort.equals("view")) {
            sort = newSort;
        } else System.out.println("invalid sort");

    }

    private void showCurrentSort() {
        System.out.println("your current sort is : " + sort);
    }

    private void disableSort() {
        sort = null;
    }

    private void showProducts() {
        ArrayList<String> products = null;
        try {
            products = productController.showProducts(user, category, sort);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (products == null) {
            System.out.println("there is nothing to show!");
            return;
        }
        for (String product : products) {
            System.out.println(product);
        }
    }

    public void showProduct(String productId) {
        Product product = ProductController.getProductById(productId);
        if (product == null) {
            System.out.println("there is not any product with this ID!");
        } else {
            ShowProductMenu.getInstance().setProduct(product);
            ShowProductMenu.getInstance().run();
        }
    }

}
