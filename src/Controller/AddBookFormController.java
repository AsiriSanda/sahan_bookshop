package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Book;
import util.TableLoadEvent;
import util.ValidationUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddBookFormController implements Initializable {
    public TextField bookID;
    public TextField pubID;
    public TextField bookTitle;
    public TextField bookAuthor;
    public TextField qty;
    public TextField price;
    public Button btnAddBooks;

    private TableLoadEvent event;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern bookIDPattern = Pattern.compile("^(B-)[0-9]{3,4}$");
    Pattern pubIDPattern = Pattern.compile("^(P-)[0-9]{3,4}$");
    Pattern bookTitlePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern bookAuthorPattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern qtyPattern = Pattern.compile("^[1-9][0-9]*$");
    Pattern pricePattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnAddBooks.setDisable(true);
        storeValidations();
    }

    private void storeValidations() {
        map.put(bookID, bookIDPattern);
        map.put(pubID, pubIDPattern);
        map.put(bookTitle, bookTitlePattern);
        map.put(bookAuthor, bookAuthorPattern);
        map.put(qty, qtyPattern);
        map.put(price, pricePattern);
    }

    public void btnAddBooksOnAction(ActionEvent actionEvent) {
        try {
            Book book = new Book(
                    bookID.getText(),
                    pubID.getText(),
                    bookTitle.getText(),
                    bookAuthor.getText(),
                    Integer.parseInt(qty.getText()),
                    Double.parseDouble(price.getText())

            );
            boolean isSave = BookController.addBook(book);
            if (isSave) {
                new Alert(Alert.AlertType.INFORMATION, "Success").show();
                event.loadData();
            } else {
                new Alert(Alert.AlertType.ERROR, "Error").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
          bookID.clear();
          pubID.clear();
          bookTitle.clear();
          bookAuthor.clear();
          qty.clear();
          price.clear();
    }


    public void setEvent(TableLoadEvent event) {
        this.event = event;
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map, btnAddBooks);

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
