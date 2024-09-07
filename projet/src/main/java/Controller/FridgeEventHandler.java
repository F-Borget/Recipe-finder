package Controller;

import Model.ConnectionToDB;
import Model.DataBaseManager;
import View.Fridge;
import View.RegisterView;
import View.StartPageView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class FridgeEventHandler implements ActionListener {

//    private JButton addButton;
    private JTextField ingredientNameField;
    private JTextField quantityField;
    private JComboBox<String> unitComboBox;
    private JTextField expirationDateField;
    private Fridge fridge;
    private String id;
    private FridgeController fridgeController = new FridgeController();
    public FridgeEventHandler(Fridge fridge, JTextField ingredientNameField, JTextField expirationDateField, JTextField quantityField, JComboBox<String> unitComboBox){
        this.ingredientNameField = ingredientNameField;
        this.expirationDateField = expirationDateField;
        this.quantityField = quantityField;
        this.unitComboBox = unitComboBox;
        this.fridge = fridge;
    }

    public FridgeEventHandler(){
    }
    public FridgeEventHandler(Fridge fridge, JTextField ingredientNameField, JTextField quantityField, JComboBox<String> unitComboBox, JTextField expirationDateField, String id) {
        this.fridge = fridge;
        this.ingredientNameField = ingredientNameField;
        this.quantityField = quantityField;
        this.unitComboBox = unitComboBox;
        this.expirationDateField = expirationDateField;
        this.id = id;
    }
    public FridgeEventHandler(Fridge fridge) {
        this.fridge = fridge;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch (actionCommand){
            case "AddIngredient":
                String ingredientName = this.ingredientNameField.getText();
                String quatity = this.quantityField.getText();
                String unity = this.unitComboBox.getSelectedItem().toString();
                String expirationDate = this.expirationDateField.getText();
                System.out.println(ingredientName+"---"+quatity+"---"+expirationDate);
                fridgeController.insertIngredients(ingredientName,expirationDate,quatity,unity);
                fridge.updateList();
                break;
            case "resetFridge":
                JFrame frame = new JFrame("Reset Fridge ");
                JButton okButton = new JButton("Yes");
                okButton.addActionListener(
                        new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                FridgeController fridgeController = new FridgeController();
                                fridgeController.resetFridge();
                                fridge.updateList();
                                frame.dispose();
                            }
                        });
                JLabel titleLabel = new JLabel("Do you want to reset the fridge ?");
                frame(frame,okButton,titleLabel);
                break;
            case "deleteIngredientInFridge":
                JButton button = (JButton) e.getSource();
                JFrame frame1 = new JFrame("Delete Ingredient");
                JButton okButton1 = new JButton("Yes");
                okButton1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String ingredientID = button.getName();
                        System.out.println("button name = "+button.getName());
                        FridgeController fridgeController = new FridgeController();
                        fridgeController.deleteIngredients(ingredientID);
                        fridge.updateList();
                        frame1.dispose();
                    }
                });
                JLabel titleLabel1 = new JLabel("Do you want to delete this ingredient?");
                frame(frame1,okButton1,titleLabel1);
                break;
            case "updateIngredient":
                String ingredient_id = id;
                String ingredient_name = ingredientNameField.getText();
                String expiration_date = expirationDateField.getText();
                String quantity = quantityField.getText();
                String unity1 = unitComboBox.getSelectedItem().toString();
                System.out.println(ingredient_id +"=="+ingredient_name+"=="+expiration_date+"=="+quantity+"=="+unity1);
                fridgeController.updateIngredient(ingredient_name,expiration_date,quantity,unity1,ingredient_id);
                fridge.updateList();
                break;
                
            case "Logout":
                JComponent comp = (JComponent) e.getSource();
                Window win = SwingUtilities.getWindowAncestor(comp);
                win.dispose();
                SwingUtilities.invokeLater(() -> {
                    RegisterView registerView = new RegisterView();
                    registerView.buildFrame();
                });
                break;

        }
    }
    private void frame(JFrame frame, JButton okButton,JLabel titleLabel){
        frame.setSize(350, 100);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // création du paneau principal
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(1, 1));

        // création du titre de la page
        titleLabel.setOpaque(true);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // création du bouton et de si on appuie sur Cancel
        JButton cancelButton = new JButton("No");
        cancelButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose(); // Fermer la fenêtre sans soumettre
                    }
                });

        // ajout des différentes lignes sur le paneau principal
        formPanel.add(titleLabel);

        // gestion graphique du bas du panneau pour les boutons Cancel et OK
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel greenPanel1 = new JPanel();
        JPanel greenPanel2 = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel spacePanel2 = new JPanel();
        JPanel spacePanel3 = new JPanel();
        JPanel spacePanel4 = new JPanel();
        spacePanel2.setBorder(new EmptyBorder(0, 0, 5, 0));
        spacePanel3.setBorder(new EmptyBorder(0, 0, 5, 0));
        spacePanel4.setBorder(new EmptyBorder(0, 0, 5, 0));
        buttonPanel.setLayout(new GridLayout(1, 5));
        buttonPanel.add(spacePanel2);
        buttonPanel.add(okButton);
        buttonPanel.add(spacePanel3);
        buttonPanel.add(cancelButton);
        buttonPanel.add(spacePanel4);
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        bottomPanel.add(greenPanel1, BorderLayout.NORTH);
        bottomPanel.add(greenPanel2, BorderLayout.SOUTH);
        frame.add(formPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
