package Model;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
@RunWith(JMockit.class)
public class CommentTest {
    @Injectable
    Customer customer;
    @Injectable
    Product product;
    @Test
    public void acceptedStatus() {
        Comment comment = new Comment(customer ,product , "commentTitle" , "commentContent" , true );

        new Expectations(){
            {
               comment.getCommentStatus();
            }
        };
        comment.acceptedStatus();
        assertNotNull(customer);
    }

    @Test
    public void rejectedStatus() {
        Comment comment = new Comment(customer ,product , "commentTitle" , "commentContent" , true );

        new Expectations(){
            {
                comment.getCommentStatus();
            }
        };
        comment.rejectedStatus();
    }

    @Test
    public void getCustomer() {
        Comment comment = new Comment(customer ,product , "commentTitle" , "commentContent" , true );

        assertEquals(customer , comment.getCustomer());
    }

    @Test
    public void getProduct() {
        Comment comment = new Comment(customer ,product , "commentTitle" , "commentContent" , true );
        assertEquals(product , comment.getProduct());
    }

    @Test
    public void getCommentTitle() {
        Comment comment = new Comment(customer ,product , "commentTitle" , "commentContent" , true );
        assertEquals("commentTitle" , comment.getCommentTitle());
    }

    @Test
    public void getIsBuyer() {
        Comment comment = new Comment(customer ,product , "commentTitle" , "commentContent" , true );
        assertEquals(true , comment.getIsBuyer());

    }

    @Test
    public void getCommentContent() {
        Comment comment = new Comment(customer ,product , "commentTitle" , "commentContent" , true );
        assertEquals("commentContent" , comment.getCommentContent());
    }
}