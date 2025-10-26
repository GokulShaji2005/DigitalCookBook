package ui.admin;

import dao.announcementDao.AnnouncementDAO;
import model.Announcements;
import model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class CreateAnnounce extends JFrame {
    private JTextField txtTitle;
    private JTextArea txtMessage;
    private JComboBox<String> cmbAudience;
    private JButton btnSend;
    private User adminUser;
    private AdminAnnounce parentPanel; // reference to parent panel

    public CreateAnnounce(User adminUser, AdminAnnounce parentPanel) {
        this.adminUser = adminUser;
        this.parentPanel = parentPanel;

        setTitle("📢 Create Announcement");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new JLabel("Title:");
        txtTitle = new JTextField(25);

        JLabel lblMessage = new JLabel("Message:");
        txtMessage = new JTextArea(5, 25);
        txtMessage.setLineWrap(true);
        txtMessage.setWrapStyleWord(true);

        JLabel lblAudience = new JLabel("Target Audience:");
        cmbAudience = new JComboBox<>(new String[]{"Chef", "Viewer", "Both"});

        btnSend = new JButton("Send Announcement");
        btnSend.addActionListener(this::handleSend);

        panel.add(lblTitle);
        panel.add(txtTitle);
        panel.add(Box.createVerticalStrut(10));
        panel.add(lblMessage);
        panel.add(new JScrollPane(txtMessage));
        panel.add(Box.createVerticalStrut(10));
        panel.add(lblAudience);
        panel.add(cmbAudience);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnSend);

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void handleSend(ActionEvent e) {
        String title = txtTitle.getText().trim();
        String message = txtMessage.getText().trim();
        String audience = (String) cmbAudience.getSelectedItem();

        if (title.isEmpty() || message.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        Announcements a = new Announcements(0, title, message, audience, null);

        try {
            new AnnouncementDAO().createAnnouncement(a);
            JOptionPane.showMessageDialog(this, "✅ Announcement sent successfully!");

            // refresh parent panel
            parentPanel.loadAnnouncements(); // you need to implement this in AdminAnnounce

            dispose(); // close the create frame
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Error sending announcement: " + ex.getMessage());
        }
    }
}
