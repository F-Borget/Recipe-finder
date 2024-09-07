package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.MatteBorder;
import Controller.FridgeController;
import Model.IngredientAPI;
import Model.RecipeManager;

public class SelectionIngredientPanelDisposition {

	JPanel ingredientPanel;
	List<Model.Recipe> recipes;
	List<IngredientAPI> shoppingList;

	public SelectionIngredientPanelDisposition(JPanel ingredientPanel, List<Model.Recipe> recipes) {
		this.ingredientPanel = ingredientPanel;
		this.recipes = recipes;
	}

	protected void panelDisposition() {
		ingredientPanel.setVisible(true);

		ingredientPanel.removeAll();
		ingredientPanel.repaint();
		ingredientPanel.revalidate();

		// Titre du panel des sélections
		JPanel ingredientsTitlePanel = new JPanel();
		ingredientsTitlePanel.setBackground(new Color(250, 250, 100));
		ingredientsTitlePanel.setPreferredSize(new Dimension(ingredientsTitlePanel.getWidth(), 53));
		JLabel selectionsTitleLabel = new JLabel("Ingredients");
		ingredientsTitlePanel.add(selectionsTitleLabel);
		ingredientPanel.add(ingredientsTitlePanel, BorderLayout.NORTH);

		// Panel qui contient les autres panels d'informations
		JPanel ingredientsMainPanel = new JPanel(new GridBagLayout());
		ingredientsMainPanel.setBackground(new Color(250, 250, 100).brighter());
		ingredientPanel.add(ingredientsMainPanel, BorderLayout.CENTER);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;

		JScrollPane scrollPane = new JScrollPane(ingredientsMainPanel);
		scrollPane.setBorder(null);
		ingredientPanel.add(scrollPane);

		// Panel pour le frigo
		JPanel fridgePanel = new JPanel(new BorderLayout());
		fridgePanel.setBackground(new Color(250, 250, 100).brighter());
		ingredientsMainPanel.add(fridgePanel, gbc);
		JLabel fridgeLabel = new JLabel("My fridge : ");
		fridgePanel.add(fridgeLabel, BorderLayout.NORTH);
		JPanel fridgeMainPanel = new JPanel(new GridLayout(0, 3));
		fridgeMainPanel.setBackground(Color.white);
		fridgePanel.add(fridgeMainPanel, BorderLayout.CENTER);

		// Remplissage du frigo
		List<IngredientAPI> ingredients = FridgeController.selectIngredients();
		this.fillFridge(fridgeMainPanel, ingredients);

		// Espace avec le panel du dessous
		PanelSpaces.bottomSpace(fridgePanel, 10, new Color(250, 250, 100).brighter());

		// Ingrémentation de gbc pour que le prochain Panel soit en dessous
		gbc.gridy = 1;

		// Panel contenant tous les ingrédients des recettes
		JPanel neededIngredientsPanel = new JPanel(new BorderLayout());
		neededIngredientsPanel.setBackground(new Color(250, 250, 100).brighter());
		ingredientsMainPanel.add(neededIngredientsPanel, gbc);
		JLabel neededIngredientsLabel = new JLabel("Ingredients in this selection : ");
		neededIngredientsPanel.add(neededIngredientsLabel, BorderLayout.NORTH);
		JPanel neededIngredientsMainPanel = new JPanel(new GridLayout(0, 3));
		neededIngredientsMainPanel.setBackground(Color.white);
		neededIngredientsPanel.add(neededIngredientsMainPanel, BorderLayout.CENTER);

		// Déterminer les ingrédients nécéssaires
		List<IngredientAPI> neededIngredients = new ArrayList<IngredientAPI>();
		List<IngredientAPI> aggregatedIngredients = new ArrayList<IngredientAPI>();
		for (int i = 0; i < recipes.size(); i++) {
			List<IngredientAPI> tmp = extractAndConvertIngredients(recipes.get(i).getIngredients());

			for (int j = 0; j < tmp.size(); j++) {
				neededIngredients.add(tmp.get(j));
			}
			aggregatedIngredients = RecipeManager.aggregateIngredientsFromIngredientList(neededIngredients);
		}

		// Remplissage du frigo
		this.fillFridge(neededIngredientsMainPanel, aggregatedIngredients);

		// Espace avec le panel du dessous
		PanelSpaces.bottomSpace(neededIngredientsPanel, 10, new Color(250, 250, 100).brighter());

		// Ingrémentation de gbc pour que le prochain Panel soit en dessous
		gbc.gridy = 2;

		// Panel pour les ingrédients à ajouter à la shopping list
		JPanel listPanel = new JPanel(new BorderLayout());
		listPanel.setBackground(new Color(250, 250, 100).brighter());
		ingredientsMainPanel.add(listPanel, gbc);
		JLabel listLabel = new JLabel("Ingredient to add to the shopping list : ");
		listPanel.add(listLabel, BorderLayout.NORTH);
		JPanel listMainPanel = new JPanel(new GridLayout(0, 3));
		listMainPanel.setBackground(Color.white);
		listPanel.add(listMainPanel, BorderLayout.CENTER);

		// Find missing ingredients
		shoppingList = RecipeManager.findMissingIngredients(aggregatedIngredients, ingredients);

		// Remplissage du frigo
		this.fillFridge(listMainPanel, shoppingList);

	}

	private void fillFridge(JPanel fridgeMainPanel, List ingredients) {

		for (int i = 0; i < ingredients.size(); i++) {
			// Bouton d'ajout de l'ingrédient à la recette

			final int currentIngredientIndex = i;

			JTextArea ingredientsLabel = null;
			// Nom de l'ingrédient
			if (ingredients.get(i) instanceof IngredientAPI) {
				if (((IngredientAPI) ingredients.get(i)).getUnit().equals("unit")) {
					ingredientsLabel = new JTextArea(((IngredientAPI) ingredients.get(i)).getAmount() + " "
							+ ((IngredientAPI) ingredients.get(i)).getName());
				} else {
					ingredientsLabel = new JTextArea(((IngredientAPI) ingredients.get(i)).getAmount()
							+ ((IngredientAPI) ingredients.get(i)).getUnit() + " of "
							+ ((IngredientAPI) ingredients.get(i)).getName());
				}

			} else {
				ingredientsLabel = new JTextArea((String) ingredients.get(i));
			}
			ingredientsLabel.setMaximumSize(new Dimension(120, Short.MAX_VALUE));
			ingredientsLabel.setEditable(false);
			ingredientsLabel.setLineWrap(true);
			ingredientsLabel.setWrapStyleWord(true);

			// Ajout d'un panel pour contenir les informations
			JPanel ingredientPanel = new JPanel(new BorderLayout());
			ingredientPanel.add(ingredientsLabel, BorderLayout.CENTER);
			ingredientPanel.setBackground(Color.WHITE);

			fridgeMainPanel.add(ingredientPanel);
			MatteBorder bordure = new MatteBorder(1, 1, 1, 1, Color.BLACK);
			ingredientsLabel.setBorder(bordure);

		}
	}

	private static List<IngredientAPI> extractAndConvertIngredients(String inputString) {
		List<IngredientAPI> ingredients = new ArrayList<>();

		String regex = "(\\d*\\.?\\d+)\\s+(\\w+)\\s+([^,]+)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(inputString);

		while (matcher.find()) {
			double quantity = Double.parseDouble(matcher.group(1));
			String originalUnit = matcher.group(2);
			String name = matcher.group(3);

			ingredients.add(RecipeManager.convertToMetric(originalUnit, quantity, name));
		}

		return ingredients;
	}

	
}
