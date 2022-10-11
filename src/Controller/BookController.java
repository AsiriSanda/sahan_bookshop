package Controller;

import db.DBUtil;
import model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookController {
    public static boolean addBook(Book book) throws SQLException, ClassNotFoundException {
        Connection con = DBUtil.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("INSERT INTO book values (?,?,?,?,?,?)");
        pstm.setObject(1,book.getBookId());
        pstm.setObject(2,book.getPubId());
        pstm.setObject(3,book.getBookTitle());
        pstm.setObject(4,book.getBookAuthor());
        pstm.setObject(5,book.getQty());
        pstm.setObject(6,book.getPrice());


        return  pstm.executeUpdate() > 0;
    }

    public static boolean updateBook(Book book) throws SQLException, ClassNotFoundException {
        Connection con = DBUtil.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE book SET pubId=?, bookTitle=?, bookAuthor=?, Qty=?, Price=? WHERE bookId=?");

        pstm.setObject(1,book.getPubId());
        pstm.setObject(2,book.getBookTitle());
        pstm.setObject(3,book.getBookAuthor());
        pstm.setObject(4,book.getQty());
        pstm.setObject(5,book.getPrice());
        pstm.setObject(6,book.getBookId());


        return  pstm.executeUpdate() > 0;
    }

    public static boolean deleteBook(String id) throws SQLException, ClassNotFoundException {
        Connection con = DBUtil.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM book WHERE bookId=?");
        pstm.setObject(1,id);
        return pstm.executeUpdate() > 0;
    }

    public static List<Book > searchBook(String value) throws SQLException, ClassNotFoundException {
        Connection con = DBUtil.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM book WHERE bookTitle LIKE '%"+value+"%'");
        ResultSet rs = pstm.executeQuery();
        List<Book> book = new ArrayList<>();
        while (rs.next()) {
            book.add(new Book(
                    rs.getString("bookId"),
                    rs.getString("pubId"),
                    rs.getString("bookTitle"),
                    rs.getString("bookAuthor"),
                    rs.getInt("Qty"),
                    rs.getDouble("Price")
            ));

        }

        return book;
    }



    public static List<Book> getAllBook() throws SQLException, ClassNotFoundException {
        Connection con = DBUtil.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM book");
        ResultSet rs = pstm.executeQuery();
        List<Book> book = new ArrayList<>();
        while (rs.next()) {
            book.add(new Book(
                    rs.getString("bookId"),
                    rs.getString("pubId"),
                    rs.getString("bookTitle"),
                    rs.getString("bookAuthor"),
                    rs.getInt("Qty"),
                    rs.getDouble("Price")
            ));

        }

        return book;
    }
}
