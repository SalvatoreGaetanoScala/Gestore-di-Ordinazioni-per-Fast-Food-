package domain;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * La classe {@code Ordine} rappresenta una richiesta effettuata da un cliente.
 * Traccia l'intero ciclo di vita dell'ordinazione, le voci contenute, il totale e il pagamento.
 */
public class Ordine {
    private static int contatoreOrdini = 1;
    private String idOrdine; // Modificato da int a String
    private Date date;
    private double totale;
    private String stato; // "In Creazione", "Ricevuto", "In Preparazione", "Pronto"
    
    private List<VoceOrdine> voci;
    private Pagamento pagamento;

    /**
     * Costruttore di default.
     * Genera automaticamente un ID alfanumerico sequenziale, imposta la data odierna,
     * inizializza lo stato a "In Creazione" e crea una lista vuota per le voci.
     */
    public Ordine() {
        // Generazione del codice alfanumerico (es. A1, A2...)
        this.idOrdine = "A" + contatoreOrdini++; 
        this.date = new Date();
        this.stato = "In Creazione";
        this.voci = new ArrayList<>();
        this.totale = 0.0;
    }

    /**
     * Aggiunge una nuova voce all'ordine, applicando eventuali personalizzazioni.
     *
     * @param p                 Il prodotto selezionato dal catalogo.
     * @param quantita          La quantità desiderata.
     * @param personalizzazioni Una lista di array di stringhe, dove ogni array contiene
     *                          le informazioni della personalizzazione (tipo, ingrediente, sovrapprezzo).
     */
    public void aggiungiVoce(Prodotto p, int quantita, List<String[]> personalizzazioni) {
        VoceOrdine vo = new VoceOrdine(p, quantita);
        if (personalizzazioni != null) {
            for (String[] pers : personalizzazioni) {
                vo.aggiungiPersonalizzazione(pers[0], pers[1], Double.parseDouble(pers[2]));
            }
        }
        voci.add(vo);
        calcolaTotale();
    }

    /**
     * Ricalcola il totale dell'ordine sommando i subtotali di tutte le voci.
     *
     * @return Il totale aggiornato dell'ordine.
     */
    public double calcolaTotale() {
        this.totale = 0.0;
        for (VoceOrdine vo : voci) {
            this.totale += vo.getSubTotale();
        }
        return this.totale;
    }

    /**
     * Applica la prima promozione attiva presente nella lista fornita al totale dell'ordine.
     *
     * @param promozioniAttive La lista delle promozioni attualmente valide.
     */
    public void applicaPromozione(List<Promozione> promozioniAttive) {
        if (!promozioniAttive.isEmpty()) {
            Promozione promo = promozioniAttive.get(0);
            this.totale = promo.applicaSconto(this.totale);
            System.out.println("Applicata promozione: " + promo.getDescrizione());
        }
    }

    /** @param stato Il nuovo stato dell'ordine. */
    public void setStato(String stato) { this.stato = stato; }
    
    /** @return Lo stato attuale dell'ordine. */
    public String getStato() { return stato; }
    
    /** @return L'identificativo alfanumerico dell'ordine. */
    public String getIdOrdine() { return idOrdine; } // Ritorna una Stringa
    
    /** @return Il costo totale dell'ordine. */
    public double getTotale() { return totale; }
    
    /** @return La data e l'ora di creazione dell'ordine. */
    public Date getDate() { return date; }
    
    /** @param p L'oggetto Pagamento da associare a questo ordine. */
    public void setPagamento(Pagamento p) { this.pagamento = p; }
    
    /** @return I dettagli del pagamento effettuato per questo ordine. */
    public Pagamento getPagamento() { return pagamento; }
    
    /** @return La lista delle voci che compongono l'ordine. */
    public List<VoceOrdine> getVoci() { return voci; }
}
