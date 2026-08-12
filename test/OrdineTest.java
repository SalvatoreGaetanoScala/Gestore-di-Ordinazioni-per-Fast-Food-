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
        // Esecuzione
        ordine.aggiungiVoce(hamburger, 1, null);
        double totale = ordine.getTotale();

        // Verifica: ci aspettiamo che il totale sia esattamente 5.50
        // (Il terzo parametro 0.01 è la tolleranza per i numeri decimali double)
        assertEquals(5.50, totale, 0.01, "Il totale di un Hamburger Classico deve essere 5.50");
    }

    /**
     * TEST 2: Il calcolo corretto del totale con l'aggiunta di ingredienti extra (sovrapprezzo applicato).
     */
    @Test
    public void testCalcoloTotaleConIngredientiExtra() {
        // Preparazione della personalizzazione
        List<String[]> personalizzazioni = new ArrayList<>();
        // Formato array: [0]=tipo, [1]=ingrediente, [2]=sovrapprezzo
        personalizzazioni.add(new String[]{"Aggiunta", "Bacon", "1.50"});

        // Esecuzione
        ordine.aggiungiVoce(hamburger, 1, personalizzazioni);
        double totale = ordine.getTotale();

        // Verifica: 5.50 (base) + 1.50 (extra) = 7.00
        assertEquals(7.00, totale, 0.01, "Il totale deve includere il sovrapprezzo del Bacon");
    }

    /**
     * TEST 3: Il calcolo corretto del totale con la rimozione di ingredienti standard 
     * (nessuna deduzione di prezzo applicata, in base alla Regola di Business R2).
     */
    @Test
    public void testCalcoloTotaleConRimozioneIngredienti() {
        // Preparazione della personalizzazione (rimozione cipolla a 0 euro)
        List<String[]> personalizzazioni = new ArrayList<>();
        personalizzazioni.add(new String[]{"Rimozione", "Cipolla", "0.00"});

        // Esecuzione
        ordine.aggiungiVoce(hamburger, 1, personalizzazioni);
        double totale = ordine.getTotale();

        // Verifica: il prezzo deve rimanere 5.50 secondo la regola R2
        assertEquals(5.50, totale, 0.01, "Rimuovere ingredienti non deve diminuire il prezzo base");
    }

    /**
     * TEST 4: Il calcolo corretto del totale quando un carrello soddisfa i requisiti di una promozione.
     */
    @Test
    public void testApplicazionePromozioneScontoPercentuale() {
        // Preparazione
        ordine.aggiungiVoce(hamburger, 1, null); // 5.50
        List<Promozione> promozioniAttive = new ArrayList<>();
        promozioniAttive.add(new Promozione("PR1", "Sconto 10%", "PERCENTUALE", 10.0));

        // Esecuzione
        ordine.applicaPromozione(promozioniAttive);
        double totale = ordine.getTotale();

        // Verifica: il 10% di 5.50 è 0.55. Quindi 5.50 - 0.55 = 4.95
        assertEquals(4.95, totale, 0.01, "Lo sconto percentuale del 10% deve essere applicato correttamente");
    }

    /**
     * TEST 5: L'impossibilita' da parte della cucina di segnare come "Pronto" 
     * un ordine che non sia prima in stato "In Preparazione".
     */
    @Test
    public void testImpossibilitaSegnareProntoSeNonInPreparazione() {
        // Utilizziamo il Chiosco (Controller) per testare il flusso reale
        Chiosco controller = new Chiosco();
        controller.iniziaOrdine();
        controller.aggiungiProdotto("P1", 1, null);
        controller.terminaOrdine();
        
        // Pagamento andato a buon fine, l'ordine viene creato e il suo stato passa a "Ricevuto"
        String esitoPagamento = controller.paga("1234567812345678", "1226", "123"); 
        
        // Estraiamo dinamicamente l'ID generato dal sistema dal messaggio di ricevuta
        // Il messaggio è: "Pagamento approvato!\nIl tuo numero d'ordine e': XX"
        String[] splitMessaggio = esitoPagamento.split(": ");
        String idOrdine = splitMessaggio[1].trim();
        
        // L'ordine e' attualmente "Ricevuto". 
        // Tentiamo di forzarne lo stato a "Pronto" saltando "In Preparazione"
        String esitoPronto = controller.segnaPronto(idOrdine);
        
        // Verifichiamo che il controller blocchi l'azione con il messaggio di errore previsto
        assertEquals("Impossibile segnare pronto (Non trovato o stato errato).", esitoPronto, 
                "Il sistema deve impedire la transizione a 'Pronto' se l'ordine non è 'In Preparazione'");
    }
}
