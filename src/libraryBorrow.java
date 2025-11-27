import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class libraryBorrow extends JFrame {
    private JButton homeButton;
    private JButton listOfBooksButton;
    private JButton borrowedButton;
    private JPanel mainPanel;
    private JLabel welcomeLabel;
    private JButton logoutButton;
    private JLabel picLabel;
    private JLabel iconLabel;
    private JLabel usersOnline;
    private MemberList memberList;
    private Member currentMember;
    private int num = new Random().nextInt(10000 - 5 + 1) + 5;

    public libraryBorrow(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        // Load image from resources folder
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/wallpaper.png"));
        picLabel.setIcon(icon);
        setIconImage(new ImageIcon(getClass().getResource("/images/icon64.png")).getImage());
        memberList = new MemberList();
        ImageIcon greenCircle = new ImageIcon(getClass().getResource("/images/greendot.png"));
        iconLabel.setIcon(greenCircle);
        iconLabel.setText("Active users: " + num);
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginWindow dialog = new loginWindow("Log-in", memberList, libraryBorrow.this);
                dialog.pack();
                dialog.setLocationRelativeTo(libraryBorrow.this);
                dialog.setVisible(true);
            }
        });

        listOfBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(currentMember != null) {
                    JOptionPane.showMessageDialog(libraryBorrow.this,
                            "To implement");
                } else {
                    JOptionPane.showMessageDialog(libraryBorrow.this,
                            "Please log in first!");
                }
            }
        });

        borrowedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(currentMember != null) {
                    JOptionPane.showMessageDialog(libraryBorrow.this,
                            "To implement");
                } else {
                    JOptionPane.showMessageDialog(libraryBorrow.this,
                            "Please log in first!");
                }
            }
        });

        if(logoutButton != null) {
            logoutButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    logout();
                }
            });
        }

        updateUI();
    }

    public void setCurrentMember(Member member) {
        this.currentMember = member;
        updateUI();
    }

    private void updateUI() {
        if(currentMember != null) {
            if(welcomeLabel != null) {
                welcomeLabel.setText("Welcome, " + currentMember.getUsername() + "!");
            }
            homeButton.setText("Account");
            if(logoutButton != null) {
                logoutButton.setVisible(true);
            }
        } else {
            if(welcomeLabel != null) {
                welcomeLabel.setText("Welcome! Please log in.");
            }
            homeButton.setText("Log In");
            if(logoutButton != null) {
                logoutButton.setVisible(false);
            }
        }
    }

    private void logout() {
        int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to log out?",
                "Logout",
                JOptionPane.YES_NO_OPTION);

        if(choice == JOptionPane.YES_OPTION) {
            currentMember = null;
            updateUI();
            JOptionPane.showMessageDialog(this, "Logged out successfully!");
        }
    }

    public MemberList getMemberList() {
        return memberList;
    }
}