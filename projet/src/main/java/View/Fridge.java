package View;

import Controller.*;
import Model.IngredientAPI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;


public class Fridge extends JPanel{
    private JTextField ingredientNameField;
    private JTextField quantityField;
    private JComboBox<String> unitComboBox;
    private JTextField expirationDateField;
    private JPanel ingredientPanel;
    private JLabel ingredientsLabel= new JLabel("Ingredients in my fridge:");

    protected void myFridge() {
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

        List<IngredientAPI> ingredients = FridgeController.selectIngredients();
        int taille=ingredients.size();
        ingredientsLabel.setText("Ingredients in my fridge: "+taille);
        ingredientsLabel.setBounds(575, 30, 170, 25);
        Border bottomBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK);
        ingredientsLabel.setBorder(bottomBorder);
        searchPanel.add(ingredientsLabel);             
		
        RoundButton addIngredientButton = new RoundButton("Add an ingredient to my fridge");
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
        
        RoundButton resetButton = new RoundButton("Reset my fridge");
        resetButton.setBackground(Color.decode("#A7001E")); 
        resetButton.setForeground(Color.BLACK);
        resetButton.setBounds(500, 100, 300, 30);
        // TODO DONE 3
        resetButton.setActionCommand("resetFridge");
        resetButton.addActionListener(new FridgeEventHandler(this));

        this.add(resetButton);

        RoundButton logoutButton = new RoundButton("Logout");
        logoutButton.setBackground(Color.decode("#A87C7C"));
        logoutButton.setForeground(Color.BLACK);
        logoutButton.setBounds(1100, 30, 100, 25);
        // TODO DONE 3
        logoutButton.setActionCommand("Logout");
        logoutButton.addActionListener(new FridgeEventHandler(this));

        this.add(logoutButton);

		// Ajout d'un bouton de tri
        JPanel sortPanel = new JPanel();
		sortPanel.setLayout(null);
		sortPanel.setBounds(900, 100, 300, 30);
		sortPanel.setBackground(Color.decode("#efefef"));
		String[] sortString = { "a-z", "date" };
		JComboBox sortComboBox = new JComboBox(sortString);
		sortComboBox.setBounds(20, 5, 120, 20);
		sortPanel.add(sortComboBox);
		RoundButton sortButton = new RoundButton("Sort");
		sortPanel.add(sortButton);
		
		sortButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ingredientPanel.removeAll();
				ingredientPanel.repaint();
				ingredientPanel.revalidate();
				if (sortComboBox.getSelectedItem().equals("a-z")) {
					Collections.sort(ingredients, Comparator.comparing(IngredientAPI::getName));
				} else {
					Collections.sort(ingredients, Comparator.comparing(IngredientAPI::getDate));
				}
				listIngredients(ingredientPanel, ingredients);
			}
		});
		
		sortButton.setBounds(160, 5, 120, 20);
		sortComboBox.setBackground(Color.WHITE);
		Border comboBoxBorder = BorderFactory.createLineBorder(Color.decode("#efefef"));	
		sortComboBox.setBorder(comboBoxBorder);
		
        this.add(sortPanel);

        sortPanel.setLayout(null);

       
        JLabel myIngredientsLabel = new JLabel("My Ingredients");
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
    	//création de la fenetre
        JFrame frame = new JFrame("Add Ingredient");
        frame.setSize(500, 350);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        //création du paneau principal
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 1)); 

        //création du titre de la page
        JLabel titleLabel = new JLabel("Add Ingredient");
        titleLabel.setBackground(Color.decode("#7AA95C")); 
        titleLabel.setOpaque(true); 
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        //titleLabel.setBorder(new LineBorder(Color.BLACK));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        //création de la ligne Ingredient avec la zone de texte
        JLabel nameLabel = new JLabel("         Ingredient:                  ");
        ingredientNameField = new JTextField(25);
        //ingredientNameField.setBorder(new RoundBorder(25));
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

     	//création de la ligne Quantity avec la zone de texte
        JLabel quantityLabel = new JLabel("         Quantity:                      ");
        quantityField = new JTextField(25);
        //quantityField.setBorder(new RoundBorder(25));
        JPanel quantityPanel = new JPanel(new BorderLayout());
        quantityPanel.add(quantityLabel, BorderLayout.WEST);
        
        
        quantityPanel.add(quantityField, BorderLayout.CENTER);
        String[] unitTypes = { "g", "cl", "unit" };
        unitComboBox = new JComboBox<>(unitTypes);
        quantityPanel.add(unitComboBox, BorderLayout.EAST);
        JPanel spacequantityPanel = new JPanel();
        spacequantityPanel.setBorder(new EmptyBorder(0, 0, 5, 0));
        quantityPanel.add(spacequantityPanel, BorderLayout.SOUTH);
        JPanel spacequantityPanel2 = new JPanel();
        spacequantityPanel2.setBorder(new EmptyBorder(0, 0, 5, 0));
        quantityPanel.add(spacequantityPanel2, BorderLayout.NORTH);

        //création de la ligne de la date d'expiration avec la zone de texte
        JLabel expirationDateLabel = new JLabel("<html><center>Expiration Date:<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;YYYY/MM/DD&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</center></html>");
        expirationDateField = new JTextField(25);
        //expirationDateField.setBorder(new RoundBorder(25));
        JPanel expirationDatePanel=new JPanel(new BorderLayout());
        expirationDatePanel.add(expirationDateLabel, BorderLayout.WEST);
        expirationDatePanel.add(expirationDateField, BorderLayout.CENTER);
        JLabel sapceLabel2 = new JLabel("                ");
        expirationDatePanel.add(sapceLabel2, BorderLayout.EAST);
        JPanel spaceexpiPanel = new JPanel();
        spaceexpiPanel.setBorder(new EmptyBorder(0, 0, 5, 0));
        expirationDatePanel.add(spaceexpiPanel, BorderLayout.SOUTH);
        JPanel spaceexpiPanel2 = new JPanel();
        spaceexpiPanel2.setBorder(new EmptyBorder(0, 0, 5, 0));
        expirationDatePanel.add(spaceexpiPanel2, BorderLayout.NORTH);

        // TODO DONE 2
        //création du bouton et de si on appuye sur OK
        JButton okButton = new JButton("OK");
        okButton.setActionCommand("AddIngredient");
        okButton.addActionListener(new FridgeEventHandler(this,ingredientNameField,expirationDateField,quantityField,unitComboBox));
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        //création du bouton et de si on appuye sur Cancel
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Fermer la fenêtre sans soumettre
            }
        });

        //ajout des différentes lignes sur le paneau principal
        formPanel.add(titleLabel);
     	
        formPanel.add(ingrePanel);
        formPanel.add(quantityPanel);
        //formPanel.add(unitPanel);
        formPanel.add(expirationDatePanel);

        //gestion graphique du bas du panneau pour les boutton Cancel et OK
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
        
        bottomPanel.add(buttonPanel,BorderLayout.CENTER);
        bottomPanel.add(greenPanel1,BorderLayout.NORTH);
        bottomPanel.add(greenPanel2,BorderLayout.SOUTH);
        //bottomPanel.setBorder(new LineBorder(Color.BLACK));

        frame.add(formPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
    
    public void listIngredients(JPanel ingredientPanel) {
	    
        ingredientPanel.removeAll();
        List<IngredientAPI> ingredients = FridgeController.selectIngredients();
        int i=0;
        int taille=ingredients.size();
        ingredientsLabel.setText("Ingredients in my fridge: "+taille);
        
        for(IngredientAPI ingredient:ingredients){ 
        	
        	int collum=i%3;
            int line=i/3;
            i=i+1;
             
        	if (!ingredient.equals(null)){
        		JTextArea ingredientLabel = new JTextArea(ingredient.getName() + " " + ingredient.getAmount() + " " + ingredient.getUnit() + " " + ingredient.getDate());
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
        		redEastPanel.setBorder(new EmptyBorder(0, 0, 0, 	5));
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
                

                redButton.setName(String.valueOf(ingredient.getId()));
                greenButton.setName(String.valueOf(ingredient.getId()));

                redButton.setEnabled(true);
                greenButton.setEnabled(true);
                redButton.setName(String.valueOf(ingredient.getId()));
                redButton.setActionCommand("deleteIngredientInFridge");
                redButton.addActionListener(new FridgeEventHandler(this));
                
                greenButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        showAddIngredientForm(ingredientPanel,ingredient);
                    }
                });
                // Créer un panneau pour contenir le label et les boutons
                JPanel oneIngredientPanel = new JPanel(new GridLayout(1,3));
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = collum;
                gbc.gridy = line;
              
                oneIngredientPanel.setBackground(Color.WHITE);
                oneIngredientPanel.setPreferredSize(new Dimension(400,100));
                RoundBorder roundBorder = new RoundBorder(10,Color.decode("#CCCCCC"));
                
                oneIngredientPanel.setBorder(roundBorder);
                
                // Ajouter le label et les boutons au panneau
                oneIngredientPanel.add(ingredientLabel);
                redPanel.add(redButton, BorderLayout.CENTER);
                greenPanel.add(greenButton, BorderLayout.CENTER);
                oneIngredientPanel.add(redPanel);
                oneIngredientPanel.add(greenPanel);
                
                // Ajouter le panneau à ingredientPanel
                ingredientPanel.add(oneIngredientPanel,gbc);
            }
        }
        
        // Menu déroulant
        JScrollPane ingredientScrollPane = new JScrollPane(ingredientPanel);
        ingredientScrollPane.setBackground(Color.WHITE);
        ingredientScrollPane.setBorder(BorderFactory.createEmptyBorder());
        ingredientScrollPane.setBounds(10, 200, 1260, 400);
        this.add(ingredientScrollPane);
        
        ingredientPanel.revalidate();
        ingredientPanel.repaint();
    }
    
