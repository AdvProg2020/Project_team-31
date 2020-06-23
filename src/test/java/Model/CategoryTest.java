package Model;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;
@RunWith(JMockit.class)
public class CategoryTest {
    ArrayList<String> sample = new ArrayList<>();
    Category category = new Category("firstCategory" , sample);
    @Injectable
    Product product;

    @Test
    public void getName() {
        Assert.assertEquals("firstCategory" , category.getName());
    }

    @Test
    public void getSpecialProperties() {
        Assert.assertEquals(sample , category.getSpecialProperties());
    }

    @Test
    public void addAndGetProduct() {
        new Expectations(){
            {

            }
        };
        category.addProduct(product);
        ArrayList<Product> products= new ArrayList<>();
        products.add(product);
        Assert.assertEquals(products , category.getProducts());
    }

    @Test
    public void deleteCategory() {
        new Expectations(){
            {
                Category.getAllCategories().remove(category);
            }
        };
        category.deleteCategory();
    }

    @Test
    public void setSpecialProperties() {
        ArrayList<String> specialProperties = new ArrayList<>();
        specialProperties.add("good as hell");
        category.setSpecialProperties(specialProperties);
        Assert.assertEquals(specialProperties , category.getSpecialProperties());
    }

    @Test
    public void removeProduct() {
        new Expectations(){
            {
                category.getProducts();
            }
        };
        category.removeProduct(product);
    }

    @Test
    public void logToFile() {
        new Expectations(){
            {

            }
        };
    }

    @Test
    public void fileToLog() {
        new Expectations(){
            {

            }
        };
    }
}