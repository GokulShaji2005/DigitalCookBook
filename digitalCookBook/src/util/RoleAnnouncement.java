package util;



import dao.announcementDao.AnnouncementDAO;
import model.Announcements;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class RoleAnnouncement extends JPanel {

    private JPanel listPanel;
    private String role; // "CHEF" or "VIEWER"

    public RoleAnnouncement(String role) {
        this.role = role;

        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // 🔹 Header
        JLabel header = new JLabel("📢 Announcements", SwingConstants.LEFT);
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(header, BorderLayout.NORTH);

        // 🔹 List Panel
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        loadAnnouncements();
    }

    public void loadAnnouncements() {
        listPanel.removeAll();
        try {
            List<Announcements> announcements = new AnnouncementDAO().getAnnouncementsForRole(role);
            if (announcements.isEmpty()) {
                JLabel noLabel = new JLabel("No announcements yet.", SwingConstants.CENTER);
                noLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
                noLabel.setForeground(Color.GRAY);
                listPanel.add(Box.createVerticalGlue());
                listPanel.add(noLabel);
                listPanel.add(Box.createVerticalGlue());
            } else {
                for (Announcements a : announcements) {
                    JPanel card = createAnnouncementCard(a);
                    listPanel.add(card);
                    listPanel.add(Box.createVerticalStrut(10));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            listPanel.add(new JLabel("❌ Error loading announcements"));
        }
        revalidate();
        repaint();
    }

    private JPanel createAnnouncementCard(Announcements a) {
        JPanel card = new JPanel(new BorderLayout());
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        card.setBackground(new Color(247, 249, 252));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        JLabel lblTitle = new JLabel("📌 " + a.getTitle());
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));

        // Plain text message using JTextArea (non-editable)
        JTextArea lblMsg = new JTextArea(a.getMessage());
        lblMsg.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblMsg.setLineWrap(true);
        lblMsg.setWrapStyleWord(true);
        lblMsg.setEditable(false);
        lblMsg.setBackground(new Color(247, 249, 252));
        lblMsg.setBorder(BorderFactory.createEmptyBorder());

        JLabel lblInfo = new JLabel("👥 " + a.getTargetAudience() + " | 🕒 " + a.getCreatedAt());
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblInfo.setForeground(Color.GRAY);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(new Color(247, 249, 252));
        textPanel.add(lblTitle);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(lblMsg);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(lblInfo);

        card.add(textPanel, BorderLayout.CENTER);
        return card;
    }
}
