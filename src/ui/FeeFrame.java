package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Dao.FeeDao;
import model.Fee;
import model.User;

import java.awt.Color;
import java.awt.Font;
import java.util.Date;

public class FeeFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    JTextField txtId, txtPaid, txtPending;
    JComboBox<String> cmbStatus;

    DefaultTableModel model;
    JTable table;
    User loggedUser;

    public FeeFrame(User user) throws Exception {
        this.loggedUser = user;

        setTitle("ClassFlow - Fee Management");
        setSize(720, 520);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        BackgroundPanel bg = new BackgroundPanel("src/images/fees.jpg");
        setContentPane(bg);
        bg.setLayout(null);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Color labelColor = Color.WHITE;

        // ===== TITLE =====
        JLabel lblTitle = new JLabel("Fee Entry Form");
        lblTitle.setBounds(30, 10, 300, 30);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        bg.add(lblTitle);

        // ===== STUDENT ID =====
        JLabel lblId = new JLabel("Student ID:");
        lblId.setBounds(30, 60, 130, 25);
        lblId.setFont(labelFont);
        lblId.setForeground(labelColor);
        bg.add(lblId);

        txtId = new JTextField();
        txtId.setBounds(170, 60, 150, 25);
        bg.add(txtId);

        // ===== AMOUNT PAID =====
        JLabel lblPaid = new JLabel("Amount Paid:");
        lblPaid.setBounds(30, 100, 130, 25);
        lblPaid.setFont(labelFont);
        lblPaid.setForeground(labelColor);
        bg.add(lblPaid);

        txtPaid = new JTextField();
        txtPaid.setBounds(170, 100, 150, 25);
        bg.add(txtPaid);

        // ===== AMOUNT PENDING =====
        JLabel lblPending = new JLabel("Amount Pending:");
        lblPending.setBounds(30, 140, 130, 25);
        lblPending.setFont(labelFont);
        lblPending.setForeground(labelColor);
        bg.add(lblPending);

        txtPending = new JTextField();
        txtPending.setBounds(170, 140, 150, 25);
        bg.add(txtPending);

        // ===== FEE STATUS =====
        JLabel lblStatus = new JLabel("Fee Status:");
        lblStatus.setBounds(30, 180, 130, 25);
        lblStatus.setFont(labelFont);
        lblStatus.setForeground(labelColor);
        bg.add(lblStatus);

        cmbStatus = new JComboBox<>(new String[]{"Paid", "Pending"});
        cmbStatus.setBounds(170, 180, 150, 25);
        bg.add(cmbStatus);

        // ===== BUTTONS =====
        JButton btnSave = new JButton("Save");
        btnSave.setBounds(360, 60, 140, 30);
        bg.add(btnSave);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setBounds(360, 100, 140, 30);
        bg.add(btnUpdate);

        JButton btnView = new JButton("View All");
        btnView.setBounds(360, 140, 140, 30);
        bg.add(btnView);

        JButton btnInvoice = new JButton("Generate Invoice");
        btnInvoice.setBounds(360, 180, 180, 30);
        bg.add(btnInvoice);

        // ===== TABLE =====
        model = new DefaultTableModel(new String[]{
                "Student ID", "Amount Paid", "Amount Pending", "Status", "Payment Date"
        }, 0);

        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(30, 240, 640, 200);
        bg.add(scroll);

        // ===== ACTIONS =====
        btnSave.addActionListener(e -> saveFee());
        btnUpdate.addActionListener(e -> updateFee());
        btnView.addActionListener(e -> {
            try {
                loadFees();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        btnInvoice.addActionListener(e -> generateInvoice());

        // ===== STUDENT ROLE RESTRICTION =====
        if (loggedUser.getRole().equalsIgnoreCase("STUDENT")) {
            txtId.setText(String.valueOf(loggedUser.getId()));
            txtId.setEditable(false);
            txtPaid.setEditable(false);
            txtPending.setEditable(false);
            cmbStatus.setEnabled(false);
            btnSave.setEnabled(false);
            btnUpdate.setEnabled(false);
            btnInvoice.setEnabled(false);
            loadFees();
        }

        loadFees();
        setVisible(true);
    }

    // ===== SAVE =====
    private void saveFee() {
        try {
            int id = Integer.parseInt(txtId.getText());
            double paid = Double.parseDouble(txtPaid.getText());
            double pending = Double.parseDouble(txtPending.getText());
            String status = cmbStatus.getSelectedItem().toString();

            Fee fee = new Fee(id, paid, pending, status, new Date(), loggedUser.getRole());
            if (new FeeDao().insertFee(fee)) {
                JOptionPane.showMessageDialog(this, "Fee saved successfully!");
                loadFees();
                clearFields();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please enter valid fee details!");
        }
    }

    // ===== UPDATE =====
    private void updateFee() {
        try {
            int id = Integer.parseInt(txtId.getText());
            double paid = Double.parseDouble(txtPaid.getText());
            double pending = Double.parseDouble(txtPending.getText());
            String status = cmbStatus.getSelectedItem().toString();

            Fee fee = new Fee(id, paid, pending, status, new Date(), loggedUser.getRole());
            if (new FeeDao().updateFee(fee)) {
                JOptionPane.showMessageDialog(this, "Fee updated successfully!");
                loadFees();
                clearFields();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input!");
        }
    }

    // ===== LOAD FEES =====
    private void loadFees() throws Exception {
        model.setRowCount(0);
        FeeDao dao = new FeeDao();

        if (loggedUser.getRole().equalsIgnoreCase("STUDENT")) {
            Fee f = dao.getFeeByStudentId(loggedUser.getId());
            if (f != null) {
                model.addRow(new Object[]{
                        f.getStudentId(),
                        f.getAmountPaid(),
                        f.getAmountPending(),
                        f.getStatus(),
                        f.getPaymentDate()
                });
            }
        } else {
            for (Fee f : dao.getAllFees()) {
                model.addRow(new Object[]{
                        f.getStudentId(),
                        f.getAmountPaid(),
                        f.getAmountPending(),
                        f.getStatus(),
                        f.getPaymentDate()
                });
            }
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtPaid.setText("");
        txtPending.setText("");
        cmbStatus.setSelectedIndex(0);
    }

    // ===== INVOICE =====
    private void generateInvoice() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a record to generate invoice");
            return;
        }

        String invoice =
                "Invoice\n\n" +
                "Student ID: " + model.getValueAt(row, 0) +
                "\nAmount Paid: " + model.getValueAt(row, 1) +
                "\nAmount Pending: " + model.getValueAt(row, 2) +
                "\nStatus: " + model.getValueAt(row, 3);

        JOptionPane.showMessageDialog(this, invoice, "Invoice", JOptionPane.INFORMATION_MESSAGE);
    }
}
