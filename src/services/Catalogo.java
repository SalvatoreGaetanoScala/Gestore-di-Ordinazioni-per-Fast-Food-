package services;
import domain.Prodotto;
import domain.Promozione;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code Catalogo} gestisce l'insieme dei prodotti disponibili per l'acquisto
 * e le eventuali promozioni attive nel sistema. All'inizializzazione popola i listini predefiniti.
 */
public class Catalogo {
    private List<Prodotto> prodotti;
    private List<Promozione> promozioni;

    /**
     * Costruttore della classe {@code Catalogo}.
     * Inizializza le liste e popola il catalogo con i prodotti standard del fast food
     * (Menu Combo, Panini, Sfiziosità, Bibite e Dolci).
     */
    public Catalogo() {
        prodotti = new ArrayList<>();
        promozioni = new ArrayList<>();
        
        // Menu Completi (Combo) - Prezzi invariati
        prodotti.add(new Prodotto("M1", "Menu' Crispy Chicken Burger", 10.00, "Menu Combo"));
        prodotti.add(new Prodotto("M2", "Menu' American Burger", 12.00, "Menu Combo"));
        prodotti.add(new Prodotto("M3", "Menu' Double BBQ Burger", 15.00, "Menu Combo"));
        prodotti.add(new Prodotto("M4", "Menu' Veggie Burger", 8.00, "Menu Combo"));
        prodotti.add(new Prodotto("M5", "Menu' Crispy Fish", 12.00, "Menu Combo"));

        // Panini Singoli - Aumentati di 2.00$
        prodotti.add(new Prodotto("P1", "Crispy Chicken Burger", 8.00, "Panino"));
        prodotti.add(new Prodotto("P2", "American burger", 10.00, "Panino"));
        prodotti.add(new Prodotto("P3", "Double BBQ Burger", 12.00, "Panino"));
        prodotti.add(new Prodotto("P4", "Veggie Burger", 7.00, "Panino"));
        prodotti.add(new Prodotto("P5", "Crispy Fish", 9.00, "Panino"));

        // Sfiziosita - Prezzi invariati
        prodotti.add(new Prodotto("S1", "Patatine Piccole", 3.00, "Sfiziosita"));
        prodotti.add(new Prodotto("S2", "Patatine Medie", 4.50, "Sfiziosita"));
        prodotti.add(new Prodotto("S3", "Patatine Grandi", 5.00, "Sfiziosita"));
        prodotti.add(new Prodotto("S4", "Crocchette di pollo (4 pz)", 3.50, "Sfiziosita"));
        prodotti.add(new Prodotto("S5", "Crocchette di pollo (8 pz)", 6.00, "Sfiziosita"));
        prodotti.add(new Prodotto("S6", "Ali di pollo normali (4 pz)", 5.00, "Sfiziosita"));
        prodotti.add(new Prodotto("S7", "Ali di pollo spicy (4 pz)", 6.00, "Sfiziosita"));

        // Bibite - Aumentate di 0.50$
        prodotti.add(new Prodotto("B1", "Acqua Naturale 500ml", 1.50, "Bibita"));
        prodotti.add(new Prodotto("B2", "Acqua Gassata 500ml", 1.50, "Bibita"));
        prodotti.add(new Prodotto("B3", "Coca Cola 330ml", 3.00, "Bibita"));
        prodotti.add(new Prodotto("B4", "Coca Cola Zero 330ml", 4.00, "Bibita"));
        prodotti.add(new Prodotto("B5", "Sprite 330ml", 2.50, "Bibita"));
        prodotti.add(new Prodotto("B6", "Pepsi 330ml", 3.00, "Bibita"));

        // Dolci - Prezzi invariati
        prodotti.add(new Prodotto("D1", "Cookie", 1.00, "Dolce"));
        prodotti.add(new Prodotto("D2", "Ciambella", 1.50, "Dolce"));
        prodotti.add(new Prodotto("D3", "Gelato cono", 3.50, "Dolce"));
        prodotti.add(new Prodotto("D4", "Coppetta Piccola", 1.50, "Dolce"));
        prodotti.add(new Prodotto("D5", "Coppetta Media", 3.00, "Dolce"));
        prodotti.add(new Prodotto("D6", "Coppetta Grande", 4.00, "Dolce"));
    }

    /**
     * Ricerca un prodotto all'interno del catalogo usando il suo codice identificativo.
     *
     * @param idProdotto L'ID univoco del prodotto cercato (es. "M1", "P1").
     * @return L'oggetto {@link Prodotto} corrispondente, oppure {@code null} se non trovato.
     */
    public Prodotto getProdotto(String idProdotto) {
        for (Prodotto p : prodotti) {
            if (p.getIdProdotto().equals(idProdotto)) return p;
        }
        return null; 
    }

    /**
     * Restituisce la lista delle promozioni attualmente configurate e applicabili.
     *
     * @return Una lista di oggetti {@link Promozione}.
     */
    public List<Promozione> getPromozioniAttive() {
        return promozioni;
    }
}
