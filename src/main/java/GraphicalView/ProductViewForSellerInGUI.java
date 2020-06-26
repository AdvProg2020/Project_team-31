package GraphicalView;

import Controller.SellerController;
import GraphicalView.DataBase;
import GraphicalView.ProductsMenu;
import GraphicalView.Runner;
import Model.Product;
import Model.Seller;
import javafx.scene.control.Button;

public class ProductViewForSellerInGUI {
    Product product;
    String productId;
    Button view;
    Button edit;
    Button delete;
    static Runner runner = Runner.getInstance();

    public ProductViewForSellerInGUI(Product product, Button view, Button edit, Button delete) {
        this.product = product;
        this.productId = product.getProductId();
        this.view = view;
        this.edit = edit;
        this.delete = delete;
        view.setOnAction(event -> viewProduct());
        edit.setOnAction(event -> editProduct());
        delete.setOnAction(event -> deleteProduct());
    }

    private void deleteProduct() {
        try {
            if (DataBase.getInstance().user instanceof Seller)
                SellerController.getInstance().removeProductFromUser(DataBase.getInstance().user, productId);
            else {
                SellerController.getInstance().removeProduct(productId);
            }
            runner.back();
//            runner.changeScene("ManageProducts.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void editProduct() {
        Runner.buttonSound();
        ProductsMenu.product = product;
        runner.changeScene("EditProduct.fxml");
    }

    private void viewProduct() {
        Runner.buttonSound();
        ProductsMenu.product = product;
        runner.changeScene("ProductArea.fxml");
    }

    public Product getProduct() {
        return product;
    }

    public String getProductId() {
        return productId;
    }

    public Button getView() {
        return view;
    }

    public Button getEdit() {
        return edit;
    }

    public Button getDelete() {
        return delete;
    }
}
