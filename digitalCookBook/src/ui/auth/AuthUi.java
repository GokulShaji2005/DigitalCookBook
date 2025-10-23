
package ui.auth;

import ui.admin.AdminPanel;
import ui.Cook.UserPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import services.LoginService;
import services.SignUpService;
import model.User;
import ui.viewer.*;

public class AuthUi {
    JFrame frame;
    JPanel mainPanel;
    CardLayout cardLayout;
    JLabel toggleLabel;
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

        // Main panel (CardLayout)
        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        // Create login and signup cards
        loginPanel = new CardPanel(false);
        signUpPanel = new CardPanel(true);

        mainPanel.add(loginPanel, "login");
        mainPanel.add(signUpPanel, "signup");

        // Toggle label
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

        // 🔹 Toggle between Login and Sign Up (MouseAdapter replaced with anonymous class)
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

        // 🔹 Login button action (replaced lambda with ActionListener)
        loginPanel.submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                String username = loginPanel.getUsername();
                String password = loginPanel.getPassword();

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "⚠ Please enter both username and password!");
                    return;
                }

                try {
                    LoginService loginService = new LoginService();
                    User loggedInUser = loginService.loginService(username, password);

                    if (loggedInUser != null) {
                        JOptionPane.showMessageDialog(frame,
                                "✅ Login successful! Welcome " + loggedInUser.getUsername());

                        String role = loggedInUser.getRole();

                        if (role.equalsIgnoreCase("Chef")) {
                            new UserPanel(loggedInUser);
                        } else if (role.equalsIgnoreCase("Admin")) {
                            new AdminPanel(loggedInUser);
                        } else if (role.equalsIgnoreCase("Viewer")) {
                            new ViewerPanel(loggedInUser);
                        } else {
                            JOptionPane.showMessageDialog(frame, "⚠ Unknown role: " + role);
                            return;
                        }

                        frame.dispose();

                    } else {
                        JOptionPane.showMessageDialog(frame,
                                "❌ Invalid username or password!",
                                "Login Failed", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame,
                            "❌ Error during login: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        // 🔹 Sign Up button action (replaced lambda with ActionListener)
        signUpPanel.submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                String user = signUpPanel.getUsername();
                String pass = signUpPanel.getPassword();
                String role = signUpPanel.getRole();

                if (user.isEmpty() || pass.isEmpty() || role.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "⚠ Please fill all fields!");
                    return;
                }

                try {
                    SignUpService signupService = new SignUpService();
                    boolean success = signupService.signUpService(user, pass, role);

                    if (success) {
                        JOptionPane.showMessageDialog(frame, "✅ Sign Up Successful! Please login.");
                        signUpPanel.clearFields();
                        cardLayout.show(mainPanel, "login");
                        toggleLabel.setText("Don't have an account? Sign Up");
                    } else {
                        JOptionPane.showMessageDialog(frame, "❌ Sign Up Failed! Username already exists.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame,
                            "❌ Error during Sign Up: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        // Show login by default
        cardLayout.show(mainPanel, "login");
        frame.setVisible(true);
    }

    public void setVisible(boolean b) {
        frame.setVisible(b);
    }
}
