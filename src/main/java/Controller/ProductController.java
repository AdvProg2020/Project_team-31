package Controller;

import Model.Category;
import Model.Manager;
import Model.Product;
import Model.User;

import java.util.ArrayList;

public class ProductController {
    private static ProductController productControllerInstance = new ProductController();

    public ProductController() {
    }

    public static ProductController getInstance() {
        return productControllerInstance;
    }

    public String showProducts(User user, String sorting) {
        return null;
    }

    public ArrayList<String> showAvailableFiltersForUser(User user, String categoryName) {
        ArrayList<String> availableFilters = new ArrayList<>();
        if(categoryName == null){
            for (Category category : Category.getAllCategories()) {
                availableFilters.add("category:" + category.getName());
            }
            return availableFilters;
        }
        for (String property : ManagerController.getInstance().getCategoryByName(categoryName).getSpecialProperties()) {
            availableFilters.add(property);
        }
        return availableFilters;
    }

    public String filterAndShowProducts(User user, String filterKey, String filterValue) {
        return null;
    }

    public String ShowCurrentFilters(User user) {
        return null;
    }

    public void disableFilterForUser(User user, String filterKey) {

    }

    public ArrayList<String> compareTwoProduct(Product product1, String productId2) {
        Product product2 = getProductById(productId2);
        ArrayList<String> information = new ArrayList<>();
        information.add("name:1-" + product1.getName() + ";2-" + product2.getName());
        information.add("price:1-" + product1.getPrice() + ";2-" + product2.getPrice());
        information.add("rate:1-" + (product1.getSumOfCustomersRate()/product1.getCustomersWhoRated()) + ";2-" + (product2.getSumOfCustomersRate()/product2.getCustomersWhoRated()));
        return information;
    }

    public ArrayList<String> showCommentAboutProduct(String productId) {
        Product product = getProductById(productId);
        ArrayList<String> allComments = new ArrayList<>();
        // naghes
        return null;
    }

    public void addComment(User user, String productId, String title, String content) {
        
    }

    public static Product getProductById(String productId) {
        for (Product product : Product.allProducts) {
            if(product.getProductId().equals(productId))
                return product;
        }
        return null;
    }
}
class CanNotFilterOnThisKey extends Exception {
    public CanNotFilterOnThisKey(String message) {
        super(message);
    }
}
