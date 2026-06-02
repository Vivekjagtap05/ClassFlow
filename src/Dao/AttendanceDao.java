package Dao;

import java.sql.*;
import java.util.*;
import model.Attendance;

public class AttendanceDao {

    public boolean insertAttendance(Attendance a) {
        try {
            Connection con = DBconnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO attendance VALUES (?,?,?)");

            ps.setInt(1, a.getStudentId());
            ps.setInt(2, a.getTotalDays());
            ps.setInt(3, a.getPresentDays());
            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateAttendance(Attendance a) {
        try {
            Connection con = DBconnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "UPDATE attendance SET total_days=?, present_days=? WHERE student_id=?");

            ps.setInt(1, a.getTotalDays());
            ps.setInt(2, a.getPresentDays());
            ps.setInt(3, a.getStudentId());
            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteAttendance(int studentId) {
        try {
            Connection con = DBconnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM attendance WHERE student_id=?");

            ps.setInt(1, studentId);
            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public Attendance getAttendanceById(int studentId) {
        try {
            Connection con = DBconnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM attendance WHERE student_id=?");

            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Attendance(
                    rs.getInt("student_id"),
                    rs.getInt("total_days"),
                    rs.getInt("present_days")
                );
            }
        } catch (Exception e) {}
        return null;
    }

    public List<Attendance> getAllAttendance() {
        List<Attendance> list = new ArrayList<>();
        try {
            Connection con = DBconnection.getConnection();
            ResultSet rs =
                con.prepareStatement("SELECT * FROM attendance").executeQuery();

            while (rs.next()) {
                list.add(new Attendance(
                    rs.getInt("student_id"),
                    rs.getInt("total_days"),
                    rs.getInt("present_days")
                ));
            }
        } catch (Exception e) {}
        return list;
    }

	public Attendance getAttendanceByStudentId(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}
