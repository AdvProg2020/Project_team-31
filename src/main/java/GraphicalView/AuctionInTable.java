package GraphicalView;

public class AuctionInTable {
    String auctionId;
    String productName;
    int minPrice;

    public AuctionInTable(String auctionId, String productName, int minPrice) {
        this.auctionId = auctionId;
        this.productName = productName;
        this.minPrice = minPrice;
    }

    public String getAuctionId() {
        return auctionId;
    }

    public String getProductName() {
        return productName;
    }

    public int getMinPrice() {
        return minPrice;
    }
}
