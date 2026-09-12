package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import controller.Chiosco;

/**
 * Classe di test per verificare le transizioni di stato dell'ordine (UC2).
 */
class GestioneStatiOrdineTest {

    private Chiosco chiosco;

    @BeforeEach
    void setUp() {
        chiosco = new Chiosco();
    }

    @Test
    void testAvanzamentoStatoOrdine() {


        // #SETUP: Creiamo e paghiamo un ordine
        chiosco.iniziaOrdine();
        chiosco.aggiungiProdotto("P1", 1, null);
        chiosco.terminaOrdine();
        
        // Effettuiamo il pagamento ed estrapoliamo l'ID assegnato al volo dalla stringa di risposta
        String rispostaPagamento = chiosco.paga
        (
            "1234567812345678", 
            "1225", 
            "123"
        );

        String[] parti = rispostaPagamento.split(": ");
        String idOrdine = parti[1].trim(); // Es: "A1"

        // #VERIFY INIZIALE: Appena pagato, lo stato di default nella Coda Cucina 
        // deve essere RICEVUTO
        String vistaCoda = chiosco.visualizzaCodaOrdini();

        assertTrue(
            vistaCoda.contains("STATO: RICEVUTO"), 
            "L'ordine appena pagato deve risultare RICEVUTO." // il messaggio appare in caso di fallimento
        );

        // #EXERCISE 1: La cucina prende in carico l'ordine
        chiosco.prendiInCarico(idOrdine);
        
        // #VERIFY 1: Controlliamo che l'output della cucina riporti il nuovo stato
        vistaCoda = chiosco.visualizzaCodaOrdini();
        assertTrue
        (
            vistaCoda.contains("STATO: IN PREPARAZIONE"), 
            "Dopo la presa in carico, lo stato deve essere IN PREPARAZIONE."
        );

        // #EXERCISE 2: La cucina segna l'ordine come pronto
        chiosco.segnaPronto(idOrdine);

        // #VERIFY 2
        vistaCoda = chiosco.visualizzaCodaOrdini();
        assertTrue(
            vistaCoda.contains("STATO: PRONTO"), 
            "Dopo aver cliccato 'Pronto', lo stato deve aggiornarsi a PRONTO."
        );
    }
}
