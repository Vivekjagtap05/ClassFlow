package ui;

import javax.swing.*;
import java.awt.*;
import Dao.MarksDao;
import model.Marks;
import model.User;

public class MarksFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    JTextField txtId, txtPercent, txtGrade;
    JButton btnView, btnSave, btnUpdate, btnDelete;
    JProgressBar progressBar;

    User user;

    public MarksFrame(User user) {

        this.user = user;

        // ===== FRAME SETTINGS =====
        setTitle("Marks Management");
        setSize(520, 360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);

        JLabel lblId = new JLabel("Student ID:");
        lblId.setBounds(30, 30, 120, 25);
        lblId.setFont(labelFont);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(160, 30, 160, 25);
        add(txtId);

        JLabel lblPercent = new JLabel("Percentage:");
        lblPercent.setBounds(30, 70, 120, 25);
        lblPercent.setFont(labelFont);
        add(lblPercent);

        txtPercent = new JTextField();
        txtPercent.setBounds(160, 70, 160, 25);
        add(txtPercent);

        JLabel lblGrade = new JLabel("Grade:");
        lblGrade.setBounds(30, 110, 120, 25);
        lblGrade.setFont(labelFont);
        add(lblGrade);

        txtGrade = new JTextField();
        txtGrade.setBounds(160, 110, 160, 25);
        add(txtGrade);

        btnView = new JButton("View");
        btnSave = new JButton("Save");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");

        btnView.setBounds(30, 160, 80, 32);
        btnSave.setBounds(120, 160, 80, 32);
        btnUpdate.setBounds(210, 160, 90, 32);
        btnDelete.setBounds(310, 160, 90, 32);

        add(btnView);
        add(btnSave);
        add(btnUpdate);
        add(btnDelete);

        progressBar = new JProgressBar(0, 100);
        progressBar.setBounds(80, 230, 360, 30);
        progressBar.setStringPainted(true);
        add(progressBar);

        // 🔐 ROLE BASED ACCESS
        if (user.getRole() != null && user.getRole().equalsIgnoreCase("STUDENT")) {

            txtId.setText(String.valueOf(user.getId()));
            txtId.setEditable(false);

            txtPercent.setEditable(false);
            txtGrade.setEditable(false);

            btnSave.setEnabled(false);
            btnUpdate.setEnabled(false);
            btnDelete.setEnabled(false);

            viewMarks(); // auto load student marks
        }

        // ===== BUTTON ACTIONS =====
        btnView.addActionListener(e -> viewMarks());
        btnSave.addActionListener(e -> saveMarks());
        btnUpdate.addActionListener(e -> updateMarks());
        btnDelete.addActionListener(e -> deleteMarks());

        setVisible(true); // ⭐ MOST IMPORTANT
    }

    private void viewMarks() {
        try {
            int id = Integer.parseInt(txtId.getText());
            Marks m = new MarksDao().getMarksByStudentId(id);

            if (m != null) {
                txtPercent.setText(String.valueOf(m.getPercentage()));
                txtGrade.setText(m.getGrade());
                progressBar.setValue((int) m.getPercentage());
            } else {
                JOptionPane.showMessageDialog(this, "No marks found");
                progressBar.setValue(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid Student ID");
        }
    }

    private void saveMarks() {
        Marks m = new Marks(
                Integer.parseInt(txtId.getText()),
                Double.parseDouble(txtPercent.getText()),
                txtGrade.getText()
        );
        new MarksDao().insertMarks(m);
        JOptionPane.showMessageDialog(this, "Marks Saved");
    }

    private void updateMarks() {
        Marks m = new Marks(
                Integer.parseInt(txtId.getText()),
                Double.parseDouble(txtPercent.getText()),
                txtGrade.getText()
        );
        new MarksDao().updateMarks(m);
        JOptionPane.showMessageDialog(this, "Marks Updated");
    }

    private void deleteMarks() {
        int id = Integer.parseInt(txtId.getText());
        new MarksDao().deleteMarks(id);
        JOptionPane.showMessageDialog(this, "Marks Deleted");
        progressBar.setValue(0);
    }
}
