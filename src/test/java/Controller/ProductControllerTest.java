package Controller;

import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
@RunWith(JMockit.class)
public class ProductControllerTest {
    ProductController productController = ProductController.getInstance();
    @Test
    public void getInstance() {
        assertNotNull(ProductController.getInstance());
    }

    @Test
    public void showProducts() {
    }

    @Test
    public void showDigestOfProduct() {
    }

    @Test
    public void showAttributesOfProduct() {
    }

    @Test
    public void showAvailableFiltersForUser() {
    }

    @Test
    public void addFilterForUser() {
    }

    @Test
    public void showOffProduct() {
    }

    @Test
    public void clearFilters() {
    }

    @Test
    public void showCurrentFilters() {
    }

    @Test
    public void disableFilterForUser() {
    }

    @Test
    public void compareTwoProduct() {
    }

    @Test
    public void showCommentAboutProduct() {
    }

    @Test
    public void addComment() {
    }

    @Test
    public void getProductById() {
    }
}