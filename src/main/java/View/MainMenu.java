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
        String[] changedInfo = new String[6];
        String command;
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            Matcher matcher = getMatcher("^(?i)edit\\s+(.+)$", command);
            if (matcher.find()) {
                int index = findIndex(matcher.group(1));
                if (index != 6 && index != 7) editPersonalInformation(index, changedInfo);
            } else System.out.println("invalid command");
        }
    }

    private int findIndex(String check) {
        if (check.equalsIgnoreCase("first name"))
            return 0;
        else if (check.equalsIgnoreCase("last name"))
            return 1;
        else if (check.equalsIgnoreCase("email address"))
            return 2;
        else if (check.equalsIgnoreCase("phone number"))
            return 3;
        else if (check.equalsIgnoreCase("password"))
            return 4;
        else if (check.equalsIgnoreCase("company name")) {
            if (user instanceof Seller)
                return 5;
            else System.out.println("you are not a seller!");
            return 6;
        } else return 7;
    }

    private void editPersonalInformation(int index, String[] data) {
        if (index == 0)
            data[0] = editFirstName();
        else if (index == 1)
            data[1] = editLastName();
        else if (index == 2)
            data[2] = editEmail();
        else if (index == 3)
            data[3] = editPhoneNumber();
        else if (index == 4)
            data[4] = editPassword();
        else if (index == 5)
            data[5] = editCompanyName();
        loginController.editPersonalInformation(user, data);
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
        try {
            System.out.println(customerController.showProduct(productId, user));
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
//        String[] data = new String[4];
//        System.out.println("please select the data number you want to change:(-1 for exit)\n" +
//                "1.name\n2.price\n3.availability status\n4.product description ");
//        String command;
//        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("-1")) {
//            if (!(command.equals("1") || command.equals("2") || command.equals("3") || command.equals("4"))) {
//                System.out.println("please enter a valid number");
//            } else {
//                System.out.println("please enter the value : ");
//                data[Integer.parseInt(command) - 1] = scanner.nextLine().trim();
//            }

//...
        System.out.println("please enter the price : ");
        String priceStr=scanByRegex("^\\d+$","the price must be numbers (example 10000)(-1 for scape)");
        int price=Integer.parseInt(priceStr);

        System.out.println("please enter the avail");
    } ///

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
        System.out.println("please enter the product availability status (available/unavailable)");
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
        SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy hh:mm");
        System.out.println("please enter the start time by format(\"dd/mm/yyyy hh:mm\")");
        String dateString = scanByRegex("^\\d{2}\\/\\d{2}\\/\\d{4}\\s+\\d{2}:\\d{2}$", "invalid format");
        Date startDate = format.parse(dateString);
        if (startDate.before(new Date()))
            throw new Exception("please enter the end time by format(\"dd/mm/yyyy hh:mm\")");
        System.out.println("please enter the end time");
        dateString = scanByRegex("^\\d{2}\\/\\d{2}\\/\\d{4}\\s+\\d{2}:\\d{2}$", "invalid format");
        Date endDate = format.parse(dateString);
        if (endDate.before(startDate))
            throw new Exception("the end date must be after start date");
        System.out.println("please enter the discount percentage (by format DD for example 78%)");
        String percentage = scanByRegex("^(\\d{2})%?$", "invalid format");
        int percent = Integer.parseInt(percentage);
        if (percent <= 0 || percent >= 100)
            throw new WrongPercentageException("wrong percentage");
        System.out.println("please enter the product IDs (-1 for exit)");
        String productId;
        ArrayList<String> productIds = new ArrayList<>();
        while ((productId = scanner.nextLine().trim()).equalsIgnoreCase("-1")) {
            productIds.add(productId);
        }
        sellerController.addOff(user, productIds, startDate, endDate, percent);
    }

    private void editOff(String offId) throws Exception {

    }///

    private void viewOff(String offId) {
        String[] offData = sellerController.showOff(offId);
        System.out.println("////////////////////////////////////////////////");
        for (String data : offData)
            System.out.println(data);
        System.out.println("////////////////////////////////////////////////");
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

    }

    private void manageAllProducts() {
        String command;
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            Matcher removeMathcer = getMatcher("^(?i)remoce\\s+(\\S+)$", command);
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

    private void createDiscountCode() /*throws Exception*/ {
//        System.out.println("please enter the discount code (must not contain space)");
//        String discountCode=scanByRegex("^\\S+$","invalid format");
//        SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy hh:mm");
//        System.out.println("please enter the start time by format(\"dd/mm/yyyy hh:mm\")");
//        String dateString = scanByRegex("^\\d{2}\\/\\d{2}\\/\\d{4}\\s+\\d{2}:\\d{2}$", "invalid format");
//        Date startDate = format.parse(dateString);
//        if (startDate.before(new Date()))
//            throw new Exception("please enter the end time by format(\"dd/mm/yyyy hh:mm\")");
//        System.out.println("please enter the end time");
//        dateString = scanByRegex("^\\d{2}\\/\\d{2}\\/\\d{4}\\s+\\d{2}:\\d{2}$", "invalid format");
//        Date endDate = format.parse(dateString);
//        if (endDate.before(startDate))
//            throw new Exception("the end date must be after start date");
//        System.out.println("please enter the discount percentage (by format DD for example 78%)");
//        String percentage = scanByRegex("^(\\d{2})%?$", "invalid format");
//        int percent = Integer.parseInt(percentage);
//        if (percent <= 0 || percent >= 100)
//            throw new WrongPercentageException("wrong percentage");
//        System.out.println("please enter the maximum discount : ");
//        String maxDiscount = scanByRegex("^(\\d+)$", "invalid format");
//        int maximumDiscount = Integer.parseInt(percentage);
//        if (maximumDiscount<= 0 )
//            throw new WrongPercentageException("wrong value for maximum discount");
//        System.out.println("please enter the username of customers and the number of validity of each of them\n" +
//                "in format (ali.mohammady 5) -1 for exit");
//        String command;
//        while (!(command=scanner.nextLine().trim()).equalsIgnoreCase("-1")){
//            //...
//        }
    }///

    private void viewDiscountCodesForManager() {
    }///

    private void manageRequests() {
    }///

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

class WrongPercentageException extends Exception {
    public WrongPercentageException(String message) {
        super(message);
    }
}