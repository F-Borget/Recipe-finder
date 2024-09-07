package View;

import Controller.LoginEventHandler;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.*;

public class LoginView {
    private JLabel usernameLabel = new JLabel("username");
    private JLabel emailLabel = new JLabel("email");
    private JLabel passwordLabel = new JLabel("password");
    private JTextField usernameField = new JTextField();
    private JTextField emailField = new JTextField();
    private JPasswordField passwordField = new JPasswordField();
    private JButton loginButton = new JButton("Login");
    private JButton backButton = new JButton("Back");
    private JCheckBox revealPassword = new JCheckBox("Reveal Password");
    private JFrame frame = new JFrame();

    public void LoginView(){
    }

    public void buildFrame(){
    	
    	// Changement du logo de l'application
    	ImageIcon icon = new ImageIcon("images\\logo_icon_rbg.png");
        Image img = icon.getImage();
        frame.setIconImage(img);
        
        frame.setLayout(null);
        frame.setTitle("Login");

        // Set the background color of the content pane
        frame.getContentPane().setBackground(Color.WHITE);

        // Image at the top with FlowLayout
        icon = new ImageIcon("images\\logo_login.png");
        img = icon.getImage();
        Image scaledImage = img.getScaledInstance(310, 80, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        imagePanel.setBackground(Color.WHITE);
        imagePanel.add(imageLabel);
        imagePanel.setBounds(0, 0, 400, 100); // Set bounds for the image panel
        frame.add(imagePanel);
        
        setBound();
        addFieldsToFrame();
        addButtonEvents();

        revealPassword.setBackground(Color.WHITE);

        frame.setBounds(10, 10, 400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private void setBound(){
        usernameLabel.setBounds(70, 120, 100, 30);
        usernameField.setBounds(165, 120, 150, 30);
        emailLabel.setBounds(70, 180, 100, 30);
        emailField.setBounds(165, 180, 150, 30);
        passwordLabel.setBounds(70, 240, 100, 30);
        passwordField.setBounds(165, 240, 150, 30);
        revealPassword.setBounds(165, 270, 150, 30);
        loginButton.setBounds(70, 350, 100, 30);
        loginButton.setBackground(new Color(110, 215, 40));
        backButton.setBounds(220, 350, 100, 30);
        backButton.setBackground(new Color(250, 250, 250));
    }

    private void addFieldsToFrame(){
        frame.add(usernameLabel);
        frame.add(usernameField);
        frame.add(emailLabel);
        frame.add(emailField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(revealPassword);
        frame.add(loginButton);
        frame.add(backButton);
    }

    private void addButtonEvents(){
        loginButton.setActionCommand("login");
        backButton.setActionCommand("back");
        revealPassword.addActionListener(new LoginEventHandler(usernameField, emailField,passwordField,revealPassword,frame));
        loginButton.addActionListener(new LoginEventHandler(usernameField, emailField,passwordField,revealPassword,frame));
        backButton.addActionListener(new LoginEventHandler(frame));
    }
}
