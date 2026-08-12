package services;
import domain.Ordine;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code CodaCucina} gestisce la lista degli ordini in coda di elaborazione 
 * per il personale di cucina, monitorandone lo stato corrente.
 */
public class CodaCucina {
    private List<Ordine> ordiniInCoda;
    private List<String> ordiniProntiMostrati;

    /**
     * Costruttore della classe {@code CodaCucina}.
     * Inizializza le liste per gli ordini in coda e per quelli pronti già tracciati.
     */
    public CodaCucina() {
        this.ordiniInCoda = new ArrayList<>();
        this.ordiniProntiMostrati = new ArrayList<>();
    }

    /**
     * Inserisce un nuovo ordine all'interno della coda di cucina.
     *
     * @param o L'oggetto {@link Ordine} da aggiungere.
     */
    public void aggiungiOrdine(Ordine o) {
        ordiniInCoda.add(o);
    }

    /**
     * Cerca e restituisce un ordine in base al suo identificativo alfanumerico.
     *
     * @param idOrdine L'ID dell'ordine da ricercare.
     * @return L'oggetto {@link Ordine} corrispondente, oppure {@code null} se non viene trovato.
     */
    public Ordine getOrdine(String idOrdine) {
        for (Ordine o : ordiniInCoda) {
            if (o.getIdOrdine().equalsIgnoreCase(idOrdine)) return o;
        }
        return null;
    }

    /**
     * Genera una rappresentazione testuale formattata della coda ordini corrente,
     * adatta per essere mostrata nell'interfaccia grafica del back-office.
     *
     * @return Una stringa contenente l'elenco degli ordini attivi e dei relativi stati.
     */
    // Metodo adattato per l'interfaccia grafica
    public String mostraCodaGUI() {
        StringBuilder sb = new StringBuilder("=== CODA CUCINA ===\n\n");
        boolean ciSonoOrdiniAttivi = false;
        
        for (Ordine o : ordiniInCoda) {
            String stato = o.getStato();
            
            if (stato.equals("Pronto")) {
                if (!ordiniProntiMostrati.contains(o.getIdOrdine())) {
                    sb.append("Ordine ").append(o.getIdOrdine()).append(" | Stato: ").append(stato).append("\n");
                    ciSonoOrdiniAttivi = true;
                    ordiniProntiMostrati.add(o.getIdOrdine());
                }
            } else if (!stato.equals("Ritirato")) {
                sb.append("Ordine ").append(o.getIdOrdine()).append(" | Stato: ").append(stato).append("\n");
                ciSonoOrdiniAttivi = true;
            }
        }
        
        if (!ciSonoOrdiniAttivi) {
            sb.append("Nessun ordine attivo da preparare.\n");
        }
        return sb.toString();
    }
}
