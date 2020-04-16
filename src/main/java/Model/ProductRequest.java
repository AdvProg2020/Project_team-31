package Model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductRequest extends Request {
    private Product product;
    private static ArrayList<ProductRequest> allProductRequest = new ArrayList<>();

    public ProductRequest(Product product) {
        super("productRequest" + allRequests.size()+1);
        this.product = product;
        allProductRequest.add(this);
    }

    @Override
    public void acceptRequest() {

    }

    @Override
    public void declineRequest() {

    }

    @Override
    public String showDetail() {
        return null;
    }
}
