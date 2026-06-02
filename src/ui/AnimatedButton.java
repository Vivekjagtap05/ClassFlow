package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AnimatedButton extends JButton {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Color normal = new Color(52, 152, 219);
    Color hover = new Color(41, 128, 185);
    Color press = new Color(31, 97, 141);

    public AnimatedButton(String text) {
        super(text);
        setBackground(normal);
        setForeground(Color.white);
        setFont(new Font("Segoe UI", Font.BOLD, 14));
        setFocusPainted(false);
        setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                setBackground(hover);
            }

            public void mouseExited(MouseEvent e) {
                setBackground(normal);
            }

            public void mousePressed(MouseEvent e) {
                setBackground(press);
            }

            public void mouseReleased(MouseEvent e) {
                setBackground(hover);
            }
        });
    }
}
