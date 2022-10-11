package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Customer;
import util.TableLoadEvent;
import util.ValidationUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddCustomerFormController  implements Initializable {
    public TextField cusID;
    public TextField cusName;
    public TextField cusAddress;
    public TextField cusContact;
    public Button btnAddCustomer;

    private TableLoadEvent event;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern cusIDPattern = Pattern.compile("^(C-)[0-9]{3,4}$");
    Pattern cusNamePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern cusAddressPattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern cusContactPattern = Pattern.compile("^(071|072|075|076|077|078)[-]?[0-9]{7}$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnAddCustomer.setDisable(true);
        storeValidations();
    }

    private void storeValidations() {
        map.put(cusID, cusIDPattern);
        map.put(cusName, cusNamePattern);
        map.put(cusAddress, cusAddressPattern);
        map.put(cusContact, cusContactPattern);
    }



    public void btnAddCustomerOnAction(ActionEvent actionEvent) {
        try {
            Customer customer = new Customer(
                    cusID.getText(),
                    cusName.getText(),
                    cusAddress.getText(),
                    cusContact.getText()
            );
            boolean isSave = CustomerController.addCustomer(customer);
            if (isSave) {
                new Alert(Alert.AlertType.INFORMATION, "Success").show();
                event.loadData();
            } else {
                new Alert(Alert.AlertType.ERROR, "Error").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        cusID.clear();
        cusName.clear();
        cusAddress.clear();
        cusContact.clear();


    }

    public void setEvent(TableLoadEvent event) {
        this.event = event;
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map, btnAddCustomer);

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
