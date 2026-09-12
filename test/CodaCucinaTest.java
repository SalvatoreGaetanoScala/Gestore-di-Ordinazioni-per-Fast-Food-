package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import domain.Ordine;
import services.CodaCucina;

class CodaCucinaTest {

    private CodaCucina codaCucina;

    @BeforeEach
    void setUp() {
        codaCucina = new CodaCucina();
    }

    @Test
    void testGetOrdineConIdInesistente() {
        // #EXERCISE: Tentiamo di recuperare un ordine inesistente dalla coda
        Ordine ordineTrovato = codaCucina.getOrdine("A999");

        // #VERIFY: L'applicazione non deve lanciare eccezioni ma restituire null
        assertNull(
            ordineTrovato, 
            "La ricerca di un ID ordine non presente in coda cucina deve restituire null."
        );
    }
}
