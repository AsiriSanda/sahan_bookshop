package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Staff;
import util.TableLoadEvent;
import util.ValidationUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddStaffFormController implements Initializable {
    public TextField staffID;
    public TextField staffName;
    public TextField staffAddress;
    public TextField staffAge;
    public TextField staffContact;
    public Button btnAddStaff;

    private TableLoadEvent event;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern staffIDPattern = Pattern.compile("^(S-)[0-9]{3,4}$");
    Pattern staffNamePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern staffAddressPattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern staffAgePattern = Pattern.compile("^[0-9]{2}$");
    Pattern staffContactPattern = Pattern.compile("^(071|072|075|076|077|078)[-]?[0-9]{7}$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnAddStaff.setDisable(true);
        storeValidations();
    }

    private void storeValidations() {
        map.put(staffID, staffIDPattern);
        map.put(staffName, staffNamePattern);
        map.put(staffAddress, staffAddressPattern);
        map.put(staffAge, staffAgePattern);
        map.put(staffContact, staffContactPattern);
    }

    public void btnAddStaffOnAction(ActionEvent actionEvent) {
        try {
            Staff staff = new Staff(
                    staffID.getText(),
                    staffName.getText(),
                    staffAddress.getText(),
                    Integer.parseInt(staffAge.getText()),
                    staffContact.getText()
            );
            boolean isSave = StaffController.addStaff(staff);
            if (isSave) {
                new Alert(Alert.AlertType.INFORMATION, "Success").show();
                event.loadData();
            } else {
                new Alert(Alert.AlertType.ERROR, "Error").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        staffID.clear();
        staffName.clear();
        staffAddress.clear();
        staffAge.clear();
        staffContact.clear();


    }

    public void setEvent(TableLoadEvent event) {
        this.event = event;
    }


    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map, btnAddStaff);

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
