package Controller;

import db.DBUtil;
import model.Book;
import model.Publisher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PublisherController {
    public static boolean addPublisher(Publisher publisher) throws SQLException, ClassNotFoundException {
        Connection con = DBUtil.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("INSERT INTO publisher values (?,?,?,?)");
        pstm.setObject(1,publisher.getPubId());
        pstm.setObject(2,publisher.getPubName());
        pstm.setObject(3,publisher.getPubTitle());
        pstm.setObject(4,publisher.getPubPayments());

        return  pstm.executeUpdate() > 0;
    }

    public static boolean updatePublisher(Publisher publisher) throws SQLException, ClassNotFoundException {
        Connection con = DBUtil.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE publisher SET pubName=?, pubTitle=?, pubPayments=?, WHERE pubId=?");

        pstm.setObject(1,publisher.getPubName());
        pstm.setObject(2,publisher.getPubTitle());
        pstm.setObject(3,publisher.getPubPayments());
        pstm.setObject(4,publisher.getPubId());


        return  pstm.executeUpdate() > 0;
    }

    public static boolean deletePublisher(String id) throws SQLException, ClassNotFoundException {
        Connection con = DBUtil.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM publisher WHERE pubId=?");
        pstm.setObject(1,id);
        return pstm.executeUpdate() > 0;
    }

    public static List<Publisher > searchPublisher(String value) throws SQLException, ClassNotFoundException {
        Connection con = DBUtil.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM publisher WHERE pubTitle LIKE '%"+value+"%'");
        ResultSet rs = pstm.executeQuery();
        List<Publisher> publisher = new ArrayList<>();
        while (rs.next()) {
            publisher.add(new Publisher(
                    rs.getString("pubId"),
                    rs.getString("pubName"),
                    rs.getString("pubTitle"),
                    rs.getDouble("pubPayments")
            ));

        }

        return publisher;
    }



    public static List<Publisher> getAllPublisher() throws SQLException, ClassNotFoundException {
        Connection con = DBUtil.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM publisher");
        ResultSet rs = pstm.executeQuery();
        List<Publisher> publisher = new ArrayList<>();
        while (rs.next()) {
            publisher.add(new Publisher(
                    rs.getString("pubId"),
                    rs.getString("pubName"),
                    rs.getString("pubTitle"),
                    rs.getDouble("pubPayments")
            ));

        }

        return publisher;
    }
}
