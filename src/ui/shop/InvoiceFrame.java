package ui.shop;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.print.PrinterException;
import java.text.SimpleDateFormat;
import model.shop.*;
import Service.BillService;

public class InvoiceFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private BillService billService = new BillService();

    public InvoiceFrame(int billId) {

        BillWithDetails billData = billService.getCompleteBill(billId);
        Bill bill = billData.getBill();

        setTitle("Invoice - Bill #" + billId);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // ================= HEADER =================
        JPanel header = new JPanel(new GridLayout(4, 1));
        header.setBackground(Color.WHITE);

        JLabel schoolName = new JLabel("CLASSFLOWS SCHOOL STORE", SwingConstants.CENTER);
        schoolName.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JLabel address = new JLabel("Nashik, Maharashtra | Phone: 9876543210", SwingConstants.CENTER);
        JLabel invoiceTitle = new JLabel("INVOICE", SwingConstants.CENTER);
        invoiceTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));

        header.add(schoolName);
        header.add(address);
        header.add(invoiceTitle);

        add(header, BorderLayout.NORTH);

        // ================= BILL INFO =================
        JPanel infoPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        infoPanel.setBackground(Color.WHITE);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        infoPanel.add(new JLabel("Bill No: " + bill.getId()));
        infoPanel.add(new JLabel("Date: " + sdf.format(bill.getBillDate())));
        infoPanel.add(new JLabel("Student ID: " + bill.getStudentId()));
        infoPanel.add(new JLabel("Shopkeeper ID: " + bill.getShopKeeperId()));

        add(infoPanel, BorderLayout.WEST);

        // ================= TABLE =================
        String[] cols = {"Product ID", "Quantity", "Subtotal"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        JTable table = new JTable(model);
        table.setRowHeight(25);

        double total = 0;
        for (BillDetails bd : billData.getDetails()) {
            model.addRow(new Object[]{
                    bd.getProductId(),
                    bd.getQuantity(),
                    bd.getSubtotal()
            });
            total += bd.getSubtotal();
        }

        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        // ================= FOOTER =================
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        footer.setBackground(Color.WHITE);

        JLabel totalLabel = new JLabel("TOTAL AMOUNT : ₹ " + total);
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JButton printBtn = new JButton("Print Invoice");
        JButton closeBtn = new JButton("Close");

        JPanel btnPanel = new JPanel();
        btnPanel.add(printBtn);
        btnPanel.add(closeBtn);

        footer.add(totalLabel, BorderLayout.WEST);
        footer.add(btnPanel, BorderLayout.EAST);

        add(footer, BorderLayout.SOUTH);

        // ================= ACTIONS =================
        printBtn.addActionListener(e -> {
            try {
                table.print();
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(this, "Printing Failed!");
            }
        });

        closeBtn.addActionListener(e -> dispose());

        setVisible(true);
    }
}
