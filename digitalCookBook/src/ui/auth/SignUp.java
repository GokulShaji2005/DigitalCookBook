
package ui.auth;

import javax.swing.*;
import java.awt.*;
import services.SignUpService;

public class SignUp {
    JFrame frame;
    JTextField username;
    JPasswordField password;
    JComboBox<String> roleCombo;
    JLabel titleLabel;
    JButton submitButton;

    public SignUp() {
        frame = new JFrame("Sign Up");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // === Background Panel ===
        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setBackground(new Color(230, 240, 250));

        // === Card Panel ===
        JPanel cardPanel = new JPanel(new GridBagLayout());
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setPreferredSize(new Dimension(400, 360));
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 8, 10, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // === Title ===
        titleLabel = new JLabel("Sign Up", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(40, 40, 40));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        cardPanel.add(titleLabel, gbc);

        // === Username ===
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        cardPanel.add(userLabel, gbc);

        username = new JTextField();
        username.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        username.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(160, 160, 160), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        cardPanel.add(username, gbc);

        // === Password ===
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        cardPanel.add(passwordLabel, gbc);

        password = new JPasswordField();
        password.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        password.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(160, 160, 160), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridx = 1;
        cardPanel.add(password, gbc);

        // === Role Selection ===
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        cardPanel.add(roleLabel, gbc);

        roleCombo = new JComboBox<>(new String[]{"Viewer", "Chef"});
        roleCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        roleCombo.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        gbc.gridx = 1;
        cardPanel.add(roleCombo, gbc);

        // === Submit Button ===
        submitButton = new JButton("Sign Up");
        submitButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        submitButton.setBackground(new Color(0, 102, 128));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        cardPanel.add(submitButton, gbc);

        // === Action Listener ===
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent ev) {
                String user = username.getText();
                String pass = new String(password.getPassword());
                String role = (String) roleCombo.getSelectedItem();

                if (user.isEmpty() || pass.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "⚠ Please fill all fields!");
                    return;
                }

                try {
                    SignUpService signup = new SignUpService();
                    boolean success = signup.signUpService(user, pass, role);
                    if (success) {
                        JOptionPane.showMessageDialog(frame, "✅ Sign Up Successful! Please login.");
                        username.setText("");
                        password.setText("");
                        roleCombo.setSelectedIndex(0);

                        frame.dispose();
                        new AuthUi();
                    } else {
                        JOptionPane.showMessageDialog(frame, "❌ Sign Up Failed! Username already exists.");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, "⚠ Error during Sign Up: " + e.getMessage());
                }
            }
        });

        backgroundPanel.add(cardPanel);
        frame.setContentPane(backgroundPanel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SignUp();
            }
        });
    }
}
