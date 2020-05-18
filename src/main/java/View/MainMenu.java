package View;

import Controller.*;
import Model.*;

import java.text.*;;
import java.util.*;
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
                viewBalanceForSeller();
            else if (getMatcher("^(?i)products$", command).find())
                productMenu();
            else if (getMatcher("^(?i)offs$", command).find())
                showOffs();
            else if (getMatcher("^(?i)login$", command).find())
                loginAndLogOut(true);
            else if (getMatcher("^(?i)logout$", command).find())
                loginAndLogOut(false);
            else if (getMatcher("^(?i)help", command).find())
                sellerHelp();
            else if (getMatcher("^(?i)end$", command).find()) {
                end = true;
                break;
            } else System.out.println("invalid command");
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
            } else if (command.equalsIgnoreCase("help")) {
                personalInfoHelp();
            } else System.out.println("invalid command");
        }
    }

    private void personalInfoHelp() {
        System.out.println("///////////////////////help////////////////////");
        System.out.println("edit");
        System.out.println("help");
        System.out.println("back");
        System.out.println("///////////////////////help////////////////////");
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
        try {
            newData[6] = sellerController.showCompanyInformation(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
        try {
            String info = sellerController.showCompanyInformation(user);
            System.out.println("company name : " + info);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
            else if (command.equalsIgnoreCase("help"))
                manageProductsHelp();
            else if (getMatcher("^(?i)login$", command).find())
                loginAndLogOut(true);
            else if (getMatcher("^(?i)logout$", command).find())
                loginAndLogOut(false);
            else if (editMatcher.find()) {
                try {
                    editProduct(editMatcher.group(1));
                } catch (Exception e) {
                    System.out.println("wrong format!");
                }
            } else System.out.println("invalid command");
        }
    }

    private void manageProductsHelp() {
        System.out.println("///////////////////////help////////////////////");
        System.out.println("view [productId]");
        System.out.println("view buyers [productId]");
        System.out.println("edit [productId]");
        System.out.println("login");
        System.out.println("logout");
        System.out.println("back");
        System.out.println("help");
        System.out.println("///////////////////////help////////////////////");
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
            else if (command.equalsIgnoreCase("help")) {
                viewAllOffsHelp();
            } else if (getMatcher("^(?i)login$", command).find())
                loginAndLogOut(true);
            else if (getMatcher("^(?i)logout$", command).find())
                loginAndLogOut(false);
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

    private void viewAllOffsHelp() {
        System.out.println("///////////////////////help////////////////////");
        System.out.println("view [offId]");
        System.out.println("edit [offId]");
        System.out.println("add off");
        System.out.println("login");
        System.out.println("logout");
        System.out.println("help");
        System.out.println("back");
        System.out.println("///////////////////////help////////////////////");
    }

    private void addOff() throws Exception {
        System.out.println("please enter the start time by format(\"dd/mm/yyyy hh:mm\")");
        Date startDate = scanDate();
        if (startDate == null)
            return;
        System.out.println("please enter the end time by format(\"dd/mm/yyyy hh:mm\")");
        Date endDate = scanDate();
        if (endDate == null)
            return;
        System.out.println("please enter the discount percentage (by format DD for example 78%)");
        String percentage = scanByRegex("^(\\d{2})%?$", "invalid format");
        int percent = Integer.parseInt(percentage);
        if (percent >= 100 || percent <= 0) {
            System.out.println("invalid number!");
            return;
        }
        System.out.println("please enter the product IDs (-1 for exit)");
        String productId;
        ArrayList<String> productIds = new ArrayList<>();
        while (!(productId = scanner.nextLine().trim()).equalsIgnoreCase("-1"))
            productIds.add(productId);
        try {
            sellerController.addOff(user, productIds, startDate, endDate, percent);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void editOff(String offId) throws Exception {
        System.out.println("please enter the start time by format(\"dd/mm/yyyy hh:mm\")");
        Date startDate = scanDate();
        if (startDate == null)
            return;
        System.out.println("please enter the end time by format(\"dd/mm/yyyy hh:mm\")");
        Date endDate = scanDate();
        if (endDate == null)
            return;
        System.out.println("please enter the discount percentage (by format DD for example 78%)");
        String percentage = scanByRegex("^(\\d{2})%?$", "invalid format");
        int percent = Integer.parseInt(percentage);
        if (percent >= 100 || percent <= 0) {
            System.out.println("invalid number!");
            return;
        }
        String productId;
        ArrayList<String> productIds = null;
        try {
            productIds = sellerController.getOffProducts(offId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("please enter the product IDs you want to add (-1 for exit)");
        while (!(productId = scanner.nextLine().trim()).equalsIgnoreCase("-1"))
            productIds.add(productId);
        System.out.println("please enter the product IDs you want to exclude (-1 for exit)");
        while (!(productId = scanner.nextLine().trim()).equalsIgnoreCase("-1"))
            productIds.remove(productId);
        try {
            sellerController.editOff(user, offId, productIds, startDate, endDate, percent);
            System.out.println("off edited successfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private Date scanDate() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy hh:mm");
        String dateString = null;
        try {
            while (!(dateString = scanByRegex("^\\d{2}\\/\\d{2}\\/\\d{4}\\s+\\d{2}:\\d{2}$", "invalid date format")).equalsIgnoreCase("back")) {
                try {
                    return format.parse(dateString);
                } catch (Exception e) {
                    System.out.println("invalid format");
                }
            }
        } catch (NullPointerException e) {
            return null;
        }
        return null;
    }

    private void viewOff(String offId) {
        try {
            String[] offData = sellerController.showOff(user, offId);
            System.out.println("////////////////////////////////////////////////");
            for (String data : offData)
                System.out.println(data);
            System.out.println("////////////////////////////////////////////////");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void viewBalanceForSeller() {
        try {
            System.out.println(sellerController.showBalanceOfSeller(user));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void viewBalanceForCustomer() {
        try {
            System.out.println(customerController.showBalanceForCustomer(user));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void showOffs() {
        OffMenu.getInstance().run();
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
            else if (getMatcher("^(?i)create\\s+discount\\s+code$", command).find()) {
                try {
                    createDiscountCode();
                } catch (Exception e) {
                    System.out.println("invalid date format!");
                }
            } else if (getMatcher("^(?i)view\\s+discount\\s+codes$", command).find())
                viewDiscountCodesForManager();
            else if (getMatcher("^(?i)manage\\s+requests$", command).find())
                manageRequests();
            else if (getMatcher("^(?i)manage\\s+categories$", command).find())
                manageCategories();
            else if (getMatcher("^(?i)products$", command).find())
                productMenu();
            else if (getMatcher("^(?i)login$", command).find())
                loginAndLogOut(true);
            else if (getMatcher("^(?i)logout$", command).find())
                loginAndLogOut(false);
            else if (getMatcher("^(?i)offs$", command).find())
                showOffs();
            else if (getMatcher("^(?i)help$", command).find())
                managerHelp();
            else if (getMatcher("^(?i)end$", command).find()) {
                end = true;
                break;
            } else System.out.println("invalid command");


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
            else if (command.equalsIgnoreCase("help"))
                manageUsersHelp();
            else if (command.equalsIgnoreCase("create manager profile"))
                addManager();
            else System.out.println("invalid command");
        }
    }

    private void manageUsersHelp() {
        System.out.println("///////////////////////help////////////////////");
        System.out.println("view [username]");
        System.out.println("delete user [username]");
        System.out.println("create manager profile");
        System.out.println("login");
        System.out.println("logout");
        System.out.println("help");
        System.out.println("end");
        System.out.println("///////////////////////help////////////////////");
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
            sellerController.removeProduct(productId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void createDiscountCode() throws Exception {
        System.out.println("please enter the code : ");
        String code = scanner.nextLine().trim();
        System.out.println("please enter the start time by format(\"dd/mm/yyyy hh:mm\")");
        Date startDate = scanDate();
        if (startDate == null)
            return;
        System.out.println("please enter the end time by format(\"dd/mm/yyyy hh:mm\")");
        Date endDate = scanDate();
        if (endDate == null)
            return;
        System.out.println("please enter the discount percentage (by format DD for example 78%)");
        String percentage = scanByRegex("^(\\d{2})%?$", "invalid format");
        int percent = Integer.parseInt(percentage);
        if (percent >= 100 || percent <= 0) {
            System.out.println("invalid number!");
            return;
        }
        System.out.println("please enter the maximum price");
        String price = scanByRegex("^\\d+$", "invalid format");
        if (Integer.parseInt(price) <= 0) {
            throw new Exception("invalid price!");
        }
        String command;
        HashMap<String, Integer> data = new HashMap<>();
        System.out.println("please enter the customers and the number of uses (for example reza-2)");
        while ((command = scanner.nextLine().trim()).equalsIgnoreCase("-1")) {
            Matcher matcher = getMatcher("(.+)-(\\d+)", command);
            if (matcher.find()) {
                data.put(matcher.group(1), Integer.parseInt(matcher.group(2)));
            } else {
                System.out.println("invalid format");
            }
        }
        try {
            managerController.createDiscountCode(code, startDate, endDate, percent, Integer.parseInt(price), data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void viewDiscountCodesForManager() {
        viewAllDiscountCodes();
        String command;
        while ((command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            Matcher viewMatcher = getMatcher("^(?i)view\\s+discount\\s+codes\\s+(.+)$", command);
            Matcher editMathcer = getMatcher("^(?i)edit\\s+discount\\s+codes\\s+(.+)$", command);
            Matcher removeMatcher = getMatcher("^(?i)remove\\s+discount\\s+codes\\s+(.+)$", command);
            if (viewMatcher.find())
                viewDiscountCode(viewMatcher.group(1));
            else if (editMathcer.find()) {
                try {
                    editDiscountCode(editMathcer.group(1));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else if (removeMatcher.find())
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

    private void editDiscountCode(String code) throws Exception {
        System.out.println("please enter the start time by format(\"dd/mm/yyyy hh:mm\")");
        Date startDate = scanDate();
        if (startDate == null)
            return;
        System.out.println("please enter the end time by format(\"dd/mm/yyyy hh:mm\")");
        Date endDate = scanDate();
        if (endDate == null)
            return;
        System.out.println("please enter the discount percentage (by format DD for example 78%)");
        String percentage = scanByRegex("^(\\d{2})%?$", "invalid format");
        int percent = Integer.parseInt(percentage);
        if (percent >= 100 || percent <= 0) {
            System.out.println("invalid number!");
            return;
        }
        System.out.println("please enter the maximum price");
        String price = scanByRegex("^\\d+$", "invalid format");
        if (Integer.parseInt(price) <= 0) {
            throw new Exception("invalid price!");
        }
        String command;
        HashMap<String, Integer> data = new HashMap<>();
        System.out.println("please enter the customers and the number of uses (for example reza-2)");
        while ((command = scanner.nextLine().trim()).equalsIgnoreCase("-1")) {
            Matcher matcher = getMatcher("(.+)-(\\d+)", command);
            if (matcher.find()) {
                data.put(matcher.group(1), Integer.parseInt(matcher.group(2)));
            } else {
                System.out.println("invalid format");
            }
        }
        try {
            managerController.editDiscountCode(code, startDate, endDate, percent, Integer.parseInt(price), data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

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
            Matcher matcher = getMatcher("^(?i)detail\\s+(.+)$", command);
            if (matcher.find())
                detailRequest(matcher.group(1));
            else System.out.println("invalid command");

        }
    }

    private void detailRequest(String requestId) {
        try {
            viewRequest(requestId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        String command;
        while ((command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            Matcher accept = getMatcher("^(?i)accept$", command);
            Matcher decline = getMatcher("^(?i)decline$", command);
            if (accept.find())
                if (accept.find())
                    acceptRequest(requestId);
                else if (decline.find())
                    declineRequest(requestId);
                else System.out.println("invalid command");

        }
    }

    private void acceptRequest(String requestId) {
        try {
            managerController.acceptRequest(requestId);
            System.out.println("request accepted successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void declineRequest(String requestId) {
        try {
            managerController.declineRequest(requestId);
            System.out.println("request declined successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void viewRequest(String requestId) {
        try {
            System.out.println(managerController.showRequestDetails(requestId));
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
        viewAllDiscountCodes();
        String command;
        while ((command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            Matcher edit = getMatcher("^(?i)edit\\s+(.+)$", command);
            Matcher add = getMatcher("^(?i)add\\s+(.+)$", command);
            Matcher remove = getMatcher("^(?i)remove\\s+(.+)$", command);
            if (edit.find())
                editCategory(edit.group(1));
            else if (add.find())
                addCategory(add.group(1));
            else if (remove.find())
                removeCategory(remove.group(1));
            else System.out.println("invalid command");
        }
    }

    private void editCategory(String name) {
        ArrayList<String> features = null;
        HashMap<String, String> changedFields = new HashMap<>();
        try {
            features = sellerController.getCategoryFeatures(name);
        } catch (Exception e) {
            System.out.println("there isn't any category with this name");
        }
        if (features == null) {
            System.out.println("there isn't any category with this name");
            return;
        }
        String command;
        System.out.println("please enter the features you want to change(example previousName-changedName) : (-1 for exit)");
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("-1")) {
            Matcher matcher = getMatcher(command, "^(.+)-(.+)$");
            if (matcher.find())
                changedFields.put(matcher.group(1), matcher.group(2));
        }
        managerController.changeFeatureOfCategory(name, changedFields);
        for (Map.Entry<String, String> entry : changedFields.entrySet()) {
            features.add(entry.getValue());
        }
        System.out.println("please enter the features you want to add : (-1 for exit)");
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("-1")) {
            features.add(command);
        }
        System.out.println("please enter the product IDs you want to exclude : (-1 for exit)");
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("-1")) {
            features.remove(command);
        }

    }

    private void addCategory(String name) {
        if (ManagerController.getCategoryByName(name) != null) {
            System.out.println("there is another category with this name");
            return;
        }
        System.out.println("please enter the category features (-1 for exit)");
        String command;
        ArrayList<String> features = new ArrayList<>();
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("-1"))
            features.add(command);
    }

    private void removeCategory(String name) {
        try {
            managerController.removeCategory(name);
            System.out.println("category removed successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /////////////////////////////////////////////////////////////////////
    private void customerMenu() {
        card = customerController.createCard();
        String command;
        try {
            while (true) {
                command = scanner.nextLine().trim();
                if (getMatcher("^(?i)view\\s+personal\\s+info$", command).find())
                    viewPersonalInformation();
                else if (getMatcher("^(?i)view\\s+cart$", command).find())
                    cart();
                else if (getMatcher("^(?i)purchase$", command).find())
                    purchase();
                else if (getMatcher("^(?i)view\\s+orders$", command).find())
                    viewOrders();
                else if (getMatcher("^(?i)view\\s+balance$", command).find())
                    viewBalanceForCustomer();
                else if (getMatcher("^(?i)view\\s+discount\\s+codes$", command).find())
                    viewDiscountCodesForCustomer();
                else if (getMatcher("^(?i)products$", command).find())
                    productMenu();
                else if (getMatcher("^(?i)login$", command).find())
                    loginAndLogOut(true);
                else if (getMatcher("^(?i)logout$", command).find())
                    loginAndLogOut(false);
                else if (getMatcher("^(?i)offs$", command).find())
                    offsMenu();
                else if (getMatcher("^(?i)help$", command).find())
                    customerHelp();
                else if (getMatcher("^(?i)end$", command).find())
                    break;
                else System.out.println("invalid command");
            }

        } catch (Exception e) {
            System.out.println("you have to login!");

        }
    }

    private void productMenu() {
        productController.clearFilters(user);
        ProductMenu.getInstance().run();
    }

    private void offsMenu() {
        productController.clearFilters(user);
        OffMenu.getInstance().run();
    }

    private void viewCart() {
        try {
            System.out.println("//////////////////////////////////////////////");
            ArrayList<String> products = customerController.showCard(user, card);
            for (String product : products)
                System.out.println(product);
            System.out.println("//////////////////////////////////////////////");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void cart() {
        String command;
        try {
            while ((command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
                Matcher view = getMatcher("^(?i)view\\s+(\\S+)$", command);
                Matcher increase = getMatcher("^(?i)increase\\s+(\\S+)$", command);
                Matcher decrease = getMatcher("^(?i)decrease\\s+(\\S+)$", command);
                if (getMatcher("^(?i)show\\s+products$", command).find())
                    viewCart();
                else if (view.find())
                    ProductMenu.getInstance().showProduct(view.group(1));
                else if (increase.find())
                    increase(increase.group(1));
                else if (decrease.find())
                    decrease(decrease.group(1));
                else if (getMatcher("^(?i)show\\s+total\\s+price", command).find())
                    showTotalPrice();
                else if (getMatcher("^(?i)purchase$", command).find())
                    purchase();
                else if (getMatcher("^(?i)login$", command).find())
                    loginAndLogOut(true);
                else if (getMatcher("^(?i)logout$", command).find())
                    loginAndLogOut(false);
                else System.out.println("invalid command");
            }

        } catch (Exception e) {
            System.out.println("you have to login!");

        }
    }

    private void showTotalPrice() {
        int price = customerController.showTotalPrice(card);
        System.out.println("the total price of your cart is : " + price);
    }

    private void increase(String productId) {
        System.out.println("please enter the increase number (for example 5)");
        String number = scanByRegex("^\\d+$", "invalid command");
        int num = Integer.parseInt(number);
        try {
            customerController.changeNumberOfProductInCard(user, card, productId, num);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void decrease(String productId) {
        System.out.println("please enter the decrease number (for example 5)");
        String number = scanByRegex("^\\d+$", "invalid command");
        int num = Integer.parseInt(number) * (-1);
        try {
            customerController.changeNumberOfProductInCard(user, card, productId, num);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void purchase() {
        CompletionShop.getInstance().run();
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
        String command;
        while ((command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            Matcher showOrder = getMatcher("^(?i)show\\s+order\\s+(\\S+)$", command);
            Matcher rate = getMatcher("^(?i)rate\\s+(\\S+)\\s+([12345])$", command);
            if (showOrder.find())
                showOrder(showOrder.group(1));
            else if (rate.find())
                rate(rate.group(1), rate.group(2));
            else System.out.println("invalid command");
        }
    }

    private void showOrder(String orderId) {
        try {
            customerController.showOrder(user, orderId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void rate(String productId, String rate) {
        try {
            customerController.rateProduct(user, productId, Integer.parseInt(rate));
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

    private void sellerHelp() {
        System.out.println("///////////////////////help////////////////////");
        System.out.println("remove product [productId]");
        System.out.println("view personal info");
        System.out.println("view company info");
        System.out.println("view sales history");
        System.out.println("manage products");
        System.out.println("add product");
        System.out.println("show categories");
        System.out.println("view offs");
        System.out.println("view balance");
        System.out.println("products");
        System.out.println("offs");
        System.out.println("login");
        System.out.println("logout");
        System.out.println("help");
        System.out.println("end");
        System.out.println("///////////////////////help////////////////////");
    }

    private void managerHelp() {
        System.out.println("///////////////////////help////////////////////");
        System.out.println("manage users");
        System.out.println("view personal info");
        System.out.println("manage all products");
        System.out.println("create discount code");
        System.out.println("view discount codes");
        System.out.println("manage requests");
        System.out.println("manage categories");
        System.out.println("products");
        System.out.println("login");
        System.out.println("logout");
        System.out.println("offs");
        System.out.println("help");
        System.out.println("end");
        System.out.println("///////////////////////help////////////////////");
    }

    private void customerHelp() {
        System.out.println("///////////////////////help////////////////////");
        System.out.println("view personal info");
        System.out.println("view cart");
        System.out.println("purchase");
        System.out.println("view orders");
        System.out.println("view balance");
        System.out.println("view discount codes");
        System.out.println("products");
        System.out.println("login");
        System.out.println("logout");
        System.out.println("offs");
        System.out.println("help");
        System.out.println("end");
        System.out.println("///////////////////////help////////////////////");
    }

}