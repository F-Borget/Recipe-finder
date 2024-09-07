package Controller;

import Model.RecipeAPI;
import Model.RecipeService;
import View.Favorites;
import View.RecipeDetailPanelDisposition;
import View.SelectionDisposition;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.Collectors;

public class SelectionEventHandler implements ActionListener {
    private JTextField selectionNameField;
    private JTextField descriptionField;

    private Favorites favorites;
    private SelectionController selectionController = new SelectionController();
    private RecipeAPI recipeAPI;
    private JComboBox jComboBox;
    private RecipeDetailPanelDisposition recipeDetailPanelDisposition;
    private int id;
    private int index;
    public SelectionEventHandler(Favorites favorites, int id, int index) {
        this.favorites = favorites;
        this.id=id;
        this.index=index;
    }
    public SelectionEventHandler(JTextField selectionNameField, JTextField descriptionField, RecipeDetailPanelDisposition recipeDetailPanelDisposition, RecipeAPI recipeAPI) {
        this.selectionNameField = selectionNameField;
        this.descriptionField = descriptionField;
        this.recipeDetailPanelDisposition = recipeDetailPanelDisposition;
        this.recipeAPI = recipeAPI;
    }
    public SelectionEventHandler(Favorites favorites) {
        this.favorites = favorites;
    }
    public SelectionEventHandler(RecipeAPI recipeAPI, JComboBox jComboBox) {
        this.recipeAPI = recipeAPI;
        this.jComboBox = jComboBox;
    }

    public SelectionEventHandler(JTextField selectionNameField, JTextField descriptionField,Favorites favorites) {
        this.selectionNameField = selectionNameField;
        this.descriptionField = descriptionField;
        this.favorites = favorites;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch (actionCommand){
            case "insertSelectionFromFav":
                String selection_name = selectionNameField.getText();
                String selection_desc = descriptionField.getText();
                selectionController.insertSelection(selection_name,selection_desc);
                favorites.myFavorites();
                break;
            case "addRecipeToSelection":
                String selection_name1 = (String) jComboBox.getSelectedItem();
                String title = recipeAPI.getTitle();
                String ingredients = recipeAPI.getIngredients().stream().map(ingredient->ingredient.getAmount()
                        +" "+ingredient.getUnit()+" "+ingredient.getName()).collect(Collectors.joining(","));
                selectionController.saveRecipe(title,ingredients,selection_name1);
                break;
            case "updateSelection":
                JButton button = (JButton) e.getSource();
                int id_selection = Integer.parseInt(button.getName());
                String selection_name2 = selectionNameField.getText();
                String selection_desc2 = descriptionField.getText();
                selectionController.updateSelection(id_selection,selection_name2,selection_desc2);
                favorites.myFavorites();
                break;
            case "deleteSelection":
                JButton button1 = (JButton) e.getSource();
                JFrame frame = new JFrame("Delete selection" );
                JLabel titleLabel = new JLabel("Do you want to delete this selection ?");
                JButton okButton = new JButton("Yes");
                okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int id_selection = Integer.parseInt(button1.getName());
                        SelectionController selectionController = new SelectionController();
                        selectionController.delete(id_selection);
                        favorites.myFavorites();
                        frame.dispose();
                    }
                });
                frame(frame,okButton,titleLabel);
                break;
            case "addNewSelectionFromRecipePage":
                String selection_name3 = selectionNameField.getText();
                String selection_desc3 = descriptionField.getText();
                selectionController.insertSelection(selection_name3,selection_desc3);
                recipeDetailPanelDisposition.recipeDetailPanelDisposition(recipeAPI);
                break;
            case "deleteRecipeFromSelection":
                JButton button3 = (JButton) e.getSource();
                JFrame frame3 = new JFrame("delete " );
                JLabel titleLabel3 = new JLabel("Do you want to delete this recipe ?");
                JButton okButton3 = new JButton("Yes");
                okButton3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        RecipeService selectionController = new RecipeService();
                        selectionController.deleteRecipeFromSelection(id);
                        favorites.myFavorites();
                        SelectionDisposition.detailPanelDisposition(favorites.getDetailPanel(), favorites.getSelectionList().get(index), favorites, index);
                        frame3.dispose();
                    }
                });
                frame(frame3,okButton3,titleLabel3);
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
        // bottomPanel.setBorder(new LineBorder(Color.BLACK));

        frame.add(formPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
