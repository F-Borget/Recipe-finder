package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartPageView {
    private JFrame frame = new JFrame();

    public StartPageView() {
    	
    	// Changement du logo de l'application
        ImageIcon icon = new ImageIcon("images\\logo_icon_rbg.png");
        Image img = icon.getImage();
        frame.setIconImage(img);
        buildFrame();
    }

    public void buildFrame() {
        frame.setLayout(new GridBagLayout());
        frame.setTitle("Welcome Page");
        // Set the background color of the content pane
        frame.getContentPane().setBackground(Color.WHITE);

        // Image at the top
        ImageIcon icon = new ImageIcon("images\\logo.png");       
        Image img = icon.getImage();
    	Image scaledImage = img.getScaledInstance(260, 160, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        
        GridBagConstraints imageConstraints = new GridBagConstraints();
        imageConstraints.gridx = 0;
        imageConstraints.gridy = 0;
        imageConstraints.insets = new Insets(10, 10, 10, 10); // Padding
        frame.add(imageLabel, imageConstraints);

        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome to myFoodApp !");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        GridBagConstraints welcomeConstraints = new GridBagConstraints();
        welcomeConstraints.gridx = 0;
        welcomeConstraints.gridy = 1;
        welcomeConstraints.insets = new Insets(10, 10, 10, 10); // Padding
        frame.add(welcomeLabel, welcomeConstraints);

        // Buttons below each other
        JButton signInButton = new JButton("Log In");
        JButton signUpButton = new JButton("Sign Up");

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 2;
        buttonConstraints.insets = new Insets(10, 10, 10, 10); // Padding

        signInButton.setPreferredSize(new Dimension(100, 30));
        signInButton.setBackground(new Color(250, 250, 250));
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                LoginView loginView = new LoginView();
                loginView.buildFrame();
            }
        });
        frame.add(signInButton, buttonConstraints);

        buttonConstraints.gridy = 3;

        signUpButton.setPreferredSize(new Dimension(100, 30));
        signUpButton.setBackground(new Color(110, 215, 40));
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                RegisterView registerView = new RegisterView();
                registerView.buildFrame();
            }
        });
        frame.add(signUpButton, buttonConstraints);

        frame.setSize(400, 500);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
