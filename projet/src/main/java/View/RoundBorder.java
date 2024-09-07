package View;

import java.awt.*;
import javax.swing.border.Border;

public class RoundBorder implements Border {
    private int radius;
    private Color borderColor; // Color of the border

    // Constructor with color parameter
    public RoundBorder(int radius, Color borderColor) {
        this.radius = radius;
        this.borderColor = borderColor; // Set the default border color
    }

    // Constructor that defaults to black border if color not specified
    public RoundBorder(int radius) {
        this(radius, Color.BLACK);
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(borderColor); // Use the border color
        g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        g2d.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        int value = radius / 2;
        return new Insets(value, value, value, value);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

    // Setter method for the border color
    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }
}
