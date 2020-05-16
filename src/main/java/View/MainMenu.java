package View;

import Controller.*;
import Model.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;

public class MainMenu extends Menu {
    public static MainMenu instance = null;
    public static boolean end = false;

    public static MainMenu getInstance() {
        if (instance == null)
            instance = new MainMenu();
        return instance;
    }

    private MainMenu() {
        super();
    }

    @Override
    public void run() {
        while (!end) {
            if (user instanceof Seller)
                sellerMenu();
            else if (user instanceof Manager)
                managerMenu();
            else if (user instanceof Customer || user == null)
                customerMenu();
        }
    }

    private void sellerMenu() {
        String command;
        while (true) {
            command = scanner.nextLine().trim();
            Matcher matcher = getMatcher("^(?i)remove\\s+product\\s+(\\S+)$", command);
            if (getMatcher("^(?i)view\\s+personal\\s+info$", command).find())
                viewPersonalInformation();
            else if (getMatcher("^(?i)view\\s+company\\s+information$", command).find())
                viewCompanyInformation();
            else if (getMatcher("^(?i)view\\s+sales\\s+history$", command).find())
                viewSalesHistory();
            else if (getMatcher("^(?i)manage\\s+products$", command).find())
                manageProducts();
            else if (getMatcher("^(?i)add\\s+product$", command).find())
                addProducts();
            else if (matcher.find())
                removeProduct(matcher.group(1));
            else if (getMatcher("^(?i)show\\s+categories$", command).find())
                showCategories();
            else if (getMatcher("^(?i)view\\s+offs$", command).find())
                viewAllOffs();
            else if (getMatcher("^(?i)view\\s+balance\n$", command).find())
                viewBalance();
            else if (getMatcher("^(?i)products$", command).find())
                productMenu();
            else if (getMatcher("^(?i)offs$", command).find())
                System.out.println();
            else if (getMatcher("^(?i)end$", command).find())
                break;
            else System.out.println("invalid command");
        }
    }

