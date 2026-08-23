package controller;

import domain.Catalogo;
import domain.Ordine;
import domain.Prodotto;
import domain.Promozione;
import domain.VoceOrdine;
import services.CodaCucina;
import services.MonitorSala;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code Chiosco} funge da Controller principale (GRASP) del sistema.
 */
public class Chiosco {
    
    private Catalogo catalogo;
    private Ordine ordineCorrente;
    private CodaCucina codaCucina;
    private MonitorSala monitorSala;
    
    private List<Ordine> storicoOrdini;

    public Chiosco() {
        this.catalogo = new Catalogo();
        this.codaCucina = new CodaCucina();
        this.monitorSala = new MonitorSala();
        this.storicoOrdini = new ArrayList<>();
    }

    public void iniziaOrdine() {
        this.ordineCorrente = new Ordine();
    }

    public void aggiungiProdotto(String idProdotto, int quantita, List<String[]> personalizzazioni) {
        if (ordineCorrente == null) {
            iniziaOrdine();
        }
        
        Prodotto p = catalogo.getProdotto(idProdotto);
        if (p != null) {
            ordineCorrente.aggiungiVoce(p, quantita, personalizzazioni);
        }
    }

    public void rimuoviVoce(int indice) {
        if (ordineCorrente != null && indice >= 0 && indice < ordineCorrente.getVoci().size()) {
            ordineCorrente.getVoci().remove(indice);
            ordineCorrente.calcolaTotale();
        }
    }

    public void aggiornaPersonalizzazioniVoce(int indice, List<String[]> nuovePersonalizzazioni) {
        if (ordineCorrente != null && indice >= 0 && indice < ordineCorrente.getVoci().size()) {
            VoceOrdine vo = ordineCorrente.getVoci().get(indice);
            vo.getPersonalizzazioni().clear();
            
            if (nuovePersonalizzazioni != null) {
                for (String[] pers : nuovePersonalizzazioni) {
                    vo.aggiungiPersonalizzazione(pers[0], pers[1], Double.parseDouble(pers[2]));
                }
            }
            vo.calcolaSubtotale();
            ordineCorrente.calcolaTotale();
        }
    }

    public void aggiornaQuantitaVoce(int indice, int nuovaQuantita) {
        if (ordineCorrente != null && indice >= 0 && indice < ordineCorrente.getVoci().size()) {
            VoceOrdine vo = ordineCorrente.getVoci().get(indice);
            if (nuovaQuantita >= 1) {
                vo.setQuantita(nuovaQuantita);
                vo.calcolaSubtotale();
                ordineCorrente.calcolaTotale();
            }
        }
    }

    public void terminaOrdine() {
        if (ordineCorrente != null) {
            ordineCorrente.calcolaTotale();
            List<Promozione> promozioniAttive = catalogo.getPromozioniAttive();
            ordineCorrente.applicaPromozione(promozioniAttive);
        }
    }

    public String paga(String numeroCarta, String scadenza, String cvv) {
        if (ordineCorrente == null || ordineCorrente.getVoci().isEmpty()) {
            return "Errore: Ordine vuoto.";
        }
        
        if (numeroCarta.length() == 16 && scadenza.length() == 4 && cvv.length() == 3) {
            ordineCorrente.setStato("Ricevuto");
            codaCucina.aggiungiOrdine(ordineCorrente);
            storicoOrdini.add(ordineCorrente);
            
            String idAssegnato = ordineCorrente.getIdOrdine();
            return "Pagamento Autorizzato.\nIl tuo numero d'ordine è: " + idAssegnato;
        } else {
            return "Transazione Rifiutata. Verifica i dati inseriti.";
        }
    }

    public double getTotaleOrdineCorrente() {
        if (ordineCorrente == null) return 0.0;
        return ordineCorrente.getTotale();
    }

    public int getNumeroVociCorrenti() {
        if (ordineCorrente == null) return 0;
        return ordineCorrente.getVoci().size();
    }

    public List<VoceOrdine> getVociCarrello() {
        if (ordineCorrente == null) return new ArrayList<>();
        return ordineCorrente.getVoci();
    }

    public String visualizzaCodaOrdini() {
        return codaCucina.mostraCodaGUI();
    }

    // MODIFICA: Aggiornato per non impazzire se il prodotto viene cercato col case sbagliato
    public void prendiInCarico(String idOrdine) {
        Ordine o = codaCucina.getOrdine(idOrdine.toUpperCase());
        if (o != null) {
            o.setStato("In Preparazione");
            monitorSala.aggiornaStato(idOrdine.toUpperCase(), "In Preparazione");
        }
    }

    // MODIFICA: Aggiornato per far funzionare lo shift nella vista clienti e implementare il blocco di stato
    public void segnaPronto(String idOrdine) {
        Ordine o = codaCucina.getOrdine(idOrdine.toUpperCase());
        // Controlla che l'ordine non sia nullo e che sia effettivamente "In Preparazione"
        if (o != null && "In Preparazione".equals(o.getStato())) {
            o.setStato("Pronto");
            monitorSala.aggiornaStato(idOrdine.toUpperCase(), "Pronto");
        }
    }

    public String getStatoMonitorSala() {
        return monitorSala.mostraMonitorGUI();
    }

    public String generaReport() {
        int numeroOrdini = 0;
        double incassoTotale = 0.0;

        for (Ordine o : storicoOrdini) {
            numeroOrdini++;
            incassoTotale += o.getTotale();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== REPORT VENDITE GIORNALIERO ===\n\n");
        sb.append("Totale ordini evasi: ").append(numeroOrdini).append("\n");
        sb.append("Incasso totale netto: €").append(String.format("%.2f", incassoTotale)).append("\n\n");
        sb.append("==================================");

        return sb.toString();
    }
}
