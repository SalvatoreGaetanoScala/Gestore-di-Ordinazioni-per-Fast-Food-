package domain;

/**
 * la classe {@code Prodotto} rappresenta un elemento in vendita all'interno del catalogo,
 * è una classe contenitore per i dettagli anagrafici e il prezzo di listino.
 */
public class Prodotto {
    private String idProdotto;
    private String nome;
    private double prezzoBase;
    private String categoria;

    /**
     * costruttore della classe.
     *
     * @param idProdotto -> identificativo univoco del prodotto nel database.
     * @param nome       -> nome mostrato all'utente.
     * @param prezzoBase -> prezzo di partenza del prodotto senza personalizzazioni.
     * @param categoria  -> categoria merceologica di appartenenza (es. "Panini", "Bevande").
     */
    public Prodotto(String idProdotto, String nome, double prezzoBase, String categoria) {
        this.idProdotto = idProdotto;
        this.nome = nome;
        this.prezzoBase = prezzoBase;
        this.categoria = categoria;
    }

    /** @return l'ID del prodotto. */
    public String getIdProdotto() { return idProdotto; }
    
    /** @return il nome del prodotto. */
    public String getNome() { return nome; }
    
    /** @return il prezzo di base. */
    public double getPrezzoBase() { return prezzoBase; }
    
    /** @return la categoria di appartenenza. */
    public String getCategoria() { return categoria; }
}
