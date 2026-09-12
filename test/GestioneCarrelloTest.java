package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import controller.Chiosco;

/**
 * classe di test per verificare le dinamiche del carrello virtuale (UC1).
 */
class GestioneCarrelloTest {

    private Chiosco chiosco; // il nostro System Under Test (SUT)

    /**
     * @BeforeEach viene eseguito prima di OGNI @Test.
     * Serve a creare una condizione iniziale pulita (Setup del Lifecycle).
     */
    @BeforeEach
    void setUp() {
        chiosco = new Chiosco();
        chiosco.iniziaOrdine();
    }

    @Test
    void testAggiuntaProdottiAlCarrello() {

        // il catalogo è in domain\Catalogo.java 

        // #EXERCISE: Aggiungo un Crispy Chicken (ID "P1", prezzo 8.0)  
        // e Patatine (ID "S1", prezzo 3.0)

        chiosco.aggiungiProdotto("P1", 1, null);
        chiosco.aggiungiProdotto("S1", 1, null);

        // #VERIFY: Verifico che ci siano 2 voci e che il totale sia 11.0
        // JUnit 5: assertEquals(valore_atteso, valore_effettivo, messaggio_in_caso_di_errore)
        

        // Verifico che il numero di voci nel carrello sia 2
        assertEquals(
            2, 
            chiosco.getNumeroVociCorrenti(), 
            "Il carrello dovrebbe contenere 2 prodotti."
        );
        
        // Verifico che il totale dell'ordine corrente sia 11.0€
        assertEquals(
            11.0, 
            chiosco.getTotaleOrdineCorrente(), 
            0.001, 
            "Il totale dovrebbe essere 11.0€"
        );
    }

    @Test
    void testRimozioneElementiDalCarrello() {
        // #SETUP: Inserisco due prodotti (indice 0 e indice 1)

        // P1 -> PANINO CRISPY CHICKEN (8.0€)
        // S1 -> PATATINE PICCOLE (3.0€)
        chiosco.aggiungiProdotto("P1", 1, null); // 8.0€
        chiosco.aggiungiProdotto("S1", 1, null); // 3.0€

        // #EXERCISE: Rimuovo il primo elemento (il panino all'indice 0)
        chiosco.rimuoviVoce(0);

        // VERIFY: Il totale deve essersi ricalcolato in automatico a 3.0€ (solo patatine)
        
        // qui si verifica che il numero di voci nel carrello sia 1, ossia le patatine 
        assertEquals(
            1, 
            chiosco.getNumeroVociCorrenti(), 
            "Deve rimanere una sola voce nel carrello."
        );

        // qui si verifica che il totale dell'ordine corrente sia 3.0€
        assertEquals(
            3.0, 
            chiosco.getTotaleOrdineCorrente(), 
            0.001, 
            "Il totale deve essere sceso a 3.0€"
        );
    }

    @Test
    void testModificaQuantitaElemento() {
        // #SETUP: Inserisco un panino (quantità 1) -> totale = 8.0€
        chiosco.aggiungiProdotto("P1", 1, null);

        // #EXERCISE: Aggiorno la quantità della voce all'indice 0 portandola a 3
        chiosco.aggiornaQuantitaVoce(0, 3);

        // #VERIFY: 8.0€ * 3 = 24.0€
        

        assertEquals(
            24.0, 
            chiosco.getTotaleOrdineCorrente(), 
            0.001, //delta (margine d'errore)
            "Il totale deve riflettere l'aggiornamento della quantità (8.0 * 3)."
        );
    }
}
