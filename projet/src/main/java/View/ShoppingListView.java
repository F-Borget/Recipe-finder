package View;


import Controller.*;
import Model.ConvertIngredientToShoppingList;
import Model.IngredientAPI;
import Model.RecipeManager;
import Model.ShoppingList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class ShoppingListView extends JPanel {
	private JTextField ingredientNameField = new JTextField(25);
	private JTextField quantityField = new JTextField(25);
	String[] unitTypes = { "cL", "g", "unit" };
//    unitComboBox
	private JComboBox<String> unitComboBox = new JComboBox<>(unitTypes);
	private JPanel ingredientPanel;
	private JLabel ingredientsLabel = new JLabel();

	protected void myShoppingList() {
		this.removeAll();
		this.revalidate();
		this.repaint();

		this.setBackground(Color.decode("#efefef"));

		JPanel searchPanel = new JPanel();
		searchPanel.setBackground(Color.decode("#efefef"));
		searchPanel.setLayout(null);
		searchPanel.setBounds(0, 0, 1280, 100);

		ingredientPanel = new RoundPanel(new GridBagLayout());
		ingredientPanel.setBounds(10, 200, 1260, 400);
		ingredientPanel.setBackground(Color.decode("#FFFFFF"));
//// TODO
//        // Définissez la bordure avec un contour noir
//        Border border = BorderFactory.createLineBorder(Color.BLACK);
//// TODO
//        // Définissez la couleur de fond à blanc
//        ingredientPanel.setBackground(Color.WHITE);

		int totalIngredients = ShoppingListController.countIngredientsInShoppingList();

		ingredientsLabel.setText("Ingredients in my Shopping list: " + totalIngredients);
		ingredientsLabel.setBounds(575, 30, 200, 25);
		Border bottomBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK);
		ingredientsLabel.setBorder(bottomBorder);
		searchPanel.add(ingredientsLabel);

		// Bouton Add an ingredient
		RoundButton addIngredientButton = new RoundButton("Add an ingredient to my Shopping list");
		addIngredientButton.setBackground(Color.decode("#7AA95C"));
		// Bouton Add an ingredient

		addIngredientButton.setBounds(100, 100, 300, 30);
		addIngredientButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showAddIngredientForm(ingredientPanel);
			}
		});
		this.add(addIngredientButton);

        RoundButton resetButton = new RoundButton("Reset my list");
        resetButton.setBackground(Color.decode("#A7001E"));
        resetButton.setForeground(Color.BLACK);
        resetButton.setBounds(500, 100, 300, 30);
        // TODO 8
        resetButton.setActionCommand("resetShoppingList");
        resetButton.addActionListener(new ShoppingListEventHandler(this));

        this.add(resetButton);
       
        //Bouton download
        RoundButton downloadButton = new RoundButton("Download");
        downloadButton.setBackground(Color.decode("#A87C7C"));
        downloadButton.setForeground(Color.BLACK);
        downloadButton.setBounds(900, 100, 300, 30);
        this.add(downloadButton);

        // TODO DONE 1
        downloadButton.setActionCommand("download");
        downloadButton.addActionListener(new ShoppingListEventHandler());

        JLabel myIngredientsLabel = new JLabel("My List");
        myIngredientsLabel.setBounds(10, 160, 1240, 40);
        myIngredientsLabel.setBackground(Color.decode("#FFFFFF"));
        myIngredientsLabel.setHorizontalAlignment(JLabel.CENTER);
        myIngredientsLabel.setVerticalAlignment(JLabel.CENTER);

          // Appliquer la police d'écriture
        Font myIngredientsLabelFont = new Font("Arial", Font.PLAIN, 22);
        myIngredientsLabel.setFont(myIngredientsLabelFont);

        this.setLayout(null);
        this.add(searchPanel);
        this.add(ingredientPanel);
        this.add(myIngredientsLabel);
        updateIngredientPanel();
    }
 private void showAddIngredientForm(JPanel ingredientPanel) {
        JFrame frame = new JFrame("Add Ingredient");
        JLabel titleLabel = new JLabel("Add Ingredient");
        ingredientNameField.setText("");
        quantityField.setText("");
        unitComboBox.setSelectedItem("g");
        cutomizeForm(frame,titleLabel);
        JButton okButton = new JButton("OK");
        okButton.setActionCommand("AddIngredientToShoppingList");
        okButton.addActionListener(new ShoppingListEventHandler(this,ingredientNameField,quantityField,unitComboBox));
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        cutomizeBottom(frame,okButton);
        frame.setVisible(true);
    }

	private void showAddIngredientForm(JPanel ingredientPanel, ShoppingList shoppingList) {
		JFrame frame = new JFrame("Update Ingredient");
		JLabel titleLabel = new JLabel("Update Ingredient");
		ingredientNameField.setText(shoppingList.getIngredient_name());
		quantityField.setText(String.valueOf(shoppingList.getIngredient_quantity()));
		unitComboBox.setSelectedItem(shoppingList.getIngredient_unit());
		cutomizeForm(frame, titleLabel);
		JButton okButton = new JButton("OK");
		 okButton.setActionCommand("UpdateIngredientInShoppingList");
	        okButton.addActionListener(new ShoppingListEventHandler(this,ingredientNameField,quantityField,unitComboBox,shoppingList.getId_ingredient()));
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		cutomizeBottom(frame, okButton);
		frame.setVisible(true);
	}

	private void cutomizeForm(JFrame frame, JLabel titleLabel) {
		frame.setSize(500, 350);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		JPanel formPanel = new JPanel();
		formPanel.setLayout(new GridLayout(4, 1));
		titleLabel.setBackground(Color.decode("#7AA95C"));
		titleLabel.setOpaque(true);
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

		JLabel nameLabel = new JLabel("         Ingredient:                  ");
		JPanel ingrePanel = new JPanel(new BorderLayout());
		ingrePanel.add(nameLabel, BorderLayout.WEST);
		ingrePanel.add(ingredientNameField, BorderLayout.CENTER);
		JLabel sapceLabel = new JLabel("                ");
		ingrePanel.add(sapceLabel, BorderLayout.EAST);
		JPanel spaceingrePanel = new JPanel();
		spaceingrePanel.setBorder(new EmptyBorder(0, 0, 5, 0));
		ingrePanel.add(spaceingrePanel, BorderLayout.SOUTH);
		JPanel spaceingrePanel2 = new JPanel();
		spaceingrePanel2.setBorder(new EmptyBorder(0, 0, 5, 0));
		ingrePanel.add(spaceingrePanel2, BorderLayout.NORTH);

		JLabel quantityLabel = new JLabel("         Quantity:                      ");
		JPanel quantityPanel = new JPanel(new BorderLayout());
		quantityPanel.add(quantityLabel, BorderLayout.WEST);
		quantityPanel.add(quantityField, BorderLayout.CENTER);
		quantityPanel.add(unitComboBox, BorderLayout.EAST);
		JPanel spacequantityPanel = new JPanel();
		spacequantityPanel.setBorder(new EmptyBorder(0, 0, 5, 0));
		quantityPanel.add(spacequantityPanel, BorderLayout.SOUTH);
		JPanel spacequantityPanel2 = new JPanel();
		spacequantityPanel2.setBorder(new EmptyBorder(0, 0, 5, 0));
		quantityPanel.add(spacequantityPanel2, BorderLayout.NORTH);
		formPanel.add(titleLabel);
		formPanel.add(ingrePanel);
		formPanel.add(quantityPanel);
		frame.add(formPanel, BorderLayout.CENTER);
	}

    private JPanel cutomizeBottom(JFrame frame,JButton okButton){

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Fermer la fenêtre sans soumettre
            }
        });

		JPanel bottomPanel = new JPanel(new BorderLayout());
		JPanel greenPanel1 = new JPanel();
		greenPanel1.setBackground(Color.decode("#7AA95C"));
		JPanel greenPanel2 = new JPanel();
		greenPanel2.setBackground(Color.decode("#7AA95C"));
		JPanel buttonPanel = new JPanel();
		JPanel spacePanel2 = new JPanel();
		JPanel spacePanel3 = new JPanel();
		JPanel spacePanel4 = new JPanel();
		spacePanel2.setBorder(new EmptyBorder(0, 0, 5, 0));
		spacePanel3.setBorder(new EmptyBorder(0, 0, 5, 0));
		spacePanel4.setBorder(new EmptyBorder(0, 0, 5, 0));
		spacePanel2.setBackground(Color.decode("#7AA95C"));
		spacePanel3.setBackground(Color.decode("#7AA95C"));
		spacePanel4.setBackground(Color.decode("#7AA95C"));
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
		frame.add(bottomPanel, BorderLayout.SOUTH);
		return bottomPanel;
	}   

	public void listIngredients(JPanel ingredientPanel) {
		ingredientPanel.removeAll();
		ingredientPanel.revalidate();
		ingredientPanel.repaint();
		List<ShoppingList> oldShoppingList = ShoppingListController.listShoppingList();
		List<IngredientAPI> fridgeIngredients = FridgeController.selectIngredients();
		List<IngredientAPI> convertedShoppingList = ConvertIngredientToShoppingList.convertToIngredientAPI(oldShoppingList);
		List<IngredientAPI> finalShoppingList = RecipeManager.findMissingIngredients(convertedShoppingList, fridgeIngredients);
		List<ShoppingList> shoppingList = ConvertIngredientToShoppingList.convertToShoppingList(finalShoppingList);
		int i = 0;
		int taille = shoppingList.size();
		ingredientsLabel.setText("Ingredients in my list: " + taille);

		for (ShoppingList ingredient : shoppingList) {
			int collum = i % 3;
			int line = i / 3;
			i = i + 1;
			if (!ingredient.equals(null)) {
				JTextArea ingredientLabel = new JTextArea(ingredient.getIngredient_name() + " " + ingredient.getIngredient_quantity() + " " + ingredient.getIngredient_unit() );
        		ingredientLabel.setEditable(false);
        		ingredientLabel.setLineWrap(true);
        		ingredientLabel.setWrapStyleWord(true);
				// Créer des boutons ronds rouges et verts

				JPanel redPanel = new JPanel(new BorderLayout());
				JPanel greenPanel = new JPanel(new BorderLayout());

				// Ajout d'un espace vide entre les deux panels
				JPanel redSouthPanel = new JPanel();
				redSouthPanel.setBackground(Color.WHITE);
				redSouthPanel.setBorder(new EmptyBorder(0, 0, 5, 0));
				redPanel.add(redSouthPanel, BorderLayout.SOUTH);

				// Ajout d'un espace vide entre les deux panels
				JPanel redNorthPanel = new JPanel();
				redNorthPanel.setBackground(Color.WHITE);
				redNorthPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
				redPanel.add(redNorthPanel, BorderLayout.NORTH);

				// Ajout d'un espace vide entre les deux panels
				JPanel redEastPanel = new JPanel();
				redEastPanel.setBackground(Color.WHITE);
				redEastPanel.setBorder(new EmptyBorder(0, 0, 0, 5));
				redPanel.add(redEastPanel, BorderLayout.EAST);

				// Ajout d'un espace vide entre les deux panels
				JPanel redWestPanel = new JPanel();
				redWestPanel.setBackground(Color.WHITE);
				redWestPanel.setBorder(new EmptyBorder(0, 80, 0, 0));
				redPanel.add(redWestPanel, BorderLayout.WEST);

				// Ajout d'un espace vide entre les deux panels
				JPanel greenSouthPanel = new JPanel();
				greenSouthPanel.setBackground(Color.WHITE);
				greenSouthPanel.setBorder(new EmptyBorder(0, 0, 5, 0));
				greenPanel.add(greenSouthPanel, BorderLayout.SOUTH);

				// Ajout d'un espace vide entre les deux panels
				JPanel greenNorthPanel = new JPanel();
				greenNorthPanel.setBackground(Color.WHITE);
				greenNorthPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
				greenPanel.add(greenNorthPanel, BorderLayout.NORTH);

				// Ajout d'un espace vide entre les deux panels
				JPanel greenEastPanel = new JPanel();
				greenEastPanel.setBackground(Color.WHITE);
				greenEastPanel.setBorder(new EmptyBorder(0, 0, 0, 80));
				greenPanel.add(greenEastPanel, BorderLayout.EAST);

				// Ajout d'un espace vide entre les deux panels
				JPanel greenWestPanel = new JPanel();
				greenWestPanel.setBackground(Color.WHITE);
				greenWestPanel.setBorder(new EmptyBorder(0, 5, 0, 0));
				greenPanel.add(greenWestPanel, BorderLayout.WEST);

				ImageIcon icon = new ImageIcon("images/delete.png");
				JButton redButton = new JButton();
				redButton.setIcon(icon);
				redButton.setBorder(getBorder());
				redButton.setBackground(Color.WHITE);

				ImageIcon icon2 = new ImageIcon("images/edit.png");
				JButton greenButton = new JButton();
				greenButton.setIcon(icon2);
				greenButton.setBorder(getBorder());
				greenButton.setBackground(Color.WHITE);

				redButton.setEnabled(true);
				greenButton.setEnabled(true);

				redButton.setName(String.valueOf(ingredient.getId_ingredient()));
				 redButton.setActionCommand("deleteIngredientFromShoppingList");
	                redButton.addActionListener(new ShoppingListEventHandler(this));


				greenButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						showAddIngredientForm(ingredientPanel, ingredient);
					}
				});
				// Créer un panneau pour contenir le label et les boutons
				JPanel oneIngredientPanel = new JPanel(new GridLayout(1, 3));
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridx = collum;
				gbc.gridy = line;
				if (i == 0) {
					gbc.anchor = GridBagConstraints.NORTHWEST;
				}
				oneIngredientPanel.setBackground(Color.WHITE);
				oneIngredientPanel.setPreferredSize(new Dimension(400, 100));
				RoundBorder roundBorder = new RoundBorder(10, Color.decode("#CCCCCC"));

				oneIngredientPanel.setBorder(roundBorder);

				// Ajouter le label et les boutons au panneau
				oneIngredientPanel.add(ingredientLabel);
				redPanel.add(redButton, BorderLayout.CENTER);
				greenPanel.add(greenButton, BorderLayout.CENTER);
				oneIngredientPanel.add(redPanel);
				oneIngredientPanel.add(greenPanel);

				// Ajouter le panneau à ingredientPanel
				ingredientPanel.add(oneIngredientPanel, gbc);

			}
		}

		// Menu déroulant
		JScrollPane ingredientScrollPane = new JScrollPane(ingredientPanel);
		ingredientScrollPane.setBackground(Color.WHITE);
		ingredientScrollPane.setBorder(BorderFactory.createEmptyBorder());
		ingredientScrollPane.setBounds(10, 200, 1260, 400);
		this.add(ingredientScrollPane);

	}

	public void updateIngredientPanel() {
		listIngredients(ingredientPanel);
	}

	public void updateList() {
		this.myShoppingList();
	}
}
