package services;

import domain.Ordine;
import domain.Personalizzazione;
import domain.VoceOrdine;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe {@code CodaCucina} gestisce la lista degli ordini in coda di elaborazione 
 * per il personale di cucina, monitorandone lo stato corrente.
 */
public class CodaCucina {
    private List<Ordine> ordiniInCoda;
    private List<String> ordiniProntiMostrati;

    /**
     * Costruttore della classe {@code CodaCucina}.
     * Inizializza le liste per gli ordini in coda e per quelli pronti già tracciati.
     */
    public CodaCucina() {
        this.ordiniInCoda = new ArrayList<>();
        this.ordiniProntiMostrati = new ArrayList<>();
    }

    /**
     * Inserisce un nuovo ordine all'interno della coda di cucina.
     *
     * @param o L'oggetto {@link Ordine} da aggiungere.
     */
    public void aggiungiOrdine(Ordine o) {
        ordiniInCoda.add(o);
    }

    /**
     * Cerca e restituisce un ordine in base al suo identificativo alfanumerico.
     *
     * @param idOrdine L'ID dell'ordine da ricercare.
     * @return L'oggetto {@link Ordine} corrispondente, oppure {@code null} se non viene trovato.
     */
    public Ordine getOrdine(String idOrdine) {
        for (Ordine o : ordiniInCoda) {
            if (o.getIdOrdine().equalsIgnoreCase(idOrdine)) return o;
        }
        return null;
    }

    /**
     * Genera una rappresentazione testuale formattata della coda ordini corrente,
     * adatta per essere mostrata nell'interfaccia grafica del back-office.
     * Mostra tutti i dettagli di preparazione per il personale.
     *
     * @return Una stringa contenente l'elenco degli ordini attivi e dei relativi dettagli.
     */
    public String mostraCodaGUI() {
        StringBuilder sb = new StringBuilder("=== CODA CUCINA ===\n\n");
        boolean ciSonoOrdiniAttivi = false;
        
        for (Ordine o : ordiniInCoda) {
            String stato = o.getStato();
            boolean daMostrare = false;
            
            if (stato.equals("Pronto")) {
                if (!ordiniProntiMostrati.contains(o.getIdOrdine())) {
                    daMostrare = true;
                    ordiniProntiMostrati.add(o.getIdOrdine());
                }
            } else if (!stato.equals("Ritirato")) {
                daMostrare = true;
            }

            if (daMostrare) {
                ciSonoOrdiniAttivi = true;
                sb.append("ORDINE: ").append(o.getIdOrdine())
                  .append(" | STATO: ").append(stato.toUpperCase()).append("\n");
                sb.append("--------------------------------------------------\n");

                for (VoceOrdine vo : o.getVoci()) {
                    sb.append(vo.getQuantita()).append("x ").append(vo.getProdotto().getNome()).append("\n");

                    String cat = vo.getProdotto().getCategoria();
                    String nomeProd = vo.getProdotto().getNome().toLowerCase();
                    
                    boolean isCrispy = nomeProd.contains("crispy chicken");
                    boolean isAmerican = nomeProd.contains("american burger");
                    boolean isDoubleBbq = nomeProd.contains("double bbq");
                    boolean isVeggie = nomeProd.contains("veggie burger");
                    boolean isFish = nomeProd.contains("crispy fish");

                    List<String> righeDescrittive = new ArrayList<>();
                    String bibita = null;
                    String patatine = null;
                    
                    List<String> ingredientiPanino = new ArrayList<>();
                    if (isCrispy) {
                        ingredientiPanino.add("cotoletta di pollo");
                        ingredientiPanino.add("cheddar");
                        ingredientiPanino.add("insalata");
                        ingredientiPanino.add("salsa barbecue");
                        ingredientiPanino.add("salsa crispy");
                    } else if (isAmerican) {
                        ingredientiPanino.add("doppio hamburgher");
                        ingredientiPanino.add("cheddar");
                        ingredientiPanino.add("insalata");
                        ingredientiPanino.add("cetriolini");
                    } else if (isDoubleBbq) {
                        ingredientiPanino.add("doppio hamburgher");
                        ingredientiPanino.add("doppio cheddar");
                        ingredientiPanino.add("cetriolini");
                        ingredientiPanino.add("bacon");
                        ingredientiPanino.add("salsa crispy");
                    } else if (isVeggie) {
                        ingredientiPanino.add("hamburgher di ceci");
                        ingredientiPanino.add("doppia insalata");
                        ingredientiPanino.add("salsa hummus");
                    } else if (isFish) {
                        ingredientiPanino.add("filetto di pesce fritto");
                        ingredientiPanino.add("cheddar");
                        ingredientiPanino.add("maionese");
                    }

                    for(Personalizzazione pers : vo.getPersonalizzazioni()){
                        if (pers.getTipo().equals("Bibita Combo")) {
                            bibita = pers.getIngrediente();
                        } else if (pers.getTipo().equals("Upgrade Patatine")) {
                            patatine = "Patatine " + pers.getIngrediente();
                        } else if (pers.getTipo().equals("Rimozione")) {
                            ingredientiPanino.removeIf(ing -> ing.equalsIgnoreCase(pers.getIngrediente()));
                        } else if (pers.getTipo().equals("Aggiunta")) {
                            ingredientiPanino.add(pers.getIngrediente().toLowerCase());
                        }
                    }

                    if (cat.equals("Menu Combo")) {
                        if (bibita != null) {
                            String b = bibita.equalsIgnoreCase("Acqua Naturale") ? "acqua" : bibita.toLowerCase();
                            righeDescrittive.add(b);
                        }
                        if (patatine != null) {
                            righeDescrittive.add(patatine.toLowerCase());
                        }
                    }

                    if (isCrispy) {
                        righeDescrittive.add("panino crispy chicken con: " + String.join(", ", ingredientiPanino));
                    } else if (isAmerican) {
                        righeDescrittive.add("panino american burger con: " + String.join(", ", ingredientiPanino));
                    } else if (isDoubleBbq) {
                        righeDescrittive.add("panino double bbq con: " + String.join(", ", ingredientiPanino));
                    } else if (isVeggie) {
                        righeDescrittive.add("panino veggie burger con: " + String.join(", ", ingredientiPanino));
                    } else if (isFish) {
                        righeDescrittive.add("panino crispy fish con: " + String.join(", ", ingredientiPanino));
                    } else {
                        for(Personalizzazione pers : vo.getPersonalizzazioni()){
                            if (!pers.getTipo().equals("Bibita Combo") && !pers.getTipo().equals("Upgrade Patatine")) {
                                righeDescrittive.add(pers.getTipo().toLowerCase() + " " + pers.getIngrediente().toLowerCase());
                            }
                        }
                    }

                    for (String riga : righeDescrittive) {
                        sb.append("   - ").append(riga).append("\n");
                    }
                    sb.append("\n");
                }
                sb.append("==================================================\n\n");
            }
        }
        
        if (!ciSonoOrdiniAttivi) {
            sb.append("Nessun ordine attivo da preparare.\n");
        }
        return sb.toString();
    }
}
