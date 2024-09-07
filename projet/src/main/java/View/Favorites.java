package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Controller.SelectionController;
import Controller.SelectionEventHandler;
import Model.RecipeService;
import Model.Selection;
import Model.SelectionService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

public class Favorites extends JPanel {
//    JPanel this= new JPanel();
	private JPanel formPanel = new JPanel();
	private JPanel detailPanel = new JPanel(new BorderLayout());
	JPanel ingredientPanel = new JPanel(new BorderLayout());

	private List<Selection> selectionList;

	
	public JPanel getDetailPanel() {
		return detailPanel;
	}

	public void setDetailPanel(JPanel detailPanel) {
		this.detailPanel = detailPanel;
	}

	public List<Selection> getSelectionList() {
		return selectionList;
	}

	public void setSelectionList(List<Selection> selectionList) {
		this.selectionList = selectionList;
	}

	public void myFavorites() {
		this.removeAll();

		// Informer le layout manager que la disposition des composants a changé
		this.revalidate();

		// Forcer le redessin du panel
		this.repaint();

		this.setLayout(new GridLayout(1, 3));

		// Création des JPanels principaux
		JPanel selectionsPanel = new JPanel(new BorderLayout());
		selectionsPanel.setBackground(new Color(250, 250, 100).brighter());
		this.add(selectionsPanel);

		ingredientPanel.setBackground(new Color(250, 250, 100).brighter());
		ingredientPanel.setVisible(false);
		
		this.add(detailPanel);
		this.add(ingredientPanel);

		detailPanel.setBackground(new Color(250, 250, 100).brighter());
		//this.add(detailPanel, BorderLayout.EAST);
		detailPanel.setVisible(false);

		selectionPanelDisposition(selectionsPanel, detailPanel, this);

	}

