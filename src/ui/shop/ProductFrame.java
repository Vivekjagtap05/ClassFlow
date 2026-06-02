package ui.shop;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import model.shop.Products;
import Dao.shop.ProductDao;

public class ProductFrame extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
    private DefaultTableModel model;
    private ProductDao dao = new ProductDao();

    public ProductFrame() throws Exception {
        setTitle("Manage Products");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Table
        model = new DefaultTableModel(new String[]{"ID", "Name", "Category", "Price", "Stock"}, 0);
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        // Buttons
        JPanel panel = new JPanel();
        JButton addBtn = new JButton("Add");
        JButton updateStockBtn = new JButton("Update Stock");
        JButton refreshBtn = new JButton("Refresh");
        panel.add(addBtn);
        panel.add(updateStockBtn);
        panel.add(refreshBtn);
        add(panel, BorderLayout.SOUTH);

        // Load data
        loadProducts();

        // Actions
        refreshBtn.addActionListener(e -> {
			try {
				loadProducts();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        addBtn.addActionListener(e -> {
			try {
				addProduct();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        updateStockBtn.addActionListener(e -> {
			try {
				updateStock();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

        setVisible(true);
    }

    private void loadProducts() throws Exception {
        model.setRowCount(0);
        List<Products> list = dao.getAllProducts();
        for (Products p : list) {
            model.addRow(new Object[]{p.getId(), p.getName(), p.getCategory(), p.getPrice(), p.getQuantity()});
        }
    }

    private void addProduct() throws Exception {
        String name = JOptionPane.showInputDialog("Product Name:");
        String category = JOptionPane.showInputDialog("Category:");
        double price = Double.parseDouble(JOptionPane.showInputDialog("Price:"));
        int qty = Integer.parseInt(JOptionPane.showInputDialog("Quantity:"));
        Products p = new Products(0, name, category, price, qty);
        dao.addProduct(p);
        loadProducts();
    }

    private void updateStock() throws Exception {
        int row = table.getSelectedRow();
        if (row == -1) return;
        int productId = (int) table.getValueAt(row, 0);
        int newQty = Integer.parseInt(JOptionPane.showInputDialog("New Quantity:"));
        dao.updateProductStock(productId, newQty);
        loadProducts();
    }
}
