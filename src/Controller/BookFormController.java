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
import model.Book;
import util.TableLoadEvent;
import view.tm.BookTable;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class BookFormController  implements Initializable, TableLoadEvent {
    public Button btnAddBooks;
    public Button btnDeleteBooks;
    public TextField bookSearch;
    public TableColumn colBookId;
    public TableColumn colPubId;
    public TableColumn colTitle;
    public TableColumn colAuthor;
    public TableColumn colQty;
    public TableColumn colPrice;
    public TableColumn colBookUpdate;
    public TableView<BookTable> tblBook;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colPubId.setCellValueFactory(new PropertyValueFactory<>("pubId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("bookAuthor"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        colBookUpdate.setCellValueFactory(new PropertyValueFactory<>("button"));

        tblBook.getColumns().setAll(colBookId, colPubId, colTitle, colAuthor, colQty ,colPrice ,colBookUpdate );
        loadTableData();

        bookSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search(newValue);
            }
        });

    }


    public void loadTableData() {
        try {
            List<Book> allBook = BookController.getAllBook();
            ObservableList<BookTable> tableData = FXCollections.observableArrayList();
            for (Book book : allBook) {
                tableData.add(new BookTable(
                        book.getBookId(),
                        book.getPubId(),
                        book.getBookTitle(),
                        book.getBookAuthor(),
                        book.getQty(),
                        book.getPrice(),
                        new Button("Update")
                ));
            }
            tblBook.getItems().setAll(tableData);

            for (BookTable item: tblBook.getItems()){
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

    public void showUpdateForm(BookTable table) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/UpdateBook.fxml"));
            Parent parent = loader.load();
            UpdateBookFormController controller = loader.<UpdateBookFormController>getController();
            controller.bookID.setText(table.getBookId());
            controller.pubID.setText(table.getPubId());
            controller.bookTitle.setText(table.getBookTitle());
            controller.bookAuthor.setText(table.getBookAuthor());
            controller.qty.setText(String.valueOf(table.getQty()));
            controller.price.setText(String.valueOf(table.getPrice()));
            controller.setEvent(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void btnAddBooksOnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/AddBook.fxml"));
            Parent parent = loader.load();
            AddBookFormController controller = loader.<AddBookFormController>getController();
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


    public void btnDeleteBooksOnAction(ActionEvent actionEvent) {
        try {
            String id = tblBook.getSelectionModel().getSelectedItem().getBookId();
            boolean isDeleted = BookController.deleteBook(id);
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


    public void bookSearchOnAction(ActionEvent actionEvent) {
        search(bookSearch.getText());
    }

    private void search(String value) {
        try {
            List<Book> book = BookController.searchBook(value);
            ObservableList<BookTable> tableData = FXCollections.observableArrayList();
            for (Book book1 : book) {
                tableData.add(new BookTable(
                        book1.getBookId(),
                        book1.getPubId(),
                        book1.getBookTitle(),
                        book1.getBookAuthor(),
                        book1.getQty(),
                        book1.getPrice(),
                        new Button("Update")
                ));
            }
            // Set Data to  table
            tblBook.getItems().setAll(tableData);

            // Set OnAction to table button
            for (BookTable item : tblBook.getItems()) {
                item.getButton().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        showUpdateForm((BookTable) item);
                    }
                });
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
