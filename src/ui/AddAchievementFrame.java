package ui;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class AddAchievementFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public AddAchievementFrame(Consumer<String[]> callback) {

        setTitle("Add Achievement");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel t1 = new JLabel("Title");
        t1.setBounds(40, 40, 100, 30);
        add(t1);

        JTextField title = new JTextField();
        title.setBounds(150, 40, 280, 30);
        add(title);

        JLabel t2 = new JLabel("Description");
        t2.setBounds(40, 90, 100, 30);
        add(t2);

        JTextArea desc = new JTextArea();
        desc.setBounds(150, 90, 280, 100);
        add(desc);

        JLabel t3 = new JLabel("Image Path");
        t3.setBounds(40, 210, 100, 30);
        add(t3);

        JTextField img = new JTextField("images/ach1.jpg");
        img.setBounds(150, 210, 280, 30);
        add(img);

        JButton save = new JButton("Add Achievement");
        save.setBounds(150, 270, 200, 40);
        save.setBackground(new Color(59, 130, 246));
        save.setForeground(Color.WHITE);
        add(save);

        save.addActionListener(e -> {
            callback.accept(new String[]{
                    title.getText(),
                    desc.getText(),
                    img.getText()
            });
            dispose();
        });

        setVisible(true);
    }
}
