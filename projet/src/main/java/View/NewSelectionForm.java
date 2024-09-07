package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Controller.SelectionEventHandler;
import Model.RecipeAPI;
import Model.Selection;

public class NewSelectionForm {
	private JTextField selectionNameField;
    private JTextField descriptionField;
	public void newSelectionForm(JPanel ingredientPanel,Object... args) {

        JLabel titleLabel = new JLabel("Add a new selection");
        JFrame frame = customizeForm(titleLabel);
        selectionNameField.setText("");
        descriptionField.setText("");
        JButton okButton = null;

        if (args[0] instanceof Favorites){
            okButton = customizeButton(selectionNameField,descriptionField, (Favorites) args[0]);
        }else {
            if (args[0] instanceof RecipeDetailPanelDisposition){
                okButton = customizeButton(selectionNameField,descriptionField, (RecipeDetailPanelDisposition) args[0], (RecipeAPI) args[1]);
            }
        }
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        customizeButton(okButton,frame);
        frame.setVisible(true);
    }

    private JButton customizeButton(JTextField selectionNameField,JTextField descriptionField,Favorites favorites){
        //création du bouton et de si on appuye sur OK
        JButton okButton = new JButton("OK");
        // TODO 10
        okButton.setActionCommand("insertSelectionFromFav");
        okButton.addActionListener(new SelectionEventHandler(selectionNameField,descriptionField,favorites));
        return okButton;
    }
    public JButton customizeButton(JTextField selectionNameField,JTextField descriptionField,RecipeDetailPanelDisposition recipeDetailPanelDisposition,RecipeAPI recipeAPI){
        JButton okButton = new JButton("OK");
        // TODO 14
        okButton.setActionCommand("addNewSelectionFromRecipePage");
        okButton.addActionListener(new SelectionEventHandler(selectionNameField,descriptionField,recipeDetailPanelDisposition,recipeAPI));
        return okButton;
    }

    public void updateSelectionForm(Selection selection,Favorites favorites) {
        JLabel titleLabel = new JLabel("Update "+selection.getName_selection());
        JFrame frame = customizeForm(titleLabel);
        selectionNameField.setText(selection.getName_selection());
        descriptionField.setText(selection.getDescription_selection());
        JButton okButton = new JButton("OK");
        okButton.setName(String.valueOf(selection.getId_selection()));
        // TODO 12
        okButton.setActionCommand("updateSelection");
        okButton.addActionListener(new SelectionEventHandler(selectionNameField,descriptionField,favorites));
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        customizeButton(okButton,frame);
        frame.setVisible(true);
    }

    public void customizeButton(JButton okButton,JFrame frame){
        //création du bouton et de si on appuye sur Cancel
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Fermer la fenêtre sans soumettre
            }
        });

        //gestion graphique du bas du panneau pour les boutton Cancel et OK
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel greenPanel1 = new JPanel();
        greenPanel1.setBackground(Color.decode("#A7F578"));
        JPanel greenPanel2 = new JPanel();
        greenPanel2.setBackground(Color.decode("#A7F578"));
        JPanel buttonPanel = new JPanel();
        JPanel spacePanel2 = new JPanel();
        JPanel spacePanel3 = new JPanel();
        JPanel spacePanel4 = new JPanel();
        spacePanel2.setBorder(new EmptyBorder(0, 0, 5, 0));
        spacePanel3.setBorder(new EmptyBorder(0, 0, 5, 0));
        spacePanel4.setBorder(new EmptyBorder(0, 0, 5, 0));
        spacePanel2.setBackground(Color.decode("#A7F578"));
        spacePanel3.setBackground(Color.decode("#A7F578"));
        spacePanel4.setBackground(Color.decode("#A7F578"));
        buttonPanel.setLayout(new GridLayout(1, 5));
        buttonPanel.add(spacePanel2);
        buttonPanel.add(okButton);
        buttonPanel.add(spacePanel3);
        buttonPanel.add(cancelButton);
        buttonPanel.add(spacePanel4);

        bottomPanel.add(buttonPanel,BorderLayout.CENTER);
        bottomPanel.add(greenPanel1,BorderLayout.NORTH);
        bottomPanel.add(greenPanel2,BorderLayout.SOUTH);
        //bottomPanel.setBorder(new LineBorder(Color.BLACK));
        frame.add(bottomPanel, BorderLayout.SOUTH);

    }
    private JFrame customizeForm(JLabel titleLabel){
        JFrame frame = new JFrame("Add a new selection");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        //création du paneau principal
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3, 1));

        titleLabel.setBackground(Color.decode("#A7F578"));
        titleLabel.setOpaque(true);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        //titleLabel.setBorder(new LineBorder(Color.BLACK));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        //création de la ligne Ingredient avec la zone de texte
        JLabel nameLabel = new JLabel("         Name:                  ");
        selectionNameField = new JTextField(25);
        JPanel ingrePanel = new JPanel(new BorderLayout());
        ingrePanel.add(nameLabel, BorderLayout.WEST);
        ingrePanel.add( selectionNameField, BorderLayout.CENTER);
        JLabel sapceLabel = new JLabel("                ");
        ingrePanel.add(sapceLabel, BorderLayout.EAST);
        JPanel spaceingrePanel = new JPanel();
        spaceingrePanel.setBorder(new EmptyBorder(0, 0, 5, 0));
        ingrePanel.add(spaceingrePanel, BorderLayout.SOUTH);
        JPanel spaceingrePanel2 = new JPanel();
        spaceingrePanel2.setBorder(new EmptyBorder(0, 0, 5, 0));
        ingrePanel.add(spaceingrePanel2, BorderLayout.NORTH);

        JLabel expirationDateLabel = new JLabel("         Description:        ");
        descriptionField = new JTextField(25);

        JPanel expirationDatePanel=new JPanel(new BorderLayout());
        expirationDatePanel.add(expirationDateLabel, BorderLayout.WEST);
        expirationDatePanel.add(descriptionField, BorderLayout.CENTER);
        JLabel sapceLabel2 = new JLabel("                ");
        expirationDatePanel.add(sapceLabel2, BorderLayout.EAST);
        JPanel spaceexpiPanel = new JPanel();
        spaceexpiPanel.setBorder(new EmptyBorder(0, 0, 5, 0));
        expirationDatePanel.add(spaceexpiPanel, BorderLayout.SOUTH);
        JPanel spaceexpiPanel2 = new JPanel();
        spaceexpiPanel2.setBorder(new EmptyBorder(0, 0, 5, 0));
        expirationDatePanel.add(spaceexpiPanel2, BorderLayout.NORTH);

        formPanel.add(titleLabel);
        formPanel.add(ingrePanel);
        formPanel.add(expirationDatePanel);
        frame.add(formPanel, BorderLayout.CENTER);
        return frame;
    }
}
