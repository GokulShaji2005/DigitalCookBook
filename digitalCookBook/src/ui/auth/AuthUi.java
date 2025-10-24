/*
 * File: AuthUi.java
 * Author: Angelina Binoy
 * Date: 2 October 2025
 * Description:
 *     This class creates the Authentication UI for the application. 
 *     It provides Login and Sign Up functionality using a CardLayout 
 *     to switch between forms. Based on the user's role (Admin, Chef, Viewer),
 *     it redirects to the appropriate dashboard after successful login.
 */

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

    JFrame frame;             // Main window
    JPanel mainPanel;         // Panel to hold login and signup forms
    CardLayout cardLayout;    // Layout to switch between login and signup
    JLabel toggleLabel;       // Label to toggle between login/signup
    CardPanel loginPanel;     // Login form panel
    CardPanel signUpPanel;    // Sign up form panel

    /**
     * Constructor initializes the authentication UI.
     * Creates the login and signup forms and handles user interactions.
     */
    public AuthUi() {
        frame = new JFrame("Authentication");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center window

        // 🔹 Background panel with GridBagLayout
        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setBackground(new Color(230, 240, 250));

        // 🔹 Main panel holding login and signup cards
        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        // Create login and signup cards
        loginPanel = new CardPanel(false);  // false = login
        signUpPanel = new CardPanel(true);  // true = signup

        mainPanel.add(loginPanel, "login");
        mainPanel.add(signUpPanel, "signup");

        // 🔹 Toggle label to switch between login and signup
        toggleLabel = new JLabel("Don't have an account? Sign Up", SwingConstants.CENTER);
        toggleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        toggleLabel.setForeground(new Color(0, 102, 128));
        toggleLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Position components using GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 1;
        gbc.insets = new Insets(20, 0, 0, 0);
        backgroundPanel.add(toggleLabel, gbc);

        gbc.gridy = 0;
        backgroundPanel.add(mainPanel, gbc);

        frame.setContentPane(backgroundPanel);

        // 🔹 Toggle between Login and Sign Up on click
        toggleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (loginPanel.isVisible()) {
                    cardLayout.show(mainPanel, "signup");
                    toggleLabel.setText("Already have an account? Login");
                    signUpPanel.clearFields(); // Clear form fields
                } else {
                    cardLayout.show(mainPanel, "login");
                    toggleLabel.setText("Don't have an account? Sign Up");
                    loginPanel.clearFields(); // Clear form fields
                }
            }
        });

        // 🔹 Login button action
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

                        // Redirect based on user role
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

                        frame.dispose(); // Close login window

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

        // 🔹 Sign Up button action
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

    /**
     * Set visibility of the Auth UI frame
     *
     * @param b true to show, false to hide
     */
    public void setVisible(boolean b) {
        frame.setVisible(b);
    }
}
