package GraphicalView;

public class ProductInSellingLog {
    private int totalPriceArrived;
    private int amountOfOff;
    private String productName;

    public ProductInSellingLog(int totalPriceArrived, int amountOfOff, String productName) {
        this.totalPriceArrived = totalPriceArrived;
        this.amountOfOff = amountOfOff;
        this.productName = productName;
    }

    public int getTotalPriceArrived() {
        return totalPriceArrived;
    }

    public int getAmountOfOff() {
        return amountOfOff;
    }

    public String getProductName() {
        return productName;
    }
}
