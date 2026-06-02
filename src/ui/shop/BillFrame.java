package ui.shop;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import model.shop.*;
import Dao.shop.*;
import java.util.List;
import java.util.ArrayList;

import model.shop.BillDetails;



public class BillFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTable productTable;
    private JTable billTable;

    private DefaultTableModel productModel;
    private DefaultTableModel billModel;

    private JLabel totalLabel;

    private ProductDao productDao = new ProductDao();
    private BillDao billDao = new BillDao();
    private BillDetailsDao detailsDao = new BillDetailsDao();

    private double total = 0;

    public BillFrame() {
        setTitle("🧾 Invoice Billing System");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ================= HEADER =================
        JLabel header = new JLabel("School Store Invoice", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(header, BorderLayout.NORTH);

        // ================= PRODUCT TABLE =================
        productModel = new DefaultTableModel(
                new String[]{"ID", "Product", "Price", "Stock"}, 0);
        productTable = new JTable(productModel);
        loadProducts();

        // ================= BILL TABLE =================
        billModel = new DefaultTableModel(
                new String[]{"Product", "Qty", "Price", "Subtotal"}, 0);
        billTable = new JTable(billModel);

        JPanel center = new JPanel(new GridLayout(1, 2, 10, 10));
        center.add(new JScrollPane(productTable));
        center.add(new JScrollPane(billTable));

        add(center, BorderLayout.CENTER);

        // ================= FOOTER =================
        JPanel bottom = new JPanel(new BorderLayout());

        JButton addBtn = new JButton("➕ Add To Bill");
        JButton generateBtn = new JButton("🧾 Generate Invoice");

        totalLabel = new JLabel("Total: ₹0.00");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JPanel btnPanel = new JPanel();
        btnPanel.add(addBtn);
        btnPanel.add(generateBtn);

        bottom.add(totalLabel, BorderLayout.WEST);
        bottom.add(btnPanel, BorderLayout.EAST);

        add(bottom, BorderLayout.SOUTH);

        // ================= ACTIONS =================
        addBtn.addActionListener(e -> addToBill());
        generateBtn.addActionListener(e -> generateInvoice());

        setVisible(true);
    }

    private void loadProducts() {
        try {
			for (Products p : productDao.getAllProducts()) {
			    productModel.addRow(new Object[]{
			            p.getId(), p.getName(), p.getPrice(), p.getQuantity()
			    });
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private void addToBill() {
        int row = productTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a product");
            return;
        }

        int productId = (int) productModel.getValueAt(row, 0);
        String name = productModel.getValueAt(row, 1).toString();
        double price = (double) productModel.getValueAt(row, 2);
        int stock = (int) productModel.getValueAt(row, 3);

        String qtyStr = JOptionPane.showInputDialog("Enter Quantity:");
        if (qtyStr == null) return;

        int qty = Integer.parseInt(qtyStr);
        if (qty <= 0 || qty > stock) {
            JOptionPane.showMessageDialog(this, "Invalid quantity");
            return;
        }

        double subtotal = qty * price;
        total += subtotal;

        billModel.addRow(new Object[]{name, qty, price, subtotal});
        totalLabel.setText("Total: ₹" + total);

        productModel.setValueAt(stock - qty, row, 3);
        try {
			productDao.updateProductStock(productId, stock - qty);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private void generateInvoice() {
        int studentId = Integer.parseInt(JOptionPane.showInputDialog("Student ID:"));
        int shopKeeperId = Integer.parseInt(JOptionPane.showInputDialog("Shopkeeper ID:"));

        Bill bill = new Bill(0, studentId, shopKeeperId, new Date(), total);
        int billId = 0;
		try {
			billId = billDao.createBill(bill);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<BillDetails> details = new ArrayList<BillDetails>();


        for (int i = 0; i < billModel.getRowCount(); i++) {
            int qty = (int) billModel.getValueAt(i, 1);
            double subtotal = (double) billModel.getValueAt(i, 3);

            details.add(new BillDetails(0, billId, 0, qty, subtotal));
        }

        detailsDao.addBillDetails(details);

        JOptionPane.showMessageDialog(this, "Invoice Generated Successfully!");
        new InvoiceFrame(billId);
    }
}
