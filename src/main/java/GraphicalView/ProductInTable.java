package GraphicalView;

import java.util.HashMap;

public class ProductInTable {
    private String productId;
    private String name;
    private String rate;
    private int minimumPrice;
    private String categoryName;
    private int available;
    private int views;
    private String company;
    private HashMap<String, String> specialPropertiesRelatedToCategory;

    public ProductInTable(String productId, String name, String rate, int minimumPrice, String categoryName, int available, int views, String company, HashMap<String, String> specialPropertiesRelatedToCategory) {
        this.productId = productId;
        this.name = name;
        this.rate = rate;
        this.minimumPrice = minimumPrice;
        this.categoryName = categoryName;
        this.available = available;
        this.views = views;
        this.company = company;
        this.specialPropertiesRelatedToCategory = specialPropertiesRelatedToCategory;
    }

    public String getCompany() {
        return company;
    }

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getRate() {
        return rate;
    }

    public int getMinimumPrice() {
        return minimumPrice;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getAvailable() {
        return available;
    }

    public int getViews() {
        return views;
    }

    public HashMap<String, String> getSpecialPropertiesRelatedToCategory() {
        return specialPropertiesRelatedToCategory;
    }
}