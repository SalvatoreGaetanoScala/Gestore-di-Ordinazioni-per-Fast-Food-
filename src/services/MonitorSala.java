package services;

/**
 * La classe {@code MonitorSala} si occupa di simulare il display visivo e sonoro
 * situato in sala per informare i clienti sullo stato di avanzamento dei loro ordini.
 */
public class MonitorSala {
    
    /**
     * Aggiorna lo stato di un ordine da mostrare a schermo, generando un avviso acustico (simulato).
     *
     * @param idOrdine    L'identificativo dell'ordine aggiornato.
     * @param nuovoStato  Il nuovo stato assunto dall'ordine (es. "In Preparazione", "Pronto").
     * @return Una stringa formattata che simula il messaggio visivo e il segnale acustico del monitor.
     */
    public String aggiornaStatoGUI(String idOrdine, String nuovoStato) {
        return "[MONITOR SALA] -> DING! Ordine " + idOrdine + " e' ora: " + nuovoStato.toUpperCase();
    }
}
