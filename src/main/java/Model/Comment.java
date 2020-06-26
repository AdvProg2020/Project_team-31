package Model;

import Controller.LoginController;

import java.io.Serializable;

public class Comment implements Serializable {
    private String customer;
    private String commentTitle;
    private String commentContent;
    private Boolean isBuyer;
    private CommentStatus commentStatus;

    public Comment(Customer customer, String commentTitle, String commentContent, Boolean isBuyer) {
        this.isBuyer = isBuyer;
        this.customer = customer.getUsername();
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
        return (Customer) LoginController.getUserByUsername(customer);
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
