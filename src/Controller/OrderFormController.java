package Controller;

import com.jfoenix.controls.JFXComboBox;
import db.DBUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Book;
import model.Order;
import model.OrderDetails;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.tm.OrderTable;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class OrderFormController implements Initializable {
    public Button btnAddToCart;
    public Button btnPlaceOrder;
    public JFXComboBox<String> cmbCusId;
    public JFXComboBox<String> cmbBookId;
    public TextField orderQty;
    public TextField bookTitle;
    public TextField itemPrice;
    public TextField cusName;
    public TableView<OrderTable> tblPlaceOrder;
    public TextField bookQty;
    public TextField totalAmount;
    public Label lblOrderId;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colTotal;
    public TableColumn colBookId;
    double total = 0;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        tblPlaceOrder.getColumns().setAll(colBookId, colQty, colUnitPrice, colTotal);


        //
        loadCustomerIds();
        loadItemIds();
        lastOrderId();
        //
        cmbCusId.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                getCustomerName(newValue);
            }
        });

        cmbBookId.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                getItemDetails(newValue);
            }
        });

    }

    public void loadCustomerIds() {
        try {
            List<String> customersId = OrderController.getAllCustomersId();
            cmbCusId.getItems().setAll(FXCollections.observableArrayList(customersId));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadItemIds() {
        try {
            List<String> itemIds = OrderController.getAllItemIds();
            cmbBookId.getItems().setAll(FXCollections.observableArrayList(itemIds));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getCustomerName(String id) {
        try {
            String customerById = OrderController.getCustomerById(id);
            cusName.setText(customerById);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getItemDetails(String id) {
        try {
            Book bookById = OrderController.getItemById(id);
            bookTitle.setText(bookById.getBookTitle());
            bookQty.setText(String.valueOf(bookById.getQty()));
            itemPrice.setText(String.valueOf(bookById.getPrice()));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void lastOrderId() {
        try {
            String orderId = OrderController.getLastOrderId();  //O  001
            String finalId = "O-001";

            if (orderId != null) {

                String[] splitString = orderId.split("-");
                int id = Integer.parseInt(splitString[1]);
                id++;

                if (id < 10) {
                    finalId = "O-00" + id;
                } else if (id < 100) {
                    finalId = "O-0" + id;
                } else {
                    finalId = "O-" + id;
                }
                lblOrderId.setText(finalId);
            } else {
                lblOrderId.setText(finalId);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        addToCart();
    }

    public void addToCart() {
        if (orderQty.getText().isEmpty() || Integer.parseInt(orderQty.getText()) > Integer.parseInt(bookQty.getText())) {
            new Alert(Alert.AlertType.WARNING, "Try again").show();
            return;
        }

        String itemID = cmbBookId.getSelectionModel().getSelectedItem();
        int OrderQty = Integer.parseInt(orderQty.getText());
        double ItemPrice = Double.parseDouble(itemPrice.getText());
        double itemTotal = Double.parseDouble(itemPrice.getText()) * Integer.parseInt(orderQty.getText());

        OrderTable orderTable = new OrderTable(
                itemID,
                OrderQty,
                ItemPrice,
                itemTotal
        );

        int index = findItemOnTable(itemID);
        if (index == -1) {
            tblPlaceOrder.getItems().add(orderTable);
        } else {
            OrderTable table = tblPlaceOrder.getItems().get(index);
            int newQty = table.getQty() + OrderQty;
            double newTotal = table.getTotal() + itemTotal;
            table.setQty(newQty);
            table.setTotal(newTotal);
            tblPlaceOrder.refresh();
        }
        calAmount();
    }

    public int findItemOnTable(String id) {
        for (int i = 0; i < tblPlaceOrder.getItems().size(); i++) {
            if (tblPlaceOrder.getItems().get(i).getBookId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        String orderId = lblOrderId.getText();
        String cusId = cmbCusId.getSelectionModel().getSelectedItem();
        LocalDate date = LocalDate.now();
        double totalAmount = total;

        List<OrderDetails> details = new ArrayList<>();
        for (OrderTable item : tblPlaceOrder.getItems()) {
            details.add(new OrderDetails(
                    orderId,
                    item.getBookId(),
                    item.getQty(),
                    item.getUnitPrice(),
                    item.getTotal()
            ));
        }

        boolean isSuccess = OrderController.placeOrder(new Order(
                orderId,
                cusId,
                date,
                totalAmount,
                details
        ));

        if(isSuccess){
            new Alert(Alert.AlertType.INFORMATION,"Success").show();
            lastOrderId();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try again").show();
        }
//------------------------------------------------------------------
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/ShopReport.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            ObservableList<OrderTable> items = tblPlaceOrder.getItems();//
            //JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DBUtil.getInstance().getConnection());
            //JasperViewer.viewReport(jasperPrint, false);

            String custName = cusName.getText();
            Double totalAm = totalAmount;

            HashMap map = new HashMap();
            map.put("custName", custName);
            map.put("totalAmount", totalAm);

            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, DBUtil.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);

            //JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, new JRBeanArrayDataSource(items.toArray()));
            //JasperViewer.viewReport(jasperPrint, false);
            //if you wanna print the report directly you can use this instead of JasperViewer
            //JasperPrintManager.printReport(jasperPrint,false);

        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

//-----------------------------------------------------------
    }

    public void calAmount() {

        for (OrderTable item : tblPlaceOrder.getItems()) {
            total += item.getTotal();
        }
        totalAmount.setText(total + " /=");
    }


    public void reportBill(MouseEvent mouseEvent) {

    }
}
