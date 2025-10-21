package ui.auth;

import javax.swing.*;
import java.awt.*;

public class CardPanel extends JPanel {
    JTextField username;
    JPasswordField password;
    JComboBox<String> roleCombo;
    JButton submitButton;
    JLabel titleLabel;
    boolean isSignUp;

    public CardPanel(boolean isSignUp) {
        this.isSignUp = isSignUp;
        initComponents();
    }

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

        // Title
        titleLabel = new JLabel(isSignUp ? "Sign Up" : "Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(40, 40, 40));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(titleLabel, gbc);

        // Username
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

        // Password
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

        // Role (only for sign up)
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

        // Submit button
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

    public String getUsername() {
        return username.getText();
    }

    public String getPassword() {
        return new String(password.getPassword());
    }

    public String getRole() {
        return roleCombo != null ? (String) roleCombo.getSelectedItem() : null;
    }

    public void clearFields() {
        username.setText("");
        password.setText("");
        if (roleCombo != null) roleCombo.setSelectedIndex(0);
    }
}
