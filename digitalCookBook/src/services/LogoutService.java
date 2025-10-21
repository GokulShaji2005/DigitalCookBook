package services;


import model.User;

public class LogoutService {

    
    public boolean logoutService(User user) {
        if (user != null) {
          
            user = null;
            System.out.println("✅ User logged out successfully.");
            return true;
        } else {
            System.out.println("⚠️ No user to log out.");
            return false;
        }
    }
}
