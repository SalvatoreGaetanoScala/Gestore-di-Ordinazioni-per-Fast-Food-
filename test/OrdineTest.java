package test;

import controller.Chiosco;
import domain.Ordine;
import domain.Prodotto;
import domain.Promozione;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrdineTest {

    private Ordine ordine;
    private Prodotto hamburger;

    // L'annotazione @BeforeEach esegue questo metodo prima di OGNI singolo test.
    // Serve per avere sempre un "foglio bianco" su cui fare le prove.
    @BeforeEach
    public void setUp() {
        ordine = new Ordine();
        hamburger = new Prodotto("P1", "Hamburger Classico", 5.50, "Panini");
    }

    /**
     * TEST 1: Il calcolo corretto del totale con un solo prodotto senza personalizzazioni.
     */
    @Test
    public void testCalcoloTotaleProdottoSingolo() {
        ordine.aggiungiVoce(hamburger, 1, null);
        double totale = ordine.getTotale();
        assertEquals(5.50, totale, 0.01, "Il totale di un Hamburger Classico deve essere 5.50");
    }

    /**
     * TEST 2: Il calcolo corretto del totale con l'aggiunta di ingredienti extra (sovrapprezzo applicato).
     */
    @Test
    public void testCalcoloTotaleConIngredientiExtra() {
        List<String[]> personalizzazioni = new ArrayList<>();
        // Formato array: [0]=tipo, [1]=ingrediente, [2]=sovrapprezzo
        personalizzazioni.add(new String[]{"Aggiunta", "Bacon", "1.50"});

        ordine.aggiungiVoce(hamburger, 1, personalizzazioni);
        double totale = ordine.getTotale();

        assertEquals(7.00, totale, 0.01, "Il totale deve includere il sovrapprezzo del Bacon");
    }

    /**
     * TEST 3: Il calcolo corretto del totale con la rimozione di ingredienti standard 
     * (nessuna deduzione di prezzo applicata, in base alla Regola di Business R2).
     */
    @Test
    public void testCalcoloTotaleConRimozioneIngredienti() {
        List<String[]> personalizzazioni = new ArrayList<>();
        personalizzazioni.add(new String[]{"Rimozione", "Cipolla", "0.00"});

        ordine.aggiungiVoce(hamburger, 1, personalizzazioni);
        double totale = ordine.getTotale();

        assertEquals(5.50, totale, 0.01, "Rimuovere ingredienti non deve diminuire il prezzo base");
    }

    /**
     * TEST 4: Il calcolo corretto del totale quando un carrello soddisfa i requisiti di una promozione.
     */
    @Test
    public void testApplicazionePromozioneScontoPercentuale() {
        ordine.aggiungiVoce(hamburger, 1, null); // 5.50
        List<Promozione> promozioniAttive = new ArrayList<>();
        promozioniAttive.add(new Promozione("PR1", "Sconto 10%", "PERCENTUALE", 10.0));

        ordine.applicaPromozione(promozioniAttive);
        double totale = ordine.getTotale();

        assertEquals(4.95, totale, 0.01, "Lo sconto percentuale del 10% deve essere applicato correttamente");
    }

    /**
     * TEST 5: L'impossibilita' da parte della cucina di segnare come "Pronto" 
     * un ordine che non sia prima in stato "In Preparazione".
     */
    @Test
    public void testImpossibilitaSegnareProntoSeNonInPreparazione() {
        // Utilizziamo il Chiosco (Controller) per testare il flusso reale di interazione
        Chiosco controller = new Chiosco();
        controller.iniziaOrdine();
        controller.aggiungiProdotto("P1", 1, null);
        controller.terminaOrdine();
        
        // Pagamento andato a buon fine, l'ordine viene creato e il suo stato passa a "Ricevuto"
        String esitoPagamento = controller.paga("1234567812345678", "1226", "123"); 
        
        // Estraiamo dinamicamente l'ID generato dal sistema dall'ultima parola del messaggio
        String idOrdine = esitoPagamento.substring(esitoPagamento.lastIndexOf(" ") + 1).trim();
        
        // L'ordine e' attualmente "Ricevuto". 
        // Tentiamo di forzarne lo stato a "Pronto" saltando "In Preparazione"
        controller.segnaPronto(idOrdine);
        
        // Verifichiamo che il controller abbia bloccato l'azione controllando la coda
        String codaAttuale = controller.visualizzaCodaOrdini();
        
        assertTrue(codaAttuale.contains("STATO: RICEVUTO"), 
                "L'ordine deve rimanere in stato Ricevuto e non avanzare");
        assertFalse(codaAttuale.contains("STATO: PRONTO"), 
                "Il sistema deve impedire la transizione a 'Pronto' se l'ordine non è 'In Preparazione'");
    }
}
