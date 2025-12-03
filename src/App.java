import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create the main MP3 player GUI (initially hidden)
                Mp3PlayerGUI mp3PlayerGUI = new Mp3PlayerGUI();

                // Initialize member list
                MemberList memberList = new MemberList();

                // Add default admin account
                Member adminAccount = new Member("admin", "admin@spootify.com", "admin");
                memberList.addMember(adminAccount);

                // Create and show login window
                loginWindow loginDialog = new loginWindow("Login to Spootify", memberList, mp3PlayerGUI);
                loginDialog.pack();
                loginDialog.setLocationRelativeTo(null);
                loginDialog.setVisible(true);

                // Note: Mp3PlayerGUI will be shown after successful login
            }
        });
    }
}