	private void selectionPanelDisposition(JPanel selectionsPanel, JPanel detailPanel, Favorites favorite) {
		// Titre du panel des sélections
		JPanel selectionsTitlePanel = new JPanel();
		selectionsTitlePanel.setBackground(new Color(250, 250, 100));
		selectionsTitlePanel.setPreferredSize(new Dimension(selectionsTitlePanel.getWidth(), 53));
		JLabel selectionsTitleLabel = new JLabel("My created selections");
		selectionsTitlePanel.add(selectionsTitleLabel);
		selectionsPanel.add(selectionsTitlePanel, BorderLayout.NORTH);

		// Panel principal des selections
		JPanel selectionsMainPanel = new JPanel(new BorderLayout());
		selectionsMainPanel.setBackground(new Color(250, 250, 100).brighter());
		selectionsPanel.add(selectionsMainPanel, BorderLayout.CENTER);

		JPanel selectionsButtonEmptyBorderPanel = new JPanel(new BorderLayout());
		selectionsButtonEmptyBorderPanel.setBackground(new Color(250, 250, 100).brighter());
		selectionsMainPanel.add(selectionsButtonEmptyBorderPanel, BorderLayout.NORTH);

		// Panel pour le filtre et le bouton de nouvelle selection
		JPanel selectionsButtonPanel = new JPanel(new GridLayout(1, 2));
		selectionsButtonPanel.setBackground(new Color(250, 250, 100).brighter());
		selectionsButtonEmptyBorderPanel.add(selectionsButtonPanel, BorderLayout.NORTH);

		// Ajout d'un espace vide à gauche du panel
		JPanel leftSpacePanel = new JPanel();
		leftSpacePanel.setBackground(new Color(250, 250, 100).brighter());
		selectionsButtonPanel.add(leftSpacePanel);

		// Bouton nouvelle selection
		JButton newSelectionButton = new JButton("New selection");
		newSelectionButton.setBackground(Color.decode("#7AA95C"));
		selectionsButtonPanel.add(newSelectionButton);
		newSelectionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NewSelectionForm form = new NewSelectionForm();
				form.newSelectionForm(formPanel, Favorites.this);
			}
		});

		// Ajout d'un espace vide entre les deux panels
		JPanel spacePanel = new JPanel();
		spacePanel.setBackground(new Color(250, 250, 100).brighter());
		selectionsButtonEmptyBorderPanel.add(spacePanel, BorderLayout.SOUTH);

		// Ajout d'un espace vide à droite du panel
		JPanel rightSpacePanel = new JPanel();
		rightSpacePanel.setBackground(new Color(250, 250, 100).brighter());
		rightSpacePanel.setBorder(new EmptyBorder(0, 0, 0, 10));
		selectionsButtonPanel.add(rightSpacePanel);

		// Panel pour les boutons des sélections
		SelectionService selectionService = new SelectionService();
		selectionList = selectionService.select();
		if (selectionList.size()==0) {
			selectionService.insert("Favorites", "My favorite recipes");
			this.myFavorites();
		}
		
		for (int k=1; k<selectionList.size(); k++) {
			if (selectionList.get(k).getName_selection().equals("Favorites")){
				Collections.swap(selectionList, 0, k);
			}
		}

		
		JPanel allSelectionsPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		allSelectionsPanel.setBackground(new Color(250, 250, 100).brighter());
		allSelectionsPanel.setBorder(BorderFactory.createEmptyBorder());
		for (int i = 0; i < selectionList.size(); i++) {
			final int index = i;
			
			JPanel oneSelectionPanel = new JPanel(new GridLayout(1, 4));
			oneSelectionPanel.setSize(new Dimension(600, 75));
			gbc.gridy += 1;
			
			JPanel buttonPanel = new JPanel(new BorderLayout());
			JButton oneSelectionButton = new JButton(selectionList.get(i).getName_selection());
			oneSelectionButton.setPreferredSize(new Dimension(100, 75));
			oneSelectionButton.setBackground(Color.white);
			oneSelectionPanel.add(oneSelectionButton);

			SelectionController selectionController = new SelectionController();
			int totalRecipeInSelection = selectionController.totalRecipe(selectionList.get(i).getName_selection());

			JLabel recipeNumberLabel = new JLabel(String.valueOf(totalRecipeInSelection)+" recipes");
			recipeNumberLabel.setPreferredSize(new Dimension(100, 75));
			oneSelectionPanel.add(recipeNumberLabel);

			if (i > 0) {
				JButton editButton = new JButton();
				editButton.setPreferredSize(new Dimension(100, 75));
				ImageIcon icon = new ImageIcon("images/icon_pen.png");
        	Image img = icon.getImage();
        	Image scaledImage = img.getScaledInstance(73, 50, Image.SCALE_SMOOTH);
        	editButton.setIcon(new ImageIcon(scaledImage));
        	editButton.setBackground(new Color(144, 238, 144));
			oneSelectionPanel.add(editButton);
			ImageIcon iconPen = new ImageIcon("images/icon_pen.png");
        	Image imgPen = iconPen.getImage();
        	Image scaledImagePen = imgPen.getScaledInstance(73, 50, Image.SCALE_SMOOTH);
        	editButton.setIcon(new ImageIcon(scaledImagePen));
			editButton.setBackground(Color.decode("#7AA95C"));
			oneSelectionPanel.add(editButton);

				editButton.setName(String.valueOf(selectionList.get(i).getId_selection()));

				editButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						NewSelectionForm newSelectionForm = new NewSelectionForm();
						newSelectionForm.updateSelectionForm(selectionList.get(index), Favorites.this);
					}
				});

				JButton deleteButton = new JButton();
				deleteButton.setPreferredSize(new Dimension(100, 75));
				icon = new ImageIcon("images\\icon_trash.png");
        			img = icon.getImage();
        			scaledImage = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        			deleteButton.setIcon(new ImageIcon(scaledImage));
			
				deleteButton.setBackground(Color.decode("#A7001E"));
        	icon = new ImageIcon("images\\icon_trash.png");
        	img = icon.getImage();
        	scaledImage = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        	deleteButton.setIcon(new ImageIcon(scaledImage));
				oneSelectionPanel.add(deleteButton);

				deleteButton.setName(String.valueOf(selectionList.get(i).getId_selection()));
				// TODO 13
				deleteButton.setActionCommand("deleteSelection");
				deleteButton.addActionListener(new SelectionEventHandler(this));
			}

			// Ajout d'un espace vide entre les deux panels
			JPanel buttonSpacePanel = new JPanel();
			buttonSpacePanel.setBackground(new Color(250, 250, 100).brighter());
			buttonSpacePanel.setBorder(new EmptyBorder(0, 0, 10, 0));
			buttonPanel.add(buttonSpacePanel, BorderLayout.SOUTH);

			// Ajout d'un espace vide entre les deux panels
			JPanel leftButtonSpacePanel = new JPanel();
			leftButtonSpacePanel.setBackground(new Color(250, 250, 100).brighter());
			buttonPanel.add(leftButtonSpacePanel, BorderLayout.WEST);

			// Ajout d'un espace vide entre les deux panels
			JPanel rightButtonSpacePanel = new JPanel();
			rightButtonSpacePanel.setBackground(new Color(250, 250, 100).brighter());
			buttonPanel.add(rightButtonSpacePanel, BorderLayout.EAST);
			
			buttonPanel.add(oneSelectionPanel, BorderLayout.CENTER);

			oneSelectionButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					SelectionDisposition.detailPanelDisposition(detailPanel, selectionList.get(index), favorite, index);
					RecipeService recipeService = new RecipeService();
					List<Model.Recipe> recipes = recipeService.select(selectionList.get(index).getName_selection());
					SelectionIngredientPanelDisposition panelDisposition = new SelectionIngredientPanelDisposition(ingredientPanel, recipes);
					panelDisposition.panelDisposition();
				}
			});
			allSelectionsPanel.add(buttonPanel, gbc);
		}
		selectionsMainPanel.add(allSelectionsPanel, BorderLayout.CENTER);
		selectionsMainPanel.setBackground(new Color(250, 250, 100).brighter());


		JScrollPane selectionsScrollPane = new JScrollPane(allSelectionsPanel);
		selectionsScrollPane.setBackground(new Color(250, 250, 100).brighter());
		selectionsScrollPane.setBorder(BorderFactory.createEmptyBorder());
		selectionsMainPanel.add(selectionsScrollPane);
	}
	
	public void setDetailPanelVisible(boolean visible)
	{
		detailPanel.setVisible(visible);
	}
}
