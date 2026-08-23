package services;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code MonitorSala} gestisce il monitor pubblico visibile in sala 
 * per notificare i clienti quando gli ordini sono in preparazione o pronti.
 */
public class MonitorSala {
    
    private List<String> ordiniInPreparazione;
    private List<String> ordiniPronti;

    public MonitorSala() {
        this.ordiniInPreparazione = new ArrayList<>();
        this.ordiniPronti = new ArrayList<>();
    }

    /**
     * Aggiorna lo stato di un ordine spostandolo nella lista corretta del monitor.
     * Metodo richiamato dal Chiosco quando lo stato dell'ordine cambia in cucina.
     */
    public void aggiornaStato(String idOrdine, String nuovoStato) {
        // Normalizziamo l'ID in modo da ignorare problemi di minuscolo/maiuscolo
        String idNormalized = idOrdine.trim().toUpperCase();

        if (nuovoStato.equalsIgnoreCase("In Preparazione")) {
            if (!ordiniInPreparazione.contains(idNormalized)) {
                ordiniInPreparazione.add(idNormalized);
            }
        } else if (nuovoStato.equalsIgnoreCase("Pronto")) {
            // Lo toglie da in preparazione e lo mette nei pronti
            ordiniInPreparazione.remove(idNormalized);
            if (!ordiniPronti.contains(idNormalized)) {
                ordiniPronti.add(idNormalized);
            }
        }
    }

    /**
     * Costruisce la stringa formattata da mostrare nella text area azzurra.
     */
    public String mostraMonitorGUI() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("IN PREPARAZIONE:\n");
        if (ordiniInPreparazione.isEmpty()) {
            sb.append("   - Nessun ordine\n");
        } else {
            for (String id : ordiniInPreparazione) {
                sb.append("   > Ordine ").append(id).append("\n");
            }
        }
        
        sb.append("\n==================================\n\n");
        
        sb.append("PRONTI PER IL RITIRO:\n");
        if (ordiniPronti.isEmpty()) {
            sb.append("   - Nessun ordine\n");
        } else {
            for (String id : ordiniPronti) {
                sb.append("   > ORDINE ").append(id).append("  <-- RITIRA QUI!\n");
            }
        }
        
        return sb.toString();
    }
}
