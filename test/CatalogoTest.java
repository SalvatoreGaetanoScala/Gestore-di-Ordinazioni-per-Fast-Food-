package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import domain.Catalogo;
import domain.Prodotto;

class CatalogoTest {

    private Catalogo catalogo;

    @BeforeEach
    void setUp() {
        catalogo = new Catalogo();
    }

    @Test
    void testRicercaProdottoIdValidoECategoria() {
        // #EXERCISE: recupero un prodotto esistente dal catalogo (es. "P1")
        Prodotto prodotto = catalogo.getProdotto("P1");

        // #VERIFY: il prodotto esiste e appartiene alla categoria corretta "Panino"
        assertNotNull(
            prodotto, 
            "Il prodotto con ID P1 deve esistere nel catalogo."
        );

        assertEquals(
            "Crispy Chicken", 
            prodotto.getNome(), 
            "Il nome del prodotto deve corrispondere."
        );

        assertEquals(
            "Panino", 
            prodotto.getCategoria(), 
            "La categoria del prodotto deve essere 'Panino'."
        );
    }

    @Test
    void testRicercaProdottoIdInesistente() {
        // #EXERCISE: adesso cerco un codice non presente nel listino
        Prodotto prodotto = catalogo.getProdotto("INVALID_ID");

        // #VERIFY: Il metodo deve restituire null in modo sicuro
        assertNull(
            prodotto, 
            "La ricerca di un ID inesistente nel catalogo deve restituire null."
        );
    }
}
