package domain;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * la classe {@code Ordine} rappresenta una richiesta effettuata da un cliente,
 * traccia l'intero ciclo di vita dell'ordinazione, le voci contenute, 
 * il totale e il pagamento.
 */
public class Ordine {
    private static int contatoreOrdini = 1;
    private String idOrdine; 
    private Date date;
    private double totale;
    
    private StatoOrdine stato; 
    
    private List<VoceOrdine> voci;
    private Pagamento pagamento;

    public Ordine() {
        this.idOrdine = "A" + contatoreOrdini++; 
        this.date = new Date();
        this.stato = StatoOrdine.IN_CREAZIONE; 
        this.voci = new ArrayList<>();
        this.totale = 0.0;
    }

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

    public double calcolaTotale() {
        this.totale = 0.0;
        for (VoceOrdine vo : voci) {
            this.totale += vo.getSubTotale();
        }
        return this.totale;
    }

    /**
     * applica la prima promozione attiva al totale di partenza delle voci,
     * garantisce che il ricalcolo parta sempre dal prezzo pieno per evitare doppi sconti.
     */
    public void applicaPromozione(List<Promozione> promozioniAttive) {
        // prima si ricalcola il totale pulito sommando le voci
        calcolaTotale(); 
        
        if (!promozioniAttive.isEmpty()) {
            Promozione promo = promozioniAttive.get(0);
            this.totale = promo.applicaSconto(this.totale);
            System.out.println("Applicata promozione: " + promo.getDescrizione());
        }
    }

    public void setStato(StatoOrdine stato) { this.stato = stato; } 
    public StatoOrdine getStato() { return stato; } 
    public String getIdOrdine() { return idOrdine; } 
    public double getTotale() { return totale; }
    public Date getDate() { return date; }
    public void setPagamento(Pagamento p) { this.pagamento = p; }
    public Pagamento getPagamento() { return pagamento; }
    public List<VoceOrdine> getVoci() { return voci; }
}
