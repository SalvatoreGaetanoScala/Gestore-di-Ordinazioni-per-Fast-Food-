package domain;
import java.util.Date;

/**
 * la classe {@code Pagamento} traccia i dati relativi alla transazione economica
 * per un determinato ordine.
 */
public class Pagamento {
    private double importo;
    private boolean esito;
    private Date dataOra;

    /**
     * costruttore della classe.
     *
     * @param importo -> l'importo addebitato.
     * @param esito  ->  il risultato della transazione , 
     *  cioè {@code true} se approvato,  {@code false} altrimenti.
     */
    public Pagamento(double importo, boolean esito) {
        this.importo = importo;
        this.esito = esito;
        this.dataOra = new Date();
    }

    /** @return {@code true} se il pagamento è andato a buon fine, {@code false} altrimenti. */
    public boolean isEsitoPositivo() { return esito; }
    
    /** @return l'importo della transazione. */
    public double getImporto() { return importo; }
    
    /** @return la data e l'ora in cui è stato registrato il pagamento. */
    public Date getDataOra() { return dataOra; }
}
