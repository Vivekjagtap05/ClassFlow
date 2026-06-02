package ui;

import java.awt.*;

public class ThemeManager {

    public static boolean darkMode = false;

    public static Color bg() {
        return darkMode ? new Color(30, 30, 30) : Color.WHITE;
    }

    public static Color fg() {
        return darkMode ? Color.WHITE : Color.BLACK;
    }

    public static Color button() {
        return darkMode ? new Color(70, 70, 70) : new Color(220, 220, 220);
    }

    public static void apply(Container c) {
        for (Component comp : c.getComponents()) {
            comp.setBackground(bg());
            comp.setForeground(fg());

            if (comp instanceof Container) {
                apply((Container) comp);
            }
        }
    }
}
