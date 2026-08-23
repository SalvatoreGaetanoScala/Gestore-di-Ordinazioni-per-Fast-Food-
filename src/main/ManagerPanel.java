package main;

import controller.Chiosco;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ManagerPanel extends JPanel {
    
    // Aggiunto per risolvere l'avviso "serial" in modo pulito
    private static final long serialVersionUID = 1L;
    
    private Chiosco chiosco;
    private JTextArea txtReport;

    public ManagerPanel(Chiosco chiosco) {
        this.chiosco = chiosco;
        
        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(245, 245, 245));
        setBorder(new EmptyBorder(30, 50, 30, 50));

        // --- TITOLO ---
        JLabel lblTitolo = new JLabel("BACK-OFFICE MANAGER", SwingConstants.CENTER);
        lblTitolo.setFont(new Font("SansSerif", Font.BOLD, 28));
        add(lblTitolo, BorderLayout.NORTH);

        // --- AREA REPORT ---
        txtReport = new JTextArea();
        txtReport.setFont(new Font("Monospaced", Font.BOLD, 18));
        txtReport.setEditable(false);
        txtReport.setBackground(Color.WHITE);
        txtReport.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JScrollPane scrollPane = new JScrollPane(txtReport);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        add(scrollPane, BorderLayout.CENTER);

        // --- BOTTONE GENERA ---
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlBottom.setOpaque(false);
        
        JButton btnGenera = new JButton("Genera Report Vendite Giornaliero");
        btnGenera.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnGenera.setBackground(new Color(255, 199, 44));
        btnGenera.setPreferredSize(new Dimension(400, 50));
        
        btnGenera.addActionListener(e -> {
            // Modificato con "this.chiosco" per usare correttamente la variabile di istanza
            String report = this.chiosco.generaReport();
            txtReport.setText(report);
        });
        
        pnlBottom.add(btnGenera);
        add(pnlBottom, BorderLayout.SOUTH);
        
        txtReport.setText("Premi il pulsante in basso per generare il report...");
    }
}
