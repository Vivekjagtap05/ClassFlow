package ui.shop;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import model.shop.ShopKeeper;
import Dao.shop.ShopKeeperDao;

@SuppressWarnings("unused")
public class ShopKeeperFrame extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
    private DefaultTableModel model;
    private Dao.shop.ShopKeeperDao dao = new Dao.shop.ShopKeeperDao();

    public ShopKeeperFrame() throws Exception {
        setTitle("Manage Shop Keepers");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Table
        model = new DefaultTableModel(new String[]{"ID", "Username", "Name", "Contact"}, 0);
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        // Buttons panel
        JPanel panel = new JPanel();
        JButton addBtn = new JButton("Add");
        JButton refreshBtn = new JButton("Refresh");
        panel.add(addBtn);
        panel.add(refreshBtn);
        add(panel, BorderLayout.SOUTH);

        // Load data
        loadShopKeepers();

        // Button actions
        refreshBtn.addActionListener(e -> {
			try {
				loadShopKeepers();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        addBtn.addActionListener(e -> {
			try {
				addShopKeeper();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

        setVisible(true);
    }

    private void loadShopKeepers() throws Exception {
        model.setRowCount(0);
        List<ShopKeeper> list = dao.getAllShopKeepers();
        for (ShopKeeper sk : list) {
            model.addRow(new Object[]{sk.getId(), sk.getUsername(), sk.getName(), sk.getContact()});
        }
    }

    private void addShopKeeper() throws Exception {
        String username = JOptionPane.showInputDialog("Username:");
        String password = JOptionPane.showInputDialog("Password:");
        String name = JOptionPane.showInputDialog("Name:");
        String contact = JOptionPane.showInputDialog("Contact:");
        ShopKeeper sk = new ShopKeeper(0, username, password, name, contact);
        dao.addShopKeeper(sk);
        loadShopKeepers();
    }
}
