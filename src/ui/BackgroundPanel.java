package ui;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        setImage(imagePath);
    }

    public void setImage(String imagePath) {
        backgroundImage = new ImageIcon(imagePath).getImage();
        repaint(); // 🔥 VERY IMPORTANT
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(
                backgroundImage,
                0, 0,
                getWidth(), getHeight(),
                this
            );
        }
    }
}
