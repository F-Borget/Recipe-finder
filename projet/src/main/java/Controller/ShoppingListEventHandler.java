package Controller;

import View.ShoppingListView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShoppingListEventHandler implements ActionListener {
    private JTextField ingredientNameField;
    private JTextField quantityField;
    private JComboBox<String> unitComboBox;

    private ShoppingListView shoppingListView;
    private ShoppingListController shoppingListController = new ShoppingListController();
    private int id;

    public ShoppingListEventHandler(ShoppingListView shoppingListView, JTextField ingredientNameField, JTextField quantityField, JComboBox<String> unitComboBox, int id) {
        this.shoppingListView = shoppingListView;
        this.ingredientNameField = ingredientNameField;
        this.quantityField = quantityField;
        this.unitComboBox = unitComboBox;
        this.id = id;
    }
    public ShoppingListEventHandler( ShoppingListView shoppingListView, JTextField ingredientNameField, JTextField quantityField, JComboBox<String> unitComboBox) {
        this.ingredientNameField = ingredientNameField;
        this.quantityField = quantityField;
        this.unitComboBox = unitComboBox;
        this.shoppingListView = shoppingListView;
    }
    public ShoppingListEventHandler(ShoppingListView shoppingListView) {
        this.shoppingListView = shoppingListView;
    }
    public ShoppingListEventHandler(){
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch (actionCommand){
            case "AddIngredientToShoppingList":
                String ingredient_name = ingredientNameField.getText();
                String quantity = quantityField.getText();
                String unit = (String) unitComboBox.getSelectedItem();
                shoppingListController.insertIngredientsInShoppingList(ingredient_name,quantity,unit);
                shoppingListView.updateList();
                break;
            case "deleteIngredientFromShoppingList":
                JButton button = (JButton) e.getSource();
                JFrame frame = new JFrame("delete ");
                JLabel titleLabel = new JLabel("Do you want to delete this ingredeint from shopping list ?");
                JButton okButton = new JButton("Yes");
                okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int ingredientID = Integer.parseInt(button.getName());
                        System.out.println("button name = " + button.getName());
                        ShoppingListController shoppingListController = new ShoppingListController();
                        shoppingListController.deleteIngredientFromShoppingList(ingredientID);
                        shoppingListView.updateList();
                        frame.dispose();
                    }
                });
                frame(frame,okButton,titleLabel);
                break;
            case "resetShoppingList":
                shoppingListController.resetShoppingList();
                shoppingListView.updateList();
                break;
            case "UpdateIngredientInShoppingList":
                String ingredient_name1 = ingredientNameField.getText();
                String quantity1 = quantityField.getText();
                String unity = unitComboBox.getSelectedItem().toString();
                shoppingListController.updateIngredientInShoppingList(ingredient_name1,quantity1,unity, String.valueOf(id));
                shoppingListView.updateList();
                break;
            case "download":
                ShoppingListController shoppingListController = new ShoppingListController();
                shoppingListController.downloadShoppingList();
                break;
        }

    }
    private void frame(JFrame frame, JButton okButton, JLabel titleLabel){
        frame.setSize(300, 100);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // création du paneau principal
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(1, 1));
        titleLabel.setOpaque(true);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // création du bouton et de si on appuie sur Cancel
        JButton cancelButton = new JButton("No");
        cancelButton.addActionListener(new ActionListener() {
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
