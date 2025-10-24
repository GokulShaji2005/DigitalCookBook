/*
 * File: CardPanel.java
 * Author: Angelina Binoy
 * Date: 2 October 2025
 * Description:
 *     This class represents a reusable card panel used in the Authentication UI.
 *     It can act as a Login or Sign Up form based on the isSignUp flag. 
 *     Provides fields for username, password, and role selection (for sign up),
 *     along with a submit button. Includes helper methods to get input values 
 *     and clear the form fields.
 */

package ui.auth;

import javax.swing.*;
import java.awt.*;

public class CardPanel extends JPanel {

    JTextField username;       // Input field for username
    JPasswordField password;   // Input field for password
    JComboBox<String> roleCombo; // Dropdown for role selection (only in sign up)
    JButton submitButton;      // Submit button for login/sign up
    JLabel titleLabel;         // Title of the card ("Login" or "Sign Up")
    boolean isSignUp;          // Flag to indicate whether this panel is sign up or login

    /**
     * Constructor initializes the card panel.
     * 
     * @param isSignUp true if this is a Sign Up panel, false for Login panel
     */
    public CardPanel(boolean isSignUp) {
        this.isSignUp = isSignUp;
        initComponents();
    }

    /**
     * Initialize all components of the card panel
     * including labels, text fields, combo box, and submit button.
     */
    private void initComponents() {
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(400, 360));
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 8, 10, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 🔹 Title label
        titleLabel = new JLabel(isSignUp ? "Sign Up" : "Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(40, 40, 40));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(titleLabel, gbc);

        // 🔹 Username label and field
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        this.add(userLabel, gbc);

        username = new JTextField();
        username.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        username.setForeground(Color.DARK_GRAY);
        username.setBackground(Color.WHITE);
        username.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(160, 160, 160), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(username, gbc);

        // 🔹 Password label and field
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        this.add(passLabel, gbc);

        password = new JPasswordField();
        password.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        password.setForeground(Color.DARK_GRAY);
        password.setBackground(Color.WHITE);
        password.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(160, 160, 160), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(password, gbc);

        // 🔹 Role selection (only for Sign Up)
        if (isSignUp) {
            JLabel roleLabel = new JLabel("Role:");
            roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.anchor = GridBagConstraints.EAST;
            this.add(roleLabel, gbc);

            roleCombo = new JComboBox<>(new String[]{"Viewer", "Chef"});
            roleCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            roleCombo.setBackground(Color.WHITE);
            roleCombo.setForeground(Color.DARK_GRAY);
            roleCombo.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            this.add(roleCombo, gbc);
        }

        // 🔹 Submit button
        submitButton = new JButton(isSignUp ? "Sign Up" : "Login");
        submitButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        submitButton.setBackground(new Color(0, 102, 128));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = isSignUp ? 4 : 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(submitButton, gbc);
    }

    /**
     * Get the username entered in the form
     *
     * @return username as String
     */
    public String getUsername() {
        return username.getText();
    }

    /**
     * Get the password entered in the form
     *
     * @return password as String
     */
    public String getPassword() {
        return new String(password.getPassword());
    }

    /**
     * Get the selected role (only for Sign Up)
     *
     * @return selected role as String, or null if login form
     */
    public String getRole() {
        return roleCombo != null ? (String) roleCombo.getSelectedItem() : null;
    }

    /**
     * Clear all input fields in the form
     */
    public void clearFields() {
        username.setText("");
        password.setText("");
        if (roleCombo != null) roleCombo.setSelectedIndex(0);
    }
}
