package Model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductRequest extends Request {
    private Product product;

    public ProductRequest(Product product) {
        super("productRequest" + allRequests.size()+1);
        this.product = product;
        allRequests.add(this);
    }

    @Override
    public String showDetail() {
        return null;
    }

    public Product getProduct() {
        return product;
    }
}
