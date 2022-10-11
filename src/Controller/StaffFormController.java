package Controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Staff;
import util.TableLoadEvent;
import view.tm.StaffTable;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class StaffFormController implements Initializable, TableLoadEvent {
    public Button btnAddStaff;
    public Button btnDeleteStaff;
    public TextField staffSearch;
    public TableColumn colStaffId;
    public TableColumn colStaffName;
    public TableColumn colStaffAddress;
    public TableColumn colStaffAge;
    public TableColumn colStaffContact;
    public TableColumn colStaffUpdate;
    public TableView <StaffTable>tblStaff;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colStaffId.setCellValueFactory(new PropertyValueFactory<>("staffId"));
        colStaffName.setCellValueFactory(new PropertyValueFactory<>("staffName"));
        colStaffAddress.setCellValueFactory(new PropertyValueFactory<>("staffAddress"));
        colStaffAge.setCellValueFactory(new PropertyValueFactory<>("staffAge"));
        colStaffContact.setCellValueFactory(new PropertyValueFactory<>("staffContact"));

        colStaffUpdate.setCellValueFactory(new PropertyValueFactory<>("button"));

        tblStaff.getColumns().setAll(colStaffId, colStaffName, colStaffAddress, colStaffAge, colStaffContact ,colStaffUpdate);
        loadTableData();

        staffSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search(newValue);
            }
        });



    }
    public void loadTableData() {
            try {
                List<Staff> allStaff = StaffController.getAllStaff();
                ObservableList<StaffTable> tableData = FXCollections.observableArrayList();
                for (Staff staff : allStaff) {
                    tableData.add(new StaffTable(
                            staff.getStaffId(),
                            staff.getStaffName(),
                            staff.getStaffAddress(),
                            staff.getStaffAge(),
                            staff.getStaffContact(),
                            new Button("Update")
                    ));
                }
                tblStaff.getItems().setAll(tableData);

                for (StaffTable item: tblStaff.getItems()){
                    item.getButton().setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            showUpdateForm(item);
                        }
                    });
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
        }


    }

    public void showUpdateForm(StaffTable table) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/UpdateStaff.fxml"));
            Parent parent = loader.load();
            UpdateStaffFormController controller = loader.<UpdateStaffFormController>getController();
            controller.staffID.setText(table.getStaffId());
            controller.staffName.setText(table.getStaffName());
            controller.staffAddress.setText(table.getStaffAddress());
            controller.staffAge.setText(String.valueOf(table.getStaffAge()));
            controller.staffContact.setText(table.getStaffContact());
            controller.setEvent(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






    public void btnAddStaffOnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/AddStaff.fxml"));
            Parent parent = loader.load();
            AddStaffFormController controller = loader.<AddStaffFormController>getController();
            controller.setEvent(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void loadData() {
        loadTableData();
    }


    public void btnDeleteStaffOnAction(ActionEvent actionEvent) {
        try {
            String id = tblStaff.getSelectionModel().getSelectedItem().getStaffId();
            boolean isDeleted = StaffController.deleteStaff(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Success").show();
                loadTableData();
            } else {
                new Alert(Alert.AlertType.ERROR, "Error").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }



    public void StaffSearchOnAction(ActionEvent actionEvent) {
        search(staffSearch.getText());
    }

    private void search(String value) {
        try {
            List<Staff> staff = StaffController.searchStaff(value);
            ObservableList<StaffTable> tableData = FXCollections.observableArrayList();
            for (Staff staff1 : staff) {
                tableData.add(new StaffTable(
                        staff1.getStaffId(),
                        staff1.getStaffName(),
                        staff1.getStaffAddress(),
                        staff1.getStaffAge(),
                        staff1.getStaffContact(),
                        new Button("Update")
                ));
            }
            // Set Data to  table
            tblStaff.getItems().setAll(tableData);

            // Set OnAction to table button
            for (StaffTable item : tblStaff.getItems()) {
                item.getButton().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        showUpdateForm((StaffTable) item);
                    }
                });
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
