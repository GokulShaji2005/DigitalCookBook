package main;
import javax.swing.SwingUtilities;

import ui.auth.AuthUi;
public class Main {
	public static void main(String args[]) {
		 SwingUtilities.invokeLater(AuthUi::new);
		
	}
}
