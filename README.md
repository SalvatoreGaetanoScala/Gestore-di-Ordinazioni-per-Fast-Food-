# 🍔 Gestore di Ordinazioni per Fast Food

## 📖 Descrizione del Progetto
Il sistema ha l'obiettivo di abbattere i tempi di attesa in coda alle casse fisiche di un fast food, demandando la fase di scelta e pagamento al cliente tramite chioschi self-service[span_1](start_span)[span_1](end_span). Il software offre una soluzione integrata che copre l'intero ciclo di vita di un'ordinazione, dalla composizione del menu lato cliente fino alla gestione della coda di preparazione lato cucina[span_2](start_span)[span_2](end_span).

## ✨ Funzionalità Principali
*   🖥️ **Chiosco Self-Service (Lato Cliente):** Consultazione del catalogo digitale diviso per categorie, personalizzazione dei prodotti (aggiunta/rimozione ingredienti), gestione del carrello e pagamento elettronico simulato[span_3](start_span)[span_3](end_span).
*   👨‍🍳 **Back-Office Cucina (Lato Staff):** Terminale per la visualizzazione in tempo reale degli ordini ricevuti e gestione degli stati di avanzamento (*Ricevuto -> In Preparazione -> Pronto*)[span_4](start_span)[span_4](end_span).
*   📺 **Monitor di Sala:** Sistema simulato per notificare visivamente e acusticamente i clienti quando il loro vassoio è pronto per il ritiro[span_5](start_span)[span_5](end_span).
*   💰 **Motore di Promozioni:** Calcolo dinamico dei prezzi basato su regole di business rigorose e applicazione di sconti (es. Menu Combo, sconti percentuali)[span_6](start_span)[span_6](end_span).

## 🛠️ Architettura e Tecnologie
*   **Linguaggio:** Java
*   **Interfaccia Grafica:** Java Swing (con componenti custom arrotondati e simulazione gesture touch)
*   **Architettura:** Pattern architetturali **GRASP** (Controller, Creator, Information Expert) documentati e applicati rigorosamente durante la fase di progettazione[span_7](start_span)[span_7](end_span).
*   **Testing:** **JUnit** per il collaudo funzionale (Black Box) delle logiche di calcolo del dominio e dei cambi di stato[span_8](start_span)[span_8](end_span)[span_9](start_span)[span_9](end_span).
*   **Modellazione UML:** Astah Professional[span_10](start_span)[span_10](end_span).

## 📂 Struttura del Repository
*   `src/main/`: Contiene le viste grafiche (ClientPanel, KitchenPanel, ecc.) e il punto di ingresso dell'applicazione (MainGUI).
*   `src/controller/`: Contiene il Facade Controller principale (`Chiosco.java`) che orchestra le operazioni del sistema[span_11](start_span)[span_11](end_span).
*   `src/domain/`: Contiene le classi di dominio del problema (Ordine, Prodotto, Personalizzazione, ecc.)[span_12](start_span)[span_12](end_span)[span_13](start_span)[span_13](end_span).
*   `src/services/`: Moduli per la gestione dei dati in memoria (Catalogo, CodaCucina) e simulazione di servizi esterni.
*   `src/test/`: Suite di test automatizzati scritti in JUnit[span_14](start_span)[span_14](end_span).
*   `documentation/`: Contiene la documentazione in PDF e i sorgenti dei diagrammi UML relativi alle fasi di Ideazione ed Elaborazione.

## 🚀 Come Avviare l'Applicazione
1. Clonare il repository sul proprio computer locale:
   ```bash
   git clone [https://github.com/TUO-USERNAME/Gestore-Ordinazioni-FastFood.git](https://github.com/TUO-USERNAME/Gestore-Ordinazioni-FastFood.git)
