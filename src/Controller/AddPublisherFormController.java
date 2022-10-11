package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Book;
import model.Publisher;
import util.TableLoadEvent;
import util.ValidationUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddPublisherFormController implements Initializable {
    public TextField pubID;
    public TextField pubName;
    public TextField pubTitle;
    public TextField pubPayment;
    public Button btnAddPublisher;

    private TableLoadEvent event;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern pubIDPattern = Pattern.compile("^(P-)[0-9]{3,4}$");
    Pattern pubNamePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern pubTitlePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern pubPaymentPattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnAddPublisher.setDisable(true);
        storeValidations();
    }

    private void storeValidations() {
        map.put(pubID, pubIDPattern);
        map.put(pubName, pubNamePattern);
        map.put(pubTitle, pubTitlePattern);
        map.put(pubPayment, pubPaymentPattern);
    }

    public void btnAddPublisherOnAction(ActionEvent actionEvent) {
        try {
            Publisher publisher = new Publisher(
                    pubID.getText(),
                    pubName.getText(),
                    pubTitle.getText(),
                    Double.parseDouble(pubPayment.getText())

            );
            boolean isSave = PublisherController.addPublisher(publisher);
            if (isSave) {
                new Alert(Alert.AlertType.INFORMATION, "Success").show();
                event.loadData();
            } else {
                new Alert(Alert.AlertType.ERROR, "Error").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        pubID.clear();
        pubName.clear();
        pubTitle.clear();
        pubPayment.clear();
    }
    public void setEvent(TableLoadEvent event) {
        this.event = event;
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map, btnAddPublisher);

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
