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
import model.Customer;
import util.TableLoadEvent;
import view.tm.CustomerTable;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable, TableLoadEvent {
    public Button btnAddCustomer;
    public Button btnDeleteCustomer;
    public TableColumn colCusId;
    public TableColumn colCusName;
    public TableColumn colCusAddress;
    public TableColumn colCusContact;
    public TableView<CustomerTable> tblCustomer;
    public TableColumn colCusUpdate;
    public TextField cusSearch;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colCusId.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        colCusName.setCellValueFactory(new PropertyValueFactory<>("cusName"));
        colCusAddress.setCellValueFactory(new PropertyValueFactory<>("cusAddress"));
        colCusContact.setCellValueFactory(new PropertyValueFactory<>("cusContact"));
        colCusUpdate.setCellValueFactory(new PropertyValueFactory<>("button"));

        tblCustomer.getColumns().setAll(colCusId, colCusName, colCusAddress, colCusContact, colCusUpdate);
        loadTableData();

        cusSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search(newValue);
            }
        });



    }
    public void loadTableData() {
            try {
                List<Customer> allCustomers = CustomerController.getAllCustomers();
                ObservableList<CustomerTable> tableData = FXCollections.observableArrayList();
                for (Customer customer : allCustomers) {
                    tableData.add(new CustomerTable(
                            customer.getCusId(),
                            customer.getCusName(),
                            customer.getCusAddress(),
                            customer.getCusContact(),
                            new Button("Update")
                    ));
                }
                tblCustomer.getItems().setAll(tableData);

                for (CustomerTable item: tblCustomer.getItems()) {
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

    public void showUpdateForm(CustomerTable table) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/UpdateCustomer.fxml"));
            Parent parent = loader.load();
            UpdateCustomerFormController controller = loader.<UpdateCustomerFormController>getController();
            controller.cusID.setText(table.getCusId());
            controller.cusName.setText(table.getCusName());
            controller.cusAddress.setText(table.getCusAddress());
            controller.cusContact.setText(table.getCusContact());
            controller.setEvent(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnAddCustomerOnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/AddCustomer.fxml"));
            Parent parent = loader.load();
            AddCustomerFormController controller = loader.<AddCustomerFormController>getController();
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


    public void btnDeleteCustomerOnAction(ActionEvent actionEvent) {
        try {
            String id = tblCustomer.getSelectionModel().getSelectedItem().getCusId();
            boolean isDeleted = CustomerController.deleteCustomer(id);
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


    public void cusSearchOnAction(ActionEvent actionEvent) {
        search(cusSearch.getText());
    }

    private void search(String value) {
        try {
            List<Customer> customers = CustomerController.searchCustomer(value);
            ObservableList<CustomerTable> tableData = FXCollections.observableArrayList();
            for (Customer customer : customers) {
                tableData.add(new CustomerTable(
                        customer.getCusId(),
                        customer.getCusName(),
                        customer.getCusAddress(),
                        customer.getCusContact(),
                        new Button("Update")
                ));
            }
            // Set Data to  table
            tblCustomer.getItems().setAll(tableData);

            // Set OnAction to table button
            for (CustomerTable item : tblCustomer.getItems()) {
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

}
