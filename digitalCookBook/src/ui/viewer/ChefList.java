
package ui.viewer;

import java.awt.CardLayout;
import javax.swing.JPanel;

public class ChefList extends JPanel {

    public ChefList(CardLayout cardLayout, JPanel mainContent) {
        setLayout(new CardLayout());
        add(new ViewChefList(cardLayout, mainContent)); // only viewing, no delete buttons
    }
}
