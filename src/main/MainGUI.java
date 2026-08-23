package main;

import controller.Chiosco;

import javax.swing.*;
import java.awt.*;

/**
 * Classe principale di avvio dell'applicazione.
 * Assembla il Controller (Chiosco) e le Viste (ClientPanel, KitchenPanel, ManagerPanel).
 */
public class MainGUI {

    public static void main(String[] args) {
        
        // Avvio dell'interfaccia grafica in modo thread-safe
        SwingUtilities.invokeLater(() -> {
            
            // 1. Inizializzazione del Controller (Dominio)
            Chiosco chiosco = new Chiosco();

            // 2. Creazione del Frame principale
            JFrame frame = new JFrame("Gestore Ordinazioni Fast Food - Terminali");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 800);
            frame.setLocationRelativeTo(null); // Centra la finestra
            
            // 3. Creazione del pannello a schede (TabbedPane)
            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.setFont(new Font("SansSerif", Font.BOLD, 16));

            // 4. Creazione delle tre Viste passando il controller
            
            // A. Pannello Cliente (UC1) - Passo anche una lambda per aggiornare la cucina post-pagamento
            KitchenPanel pannelloCucina = new KitchenPanel(chiosco);
            
            ClientPanel pannelloCliente = new ClientPanel(chiosco, () -> {
                // Callback: quando un ordine viene pagato, aggiorno la vista della cucina
                pannelloCucina.aggiornaCoda();
            });
            
            // B. Pannello Manager (UC5)
            ManagerPanel pannelloManager = new ManagerPanel(chiosco);

            // 5. Aggiunta dei tab
            tabbedPane.addTab(" 🍔 Kiosk Cliente (Ordina qui) ", pannelloCliente);
            tabbedPane.addTab(" 👨‍🍳 Terminale Cucina ", pannelloCucina);
            tabbedPane.addTab(" 📊 Back-Office Manager ", pannelloManager);

            // 6. Mostra l'applicazione
            frame.add(tabbedPane);
            frame.setVisible(true);
        });
    }
}
