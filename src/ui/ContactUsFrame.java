package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ContactUsFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public ContactUsFrame() {
        setTitle("Contact Us");
        setSize(700, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(230, 240, 255));

        // ===== HEADER =====
        JLabel header = new JLabel("📞 Contact Us", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        header.setForeground(Color.WHITE);
        header.setOpaque(true);
        header.setBackground(new Color(63, 81, 181));
        header.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(header, BorderLayout.NORTH);

        // ===== CARD PANEL =====
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 40, 20, 40),
                BorderFactory.createLineBorder(new Color(220, 220, 220))
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ===== INPUT FIELDS =====
        JTextField nameField = createField();
        JTextField emailField = createField();
        JTextField phoneField = createField();

        JTextArea messageArea = new JTextArea(5, 20);
        messageArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        JScrollPane messageScroll = new JScrollPane(messageArea);

        addField(card, gbc, 0, "Full Name", nameField);
        addField(card, gbc, 1, "Email Address", emailField);
        addField(card, gbc, 2, "Phone Number", phoneField);

        gbc.gridx = 0;
        gbc.gridy = 3;
        card.add(new JLabel("Message"), gbc);

        gbc.gridx = 1;
        card.add(messageScroll, gbc);

        add(card, BorderLayout.CENTER);

        // ===== BUTTON PANEL =====
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(230, 240, 255));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        JButton sendBtn = createButton("📤 Send", new Color(76, 175, 80));
        JButton clearBtn = createButton("🧹 Clear", new Color(255, 152, 0));
        JButton backBtn = createButton("⬅ Back", new Color(244, 67, 54));

        buttonPanel.add(sendBtn);
        buttonPanel.add(clearBtn);
        buttonPanel.add(backBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        // ===== ACTIONS =====
        sendBtn.addActionListener(e -> {
            if (nameField.getText().isEmpty()
                    || emailField.getText().isEmpty()
                    || messageArea.getText().isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please fill all required fields!",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Thank you, " + nameField.getText() + "!\nYour message has been sent.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
                clearFields(nameField, emailField, phoneField, messageArea);
            }
        });

        clearBtn.addActionListener(e ->
                clearFields(nameField, emailField, phoneField, messageArea)
        );

        backBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

    // ===== UI HELPERS =====
    private JTextField createField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        return field;
    }

    private JButton createButton(String text, Color baseColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(baseColor);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Color hover = baseColor.darker();

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(hover);
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(baseColor);
            }
        });

        return btn;
    }

    private void addField(JPanel panel, GridBagConstraints gbc, int y,
                          String labelText, JTextField field) {

        gbc.gridx = 0;
        gbc.gridy = y;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panel.add(label, gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void clearFields(JTextField name, JTextField email,
                             JTextField phone, JTextArea message) {
        name.setText("");
        email.setText("");
        phone.setText("");
        message.setText("");
    }
}
