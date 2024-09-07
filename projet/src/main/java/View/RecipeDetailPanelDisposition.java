package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import Controller.FridgeController;
import Controller.SelectionController;
import Controller.SelectionEventHandler;
import Controller.ShoppingListEventHandler;
import Controller.ShoppingListController;
import Model.ConvertIngredientToShoppingList;
import Model.IngredientAPI;
import Model.RecipeAPI;
import Model.RecipeManager;
import Model.ShoppingList;

public class RecipeDetailPanelDisposition {

	JPanel formPanel;
	JPanel recipeDetailPanel;

	public RecipeDetailPanelDisposition(JPanel detailedRecipePanel_) {
		this.recipeDetailPanel = detailedRecipePanel_;
		this.recipeDetailPanel.setLayout(new BorderLayout());
	}

	public void recipeDetailPanelDisposition(RecipeAPI recipe) {

		// Réinitialisation du Panel
		recipeDetailPanel.removeAll();
		recipeDetailPanel.repaint();
		recipeDetailPanel.revalidate();

		// Titre du panel
		JPanel titlePanel = new JPanel(new BorderLayout());
		titlePanel.setBackground(Color.decode("#C89F9C"));
		
		JLabel titleLabel = new JLabel("Result recipe details");
		ImageIcon icon = new ImageIcon("images\\icon_detail.png");
		Image img = icon.getImage();
		Image scaledImage = img.getScaledInstance(47, 43, Image.SCALE_SMOOTH);
		titleLabel.setIcon(new ImageIcon(scaledImage));
		
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		Font labelFont = titleLabel.getFont();
		titleLabel.setFont(new Font(labelFont.getName(), Font.PLAIN, 22));
		titlePanel.add(titleLabel, BorderLayout.CENTER);
		recipeDetailPanel.add(titlePanel, BorderLayout.NORTH);

		// Panel principal
		JPanel recipePanel = new JPanel(new BorderLayout());
		recipePanel.setBackground(Color.decode("#EEE2DF"));
		recipeDetailPanel.add(recipePanel, BorderLayout.CENTER);

		// Couche contenant le nom de la recette et les boutons pour ajouter la recette
		// aux favoris et ajouter des ingrédients à la liste de course
		JPanel recipeTitlePanel = new JPanel(new BorderLayout());
		recipeTitlePanel.setBackground(Color.decode("#EEE2DF"));
		recipePanel.add(recipeTitlePanel, BorderLayout.NORTH);
		JPanel namePanel = new JPanel(new BorderLayout());
		namePanel.setBackground(Color.decode("#EEE2DF"));
		JPanel addToSomethingElsePanel = new JPanel(new BorderLayout());
		addToSomethingElsePanel.setBackground(Color.decode("#EEE2DF"));
		recipeTitlePanel.add(namePanel, BorderLayout.CENTER);
		recipeTitlePanel.add(addToSomethingElsePanel, BorderLayout.EAST);

		// Nom de la recette
		JLabel nameLabel = new JLabel(recipe.getTitle());
		nameLabel.setHorizontalAlignment(JLabel.CENTER);
		nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
		namePanel.add(nameLabel, BorderLayout.CENTER);

		// Disposition du panel addToSomethingLabel
		JPanel favoritePanel = new JPanel();
		favoritePanel.setBackground(Color.decode("#EEE2DF"));
		JPanel shoppingListPanel = new JPanel();
		shoppingListPanel.setBackground(Color.decode("#EEE2DF"));
		addToSomethingElsePanel.add(favoritePanel, BorderLayout.NORTH);
		addToSomethingElsePanel.add(shoppingListPanel, BorderLayout.SOUTH);

		// Ajout dans une liste de recette
		// Label
		JLabel favoriteLabel = new JLabel("Add recipe to my selection : ");
		favoritePanel.add(favoriteLabel, BorderLayout.WEST);

		// Combobox
		SelectionController selectionController = new SelectionController();
		List<String> selectionList = selectionController.select().stream().map(e->e.getName_selection()).collect(Collectors.toList());
		JComboBox recipeListComboBox = new JComboBox(selectionList.toArray());
		favoritePanel.add(recipeListComboBox, BorderLayout.CENTER);

		// Panel pour les boutons
		JPanel buttonsPanel = new JPanel(new BorderLayout());
		buttonsPanel.setBackground(Color.decode("#EEE2DF"));
		favoritePanel.add(buttonsPanel, BorderLayout.EAST);



//		// Bouton ajouter
		JButton addToAListButton = new JButton("Ok");
		buttonsPanel.add(addToAListButton, BorderLayout.WEST);
		// TODO 11
		addToAListButton.setActionCommand("addRecipeToSelection");
		addToAListButton.addActionListener(new SelectionEventHandler(recipe,recipeListComboBox));

		// Bouton nouvelle selection
		JButton newSelectionButton = new JButton("+");
		buttonsPanel.add(newSelectionButton, BorderLayout.EAST);
		newSelectionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NewSelectionForm form = new NewSelectionForm();
				form.newSelectionForm(formPanel,RecipeDetailPanelDisposition.this,recipe);
			}
		});

		// Ajout d'ingrédients dans la liste de course
		// Label
		JLabel missingIngredientLabel = new JLabel(" ingredients are missing : ");
		shoppingListPanel.add(missingIngredientLabel, BorderLayout.WEST);

		// Bouton ajouter
		JButton addToShoppingListButton = new JButton("Add to my shopping list");
		shoppingListPanel.add(addToShoppingListButton, BorderLayout.EAST);

		addToShoppingListButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<IngredientAPI> fridgeIngredients = FridgeController.selectIngredients(); // Ingrédients dans le frigo
				List<RecipeAPI> recipes = new ArrayList<>();
				recipes.add(recipe);
				RecipeManager recipeManager = new RecipeManager(recipes, fridgeIngredients);
				List<IngredientAPI> shoppingList = recipeManager.generateShoppingList(); // Ingrédients nécéssaire pour la recette
				List<IngredientAPI> newShoppingList = new ArrayList<IngredientAPI>();

				for (IngredientAPI ingredient : shoppingList) {	
					newShoppingList.add(RecipeManager.convertToMetric(ingredient.getUnit(), ingredient.getAmount(), ingredient.getName()));
				}

				List<ShoppingList> ingredientInShoppingList = ShoppingListController.listShoppingList(); // Ingrédients dans la shopping list
				List<IngredientAPI> ingredientConverted = ConvertIngredientToShoppingList.convertToIngredientAPI(ingredientInShoppingList);
				for (int i=0; i<ingredientConverted.size(); i++) {
					newShoppingList.add(ingredientConverted.get(i));
				}
				ShoppingListController.resetShoppingList();
				newShoppingList = RecipeManager.aggregateIngredientsFromIngredientList(newShoppingList);
				for (IngredientAPI ingredient : newShoppingList) {	
					ShoppingListController.insertIngredientsInShoppingList(ingredient.getName(),String.valueOf(ingredient.getAmount()),ingredient.getUnit());
			    }
			}
		});
		
		
		
		// Panel intermédiaire
		JPanel middlePanel = new JPanel(new BorderLayout());
		middlePanel.setBackground(Color.decode("#EEE2DF"));
		recipePanel.add(middlePanel, BorderLayout.CENTER);

		// Panel des informations principales
		LineBorder lineBorder = new LineBorder(Color.BLACK, 2);
		JPanel recipeMainPanel = new JPanel(new BorderLayout());
		recipeMainPanel.setBackground(Color.white);
		middlePanel.add(recipeMainPanel, BorderLayout.NORTH);

		// Ajout d'un espace vide entre les deux panels
		JPanel spacePanel = new JPanel();
		spacePanel.setBackground(Color.decode("#EEE2DF"));
		spacePanel.setBorder(new EmptyBorder(0, 0, 10, 0));
		recipeMainPanel.add(spacePanel, BorderLayout.SOUTH);

		// Séparation de recipeMainPanel en deux panels
		JPanel preparationAndIngredientsPanel = new JPanel(new BorderLayout());
		JPanel recipePicturePanel = new JPanel(new BorderLayout());
		preparationAndIngredientsPanel.setBorder(lineBorder);
		recipePicturePanel.setBorder(lineBorder);
		preparationAndIngredientsPanel.setBackground(Color.white);
		recipePicturePanel.setBackground(Color.white);
		recipeMainPanel.add(preparationAndIngredientsPanel, BorderLayout.CENTER);
		recipeMainPanel.add(recipePicturePanel, BorderLayout.EAST);

		// disposition de recipePicturePanel
		URL url;
		try {
			url = new URL(recipe.getImageUrl());
			BufferedImage recipePicture = ImageIO.read(url);
			ImageIcon recipePictureIcon = new ImageIcon(recipePicture);
			JLabel recipePictureLabel = new JLabel(recipePictureIcon);
			recipePicturePanel.add(recipePictureLabel, BorderLayout.CENTER);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		// Ajout du panel du temps de preparation
		JPanel preparationPanel = new JPanel(new BorderLayout());
		preparationPanel.setBackground(Color.white);
		preparationAndIngredientsPanel.add(preparationPanel, BorderLayout.NORTH);

		// Titre du panel de préparation
		JPanel preparationTitlePanel = new JPanel(new BorderLayout());
		preparationTitlePanel.setBackground(new Color(220, 220, 220));
		JLabel preparationTitleLabel = new JLabel("Preparation & informations");
		preparationTitleLabel.setHorizontalAlignment(JLabel.CENTER);
		preparationTitlePanel.add(preparationTitleLabel, BorderLayout.CENTER);
		preparationPanel.add(preparationTitlePanel, BorderLayout.NORTH);

		JPanel preparationMiddlePanel = new JPanel(new BorderLayout());
		preparationMiddlePanel.setBackground(Color.white);
		preparationPanel.add(preparationMiddlePanel, BorderLayout.CENTER);

		// Informations du Panel de préparation
		JLabel preparationTimeLabel = new JLabel("Ready in " + recipe.getPreparationTime() + " minutes");
		preparationTimeLabel.setHorizontalAlignment(JLabel.CENTER);
		preparationMiddlePanel.add(preparationTimeLabel, BorderLayout.NORTH);

		// Mettre la liste des intolérances et tout
		JPanel infoPanel = new JPanel();
		infoPanel.setBackground(Color.white);
		preparationMiddlePanel.add(infoPanel, BorderLayout.CENTER);
		JTextArea infoTextArea = new JTextArea();
		infoTextArea.setEditable(false);
		ArrayList<String> infoTmp = new ArrayList<String>();
		for (int i = 0; i < recipe.getSteps().size(); i++) {
			for (int j = 0; j < recipe.getSteps().get(i).getEquipment().size(); j++) {
				boolean alreadyThere = false;
				for (int k = 0; k < infoTmp.size(); k++) {
					if (recipe.getSteps().get(i).getEquipment().get(j).toString().equals(infoTmp.get(k))) {
						alreadyThere = true;
					}
				}
				if (alreadyThere == true) {
					infoTmp.add(recipe.getSteps().get(i).getEquipment().get(j).toString());
				}
			}
		}
		for (int i = 0; i < infoTmp.size(); i++) {
			infoTextArea.append(infoTmp.get(i));
			if (i < infoTmp.size() - 1) {
				infoTextArea.append(", ");
			}
		}
		infoPanel.add(infoTextArea);

		// Ajout du panel des ingrédients
		JPanel ingredientsPanel = new JPanel(new BorderLayout());
		ingredientsPanel.setBackground(Color.white);
		Border topBorder = BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK);
		ingredientsPanel.setBorder(topBorder);
		preparationAndIngredientsPanel.add(ingredientsPanel, BorderLayout.SOUTH);

		// Titre du panel des ingrédients
		JPanel ingredientsTitlePanel = new JPanel(new BorderLayout());
		ingredientsTitlePanel.setBackground(new Color(230, 230, 230));
		JLabel ingredientsTitleLabel = new JLabel("Ingredients");
		ingredientsTitleLabel.setHorizontalAlignment(JLabel.CENTER);
		ingredientsTitlePanel.add(ingredientsTitleLabel, BorderLayout.CENTER);
		ingredientsPanel.add(ingredientsTitlePanel, BorderLayout.NORTH);

		// Ajout d'un Panel pour la liste des ingrédients
		JPanel ingredientsListPanel = new JPanel(new GridLayout(0, 2));
		ingredientsListPanel.setBackground(Color.WHITE);
		ingredientsPanel.add(ingredientsListPanel, BorderLayout.CENTER);

		// Ajout des ingrédients
		for (int i = 0; i < recipe.getIngredients().size(); i++) {
			JLabel ingredientLabel = new JLabel(recipe.getIngredients().get(i).getName().toString());
			ingredientLabel.setHorizontalAlignment(JLabel.CENTER);
			ingredientsListPanel.add(ingredientLabel);

		}

		// Ajout du panel des étapes
		JPanel allStepsPanel = new JPanel(new BorderLayout());
		allStepsPanel.setBackground(Color.white);
		middlePanel.add(allStepsPanel, BorderLayout.CENTER);

		// Ajout d'un espace vide à gauche du panel
		JPanel leftSpacePanel = new JPanel();
		leftSpacePanel.setBackground(Color.decode("#EEE2DF"));
		leftSpacePanel.setBorder(new EmptyBorder(0, 10, 0, 0));
		allStepsPanel.add(leftSpacePanel, BorderLayout.WEST);

		// Ajout d'un espace vide à gauche du panel
		JPanel rightSpacePanel = new JPanel();
		rightSpacePanel.setBackground(Color.decode("#EEE2DF"));
		rightSpacePanel.setBorder(new EmptyBorder(0, 0, 0, 10));
		allStepsPanel.add(rightSpacePanel, BorderLayout.EAST);

		// Titre du Panel des instructions
		JPanel stepsTitlePanel = new JPanel(new BorderLayout());
		stepsTitlePanel.setBackground(new Color(220, 220, 220));
		JLabel stepsTitleLabel = new JLabel("Instructions");
		stepsTitleLabel.setHorizontalAlignment(JLabel.CENTER);
		stepsTitlePanel.add(stepsTitleLabel, BorderLayout.CENTER);
		allStepsPanel.add(stepsTitlePanel, BorderLayout.NORTH);

		// Ajout d'un espace vide à gauche du panel
		JPanel leftTitleSpacePanel = new JPanel();
		leftTitleSpacePanel.setBackground(Color.decode("#EEE2DF"));
		leftTitleSpacePanel.setBorder(new EmptyBorder(0, 10, 0, 0));
		stepsTitlePanel.add(leftTitleSpacePanel, BorderLayout.WEST);

		// Ajout d'un espace vide à gauche du panel
		JPanel rightTitleSpacePanel = new JPanel();
		rightTitleSpacePanel.setBackground(Color.decode("#EEE2DF"));
		rightTitleSpacePanel.setBorder(new EmptyBorder(0, 0, 0, 10));
		stepsTitlePanel.add(rightTitleSpacePanel, BorderLayout.EAST);

		// Panel contenant les informations + les étapes
		JPanel realStepsPanel = new JPanel(new GridLayout(0, 1));
		realStepsPanel.setBackground(Color.white);
		allStepsPanel.add(realStepsPanel, BorderLayout.CENTER);
		for (int i = 0; i < recipe.getSteps().size(); i++) {

			JTextArea textArea = new JTextArea();
			textArea.setEditable(false);
			textArea.setLineWrap(true);
			textArea.setWrapStyleWord(true);
			String textWithoutHtml = recipe.getSteps().get(i).getStep().toString().replaceAll("\\<.*?\\>", "");
			if (!textWithoutHtml.equals("")) {
				textArea.setText(recipe.getSteps().get(i).getNumber() + ": " + textWithoutHtml);
			}
			realStepsPanel.add(textArea);
			if (recipe.getSteps().get(i).getEquipment().size() > 0) {
				textArea.append("\nEquipment : " + recipe.getSteps().get(i).getEquipment().get(0));
				for (int j = 1; j < recipe.getSteps().get(i).getEquipment().size(); j++) {
					textArea.append(", " + recipe.getSteps().get(i).getEquipment().get(j));
				}
			}
		}

		JScrollPane recipeScrollPane = new JScrollPane(realStepsPanel);
		recipeScrollPane.setBackground(Color.WHITE);
		allStepsPanel.add(recipeScrollPane);

	}

}
