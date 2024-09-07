package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import Controller.FridgeController;
import Controller.OkButtonActionListener;
import Controller.SelectionEventHandler;
import Controller.ShoppingListController;
import Model.ConvertIngredientToShoppingList;
import Model.IngredientAPI;
import Model.Recipe;
import Model.RecipeAPI;
import Model.RecipeManager;
import Model.RecipeService;
import Model.Selection;
import Model.ShoppingList;

public class SelectionDisposition {

	public static void detailPanelDisposition(JPanel detailPanel, Selection selection, Favorites favorite, int index) {

		// réinitialisation du panel
		detailPanel.setVisible(true);
		detailPanel.removeAll();
		detailPanel.repaint();
		detailPanel.revalidate();

		// Titre du panel des détails
		JPanel detailTitlePanel = new JPanel();
		detailTitlePanel.setBackground(new Color(250, 250, 100));
		JLabel detailTitleLabel = new JLabel("Selection details");
		ImageIcon icon = new ImageIcon("images\\icon_detail.png");
		Image img = icon.getImage();
		Image scaledImage = img.getScaledInstance(47, 43, Image.SCALE_SMOOTH);
		detailTitleLabel.setIcon(new ImageIcon(scaledImage));

		detailTitlePanel.add(detailTitleLabel);
		detailTitlePanel.add(detailTitleLabel);
		detailPanel.add(detailTitlePanel, BorderLayout.NORTH);

		// Panel principal des détails
		JPanel detailMainPanel = new JPanel(new BorderLayout());
		detailMainPanel.setBackground(new Color(250, 250, 100).brighter());
		detailPanel.add(detailMainPanel, BorderLayout.CENTER);

		// Panel contenant le titre de la sélection et le bouton add to shopping list
		JPanel selectionTitleAndShoppingListPanel = new JPanel(new BorderLayout());
		selectionTitleAndShoppingListPanel.setBackground(new Color(250, 250, 100).brighter());

		JLabel titleLabel = new JLabel(selection.getName_selection());
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		// titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
		selectionTitleAndShoppingListPanel.add(titleLabel, BorderLayout.CENTER);

		JButton shoppingListButton = new JButton("Add all ingredients to my shopping list");
		shoppingListButton.setBackground(new Color(250, 250, 100).darker());
		selectionTitleAndShoppingListPanel.add(shoppingListButton, BorderLayout.SOUTH);

		// Ajout d'un espace vide entre les deux panels
		JPanel northTitlePanel = new JPanel();
		northTitlePanel.setBackground(new Color(250, 250, 100).brighter());
		northTitlePanel.setBorder(new EmptyBorder(10, 0, 0, 0));
		selectionTitleAndShoppingListPanel.add(northTitlePanel, BorderLayout.NORTH);

		detailMainPanel.add(selectionTitleAndShoppingListPanel, BorderLayout.NORTH);

		JPanel aboutAndIngredientsPanel = new JPanel(new BorderLayout());
		aboutAndIngredientsPanel.setBackground(new Color(250, 250, 100).brighter());
		detailMainPanel.add(aboutAndIngredientsPanel, BorderLayout.CENTER);

		// Panel About
		JPanel aboutPanel = new JPanel(new BorderLayout());
		aboutPanel.setBackground(Color.GRAY.brighter());
		PanelSpaces.leftSpace(aboutPanel, 20, 250, 250, 100, true);
		PanelSpaces.rightSpace(aboutPanel, 20, 250, 250, 100, true);
		PanelSpaces.bottomSpace(aboutPanel, 10, 250, 250, 100, true);
		aboutAndIngredientsPanel.add(aboutPanel, BorderLayout.NORTH);

		// Titre du panel About
		JPanel aboutTitlePanel = new JPanel(new BorderLayout());
		PanelSpaces.leftSpace(aboutTitlePanel, 20, 250, 250, 100, true);
		PanelSpaces.rightSpace(aboutTitlePanel, 20, 250, 250, 100, true);
		JLabel aboutTitleLabel = new JLabel("About");
		aboutTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		aboutTitleLabel.setBackground(Color.LIGHT_GRAY);
		aboutTitlePanel.add(aboutTitleLabel, BorderLayout.CENTER);
		aboutPanel.add(aboutTitlePanel, BorderLayout.NORTH);

		// Ajout d'un espace vide entre les deux panels
		JPanel southTitlePanel = new JPanel();
		southTitlePanel.setBackground(new Color(250, 250, 100).brighter());
		southTitlePanel.setBorder(new EmptyBorder(10, 0, 0, 0));
		aboutTitlePanel.add(southTitlePanel, BorderLayout.NORTH);

		// Panel contenant les informations principales dans about
		JPanel aboutMainPanel = new JPanel(new BorderLayout());
		aboutMainPanel.setBackground(Color.GRAY.brighter());
		PanelSpaces.topSpace(aboutMainPanel, 10, Color.GRAY.brighter());
		aboutPanel.add(aboutMainPanel, BorderLayout.CENTER);

		// Description de la sélection
		JPanel descriptionPanel = new JPanel(new BorderLayout());
		descriptionPanel.setBackground(Color.GRAY.brighter());
		JPanel descriptionLabelPanel = new JPanel(new BorderLayout());
		descriptionLabelPanel.setBackground(Color.GRAY.brighter());
		PanelSpaces.leftSpace(descriptionLabelPanel, 5, Color.gray.brighter());
		JLabel descriptionLabel = new JLabel("Description : ");
		descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		descriptionLabelPanel.add(descriptionLabel, BorderLayout.CENTER);
		descriptionPanel.add(descriptionLabelPanel, BorderLayout.WEST);
		JPanel descriptionTextAreaPanel = new JPanel(new BorderLayout());
		descriptionTextAreaPanel.setBackground(Color.white);
		PanelSpaces.rightSpace(descriptionTextAreaPanel, 5, Color.GRAY.brighter());
		descriptionPanel.add(descriptionTextAreaPanel, BorderLayout.CENTER);

		// Label de la description
		JTextArea descriptionTextArea = new JTextArea(selection.getDescription_selection());
		descriptionTextArea.setLineWrap(true);
		descriptionTextArea.setWrapStyleWord(true);
		descriptionTextArea.setEditable(false);
		descriptionTextAreaPanel.add(descriptionTextArea, BorderLayout.CENTER);
		PanelSpaces.bottomSpace(descriptionPanel, 10, Color.GRAY.brighter());

		JPanel aboutInformationsPanel = new JPanel(new BorderLayout());
		aboutInformationsPanel.add(descriptionPanel, BorderLayout.NORTH);

		// Nombre de recettes de la sélection
		JPanel recipesNumberPanel = new JPanel(new BorderLayout());
		recipesNumberPanel.setBackground(Color.GRAY.brighter());
		JPanel recipesNumberLabelPanel = new JPanel(new BorderLayout());
		recipesNumberLabelPanel.setBackground(Color.GRAY.brighter());
		PanelSpaces.leftSpace(recipesNumberLabelPanel, 5, Color.gray.brighter());
		JLabel recipesNumberLabel = new JLabel("Number of recipes : ");
		recipesNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
		recipesNumberLabelPanel.add(recipesNumberLabel, BorderLayout.CENTER);
		recipesNumberPanel.add(recipesNumberLabelPanel, BorderLayout.WEST);
		JPanel recipesNumberTextAreaPanel = new JPanel(new BorderLayout());
		recipesNumberTextAreaPanel.setBackground(Color.white);
		PanelSpaces.rightSpace(recipesNumberTextAreaPanel, 5, Color.GRAY.brighter());
		recipesNumberPanel.add(recipesNumberTextAreaPanel, BorderLayout.CENTER);

		RecipeService recipeService = new RecipeService();
		List<Recipe> recipes = recipeService.select(selection.getName_selection());
		
		shoppingListButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<IngredientAPI> fridgeIngredients = FridgeController.selectIngredients(); // Ingrédients dans le frigo
				List<IngredientAPI> shoppingList = new ArrayList<IngredientAPI>();
				for (int i=0; i<recipes.size(); i++) {
					for (int j=0; j<extractIngredients(recipes.get(i).getIngredients()).size(); j++) {
						// Ingrédients nécéssaire pour la recette
						shoppingList.add(extractIngredients(recipes.get(i).getIngredients()).get(j));
					}
				}
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

		// Label de la description
		JTextArea recipesNumberTextArea = new JTextArea(String.valueOf(recipes.size()));
		recipesNumberTextAreaPanel.add(recipesNumberTextArea, BorderLayout.CENTER);
		recipesNumberTextArea.setEditable(false);
		PanelSpaces.bottomSpace(recipesNumberPanel, 10, Color.GRAY.brighter());

		aboutInformationsPanel.add(recipesNumberPanel, BorderLayout.SOUTH);
		aboutMainPanel.add(aboutInformationsPanel, BorderLayout.CENTER);

		// Panel des recettes et des ingrédients
		JPanel ingredientsPanel = new JPanel(new BorderLayout());
		ingredientsPanel.setBackground(Color.gray.brighter());
		PanelSpaces.leftSpace(aboutAndIngredientsPanel, 10, 250, 250, 100, true);
		PanelSpaces.rightSpace(aboutAndIngredientsPanel, 10, 250, 250, 100, true);
		aboutAndIngredientsPanel.add(ingredientsPanel, BorderLayout.CENTER);

		JScrollPane ingredientsJScrollPane = new JScrollPane(detailMainPanel);
		ingredientsJScrollPane.setBorder(BorderFactory.createEmptyBorder());
		ingredientsJScrollPane.setBackground(Color.gray.brighter());
		detailPanel.add(ingredientsJScrollPane);

		// Titre du panel des recettes et des ingrédients
		JPanel ingredientsTitlePanel = new JPanel(new BorderLayout());
		JLabel ingredientsTitleLabel = new JLabel("List of recipes");
		ingredientsTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ingredientsTitleLabel.setBackground(Color.LIGHT_GRAY);
		ingredientsTitlePanel.add(ingredientsTitleLabel, BorderLayout.CENTER);
		ingredientsPanel.add(ingredientsTitlePanel, BorderLayout.NORTH);

		// Panel contenant toutes les recettes
		JPanel mainIngredientsPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		mainIngredientsPanel.setBackground(Color.gray.brighter());
		ingredientsPanel.add(mainIngredientsPanel, BorderLayout.CENTER);
		PanelSpaces.bottomSpace(ingredientsPanel, 5, 250, 250, 100, true);

		for (int i = 0; i < recipes.size(); i++) {
			int k = i;
			// Panel pour une recette (contenant toutes ses informations)
			JPanel recipePanel = new JPanel(new BorderLayout());
			recipePanel.setBackground(Color.gray.brighter());

			// Titre du panel d'une recette
			JPanel recipeTitlePanel = new JPanel(new BorderLayout());
			recipeTitlePanel.setPreferredSize(new Dimension(375, 50));
			recipeTitlePanel.setBackground(Color.LIGHT_GRAY);

			// Bouton pour supprimer la recette
			JButton deleteRecipeButton = new JButton();
			deleteRecipeButton.setBackground(Color.RED);
			icon = new ImageIcon("images\\icon_trash.png");
			img = icon.getImage();
			scaledImage = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			deleteRecipeButton.setIcon(new ImageIcon(scaledImage));
			recipeTitlePanel.add(deleteRecipeButton, BorderLayout.WEST);
			// TODO 14
			deleteRecipeButton.setActionCommand("deleteRecipeFromSelection");
			deleteRecipeButton
					.addActionListener(new SelectionEventHandler(favorite, recipes.get(i).getRecipe_id(), index));
			

			// Titre de la recette
			JTextArea recipeTitleTextArea = new JTextArea(recipes.get(i).getTitle());
			recipeTitleTextArea.setBackground(Color.LIGHT_GRAY);
	        recipeTitleTextArea.setEditable(false);
	        recipeTitleTextArea.setLineWrap(true);
	        recipeTitleTextArea.setWrapStyleWord(true);

	        // Ajouter la JTextArea au centre du JPanel
	        recipeTitlePanel.add(recipeTitleTextArea, BorderLayout.CENTER);

			// Bouton pour ajouter les ingrédients sélectionnés à la shopping list
			JButton searchRecipeButton = new JButton("Search recipe details");
			searchRecipeButton.setBackground(Color.gray);
			searchRecipeButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					favorite.getDetailPanel().setVisible(false);

					ArrayList<Object> formResult = new ArrayList<Object>();
					// Enregistrement des données dans resultForm
					formResult.add(0, recipes.get(k).getTitle());
					formResult.add(1, "");
					formResult.add(2, "10000");
					formResult.add(3, "1");
					formResult.add(4, "");
					formResult.add(5, "");
					formResult.add(6, "");
					formResult.add(7, "10000");
					String chosenIngredients = "";
					String chosenIntolerances = "";
					formResult.add(8, "");
					formResult.add(9, "");

					// Requête API
					List<RecipeAPI> recipesComplexSearch = new ArrayList<RecipeAPI>();
					OkButtonActionListener okButtonController = new OkButtonActionListener();
					recipesComplexSearch = okButtonController.actionPerformed(formResult);

					ShowSelectionRecipePanel showRecipe= new ShowSelectionRecipePanel(detailPanel);
					showRecipe.recipeDetailPanelDisposition(recipesComplexSearch.get(0));
					detailPanel.setVisible(true);
				}
			});
			recipeTitlePanel.add(searchRecipeButton, BorderLayout.SOUTH);

			// Ajout de la bordure sud du panel de titre de la recette
			Color borderColor = Color.BLACK;
			int borderWidth = 1;
			MatteBorder bottomBorder = new MatteBorder(0, 0, borderWidth, 0, borderColor);
			recipeTitlePanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(0, 0, 0, 0), bottomBorder));

			// Ajout du panel de titre au nord du panel de la recette
			recipePanel.add(recipeTitlePanel, BorderLayout.NORTH);

			// Extraire les ingrédients nécessaire à chaque recette
			List<IngredientAPI> ingredients = extractIngredients(recipes.get(i).getIngredients());

			JPanel recipeIngredientsPanel = new JPanel(new GridBagLayout());
			GridBagConstraints gbc2 = new GridBagConstraints();
			gbc2.gridx = 0;
			gbc2.gridy = 0;
			recipeIngredientsPanel.setBackground(Color.gray.brighter());
			//recipeIngredientsPanel.add(Box.createVerticalStrut(50));

			for (int j = 0; j < ingredients.size(); j++) {
				// Panel pour un ingrédient
				JPanel oneIngredientPanel = new JPanel(new BorderLayout());
				oneIngredientPanel.setBackground(Color.gray.brighter());

				// Nom de l'ingrédient
				JTextArea oneIngredientTextArea = new JTextArea("- " + ingredients.get(j).getName());
				oneIngredientTextArea.setBackground(Color.gray.brighter());
				oneIngredientTextArea.setEditable(false); //
				oneIngredientTextArea.setLineWrap(true);
				oneIngredientTextArea.setWrapStyleWord(true);

				oneIngredientPanel.add(oneIngredientTextArea, BorderLayout.CENTER);

				// Ajout du panel de l'ingrédient au panel des ingrédients
				recipeIngredientsPanel.add(oneIngredientPanel, gbc2);
				gbc2.gridy+=1;

				// Ajout d'un espace vertical entre les panneaux d'ingrédients
				if (j < ingredients.size() - 1) {
					recipeIngredientsPanel.add(Box.createVerticalStrut(5)); // Espace entre les ingrédients
				} else {
					// Ajout d'un espace plus grand après le dernier ingrédient
					recipeIngredientsPanel.add(Box.createVerticalStrut(20));
				}
			}
			recipeIngredientsPanel.add(Box.createVerticalStrut(20));

			// Ajout du panel des ingrédients au panel des recettes
			recipePanel.add(recipeIngredientsPanel, BorderLayout.CENTER);

			// Ajout du panel de la recette au panel principal
			mainIngredientsPanel.add(recipePanel, gbc);
			
			gbc.gridy+=1;
		}

	}

	private static List<IngredientAPI> extractIngredients(String inputString) {
        List<IngredientAPI> ingredients = new ArrayList<>();

        String regex = "(\\d*\\.?\\d+)\\s+(\\w+)\\s+([^,]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inputString);

        while (matcher.find()) {
            double quantity = Double.parseDouble(matcher.group(1));
            String unit = matcher.group(2);
            String name = matcher.group(3);

            ingredients.add(new IngredientAPI(0, name, "", quantity, unit));
        }

        return ingredients;
    }

	

}
