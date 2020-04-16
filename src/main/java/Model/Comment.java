package Model;

public class Comment {
    private Customer customer;
    private Product product;
    private String commentTitle;
    private String commentContent;
    private CommentStatus commentStatus;

    public Comment(Customer customer, Product product, String commentTitle, String commentContent) {
        this.customer = customer;
        this.product = product;
        this.commentTitle = commentTitle;
        this.commentContent = commentContent;
        this.commentStatus = CommentStatus.waiting;
    }

}

enum CommentStatus{
    waiting,accepted,reject;
}
