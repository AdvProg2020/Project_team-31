package GraphicalView;

public class ProductShowInLog {
    String productName;
    String number;

    public String getProductName() {
        return productName;
    }

    public String getNumber() {
        return number;
    }

    public String getSeller() {
        return seller;
    }

    String seller;

    public ProductShowInLog(String productName, String number, String seller) {
        this.productName = productName;
        this.number = number;
        this.seller = seller;
    }
}