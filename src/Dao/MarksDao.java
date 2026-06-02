package Dao;

import java.sql.*;
import model.Marks;

public class MarksDao {

    public Marks getMarksByStudentId(int id) {
        try {
            Connection con = DBconnection.getConnection();
            PreparedStatement ps =
                con.prepareStatement("SELECT * FROM marks WHERE student_id=?");
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Marks(
                    rs.getInt("student_id"),
                    rs.getDouble("percentage"),
                    rs.getString("grade")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertMarks(Marks m) {
        try {
            Connection con = DBconnection.getConnection();
            PreparedStatement ps =
                con.prepareStatement("INSERT INTO marks VALUES (?,?,?)");
            ps.setInt(1, m.getStudentId());
            ps.setDouble(2, m.getPercentage());
            ps.setString(3, m.getGrade());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateMarks(Marks m) {
        try {
            Connection con = DBconnection.getConnection();
            PreparedStatement ps =
                con.prepareStatement(
                    "UPDATE marks SET percentage=?, grade=? WHERE student_id=?"
                );
            ps.setDouble(1, m.getPercentage());
            ps.setString(2, m.getGrade());
            ps.setInt(3, m.getStudentId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteMarks(int id) {
        try {
            Connection con = DBconnection.getConnection();
            PreparedStatement ps =
                con.prepareStatement("DELETE FROM marks WHERE student_id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
