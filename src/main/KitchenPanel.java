package main;

import controller.Chiosco;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Interfaccia grafica per il personale di cucina e per il monitor sala (UC2).
 * Mostra la coda degli ordini a sinistra, il monitor a destra, e permette di 
 * aggiornarne lo stato tramite un tastierino touch.
 */
public class KitchenPanel extends JPanel {
    
    private static final long serialVersionUID = 1L;
    
    private Chiosco chiosco;
    private JTextArea txtCoda;
    private JTextArea txtMonitor;
    private JTextField txtIdOrdine;

    private final Color COLOR_BG = new Color(245, 245, 245);
    private final Color COLOR_RED = new Color(218, 41, 28);
    private final Color COLOR_YELLOW = new Color(255, 199, 44);
    private final Color COLOR_GREEN = new Color(34, 139, 34);

    public KitchenPanel(Chiosco chiosco) {
        this.chiosco = chiosco;
        
        setLayout(new BorderLayout(15, 15));
        setBackground(COLOR_BG);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // --- TITOLO ---
        JLabel lblTitolo = new JLabel("TERMINALE CUCINA - CODA ORDINI E MONITOR", SwingConstants.CENTER);
        lblTitolo.setFont(new Font("SansSerif", Font.BOLD, 28));
        add(lblTitolo, BorderLayout.NORTH);

        // --- PANNELLO CENTRALE (Due Colonne) ---
        JPanel pnlCentro = new JPanel(new GridLayout(1, 2, 20, 0));
        pnlCentro.setBackground(COLOR_BG);

        // Colonna 1: Coda Cucina (Lato Personale)
        txtCoda = new JTextArea();
        txtCoda.setFont(new Font("Monospaced", Font.PLAIN, 16));
        txtCoda.setEditable(false);
        txtCoda.setBackground(Color.WHITE);
        JScrollPane scrollCoda = new JScrollPane(txtCoda);
        scrollCoda.setBorder(BorderFactory.createTitledBorder(null, "=== CODA CUCINA ===", 
                             javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
                             javax.swing.border.TitledBorder.DEFAULT_POSITION, 
                             new Font("SansSerif", Font.BOLD, 14)));
        pnlCentro.add(scrollCoda);

        // Colonna 2: Monitor Sala (Lato Clienti) - Testo Azzurro
        txtMonitor = new JTextArea();
        txtMonitor.setFont(new Font("Monospaced", Font.BOLD, 18));
        txtMonitor.setForeground(Color.BLUE); // Colore azzurro richiesto
        txtMonitor.setEditable(false);
        txtMonitor.setBackground(Color.WHITE);
        JScrollPane scrollMonitor = new JScrollPane(txtMonitor);
        scrollMonitor.setBorder(BorderFactory.createTitledBorder(null, "=== MONITOR SALA ===", 
                                 javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
                                 javax.swing.border.TitledBorder.DEFAULT_POSITION, 
                                 new Font("SansSerif", Font.BOLD, 14)));
        pnlCentro.add(scrollMonitor);

        add(pnlCentro, BorderLayout.CENTER);

        // --- PANNELLO COMANDI (Sotto) ---
        JPanel pnlComandi = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pnlComandi.setOpaque(false);

        JLabel lblId = new JLabel("ID Ordine:");
        lblId.setFont(new Font("SansSerif", Font.BOLD, 18));
        pnlComandi.add(lblId);

        // Campo di testo con tastierino touch collegato
        txtIdOrdine = new JTextField(8);
        txtIdOrdine.setFont(new Font("SansSerif", Font.BOLD, 18));
        txtIdOrdine.setHorizontalAlignment(JTextField.CENTER);
        txtIdOrdine.setEditable(false); // Disabilitata tastiera fisica
        txtIdOrdine.setCursor(new Cursor(Cursor.HAND_CURSOR));
        txtIdOrdine.setBackground(Color.WHITE);
        
        txtIdOrdine.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String input = apriTastierinoTouch("Inserisci ID Ordine", 10);
                if (input != null) {
                    txtIdOrdine.setText(input);
                }
            }
        });
        pnlComandi.add(txtIdOrdine);

        // Utilizzo RoundedButton per risolvere il bug del Mac in cui i pulsanti 
        // colorati classici diventano bianchi/illeggibili.
        
        RoundedButton btnPrendi = new RoundedButton("Prendi in Carico", COLOR_YELLOW, Color.BLACK, 15);
        btnPrendi.setPreferredSize(new Dimension(180, 45));
        btnPrendi.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnPrendi.addActionListener(e -> {
            String id = txtIdOrdine.getText().trim();
            if (!id.isEmpty()) {
                chiosco.prendiInCarico(id);
                aggiornaCoda();
                txtIdOrdine.setText("");
            }
        });
        pnlComandi.add(btnPrendi);

        RoundedButton btnPronto = new RoundedButton("Segna Pronto", COLOR_GREEN, Color.WHITE, 15);
        btnPronto.setPreferredSize(new Dimension(180, 45));
        btnPronto.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnPronto.addActionListener(e -> {
            String id = txtIdOrdine.getText().trim();
            if (!id.isEmpty()) {
                chiosco.segnaPronto(id);
                aggiornaCoda();
                
                // AGGIUNTA: Emette l'avviso acustico richiesto dalla documentazione (UC2 e CO6)
                Toolkit.getDefaultToolkit().beep();
                
                txtIdOrdine.setText("");
            }
        });
        pnlComandi.add(btnPronto);

        RoundedButton btnAggiorna = new RoundedButton("Aggiorna Vista", Color.LIGHT_GRAY, Color.BLACK, 15);
        btnAggiorna.setPreferredSize(new Dimension(180, 45));
        btnAggiorna.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnAggiorna.addActionListener(e -> aggiornaCoda());
        pnlComandi.add(btnAggiorna);

        add(pnlComandi, BorderLayout.SOUTH);

        // Popola la coda all'avvio
        aggiornaCoda();
    }

    /**
     * Richiede al controller la stringa formattata della coda e del monitor e le stampa a video.
     */
    public void aggiornaCoda() {
        txtCoda.setText(chiosco.visualizzaCodaOrdini());
        txtMonitor.setText(chiosco.getStatoMonitorSala());
    }

    /**
     * Tastierino Touch Alfanumerico (QWERTY + Numeri)
     */
    private String apriTastierinoTouch(String titolo, int maxLength) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), titolo, true);
        dialog.setSize(750, 450);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.getContentPane().setBackground(COLOR_BG);

        JTextField display = new JTextField();
        display.setFont(new Font("SansSerif", Font.BOLD, 32));
        display.setHorizontalAlignment(JTextField.CENTER);
        display.setForeground(Color.BLACK);
        display.setEditable(false);
        dialog.add(display, BorderLayout.NORTH);

        JPanel tastierino = new JPanel(new GridLayout(4, 10, 5, 5));
        tastierino.setBackground(COLOR_BG);
        tastierino.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        String[] tasti = {
            "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P",
            "A", "S", "D", "F", "G", "H", "J", "K", "L", "-",
            "Z", "X", "C", "V", "B", "N", "M", "CANC", "", "",
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"
        };

        for (String t : tasti) {
            if (t.isEmpty()) {
                tastierino.add(new JLabel("")); 
                continue;
            }
            
            Color bg = t.equals("CANC") ? COLOR_RED : Color.WHITE;
            Color fg = t.equals("CANC") ? Color.WHITE : Color.BLACK;
            
            RoundedButton btn = new RoundedButton(t, bg, fg, 10);
            btn.setFont(new Font("SansSerif", Font.BOLD, 18));
            btn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            
            btn.addActionListener(e -> {
                if (t.equals("CANC")) {
                    display.setText("");
                } else if (display.getText().length() < maxLength) {
                    display.setText(display.getText() + t);
                }
            });
            tastierino.add(btn);
        }

        RoundedButton btnOk = new RoundedButton("OK", COLOR_YELLOW, Color.BLACK, 20);
        btnOk.setFont(new Font("SansSerif", Font.BOLD, 24));
        btnOk.setPreferredSize(new Dimension(0, 60));
        
        final String[] result = {null};
        btnOk.addActionListener(e -> { 
            result[0] = display.getText(); 
            dialog.dispose(); 
        });

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(COLOR_BG);
        bottomPanel.setBorder(new EmptyBorder(10, 20, 20, 20));
        bottomPanel.add(btnOk, BorderLayout.CENTER);

        dialog.add(tastierino, BorderLayout.CENTER);
        dialog.add(bottomPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);

        return result[0];
    }
}
