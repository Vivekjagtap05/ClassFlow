package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DashboardCardsPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DashboardCardsPanel() {
        setOpaque(false);
        setLayout(new GridLayout(2, 3, 30, 30)); // spacing
        setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        add(createCard(
                "School Events",
                "src/images/event_icon.png",
                "Programs & holidays"
        ));

        add(createCard(
                "Indian Personalities",
                "src/images/personality_icon.png",
                "Freedom fighters & leaders"
        ));

        add(createCard(
                "Student Activities",
                "src/images/activity_icon.png",
                "Sports & cultural events"
        ));

        add(createCard(
                "Achievements",
                "src/images/trophy_icon.png",
                "Awards & results"
        ));

        add(createCard(
                "Analytics",
                "src/images/gallery_icon.png",
                "Photos & videos"
        ));

        add(createCard(
                "Academics",
                "src/images/education_icon.png",
                "Grades & performance"
        ));
    }

    // ===== CARD CREATOR =====
    private JPanel createCard(String title, String iconPath, String desc) {

        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 200));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
            }
        };

        card.setOpaque(false);
        card.setLayout(null);
        card.setPreferredSize(new Dimension(220, 180));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // ICON
        JLabel iconLabel = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(iconPath);
            Image img = icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            iconLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.err.println("Icon missing: " + iconPath);
        }
        iconLabel.setBounds(78, 20, 64, 64);
        card.add(iconLabel);

        // TITLE
        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setBounds(0, 95, 220, 25);
        card.add(lblTitle);

        // DESCRIPTION
        JLabel lblDesc = new JLabel(desc, SwingConstants.CENTER);
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDesc.setForeground(Color.DARK_GRAY);
        lblDesc.setBounds(0, 120, 220, 20);
        card.add(lblDesc);

        // ===== HOVER EFFECT =====
        card.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                card.setLocation(card.getX(), card.getY() - 6);
                card.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setLocation(card.getX(), card.getY() + 6);
                card.repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(
                        card,
                        title + " – Coming Soon",
                        "Info",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        return card;
    }
}
