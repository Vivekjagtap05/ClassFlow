package Dao.shop;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Dao.DBconnection;
import model.shop.ShopKeeper;

public class ShopKeeperDao {

    // Add a new ShopKeeper
    public void addShopKeeper(ShopKeeper sk) throws Exception {
        String sql = "INSERT INTO shopkeepers(username, password, name, contact) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, sk.getUsername());
            pst.setString(2, sk.getPassword());
            pst.setString(3, sk.getName());
            pst.setString(4, sk.getContact());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all ShopKeepers
    public List<ShopKeeper> getAllShopKeepers() throws Exception {
        List<ShopKeeper> list = new ArrayList<>();
        String sql = "SELECT * FROM shopkeepers";
        try (Connection conn = DBconnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                ShopKeeper sk = new ShopKeeper(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("contact")
                );
                list.add(sk);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Get ShopKeeper by ID
    public ShopKeeper getShopKeeperById(int id) throws Exception {
        ShopKeeper sk = null;
        String sql = "SELECT * FROM shopkeepers WHERE id=?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                sk = new ShopKeeper(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("contact")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sk;
    }

	public ShopKeeper validateLogin(String u, String p) {
		// TODO Auto-generated method stub
		return null;
	}
}
