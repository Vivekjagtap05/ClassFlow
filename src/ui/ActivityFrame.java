package ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import model.User;
import model.Activity;
import Dao.ActivityDao;
import ui.BackgroundPanel;

@SuppressWarnings("unused")
public class ActivityFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField txtStudentId, txtActivity;
    private JComboBox<String> cmbStatus;
    private User user;

    public ActivityFrame(User user) {
        this.user = user;

        setTitle("Activities");
        setSize(550, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        BackgroundPanel bg = new BackgroundPanel("src/images/activity1.jpg");
        setContentPane(bg);
        bg.setLayout(null);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 15);
        Color labelColor = new Color(255, 215, 0); // GOLD

        if (user.getRole().equalsIgnoreCase("STUDENT")) {

            // ===== STUDENT VIEW =====
            JLabel lblInfo = new JLabel("Enter your Student ID to view activities:");
            lblInfo.setFont(labelFont);
            lblInfo.setForeground(labelColor);
            lblInfo.setBounds(80, 80, 350, 30);
            bg.add(lblInfo);

            txtStudentId = new JTextField();
            txtStudentId.setBounds(80, 120, 200, 28);
            bg.add(txtStudentId);

            JButton btnView = new JButton("View Activities");
            btnView.setBounds(300, 120, 150, 28);
            bg.add(btnView);

            btnView.addActionListener(e -> {
                try {
                    int studentId = Integer.parseInt(txtStudentId.getText());
                    showActivitiesTable(studentId); // pass entered ID
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Invalid Student ID");
                }
            });

        } else { // TEACHER or ADMIN

            JLabel title = new JLabel("Add Student Activity");
            title.setFont(new Font("Segoe UI", Font.BOLD, 18));
            title.setForeground(Color.WHITE);
            title.setBounds(150, 20, 250, 30);
            bg.add(title);

            JLabel l1 = new JLabel("Student ID:");
            l1.setFont(labelFont);
            l1.setForeground(labelColor);
            l1.setBounds(80, 80, 120, 25);
            bg.add(l1);

            txtStudentId = new JTextField();
            txtStudentId.setBounds(200, 80, 200, 28);
            bg.add(txtStudentId);

            JLabel l2 = new JLabel("Activity Name:");
            l2.setFont(labelFont);
            l2.setForeground(labelColor);
            l2.setBounds(80, 120, 120, 25);
            bg.add(l2);

            txtActivity = new JTextField();
            txtActivity.setBounds(200, 120, 200, 28);
            bg.add(txtActivity);

            JLabel l3 = new JLabel("Status:");
            l3.setFont(labelFont);
            l3.setForeground(labelColor);
            l3.setBounds(80, 160, 120, 25);
            bg.add(l3);

            cmbStatus = new JComboBox<>(new String[]{"PENDING", "COMPLETED"});
            cmbStatus.setBounds(200, 160, 200, 28);
            bg.add(cmbStatus);

            // ===== Add Activity Button =====
            JButton btnAdd = new JButton("Add Activity");
            btnAdd.setBounds(80, 210, 150, 35);
            bg.add(btnAdd);
            btnAdd.addActionListener(e -> addActivity());

            // ===== View Activities Button =====
            JButton btnView = new JButton("View Activities");
            btnView.setBounds(250, 210, 150, 35);
            bg.add(btnView);
            btnView.addActionListener(e -> showActivitiesTable(-1)); // -1 means show all
        }

        ThemeManager.apply(bg);
        setVisible(true);
    }

    // ===== ADD ACTIVITY =====
    private void addActivity() {
        try {
            int studentId = Integer.parseInt(txtStudentId.getText());
            String activityName = txtActivity.getText();
            String status = cmbStatus.getSelectedItem().toString();

            Activity a = new Activity();
            a.setStudentId(studentId);
            a.setActivityName(activityName);
            a.setStatus(status);
            a.setAddedBy(user.getRole());

            boolean success = new ActivityDao().insertActivity(a);
            if (success) {
                JOptionPane.showMessageDialog(this, "Activity added successfully!");
                txtActivity.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add activity");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input! Please check Student ID and Activity Name");
        }
    }

    // ===== SHOW ACTIVITIES TABLE =====
    private void showActivitiesTable(int studentId) {
        List<Activity> activities;

        if (user.getRole().equalsIgnoreCase("STUDENT")) {
            activities = new ActivityDao().getActivitiesByStudentId(studentId);
        } else { // Admin/Teacher
            if (studentId > 0) {
                activities = new ActivityDao().getActivitiesByStudentId(studentId);
            } else {
                activities = new ActivityDao().getAllActivities();
            }
        }

        if (activities.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No activities found!");
            return;
        }

        String[] columns = user.getRole().equalsIgnoreCase("STUDENT") ?
                new String[]{"S.No", "Activity Name", "Status"} :
                new String[]{"S.No", "Student ID", "Activity Name", "Status"};

        Object[][] data = new Object[activities.size()][columns.length];

        for (int i = 0; i < activities.size(); i++) {
            Activity a = activities.get(i);
            data[i][0] = i + 1;
            if (columns.length == 4) data[i][1] = a.getStudentId();
            data[i][columns.length - 2] = a.getActivityName();
            data[i][columns.length - 1] = a.getStatus();
        }

        JTable table = new JTable(data, columns);
        table.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(450, 250));

        JOptionPane.showMessageDialog(this, scrollPane, "Activities List", JOptionPane.INFORMATION_MESSAGE);
    }
}
