package GraphicalView;

import Model.Product;
import Model.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class RequestMenu implements Initializable {
    public TableColumn idColumn;
    public TableView tableOfRequest;
    public static Request request;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("requestId"));
        addButton();
        setTable();
    }

    private void setTable() {
        ObservableList<Request> requests = FXCollections.observableArrayList();
        requests.addAll(Request.getAllRequest());
        tableOfRequest.setItems(requests);
    }

    private void addButton() {
        TableColumn<Request, Void> colBtn = new TableColumn();
        Callback<TableColumn<Request, Void>, TableCell<Request, Void>> cellFactory = new Callback<TableColumn<Request, Void>, TableCell<Request, Void>>() {
            @Override
            public TableCell<Request, Void> call(final TableColumn<Request, Void> param) {
                final TableCell<Request, Void> cell = new TableCell<Request, Void>() {
                    private final Button btn = new Button("detail");

                    {
                        btn.setMinWidth(75);
                        btn.setOnAction((ActionEvent event) -> {
                            Runner.buttonSound();
                            request = getTableView().getItems().get(getIndex());
                            showDetail();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        tableOfRequest.getColumns().add(colBtn);
    }

    private void showDetail() {
        Runner.buttonSound();
        Runner.getInstance().changeScene("RequestArea.fxml");
    }

    public void back(MouseEvent mouseEvent) {
        Runner.buttonSound();
        Runner.getInstance().back();
    }
}
