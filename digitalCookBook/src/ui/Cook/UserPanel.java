package ui.endUser;
import ui.endUser.RecipiePanel;
import javax.swing.*;
import java.awt.*;
//import java.awt.event.*;

public class UserPanel {
    JFrame frame;
    JPanel mainContent;
    CardLayout cardLayout;

    UserPanel(String username) {
        frame = new JFrame("Recipe Manager Dashboard");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

       
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(30, 144, 255));

        JLabel projectLabel = new JLabel("Recipe Manager");
        projectLabel.setForeground(Color.WHITE);
        projectLabel.setFont(new Font("Arial", Font.BOLD, 22));
        projectLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));

        JLabel welcomeLabel = new JLabel("Welcome, " + username);
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));

        topBar.add(projectLabel, BorderLayout.WEST);
        topBar.add(welcomeLabel, BorderLayout.EAST);
        frame.add(topBar, BorderLayout.NORTH);

       
        JPanel sideMenu = new JPanel();
        sideMenu.setLayout(new BoxLayout(sideMenu, BoxLayout.Y_AXIS));
        sideMenu.setBackground(new Color(240, 240, 240));
        sideMenu.setPreferredSize(new Dimension(180, 0));

        JButton btnOption1 = createMenuButton("Dashboard");
        JButton btnOption2 = createMenuButton("Recepies");
        JButton btnOption3 = createMenuButton("Others");
        JButton btnOption4 = createMenuButton("log out");

        sideMenu.add(Box.createVerticalStrut(20)); // top spacing
        sideMenu.add(btnOption1);
        sideMenu.add(Box.createVerticalStrut(15));
        sideMenu.add(btnOption2);
        sideMenu.add(Box.createVerticalStrut(15));
        sideMenu.add(btnOption3);
        sideMenu.add(Box.createVerticalStrut(15));
        sideMenu.add(btnOption4);

        frame.add(sideMenu, BorderLayout.WEST);

        cardLayout = new CardLayout();
        mainContent = new JPanel(cardLayout);

        JPanel panel1 = createPagePanel("Page 1 Content");
          RecipiePanel recipePanel=new RecipiePanel();
        JPanel panel3 = createPagePanel("Page 3 Content");

        mainContent.add(panel1, "1");
        mainContent.add(recipePanel, "2");
        mainContent.add(panel3, "3");

        frame.add(mainContent, BorderLayout.CENTER);

        btnOption1.addActionListener(e -> cardLayout.show(mainContent, "1"));
        btnOption2.addActionListener(e -> cardLayout.show(mainContent, "2"));
        btnOption3.addActionListener(e -> cardLayout.show(mainContent, "3"));

        frame.setVisible(true);
    }


    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(160, 50)); 
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFocusPainted(false);
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));


        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 149, 237));
            }
//            public void mouseExited(java.awt.event.MouseEvent evt) {
//                button.setBackground(new Color(70, 130, 180));
//            }
        });

        return button;
    }


    private JPanel createPagePanel(String text) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(label, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserPanel("Gokul"));
    }
}
