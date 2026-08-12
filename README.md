# 🍔 Gestore di Ordinazioni per Fast Food

## 📖 Descrizione del Progetto
Il sistema ha l'obiettivo di abbattere i tempi di attesa in coda alle casse fisiche di un fast food, demandando la fase di scelta e pagamento al cliente tramite chioschi self-service. Il software offre una soluzione integrata che copre l'intero ciclo di vita di un'ordinazione, dalla composizione del menu lato cliente fino alla gestione della coda di preparazione lato cucina.

## ✨ Funzionalità Principali
*   🖥️ **Chiosco Self-Service (Lato Cliente):** Consultazione del catalogo digitale diviso per categorie, personalizzazione dei prodotti (aggiunta/rimozione ingredienti), gestione del carrello e pagamento elettronico simulato.
*   👨‍🍳 **Back-Office Cucina (Lato Staff):** Terminale per la visualizzazione in tempo reale degli ordini ricevuti e gestione degli stati di avanzamento (*Ricevuto -> In Preparazione -> Pronto*).
*   📺 **Monitor di Sala:** Sistema simulato per notificare visivamente e acusticamente i clienti quando il loro vassoio è pronto per il ritiro.
*   💰 **Motore di Promozioni:** Calcolo dinamico dei prezzi basato su regole di business rigorose e applicazione di sconti (es. Menu Combo, sconti percentuali).

## 🛠️ Architettura e Tecnologie
*   **Linguaggio:** Java
*   **Interfaccia Grafica:** Java Swing (con componenti custom arrotondati e simulazione gesture touch)
*   **Architettura:** Pattern architetturali **GRASP** (Controller, Creator, Information Expert) documentati e applicati rigorosamente durante la fase di progettazione.
*   **Testing:** **JUnit** per il collaudo funzionale (Black Box) delle logiche di calcolo del dominio e dei cambi di stato.
*   **Modellazione UML:** Astah Professional.

## 📂 Struttura del Repository
*   `src/main/`: Contiene le viste grafiche (ClientPanel, KitchenPanel, ecc.) e il punto di ingresso dell'applicazione (MainGUI).
*   `src/controller/`: Contiene il Facade Controller principale (`Chiosco.java`) che orchestra le operazioni del sistema.
*   `src/domain/`: Contiene le classi di dominio del problema (Ordine, Prodotto, Personalizzazione, ecc.).
*   `src/services/`: Moduli per la gestione dei dati in memoria (Catalogo, CodaCucina) e simulazione di servizi esterni.
*   `src/test/`: Suite di test automatizzati scritti in JUnit.
*   `documentazione/`: Contiene la documentazione in PDF, i sorgenti e le foto dei diagrammi UML relativi alle fasi di Ideazione ed Elaborazione.
*   `documentazione completa/`: Contiene la documentazione completa in PDF e i sorgenti  e le fotodei diagrammi UML relativi a tutte le fasi del progetto.
*   `immagini`: contiene le varie foto dei menù e dei relativi prodotti dell'applicazione.
    


## 🚀 Come Avviare l'Applicazione
1. Clonare il repository sul proprio computer locale:
   ```bash
   git clone [https://github.com/TUO-USERNAME/Gestore-Ordinazioni-FastFood.git](https://github.com/TUO-USERNAME/Gestore-Ordinazioni-FastFood.git)
