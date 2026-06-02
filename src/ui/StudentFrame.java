package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import Dao.StudentDao;
import model.Student;
import model.User;

public class StudentFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    JTextField txtId, txtName, txtDept, txtSearch;
    JComboBox<String> cmbFilter;
    JButton btnSave, btnUpdate, btnDelete, btnSearch, btnReset;
    JTable table;
    DefaultTableModel model;

    private User loggedUser;

    @SuppressWarnings("unused")
	public StudentFrame(User user) {
        this.loggedUser = user;

        setTitle("ClassFlow - Student Management");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Font font = new Font("Segoe UI", Font.PLAIN, 14);

        // ===== FILTER AREA =====
        JLabel lblFilter = new JLabel("Filter By:");
        lblFilter.setBounds(30, 20, 80, 25);
        add(lblFilter);

        cmbFilter = new JComboBox<>(
                new String[]{"ALL", "ID", "NAME", "DEPARTMENT"}
        );
        cmbFilter.setBounds(110, 20, 130, 25);
        add(cmbFilter);

        txtSearch = new JTextField();
        txtSearch.setBounds(260, 20, 200, 25);
        add(txtSearch);

        btnSearch = new JButton("Search");
        btnSearch.setBounds(480, 20, 90, 25);
        add(btnSearch);

        btnReset = new JButton("Reset");
        btnReset.setBounds(580, 20, 90, 25);
        add(btnReset);

        // ===== INPUT FIELDS =====
        JLabel lblId = new JLabel("Student ID:");
        lblId.setBounds(30, 70, 100, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(140, 70, 180, 25);
        add(txtId);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(30, 110, 100, 25);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(140, 110, 180, 25);
        add(txtName);

        JLabel lblDept = new JLabel("Department:");
        lblDept.setBounds(30, 150, 100, 25);
        add(lblDept);

        txtDept = new JTextField();
        txtDept.setBounds(140, 150, 180, 25);
        add(txtDept);

        // ===== BUTTONS =====
        btnSave = new JButton("Save");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");

        btnSave.setBounds(30, 200, 90, 35);
        btnUpdate.setBounds(130, 200, 90, 35);
        btnDelete.setBounds(230, 200, 90, 35);

        add(btnSave);
        add(btnUpdate);
        add(btnDelete);

        // ===== TABLE =====
        model = new DefaultTableModel(
                new String[]{"ID", "Name", "Department"}, 0
        );
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(30, 260, 770, 280);
        add(scroll);

        // ===== EVENTS =====
        loadStudents();

        btnSearch.addActionListener(e -> smartSearch());
        btnReset.addActionListener(e -> loadStudents());

        btnSave.addActionListener(e -> saveStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnDelete.addActionListener(e -> deleteStudent());

        table.getSelectionModel().addListSelectionListener(e -> fillFields());

        applyRoleAccess();
        setVisible(true);
    }

    // ===== SMART SEARCH =====
    private void smartSearch() {
        String filter = cmbFilter.getSelectedItem().toString();
        String value = txtSearch.getText().trim();

        StudentDao dao = new StudentDao();
        List<Student> list;

        if (filter.equals("ALL")) {
            list = dao.getAllStudents();
        } else {
            if (value.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter search value");
                return;
            }
            list = dao.filterStudents(filter, value);
        }

        model.setRowCount(0);
        for (Student s : list) {
            model.addRow(new Object[]{
                    s.getId(), s.getName(), s.getDepartment()
            });
        }
    }

    // ===== ROLE ACCESS =====
    private void applyRoleAccess() {

        if (loggedUser.getRole().equalsIgnoreCase("STUDENT")) {

            cmbFilter.setEnabled(false);
            txtSearch.setEnabled(false);
            btnSearch.setEnabled(false);

            btnSave.setEnabled(false);
            btnUpdate.setEnabled(false);
            btnDelete.setEnabled(false);

            txtId.setEditable(false);
            txtName.setEditable(false);
            txtDept.setEditable(false);

            model.setRowCount(0);
            Student s = new StudentDao()
                    .getStudentById(loggedUser.getId());
            if (s != null) {
                model.addRow(new Object[]{
                        s.getId(), s.getName(), s.getDepartment()
                });
            }
        }

        if (loggedUser.getRole().equalsIgnoreCase("TEACHER")) {
            btnSave.setEnabled(false);
            btnUpdate.setEnabled(false);
            btnDelete.setEnabled(false);
        }
    }

    // ===== CRUD =====
    private void saveStudent() {
        Student s = new Student(
                Integer.parseInt(txtId.getText()),
                txtName.getText(),
                txtDept.getText()
        );
        new StudentDao().insertStudent(s);
        loadStudents();
        clearFields();
    }

    private void updateStudent() {
        Student s = new Student(
                Integer.parseInt(txtId.getText()),
                txtName.getText(),
                txtDept.getText()
        );
        new StudentDao().updateStudent(s);
        loadStudents();
        clearFields();
    }

    private void deleteStudent() {
        int id = Integer.parseInt(txtId.getText());
        new StudentDao().deleteStudent(id);
        loadStudents();
        clearFields();
    }

    private void fillFields() {
        int r = table.getSelectedRow();
        if (r >= 0) {
            txtId.setText(model.getValueAt(r, 0).toString());
            txtName.setText(model.getValueAt(r, 1).toString());
            txtDept.setText(model.getValueAt(r, 2).toString());
        }
    }

    private void loadStudents() {
        model.setRowCount(0);
        for (Student s : new StudentDao().getAllStudents()) {
            model.addRow(new Object[]{
                    s.getId(), s.getName(), s.getDepartment()
            });
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtDept.setText("");
    }
}
