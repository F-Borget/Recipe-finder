package View;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainPanel {
	
	// Création de la fenêtre principale
    JFrame frame = new JFrame("MyFoodApp");
    
    // Création d'un panneau principal avec CardLayout
    JPanel mainPanel = new JPanel(new CardLayout());
    Recipe myRecipesPanel = new Recipe();
    Fridge monFrigoPanel = new Fridge();
    Favorites myFavoritesPanel = new Favorites();
    ShoppingListView myShoppingPanel = new ShoppingListView();

    // Déclarez les boutons en tant que variables de classe
    private MenuButton monFrigoButton = new MenuButton("My Fridge");
    private MenuButton mesRecettesButton = new MenuButton("My Recipes");
    private MenuButton mesFavorisButton = new MenuButton("My Favorites");
    private MenuButton myShoppingButton = new MenuButton("My Shopping List");
    /*private JButton monFrigoButton = new JButton("My Fridge");
    private JButton mesRecettesButton = new JButton("My Recipes");
    private JButton mesFavorisButton = new JButton("My Favorites");
    private JButton myShoppingButton = new JButton("My Shopping List");*/


    private JButton currentButton; // Ajout d'une variable pour suivre le bouton actuel

    public void menu() {
    	
    	// Changement du logo de l'application
        ImageIcon icon = new ImageIcon("images\\logo_icon_rbg.png");
        Image img = icon.getImage();
        frame.setIconImage(img);

        // Ajuster la taille des boutons
        monFrigoButton.setPreferredSize(new Dimension(monFrigoButton.getPreferredSize().width + 10, monFrigoButton.getPreferredSize().height));
        mesRecettesButton.setPreferredSize(new Dimension(mesRecettesButton.getPreferredSize().width + 10, mesRecettesButton.getPreferredSize().height));
        mesFavorisButton.setPreferredSize(new Dimension(mesFavorisButton.getPreferredSize().width + 10, mesFavorisButton.getPreferredSize().height));
        myShoppingButton.setPreferredSize(new Dimension(myShoppingButton.getPreferredSize().width + 10, myShoppingButton.getPreferredSize().height));
        
        // Ajout des panneaux au panneau principal
        mainPanel.add(monFrigoPanel, "Mon frigo");
        mainPanel.add(myRecipesPanel, "Mes recettes");
        mainPanel.add(myFavoritesPanel, "Mes favoris");
        mainPanel.add(myShoppingPanel, "Ma liste de course");

        // Ajout d'un ActionListener à chaque bouton
        monFrigoButton.addActionListener(e -> changerOnglet(mainPanel, "Mon frigo", monFrigoButton, mesRecettesButton,
                mesFavorisButton, myShoppingButton, monFrigoPanel::myFridge));

        mesRecettesButton.addActionListener(e -> changerOnglet(mainPanel, "Mes recettes", monFrigoButton,
                mesRecettesButton, mesFavorisButton, myShoppingButton, myRecipesPanel::myRecipes));

        mesFavorisButton.addActionListener(e -> changerOnglet(mainPanel, "Mes favoris", monFrigoButton,
                mesRecettesButton, mesFavorisButton, myShoppingButton, myFavoritesPanel::myFavorites));

        myShoppingButton.addActionListener(e -> changerOnglet(mainPanel, "Ma liste de course", monFrigoButton,
                mesRecettesButton, mesFavorisButton, myShoppingButton, myShoppingPanel::myShoppingList));
        
        // Appliquer le style aux boutons
        applyStyle(monFrigoButton);
        applyStyle(mesRecettesButton);
        applyStyle(mesFavorisButton);
        applyStyle(myShoppingButton);

        // Initialiser le bouton actuel au démarrage
        currentButton = monFrigoButton;

        // Ajout d'un composant JLabel aux onglets
        JLabel monFrigoLabel = new JLabel("");
        monFrigoPanel.add(monFrigoLabel);

        JLabel mesRecettesLabel = new JLabel("");
        myRecipesPanel.add(mesRecettesLabel);

        JLabel mesFavorisLabel = new JLabel("");
        myFavoritesPanel.add(mesFavorisLabel);

        JLabel myShoppingLabel = new JLabel("");
        myShoppingPanel.add(myShoppingLabel);

        // Création d'un panneau pour contenir les boutons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        int paddingSize = 20;
        buttonPanel.setBorder(new EmptyBorder(paddingSize,0,0,0));
        buttonPanel.setBackground(Color.decode("#FFFFFF"));
        buttonPanel.add(monFrigoButton);
        buttonPanel.add(mesRecettesButton);
        buttonPanel.add(mesFavorisButton);
        buttonPanel.add(myShoppingButton);

        // Ajout du panneau des boutons au nord de la fenêtre principale
        frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);
        
        // Ajout du panneau principal au centre de la fenêtre principale
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);

        // Configuration de la fermeture de la fenêtre
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Changer par défaut à l'onglet "My Fridge"
        changerOnglet(mainPanel, "Mon frigo", monFrigoButton, mesRecettesButton, mesFavorisButton, myShoppingButton, monFrigoPanel::myFridge);

        // Rendre la fenêtre principale plein écran
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    private void applyStyle(JButton button) {
        button.setBackground(Color.LIGHT_GRAY);
        button.setForeground(Color.BLACK);

        // Ajouter une bordure
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        button.setBorder(border);

        button.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                // Mise à jour du bouton actuel et changement de couleur
                currentButton.setBackground(Color.LIGHT_GRAY);
                currentButton = button;
                button.setBackground(Color.WHITE);
            }

            public void mouseReleased(MouseEvent evt) {
                // Aucune action nécessaire ici
            }

            public void mouseEntered(MouseEvent evt) {
                // Aucune action nécessaire ici
            }

            public void mouseExited(MouseEvent evt) {
                // Aucune action nécessaire ici
            }
        });
    }

    private void changerOnglet(JPanel mainPanel, String onglet, JButton monFrigoButton, JButton mesRecettesButton,
            JButton mesFavorisButton, JButton myShoppingButton, Runnable contentFunction) {
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();

        currentButton.setBackground(Color.LIGHT_GRAY); 

        // Mise à jour du bouton actuel en fonction de l'onglet
        if (onglet.equals("Mon frigo")) {
            currentButton = monFrigoButton;
        } else if (onglet.equals("Mes recettes")) {
            currentButton = mesRecettesButton;
        } else if (onglet.equals("Mes favoris")) {
            currentButton = mesFavorisButton;
        } else if (onglet.equals("Ma liste de course")) {
            currentButton = myShoppingButton;
        }

        // Mettre en surbrillance le bouton actuel
        currentButton.setBackground(Color.WHITE);

        contentFunction.run();
        cardLayout.show(mainPanel, onglet);
    }

}
