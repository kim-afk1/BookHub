import javax.swing.*;

public class MusicPlayerApp {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            // Initialize member list with some default members (optional)
            MemberList memberList = new MemberList();
            // Add a test account
            memberList.addMember(new Member("admin", "admin@musicplayer.com", "admin123"));

            // Create the main music player GUI (hidden initially)
            Mp3PlayerGUI mp3PlayerGUI = new Mp3PlayerGUI();

            // Show login window first
            loginWindow login = new loginWindow("Music Player Login", memberList, mp3PlayerGUI);
            login.pack();
            login.setLocationRelativeTo(null);
            login.setVisible(true);
        });
    }
}