package services;

import domain.StatoOrdine; // import aggiunto per l'Enum StatoOrdine
import java.util.ArrayList;
import java.util.List;

/**
 * la classe {@code MonitorSala} gestisce il monitor pubblico visibile in sala 
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
     * aggiorna lo stato di un ordine spostandolo nella lista corretta del monitor,
     * metodo richiamato dal Chiosco quando lo stato dell'ordine cambia in cucina.
     * 
     * @param idOrdine -> ID dell'ordine.
     * @param nuovoStato -> Enum che rappresenta il nuovo stato dell'ordine.
     */
    public void aggiornaStato(String idOrdine, StatoOrdine nuovoStato) { // MODIFICA
        String idNormalized = idOrdine.trim().toUpperCase();

        // modifica -> confronto pulito usando l'Enum
        if (nuovoStato == StatoOrdine.IN_PREPARAZIONE) {
            if (!ordiniInPreparazione.contains(idNormalized)) {
                ordiniInPreparazione.add(idNormalized);
            }
        } else if (nuovoStato == StatoOrdine.PRONTO) {
            ordiniInPreparazione.remove(idNormalized);
            if (!ordiniPronti.contains(idNormalized)) {
                ordiniPronti.add(idNormalized);
            }
        }
    }

    /**
     * costruisce la stringa formattata da mostrare nella text area azzurra.
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
