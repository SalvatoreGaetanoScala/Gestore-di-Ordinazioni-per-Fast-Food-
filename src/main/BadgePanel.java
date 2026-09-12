package main;

import javax.swing.*;
import java.awt.*;

/**
 * la classe {@code BadgePanel} disegna un piccolo cerchio contenente un numero
 * in stile notifica/badge, molto usato per indicare le quantità nel carrello.
 */
public class BadgePanel extends JPanel {
    private String text;
    private final Color COLOR_RED = new Color(218, 41, 28);

    /**
     * costruttore della classe.
     *
     * @param text -> la stringa da mostrare all'interno del badge.
     */
    public BadgePanel(String text) {
        this.text = text;
        setPreferredSize(new Dimension(35, 35));
        setOpaque(false);
    }
    
    /**
     * aggiorna il testo e forza un ridisegno grafico del componente.
     * @param text -> il nuovo testo da renderizzare.
     */
    public void setText(String text) { 
        this.text = text; 
        repaint(); 
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(COLOR_RED);
        g2.fillOval(0, 0, 35, 35);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 18));
        FontMetrics fm = g2.getFontMetrics();
        int x = (35 - fm.stringWidth(text)) / 2;
        int y = ((35 - fm.getHeight()) / 2) + fm.getAscent();
        g2.drawString(text, x, y);
    }
}
