package main;

import controller.Chiosco;

import javax.swing.*;
import java.awt.*;

/**
 * La classe {@code MainGUI} rappresenta l'entry point (punto di ingresso) dell'applicazione.
 * Si occupa di inizializzare il controller principale ({@link Chiosco}) e di configurare
 * il contenitore grafico principale ({@link JFrame}), al cui interno vengono caricati i pannelli
 * del cliente ({@link ClientPanel}) e della cucina ({@link KitchenPanel}) mediante schede (tabs).
 */
public class MainGUI extends JFrame {
    private Chiosco chiosco;
    private ClientPanel clientPanel;
    private KitchenPanel kitchenPanel;

    /**
     * Costruttore della classe {@code MainGUI}.
     * Configura le dimensioni della finestra, inizializza le classi controller
     * e collega i pannelli dell'interfaccia utente.
     */
    public MainGUI() {
        // Inizializza il sistema MVC come richiesto
        chiosco = new Chiosco();
        chiosco.iniziaOrdine();

        setTitle("Kiosk Fast Food - Self Service");
        setSize(1280, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Separiamo le responsabilità: la Cucina riceve il Controller
        kitchenPanel = new KitchenPanel(chiosco);
        
        // Passiamo a ClientPanel un'azione (Runnable) per aggiornare la cucina dopo il pagamento
        clientPanel = new ClientPanel(chiosco, () -> kitchenPanel.aggiornaCode());

        // Aggiunta componenti al frame
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("SansSerif", Font.BOLD, 18));
        tabbedPane.addTab("CHIOSCO (Ordina Qui)", clientPanel);
        tabbedPane.addTab("CUCINA (Back-Office)", kitchenPanel);

        add(tabbedPane);
    }

    /**
     * Metodo {@code main} eseguibile per l'avvio dell'applicazione.
     * Applica il Look & Feel di sistema e rende visibile la finestra principale.
     *
     * @param args Argomenti da riga di comando (non utilizzati).
     */
    public static void main(String[] args) {
        // Applica il look and feel del sistema per adattarsi (es. MacOS/Windows)
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } 
        catch (Exception e) { e.printStackTrace(); }

        SwingUtilities.invokeLater(() -> new MainGUI().setVisible(true));
    }
}
