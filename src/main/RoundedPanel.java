package main;

import javax.swing.*;
import java.awt.*;

/**
 * La classe {@code RoundedPanel} sostituisce il normale JPanel per poter 
 * applicare degli angoli smussati ai contenitori (ad esempio le card dei prodotti).
 */
public class RoundedPanel extends JPanel {
    private int radius;
    private Color bgColor;
    
    /**
     * Costruisce il pannello.
     *
     * @param radius  Il raggio di smussatura degli angoli.
     * @param bgColor Il colore solido di sfondo.
     */
    public RoundedPanel(int radius, Color bgColor) {
        super();
        this.radius = radius;
        this.bgColor = bgColor;
        setOpaque(false);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(bgColor);
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
    }
}
