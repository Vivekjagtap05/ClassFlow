package ui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class AchievementDetailFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public AchievementDetailFrame(String title, String desc, String imgPath) {

        setTitle(title + " - Details");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(Color.WHITE);

        // ================= IMAGE PANEL =================
        JLabel imageLabel = new JLabel("", JLabel.CENTER);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        URL url = getClass().getResource(imgPath);
        if (url != null) {
            Image img = new ImageIcon(url).getImage()
                    .getScaledInstance(700, 280, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(img));
        } else {
            imageLabel.setText("Image not found");
            imageLabel.setForeground(Color.RED);
        }

        add(imageLabel, BorderLayout.NORTH);

        // ================= CONTENT PANEL =================
        JPanel content = new JPanel(new BorderLayout(10, 10));
        content.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));
        content.setBackground(Color.WHITE);

        JLabel heading = new JLabel(title);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 26));
        content.add(heading, BorderLayout.NORTH);

        // ================= DESCRIPTION LOGIC =================
        JTextArea descriptionArea = new JTextArea();
        descriptionArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(Color.WHITE);
        descriptionArea.setBorder(null);

        String details = "";

        if (title.equalsIgnoreCase("Best School Award")) {
            details =
                "Awarded by the State Education Board for excellence.\n" +
                "The program manages and displays major school achievements.\n" +
                "Administrators can track awards year-wise.\n" +
                "Users can view detailed achievement information.\n" +
                "This enhances the school's credibility and reputation.";

        } else if (title.equalsIgnoreCase("Science Fair Winner")) {
            details =
                "Awarded for securing first rank at the national science fair.\n" +
                "The system stores academic and innovation-based achievements.\n" +
                "Teachers can highlight student creativity and research skills.\n" +
                "Each achievement is presented with visuals and year details.\n" +
                "This encourages innovation and competitive learning culture.";

        } else if (title.equalsIgnoreCase("Sports Champion")) {
            details =
                "Won gold medal in inter-school sports competition.\n" +
                "The module tracks sports-related achievements efficiently.\n" +
                "It allows schools to maintain a history of athletic success.\n" +
                "Students can view achievements and get inspired.\n" +
                "This promotes physical education and teamwork values.";

        } else if (title.equalsIgnoreCase("Cultural Fest Winner")) {
            details =
                "Secured first position in state-level cultural fest.\n" +
                "The program highlights non-academic achievements equally.\n" +
                "It stores cultural event records with images and details.\n" +
                "Users can explore achievements through an interactive UI.\n" +
                "This supports overall personality development of students.";

        } else if (title.equalsIgnoreCase("Top Academic Result")) {
            details =
                "Achieved 100% result in board examinations.\n" +
                "The system manages academic excellence records digitally.\n" +
                "It helps track yearly academic performance achievements.\n" +
                "Students and parents can view result-based awards.\n" +
                "This motivates students to aim for academic excellence.";
        }

        descriptionArea.setText(details);

        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        content.add(scrollPane, BorderLayout.CENTER);
        add(content, BorderLayout.CENTER);

        setVisible(true);
        toFront();
    }
}
