package Controller;

import View.LoginView;
import View.MainPanel;
import View.StartPageView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginEventHandler implements ActionListener {

    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JCheckBox revealPassword;
    private JFrame frame;
    private Integer userID = null;

    public LoginEventHandler(JTextField usernameField, JTextField emailField, JPasswordField passwordField, JCheckBox revealPassword, JFrame frame) {
        this.usernameField = usernameField;
        this.emailField = emailField;
        this.passwordField = passwordField;
        this.frame = frame;
        this.revealPassword = revealPassword;
    }

    public LoginEventHandler(JFrame frame) {
        this.frame = frame;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        UserController userController = new UserController();
        String username ;
        String email ;
        String password ;
        if(e.getSource() instanceof JButton){
            String actionCommand = ((JButton) e.getSource()).getActionCommand();
            switch (actionCommand) {
                case "register":
                    username = usernameField.getText();
                    email = emailField.getText();
                    password = passwordField.getText();
                    if (userController.checkUserEmail(email)) {
                        JOptionPane.showMessageDialog(frame, "email: " + email + " already exists.");
                        usernameField.setText("");
                        emailField.setText("");
                        passwordField.setText("");

                    } else {
                        userController.registerUser(username, email, password);
                        frame.dispose();
                        SwingUtilities.invokeLater(() -> {
                            LoginView loginView = new LoginView();
                            loginView.buildFrame();
                        });
                    }
                    break;
                case "goToLoginPage":
                    frame.dispose();
                    LoginView loginView = new LoginView();
                    loginView.buildFrame();
                    break;
                case "login":
                    username = usernameField.getText();
                    email = emailField.getText();
                    password = passwordField.getText();
                    userID = userController.checkUserExistance(username, email, password);
                    Application.userID = userID;
                    if (userID != 0) {
                        JOptionPane.showMessageDialog(frame, "User " + userID + " logged in Successfuly");
                        frame.dispose();
                        SwingUtilities.invokeLater(() -> {
                            MainPanel mainPanel = new MainPanel();
                            mainPanel.menu();
                        });
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid Credentials, try again");
                        usernameField.setText("");
                        emailField.setText("");
                        passwordField.setText("");
                    }
                    break;
                case "back":
                	frame.dispose();
                	StartPageView startPageView = new StartPageView();
                    break;
                	
                    
            }
        }
        if (e.getSource() instanceof JCheckBox){
            if (revealPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        }
    }
}
