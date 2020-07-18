package GraphicalView;

import com.google.gson.JsonObject;
import javafx.scene.control.Button;

public class ProductViewForSellerInGUI {
    String productId;
    Button view;
    Button edit;
    Button delete;
    static Runner runner = Runner.getInstance();

    public ProductViewForSellerInGUI(String productId, Button view, Button edit, Button delete) {
        this.productId = productId;
        this.view = view;
        this.edit = edit;
        this.delete = delete;
        view.setOnAction(event -> viewProduct());
        edit.setOnAction(event -> editProduct());
        delete.setOnAction(event -> deleteProduct());
    }

    private void deleteProduct() {
        try {
            JsonObject output = runner.jsonMaker("manager", "deleteProduct");
            output.addProperty("id", productId);
            DataBase.getInstance().dataOutputStream.writeUTF(output.toString());
            DataBase.getInstance().dataOutputStream.flush();
            DataBase.getInstance().dataInputStream.readUTF();

            runner.back();
//            runner.changeScene("ManageProducts.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void editProduct() {
        Runner.buttonSound();
        ProductsMenu.productId = productId;
        runner.changeScene("EditProduct.fxml");
    }

    private void viewProduct() {
        Runner.buttonSound();
        ProductsMenu.productId = productId;
        runner.changeScene("ProductArea.fxml");
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
