package Model;

public class Score {
    private Customer customer;
    private Double score;
    private Product product;

    public Score(Customer customer, Double score, Product product) {
        this.customer = customer;
        this.score = score;
        this.product = product;
        //this.product.addCustomerWhoRated(this.customer);
    }
}
