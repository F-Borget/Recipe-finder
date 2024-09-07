package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import Controller.FridgeController;
import Controller.OkButtonActionListener;
import Model.IngredientAPI;
import Model.RecipeAPI;

public class DetailedFormPanelDisposition {

	protected void detailedFormPanelDisposition(JPanel detailedFormPanel, JPanel resultsPanel,
			ResultsPanelDisposition resultPanelClass, JPanel detailedRecipePanel) {

		Font font = new Font("Arial", Font.PLAIN, 16);

		// Liste temporaire des ingrédients
		List<IngredientAPI> ingredients = FridgeController.selectIngredients();

		ArrayList<Boolean> selectionnedIngredients = new ArrayList<Boolean>();

		for (int i = 0; i < ingredients.size(); i++) {
			selectionnedIngredients.add(false);
		}

		// Liste temporaire des intolérances
		List<String> intolerances = new ArrayList<>(Arrays.asList("Dairy", "Egg", "Gluten", "Grain", "Peanut",
				"Seafood", "Sesame", "Shellfish", "Soy", "Sulfite", "Tree nut", "Wheat"));

		ArrayList<Boolean> selectionnedIntolerances = new ArrayList<Boolean>();

		for (int i = 0; i < intolerances.size(); i++) {
			selectionnedIntolerances.add(false);
		}

		ArrayList<Object> formResult = new ArrayList<Object>();

		// Espace où se trouve le titre du panel
		JPanel title = new JPanel();
		title.setBounds(0, 0, 850, 50);
		title.setBackground(Color.decode("#7DC2A5"));
		detailedFormPanel.add(title);

		// Titre du panel
		JLabel titleLabel = new JLabel("Search Page Detail");
		ImageIcon icon = new ImageIcon("images\\icon_detail.png");
		Image img = icon.getImage();
		Image scaledImage = img.getScaledInstance(47, 43, Image.SCALE_SMOOTH);
		titleLabel.setIcon(new ImageIcon(scaledImage));
		
		Font titleLabelFont = new Font("Arial", Font.PLAIN, 22);
		titleLabel.setBounds(300, 10, 850, 35);
		titleLabel.setFont(titleLabelFont);
		title.add(titleLabel);

		title.setLayout(null);

		// Bouton de validation du formulaire
		JButton searchButton = new JButton("OK");
		searchButton.setBounds(740, 10, 100, 30);
		searchButton.setBackground(new Color(255, 255, 255));

		title.add(searchButton);

		// Recherche par nom de la recette
		JLabel recipeNameLabel = new JLabel("Recipe name :");
		recipeNameLabel.setBounds(50, 70, 850, 30);
		recipeNameLabel.setFont(font);
		detailedFormPanel.add(recipeNameLabel);
		JTextField recipeNameTextField = new JTextField("");
		recipeNameTextField.setName("recipeNameLabel");
		recipeNameTextField.setForeground(Color.BLACK);
		recipeNameTextField.setBounds(155, 70, 150, 30);
		detailedFormPanel.add(recipeNameTextField);

		// Recherche par type de cuisine
		JLabel cuisineTypeLabel = new JLabel("Cuisine type :");
		cuisineTypeLabel.setBounds(450, 150, 850, 30);
		cuisineTypeLabel.setFont(font);
		detailedFormPanel.add(cuisineTypeLabel);
		String[] cuisineTypes = { "All", "African", "Asian", "American", "British", "Cajun", "Caribbean", "Chinese",
				"Eastern European", "European", "French", "German", "Greek", "Indian", "Irish", "Italian", "Japanese",
				"Jewish", "Korean", "Latin American", "Mediterranean", "Mexican", "Middle Eastern", "Nordic",
				"Southern", "Spanish", "Thai", "Vietnamese" };
		JComboBox cuisineTypeComboBox = new JComboBox(cuisineTypes);
		cuisineTypeComboBox.setName("cuisineTypeComboBox");
		cuisineTypeComboBox.setBounds(555, 150, 150, 30);
		detailedFormPanel.add(cuisineTypeComboBox);

		// Recherche par temps de préparation
		JLabel preparationTimeLabel = new JLabel("Preparation time (in minutes) :");
		preparationTimeLabel.setBounds(50, 230, 850, 30);
		preparationTimeLabel.setFont(font);
		detailedFormPanel.add(preparationTimeLabel);
		JTextField preparationTimeTextField = new JTextField("");
		preparationTimeTextField.setName("preparationTimeTextField");
		preparationTimeTextField.setForeground(Color.BLACK);
		preparationTimeTextField.setBounds(270, 230, 35, 30);
		detailedFormPanel.add(preparationTimeTextField);

		// Nombre de résultats attendus
		JLabel expectedResultsLabel = new JLabel("Number of expected results :");
		expectedResultsLabel.setBounds(50, 310, 850, 30);
		expectedResultsLabel.setFont(font);
		detailedFormPanel.add(expectedResultsLabel);
		JTextField expectedResultsTextField = new JTextField("10");
		expectedResultsTextField.setName("expectedResultsTextField");
		expectedResultsTextField.setForeground(Color.BLACK);
		expectedResultsTextField.setBounds(265, 310, 40, 30);
		detailedFormPanel.add(expectedResultsTextField);

		// Recherche par type de plat
		JLabel mealTypeLabel = new JLabel("Meal type :");
		mealTypeLabel.setBounds(450, 70, 850, 30);
		mealTypeLabel.setFont(font);
		detailedFormPanel.add(mealTypeLabel);
		String[] mealTypes = { "All", "Main course", "Side dish", "Dessert", "Appetizer", "Salad", "Bread", "Breakfast",
				"Soup", "Beverage", "Sauce", "Marinade", "Fingerfood", "Snack", "Drink" };
		JComboBox mealTypeComboBox = new JComboBox(mealTypes);
		mealTypeComboBox.setName("mealTypeComboBox");
		mealTypeComboBox.setBounds(535, 70, 180, 30);
		detailedFormPanel.add(mealTypeComboBox);

		// Recherche par équipements nécéssaires
		JLabel equipmentLabel = new JLabel("Equipment :");
		equipmentLabel.setBounds(50, 150, 850, 30);
		equipmentLabel.setFont(font);
		detailedFormPanel.add(equipmentLabel);
		JTextField equipmentTextField = new JTextField("");
		equipmentTextField.setName("equipmentTextField");
		equipmentTextField.setForeground(Color.BLACK);
		equipmentTextField.setBounds(140, 150, 175, 30);
		detailedFormPanel.add(equipmentTextField);

		// Recherche par régime alimentaire
		JLabel dietLabel = new JLabel("Diet type :");
		dietLabel.setBounds(450, 230, 850, 30);
		dietLabel.setFont(font);
		detailedFormPanel.add(dietLabel);
		String[] dietTypes = { "None", "Gluten free", "Ketogenic", "Vegetarian", "Lacto-vegetarian", "Ovo-vegetarian",
				"Vegan", "Pescetarian", "Paleo", "Primal", "Low FODMAP", "Whole30" };
		JComboBox dietComboBox = new JComboBox(dietTypes);
		dietComboBox.setName("dietComboBox");
		dietComboBox.setBounds(535, 230, 180, 30);
		detailedFormPanel.add(dietComboBox);

		// Recherche par calories maximum
		JLabel maximumCaloriesLabel = new JLabel("Maximum calories (per serving) :");
		maximumCaloriesLabel.setBounds(450, 310, 850, 30);
		maximumCaloriesLabel.setFont(font);
		detailedFormPanel.add(maximumCaloriesLabel);
		JTextField maximumCaloriesField = new JTextField("");
		maximumCaloriesField.setName("maximumCaloriesField");
		maximumCaloriesField.setForeground(Color.BLACK);
		maximumCaloriesField.setBounds(685, 310, 35, 30);
		detailedFormPanel.add(maximumCaloriesField);

		// Création du panel contenant les ingrédients du frigo
		JPanel fridgePanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		JLabel ingredientsLabel = new JLabel("Ingredients :");
		ingredientsLabel.setBounds(50, 390, 850, 30);
		ingredientsLabel.setFont(font);
		detailedFormPanel.add(ingredientsLabel);
		JButton allButton = new JButton("All");
		allButton.setBounds(150, 395, 50, 20);
		allButton.setBackground(Color.WHITE);

		detailedFormPanel.add(allButton);
		fridgePanel.setBounds(50, 430, 350, 170);
		fridgePanel.setBackground(Color.WHITE);
		detailedFormPanel.add(fridgePanel);

		// Ajout d'un bouton de tri
		JPanel sortPanel = new JPanel();
		sortPanel.setLayout(null);
		sortPanel.setBounds(210, 390, 190, 30);
		sortPanel.setBackground(Color.decode("#C9E3CC"));
		detailedFormPanel.add(sortPanel);
		String[] sortString = { "a-z", "date" };
		JComboBox sortComboBox = new JComboBox(sortString);
		sortComboBox.setBounds(5, 5, 85, 20);
		sortPanel.add(sortComboBox);
		JButton sortButton = new JButton("Sort");
		sortButton.setBounds(95, 5, 85, 20);

		// ajout d'un actionlistener à sortButton
		sortButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fridgePanel.removeAll();
				fridgePanel.repaint();
				fridgePanel.revalidate();
				if (sortComboBox.getSelectedItem().equals("a-z")) {
					Collections.sort(ingredients, Comparator.comparing(IngredientAPI::getName));
				} else {
					Collections.sort(ingredients, Comparator.comparing(IngredientAPI::getDate));
				}
				for (int i = 0; i < ingredients.size(); i++) {
					selectionnedIngredients.add(false);
				}
				fillFridge(fridgePanel, allButton, ingredients, selectionnedIngredients, gbc);

			}
		});
		sortPanel.add(sortButton);

		// Ajout d'une scrollpane au panel du frigo
		JScrollPane fridgeScrollPane = new JScrollPane(fridgePanel);
		fridgeScrollPane.setBackground(Color.WHITE);
		fridgeScrollPane.setBounds(50, 430, 350, 170);
		detailedFormPanel.add(fridgeScrollPane);

		// Remplissage du frigo
		fillFridge(fridgePanel, allButton, ingredients, selectionnedIngredients, gbc);

		// Création du panel contenant les intolérances
		JLabel intolerancesLabel = new JLabel("Intolerances :");
		intolerancesLabel.setBounds(450, 390, 850, 30);
		intolerancesLabel.setFont(font);
		detailedFormPanel.add(intolerancesLabel);
		JPanel intolerancesPanel = new JPanel(new GridLayout(0, 1));
		intolerancesPanel.setBounds(450, 430, 350, 170);
		intolerancesPanel.setBackground(Color.WHITE);
		detailedFormPanel.add(intolerancesPanel);
		JButton allIntolerancesButton = new JButton("All");
		allIntolerancesButton.setBounds(550, 395, 50, 20);
		allIntolerancesButton.setBackground(Color.WHITE);
		detailedFormPanel.add(allIntolerancesButton);

		// Ajout d'une scrollpane au panel du frigo
		JScrollPane intolerancesScrollPane = new JScrollPane(intolerancesPanel);
		intolerancesScrollPane.setBackground(Color.WHITE);
		intolerancesScrollPane.setBounds(450, 430, 350, 170);
		detailedFormPanel.add(intolerancesScrollPane);

		// Remplissage du panel des intolerances
		fillFridge(intolerancesPanel, allIntolerancesButton, intolerances, selectionnedIntolerances, gbc);

		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// Enregistrement des données dans resultForm
				formResult.add(0, recipeNameTextField.getText());
				formResult.add(1, equipmentTextField.getText());
				formResult.add(2, preparationTimeTextField.getText());
				formResult.add(3, expectedResultsTextField.getText());
				formResult.add(4, mealTypeComboBox.getSelectedItem());
				formResult.add(5, cuisineTypeComboBox.getSelectedItem());
				formResult.add(6, dietComboBox.getSelectedItem());
				formResult.add(7, maximumCaloriesField.getText());
				String chosenIngredients = "";
				String chosenIntolerances = "";
				for (int i = 0; i < ingredients.size(); i++) {
					if (selectionnedIngredients.get(i).equals(true)) {
						if (!chosenIngredients.equals("")) {
							chosenIngredients += ",";
						}
						chosenIngredients += ingredients.get(i).getName();
					}
				}
				formResult.add(8, chosenIngredients);
				for (int i = 0; i < intolerances.size(); i++) {
					if (selectionnedIntolerances.get(i).equals(true)) {
						if (!chosenIntolerances.equals("")) {
							chosenIntolerances += ",";
						}
						chosenIntolerances += intolerances.get(i);
					}
				}

				if (formResult.get(2).equals("")) {
					formResult.set(2, "10000");
				}
				if (formResult.get(7).equals("")) {
					formResult.set(7, "10000");
				}
				if (formResult.get(4).equals("All")) {
					formResult.set(4, "");
				}
				if (formResult.get(5).equals("All")) {
					formResult.set(5, "");
				}
				if (formResult.get(6).equals("None")) {
					formResult.set(6, "");
				}

				formResult.add(9, chosenIntolerances);

				// Requête API
				List<RecipeAPI> recipesComplexSearch = new ArrayList<RecipeAPI>();
				OkButtonActionListener okButtonController = new OkButtonActionListener();
				recipesComplexSearch = okButtonController.actionPerformed(formResult);

				// Affichage
				resultPanelClass.updateRecipesList(resultsPanel, recipesComplexSearch, detailedFormPanel,
						detailedRecipePanel);

			}
		});
	}

	private void fillFridge(JPanel fridgePanel, JButton allButton, List ingredients,
			ArrayList<Boolean> selectionnedIngredients, GridBagConstraints gbc) {
		Font font = new Font("Arial", Font.PLAIN, 16);

		for (int i = 0; i < ingredients.size(); i++) {
			// Bouton d'ajout de l'ingrédient à la recette

			final int currentIngredientIndex = i;

			JButton addButton = new JButton("");
			addButton.setBackground(new Color(240, 240, 240));
			addButton.addActionListener(new ActionListener() {
				private boolean isAdded = false;

				@Override
				public void actionPerformed(ActionEvent e) {
					if (!isAdded) {
						addButton.setBackground(Color.GREEN);
						selectionnedIngredients.set(currentIngredientIndex, true);
					} else {
						addButton.setBackground(new Color(240, 240, 240));
						selectionnedIngredients.set(currentIngredientIndex, false);
					}
					isAdded = !isAdded;
				}
			});
			JLabel ingredientsLabel = null;
			// Nom de l'ingrédient
			if(ingredients.get(i) instanceof IngredientAPI){
				ingredientsLabel = new JLabel(((IngredientAPI) ingredients.get(i)).getName()+" "+((IngredientAPI) ingredients.get(i)).getDate());
			}else {
				ingredientsLabel = new JLabel((String) ingredients.get(i));
			}
			ingredientsLabel.setFont(font);

			// Ajout d'un panel pour contenir les informations
			JPanel ingredientPanel = new JPanel(new BorderLayout());
			ingredientPanel.add(addButton, BorderLayout.WEST);
			ingredientPanel.add(ingredientsLabel, BorderLayout.CENTER);
			ingredientPanel.setBackground(Color.WHITE);

			// Ajout du ActionListener du bouton "All" à chaque bouton d'ajout
			allButton.addActionListener(new ActionListener() {
				private boolean isGreen = false;

				@Override
				public void actionPerformed(ActionEvent e) {
					if (!isGreen) {
						if (!addButton.getBackground().equals(Color.GREEN)) {
							addButton.doClick(); // Simule un clic sur le bouton
						}
					} else {
						if (addButton.getBackground().equals(Color.GREEN)) {
							addButton.doClick(); // Simule un clic sur le bouton
						}
					}
					isGreen = !isGreen;
					for (int i = 0; i < ingredients.size(); i++) {
						selectionnedIngredients.set(i, true);
					}

				}
			});

			fridgePanel.add(ingredientPanel, gbc);
			gbc.gridy+=1;
		}
	}

	private void setPlaceholder(JTextField searchText, String placeholder) {
		searchText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (searchText.getText().equals(placeholder)) {
					searchText.setText("");
				}
			}
		});
	}
}
