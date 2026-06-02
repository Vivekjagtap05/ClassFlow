package Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import model.Fee;
import Dao.DBconnection; // your DB connection utility

@SuppressWarnings("unused")
public class FeeDao {

    public boolean insertFee(Fee fee) throws Exception {
        String sql = "INSERT INTO fees(student_id, amount_paid, amount_pending, status, payment_date, added_by) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, fee.getStudentId());
            ps.setDouble(2, fee.getAmountPaid());
            ps.setDouble(3, fee.getAmountPending());
            ps.setString(4, fee.getStatus());
            if (fee.getPaymentDate() != null) {
                ps.setDate(5, new java.sql.Date(fee.getPaymentDate().getTime()));
            } else {
                ps.setDate(5, null);
            }
            ps.setString(6, fee.getAddedBy());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateFee(Fee fee) throws Exception {
        String sql = "UPDATE fees SET amount_paid=?, amount_pending=?, status=?, payment_date=?, added_by=? WHERE student_id=?";
        try (Connection con = DBconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, fee.getAmountPaid());
            ps.setDouble(2, fee.getAmountPending());
            ps.setString(3, fee.getStatus());
            if (fee.getPaymentDate() != null) {
                ps.setDate(4, new java.sql.Date(fee.getPaymentDate().getTime()));
            } else {
                ps.setDate(4, null);
            }
            ps.setString(5, fee.getAddedBy());
            ps.setInt(6, fee.getStudentId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Fee> getAllFees() throws Exception {
        List<Fee> list = new ArrayList<>();
        String sql = "SELECT * FROM fees";
        try (Connection con = DBconnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Fee f = new Fee();
                f.setStudentId(rs.getInt("student_id"));
                f.setAmountPaid(rs.getDouble("amount_paid"));
                f.setAmountPending(rs.getDouble("amount_pending"));
                f.setStatus(rs.getString("status"));
                f.setPaymentDate(rs.getDate("payment_date"));
                f.setAddedBy(rs.getString("added_by"));
                list.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Fee getFeeByStudentId(int studentId) throws Exception {
        String sql = "SELECT * FROM fees WHERE student_id=?";
        try (Connection con = DBconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Fee f = new Fee();
                f.setStudentId(rs.getInt("student_id"));
                f.setAmountPaid(rs.getDouble("amount_paid"));
                f.setAmountPending(rs.getDouble("amount_pending"));
                f.setStatus(rs.getString("status"));
                f.setPaymentDate(rs.getDate("payment_date"));
                f.setAddedBy(rs.getString("added_by"));
                return f;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