public void listIngredients(JPanel ingredientPanel, List<IngredientAPI> ingredients) {
	    
        ingredientPanel.removeAll();
        int i=0;
        int taille=ingredients.size();
        ingredientsLabel.setText("Ingredients in my fridge: "+taille);
        
        for(IngredientAPI ingredient:ingredients){ 
        	
        	int collum=i%3;
            int line=i/3;
            i=i+1;
             
        	if (!ingredient.equals(null)){
        		JTextArea ingredientLabel = new JTextArea(ingredient.getName() + " " + ingredient.getAmount() + " " + ingredient.getUnit() + " " + ingredient.getDate());
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
        		redEastPanel.setBorder(new EmptyBorder(0, 0, 0, 	5));
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
                

                redButton.setName(String.valueOf(ingredient.getId()));
                greenButton.setName(String.valueOf(ingredient.getId()));

                redButton.setEnabled(true);
                greenButton.setEnabled(true);
                redButton.setName(String.valueOf(ingredient.getId()));
                // TODO DONE 4
                redButton.setActionCommand("deleteIngredientInFridge");
                redButton.addActionListener(new FridgeEventHandler(this));
                greenButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        showAddIngredientForm(ingredientPanel,ingredient);
                    }
                });
                // Créer un panneau pour contenir le label et les boutons
                JPanel oneIngredientPanel = new JPanel(new GridLayout(1,3));
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = collum;
                gbc.gridy = line;
              
                oneIngredientPanel.setBackground(Color.WHITE);
                oneIngredientPanel.setPreferredSize(new Dimension(400,100));
                RoundBorder roundBorder = new RoundBorder(10,Color.decode("#CCCCCC"));
                
                oneIngredientPanel.setBorder(roundBorder);
                
                // Ajouter le label et les boutons au panneau
                oneIngredientPanel.add(ingredientLabel);
                redPanel.add(redButton, BorderLayout.CENTER);
                greenPanel.add(greenButton, BorderLayout.CENTER);
                oneIngredientPanel.add(redPanel);
                oneIngredientPanel.add(greenPanel);
                
                // Ajouter le panneau à ingredientPanel
                ingredientPanel.add(oneIngredientPanel,gbc);
            }
        }
        
        // Menu déroulant
        JScrollPane ingredientScrollPane = new JScrollPane(ingredientPanel);
        ingredientScrollPane.setBackground(Color.WHITE);
        ingredientScrollPane.setBorder(BorderFactory.createEmptyBorder());
        ingredientScrollPane.setBounds(10, 200, 1260, 400);
        this.add(ingredientScrollPane);
        
        ingredientPanel.revalidate();
        ingredientPanel.repaint();
    }

    private void showAddIngredientForm(JPanel ingredientPanel,IngredientAPI ingredientAPI) {
    	//création de la fenetre
        JFrame frame = new JFrame("Update Ingredient");
        frame.setSize(500, 350);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        //création du paneau principal
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 1)); 

        //création du titre de la page
        JLabel titleLabel = new JLabel("Update Ingredient");
        titleLabel.setBackground(Color.decode("#7AA95C")); 
        titleLabel.setOpaque(true); 
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        //titleLabel.setBorder(new LineBorder(Color.BLACK));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        //création de la ligne Ingredient avec la zone de texte
        JLabel nameLabel = new JLabel("         Ingredient:                  ");
        ingredientNameField = new JTextField(25);
        ingredientNameField.setText(ingredientAPI.getName());
        //ingredientNameField.setBorder(new RoundBorder(25));
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

     	//création de la ligne Quantity avec la zone de texte
        JLabel quantityLabel = new JLabel("         Quantity:                      ");
        quantityField = new JTextField(25);
        quantityField.setText(String.valueOf(ingredientAPI.getAmount()));
        //quantityField.setBorder(new RoundBorder(25));
        JPanel quantityPanel = new JPanel(new BorderLayout());
        quantityPanel.add(quantityLabel, BorderLayout.WEST);
        
        
        quantityPanel.add(quantityField, BorderLayout.CENTER);
        String[] unitTypes = { "g", "cl", "unit" };
        unitComboBox = new JComboBox<>(unitTypes);
        unitComboBox.setSelectedItem(ingredientAPI.getUnit());
        quantityPanel.add(unitComboBox, BorderLayout.EAST);
        JPanel spacequantityPanel = new JPanel();
        spacequantityPanel.setBorder(new EmptyBorder(0, 0, 5, 0));
        quantityPanel.add(spacequantityPanel, BorderLayout.SOUTH);
        JPanel spacequantityPanel2 = new JPanel();
        spacequantityPanel2.setBorder(new EmptyBorder(0, 0, 5, 0));
        quantityPanel.add(spacequantityPanel2, BorderLayout.NORTH);

        //création de la ligne de la date d'expiration avec la zone de texte
        JLabel expirationDateLabel = new JLabel("<html><center>Expiration Date:<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;YYYY/MM/DD&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</center></html>");
        expirationDateField = new JTextField(25);
        expirationDateField.setText(ingredientAPI.getDate());
        //expirationDateField.setBorder(new RoundBorder(25));
        JPanel expirationDatePanel=new JPanel(new BorderLayout());
        expirationDatePanel.add(expirationDateLabel, BorderLayout.WEST);
        expirationDatePanel.add(expirationDateField, BorderLayout.CENTER);
        JLabel sapceLabel2 = new JLabel("                ");
        expirationDatePanel.add(sapceLabel2, BorderLayout.EAST);
        JPanel spaceexpiPanel = new JPanel();
        spaceexpiPanel.setBorder(new EmptyBorder(0, 0, 5, 0));
        expirationDatePanel.add(spaceexpiPanel, BorderLayout.SOUTH);
        JPanel spaceexpiPanel2 = new JPanel();
        spaceexpiPanel2.setBorder(new EmptyBorder(0, 0, 5, 0));
        expirationDatePanel.add(spaceexpiPanel2, BorderLayout.NORTH);

        JButton okButton = new JButton("update");
        // TODO DONE 5
        okButton.setActionCommand("updateIngredient");
        okButton.addActionListener(new FridgeEventHandler(this,ingredientNameField, quantityField, unitComboBox, expirationDateField, String.valueOf(ingredientAPI.getId())));
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Fermer la fenêtre sans soumettre
            }
        });

        formPanel.add(titleLabel);

        formPanel.add(ingrePanel);
        formPanel.add(quantityPanel);
        //formPanel.add(unitPanel);
        formPanel.add(expirationDatePanel);

      //gestion graphique du bas du panneau pour les boutton Cancel et OK
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
        
        bottomPanel.add(buttonPanel,BorderLayout.CENTER);
        bottomPanel.add(greenPanel1,BorderLayout.NORTH);
        bottomPanel.add(greenPanel2,BorderLayout.SOUTH);
        //bottomPanel.setBorder(new LineBorder(Color.BLACK));

        frame.add(formPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
   
    public void updateIngredientPanel() {
        listIngredients(ingredientPanel);
    }
    
    public void updateList() {
        this.myFridge();
    }
}

