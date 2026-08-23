package domain;

import java.util.ArrayList;
import java.util.List;

public class VoceOrdine {
    private Prodotto prodotto;
    private int quantita;
    private double subtotale;
    private List<Personalizzazione> personalizzazioni;

    public VoceOrdine(Prodotto prodotto, int quantita) {
        this.prodotto = prodotto;
        this.quantita = quantita;
        this.personalizzazioni = new ArrayList<>();
        calcolaSubtotale();
    }

    public void aggiungiPersonalizzazione(String tipo, String ingrediente, double sovrapprezzo) {
        personalizzazioni.add(new Personalizzazione(tipo, ingrediente, sovrapprezzo));
        calcolaSubtotale();
    }

    // MODIFICA: Aggiunto il setter per cambiare quantità in modo dinamico
    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    // MODIFICA: Cambiato da private a public per renderlo accessibile dal Chiosco
    public void calcolaSubtotale() {
        double totalePers = 0.0;
        for (Personalizzazione p : personalizzazioni) {
            totalePers += p.getSovrapprezzo();
        }
        this.subtotale = (prodotto.getPrezzoBase() + totalePers) * quantita;
    }

    public Prodotto getProdotto() { 
        return prodotto; 
    }
    
    public int getQuantita() { 
        return quantita; 
    }
    
    public double getSubTotale() { 
        return subtotale; 
    }
    
    public List<Personalizzazione> getPersonalizzazioni() { 
        return personalizzazioni; 
    }
}
