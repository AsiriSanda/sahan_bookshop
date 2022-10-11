package Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

public class LogInFormController {
    public Button btnLogin;
    public BorderPane rootPane;
    public TextField user;
    public PasswordField pass;
    public Label warning;

    public void btnLoginOnAction() throws IOException {
        checkLogIn();

    }

    private void checkLogIn() throws IOException {
        if (user.getText().equals("cashier") && pass.getText().equals("123")){
            FXMLLoader fxmlLoader =new FXMLLoader(this.getClass().getResource("../view/CashDashBoard.fxml"));
            Parent rootPane1 = fxmlLoader.load();
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(new Scene(rootPane1));
        }else if(user.getText().equals("manager") && pass.getText().equals("123")){
            FXMLLoader fxmlLoader =new FXMLLoader(this.getClass().getResource("../view/ManDashBoard.fxml"));
            Parent rootPane1 = fxmlLoader.load();
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(new Scene(rootPane1));
        }else if(user.getText().equals("owner") && pass.getText().equals("123")){
            FXMLLoader fxmlLoader =new FXMLLoader(this.getClass().getResource("../view/OwnerDashBoard.fxml"));
            Parent rootPane1 = fxmlLoader.load();
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(new Scene(rootPane1));
        }else if(user.getText().isEmpty() && pass.getText().isEmpty()){
            warning.setText("Please Enter Your Data");
        }else {
            warning.setText("Wrong User Name Or Password");
        }
    }
}
