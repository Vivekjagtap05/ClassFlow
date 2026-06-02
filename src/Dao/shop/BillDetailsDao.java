package Dao.shop;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Dao.DBconnection;
import model.shop.BillDetails;

public class BillDetailsDao {

    // Add multiple bill details (UNCHANGED)
    public void addBillDetails(List<BillDetails> billDetailsList) {
        String sql = "INSERT INTO bill_details(bill_id, product_id, quantity, subtotal) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            for (BillDetails bd : billDetailsList) {
                pst.setInt(1, bd.getBillId());
                pst.setInt(2, bd.getProductId());
                pst.setInt(3, bd.getQuantity());
                pst.setDouble(4, bd.getSubtotal());
                pst.addBatch();
            }
            pst.executeBatch();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔥 NEW: Get bill items by bill ID
    public List<BillDetails> getDetailsByBillId(int billId) {
        List<BillDetails> list = new ArrayList<>();
        String sql = "SELECT * FROM bill_details WHERE bill_id = ?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, billId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                list.add(new BillDetails(
                    rs.getInt("id"),
                    rs.getInt("bill_id"),
                    rs.getInt("product_id"),
                    rs.getInt("quantity"),
                    rs.getDouble("subtotal")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
