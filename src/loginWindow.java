import javax.swing.*;
import java.awt.event.*;

public class loginWindow extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField userField;
    private JPasswordField passField;
    private JLabel loginTitle;
    private JLabel passLabel;
    private JLabel userLabel;
    private JButton createAccountButton;
    private JLabel loginFeedback;
    private MemberList memberList;
    private libraryBorrow homeScreen;

    public loginWindow(String title, MemberList memberList, libraryBorrow homeScreen) {
        super();
        this.memberList = memberList;
        this.homeScreen = homeScreen;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setIconImage(new ImageIcon(getClass().getResource("/images/icon64.png")).getImage());

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccount dialog = new createAccount(memberList);
                dialog.pack();
                dialog.setVisible(true);
            }
        });
    }

    private void onOK() {
        String username = userField.getText();
        String password = new String(passField.getPassword());

        if(username.isEmpty() || password.isEmpty()) {
            loginFeedback.setText("Enter credentials");
            return;
        }

        Member member = memberList.findMember(username, password);
        if(member != null) {
            loginFeedback.setText("Login successful!");
            homeScreen.setCurrentMember(member);
            dispose();
        } else {
            loginFeedback.setText("Invalid username or password");
        }
    }

    private void onCancel() {
        dispose();
    }
}