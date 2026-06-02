package ui;

import javax.swing.*;
import java.awt.*;

import Dao.AttendanceDao;
import model.Attendance;
import model.User;
import ui.BackgroundPanel;

@SuppressWarnings("unused")
public class AttendanceFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField txtStudentId, txtTotal, txtPresent;
    private JButton btnView, btnSave, btnUpdate, btnDelete;
    private User user;

    public AttendanceFrame(User user) {
        this.user = user;

        setTitle("Attendance");
        setSize(520, 360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        BackgroundPanel bg = new BackgroundPanel("src/images/attendance.png");
        setContentPane(bg);
        bg.setLayout(null);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Color labelColor = Color.WHITE;

        JLabel lblId = new JLabel("Student ID:");
        lblId.setFont(labelFont);
        lblId.setForeground(labelColor);
        lblId.setBounds(30, 30, 120, 25);
        bg.add(lblId);

        txtStudentId = new JTextField();
        txtStudentId.setBounds(150, 30, 150, 25);
        bg.add(txtStudentId);

        JLabel lblTotal = new JLabel("Total Days:");
        lblTotal.setFont(labelFont);
        lblTotal.setForeground(labelColor);
        lblTotal.setBounds(30, 80, 120, 25);
        bg.add(lblTotal);

        txtTotal = new JTextField();
        txtTotal.setBounds(150, 80, 150, 25);
        bg.add(txtTotal);

        JLabel lblPresent = new JLabel("Present Days:");
        lblPresent.setFont(labelFont);
        lblPresent.setForeground(labelColor);
        lblPresent.setBounds(30, 120, 120, 25);
        bg.add(lblPresent);

        txtPresent = new JTextField();
        txtPresent.setBounds(150, 120, 150, 25);
        bg.add(txtPresent);

        btnView = new JButton("View");
        btnView.setBounds(30, 180, 80, 30);
        bg.add(btnView);

        btnSave = new JButton("Save");
        btnSave.setBounds(120, 180, 80, 30);
        bg.add(btnSave);

        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(210, 180, 90, 30);
        bg.add(btnUpdate);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(310, 180, 90, 30);
        bg.add(btnDelete);

        // ===== ACTIONS =====
        btnView.addActionListener(e -> viewAttendance());
        btnSave.addActionListener(e -> saveAttendance());
        btnUpdate.addActionListener(e -> updateAttendance());
        btnDelete.addActionListener(e -> deleteAttendance());

        // 🔐 STUDENT = VIEW ONLY
        if (user.getRole().equalsIgnoreCase("STUDENT")) {
            txtStudentId.setText(String.valueOf(user.getId()));
            txtStudentId.setEditable(true); // can input ID
            txtTotal.setEditable(false);
            txtPresent.setEditable(false);
            btnSave.setEnabled(false);
            btnUpdate.setEnabled(false);
            btnDelete.setEnabled(false);
        }

        ThemeManager.apply(bg);
        setVisible(true);
    }

    private void viewAttendance() {
        try {
            int id = Integer.parseInt(txtStudentId.getText());

            // Students can only view their own attendance
            if (user.getRole().equalsIgnoreCase("STUDENT") && id != user.getId()) {
                JOptionPane.showMessageDialog(this, "You can only view your own attendance!");
                return;
            }

            Attendance a = new AttendanceDao().getAttendanceByStudentId(id);
            if (a != null) {
                txtTotal.setText(String.valueOf(a.getTotalDays()));
                txtPresent.setText(String.valueOf(a.getPresentDays()));
            } else {
                JOptionPane.showMessageDialog(this, "No attendance record found");
                txtTotal.setText("");
                txtPresent.setText("");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Student ID");
        }
    }

    private void saveAttendance() {
        Attendance a = new Attendance(
                Integer.parseInt(txtStudentId.getText()),
                Integer.parseInt(txtTotal.getText()),
                Integer.parseInt(txtPresent.getText())
        );
        new AttendanceDao().insertAttendance(a);
        JOptionPane.showMessageDialog(this, "Attendance Saved");
    }

    private void updateAttendance() {
        Attendance a = new Attendance(
                Integer.parseInt(txtStudentId.getText()),
                Integer.parseInt(txtTotal.getText()),
                Integer.parseInt(txtPresent.getText())
        );
        new AttendanceDao().updateAttendance(a);
        JOptionPane.showMessageDialog(this, "Attendance Updated");
    }

    private void deleteAttendance() {
        int id = Integer.parseInt(txtStudentId.getText());

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete attendance?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            new AttendanceDao().deleteAttendance(id);
            JOptionPane.showMessageDialog(this, "Attendance Deleted");
            txtTotal.setText("");
            txtPresent.setText("");
        }
    }
}
