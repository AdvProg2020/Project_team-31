package GraphicalView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateAuction {
    public DatePicker endDate;
    public TextField endTime;
    public TextField product;
    public TextField startTime;
    public DatePicker startDate;

    public void submit(MouseEvent mouseEvent) {
        Runner.buttonSound();
        if (isInvalid() != null) {
            Alert error = new Alert(Alert.AlertType.ERROR, "please enter a valid " + isInvalid(), ButtonType.OK);
            error.show();
        } else if (getEndDate().before(getStartDate())) {
            Alert error = new Alert(Alert.AlertType.ERROR, "end date is before start date!", ButtonType.OK);
            error.show();
        } else {
            addAuction();
        }
    }

    public void addAuction() {
        JsonObject output = Runner.getInstance().jsonMaker("seller", "createAuction");
        output.addProperty("product", product.getText());
        output.addProperty("start", startDate.getEditor().getText() + " " + startTime.getText());
        output.addProperty("end", endDate.getEditor().getText() + " " + endTime.getText());
        JsonObject jsonObject = null;
        try {
            DataBase.getInstance().dataOutputStream.writeUTF(output.toString());
            DataBase.getInstance().dataOutputStream.flush();
            String input = DataBase.getInstance().dataInputStream.readUTF();
            jsonObject = (JsonObject) new JsonParser().parse(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(jsonObject.get("type").getAsString().equals("failed")) {
            Alert error = new Alert(Alert.AlertType.ERROR, jsonObject.get("message").getAsString(), ButtonType.OK);
            error.show();
        } else {
            Alert error = new Alert(Alert.AlertType.INFORMATION, "auction created successfully", ButtonType.OK);
            error.show();
            Runner.getInstance().back();
        }
    }

    private Date getStartDate() {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm");
        String dateString = startDate.getEditor().getText() + " " + startTime.getText();
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    private Date getEndDate() {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm");
        String dateString = endDate.getEditor().getText() + " " + endTime.getText();
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String isInvalid() {
        if (startDate.getEditor().getText().equals(""))
            return "start date";
        else if (endDate.getEditor().getText().equals(""))
            return "end date";
        else if (startTime.getText().equals(""))
            return "start time";
        else if (endTime.getText().equals(""))
            return "end time";
        else if (product.getText().equals(""))
            return "product";
        return null;
    }

    public void back(MouseEvent mouseEvent) {
        Runner.buttonSound();
        Runner.getInstance().back();
    }


}
