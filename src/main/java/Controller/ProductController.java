package Controller;

import Model.Product;
import Model.User;

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

    public String showAllCategories() {
        return null;
    }

    public String showAvailableFiltersForUser(User user, String categoryName) {
        return null;
    }

    public String filterAndShowProducts(User user, String filterKey, String filterValue) {
        return null;
    }

    public String ShowCurrentFilters(User user) {
        return null;
    }

    public void disableFilterForUser(User user, String filterKey) {

    }

    public void addProductToCard(User user, String productId, String seller) {

    }

    public String compareTwoProduct(String productId1, String productId2) {
        return null;
    }

    public String showCommentAboutProduct(String productId) {
        return null;
    }

    public void addComment(User user, String productId, String title, String content) {

    }

}

class canNotFilterOnThisKey extends Exception {
    public canNotFilterOnThisKey(String message) {
        super(message);
    }
}
