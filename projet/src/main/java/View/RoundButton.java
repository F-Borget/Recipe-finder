
package View;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class RoundButton extends JButton {
    private Color backgroundColor;
    private Color hoverColor;
    private Color pressedColor;
    private int radius; // Radius of the rounded corners

    public RoundButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);

        backgroundColor = Color.decode("#CCCCCC"); // Default background color
        hoverColor = Color.decode("#AAAAAA"); // Color when the mouse hovers over the button
        pressedColor = Color.decode("#888888"); // Color when the button is pressed
        radius = 20; // Default radius

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        int width = getWidth();
        int height = getHeight();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

  
        if (getModel().isPressed()) {
            g2d.setColor(pressedColor);
        } else if (getModel().isRollover()) {
            g2d.setColor(hoverColor);
        } else {
            g2d.setColor(backgroundColor);
        }

        g2d.fill(new RoundRectangle2D.Double(0, 0, width - 1, height - 1, radius, radius));

     
        g2d.setColor(getForeground());
        FontMetrics metrics = g2d.getFontMetrics();
        int x = (width - metrics.stringWidth(getText())) / 2;
        int y = ((height - metrics.getHeight()) / 2) + metrics.getAscent();
        g2d.drawString(getText(), x, y);

        g2d.dispose();
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackground(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Color getHoverColor() {
        return hoverColor;
    }

    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    public Color getPressedColor() {
        return pressedColor;
    }

    public void setPressedColor(Color pressedColor) {
        this.pressedColor = pressedColor;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("RoundButton Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new FlowLayout());

        RoundButton button = new RoundButton("Click me!");
        button.setPreferredSize(new Dimension(120, 40));
        button.setRadius(10); // Set a custom radius
        frame.add(button);

        frame.setVisible(true);
    }
}

