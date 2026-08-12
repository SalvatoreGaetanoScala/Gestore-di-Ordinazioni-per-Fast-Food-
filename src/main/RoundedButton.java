package main;

import javax.swing.*;
import java.awt.*;

/**
 * La classe {@code RoundedButton} estende i bottoni base di Java per 
 * supportare la colorazione degli angoli arrotondati e personalizzazione di sfondi.
 */
public class RoundedButton extends JButton {
    private Color bgColor;
    private int radius;
    
    /**
     * Costruisce il bottone.
     *
     * @param text   Il testo sul bottone.
     * @param bg     Il colore di background.
     * @param fg     Il colore del testo (foreground).
     * @param radius Il raggio di curvatura per arrotondare i bordi.
     */
    public RoundedButton(String text, Color bg, Color fg, int radius) {
        super(text);
        this.bgColor = bg;
        this.radius = radius;
        setForeground(fg);
        setFont(new Font("SansSerif", Font.BOLD, 18));
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);
        
        // Modifica: Cambia il cursore in "manina" quando ci si passa sopra
        setCursor(new Cursor(Cursor.HAND_CURSOR)); 
    }
    
    /**
     * Cambia i colori del bottone a runtime e ne forza il ridisegno.
     *
     * @param bg Il nuovo colore di background.
     * @param fg Il nuovo colore del testo.
     */
    public void setButtonColor(Color bg, Color fg) {
        this.bgColor = bg;
        setForeground(fg);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (getModel().isPressed()) g2.setColor(bgColor.darker());
        else g2.setColor(bgColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        g2.dispose();
        super.paintComponent(g);
    }
}
