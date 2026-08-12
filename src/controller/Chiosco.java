package controller;

import domain.Ordine;
import domain.Pagamento;
import domain.Prodotto;
import domain.VoceOrdine;
import domain.Personalizzazione;
import services.Catalogo;
import services.CodaCucina;
import services.MonitorSala;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code Chiosco} funge da controller principale dell'applicazione.
 * Gestisce l'intero ciclo di vita di un'ordinazione, coordinando le interazioni 
 * tra l'utente, il catalogo, i pagamenti e i sistemi della cucina.
 */
public class Chiosco {
    private Catalogo catalogo;
    private CodaCucina codaCucina;
    private MonitorSala monitorSala;
    private Ordine ordineCorrente;

    /**
     * Costruttore della classe {@code Chiosco}.
     * Inizializza i servizi necessari per il funzionamento del sistema:
     * catalogo, coda della cucina e monitor della sala.
     */
    public Chiosco() {
        this.catalogo = new Catalogo();
        this.codaCucina = new CodaCucina();
        this.monitorSala = new MonitorSala();
    }

    /**
     * Avvia una nuova sessione di ordinazione creando un'istanza vuota di {@code Ordine}.
     */
    public void iniziaOrdine() {
        this.ordineCorrente = new Ordine();
    }

    /**
     * Aggiunge un prodotto all'ordine corrente, includendo l'eventuale quantità e personalizzazioni.
     * 
     * @param idProdotto        L'identificativo univoco del prodotto da aggiungere.
     * @param quantita          La quantità del prodotto selezionato.
     * @param personalizzazioni Lista di array di stringhe contenente i dettagli delle personalizzazioni.
     * @return Una stringa di conferma dell'aggiunta con il totale parziale aggiornato,
     *         oppure un messaggio di errore se l'ordine non è avviato o il prodotto non esiste.
     */
    public String aggiungiProdotto(String idProdotto, int quantita, List<String[]> personalizzazioni) {
        if (ordineCorrente == null) return "Avvia prima un ordine.";
        
        Prodotto p = catalogo.getProdotto(idProdotto);
        if (p != null) {
            ordineCorrente.aggiungiVoce(p, quantita, personalizzazioni);
            return "Aggiunto: " + quantita + "x " + p.getNome() + "\nTotale parziale: €" + String.format("%.2f", ordineCorrente.getTotale());
        }
        return "Prodotto non trovato.";
    }

    /**
     * Conclude la fase di composizione dell'ordine, applicando le eventuali promozioni attive
     * e calcolando il totale definitivo da pagare.
     * 
     * @return Una stringa contenente il totale finale da pagare, o un messaggio di errore se non ci sono ordini in corso.
     */
    public String terminaOrdine() {
        if (ordineCorrente == null) return "Nessun ordine in corso.";
        ordineCorrente.applicaPromozione(catalogo.getPromozioniAttive());
        return "Totale da pagare: €" + String.format("%.2f", ordineCorrente.getTotale());
    }

    /**
     * Simula il processo di pagamento per l'ordine corrente. Se la transazione ha esito
     * positivo, l'ordine viene inoltrato alla cucina.
     * 
     * @param numeroCarta Il numero della carta di credito/debito.
     * @param scadenza    La data di scadenza della carta.
     * @param cvv         Il codice di sicurezza CVV.
     * @return Un messaggio che indica l'esito della transazione e, in caso di successo, il numero dell'ordine.
     */
    public String paga(String numeroCarta, String scadenza, String cvv) {
        if (ordineCorrente == null) return "Errore di sistema.";
        
        Pagamento p = new Pagamento(ordineCorrente.getTotale(), true);
        ordineCorrente.setPagamento(p);
        
        if (p.isEsitoPositivo()) {
            ordineCorrente.setStato("Ricevuto");
            codaCucina.aggiungiOrdine(ordineCorrente);
            String msg = "Pagamento approvato!\nIl tuo numero d'ordine e': " + ordineCorrente.getIdOrdine();
            this.ordineCorrente = null; 
            return msg;
        }
        return "Transazione rifiutata.";
    }

