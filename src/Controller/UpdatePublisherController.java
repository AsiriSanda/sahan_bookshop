package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Publisher;
import util.TableLoadEvent;
import util.ValidationUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class UpdatePublisherController implements Initializable {
    public TextField pubID;
    public TextField pubName;
    public TextField pubTitle;
    public TextField pubPayment;
    public Button btnUpdatePublisher;

    private TableLoadEvent event;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern pubIDPattern = Pattern.compile("^(P-)[0-9]{3,4}$");
    Pattern pubNamePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern pubTitlePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern pubPaymentPattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnUpdatePublisher.setDisable(true);
        storeValidations();
    }

    private void storeValidations() {
        map.put(pubID, pubIDPattern);
        map.put(pubName, pubNamePattern);
        map.put(pubTitle, pubTitlePattern);
        map.put(pubPayment, pubPaymentPattern);
    }

    public void btnUpdatePublisherOnAction(ActionEvent actionEvent) {
        try {
            boolean isUpdate = PublisherController.updatePublisher(new Publisher(
                    pubID.getText(),
                    pubName.getText(),
                    pubTitle.getText(),
                    Double.parseDouble(pubPayment.getText())
            ));
            if(isUpdate){
                new Alert(Alert.AlertType.INFORMATION,"Success").show();
                event.loadData();
            }else {
                new Alert(Alert.AlertType.ERROR,"Error").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }
    public void setEvent(TableLoadEvent event){
        this.event = event;
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map, btnUpdatePublisher);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Aded").showAndWait();
            }
        }
    }
}
