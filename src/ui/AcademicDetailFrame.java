package ui;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class AcademicDetailFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public AcademicDetailFrame(String title, String content) {

        setTitle(title);
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Color.WHITE);
        main.setBorder(new EmptyBorder(25, 30, 25, 30));
        add(main);

        JLabel heading = new JLabel(title);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 24));
        main.add(heading, BorderLayout.NORTH);

        JTextArea text = new JTextArea(content);
        text.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setEditable(false);
        text.setBackground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(text);
        scroll.setBorder(null);
        main.add(scroll, BorderLayout.CENTER);

        setVisible(true);
    }
}
