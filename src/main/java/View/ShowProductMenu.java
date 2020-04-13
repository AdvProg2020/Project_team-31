package View;

import Model.Product;

public class ShowProductMenu extends Menu {
    public static ShowProductMenu instance = null;

    private ShowProductMenu() {
        super();
    }

    public static ShowProductMenu getInstance() {
        if (instance == null)
            instance = new ShowProductMenu();
        return instance;
    }

    public Product product;

    @Override
    public void run() {
        String command;
        while (true) {
            command = scanner.nextLine().trim();
            if (getMatcher("^(?i)digest$", command).find())
                digest();
            else if (getMatcher("^(?i)attributes$", command).find())
                attributes();
            else if (getMatcher("^(?i)compare\\s+(\\S+)$", command).find())
                compare();
            else if (getMatcher("^(?i)Comments$", command).find())
                comments();
            else if (getMatcher("^(?i)back$", command).find())
                break;
            else System.out.println("invalid command");

        }
    }

    private void digest() {
    }

    private void attributes() {
    }

    private void compare() {
    }

    private void comments() {
    }


}
