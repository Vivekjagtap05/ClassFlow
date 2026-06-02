package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.User;

public class PersonalitiesFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private JLabel imageLabel;
    private JTextArea infoArea;
    private int index = 0;
    private Timer autoTimer;

    // ===== PERSONALITY DATA =====
    private String[] names = {
            "Savitribai Phule",
            "Dr. B. R. Ambedkar",
            "Mahatma Gandhi",
            "Jyotiba Phule",
            "APJ Abdul Kalam",
            "Rani Lakshmibai",
            "Swami Vivekananda",
            "Sardar Patel",
            "Subhash Chandra Bose",
            "Bal Gangadhar Tilak"
    };

    private String[] images = {
            "src/images/savitribaii.jpg",
            "src/images/ambedkar.jpg",
            "src/images/gandhi.jpg",
            "src/images/jyotiba.jpg",
            "src/images/kalam.jpg",
            "src/images/rani.jpg",
            "src/images/swami.jpg",
            "src/images/patel.jpg",
            "src/images/bose.jpg",
            "src/images/tilak.jpg"
    };

    private String[] info = {
            "Savitribai Phule was India’s first female teacher and a social reformer. "
                    + "She worked for women education, caste equality and fought social injustice.",

            "Dr. B. R. Ambedkar was the chief architect of the Indian Constitution and "
                    + "a great social reformer who fought against caste discrimination.",

            "Mahatma Gandhi led India’s freedom struggle through non-violence and truth. "
                    + "He is known as the Father of the Nation.",

            "Jyotiba Phule worked for social equality and education of women and lower castes "
                    + "along with Savitribai Phule.",

            "Dr. APJ Abdul Kalam was India’s Missile Man and 11th President of India. "
                    + "He inspired millions of students.",

            "Rani Lakshmibai was a brave queen who fought against British rule in 1857. "
                    + "She symbolizes courage and patriotism.",

            "Swami Vivekananda spread Indian philosophy worldwide and inspired youth "
                    + "to build strong character.",

            "Sardar Vallabhbhai Patel unified India by integrating princely states. "
                    + "He is known as the Iron Man of India.",

            "Subhash Chandra Bose led the Indian National Army and fought for freedom "
                    + "with the slogan ‘Give me blood, I will give you freedom’.",

            "Bal Gangadhar Tilak was a freedom fighter who popularized the slogan "
                    + "‘Swaraj is my birthright and I shall have it’."
    };

    public PersonalitiesFrame(User user) {

        setTitle("Indian Personalities");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // ===== TITLE =====
        JLabel title = new JLabel("Great Indian Personalities", SwingConstants.CENTER);
        title.setBounds(0, 10, 900, 40);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(title);

        // ===== IMAGE =====
        imageLabel = new JLabel();
        imageLabel.setBounds(50, 80, 350, 400);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(imageLabel);

        // ===== INFO AREA =====
        infoArea = new JTextArea();
        infoArea.setBounds(430, 100, 400, 350);
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        infoArea.setEditable(false);
        infoArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        infoArea.setBackground(new Color(245, 245, 245));
        infoArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(infoArea);

        // ===== LEFT BUTTON =====
        JButton prevBtn = new JButton("<");
        prevBtn.setBounds(10, 260, 50, 50);
        prevBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(prevBtn);

        // ===== RIGHT BUTTON =====
        JButton nextBtn = new JButton(">");
        nextBtn.setBounds(840, 260, 50, 50);
        nextBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(nextBtn);

        // ===== BUTTON ACTIONS =====
        prevBtn.addActionListener(e -> {
            index = (index - 1 + names.length) % names.length;
            updatePersonality();
            resetTimer();
        });

        nextBtn.addActionListener(e -> {
            index = (index + 1) % names.length;
            updatePersonality();
            resetTimer();
        });

        // ===== AUTO SLIDE (10 SECONDS) =====
        autoTimer = new Timer(10000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                index = (index + 1) % names.length;
                updatePersonality();
            }
        });
        autoTimer.start();

        updatePersonality();
        setVisible(true);
    }

    // ===== UPDATE DATA =====
    private void updatePersonality() {
        imageLabel.setIcon(scaleImage(images[index], 320, 380));
        infoArea.setText(names[index] + "\n\n" + info[index]);
    }

    private void resetTimer() {
        autoTimer.restart();
    }

    private ImageIcon scaleImage(String path, int w, int h) {
        Image img = new ImageIcon(path).getImage()
                .getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}
