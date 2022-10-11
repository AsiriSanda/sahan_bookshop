package Controller;

import db.DBUtil;
import model.Book;
import model.Order;
import model.OrderDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderController {

    public static List<String> getAllCustomersId() throws SQLException, ClassNotFoundException {
        Connection connection = DBUtil.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT cusId from customer");
        ResultSet rs = pstm.executeQuery();
        List<String> customersId = new ArrayList<>();
        while (rs.next()) {
            customersId.add(rs.getString("cusId"));
        }
        return customersId;
    }

    public static String getCustomerById(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBUtil.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT cusName from customer  WHERE cusId=?");
        pstm.setObject(1, id);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return rs.getString("cusName");
        }
        return null;
    }

    public static List<String> getAllItemIds() throws SQLException, ClassNotFoundException {
        Connection connection = DBUtil.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT bookId from book");
        ResultSet rs = pstm.executeQuery();
        List<String> itemId = new ArrayList<>();
        while (rs.next()) {
            itemId.add(rs.getString("bookId"));
        }
        return itemId;
    }

    public static Book getItemById(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBUtil.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * from book  WHERE bookId=?");
        pstm.setObject(1, id);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return new Book(
                    rs.getString("bookId"),
                    rs.getString("pubId"),
                    rs.getString("bookTitle"),
                    rs.getString("bookAuthor"),
                    rs.getInt("qty"),
                    rs.getDouble("price")
            );
        }
        return null;
    }

    public static String getLastOrderId() throws SQLException, ClassNotFoundException {
        Connection connection = DBUtil.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT orderId FROM `order` ORDER BY orderId DESC LIMIT 1");
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return rs.getString("orderId");
        }
        return null;
    }

    public static boolean placeOrder(Order order) {
        Connection connection = null;
        try {
            connection = DBUtil.getInstance().getConnection();
            connection.setAutoCommit(false);

            PreparedStatement pstm = connection.prepareStatement("INSERT INTO `order` values(?,?,?,?)");
            pstm.setObject(1, order.getOrderId());
            pstm.setObject(2, order.getCusId());
            pstm.setObject(3, order.getDate());
            pstm.setObject(4, order.getTotalAmount());

            boolean isOrderPlaced = pstm.executeUpdate() > 0;
            if (isOrderPlaced) {
                boolean isSuccess = addOrderDetails(order.getDetails());
                if (!isSuccess) {
                    connection.rollback();
                    return false;
                }
            }
            connection.commit();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private static boolean addOrderDetails(List<OrderDetails> list) throws SQLException, ClassNotFoundException {
        Connection connection = DBUtil.getInstance().getConnection();
        for (OrderDetails details : list) {
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO orderdetails VALUES (?,?,?,?,?)");
            pstm.setObject(1, details.getOrderId());
            pstm.setObject(2, details.getBookId());
            pstm.setObject(3, details.getQty());
            pstm.setObject(4, details.getUnitPrice());
            pstm.setObject(5, details.getTotalAmount());
            boolean isAdded = pstm.executeUpdate() > 0;
            if (isAdded) {
                boolean isSuccess = updateQty(details.getBookId(), details.getQty());
                if (!isSuccess) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean updateQty(String id, int qty) {
        try {
            Connection connection = DBUtil.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("UPDATE book SET qty= qty-? WHERE bookId=?");
            pstm.setObject(1, qty);
            pstm.setObject(2, id);
            return pstm.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


}
