package domain;

import java.util.ArrayList;
import java.util.List;

public class Catalogo {
    private List<Prodotto> menu;
    private List<Promozione> promozioniAttive;

    public Catalogo() {
        menu = new ArrayList<>();
        promozioniAttive = new ArrayList<>();
        
        // Inserimento prodotti per i test dell'interfaccia
        menu.add(new Prodotto("M1", "Menu Combo Crispy Chicken", 10.0, "Menu Combo"));
        menu.add(new Prodotto("M2", "Menu Combo American Burger", 12.0, "Menu Combo"));
        menu.add(new Prodotto("M3", "Menu Combo Double BBQ", 15.0, "Menu Combo"));
        menu.add(new Prodotto("M4", "Menu Combo Veggie Burger", 8.0, "Menu Combo"));
        menu.add(new Prodotto("M5", "Menu Combo Crispy Fish", 12.0, "Menu Combo"));
        
        menu.add(new Prodotto("P1", "Crispy Chicken", 8.0, "Panino"));
        menu.add(new Prodotto("P2", "American burger", 10.0, "Panino"));
        menu.add(new Prodotto("P3", "Double BBQ", 12.0, "Panino"));
        menu.add(new Prodotto("P4", "Veggie Burger", 7.0, "Panino"));
        menu.add(new Prodotto("P5", "Crispy Fish", 9.0, "Panino"));
        
        menu.add(new Prodotto("S1", "Patatine Piccole", 3.0, "Sfiziosita"));
        menu.add(new Prodotto("S2", "Patatine Medie", 4.5, "Sfiziosita"));
        menu.add(new Prodotto("S3", "Patatine Grandi", 5.0, "Sfiziosita"));
        menu.add(new Prodotto("S4", "Crocchette 4pz", 3.5, "Sfiziosita"));
        menu.add(new Prodotto("S5", "Crocchette 8pz", 6.0, "Sfiziosita"));
        menu.add(new Prodotto("S6", "Ali 4pz", 5.0, "Sfiziosita"));
        menu.add(new Prodotto("S7", "Ali 8pz", 6.0, "Sfiziosita"));

        menu.add(new Prodotto("B1", "Acqua Naturale", 1.5, "Bibita"));
        menu.add(new Prodotto("B2", "Acqua Gassata", 1.5, "Bibita"));
        menu.add(new Prodotto("B3", "Coca Cola", 3.0, "Bibita"));
        menu.add(new Prodotto("B4", "Coca Zero", 4.0, "Bibita"));
        menu.add(new Prodotto("B5", "Sprite", 2.5, "Bibita"));
        menu.add(new Prodotto("B6", "Pepsi", 3.0, "Bibita"));

        menu.add(new Prodotto("D1", "Cookie", 1.0, "Dolce"));
        menu.add(new Prodotto("D2", "Ciambella", 1.5, "Dolce"));
        menu.add(new Prodotto("D3", "Gelato cono", 3.5, "Dolce"));
        menu.add(new Prodotto("D4", "Coppetta Picc.", 1.5, "Dolce"));
        menu.add(new Prodotto("D5", "Coppetta Med.", 3.0, "Dolce"));
        menu.add(new Prodotto("D6", "Coppetta Gran.", 4.0, "Dolce"));
    }

    public Prodotto getProdotto(String idProdotto) {
        for (Prodotto p : menu) {
            if (p.getIdProdotto().equalsIgnoreCase(idProdotto)) {
                return p;
            }
        }
        return null;
    }

    public List<Promozione> getPromozioniAttive() {
        return promozioniAttive;
    }
}
