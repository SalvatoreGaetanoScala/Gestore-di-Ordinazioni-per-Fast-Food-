package domain;

public enum StatoOrdine {
    IN_CREAZIONE("In Creazione"),
    RICEVUTO("Ricevuto"),
    IN_PREPARAZIONE("In Preparazione"),
    PRONTO("Pronto"),
    RITIRATO("Ritirato");

    private final String descrizione;

    // costruttore dell'enum
    StatoOrdine(String descrizione) {
        this.descrizione = descrizione;
    }

    // metodo per recuperare il testo da mostrare nella GUI
    public String getDescrizione() {
        return descrizione;
    }
}
