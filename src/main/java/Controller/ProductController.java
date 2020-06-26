package Controller;

import Model.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ProductController {
    private static ProductController productControllerInstance = new ProductController();

    public ProductController() {
    }

    public static ProductController getInstance() {
        return productControllerInstance;
    }

    public ArrayList<String> showProducts(User user, String categoryName, String sorting) throws Exception {
        ArrayList<Product> products;
        Category category;
        if (categoryName == null) {
            products = Product.allProducts;
            category = null;
        } else {
            category = ManagerController.getCategoryByName(categoryName);
            products = category.getProducts();
        }
        ArrayList<Product> filteredProducts = new ArrayList<>();
        filteredProducts = (ArrayList<Product>) products.stream()
                .filter(product -> isContainThisProduct(user.getFilters(), product, category))
                .collect(Collectors.toList());
        if (filteredProducts.size() == 0) {
            throw new Exception("There is no product with these filters");
        }
        return (ArrayList<String>) sortProduct(filteredProducts, sorting).stream()
                .map(product -> "name=" + product.getName() + ", price=" + product.getMinimumPrice() + ", rate=" + (product.getSumOfCustomersRate() / product.getCustomersWhoRated()))
                .collect(Collectors.toList());
    }

    public ArrayList<Product> showProductInGui(User user, String categoryName) {
        ArrayList<Product> products;
        Category category;
        if (categoryName == null || categoryName.equals("all")) {
            products = Product.allProducts;
            category = null;
        } else {
            category = ManagerController.getCategoryByName(categoryName);
            products = category.getProducts();
        }
        ArrayList<Product> filteredProducts;
        filteredProducts = (ArrayList<Product>) products.stream()
                .filter(product -> isContainThisProduct(user.getFilters(), product, category))
                .collect(Collectors.toList());
        return filteredProducts;
    }

    private ArrayList<Product> sortProduct(ArrayList<Product> filteredProducts, String sorting) {
        ArrayList<Product> sortedProducts = new ArrayList<>();
        if (sorting == null) {
            HashMap<Product, Double> sortedByView = new HashMap<>();
            for (Product product : filteredProducts) {
                sortedByView.put(product, (double) product.getViews());
            }
            sortedProducts.addAll(Arrays.asList(sortProductsByValues(sortedByView)));
        } else if (sorting.equalsIgnoreCase("price")) {
            HashMap<Product, Double> sortedByPrice = new HashMap<>();
            for (Product product : filteredProducts) {
                sortedByPrice.put(product, (double) product.getMinimumPrice());
            }
            sortedProducts.addAll(Arrays.asList(sortProductsByValues(sortedByPrice)));
        } else if (sorting.equalsIgnoreCase("rate")) {
            HashMap<Product, Double> sortedByRate = new HashMap<>();
            for (Product product : filteredProducts) {
                sortedByRate.put(product, (double) (product.getSumOfCustomersRate() / product.getCustomersWhoRated()));
            }
            sortedProducts.addAll(Arrays.asList(sortProductsByValues(sortedByRate)));
        } else {
            HashMap<Product, Double> sortedByView = new HashMap<>();
            for (Product product : filteredProducts) {
                sortedByView.put(product, (double) product.getViews());
            }
            sortedProducts.addAll(Arrays.asList(sortProductsByValues(sortedByView)));
        }

        return sortedProducts;
    }

    private Product[] sortProductsByValues(HashMap<Product, Double> products) {
        Product[] productsArray = new Product[products.keySet().size()];
        productsArray = products.keySet().toArray(productsArray);
        for (int first = 0; first < productsArray.length; first++) {
            for (int second = first + 1; second < productsArray.length; second++) {
                if (products.get(productsArray[first]) < products.get(productsArray[second])) {
                    Product newProduct = productsArray[second];
                    productsArray[second] = productsArray[first];
                    productsArray[first] = newProduct;
                }
            }
        }
        return productsArray;
    }

    public ArrayList<String> showDigestOfProduct(Product product, User user) {
        ArrayList<String> digestOfProduct = new ArrayList<>();
        if (user instanceof Customer)
            product.addView();
        digestOfProduct.add("name=" + product.getName());
        digestOfProduct.add("rate=" + (product.getSumOfCustomersRate() / product.getCustomersWhoRated()));
        digestOfProduct.add("status=" + product.getProductStatus());
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
        attributes.add("minimumPrice: " + product.getMinimumPrice());
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
            attributes.add("seller: " + off.getSeller().getUsername() + ", id: " + off.getOffId() + ", percent: " + off.getOffPercent());
        }
        return attributes;
    }

    public ArrayList<String> showAvailableFiltersForUser(User user, String categoryName) {
        ArrayList<String> availableFilters = new ArrayList<>();
        if (categoryName != null)
            availableFilters.addAll(ManagerController.getCategoryByName(categoryName).getSpecialProperties());
        availableFilters.addAll(Arrays.asList("minimumPrice", "company", "name", "rate", "availability"));
        return availableFilters;
    }

    public ArrayList<String> showAvailableFiltersForUserGui(String categoryName) {
        ArrayList<String> availableFilters = new ArrayList<>();
        if (!categoryName.equals("all"))
            availableFilters.addAll(ManagerController.getCategoryByName(categoryName).getSpecialProperties());
        availableFilters.addAll(Arrays.asList("minimumPrice", "company", "name", "rate", "availability"));
        return availableFilters;
    }

    public void addFilterForUser(User user, String filterKey, String filterValue) {
        if (user.getFilters().keySet().contains(filterKey))
            user.removeFilter(filterKey);
        user.addFilter(filterKey, filterValue);
    }

    public ArrayList<String> showOffProduct(User user, String sorting) throws Exception {
        SellerController.getInstance().checkTimeOfOffs();
        ArrayList<Product> offedProduct = new ArrayList<>();
        for (Product product : Product.allProducts) {
            if (product.getOffs().size() > 0) {
                offedProduct.add(product);
            }
        }
        offedProduct.stream()
                .filter(product -> isContainThisProduct(user.getFilters(), product, null));
        ArrayList<String> offs = new ArrayList<>();
        for (Product product : sortProduct(offedProduct, sorting)) {
            for (Off off : product.getOffs()) {
                offs.add("productId:" + product.getProductId() + ", seller:" + off.getSeller().getUsername() + ", original price: " + product.getSellersOfThisProduct().get(off.getSeller()) + ", offPercent: " + off.getOffPercent());
            }
        }
        if (offs.size() == 0)
            throw new Exception("there is no off with this filters");
        return offs;
    }

    public ArrayList<OffedProduct> showOffProductInGui() {
        SellerController.getInstance().checkTimeOfOffs();
        ArrayList<OffedProduct> offedProduct = new ArrayList<>();
        for (Off off : Off.getAllOffs()) {
            for (Product saleProduct : off.getOnSaleProducts()) {
                offedProduct.add(new OffedProduct(saleProduct, off.getEndTime(), saleProduct.getSellersOfThisProduct().get(off.getSeller()), off.getOffPercent()));
            }
        }
        return offedProduct;
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

    public void clearFilters(User user) {
        for (String s : user.getFilters().keySet()) {
            user.removeFilter(s);
        }
    }

    public HashMap<String, String> ShowCurrentFilters(User user) {
        return user.getFilters();
    }

    public void disableFilterForUser(User user, String filterKey) throws Exception {
        if (!user.getFilters().keySet().contains(filterKey))
            throw new Exception("User does not have this filter");
        user.removeFilter(filterKey);
    }

    public ArrayList<String> compareTwoProduct(Product product1, String productId2) throws Exception {
        Product product2 = getProductById(productId2);
        if (product2 == null) {
            throw new Exception("second product doesn't exist");
        }
        ArrayList<String> information = new ArrayList<>();
        information.add("name:1-" + product1.getName() + ";2-" + product2.getName());
        information.add("minimumPrice:1-" + product1.getMinimumPrice() + ";2-" + product2.getMinimumPrice());
        information.add("rate:1-" + (1.0 * product1.getSumOfCustomersRate() / product1.getCustomersWhoRated()) + ";2-" + (1.0 * product2.getSumOfCustomersRate() / product2.getCustomersWhoRated()));
        information.add("views:1-" + product1.getViews() + ";2-" + product2.getViews());
        for (String s : product1.getSpecialPropertiesRelatedToCategory().keySet()) {
            if (product2.getSpecialPropertiesRelatedToCategory().keySet().contains(s)) {
                information.add(s + ":1-" + product1.getSpecialPropertiesRelatedToCategory().get(s) + ";2-" + product2.getSpecialPropertiesRelatedToCategory().get(s));
            }
        }
        return information;
    }

    public ArrayList<String> showCommentAboutProduct(Product product) {
        ArrayList<String> allComments = new ArrayList<>();
        for (Comment comment : product.getAllComments()) {
            allComments.add("title: " + comment.getCommentTitle() + ", customer: " + comment.getCustomer().getUsername() + ", is buyer: " + comment.getIsBuyer() + "\n content: " + comment.getCommentContent());
        }
        return allComments;
    }

    public void addComment(User user, Product product, String title, String content) {
        product.addComment(new Comment((Customer) user, title, content, ((Customer) user).getRecentShoppingProducts().contains(product)));
    }

    public static Product getProductById(String productId) {
        for (Product product : Product.allProducts) {
            if (product.getProductId().equals(productId))
                return product;
        }
        return null;
    }

    public boolean isThereAnyProduct(String productName) {
        for (Product product : Product.allProducts)
            if (product.getName().equals(productName))
                return true;
        return false;
    }
}
