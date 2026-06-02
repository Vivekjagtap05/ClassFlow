package ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.User;

public class AnalyticsFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public AnalyticsFrame(User user) {

        setTitle("Academic Analytics Dashboard");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        // ================= HEADER =================
        JLabel header = new JLabel("📊 Academic Analytics Overview", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        header.setBorder(new EmptyBorder(15, 10, 10, 10));
        add(header, BorderLayout.NORTH);

        // ================= GRID =================
        JPanel grid = new JPanel(new GridLayout(2, 2, 20, 20));
        grid.setBorder(new EmptyBorder(20, 20, 20, 20));
        grid.setBackground(new Color(245, 248, 255));
        add(grid, BorderLayout.CENTER);

        // 1️⃣ TOTAL STUDENTS RATIO – BAR GRAPH
        grid.add(new StudentRatioBarChart(
                "Total Students Ratio",
                new String[]{"FY", "SY", "TY", "Final"},
                new int[]{320, 280, 250, 190}
        ));

        // 2️⃣ AVG ATTENDANCE – SHARE MARKET STYLE LINE GRAPH
        grid.add(new AttendanceLineChart(
                "Average Attendance Trend (%)",
                new int[]{65, 68, 72, 78, 82, 88}
        ));

        // 3️⃣ PASS RATE – PIE CHART
        grid.add(new PassRatePieChart(
                "Pass Rate",
                new int[]{85, 15} // Pass, Fail
        ));

        // 4️⃣ ACHIEVEMENTS – BAR GRAPH
        grid.add(new AchievementBarChart(
                "Achievements",
                new String[]{"Academic", "Sports", "Cultural", "Other"},
                new int[]{45, 30, 20, 10}
        ));

        setVisible(true);
    }

    // ================= STUDENT RATIO BAR =================
    class StudentRatioBarChart extends JPanel {
        String title;
        String[] labels;
        int[] values;

        StudentRatioBarChart(String title, String[] labels, int[] values) {
            this.title = title;
            this.labels = labels;
            this.values = values;
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(new Color(200, 210, 255)));
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            g2.setFont(new Font("Segoe UI", Font.BOLD, 16));
            g2.drawString(title, 15, 25);

            int w = getWidth();
            int h = getHeight();
            int barWidth = (w - 80) / values.length;

            for (int i = 0; i < values.length; i++) {
                int barHeight = values[i];
                int x = 40 + i * barWidth;
                int y = h - barHeight - 50;

                g2.setColor(new Color(72, 118, 255));
                g2.fillRoundRect(x, y, barWidth - 20, barHeight, 10, 10);

                g2.setColor(Color.BLACK);
                g2.drawString(labels[i], x + 5, h - 20);
                g2.drawString(values[i] + "", x + 5, y - 5);
            }
        }
    }

    // ================= ATTENDANCE LINE (UP GRAPH) =================
    class AttendanceLineChart extends JPanel {
        String title;
        int[] values;

        AttendanceLineChart(String title, int[] values) {
            this.title = title;
            this.values = values;
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(new Color(200, 210, 255)));
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            g2.setFont(new Font("Segoe UI", Font.BOLD, 16));
            g2.drawString(title, 15, 25);

            int h = getHeight();
            int step = (getWidth() - 80) / (values.length - 1);

            g2.setColor(new Color(40, 167, 69)); // Green (growth)
            for (int i = 0; i < values.length - 1; i++) {
                int x1 = 40 + i * step;
                int y1 = h - values[i] * 3;
                int x2 = 40 + (i + 1) * step;
                int y2 = h - values[i + 1] * 3;

                g2.drawLine(x1, y1, x2, y2);
                g2.fillOval(x1 - 4, y1 - 4, 8, 8);
            }
        }
    }

    // ================= PASS RATE PIE =================
    class PassRatePieChart extends JPanel {
        String title;
        int[] values;

        PassRatePieChart(String title, int[] values) {
            this.title = title;
            this.values = values;
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(new Color(200, 210, 255)));
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            g2.setFont(new Font("Segoe UI", Font.BOLD, 16));
            g2.drawString(title, 15, 25);

            int total = values[0] + values[1];
            int passAngle = (int) (360 * (values[0] / (double) total));

            g2.setColor(new Color(40, 167, 69));
            g2.fillArc(60, 50, 200, 200, 0, passAngle);

            g2.setColor(new Color(220, 80, 80));
            g2.fillArc(60, 50, 200, 200, passAngle, 360 - passAngle);

            g2.setColor(Color.BLACK);
            g2.drawString("Pass: " + values[0] + "%", 280, 120);
            g2.drawString("Fail: " + values[1] + "%", 280, 150);
        }
    }

    // ================= ACHIEVEMENT BAR =================
    class AchievementBarChart extends JPanel {
        String title;
        String[] labels;
        int[] values;

        AchievementBarChart(String title, String[] labels, int[] values) {
            this.title = title;
            this.labels = labels;
            this.values = values;
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(new Color(200, 210, 255)));
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            g2.setFont(new Font("Segoe UI", Font.BOLD, 16));
            g2.drawString(title, 15, 25);

            int barWidth = (getWidth() - 80) / values.length;
            int h = getHeight();

            for (int i = 0; i < values.length; i++) {
                int barHeight = values[i] * 4;
                int x = 40 + i * barWidth;
                int y = h - barHeight - 50;

                g2.setColor(new Color(255, 159, 64));
                g2.fillRoundRect(x, y, barWidth - 20, barHeight, 10, 10);

                g2.setColor(Color.BLACK);
                g2.drawString(labels[i], x, h - 20);
            }
        }
    }
}
