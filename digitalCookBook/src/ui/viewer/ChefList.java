package ui.viewer;

import java.awt.CardLayout;
import javax.swing.JPanel;
import ui.admin.UserListpanel;

public class ChefList extends JPanel {

    private CardLayout cardLayout;
    private JPanel mainContent;

    public ChefList(CardLayout cardLayout, JPanel mainContent) {
        this.cardLayout = cardLayout;
        this.mainContent = mainContent;

    
        UserListpanel userList = new UserListpanel(cardLayout, mainContent, false);
        setLayout(new CardLayout());
        add(userList); // Add to this panel
    }
}
