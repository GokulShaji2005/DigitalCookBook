package ui.auth;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import services.LoginService;
import services.SignUpService;
import ui.Cook.UserPanel;
import model.User;
public class AuthUi {
    JFrame frame;
    JPanel mainPanel;
    CardLayout cardLayout;

    // Toggle label at bottom
    JLabel toggleLabel;

    // Panels
    CardPanel loginPanel;
    CardPanel signUpPanel;

    public AuthUi() {
        frame = new JFrame("Authentication");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Background panel
        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setBackground(new Color(230, 240, 250));

        // Main panel with CardLayout
        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        // Create login and signup card panels
        loginPanel = new CardPanel(false); // Login
        signUpPanel = new CardPanel(true); // Sign Up

        mainPanel.add(loginPanel, "login");
        mainPanel.add(signUpPanel, "signup");

        // Toggle label at bottom
        toggleLabel = new JLabel("Don't have an account? Sign Up", SwingConstants.CENTER);
        toggleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        toggleLabel.setForeground(new Color(0, 102, 128));
        toggleLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 1;
        gbc.insets = new Insets(20, 0, 0, 0);
        backgroundPanel.add(toggleLabel, gbc);

        gbc.gridy = 0;
        backgroundPanel.add(mainPanel, gbc);

        frame.setContentPane(backgroundPanel);

        // Toggle panel logic
        toggleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (loginPanel.isVisible()) {
                    cardLayout.show(mainPanel, "signup");
                    toggleLabel.setText("Already have an account? Login");
                    signUpPanel.clearFields();
                } else {
                    cardLayout.show(mainPanel, "login");
                    toggleLabel.setText("Don't have an account? Sign Up");
                    loginPanel.clearFields();
                }
            }
        });

        // Login action
        loginPanel.submitButton.addActionListener(ev -> {
            String user = loginPanel.getUsername();
            String pass = loginPanel.getPassword();
            LoginService loginService = new LoginService();
            try {
                User loggedInUser = loginService.loginService(user, pass);
                if (loggedInUser != null) {
                    JOptionPane.showMessageDialog(frame, "Login Successful!");
                    loginPanel.clearFields();

                   new UserPanel(loggedInUser);

                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error during login!");
                ex.printStackTrace();
            }
        });

        // SignUp action
        signUpPanel.submitButton.addActionListener(ev -> {
            String user = signUpPanel.getUsername();
            String pass = signUpPanel.getPassword();
            String role = signUpPanel.getRole();
            SignUpService signupService = new SignUpService();
            try {
                boolean success = signupService.signUpService(user, pass, role);
                if (success) {
                    JOptionPane.showMessageDialog(frame, "Sign Up Successful! Please login.");
                    signUpPanel.clearFields();
                    cardLayout.show(mainPanel, "login");
                    toggleLabel.setText("Don't have an account? Sign Up");
                } else {
                    JOptionPane.showMessageDialog(frame, "Sign Up Failed! Username already exists.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error during Sign Up!");
            }
        });

        cardLayout.show(mainPanel, "login");
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AuthUi::new);
    }
}
