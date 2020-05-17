package View;

import Model.Product;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class ShowProductMenu extends Menu {
    public static ShowProductMenu instance = null;
    private Product product = null;

    private ShowProductMenu() {
        super();
    }

    public static ShowProductMenu getInstance() {
        if (instance == null)
            instance = new ShowProductMenu();
        return instance;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public void run() {
        String command;
        while ((command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            Matcher matcher = getMatcher("^(?i)compare\\s+(\\S+)$", command);
            if (getMatcher("^(?i)digest$", command).find())
                digest();
            else if (getMatcher("^(?i)attributes$", command).find())
                attributes();
            else if (matcher.find())
                compare(matcher.group(1));
            else if (getMatcher("^(?i)login$", command).find())
                loginAndLogOut(true);
            else if (getMatcher("^(?i)logout$", command).find())
                loginAndLogOut(false);
            else if (getMatcher("^(?i)Comments$", command).find())
                comments();
            else System.out.println("invalid command");

        }
    }

    private void digest() {
        ArrayList<String> information = productController.showDigestOfProduct(product, user);
        for (String line : information)
            System.out.println(line);
        String command;
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            if (command.equalsIgnoreCase("add to card"))
                addToCard();
            else System.out.println("invalid command");
        }

    }

    private void addToCard() {
        if (user == null) {
            System.out.println("you have to login.");
            LoginMenu.getInstance().run();
        }
        System.out.println("please select your seller");
        String seller = scanner.nextLine().trim();
        try {
            customerController.addProductToCard(user, card, product, seller);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void attributes() {
        ArrayList<String> information = productController.showAttributesOfProduct(product);
        for (String line : information)
            System.out.println(line);
    }

    private void compare(String secondProduct) {
        System.out.println("/////////////////////////////////////////////");
        try {
            ArrayList<String> fields = productController.compareTwoProduct(product, secondProduct);
            for (String field : fields) {
                System.out.println(field);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("/////////////////////////////////////////////");
    }

    private void comments() {
        ArrayList<String> information = productController.showCommentAboutProduct(product);
        for (String line : information)
            System.out.println(line);
        String command;
        while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("back")) {
            if (command.equalsIgnoreCase("add comment")) {
                addComment();
            } else System.out.println("invalid command");
        }
    }

    private void addComment() {
        System.out.println("Title : ");
        String title = scanner.nextLine().trim();
        System.out.println("Content : ");
        String content = scanner.nextLine().trim();
        productController.addComment(user, product, title, content);
    }
}
