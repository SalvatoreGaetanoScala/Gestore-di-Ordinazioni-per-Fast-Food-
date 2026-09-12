package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import domain.Prodotto;
import domain.VoceOrdine;

class PersonalizzazioneTest {

    //NIENTE BEFORE EACH PERCHÉ FACCIO 2 TEST (1 CON QTÀ 1 E 1 CON QTÀ 2) 
    // E NON VOGLIO RICREARE OGNI VOLTA IL PRODOTTO
    
    @Test
    void testSubtotaleConAggiuntaExtraESovrapprezzo() {
        // #SETUP: recuperiamo un prodotto base (es. Panino Crispy Chicken a 8.0€)
        Prodotto prodotto = new Prodotto(
            "P1",
             "Crispy Chicken", 
             8.0, 
             "Panino"
        );

        VoceOrdine voce = new VoceOrdine(prodotto, 1);

        // #EXERCISE: aggiungo una personalizzazione con sovrapprezzo (es. Extra Bacon +1.5€)
        voce.aggiungiPersonalizzazione("Aggiunta", "Extra Bacon", 1.5);

        // #VERIFY: Il subtotale deve essere (8.0 + 1.5) * 1 = 9.5€
        assertEquals(
            9.5, 
            voce.getSubTotale(), 
            0.001, // delta per confronto double
            "Il subtotale della voce deve includere correttamente il sovrapprezzo dell'ingrediente extra."
        );
    }

    @Test
    void testSubtotaleConRimozioneEQuantitaMultipla() {

        // #SETUP: creo un prodotto e impostiamo quantità 2
        Prodotto prodotto = new Prodotto(
            "P2", 
            "American burger", 
            10.0, 
            "Panino"
        );

        VoceOrdine voce = new VoceOrdine(prodotto, 2);

        // #EXERCISE: Aggiungiamo una rimozione (costo 0.0) 
        // e un upgrade (es. Patatine Medie +1.0)
        voce.aggiungiPersonalizzazione("Rimozione", "Cetriolini", 0.0);
        voce.aggiungiPersonalizzazione("Upgrade Patatine", "Medie", 1.0);

        // #VERIFY: Prezzo base (10.0) + sovrapprezzi (0.0 + 1.0) * quantità (2) = 22.0€
        assertEquals(
            22.0, 
            voce.getSubTotale(), 
            0.001, 
            "Il subtotale deve calcolare correttamente prezzo base, variazioni e quantità multipla."
        );
    }
}
