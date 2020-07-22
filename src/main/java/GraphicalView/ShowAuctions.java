package GraphicalView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowAuctions implements Initializable {
    public TableView tableOfAuctions;
    public TableColumn idColumn;
    public TableColumn productColumn;
    public TableColumn priceColumn;
    public static String auctionToView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addButtonToTable();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("auctionId"));
        productColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("minPrice"));
        try {
            DataBase.getInstance().dataOutputStream.writeUTF(Runner.getInstance().jsonMaker("manager", "getAllAuctions").toString());
            DataBase.getInstance().dataOutputStream.flush();
            String input = DataBase.getInstance().dataInputStream.readUTF();
            analyzeInput((JsonObject) new JsonParser().parse(input));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void analyzeInput(JsonObject jsonObject) {
        ObservableList<AuctionInTable> auctionsInTable = FXCollections.observableArrayList();
        JsonArray auctions = jsonObject.getAsJsonArray("auctions");
        for (JsonElement element : auctions) {
            JsonObject auc = element.getAsJsonObject();
            auctionsInTable.add(new AuctionInTable(auc.get("id").getAsString(), auc.get("product").getAsString(), auc.get("minPrice").getAsInt()));
        }
        tableOfAuctions.setItems(auctionsInTable);
    }

    private void addButtonToTable() {
        TableColumn<AuctionInTable, Void> colBtn = new TableColumn("view");
        Callback<TableColumn<AuctionInTable, Void>, TableCell<AuctionInTable, Void>> cellFactory = new Callback<TableColumn<AuctionInTable, Void>, TableCell<AuctionInTable, Void>>() {
            @Override
            public TableCell<AuctionInTable, Void> call(final TableColumn<AuctionInTable, Void> param) {
                final TableCell<AuctionInTable, Void> cell = new TableCell<AuctionInTable, Void>() {
                    private final Button btn = new Button("view");
                    {
                        btn.setMinWidth(75);
                        btn.setOnAction((ActionEvent event) -> {
                            Runner.buttonSound();
                            auctionToView = getTableView().getItems().get(getIndex()).getAuctionId();
                            Runner.buttonSound();
                            //Runner.getInstance().changeScene();
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
        tableOfAuctions.getColumns().add(colBtn);
    }

    public void back(ActionEvent actionEvent) {
        Runner.buttonSound();
        Runner.getInstance().back();
    }

    public void userArea(ActionEvent actionEvent) {
        Runner.buttonSound();
        Runner.getInstance().setUserAreaScene();
    }

}
