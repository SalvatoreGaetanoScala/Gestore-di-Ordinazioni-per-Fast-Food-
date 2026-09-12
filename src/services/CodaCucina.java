package services;

import domain.Ordine;
import domain.Personalizzazione;
import domain.StatoOrdine; //  import aggiunto per gestire lo stato come Enum
import domain.VoceOrdine;
import java.util.ArrayList;
import java.util.List;

/**
 * la classe {@code CodaCucina} gestisce la lista degli ordini in coda di elaborazione 
 * per il personale di cucina, monitorandone lo stato corrente.
 */
public class CodaCucina {
    private List<Ordine> ordiniInCoda;
    private List<String> ordiniProntiMostrati;

    /**
     * costruttore della classe {@code CodaCucina}.
     */
    public CodaCucina() {
        this.ordiniInCoda = new ArrayList<>();
        this.ordiniProntiMostrati = new ArrayList<>();
    }

    public void aggiungiOrdine(Ordine o) {
        ordiniInCoda.add(o);
    }

    public Ordine getOrdine(String idOrdine) {
        for (Ordine o : ordiniInCoda) {
            if (o.getIdOrdine().equalsIgnoreCase(idOrdine)) return o;
        }
        return null;
    }

    public String mostraCodaGUI() {
        StringBuilder sb = new StringBuilder("=== CODA CUCINA ===\n\n");
        boolean ciSonoOrdiniAttivi = false;
        
        for (Ordine o : ordiniInCoda) {
            // modifica -> recupero lo stato come Enum
            StatoOrdine statoEnum = o.getStato(); 
            boolean daMostrare = false;
            
            // modifica -> confronto con == grazie all'Enum
            if (statoEnum == StatoOrdine.PRONTO) {
                if (!ordiniProntiMostrati.contains(o.getIdOrdine())) {
                    daMostrare = true;
                    ordiniProntiMostrati.add(o.getIdOrdine());
                }
            } else if (statoEnum != StatoOrdine.RITIRATO) {
                daMostrare = true;
            }

            if (daMostrare) {
                ciSonoOrdiniAttivi = true;
                // modifica -> uso .getDescrizione() per ottenere il testo
                sb.append("ORDINE: ").append(o.getIdOrdine())
                  .append(" | STATO: ").append(statoEnum.getDescrizione().toUpperCase()).append("\n");
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
