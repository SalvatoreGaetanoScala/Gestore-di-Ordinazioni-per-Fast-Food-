package domain;

/**
 * La classe {@code Prodotto} rappresenta un elemento in vendita all'interno del catalogo.
 * È una classe contenitore per i dettagli anagrafici e il prezzo di listino.
 */
public class Prodotto {
    private String idProdotto;
    private String nome;
    private double prezzoBase;
    private String categoria;

    /**
     * Costruttore della classe.
     *
     * @param idProdotto Identificativo univoco del prodotto nel database.
     * @param nome       Nome mostrato all'utente.
     * @param prezzoBase Prezzo di partenza del prodotto senza personalizzazioni.
     * @param categoria  Categoria merceologica di appartenenza (es. "Panini", "Bevande").
     */
    public Prodotto(String idProdotto, String nome, double prezzoBase, String categoria) {
        this.idProdotto = idProdotto;
        this.nome = nome;
        this.prezzoBase = prezzoBase;
        this.categoria = categoria;
    }

    /** @return L'ID del prodotto. */
    public String getIdProdotto() { return idProdotto; }
    
    /** @return Il nome del prodotto. */
    public String getNome() { return nome; }
    
    /** @return Il prezzo di base. */
    public double getPrezzoBase() { return prezzoBase; }
    
    /** @return La categoria di appartenenza. */
    public String getCategoria() { return categoria; }
}
