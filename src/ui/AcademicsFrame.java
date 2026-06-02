package ui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.User;

public class AcademicsFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public AcademicsFrame(User user) {

        setTitle("Academics");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(245, 247, 255));
        add(main);

        // ===== HEADER =====
        JLabel title = new JLabel("📚 Academics", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setBorder(new EmptyBorder(20, 0, 20, 0));
        main.add(title, BorderLayout.NORTH);

        // ===== CARD GRID =====
        JPanel grid = new JPanel(new GridLayout(2, 3, 30, 30));
        grid.setBorder(new EmptyBorder(30, 40, 40, 40));
        grid.setBackground(new Color(245, 247, 255));

        grid.add(academicCard(
                "Subjects",
                "View all subjects offered",
                new Color(72, 118, 255),
                "Subjects Offered",
                "Our institution offers Mathematics, Science, English, History, Geography, Computer Science, and optional vocational subjects designed as per the latest education policy."
        ));

        grid.add(academicCard(
                "Syllabus",
                "Updated curriculum details",
                new Color(0, 180, 160),
                "Academic Syllabus",
                "The syllabus follows the latest board/university guidelines for the academic year 2024–25, ensuring conceptual clarity, practical exposure, and continuous assessment."
        ));

        grid.add(academicCard(
                "Examinations",
                "Exam pattern & schedule",
                new Color(255, 140, 0),
                "Examination System",
                "We conduct Unit Tests, Mid-Term Exams, and Final Examinations with a transparent evaluation system and timely result declaration."
        ));

        grid.add(academicCard(
                "Timetable",
                "Daily class schedule",
                new Color(138, 43, 226),
                "Class Timetable",
                "The timetable is carefully structured to balance academics, activities, and breaks, ensuring effective learning without overload."
        ));

        grid.add(academicCard(
                "Faculty",
                "Qualified teaching staff",
                new Color(220, 80, 80),
                "Faculty Information",
                "Our faculty consists of experienced and highly qualified educators dedicated to academic excellence and student mentoring."
        ));

        grid.add(academicCard(
                "Academic Calendar",
                "Important academic dates",
                new Color(34, 139, 34),
                "Academic Calendar",
                "The academic calendar includes term dates, holidays, examinations, and important institutional events for the year."
        ));

        main.add(grid, BorderLayout.CENTER);

        setVisible(true);
    }

    // ===== ACADEMIC CARD =====
    private JPanel academicCard(String title, String subtitle, Color color,
                                String detailTitle, String detailText) {

        JPanel card = new JPanel(null);
        card.setBackground(Color.WHITE);
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        card.setPreferredSize(new Dimension(250, 160));

        JPanel colorStrip = new JPanel();
        colorStrip.setBackground(color);
        colorStrip.setBounds(0, 0, 250, 6);
        card.add(colorStrip);

        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.BOLD, 18));
        t.setBounds(20, 30, 210, 30);
        card.add(t);

        JLabel sub = new JLabel(subtitle);
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        sub.setForeground(Color.DARK_GRAY);
        sub.setBounds(20, 65, 210, 25);
        card.add(sub);

        JLabel view = new JLabel("View Details →");
        view.setFont(new Font("Segoe UI", Font.BOLD, 13));
        view.setForeground(color);
        view.setBounds(20, 105, 150, 25);
        card.add(view);

        // ===== HOVER & CLICK =====
        card.addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(250, 252, 255));
                card.setBorder(BorderFactory.createLineBorder(color, 2));
            }

            public void mouseExited(MouseEvent e) {
                card.setBackground(Color.WHITE);
                card.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
            }

            public void mouseClicked(MouseEvent e) {
                new AcademicDetailFrame(detailTitle, detailText);
            }
        });

        return card;
    }
}
