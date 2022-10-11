package Controller;

import db.DBUtil;
import model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerController {

    public static boolean addCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        Connection con = DBUtil.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("INSERT INTO customer values (?,?,?,?)");
        pstm.setObject(1,customer.getCusId());
        pstm.setObject(2,customer.getCusName());
        pstm.setObject(3,customer.getCusAddress());
        pstm.setObject(4,customer.getCusContact());


        return  pstm.executeUpdate() > 0;
    }

    public static boolean updateCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        Connection con = DBUtil.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE customer SET cusName=?, cusAddress=?, cusContact=? WHERE cusId=?");

        pstm.setObject(1,customer.getCusName());
        pstm.setObject(2,customer.getCusAddress());
        pstm.setObject(3,customer.getCusContact());
        pstm.setObject(4,customer.getCusId());


        return  pstm.executeUpdate() > 0;
    }

    public static boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        Connection con = DBUtil.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM customer WHERE cusId=?");
        pstm.setObject(1,id);
        return pstm.executeUpdate() > 0;
    }

    public static List<Customer> searchCustomer(String value) throws SQLException, ClassNotFoundException {
        Connection con = DBUtil.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM customer WHERE cusName LIKE '%"+value+"%'");
        ResultSet rs = pstm.executeQuery();
        List<Customer> customers = new ArrayList<>();
        while (rs.next()) {
            customers.add(new Customer(
                    rs.getString("cusId"),
                    rs.getString("cusName"),
                    rs.getString("cusAddress"),
                    rs.getString("cusContact")
            ));
        }

        return customers;
    }



    public static List<Customer> getAllCustomers() throws SQLException, ClassNotFoundException {
        Connection con = DBUtil.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM customer");
        ResultSet rs = pstm.executeQuery();
        List<Customer> customers = new ArrayList<>();
        while (rs.next()) {
            customers.add(new Customer(
                    rs.getString("cusId"),
                    rs.getString("cusName"),
                    rs.getString("cusAddress"),
                    rs.getString("cusContact")
            ));

        }

        return customers;
    }


}

