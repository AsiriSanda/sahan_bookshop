package Controller;

import db.DBUtil;
import model.Staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffController {
    
    public static boolean addStaff(Staff staff) throws SQLException, ClassNotFoundException {
        Connection con = DBUtil.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("INSERT INTO staff values (?,?,?,?,?)");
        pstm.setObject(1,staff.getStaffId());
        pstm.setObject(2,staff.getStaffName());
        pstm.setObject(3,staff.getStaffAddress());
        pstm.setObject(4,staff.getStaffAge());
        pstm.setObject(5,staff.getStaffContact());


        return  pstm.executeUpdate() > 0;
    }

    public static boolean updateStaff(Staff staff) throws SQLException, ClassNotFoundException {
        Connection con = DBUtil.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("UPDATE staff SET staffName=?, staffAddress=?, staffAge=?, staffContact=? WHERE staffId=?");

        pstm.setObject(1,staff.getStaffName());
        pstm.setObject(2,staff.getStaffAddress());
        pstm.setObject(3,staff.getStaffAge());
        pstm.setObject(4,staff.getStaffContact());
        pstm.setObject(5,staff.getStaffId());


        return  pstm.executeUpdate() > 0;
    }

    public static boolean deleteStaff(String id) throws SQLException, ClassNotFoundException {
        Connection con = DBUtil.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM staff WHERE staffId=?");
        pstm.setObject(1,id);
        return pstm.executeUpdate() > 0;
    }

    public static List<Staff > searchStaff(String value) throws SQLException, ClassNotFoundException {
        Connection con = DBUtil.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM staff WHERE staffName LIKE '%"+value+"%'");
        ResultSet rs = pstm.executeQuery();
        List<Staff> staff = new ArrayList<>();
        while (rs.next()) {
            staff.add(new Staff(
                    rs.getString("staffId"),
                    rs.getString("staffName"),
                    rs.getString("staffAddress"),
                    rs.getInt("staffAge"),
                    rs.getString("staffContact")
            ));

        }

        return staff;
    }



    public static List<Staff> getAllStaff() throws SQLException, ClassNotFoundException {
        Connection con = DBUtil.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT * FROM staff");
        ResultSet rs = pstm.executeQuery();
        List<Staff> staff = new ArrayList<>();
        while (rs.next()) {
            staff.add(new Staff(
                    rs.getString("staffId"),
                    rs.getString("staffName"),
                    rs.getString("staffAddress"),
                    rs.getInt("staffAge"),
                    rs.getString("staffContact")
            ));

        }

        return staff;
    }

}
