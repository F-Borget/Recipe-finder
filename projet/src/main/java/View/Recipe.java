package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Recipe extends JPanel{
//    JPanel this = new JPanel();
	public void myRecipes() {
		this.removeAll();

		// Informer le layout manager que la disposition des composants a changé
		this.revalidate();

			// Forcer le redessin du panel
			this.repaint();

			// Création des trois zones de la page
			JPanel formPanel = new JPanel();
			JPanel resultsPanel = new JPanel();
			JPanel detailedRecipePanel = new JPanel();
			JPanel detailedFormPanel = new JPanel();

			resultsPanel.setBackground(new Color(0, 194, 255).brighter());
			detailedRecipePanel.setBackground(new Color(0, 194, 255).brighter());
			detailedFormPanel.setBackground(Color.decode("#C9E3CC"));

			formPanel.setLayout(null);
			resultsPanel.setLayout(null);
			detailedRecipePanel.setLayout(null);
			detailedFormPanel.setLayout(null);

			// Création du bouton du formulaire
			ImageIcon icon = new ImageIcon("images\\icon_toque.png");
			JButton searchRecipesButton = new JButton("Search recipes");
			searchRecipesButton.setIcon(icon);

			Image img = icon.getImage();
			Image scaledImage = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			searchRecipesButton.setIcon(new ImageIcon(scaledImage));

			searchRecipesButton.setBounds(0, 0, 400, 100);
			searchRecipesButton.setBackground(Color.decode("#7DC2A5"));
			Font searchRecipesFont = new Font("Arial", Font.PLAIN, 22);
			searchRecipesButton.setFont(searchRecipesFont);
			searchRecipesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				detailedRecipePanel.setVisible(false);
				detailedFormPanel.setVisible(true);
			}
		});

			// Ajout du bouton au panel formulaire
			formPanel.add(searchRecipesButton);

			// Construction du panel des résultats
			ResultsPanelDisposition resultPanelClass = new ResultsPanelDisposition();
			resultPanelClass.resultsPanelDisposition(resultsPanel, detailedRecipePanel, detailedRecipePanel);

		// Construction du panel de formulaire
		DetailedFormPanelDisposition detailedFormPanelClass = new DetailedFormPanelDisposition();
		detailedFormPanelClass.detailedFormPanelDisposition(detailedFormPanel, resultsPanel, resultPanelClass, detailedRecipePanel);

		// Positionner les différentes sections
		formPanel.setBounds(10, 10, 400, 100);
		resultsPanel.setBounds(10, 120, 400, 510);
		detailedRecipePanel.setBounds(420, 10, 850, 610);
		detailedFormPanel.setBounds(420, 10, 850, 610);

			this.setLayout(null);

		// Ajouter les sections au panel principal
		this.add(formPanel);
		this.add(resultsPanel);
		this.add(detailedRecipePanel);
		this.add(detailedFormPanel);

		detailedRecipePanel.setVisible(false);
	}
}
