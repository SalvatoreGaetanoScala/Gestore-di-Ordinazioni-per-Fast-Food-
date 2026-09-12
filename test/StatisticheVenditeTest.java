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
        // questo SETUP simuliamo una giornata di lavoro con 2 clienti diversi

        //  ##cliente 1 (spende 8.0€) 
        chiosco.iniziaOrdine();

        chiosco.aggiungiProdotto(
            "P1", 
            1, 
            null
        ); 

        chiosco.terminaOrdine();
        chiosco.paga(
            "1234567812345678", 
            "1225", 
            "123"
        );

        //  ##cliente 2 (spende 3.0€) 
        chiosco.iniziaOrdine();
        chiosco.aggiungiProdotto(
            "S1", 
            1, 
            null
        ); // 3.0€


        chiosco.terminaOrdine();
        chiosco.paga
        (
            "1234567812345678", 
            "1225", 
            "123"
        );

        // #EXERCISE: chiediamo al ManagerPanel virtuale di generare il report
        String reportGenerato = chiosco.generaReport();

        // #VERIFY: dobbiamo avere 2 ordini evasi e un incasso totale di 11.0€

        //qui verifico che ho effettivamente 2 ordini evasi 
        assertTrue(
            reportGenerato.contains("Totale ordini evasi: 2"), 
            "Il report deve conteggiare esattamente 2 ordini."
        );
        
        //qui verifico che l'incasso totale dei 2 ordini evasi sia di 11.0€
        assertTrue(
            reportGenerato.contains("Incasso totale netto: €11,00"), 
            "La somma degli incassi deve risultare 11,00€."
        ); 
        // nb: il formattatore %.2f di Java usa la virgola ',' per la localizzazione italiana di default.
    }
}
