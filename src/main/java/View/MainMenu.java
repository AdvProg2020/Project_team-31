package View;

import Model.*;

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
            else if (getMatcher("^(?i)remove\\s+product\\s+(\\S+)$", command).find())
                removeProducts();
            else if (getMatcher("^(?i)show\\s+categories$", command).find())
                showCategories();
            else if (getMatcher("^(?i)view\\s+offs$", command).find())
                viewOffs();
            else if (getMatcher("^(?i)view\\s+balance\n$", command).find())
                viewBalance();
            else if (getMatcher("^(?i)products$", command).find())
                System.out.println();
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
        String[] changedInfo = new String[6];
        changedInfo[0] = information[0]; //first name
        changedInfo[1] = information[1]; //last name
        changedInfo[2] = information[3]; //email
        changedInfo[3] = information[4]; // phone
        changedInfo[4] = information[5]; //password
        if (user instanceof Seller) {
            changedInfo[5] = sellerController.showCompanyInformation(user);
        }
        String command;
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            Matcher matcher = getMatcher("^(?i)edit\\s+(.+)$", command);
            if (matcher.find()) {
                int index = findIndex(matcher.group(1));
                if (index == 7)
                    System.out.println("invalid command");
                else if (index != 6) editPersonalInformation();
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

    private void editPersonalInformation() {

    }

    private void viewCompanyInformation() {

    }

    private void viewSalesHistory() {
    }

    private void manageProducts() {
    }

    private void addProducts() {
    }

    private void removeProducts() {
    }

    private void showCategories() {
    }

    private void viewOffs() {
    }

    private void viewBalance() {
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
                System.out.println();
            else if (getMatcher("^(?i)offs$", command).find())
                System.out.println();
            else if (getMatcher("^(?i)end$", command).find())
                break;
            else System.out.println("invalid command");


        }
    }

    private void manageUsers() {
    }

    private void manageAllProducts() {
    }

    private void createDiscountCode() {
    }

    private void viewDiscountCodesForManager() {
    }

    private void manageRequests() {
    }

    private void manageCategories() {
    }

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
                System.out.println();
            else if (getMatcher("^(?i)offs$", command).find())
                System.out.println();
            else if (getMatcher("^(?i)end$", command).find())
                break;
            else System.out.println("invalid command");


        }
    }

    private void viewCart() {
    }

    private void purchase() {
    }

    private void viewOrders() {
    }

    private void viewDiscountCodesForCustomer() {
    }

}
