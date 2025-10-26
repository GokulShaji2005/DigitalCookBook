//package ui.viewer;
//
//import dao.announcementDao.AnnouncementDAO;
//import model.Announcements;
//import model.User;
//
//import javax.swing.*;
//import java.awt.*;
//import java.sql.SQLException;
//import java.util.List;
//
//public class ViewerAnnounce extends JPanel {
//    private JPanel listPanel;
//    private User loggedInUser;
//
//    public ViewerAnnounce(User loggedInUser) {
//        this.loggedInUser = loggedInUser;
//
//        setLayout(new BorderLayout(10, 10));
//        setBackground(Color.WHITE);
//        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
//
//        JLabel header = new JLabel("📢 Announcements", SwingConstants.LEFT);
//        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
//        add(header, BorderLayout.NORTH);
//
//        // 🔹 List Panel
//        listPanel = new JPanel();
//        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
//        listPanel.setBackground(Color.WHITE);
//
//        JScrollPane scrollPane = new JScrollPane(listPanel);
//        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
//        add(scrollPane, BorderLayout.CENTER);
//
//        loadAnnouncements();
//    }
//
//    public void loadAnnouncements() {
//        listPanel.removeAll();
//        try {
//            List<Announcements> announcements = new AnnouncementDAO().getAnnouncementsForRole("VIEWER");
//            if (announcements.isEmpty()) {
//                JLabel emptyLabel = new JLabel("No announcements yet.", SwingConstants.CENTER);
//                emptyLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
//                emptyLabel.setForeground(Color.GRAY);
//                listPanel.setLayout(new BorderLayout());
//                listPanel.add(emptyLabel, BorderLayout.CENTER);
//            } else {
//                listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
//                for (Announcements a : announcements) {
//                    listPanel.add(createAnnouncementCard(a));
//                    listPanel.add(Box.createVerticalStrut(10));
//                }
//            }
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//            JLabel errorLabel = new JLabel("❌ Error loading announcements", SwingConstants.CENTER);
//            listPanel.add(errorLabel);
//        }
//        revalidate();
//        repaint();
//    }
//
//    private JPanel createAnnouncementCard(Announcements a) {
//        JPanel card = new JPanel(new BorderLayout());
//        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
//        card.setBackground(new Color(247, 249, 252));
//        card.setBorder(BorderFactory.createCompoundBorder(
//                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
//                BorderFactory.createEmptyBorder(10, 10, 10, 10)
//        ));
//
//        JLabel lblTitle = new JLabel(a.getTitle());
//        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
//
//        JLabel lblMessage = new JLabel(a.getMessage());
//        lblMessage.setFont(new Font("Segoe UI", Font.PLAIN, 14));
//
//        JLabel lblInfo = new JLabel("Audience: " + a.getTargetAudience() + " | " + a.getCreatedAt());
//        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 12));
//        lblInfo.setForeground(Color.GRAY);
//
//        JPanel textPanel = new JPanel();
//        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
//        textPanel.setBackground(new Color(247, 249, 252));
//        textPanel.add(lblTitle);
//        textPanel.add(Box.createVerticalStrut(3));
//        textPanel.add(lblMessage);
//        textPanel.add(Box.createVerticalStrut(3));
//        textPanel.add(lblInfo);
//
//        card.add(textPanel, BorderLayout.WEST);
//
//        return card;
//    }
//}
package ui.viewer;

import dao.announcementDao.AnnouncementDAO;
import model.Announcements;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ViewerAnnounce extends JPanel {
    private JPanel listPanel;
    private User loggedInUser;

    public ViewerAnnounce(User loggedInUser) {
        this.loggedInUser = loggedInUser;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // 🔹 Header
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("📢 Announcements");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));

        topPanel.add(titleLabel, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);

        // 🔹 List panel
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // Load announcements from DB
        loadAnnouncements();
    }

    // 🔹 Load announcements for Viewer
    public void loadAnnouncements() {
        listPanel.removeAll();

        try {
            List<Announcements> announcements = new AnnouncementDAO().getAnnouncementsForRole("Viewer");

            if (announcements.isEmpty()) {
                JLabel emptyLabel = new JLabel("No announcements yet.", SwingConstants.CENTER);
                emptyLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
                emptyLabel.setForeground(Color.GRAY);

                JPanel emptyPanel = new JPanel(new BorderLayout());
                emptyPanel.setBackground(Color.WHITE);
                emptyPanel.add(emptyLabel, BorderLayout.CENTER);
                listPanel.add(emptyPanel);
            } else {
                for (Announcements a : announcements) {
                    listPanel.add(createAnnouncementCard(a));
                    listPanel.add(Box.createVerticalStrut(10));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            JLabel errorLabel = new JLabel("❌ Error loading announcements", SwingConstants.CENTER);
            errorLabel.setForeground(Color.RED);
            listPanel.add(errorLabel);
        }

        revalidate();
        repaint();
    }

    // 🔹 Create a horizontal announcement strip
    private JPanel createAnnouncementCard(Announcements a) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(247, 249, 252));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

        // Left: Title + message
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        JLabel titleLabel = new JLabel(a.getTitle());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(new Color(40, 40, 40));

        JLabel messageLabel = new JLabel("<html>" + a.getMessage() + "</html>");
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        messageLabel.setForeground(new Color(70, 70, 70));

        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(3));
        textPanel.add(messageLabel);

        // Right: Role or date
//        JLabel audienceLabel = new JLabel(a.getTargetAudience());
//        audienceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
//        audienceLabel.setForeground(new Color(120, 120, 120));
//
//        JLabel dateLabel = new JLabel(a.getCreatedAt().toString());
//        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
//        dateLabel.setForeground(new Color(150, 150, 150));

//        JPanel rightPanel = new JPanel();
//        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
//        rightPanel.setOpaque(false);
//        rightPanel.add(audienceLabel);
//        rightPanel.add(dateLabel);

        card.add(textPanel, BorderLayout.CENTER);
//        card.add(rightPanel, BorderLayout.EAST);

        return card;
    }
}
