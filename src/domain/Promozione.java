package domain;

/**
 * La classe {@code Promozione} rappresenta uno sconto applicabile a un ordine.
 * Lo sconto può essere di tipo percentuale o a valore fisso.
 */
public class Promozione {
    private String idPromozione;
    private String descrizione;
    private String tipoSconto; // es. "PERCENTUALE" o "FISSO"
    private double valore;

    /**
     * Costruttore per creare una nuova promozione.
     *
     * @param idPromozione L'identificativo univoco della promozione.
     * @param descrizione  La descrizione testuale della promozione (es. "Sconto 10%").
     * @param tipoSconto   La tipologia di sconto applicato ("PERCENTUALE" o "FISSO").
     * @param valore       Il valore dello sconto (es. 10.0 per il 10% o 5.0 per 5€).
     */
    public Promozione(String idPromozione, String descrizione, String tipoSconto, double valore) {
        this.idPromozione = idPromozione;
        this.descrizione = descrizione;
        this.tipoSconto = tipoSconto;
        this.valore = valore;
    }

    /**
     * Applica lo sconto al totale parziale calcolato.
     *
     * @param totaleAttuale Il totale su cui applicare la promozione.
     * @return Il nuovo totale scontato. Se lo sconto fisso supera il totale, restituisce 0.
     */
    public double applicaSconto(double totaleAttuale) {
        if (tipoSconto.equals("PERCENTUALE")) {
            return totaleAttuale - (totaleAttuale * (valore / 100.0));
        } else if (tipoSconto.equals("FISSO")) {
            return Math.max(0, totaleAttuale - valore);
        }
        return totaleAttuale;
    }
    
    /** @return L'identificativo della promozione. */
    public String getIdPromozione() { return idPromozione; }
    
    /** @return La descrizione della promozione. */
    public String getDescrizione() { return descrizione; }
    
    /** @return La tipologia dello sconto. */
    public String getTipoSconto() { return tipoSconto; }
    
    /** @return Il valore numerico dello sconto. */
    public double getValore() { return valore; }
}
