package Controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class OwnerDashBoardFormController implements Initializable {
    public AnchorPane ownerDashBoardFormContext;
    public Button btnSales;
    public Button btnBooks;
    public Button btnCustomers;
    public Button btnStaff;
    public Button btnReports;
    public StackPane panlRoot;
    public Button btnPublisher;
    public Text txtdate;
    public Text txttime;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDateAndTime();

    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String datetxt = simpleDateFormat.format(date);
        txtdate.setText(datetxt);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        actionEvent -> {
                            Calendar time = Calendar.getInstance();
                            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("hh:mm:ss");
                            txttime.setText(simpleDateFormat1.format(time.getTime()));
                        }
                ),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


    public void btnSalesOnAction(ActionEvent actionEvent) {
        try {
            AnchorPane pane;
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/Order.fxml"));
            pane = fxmlLoader.load();
            panlRoot.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnBooksOnAction(ActionEvent actionEvent) {
        try {
            AnchorPane pane;
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/Book.fxml"));
            pane = fxmlLoader.load();
            panlRoot.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnCustomersOnAction(ActionEvent actionEvent) {
        try {
            AnchorPane pane;
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/Customer.fxml"));
            pane = fxmlLoader.load();
            panlRoot.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnStaffOnAction(ActionEvent actionEvent) {
        try {
            AnchorPane pane;
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/Staff.fxml"));
            pane = fxmlLoader.load();
            panlRoot.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnReportsOnAction(ActionEvent actionEvent) {
    }

    public void btnPublisherOnAction(ActionEvent actionEvent) {
        try {
            AnchorPane pane;
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/Publisher.fxml"));
            pane = fxmlLoader.load();
            panlRoot.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
