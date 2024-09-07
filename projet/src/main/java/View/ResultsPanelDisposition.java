package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import Model.RecipeAPI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ResultsPanelDisposition {

	JPanel recipesListPanel = new JPanel();

	protected void resultsPanelDisposition(JPanel resultsPanel, JPanel detailedRecipePanel, JPanel detailedFormPanel) {
		resultsPanel.setLayout(new BorderLayout());

		// Espace où se trouve le titre du panel
		JPanel title = new JPanel();
		title.setBackground(Color.decode("#C89F9C"));
		resultsPanel.add(title, BorderLayout.NORTH);

		// Titre du panel
		JLabel titleLabel = new JLabel("Search Result");
		Font titleLabelFont = new Font("Arial", Font.PLAIN, 22);
		titleLabel.setFont(titleLabelFont);
		title.add(titleLabel, BorderLayout.CENTER);

		// Panel pour les résultats de la recherche

		recipesListPanel.setBackground(Color.decode("#EEE2DF"));
		recipesListPanel.setLayout(new GridBagLayout());
		resultsPanel.add(recipesListPanel, BorderLayout.CENTER);

		// Liste temporaire de recette
		String[] recipesList = {};

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0; // Tous les composants seront dans la même colonne
		gbc.gridy = 0; // Initialiser la ligne à 0

		// Nombre d'éléments à créer (peut être déterminé dynamiquement)
		int numberOfButtons = recipesList.length;

		for (int i = 0; i < recipesList.length; i++) {
			// bouton contenant le nom de la recette
			JButton recipesButton = new JButton(recipesList[i]);
			recipesButton.setPreferredSize(new Dimension(380, 75));
			recipesButton.setBackground(Color.WHITE);
			recipesButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					detailedFormPanel.setVisible(false);
					detailedRecipePanel.setVisible(true);
				}
			});

			gbc.gridy = i; // Changer de ligne pour chaque bouton
			recipesListPanel.add(recipesButton, gbc);
		}

		// Ajout d'une scrollpane au panel du frigo
		JScrollPane resultScrollPane = new JScrollPane(recipesListPanel);
		resultScrollPane.setBackground(null);
		resultScrollPane.setBounds(10, 50, 380, 440);
		resultsPanel.add(resultScrollPane);
	}

	protected void updateRecipesList(JPanel resultsPanel, List<RecipeAPI> recipesComplexSearch,
			JPanel detailedFormPanel, JPanel detailedRecipePanel) {

		recipesListPanel.removeAll();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		for (RecipeAPI recipe : recipesComplexSearch) {

			// Création du bouton

			JButton recipesButton = new JButton(
					"<html>" + recipe.getTitle() + "<br>Preparation time : " + recipe.getPreparationTime()
							+ " minutes | Spoonacular score : " + recipe.getspoonacularScore() + "</html>");
			
		    // Ajouter l'icône pour la durée de préparation
		    ImageIcon timeIcon = createScaledImageIcon("images/icon_time.png", 30, 30);
		    recipesButton.setIcon(timeIcon);

		    // Ajouter l'icône pour le score Spoonacular
		    ImageIcon scoreIcon = createScaledImageIcon("images/icon_score.png", 30, 30);
		    recipesButton.setRolloverIcon(scoreIcon);
		    
			
			recipesButton.setPreferredSize(new Dimension(380, 75));
			recipesButton.setBackground(Color.WHITE);
			
			gbc.gridy += 1;
			this.recipesListPanel.add(recipesButton, gbc);
			recipesButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					detailedFormPanel.setVisible(false);
					detailedRecipePanel.setVisible(true);
					RecipeDetailPanelDisposition recipeDetailPanelDisposition = new RecipeDetailPanelDisposition(
							detailedRecipePanel);
					recipeDetailPanelDisposition.recipeDetailPanelDisposition(recipe);

				}
			});

		}

		// Repeindre le panel
		resultsPanel.revalidate();
		resultsPanel.repaint();
	}
	
    private ImageIcon createScaledImageIcon(String path, int width, int height) {
        try {
            Image originalImage = ImageIO.read(new File(path));
            Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


	protected void setPlaceholder(JTextField searchText, String placeholder) {
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
