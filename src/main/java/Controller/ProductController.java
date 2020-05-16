package Controller;

import Model.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductController {
    private static ProductController productControllerInstance = new ProductController();

    public ProductController() {
    }

    public static ProductController getInstance() {
        return productControllerInstance;
    }

    public ArrayList<String> showProducts(User user, String categoryName, String sorting) {
        ArrayList<Product> filteredProducts = filterAndShowProducts(user, categoryName);
        if (sorting.equalsIgnoreCase("price")) {
            HashMap<Integer, Product> sortedByPrice = new HashMap<>();
            for (Product product : filteredProducts) {
                sortedByPrice.put(product.getMinimumPrice(), product);
            }
            filteredProducts.clear();
            filteredProducts.addAll(sortedByPrice.values());
        } else if (sorting.equalsIgnoreCase("rate")) {
            HashMap<Double, Product> sortedByRate = new HashMap<>();
            for (Product product : filteredProducts) {
                sortedByRate.put(product.getSumOfCustomersRate() / product.getCustomersWhoRated(), product);
            }
            filteredProducts.clear();
            filteredProducts.addAll(sortedByRate.values());
        } else {
            HashMap<Integer, Product> sortedByView = new HashMap<>();
            for (Product product : filteredProducts) {
                sortedByView.put(product.getViews(), product);
            }
            filteredProducts.clear();
            filteredProducts.addAll(sortedByView.values());
        }
        return (ArrayList<String>) filteredProducts.stream()
                .map(product -> "name=" + product.getName() + ", price=" + product.getMinimumPrice() + ", rate=" + (product.getSumOfCustomersRate() / product.getCustomersWhoRated()));
    }

    public ArrayList<String> showDigestOfProduct(Product product, User user) {
        ArrayList<String> digestOfProduct = new ArrayList<>();
        if (user instanceof Customer)
            product.addView();
        digestOfProduct.add("name=" + product.getName());
        digestOfProduct.add("rate=" + (product.getSumOfCustomersRate() / product.getCustomersWhoRated()));
        for (Seller seller : product.getSellersOfThisProduct().keySet()) {
            digestOfProduct.add("seller: " + seller + ", price: " + product.getSellersOfThisProduct().get(seller));
        }
        return digestOfProduct;
    }

    public ArrayList<String> showAttributesOfProduct(Product product) {
        SellerController.getInstance().checkTimeOfOffs();
        ArrayList<String> attributes = new ArrayList<>();
        attributes.add("status: " + product.getProductStatus());
        attributes.add("information: " + product.getInformation());
        attributes.add("minimumPrice: " + String.valueOf(product.getMinimumPrice()));
        attributes.add("category: " + product.getCategory().getName());
        attributes.add("rate:" + product.getSumOfCustomersRate() / product.getCustomersWhoRated());
        attributes.add("available:" + product.getAvailable());
        attributes.add("views:" + product.getViews());
        for (String s : product.getSpecialPropertiesRelatedToCategory().keySet()) {
            attributes.add(s + ": " + product.getSpecialPropertiesRelatedToCategory().get(s));
        }
        for (Seller seller : product.getSellersOfThisProduct().keySet()) {
            attributes.add("seller: " + seller.getUsername() + ", price: " + product.getSellersOfThisProduct().get(seller));
        }
        for (Off off : product.getOffs()) {
            attributes.add("seller: " + off.getSeller().getUsername() + ", id: " + off.getOffId() + ", amount: " + off.getOffAmount());
        }
        return attributes;
    }

    public String[] showAvailableSorts() {
        return (new String[]{"price", "rate", "view"});
    }

    public ArrayList<String> showAvailableFiltersForUser(User user, String categoryName) {
        ArrayList<String> availableFilters = new ArrayList<>();
        availableFilters.addAll(ManagerController.getCategoryByName(categoryName).getSpecialProperties());
        availableFilters.addAll(Arrays.asList("minimumPrice", "company", "name", "rate", "availability"));
        return availableFilters;
    }

    public void addFilterForUser(User user, String filterKey, String filterValue) {
        if (user.getFilters().keySet().contains(filterKey))
            user.removeFilter(filterKey);
        user.addFilter(filterKey, filterValue);
    }

    private ArrayList<Product> filterAndShowProducts(User user, String categoryName) {
        ArrayList<Product> products;
        Category category;
        if (categoryName == null) {
            products = Product.allProducts;
            category = null;
        } else {
            category = ManagerController.getCategoryByName(categoryName);
            products = category.getProducts();
        }
        return (ArrayList<Product>) products.stream()
                .filter(product -> isContainThisProduct(user.getFilters(), product, category));
    }

    private Boolean isContainThisProduct(HashMap<String, String> filters, Product product, Category category) {
        HashMap<String, String> specialPropertiesOfProduct = product.getSpecialPropertiesRelatedToCategory();
        for (String key : filters.keySet()) {
            if (category != null) {
                if (category.getSpecialProperties().contains(key) && specialPropertiesOfProduct.keySet().contains(key)) {
                    if (!doesMatchWithFilter(filters.get(key), specialPropertiesOfProduct.get(key)))
                        return false;
                }
            } else if (key.equalsIgnoreCase("minimumPrice") && !doesMatchWithFilter(filters.get(key), String.valueOf(product.getMinimumPrice()))) {
                return false;
            } else if (key.equalsIgnoreCase("company") && !doesMatchWithFilter(filters.get(key), product.getCompany())) {
                return false;
            } else if (key.equalsIgnoreCase("name") && !doesMatchWithFilter(filters.get(key), product.getName())) {
                return false;
            } else if (key.equalsIgnoreCase("rate") && !doesMatchWithFilter(filters.get(key), String.valueOf(product.getSumOfCustomersRate() / product.getCustomersWhoRated()))) {
                return false;
            } else if (key.equalsIgnoreCase("availability") && product.getAvailable() == 0) {
                return false;
            }
        }
        return true;
    }

    private Boolean doesMatchWithFilter(String filterValue, String productValue) {
        if (!filterValue.startsWith("[") && filterValue.equalsIgnoreCase(productValue)) {
            return true;
        } else if (filterValue.startsWith("[")) {
            Pattern pattern = Pattern.compile("\\[(\\d+)\\-(\\d+)\\]");
            Matcher matcher = pattern.matcher(filterValue);
            if (!matcher.find())
                return false;
            if (Integer.parseInt(productValue) <= Integer.parseInt(matcher.group(2)) && Integer.parseInt(productValue) >= Integer.parseInt(matcher.group(1))) {
                return true;
            }
        }
        return false;
    }

    public HashMap<String, String> ShowCurrentFilters(User user) {
        return user.getFilters();
    }

    public void disableFilterForUser(User user, String filterKey) throws Exception {
        if (!user.getFilters().keySet().contains(filterKey))
            throw new Exception("User does not have this filter");
        user.removeFilter(filterKey);
    }

    public ArrayList<String> compareTwoProduct(Product product1, String productId2) {
        Product product2 = getProductById(productId2);
        ArrayList<String> information = new ArrayList<>();
        information.add("name:1-" + product1.getName() + ";2-" + product2.getName());
        information.add("minimumPrice:1-" + product1.getMinimumPrice() + ";2-" + product2.getMinimumPrice());
        information.add("rate:1-" + (product1.getSumOfCustomersRate() / product1.getCustomersWhoRated()) + ";2-" + (product2.getSumOfCustomersRate() / product2.getCustomersWhoRated()));
        return information;
    }

    public ArrayList<String> showCommentAboutProduct(Product product) {
        ArrayList<String> allComments = new ArrayList<>();
        for (Comment comment : product.getAllComments()) {
            allComments.add("title: " + comment.getCommentTitle() + ", content: " + comment.getCommentContent() + ", customer: " + comment.getCustomer().getUsername());
        }
        return allComments;
    }

    public void addComment(User user, Product product, String title, String content) {
        new Comment((Customer) user, product, title, content, ((Customer) user).getRecentShoppingProducts().contains(product));
    }

    public static Product getProductById(String productId) {
        for (Product product : Product.allProducts) {
            if (product.getProductId().equals(productId))
                return product;
        }
        return null;
    }
}
