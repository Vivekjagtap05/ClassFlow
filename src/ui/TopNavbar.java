package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TopNavbar extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame parent;

    public TopNavbar(JFrame parent) {
        this.parent = parent;

        setLayout(null);
        setBackground(new Color(25, 30, 60));

        // FULL WIDTH NAVBAR (IMPORTANT)
        setBounds(
            260, // after sidebar
            0,
            Toolkit.getDefaultToolkit().getScreenSize().width - 260,
            60
        );

        // ===== TITLE =====
        JLabel title = new JLabel("ClassFlow");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setBounds(30, 15, 200, 30);
        add(title);

        // ===== ICON POSITIONS (RIGHT SIDE) =====
        int startX = getWidth() - 180; // RIGHT ALIGN
        int gap = 50;

        add(createIcon("src/images/home.png", startX));
        add(createIcon("src/images/mail.png", startX + gap));
        add(createIcon("src/images/contact.png", startX + gap * 2));
    }

    private JLabel createIcon(String path, int x) {
        JLabel icon = new JLabel(scaleIcon(path, 26, 26));
        icon.setBounds(x, 17, 26, 26);
        icon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        icon.setOpaque(false);

        icon.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                icon.setOpaque(true);
                icon.setBackground(new Color(70, 90, 200));
            }

            public void mouseExited(MouseEvent e) {
                icon.setOpaque(false);
            }

            public void mouseClicked(MouseEvent e) {
                if (path.contains("home")) {
                    parent.dispose();
                    new DashboardFrame(((DashboardFrame) parent).getUser());
                } else if (path.contains("mail")) {
                    new NotificationsFrame();
                } else if (path.contains("contact")) {
                    new ContactUsFrame();
                }
            }
        });

        return icon;
    }

    private ImageIcon scaleIcon(String path, int w, int h) {
        return new ImageIcon(
            new ImageIcon(path).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH)
        );
    }
}
