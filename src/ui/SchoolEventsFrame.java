package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import model.User;

public class SchoolEventsFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private int index = 0;
    private Timer slideTimer;
    private Timer textTimer;

    private JLabel imageLabel;
    private JTextArea descArea;
    private JPanel eventCard;

    // ===== EVENT DATA (DYNAMIC) =====
    private String[] titles = {
            "Annual Day Celebration",
            "Sports Day Event",
            "Science Exhibition",
            "Cultural Festival"
    };

    private String[] descriptions = {
            "Annual Day is a grand celebration of student achievements, cultural performances, awards, and memorable moments that bring together students, teachers, and parents.",
            "Sports Day encourages physical fitness, teamwork, leadership, and competitive spirit through various track and field events and games.",
            "Science Exhibition promotes innovation, creativity, and problem-solving skills by allowing students to present real-world scientific models.",
            "Cultural Festival celebrates diversity, traditions, dance, music, drama, and artistic talents of students."
    };

    private String[] images = {
            "/images/events/annual.png",
            "/images/events/sports_day.jpg",
            "/images/events/scf.jpg",
            "/images/events/activity.jpg"
    };

    public SchoolEventsFrame(User user) {

        setTitle("School Events | ClassFlow");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(245, 247, 252));

        // ===== EVENT CARD =====
        eventCard = new JPanel(null) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
            }
        };
        eventCard.setBounds(120, 70, 850, 480);
        eventCard.setOpaque(false);
        add(eventCard);

        // ===== IMAGE =====
        imageLabel = new JLabel();
        imageLabel.setBounds(60, 40, 730, 360);
        imageLabel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        eventCard.add(imageLabel);

        // ===== DESCRIPTION =====
        descArea = new JTextArea();
        descArea.setBounds(60, 420, 730, 0);
        descArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        descArea.setForeground(new Color(60, 60, 60));
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setEditable(false);
        descArea.setOpaque(false);
        eventCard.add(descArea);

        // ===== HOVER EFFECT =====
        eventCard.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                eventCard.setBounds(115, 65, 860, 490);
                eventCard.repaint();
            }
            public void mouseExited(MouseEvent e) {
                eventCard.setBounds(120, 70, 850, 480);
                eventCard.repaint();
            }
        });

        loadEvent(0);
        startAutoSlide();

        setVisible(true);
    }

    // ================= LOAD EVENT =================
    private void loadEvent(int i) {
        imageLabel.setIcon(loadImage(images[i], 730, 360));
        descArea.setText("");
        descArea.setBounds(60, 420, 730, 0);

        if (textTimer != null) textTimer.stop();

        // Reveal description after image
        textTimer = new Timer(40, null);
        final int[] h = {0};

        textTimer.addActionListener(e -> {
            h[0] += 4;
            descArea.setBounds(60, 420, 730, h[0]);
            descArea.setText(descriptions[i]);
            if (h[0] >= 90) textTimer.stop();
        });

        new Timer(2500, e -> textTimer.start()).start();
    }

    // ================= AUTO SLIDER =================
    private void startAutoSlide() {
        slideTimer = new Timer(2000, e -> {
            index = (index + 1) % titles.length;
            loadEvent(index);
        });
        slideTimer.start();
    }

    // ================= IMAGE LOADER =================
    private ImageIcon loadImage(String path, int w, int h) {
        java.net.URL url = getClass().getResource(path);
        if (url == null) {
            System.out.println("Missing image: " + path);
            return null;
        }
        Image img = new ImageIcon(url).getImage()
                .getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}
