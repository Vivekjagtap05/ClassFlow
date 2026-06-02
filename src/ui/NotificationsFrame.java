package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class NotificationsFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public NotificationsFrame() {

        setTitle("Notifications");
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 248, 255));

        add(topBar(), BorderLayout.NORTH);
        add(notificationList(), BorderLayout.CENTER);

        setVisible(true);
    }

    // ================= TOP BAR =================
    private JPanel topBar() {
        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(new EmptyBorder(15, 20, 15, 20));
        top.setBackground(Color.WHITE);

        JLabel title = new JLabel("🔔 Notifications");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JLabel sub = new JLabel("Latest updates & alerts");
        sub.setForeground(Color.GRAY);

        JPanel left = new JPanel(new GridLayout(2, 1));
        left.setOpaque(false);
        left.add(title);
        left.add(sub);

        JButton clearBtn = new JButton("Clear All");
        clearBtn.setBackground(new Color(220, 53, 69));
        clearBtn.setForeground(Color.WHITE);
        clearBtn.setFocusPainted(false);

        top.add(left, BorderLayout.WEST);
        top.add(clearBtn, BorderLayout.EAST);

        return top;
    }

    // ================= NOTIFICATION LIST =================
    private JScrollPane notificationList() {

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        listPanel.setBackground(new Color(245, 248, 255));

        listPanel.add(notificationCard(
                "📚 Academic Update",
                "Final exams timetable has been published.",
                "2 hours ago",
                true
        ));

        listPanel.add(notificationCard(
                "🏆 Achievement Added",
                "Science Fair Winner achievement has been added.",
                "Yesterday",
                true
        ));

        listPanel.add(notificationCard(
                "💰 Fee Reminder",
                "Last date for fee payment is approaching.",
                "2 days ago",
                false
        ));

        listPanel.add(notificationCard(
                "🎉 School Event",
                "Annual Cultural Fest scheduled next week.",
                "3 days ago",
                false
        ));

        listPanel.add(notificationCard(
                "⚠️ Attendance Alert",
                "Your attendance is below required percentage.",
                "5 days ago",
                false
        ));

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        return scroll;
    }

    // ================= CARD =================
    private JPanel notificationCard(String title, String message, String time, boolean unread) {

        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBorder(new EmptyBorder(15, 15, 15, 15));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));

        card.setBackground(unread ? Color.WHITE : new Color(235, 238, 245));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(unread ? new Color(72,118,255) : Color.LIGHT_GRAY),
                new EmptyBorder(12, 12, 12, 12)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));

        JTextArea msg = new JTextArea(message);
        msg.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        msg.setWrapStyleWord(true);
        msg.setLineWrap(true);
        msg.setEditable(false);
        msg.setOpaque(false);

        JLabel timeLabel = new JLabel(time);
        timeLabel.setForeground(Color.GRAY);
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(titleLabel, BorderLayout.WEST);
        top.add(timeLabel, BorderLayout.EAST);

        card.add(top, BorderLayout.NORTH);
        card.add(msg, BorderLayout.CENTER);

        return card;
    }
}
