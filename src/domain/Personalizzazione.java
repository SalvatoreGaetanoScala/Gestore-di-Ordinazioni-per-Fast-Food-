package domain;

/**
 * la classe {@code Personalizzazione} definisce una variazione applicata a un prodotto
 * specifico (ad esempio l'aggiunta di un ingrediente) e l'eventuale costo aggiuntivo.
 */
public class Personalizzazione {
    private String tipo; // "Aggiunta", "Rimozione", "Upgrade"
    private String ingrediente;
    private double sovrapprezzo;

    /**
     * Costruttore per creare una personalizzazione.
     *
     * @param tipo    ->     il tipo di operazione (es. "Aggiunta", "Rimozione").
     * @param ingrediente  ->  il nome dell'ingrediente interessato dalla modifica.
     * @param sovrapprezzo ->  il costo extra applicato per questa personalizzazione.
     */
    public Personalizzazione(String tipo, String ingrediente, double sovrapprezzo) {
        this.tipo = tipo;
        this.ingrediente = ingrediente;
        this.sovrapprezzo = sovrapprezzo;
    }

    /** @return il costo aggiuntivo relativo a questa personalizzazione. */
    public double getSovrapprezzo() { return sovrapprezzo; }
    
    /** @return il tipo di personalizzazione ("Aggiunta", "Rimozione", ecc.). */
    public String getTipo() { return tipo; }
    
    /** @return il nome dell'ingrediente modificato. */
    public String getIngrediente() { return ingrediente; }
    
    /**
     * qui restituisce una rappresentazione testuale della personalizzazione.
     *
     * @return una stringa leggibile (es. "Aggiunta Bacon (+€1.5)").
     */
    @Override
    public String toString() {
        return tipo + " " + ingrediente + " (+€" + sovrapprezzo + ")";
    }
}
