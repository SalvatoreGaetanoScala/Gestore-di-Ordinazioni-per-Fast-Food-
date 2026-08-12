package main;

import controller.Chiosco;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * La classe {@code KitchenPanel} definisce l'interfaccia grafica riservata al personale di cucina.
 * Permette di visualizzare gli ordini in coda, aggiornarne lo stato ("In Preparazione", "Pronto") 
 * e mostrare il display pubblico per la sala clienti.
 */
public class KitchenPanel extends JPanel {
    private Chiosco chiosco;
    private JTextArea txtCodaCucina;
    private JTextArea txtMonitorSala;

    private final Color COLOR_BG = new Color(245, 245, 245);
    private final Color COLOR_RED = new Color(218, 41, 28);
    private final Color COLOR_YELLOW = new Color(255, 199, 44);
    private final Color COLOR_GREEN = new Color(34, 139, 34);

    /**
     * Costruttore della classe {@code KitchenPanel}.
     * Costruisce e posiziona tutti gli elementi della vista (testi, pulsanti e layout).
     *
     * @param chiosco L'istanza del controller {@link Chiosco} per comunicare con la logica di dominio.
     */
    public KitchenPanel(Chiosco chiosco) {
        this.chiosco = chiosco;
        
        setLayout(new BorderLayout(10, 10));
        setBackground(COLOR_BG);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        txtCodaCucina = new JTextArea();
        txtCodaCucina.setEditable(false);
        txtCodaCucina.setFont(new Font("Monospaced", Font.PLAIN, 18));
        txtCodaCucina.setForeground(Color.BLACK);
        JScrollPane scrollCoda = new JScrollPane(txtCodaCucina);
        scrollCoda.setBorder(BorderFactory.createTitledBorder("ORDINI DA PREPARARE"));

        txtMonitorSala = new JTextArea();
        txtMonitorSala.setEditable(false);
        txtMonitorSala.setFont(new Font("Monospaced", Font.BOLD, 18));
        txtMonitorSala.setForeground(Color.BLUE);
        JScrollPane scrollMonitor = new JScrollPane(txtMonitorSala);
        scrollMonitor.setBorder(BorderFactory.createTitledBorder("DISPLAY SALA CLIENTI"));

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        centerPanel.setBackground(COLOR_BG);
        centerPanel.add(scrollCoda);
        centerPanel.add(scrollMonitor);

        JPanel controlPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        controlPanel.setBackground(COLOR_BG);
        controlPanel.setPreferredSize(new Dimension(0, 80));

        RoundedButton btnTastierino = new RoundedButton("ID ORDINE", Color.WHITE, Color.BLACK, 15);
        btnTastierino.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        
        RoundedButton btnInPrep = new RoundedButton("IN PREP", COLOR_YELLOW, Color.BLACK, 15);
        RoundedButton btnPronto = new RoundedButton("PRONTO", COLOR_GREEN, Color.WHITE, 15);
        
        RoundedButton btnAggiorna = new RoundedButton("AGGIORNA", Color.WHITE, Color.BLACK, 15);
        btnAggiorna.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        final String[] idAttuale = {""};

        btnTastierino.addActionListener(e -> {
            String input = apriTastierinoCucina("Inserisci ID Ordine (es. A1)");
            if (input != null && !input.isEmpty()) { 
                idAttuale[0] = input; 
                btnTastierino.setText("SEL: " + input); 
            }
        });

        btnInPrep.addActionListener(e -> {
            if(!idAttuale[0].isEmpty()){
                txtMonitorSala.append(chiosco.prendiInCarico(idAttuale[0]) + "\n");
                aggiornaCode();
                idAttuale[0] = ""; btnTastierino.setText("ID ORDINE");
            }
        });

        btnPronto.addActionListener(e -> {
            if(!idAttuale[0].isEmpty()){
                txtMonitorSala.append(chiosco.segnaPronto(idAttuale[0]) + "\n");
                aggiornaCode();
                idAttuale[0] = ""; btnTastierino.setText("ID ORDINE");
            }
        });

        btnAggiorna.addActionListener(e -> aggiornaCode());

        controlPanel.add(btnTastierino);
        controlPanel.add(btnInPrep);
        controlPanel.add(btnPronto);
        controlPanel.add(btnAggiorna);

        add(centerPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    /**
     * Richiede l'aggiornamento testuale dell'area che mostra la coda degli ordini.
     * Interroga il controller per ottenere i dati freschi.
     */
    public void aggiornaCode() {
        txtCodaCucina.setText(chiosco.visualizzaCodaOrdini());
    }

    /**
     * Apre un tastierino modale a schermo per permettere al cuoco di inserire 
     * in modo touch o tramite mouse l'ID dell'ordine su cui vuole agire.
     *
     * @param titolo Il titolo da assegnare alla finestra di dialogo.
     * @return La stringa inserita dall'utente, oppure {@code null} se l'operazione è annullata.
     */
    private String apriTastierinoCucina(String titolo) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), titolo, true);
        dialog.setSize(450, 600);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.getContentPane().setBackground(COLOR_BG);

        JTextField display = new JTextField();
        display.setFont(new Font("SansSerif", Font.BOLD, 32));
        display.setHorizontalAlignment(JTextField.CENTER);
        display.setForeground(Color.BLACK);
        display.setEditable(false);
        dialog.add(display, BorderLayout.NORTH);

        JPanel tastierino = new JPanel(new GridLayout(4, 3, 10, 10));
        tastierino.setBackground(COLOR_BG);
        tastierino.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        String[] tasti = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "0", "CANC"};
        final String[] result = {null};

        for (String t : tasti) {
            Color bg = t.equals("CANC") ? COLOR_RED : Color.WHITE;
            Color fg = t.equals("CANC") ? Color.WHITE : Color.BLACK;
            
            RoundedButton btn = new RoundedButton(t, bg, fg, 15);
            btn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            
            btn.addActionListener(e -> {
                if (t.equals("CANC")) display.setText("");
                else display.setText(display.getText() + t);
            });
            tastierino.add(btn);
        }

        RoundedButton btnOk = new RoundedButton("OK", COLOR_YELLOW, Color.BLACK, 20);
        btnOk.setFont(new Font("SansSerif", Font.BOLD, 24));
        btnOk.setPreferredSize(new Dimension(0, 70));
        btnOk.addActionListener(e -> { result[0] = display.getText(); dialog.dispose(); });

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
