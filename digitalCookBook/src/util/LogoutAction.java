package util;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import ui.auth.AuthUi;

public class LogoutAction {

    // Static method to handle logout and redirect to login page
    public static void performLogout(JFrame frame) {
        int choice = JOptionPane.showConfirmDialog(frame,
                "Are you sure you want to log out?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            frame.dispose(); // close the current window
            new AuthUi().setVisible(true); // open login page
        }
    }
}
