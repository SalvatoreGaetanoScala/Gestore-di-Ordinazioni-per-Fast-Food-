package domain;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code VoceOrdine} modella una singola riga all'interno del carrello.
 * Associa un {@link Prodotto} a una quantità specifica e contiene la lista delle
 * personalizzazioni applicate su di esso.
 */
public class VoceOrdine {
    private Prodotto prodotto;
    private int quantita;
    private double subtotale;
    private List<Personalizzazione> personalizzazioni;

    /**
     * Costruttore che crea una riga d'ordine e calcola il suo subtotale di partenza.
     *
     * @param prodotto Il prodotto richiesto.
     * @param quantita Il numero di unità per questo prodotto.
     */
    public VoceOrdine(Prodotto prodotto, int quantita) {
        this.prodotto = prodotto;
        this.quantita = quantita;
        this.personalizzazioni = new ArrayList<>();
        calcolaSubtotale();
    }

    /**
     * Aggiunge una personalizzazione a questa voce e ricalcola il subtotale.
     *
     * @param tipo         Il tipo di modifica (es. "Aggiunta").
     * @param ingrediente  L'ingrediente modificato (es. "Formaggio").
     * @param sovrapprezzo L'importo da sommare al prezzo base.
     */
    public void aggiungiPersonalizzazione(String tipo, String ingrediente, double sovrapprezzo) {
        Personalizzazione pers = new Personalizzazione(tipo, ingrediente, sovrapprezzo);
        this.personalizzazioni.add(pers);
        calcolaSubtotale();
    }

    /**
     * Calcola il subtotale sommando il prezzo base del prodotto al costo 
     * delle personalizzazioni, e moltiplicando il tutto per la quantità richiesta.
     */
    private void calcolaSubtotale() {
        double extra = 0;
        for (Personalizzazione p : personalizzazioni) {
            extra += p.getSovrapprezzo();
        }
        this.subtotale = (prodotto.getPrezzoBase() + extra) * quantita;
    }

    /** @return Il costo totale calcolato per questa voce d'ordine. */
    public double getSubTotale() { return subtotale; }
    
    /** @return Il prodotto associato alla voce. */
    public Prodotto getProdotto() { return prodotto; }
    
    /** @return La quantità ordinata. */
    public int getQuantita() { return quantita; }
    
    /** @return La lista delle personalizzazioni apportate a questa voce. */
    public List<Personalizzazione> getPersonalizzazioni() { return personalizzazioni; }
    
    /**
     * Genera una descrizione di sintesi della riga d'ordine.
     *
     * @return Una stringa formattata con quantità, nome prodotto e subtotale.
     */
    @Override
    public String toString() {
        return quantita + "x " + prodotto.getNome() + " - Subtotale: €" + String.format("%.2f", subtotale);
    }
}
