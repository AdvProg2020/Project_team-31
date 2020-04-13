package View;

public class ProductMenu extends Menu {
    public static ProductMenu instance = null;

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
        while (true) {
            command = scanner.nextLine().trim();
            if (getMatcher("^(?i)view\\s+categories$", command).find())
                viewCategories();
            else if (getMatcher("^(?i)filtering$", command).find())
                filtering();
            else if (getMatcher("^(?i)sorting$", command).find())
                sorting();
            else if (getMatcher("^(?i)show\\s+products$", command).find())
                showAllProducts();
            else if (getMatcher("^(?i)show\\s+product\\s+(\\S+)$", command).find())
                showProduct();
            else if (getMatcher("^(?i)back$", command).find())
                break;
            else System.out.println("invalid command");

        }
    }

    private void viewCategories() {
    }

    private void filtering() {
    }

    private void sorting() {
    }

    private void showAllProducts() {
    }

    private void showProduct() {
        //run the showproduct
        //set the product in ShowProductMenu class
    }

}