    /**
     * Genera un riepilogo testuale formattato di tutte le voci presenti nell'ordine corrente.
     * 
     * @return Il testo formattato con i dettagli del carrello e il totale, oppure un avviso se il carrello è vuoto.
     */
    public String getRiepilogoTestuale() {
        if (ordineCorrente == null || ordineCorrente.getVoci().isEmpty()) {
            return "Il carrello e' vuoto.";
        }
        StringBuilder sb = new StringBuilder("--- RIEPILOGO ORDINE ---\n");
        int i = 1;
        for (VoceOrdine vo : ordineCorrente.getVoci()) {
            sb.append(i).append(". ").append(vo.toString()).append("\n");
            for(Personalizzazione pers: vo.getPersonalizzazioni()){
                sb.append("   * ").append(pers.toString()).append("\n");
            }
            i++;
        }
        sb.append("------------------------\n");
        sb.append("TOTALE: €").append(String.format("%.2f", ordineCorrente.getTotale()));
        return sb.toString();
    }

    /**
     * Rimuove una specifica voce dall'ordine corrente basandosi sul suo indice.
     * 
     * @param indice La posizione della voce da rimuovere all'interno della lista.
     * @return Un messaggio di conferma dell'avvenuta rimozione o un avviso di indice non valido.
     */
    public String rimuoviVoce(int indice) {
        if (ordineCorrente != null && indice >= 0 && indice < ordineCorrente.getVoci().size()) {
            ordineCorrente.getVoci().remove(indice);
            ordineCorrente.calcolaTotale();
            return "Voce rimossa.";
        }
        return "Indice non valido.";
    }

    /**
     * Richiede e restituisce la visualizzazione formattata della coda degli ordini per la cucina.
     * 
     * @return Una stringa rappresentante la coda degli ordini correnti.
     */
    public String visualizzaCodaOrdini() {
        return codaCucina.mostraCodaGUI();
    }

    /**
     * Modifica lo stato di un ordine da "Ricevuto" a "In Preparazione", indicando
     * che la cucina ha iniziato l'elaborazione.
     * 
     * @param idOrdine L'identificativo dell'ordine da prendere in carico.
     * @return Una stringa che conferma l'aggiornamento per il monitor sala o un messaggio di errore.
     */
    public String prendiInCarico(String idOrdine) {
        Ordine o = codaCucina.getOrdine(idOrdine);
        if (o != null && o.getStato().equals("Ricevuto")) {
            o.setStato("In Preparazione");
            return monitorSala.aggiornaStatoGUI(idOrdine, "In Preparazione");
        }
        return "Impossibile prendere in carico (Non trovato o stato errato).";
    }

    /**
     * Modifica lo stato di un ordine da "In Preparazione" a "Pronto", indicando
     * che l'ordine può essere ritirato dal cliente.
     * 
     * @param idOrdine L'identificativo dell'ordine completato.
     * @return Una stringa che conferma l'aggiornamento per il monitor sala o un messaggio di errore.
     */
    public String segnaPronto(String idOrdine) {
        Ordine o = codaCucina.getOrdine(idOrdine);
        if (o != null && o.getStato().equals("In Preparazione")) {
            o.setStato("Pronto");
            return monitorSala.aggiornaStatoGUI(idOrdine, "Pronto");
        }
        return "Impossibile segnare pronto (Non trovato o stato errato).";
    }
    
    /**
     * Restituisce il numero totale di voci inserite nell'ordine corrente.
     * 
     * @return Il numero di voci presenti nel carrello, oppure 0 se non esiste alcun ordine.
     */
    public int getNumeroVociCorrenti() {
        if (ordineCorrente != null) {
            return ordineCorrente.getVoci().size();
        }
        return 0;
    }

    /**
     * Restituisce il costo totale aggiornato dell'ordine corrente.
     * 
     * @return L'importo totale dell'ordine, oppure 0.0 se non esiste alcun ordine.
     */
    public double getTotaleOrdineCorrente() {
        if (ordineCorrente != null) {
            return ordineCorrente.getTotale();
        }
        return 0.0;
    }
    
    /**
     * Recupera la lista di tutte le voci correntemente nel carrello.
     * 
     * @return Una lista di oggetti {@code VoceOrdine}, oppure una lista vuota se l'ordine non esiste.
     */
    public List<VoceOrdine> getVociCarrello() {
        if (ordineCorrente != null) {
            return ordineCorrente.getVoci();
        }
        return new ArrayList<>();
    }
}
