package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import controller.Chiosco;

/**
 * classe di test per la simulazione delle statistiche e del Report Vendite (UC5).
 */
class StatisticheVenditeTest {

    private Chiosco chiosco;

    @BeforeEach
    void setUp() {
        chiosco = new Chiosco();
    }

    @Test
    void testGenerazioneReportGiornaliero() {
        // SETUP simuliamo una giornata di lavoro con 2 clienti diversi

        // ##cliente 1 (aggiunge prodotto da 8.0€) 
        chiosco.iniziaOrdine();

        chiosco.aggiungiProdotto(
            "P1", 
            1, 
            null
        ); 

        // Con terminaOrdine() viene applicato lo sconto del 10%: 8.0€ -> 7.20€
        chiosco.terminaOrdine();
        chiosco.paga(
            "1234567812345678", 
            "1225", 
            "123"
        );

        // ##cliente 2 (aggiunge prodotto da 3.0€) 
        chiosco.iniziaOrdine();
        chiosco.aggiungiProdotto(
            "S1", 
            1, 
            null
        ); 

        // Con terminaOrdine() viene applicato lo sconto del 10%: 3.0€ -> 2.70€
        chiosco.terminaOrdine();
        chiosco.paga(
            "1234567812345678", 
            "1225", 
            "123"
        );

        // #EXERCISE: chiediamo al ManagerPanel virtuale di generare il report
        String reportGenerato = chiosco.generaReport();

        // #VERIFY: dobbiamo avere 2 ordini evasi e un incasso totale di 9.90€ (7.20€ + 2.70€)

        // Verifico che ho effettivamente 2 ordini evasi 
        assertTrue(
            reportGenerato.contains("Totale ordini evasi: 2"), 
            "Il report deve conteggiare esattamente 2 ordini."
        );
        
        // Verifico che l'incasso totale netto (già scontato del 10%) sia 9,90€
        assertTrue(
            reportGenerato.contains("Incasso totale netto: €9,90"), 
            "La somma degli incassi deve risultare 9,90€ a causa dell'applicazione dello sconto del 10%."
        ); 
    }
}
