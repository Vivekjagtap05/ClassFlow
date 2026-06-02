package ui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import model.User;
import ui.shop.BillFrame;
import ui.shop.ProductFrame;
import ui.shop.ShopKeeperFrame;

public class DashboardFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private User user;
    private JLabel bgLabel;
    private Timer backgroundTimer;
    private int currentImageIndex = 0;

    private final Color CARD_NORMAL = new Color(255, 255, 255, 210);
    private final Color CARD_HOVER  = new Color(220, 230, 255);
    private final Color CARD_PRESS  = new Color(180, 200, 255);

    private String[] backgroundImages = {
        "src/images/nt.jpg",
        "src/images/abg.jpg",
        "src/images/bg.png",
        "src/images/bgg1.jpg",
        "src/images/ff.jpg",
        "src/images/final.jpg"
    };

    public DashboardFrame(User user) {
        this.setUser(user);

        setTitle("ClassFlow Dashboard - " + user.getRole());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // ===== LAYERED PANE =====
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, getScreenWidth(), getScreenHeight());
        add(layeredPane);

        // ===== BACKGROUND =====
        bgLabel = new JLabel();
        bgLabel.setBounds(0, 0, getScreenWidth(), getScreenHeight());
        bgLabel.setLayout(null);
        layeredPane.add(bgLabel, JLayeredPane.DEFAULT_LAYER);

        setBackgroundImage(0);
        startBackgroundTimer();

        // ===== TOP NAVBAR =====
        TopNavbar navbar = new TopNavbar(this);
        layeredPane.add(navbar, JLayeredPane.PALETTE_LAYER);

        // ================= SIDEBAR =================
        JPanel sidebar = new JPanel(null);
        sidebar.setBackground(new Color(20, 25, 45));
        sidebar.setBounds(0, 0, 260, getScreenHeight());

        JScrollPane scroll = new JScrollPane(sidebar);
        scroll.setBounds(0, 0, 260, getScreenHeight());
        scroll.setBorder(null);
        layeredPane.add(scroll, JLayeredPane.PALETTE_LAYER);

        JLabel logo = new JLabel("ClassFlow");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        logo.setBounds(70, 20, 150, 30);
        sidebar.add(logo);

        // ===== PROFILE =====
        JPanel profile = roundedPanel(25, 70, 210, 135);
        sidebar.add(profile);

        JLabel avatar = new JLabel(scaleIcon("src/images/admin.jpg", 70, 70));
        avatar.setBounds(70, 10, 70, 70);
        profile.add(avatar);

        JLabel name = new JLabel(user.getUsername(), SwingConstants.CENTER);
        name.setBounds(0, 85, 210, 20);
        profile.add(name);

        JLabel roleLabel = new JLabel(user.getRole(), SwingConstants.CENTER);
        roleLabel.setBounds(0, 105, 210, 18);
        profile.add(roleLabel);

        // ===== MENU =====
        int y = 230;
        String role = user.getRole().toUpperCase();

        if (role.equals("ADMIN")) {
            sidebar.add(menuBtn("Students", y += 45, () -> new StudentFrame(user)));
            sidebar.add(menuBtn("Attendance", y += 45, () -> new AttendanceFrame(user)));
            sidebar.add(menuBtn("Marks", y += 45, () -> new MarksFrame(user)));
            sidebar.add(menuBtn("Activities", y += 45, () -> new ActivityFrame(user)));
            sidebar.add(menuBtn("Fees", y += 45, () -> {
				try {
					new FeeFrame(user);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}));
            sidebar.add(menuBtn("Shop Keeper", y += 45, () -> {
				try {
					new ShopKeeperFrame();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}));
            sidebar.add(menuBtn("Products", y += 45, () -> {
				try {
					new ProductFrame();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}));
            sidebar.add(menuBtn("Billing", y += 45, BillFrame::new));
        }

        if (role.equals("TEACHER")) {
            sidebar.add(menuBtn("Attendance", y += 45, () -> new AttendanceFrame(user)));
            sidebar.add(menuBtn("Marks", y += 45, () -> new MarksFrame(user)));
            sidebar.add(menuBtn("Activities", y += 45, () -> new ActivityFrame(user)));
        }

        if (role.equals("STUDENT")) {
            sidebar.add(menuBtn("My Attendance", y += 45, () -> new AttendanceFrame(user)));
            sidebar.add(menuBtn("My Marks", y += 45, () -> new MarksFrame(user)));
            sidebar.add(menuBtn("My Activities", y += 45, () -> new ActivityFrame(user)));
            sidebar.add(menuBtn("My Fees", y += 45, () -> {
				try {
					new FeeFrame(user);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}));
        }

        // ===== LOGOUT BUTTON =====
        JButton logout = new JButton("Logout");
        logout.setBounds(55, y + 60, 150, 40);
        logout.setBackground(new Color(220, 80, 80));
        logout.setForeground(Color.WHITE);
        logout.setFocusPainted(false);
        logout.setBorderPainted(false);

        logout.addActionListener(e -> {
            if (backgroundTimer != null) backgroundTimer.stop();
            dispose();
            new LoginFrame();
        });

        sidebar.add(logout);

        // ================= ICON CARDS =================
        JPanel iconPanel = new JPanel(new GridLayout(2, 3, 40, 40));
        iconPanel.setOpaque(false);
        iconPanel.setBounds(340, 150, 960, 460);
        bgLabel.add(iconPanel);

        iconPanel.add(createIconCard("School Events", "src/images/event_icon.png",
                () -> new SchoolEventsFrame(user)));
        iconPanel.add(createIconCard("Personalities", "src/images/personality_icon.png",
                () -> new PersonalitiesFrame(user)));
        iconPanel.add(createIconCard("Activities", "src/images/checklist.png",
                () -> new ActivityFrame(user)));
        iconPanel.add(createIconCard("Achievements", "src/images/trophy_icon.png",
                () -> new AchievementsFrame(user)));
        iconPanel.add(createIconCard("Analytics", "src/images/gallery_icon.png",
                () -> new AnalyticsFrame(user)));
        iconPanel.add(createIconCard("Academics", "src/images/education_icon.png",
                () -> new AcademicsFrame(user)));

        setVisible(true);
    }

    // ================= ICON CARD =================
    private JPanel createIconCard(String title, String iconPath, Runnable action) {
        JPanel card = new JPanel(null);
        card.setBackground(CARD_NORMAL);
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel icon = new JLabel(scaleIcon(iconPath, 90, 90));
        icon.setBounds(95, 35, 90, 90);
        card.add(icon);

        JLabel text = new JLabel(title, SwingConstants.CENTER);
        text.setBounds(0, 140, 280, 30);
        text.setFont(new Font("Segoe UI", Font.BOLD, 15));
        card.add(text);

        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { card.setBackground(CARD_HOVER); }
            public void mouseExited(MouseEvent e)  { card.setBackground(CARD_NORMAL); }
            public void mousePressed(MouseEvent e) { card.setBackground(CARD_PRESS); }
            public void mouseReleased(MouseEvent e) {
                card.setBackground(CARD_HOVER);
                action.run();
            }
        });
        return card;
    }

    // ================= MENU BUTTON =================
    private JButton menuBtn(String text, int y, Runnable action) {
        JButton btn = new JButton(text);
        btn.setBounds(30, y, 200, 42);
        btn.setBackground(new Color(45, 55, 90));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(72, 118, 255));
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(45, 55, 90));
            }
        });

        btn.addActionListener(e -> action.run());
        return btn;
    }

    // ===== HELPERS =====
    private JPanel roundedPanel(int x, int y, int w, int h) {
        JPanel p = new JPanel(null) {
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(255, 255, 255, 200));
                g.fillRoundRect(0, 0, w, h, 20, 20);
            }
        };
        p.setBounds(x, y, w, h);
        p.setOpaque(false);
        return p;
    }

    private ImageIcon scaleIcon(String path, int w, int h) {
        return new ImageIcon(new ImageIcon(path).getImage()
                .getScaledInstance(w, h, Image.SCALE_SMOOTH));
    }

    private void startBackgroundTimer() {
        backgroundTimer = new Timer(3500, e -> {
            currentImageIndex = (currentImageIndex + 1) % backgroundImages.length;
            setBackgroundImage(currentImageIndex);
        });
        backgroundTimer.start();
    }

    private void setBackgroundImage(int i) {
        Image img = new ImageIcon(backgroundImages[i]).getImage()
                .getScaledInstance(getScreenWidth(), getScreenHeight(), Image.SCALE_SMOOTH);
        bgLabel.setIcon(new ImageIcon(img));
    }

    private int getScreenWidth() {
        return Toolkit.getDefaultToolkit().getScreenSize().width;
    }

    private int getScreenHeight() {
        return Toolkit.getDefaultToolkit().getScreenSize().height;
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
