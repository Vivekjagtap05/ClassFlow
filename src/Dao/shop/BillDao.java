package Dao.shop;

import java.sql.*;
import java.util.*;
import model.shop.Bill;
import Dao.DBconnection;

public class BillDao {

    public int createBill(Bill bill) throws Exception {
        String sql = "INSERT INTO bills(student_id, shopkeeper_id, bill_date, total_amount) VALUES (?, ?, ?, ?)";

        try (Connection con = DBconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, bill.getStudentId()); // MUST EXIST IN students table
            ps.setInt(2, bill.getShopKeeperId());
            ps.setDate(3, new java.sql.Date(bill.getBillDate().getTime()));
            ps.setDouble(4, bill.getTotalAmount());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        return -1;
    }

    // 🔍 Student view bill history
    public List<Bill> getBillsByStudentId(int studentId) {
        List<Bill> list = new ArrayList<>();
        String sql = "SELECT * FROM bills WHERE student_id = ?";

        try (Connection con = DBconnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Bill(
                        rs.getInt("id"),
                        rs.getInt("student_id"),
                        rs.getInt("shopkeeper_id"),
                        rs.getDate("bill_date"),
                        rs.getDouble("total_amount")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

	public Bill getBillById(int billId) {
		// TODO Auto-generated method stub
		return null;
	}
}
