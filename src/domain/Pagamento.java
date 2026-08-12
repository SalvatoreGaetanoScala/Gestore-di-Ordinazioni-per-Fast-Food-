package domain;
import java.util.Date;

/**
 * La classe {@code Pagamento} traccia i dati relativi alla transazione economica
 * per un determinato ordine.
 */
public class Pagamento {
    private double importo;
    private boolean esito;
    private Date dataOra;

    /**
     * Costruttore della classe.
     *
     * @param importo L'importo addebitato.
     * @param esito   Il risultato della transazione ({@code true} se approvato, {@code false} altrimenti).
     */
    public Pagamento(double importo, boolean esito) {
        this.importo = importo;
        this.esito = esito;
        this.dataOra = new Date();
    }

    /** @return {@code true} se il pagamento è andato a buon fine, {@code false} altrimenti. */
    public boolean isEsitoPositivo() { return esito; }
    
    /** @return L'importo della transazione. */
    public double getImporto() { return importo; }
    
    /** @return La data e l'ora in cui è stato registrato il pagamento. */
    public Date getDataOra() { return dataOra; }
}