    private void viewPersonalInformation() {
        String[] information = loginController.showPersonalInformation(user);
        System.out.println("first name : " + information[0] +
                "last name : " + information[1] +
                "username : " + information[2] +
                "email address : " + information[3] +
                "phone number : " + information[4] +
                "password : " + information[5] +
                "credit : " + information[6]);
        viewCompanyInformation();
        String command;
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            Matcher matcher = getMatcher("^(?i)edit\\s+(.+)$", command);
            if (matcher.find()) {
                int index = findIndex(matcher.group(1));
                if (index != 7 && index != 8) editPersonalInformation(index, information);
            } else System.out.println("invalid command");
        }
    }

    private int findIndex(String check) {
        if (check.equalsIgnoreCase("first name"))
            return 0;
        else if (check.equalsIgnoreCase("last name"))
            return 1;
        else if (check.equalsIgnoreCase("email address"))
            return 3;
        else if (check.equalsIgnoreCase("phone number"))
            return 4;
        else if (check.equalsIgnoreCase("password"))
            return 5;
        else if (check.equalsIgnoreCase("company name")) {
            if (user instanceof Seller)
                return 6;
            else System.out.println("you are not a seller!");
            return 7;
        } else return 8;
    }

    private void editPersonalInformation(int index, String[] data) {
        String[] newData = new String[7];
        for (int i = 0; i <= 5; i++)
            newData[i] = data[i];
        newData[6] = sellerController.showCompanyInformation(user);
        if (index == 0)
            newData[0] = editFirstName();
        else if (index == 1)
            newData[1] = editLastName();
        else if (index == 3)
            newData[3] = editEmail();
        else if (index == 4)
            newData[4] = editPhoneNumber();
        else if (index == 5)
            newData[5] = editPassword();
        else if (index == 6)
            newData[6] = editCompanyName();

        loginController.editPersonalInformation(user, newData);
    }

    private String editFirstName() {
        System.out.println("please enter your first name");
        return scanner.nextLine().trim();
    }

    private String editLastName() {
        System.out.println("please enter your last name");
        return scanner.nextLine().trim();
    }

    private String editEmail() {
        System.out.println("please enter your email address");
        String newData = "";
        while (!newData.matches("^(.+)@(.+)$")) {
            newData = scanner.nextLine();
            if (!newData.matches("^(.+)@(.+)$"))
                System.out.println("unacceptable email address");
        }
        return newData;
    }

    private String editPhoneNumber() {
        System.out.println("please enter your phone number");
        String newData = "";
        while (!newData.matches("^[0-9]{6,14}$")) {
            newData = scanner.nextLine();
            if (!newData.matches("^[0-9]{6,14}$"))
                System.out.println("unacceptable phone number");
        }
        return newData;
    }

    private String editPassword() {
        System.out.println("please enter your password:" +
                "Password must be between 4 and 8 digits long and include at least one numeric digit.\n");
        String newData = "";
        while (!newData.matches("^(?=.*\\d).{4,8}$")) {
            newData = scanner.nextLine();
            if (!newData.matches("^(?=.*\\d).{4,8}$"))
                System.out.println("unacceptable password");
        }
        return newData;
    }

    private String editCompanyName() {
        System.out.println("please enter your company name");
        return scanner.nextLine().trim();
    }

    private void viewCompanyInformation() {
        String info = sellerController.showCompanyInformation(user);
        System.out.println("company name : " + info);
    }

    private void viewSalesHistory() {
        ArrayList<String> sellLogs = sellerController.showSalesHistory(user);
        for (String log : sellLogs) {
            System.out.println(log);
        }
    }

    private void manageProducts() {
        ArrayList<String> products = sellerController.showProductsOfThisSeller(user);
        for (String product : products) {
            System.out.println(product);
        }
        String command;
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            Matcher viewMatcher = getMatcher("^(?i)view\\s+(\\S+)$", command);
            Matcher viewBuyersMatcher = getMatcher("^(?i)view\\s+buyers\\s+(\\S+)$", command);
            Matcher editMatcher = getMatcher("^(?i)edit\\s+(\\S+)$", command);
            if (viewMatcher.find())
                viewProduct(viewMatcher.group(1));
            else if (viewBuyersMatcher.find())
                viewBuyerProduct(viewBuyersMatcher.group(1));
            else if (editMatcher.find())
                editProduct(editMatcher.group(1));
            else System.out.println("invalid command");
        }
    }

    private void viewProduct(String productId) {
        ArrayList<String> data;
        try {
            Product product = ProductController.getProductById(productId);
            data = productController.showAttributesOfProduct(product);
            for (String line : data)
                System.out.println(line);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void viewBuyerProduct(String productId) {
        try {
            ArrayList<String> buyersInfo = sellerController.showBuyersOfThisProduct(user, productId);
            for (String buyerInfo : buyersInfo)
                System.out.println(buyerInfo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void editProduct(String productId) {
        int price, available;
        String description, command;
        System.out.println("please enter your new price (-1 for escape)");
        price = Integer.parseInt(scanByRegex("^\\d+$", "invalid format"));
        System.out.println("please enter your available number (-1 for escape)");
        available = Integer.parseInt(scanByRegex("^\\d+$", "invalid format"));
        System.out.println("please enter the description");
        description = scanner.nextLine().trim();
        HashMap<String, String> data = new HashMap<>();
        ArrayList<String> features = managerController.getCategoryFeaturesOfAProduct(productId);
        for (String feature : features) {
            System.out.println("please enter the value of " + feature + " (-1 for escape) : ");
            command = scanner.nextLine().trim();
            if (command.equals("-1")) {
                data.put(feature, null);
            } else {
                data.put(feature, command);
            }
        }
        try {
            sellerController.editProduct(user, productId, price, available, description, data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void addProducts() {
        String[] data = new String[10];
        getGeneralData(data);
        System.out.println("please enter the category name");
        String categoryName = scanner.nextLine().trim();
        HashMap<String, String> categoryData = new HashMap<String, String>();
        ArrayList<String> categoryFeatures = sellerController.getCategoryFeatures(categoryName);
        for (
                String categoryFeature : categoryFeatures) {
            System.out.println("enter the value of : " + categoryFeature);
            String featureValue = scanner.nextLine().trim();
            categoryData.put(categoryFeature, featureValue);
        }
        sellerController.addProduct(data, user, categoryData);
    }

    private void getGeneralData(String[] data) {
        System.out.println("please enter the product name");
        data[0] = scanner.nextLine().trim();
        System.out.println("please enter the price");
        data[1] = scanner.nextLine().trim();
        System.out.println("please enter the product available number");
        data[2] = scanner.nextLine().trim();
        System.out.println("please enter the product description");
        data[3] = scanner.nextLine().trim();
    }

    private void removeProduct(String productId) {
        try {
            sellerController.removeProduct(productId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void showCategories() {
        ArrayList<String> categories = managerController.showAllCategories();
        for (String category : categories) {
            System.out.println(category);
        }
    }

    private void viewAllOffs() {
        String[] allOffs = sellerController.showAllOffs(user);
        for (String off : allOffs)
            System.out.println(off);
        String command;
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            Matcher viewMatcher = getMatcher("^(?i)view\\s+(\\S+)$", command);
            Matcher editMatcher = getMatcher("^(?i)edit\\s+(\\S+)$", command);
            if (viewMatcher.find())
                try {
                    viewOff(viewMatcher.group(1));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            else if (editMatcher.find())
                try {
                    editOff(editMatcher.group(1));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            else if (command.trim().equalsIgnoreCase("add off"))
                try {
                    addOff();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            else System.out.println("invalid command");
        }
    }

    private void addOff() throws Exception {
        Date startDate = null, endDate = null;
        SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy hh:mm");
        System.out.println("please enter the start time by format(\"dd/mm/yyyy hh:mm\")");
        String dateString = scanByRegex("^\\d{2}\\/\\d{2}\\/\\d{4}\\s+\\d{2}:\\d{2}$", "invalid format");
        try {
            startDate = format.parse(dateString);
        } catch (Exception e) {
            System.out.println("invalid format!");
        }
        System.out.println("please enter the end time by format(\"dd/mm/yyyy hh:mm\")");
        dateString = scanByRegex("^\\d{2}\\/\\d{2}\\/\\d{4}\\s+\\d{2}:\\d{2}$", "invalid format");
        try {
            endDate = format.parse(dateString);
        } catch (Exception e) {
            System.out.println("invalid format!");
        }
        System.out.println("please enter the discount percentage (by format DD for example 78%)");
        String percentage = scanByRegex("^(\\d{2})%?$", "invalid format");
        int percent = Integer.parseInt(percentage);
        System.out.println("please enter the product IDs (-1 for exit)");
        String productId;
        ArrayList<String> productIds = new ArrayList<>();
        while ((productId = scanner.nextLine().trim()).equalsIgnoreCase("-1")) {
            productIds.add(productId);
        }
        try {
            sellerController.addOff(user, productIds, startDate, endDate, percent);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }///

    private void editOff(String offId) throws Exception {
        if (sellerController.getOffById(offId) == null) {
            throw new Exception("there isn't any off with this ID!");
        }
        Date startDate = null, endDate = null;
        SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy hh:mm");
        System.out.println("please enter the start time by format(\"dd/mm/yyyy hh:mm\")(-1 for escape)");
        String dateString = scanner.nextLine().trim();
        if (!dateString.equals("-1"))
            try {
                startDate = format.parse(dateString);
            } catch (Exception e) {
                System.out.println("invalid format!");
            }
        System.out.println("please enter the end time by format(\"dd/mm/yyyy hh:mm\") (-1 for escape)");
        dateString = scanner.nextLine().trim();
        if (!dateString.equals("-1"))
            try {
                endDate = format.parse(dateString);
            } catch (Exception e) {
                System.out.println("invalid format!");
            }
        System.out.println("please enter the discount percentage (by format DD for example 78%)(-1 for escape)");
        String percentage = scanByRegex("^(\\d{2})%?$", "invalid format(-1 for escape)");
        int percent = Integer.parseInt(percentage);
        String productId;
        ArrayList<String> oldProductIds = sellerController.getOffProducts(offId);
        ArrayList<String> newProductIds = new ArrayList<>(oldProductIds);
        System.out.println("please enter the product IDs you want to add : (-1 for exit)");
        while ((productId = scanner.nextLine().trim()).equalsIgnoreCase("-1")) {
            newProductIds.add(productId);
        }
        System.out.println("please enter the product IDs you want to exclude : (-1 for exit)");
        while ((productId = scanner.nextLine().trim()).equalsIgnoreCase("-1")) {
            newProductIds.remove(productId);
        }
        try {
            sellerController.editOff(user, offId, newProductIds, startDate, endDate, percent);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }///

    private void viewOff(String offId) {
        try {
            String[] offData = sellerController.showOff(offId);
            System.out.println("////////////////////////////////////////////////");
            for (String data : offData)
                System.out.println(data);
            System.out.println("////////////////////////////////////////////////");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void viewBalance() {
        System.out.println(sellerController.showBalanceOfSeller(user));
    }

    /////////////////////////////////////////////////////////
    private void managerMenu() {
        String command;
        while (true) {
            command = scanner.nextLine().trim();
            if (getMatcher("^(?i)view\\s+personal\\s+info$", command).find())
                viewPersonalInformation();
            else if (getMatcher("^(?i)manage\\s+users$", command).find())
                manageUsers();
            else if (getMatcher("^(?i)manage\\s+all\\s+products$", command).find())
                manageAllProducts();
            else if (getMatcher("^(?i)create\\s+discount\\s+code$", command).find())
                createDiscountCode();
            else if (getMatcher("^(?i)view\\s+discount\\s+codes$", command).find())
                viewDiscountCodesForManager();
            else if (getMatcher("^(?i)manage\\s+requests$", command).find())
                manageRequests();
            else if (getMatcher("^(?i)manage\\s+categories$", command).find())
                manageCategories();
            else if (getMatcher("^(?i)products$", command).find())
                productMenu();
            else if (getMatcher("^(?i)offs$", command).find())
                System.out.println();
            else if (getMatcher("^(?i)end$", command).find())
                break;
            else System.out.println("invalid command");


        }
    }

    private void manageUsers() {
        ArrayList<String> allUsers = managerController.showUsers();
        for (String user : allUsers)
            System.out.println(user);
        String command;
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            Matcher viewMatcher = getMatcher("^(?i)view\\s+(\\S+)$", command);
            Matcher deleteMatcher = getMatcher("^(?i)delete\\s+user\\s+(\\S+)$", command);
            if (viewMatcher.find())
                viewUser(viewMatcher.group(1));
            else if (deleteMatcher.find())
                deleteUser(deleteMatcher.group(1));
            else if (command.equalsIgnoreCase("create manager profile"))
                addManager();
            else System.out.println("invalid command");
        }
    }

    private void viewUser(String username) {
        User showingUser = LoginController.getUserByUsername(username);
        if (showingUser == null) {
            System.out.println("there isn't any user with this username!");
            return;
        }
        String[] datas = loginController.showPersonalInformation(showingUser);
        for (String data : datas)
            System.out.println(data);
    }

    private void deleteUser(String username) {
        try {
            managerController.deleteUser(username);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void addManager() {
        LoginMenu loginMenu = LoginMenu.getInstance();
        System.out.println("please enter the username : ");
        String username = scanner.nextLine().trim();
        loginMenu.register("manager", username, true);
    }

    private void manageAllProducts() {
        String command;
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            Matcher removeMathcer = getMatcher("^(?i)remove\\s+(\\S+)$", command);
            if (removeMathcer.find())
                removeProductByManager(removeMathcer.group(1));
            else System.out.println("invalid command");
        }
    }

    private void removeProductByManager(String productId) {
        try {
            managerController.removeProduct(productId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void createDiscountCode() {
    }///

    private void viewDiscountCodesForManager() {
        viewAllDiscountCodes();
        String command;
        while ((command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            Matcher viewMatcher = getMatcher("^(?i)view\\s+discount\\s+codes\\s+(.+)$", command);
            Matcher editMathcer = getMatcher("^(?i)edit\\s+discount\\s+codes\\s+(.+)$", command);
            Matcher removeMatcher = getMatcher("^(?i)remove\\s+discount\\s+codes\\s+(.+)$", command);
            if (viewMatcher.find())
                viewDiscountCode(viewMatcher.group(1));
            else if (editMathcer.find())
                editDiscountCode(editMathcer.group(1));
            else if (removeMatcher.find())
                removeDiscountCode(removeMatcher.group(1));
            else System.out.println("invalid command");


        }
    }

    private void viewAllDiscountCodes() {
        System.out.println("/////////////////////////////////////////////");
        ArrayList<String> allCodes = null;
        try {
            allCodes = managerController.showAllDiscountCodes();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        for (String code : allCodes)
            System.out.println(code);
        System.out.println("/////////////////////////////////////////////");
    }

    private void viewDiscountCode(String code) {
        try {
            System.out.println(managerController.showDiscount(code));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void editDiscountCode(String code) {

    }///

    private void removeDiscountCode(String code) {
        try {
            managerController.removeDiscountCode(code);
            System.out.println("removed successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void manageRequests() {
        showAllRequests();
        String command;
        while ((command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            Matcher viewMatcher = getMatcher("^(?i)view\\s+discount\\s+codes\\s+(.+)$", command);
            Matcher editMathcer = getMatcher("^(?i)edit\\s+discount\\s+codes\\s+(.+)$", command);
            Matcher removeMatcher = getMatcher("^(?i)remove\\s+discount\\s+codes\\s+(.+)$", command);
            if (viewMatcher.find())
                viewDiscountCode(viewMatcher.group(1));
            else if (editMathcer.find())
                editDiscountCode(editMathcer.group(1));
            else if (removeMatcher.find())
                removeDiscountCode(removeMatcher.group(1));
            else System.out.println("invalid command");

        }
    }

    private void showAllRequests() {
        try {
            ArrayList<String> allRequests = managerController.showAllRequests();
            for (String request : allRequests)
                System.out.println(request);
        } catch (Exception e) {
            System.out.println("there isn't any request!");
        }
    }

    private void manageCategories() {
    }///

    /////////////////////////////////////////////////////////////////////
    private void customerMenu() {
        String command;
        while (true) {
            command = scanner.nextLine().trim();
            if (getMatcher("^(?i)view\\s+personal\\s+info$", command).find())
                viewPersonalInformation();
            else if (getMatcher("^(?i)view\\s+cart$", command).find())
                viewCart();
            else if (getMatcher("^(?i)purchase$", command).find())
                purchase();
            else if (getMatcher("^(?i)view\\s+orders$", command).find())
                viewOrders();
            else if (getMatcher("^(?i)view\\s+balance$", command).find())
                viewBalance();
            else if (getMatcher("^(?i)view\\s+discount\\s+codes$", command).find())
                viewDiscountCodesForCustomer();
            else if (getMatcher("^(?i)products$", command).find())
                productMenu();
            else if (getMatcher("^(?i)offs$", command).find())
                offsMenu();
            else if (getMatcher("^(?i)end$", command).find())
                break;
            else System.out.println("invalid command");


        }
    }

    private void productMenu() {
        ProductMenu.getInstance().run();
    }

    private void offsMenu() {
        OffMenu offMenu = OffMenu.getInstance();
        offMenu.run();
    }

    private void viewCart() {
        try {
            System.out.println("//////////////////////////////////////////////");
            ArrayList<String> products = customerController.showCard(user);
            for (String product : products)
                System.out.println(product);
            System.out.println("//////////////////////////////////////////////");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void purchase() {
        CompletionShop completionShop = CompletionShop.getInstance();
        completionShop.run();
    }

    private void viewOrders() {
        try {
            System.out.println("///////////////////////////////////////////////");
            ArrayList<String> allOrders = customerController.showAllOrders(user);
            for (String order : allOrders)
                System.out.println(order);
            System.out.println("///////////////////////////////////////////////");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void viewDiscountCodesForCustomer() {
        try {
            System.out.println("//////////////////////////////////////////////");
            ArrayList<String> allCodes = customerController.showDiscountCodes(user);
            for (String code : allCodes)
                System.out.println(code);
            System.out.println("//////////////////////////////////////////////");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}