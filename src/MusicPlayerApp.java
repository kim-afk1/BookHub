import javax.swing.*;

public class MusicPlayerApp {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {

            MemberList memberList = new MemberList();
            memberList.addMember(new Member("admin", "admin@musicplayer.com", "admin"));

            Mp3PlayerGUI mp3PlayerGUI = new Mp3PlayerGUI();

            // Show login window first
            loginWindow login = new loginWindow("Login to Swingify", memberList, mp3PlayerGUI);
            login.pack();
            login.setLocationRelativeTo(null);
            login.setVisible(true);
        });
    }
}