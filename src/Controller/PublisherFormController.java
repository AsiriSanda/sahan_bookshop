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
import model.Publisher;
import util.TableLoadEvent;
import view.tm.PublisherTable;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class PublisherFormController implements Initializable, TableLoadEvent {
    public TextField publisherSearch;

    public TableColumn colPublisherId;
    public TableColumn colPublisherName;
    public TableColumn colPublisherTitle;
    public TableColumn colPublisherPayments;
    public TableColumn colPublisherUpdate;
    public Button btnAddPublisher;
    public Button btnDeletePublisher;
    public TableView<PublisherTable> tblPublisher;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colPublisherId.setCellValueFactory(new PropertyValueFactory<>("pubId"));
        colPublisherName.setCellValueFactory(new PropertyValueFactory<>("pubName"));
        colPublisherTitle.setCellValueFactory(new PropertyValueFactory<>("pubTitle"));
        colPublisherPayments.setCellValueFactory(new PropertyValueFactory<>("pubPayments"));
        colPublisherUpdate.setCellValueFactory(new PropertyValueFactory<>("button"));

        tblPublisher.getColumns().setAll(colPublisherId, colPublisherName, colPublisherTitle, colPublisherPayments, colPublisherUpdate );
        loadTableData();

        publisherSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search(newValue);
            }
        });

    }

    public void loadTableData() {
        try {
            List<Publisher> allPublisher = PublisherController.getAllPublisher();
            ObservableList<PublisherTable> tableData = FXCollections.observableArrayList();
            for (Publisher publisher : allPublisher) {
                tableData.add(new PublisherTable(
                        publisher.getPubId(),
                        publisher.getPubName(),
                        publisher.getPubTitle(),
                        publisher.getPubPayments(),
                        new Button("Update")
                ));
            }
            tblPublisher.getItems().setAll(tableData);

            for (PublisherTable item: tblPublisher.getItems()){
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

    public void showUpdateForm(PublisherTable table) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/UpdatePublisher.fxml"));
            Parent parent = loader.load();
            UpdatePublisherController controller = loader.<UpdatePublisherController>getController();
            controller.pubID.setText(table.getPubId());
            controller.pubName.setText(table.getPubName());
            controller.pubTitle.setText(table.getPubTitle());
            controller.pubPayment.setText(String.valueOf(table.getPubPayments()));
            controller.setEvent(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void btnAddPublisherOnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/AddPublisher.fxml"));
            Parent parent = loader.load();
            AddPublisherFormController controller = loader.<AddPublisherFormController>getController();
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

    public void btnDeletePublisherOnAction(ActionEvent actionEvent) {
        try {
            String id = tblPublisher.getSelectionModel().getSelectedItem().getPubId();
            boolean isDeleted = PublisherController.deletePublisher(id);
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



    public void publisherSearchOnAction(ActionEvent actionEvent) {
        search(publisherSearch.getText());
    }

    private void search(String value) {
        try {
            List<Publisher> publisher = PublisherController.searchPublisher(value);
            ObservableList<PublisherTable> tableData = FXCollections.observableArrayList();
            for (Publisher publisher1 : publisher) {
                tableData.add(new PublisherTable(
                        publisher1.getPubId(),
                        publisher1.getPubName(),
                        publisher1.getPubTitle(),
                        publisher1.getPubPayments(),
                        new Button("Update")
                ));
            }
            // Set Data to  table
            tblPublisher.getItems().setAll(tableData);

            // Set OnAction to table button
            for (PublisherTable item : tblPublisher.getItems()) {
                item.getButton().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        showUpdateForm((PublisherTable) item);
                    }
                });
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



}
