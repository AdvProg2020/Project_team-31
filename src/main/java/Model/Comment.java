package Model;

public class Comment {
    private Customer customer;
    private Product product;
    private String commentTitle;
    private String commentContent;
    private Boolean isBuyer;
    private CommentStatus commentStatus;

    public Comment(Customer customer, Product product, String commentTitle, String commentContent, Boolean isBuyer) {
        this.isBuyer = isBuyer;
        this.customer = customer;
        this.product = product;
        this.commentTitle = commentTitle;
        this.commentContent = commentContent;
        this.commentStatus = CommentStatus.waiting;
    }
    public void acceptedStatus(){
        this.commentStatus = CommentStatus.accepted;
    }

    public void rejectedStatus(){
        this.commentStatus = CommentStatus.rejected;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Product getProduct() {
        return product;
    }

    public String getCommentTitle() {
        return commentTitle;
    }

    public Boolean getIsBuyer() {
        return isBuyer;
    }

    public String getCommentContent() {
        return commentContent;
    }

}

enum CommentStatus{
    waiting, accepted, rejected
}
