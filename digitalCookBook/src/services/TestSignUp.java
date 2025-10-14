package services;
public class TestSignUp {
    public static void main(String[] args) {
        SignUpService service = new SignUpService();
        try {
            boolean success = service.signUpService("gokul@2005", "mypassword", "user");
            if (success) {
                System.out.println("✅ User signed up successfully!");
            } else {
                System.out.println("❌ Sign up failed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
