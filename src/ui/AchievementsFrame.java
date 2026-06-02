package ui;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.User;

public class AchievementsFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel cardsPanel;
    private JTextField searchField;
    private User user;

    private java.util.List<Achievement> allAchievements = new ArrayList<>();

    public AchievementsFrame(User user) {
        this.setUser(user);

        setTitle("Achievements");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        add(topPanel(), BorderLayout.NORTH);
        add(contentPanel(), BorderLayout.CENTER);

        loadAchievements();
        renderCards("");

        setVisible(true);
    }

    // ================= TOP BAR =================
    private JPanel topPanel() {
        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(new EmptyBorder(15, 20, 15, 20));
        top.setBackground(Color.WHITE);

        JLabel title = new JLabel("🏆 Achievements");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        top.add(title, BorderLayout.WEST);

        JPanel searchBox = new JPanel(null);
        searchBox.setPreferredSize(new Dimension(380, 40));
        searchBox.setBackground(Color.WHITE);

        searchField = new JTextField();
        searchField.setBounds(0, 0, 260, 40);
        searchField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        searchBox.add(searchField);

        JButton searchBtn = new JButton("Search");
        searchBtn.setBounds(270, 0, 100, 40);
        searchBtn.setBackground(new Color(72, 118, 255));
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFocusPainted(false);
        searchBtn.setBorderPainted(false);
        searchBox.add(searchBtn);

        searchBtn.addActionListener(e -> renderCards(searchField.getText()));

        top.add(searchBox, BorderLayout.EAST);
        return top;
    }

    // ================= CONTENT =================
    private JScrollPane contentPanel() {
        cardsPanel = new JPanel(new GridLayout(0, 3, 25, 25));
        cardsPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
        cardsPanel.setBackground(new Color(245, 248, 255));

        JScrollPane scroll = new JScrollPane(cardsPanel);
        scroll.setBorder(null);
        return scroll;
    }

    // ================= DATA =================
    private void loadAchievements() {
        allAchievements.clear();

        allAchievements.add(new Achievement(
                "Best School Award",
                "Awarded by State Education Board for excellence.",
                "2023",
                "/images/ach1.jpg"
        ));

        allAchievements.add(new Achievement(
                "Science Fair Winner",
                "Students secured first rank nationally.",
                "2024",
                "/images/ach2.jpg"
        ));

        allAchievements.add(new Achievement(
                "Sports Champion",
                "Gold medal in inter-school athletics.",
                "2023",
                "/images/ach3.jpg"
        ));

        allAchievements.add(new Achievement(
                "Cultural Fest Winner",
                "First prize in state cultural competition.",
                "2022",
                "/images/ach4.jpg"
        ));

        allAchievements.add(new Achievement(
                "Top Academic Result",
                "100% result in board examinations.",
                "2024",
                "/images/ach5.jpg"
        ));
    }

    // ================= RENDER =================
    private void renderCards(String filter) {
        cardsPanel.removeAll();

        for (Achievement a : allAchievements) {
            if (a.title.toLowerCase().contains(filter.toLowerCase())) {
                cardsPanel.add(achievementCard(a));
            }
        }

        cardsPanel.revalidate();
        cardsPanel.repaint();
    }

    // ================= CARD =================
    private JPanel achievementCard(Achievement a) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 255)),
                new EmptyBorder(12, 12, 12, 12)
        ));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // IMAGE
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        URL imgURL = getClass().getResource(a.image);
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            Image img = icon.getImage().getScaledInstance(220, 140, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(img));
        } else {
            imageLabel.setText("Image not found");
            imageLabel.setForeground(Color.RED);
        }

        JLabel title = new JLabel(a.title);
        title.setFont(new Font("Segoe UI", Font.BOLD, 15));

        JLabel year = new JLabel("Year: " + a.year);
        year.setForeground(Color.GRAY);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);
        bottom.add(title, BorderLayout.NORTH);
        bottom.add(year, BorderLayout.SOUTH);

        card.add(imageLabel, BorderLayout.CENTER);
        card.add(bottom, BorderLayout.SOUTH);

        card.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new AchievementDetailFrame(a.title, a.description, a.image);
            }
        });

        return card;
    }

    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	// ================= MODEL =================
    static class Achievement {
        String title, description, year, image;
        Achievement(String t, String d, String y, String i) {
            title = t;
            description = d;
            year = y;
            image = i;
        }
    }
}
