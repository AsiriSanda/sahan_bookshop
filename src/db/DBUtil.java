package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private static DBUtil dbUtil;
    private Connection connection;

    private DBUtil() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/sahanbookshop", "root", "1234");
    }

    public static DBUtil getInstance() throws SQLException, ClassNotFoundException {
        if(dbUtil == null){
            dbUtil = new DBUtil();
        }
        return dbUtil;
    }

    public Connection getConnection(){
        return this.connection;
    }



}
