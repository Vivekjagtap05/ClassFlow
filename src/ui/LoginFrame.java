package ui;

import javax.swing.*;
import java.awt.*;

import Dao.LoginDao;
import Dao.shop.ShopKeeperDao;
import model.User;
import model.shop.ShopKeeper;

public class LoginFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    JTextField txtUser;
    JPasswordField txtPass;
    boolean darkMode = false;

    JPanel card;
    JLabel title, l1, l2;
    JButton btnLogin;

    public LoginFrame() {

        setTitle("ClassFlow Login");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ===== BACKGROUND =====
        BackgroundPanel bg = new BackgroundPanel("src/images/bgg2.jpg");
        setContentPane(bg);
        bg.setLayout(new GridBagLayout());

        // ===== GLASS CARD =====
        card = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(darkMode
                        ? new Color(30, 30, 30, 200)
                        : new Color(255, 255, 255, 200));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
            }
        };
        card.setPreferredSize(new Dimension(360, 260));
        card.setOpaque(false);
        card.setLayout(null);
        bg.add(card);

        // ===== TITLE =====
        title = new JLabel("ClassFlow Login", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setBounds(70, 20, 220, 30);
        card.add(title);

        // ===== USERNAME =====
        l1 = new JLabel("Username");
        l1.setBounds(40, 70, 100, 25);
        card.add(l1);

        txtUser = new JTextField();
        txtUser.setBounds(40, 95, 280, 32);
        card.add(txtUser);

        // ===== PASSWORD =====
        l2 = new JLabel("Password");
        l2.setBounds(40, 135, 100, 25);
        card.add(l2);

        txtPass = new JPasswordField();
        txtPass.setBounds(40, 160, 280, 32);
        card.add(txtPass);

        // ===== MODERN LOGIN BUTTON (UI CHANGE) =====
        btnLogin = new JButton("LOGIN") {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(0, 120, 215),
                        0, getHeight(), new Color(0, 80, 160)
                );
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
            }
        };
        btnLogin.setBounds(90, 210, 180, 40);
        btnLogin.setOpaque(false);
        btnLogin.setContentAreaFilled(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.add(btnLogin);

        // ===== HOVER EFFECT =====
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btnLogin.setForeground(new Color(255, 255, 200));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btnLogin.setForeground(Color.WHITE);
            }
        });

        // ===== DARK MODE TOGGLE =====
        JToggleButton toggle = new JToggleButton("🌙");
        toggle.setBounds(300, 10, 45, 25);
        card.add(toggle);

        toggle.addActionListener(e -> {
            darkMode = toggle.isSelected();
            toggle.setText(darkMode ? "☀" : "🌙");
            applyTheme();
            card.repaint();
        });

        applyTheme();

        // ===== LOGIN LOGIC (FIXED) =====
        btnLogin.addActionListener(e -> {
            String u = txtUser.getText().trim();
            String p = new String(txtPass.getPassword());

            try {
                User user = LoginDao.login(u, p);

                // If not normal user → check SHOPKEEPER
                if (user == null) {
                    ShopKeeperDao skDao = new ShopKeeperDao();
                    ShopKeeper sk = skDao.validateLogin(u, p);
                    if (sk != null) {
                        user = new User();
                        user.setUsername(sk.getUsername());
                        user.setRole("SHOPKEEPER");
                        user.setId(sk.getId());
                    }
                }

                if (user != null) {
                    dispose();
                    new DashboardFrame(user);
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Invalid Username or Password",
                            "Login Failed",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(
                        this,
                        "Error during login: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        setVisible(true);
    }

    // ===== THEME =====
    private void applyTheme() {
        Color fg = darkMode ? Color.WHITE : Color.BLACK;
        Color inputBg = darkMode ? new Color(60, 60, 60) : Color.WHITE;

        title.setForeground(fg);
        l1.setForeground(fg);
        l2.setForeground(fg);

        txtUser.setBackground(inputBg);
        txtPass.setBackground(inputBg);

        txtUser.setForeground(fg);
        txtPass.setForeground(fg);
    }
}
