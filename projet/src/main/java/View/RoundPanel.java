package View;
import javax.swing.*;
import java.awt.*;

public class RoundPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private int radius = 30; // Default radius of the rounded corners

    public RoundPanel(LayoutManager layout) {
        super(layout);
        setOpaque(false); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the rounded panel
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, radius, radius);
    }

  

    public void setRadius(int radius) {
        this.radius = radius;
        repaint();
    }
}
