package ui;

import javax.swing.*;

public class Notifier {
    public static void show(String msg) {
        JOptionPane.showMessageDialog(
            null,
            msg,
            "ClassFlow Notification",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}
