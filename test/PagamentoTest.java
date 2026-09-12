package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import controller.Chiosco;

/**
 * classe di test per verificare la validazione del pagamento simulato.
 */
class PagamentoTest {

    private Chiosco chiosco;

    @BeforeEach
    void setUp()
    {
        chiosco = new Chiosco();
        chiosco.iniziaOrdine();
        
        //aggiungiamo un prodotto per poter pagare
        chiosco.aggiungiProdotto
        (
            "P1", 
            1, 
            null
        ); 
    }

    @Test
    void testPagamentoConDatiValidi() {

        // #EXERCISE: Simuliamo la fine dell'ordine e il pagamento con dati validi 
        // (ovvero 16 cifre carta, 4 cifre scadenza, 3 cifre cvv)
        chiosco.terminaOrdine();

        String esito = chiosco.paga(
            "1234567812345678", 
            "1225", 
            "123"
        );

        // #VERIFY: uso l'assertTrue per verifica che una condizione booleana sia vera.
        // Se il pagamento va a buon fine, il chiosco restituisce una stringa contenente "Autorizzato".
        assertTrue
        (
            esito.contains("Autorizzato"), 
            "Il pagamento con dati validi deve essere autorizzato."
        );
    }

    @Test
    void testPagamentoRifiutatoConDatiErrati() {

        // #EXERCISE: usiamo una carta troppo corta (solo 4 cifre)
        chiosco.terminaOrdine();
        String esito = chiosco.paga(
            "1234", 
            "1225", 
            "123"
        );

        // #VERIFY: Deve restituire un errore
        assertTrue
        (
            esito.contains("Rifiutata"), 
            "Il pagamento con carta di lunghezza errata deve essere rifiutato."
        );
    }
}
