package View; 

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuButton extends JButton {
    private static MenuButton selectedButton = null; // Tracks the currently selected button
    private Color hoverColor = Color.WHITE;
    private Color selectedColor = new Color(0xefefef);
    private boolean hovered = false;
    private int arcWidth = 20; // Rounded corner arc width
    private int arcHeight = 20; // Rounded corner arc height

    public MenuButton(String text) {
        super(text);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setRolloverEnabled(true);
        setForeground(Color.BLACK);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        setFont(new Font("Arial", Font.BOLD, 16));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovered = false;
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedButton != null) {
                    selectedButton.setSelected(false); // Deselect the previous button
                }
                selectedButton = MenuButton.this; // Update the selected button reference
                setSelected(true);
            }
        });
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        // Draw rounded rectangle
        if (isSelected() || hovered) {
            g2d.setColor(selectedColor);
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);
        }
        	
        // Draw underline based on text width
        if (hovered || isSelected()) {
            String text = getText();
            FontMetrics metrics = g.getFontMetrics(getFont());
            int textWidth = metrics.stringWidth(text);
            int x = (getWidth() - textWidth) / 2;
            int y = getHeight() - metrics.getDescent();
            g2d.setColor(getForeground());
            g2d.setStroke(new BasicStroke(2f));
            g2d.drawLine(x, y, x + textWidth, y);
        }
        g2d.dispose();
        // Paint the text over the button after the background has been painted
        super.paintComponent(g);
    }

   
}

