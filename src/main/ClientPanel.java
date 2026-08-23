package main;

import controller.Chiosco;
import domain.Personalizzazione;
import domain.VoceOrdine;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClientPanel extends JPanel {
    
    // Aggiunto per risolvere l'avviso "serial" in modo pulito
    private static final long serialVersionUID = 1L;
    
    private Chiosco chiosco;
    private Runnable onOrderCompleted; 
    
    private CardLayout cardLayout;
    private JPanel mainCards;
    
    private RoundedButton btnMenuCompleto;
    private RoundedButton btnPanini;
    private RoundedButton btnSfiziosita;
    private RoundedButton btnBibite;
    private RoundedButton btnDolci;

    private JPanel footerPanel;
    private JLabel lblTestoOrdine;
    private JLabel lblPrezzoTotale;
    private RoundedButton btnAvantiPaga;
    private RoundedButton btnIndietro;
    private BadgePanel badgePanel;

    private String tempComboId;
    private String tempComboDrink;
    private String currentView = "Blank";

    private final Color COLOR_BG = new Color(245, 245, 245);
    private final Color COLOR_RED = new Color(218, 41, 28);
    private final Color COLOR_YELLOW = new Color(255, 199, 44);
    private final Color COLOR_GREEN = new Color(34, 139, 34);
    private final Color COLOR_DARK = new Color(40, 40, 40);

    private JPanel panelSummaryContent;
    private JPanel pannelloModificaContainer;

    private JTextField txtCarta, txtScadenza, txtCVV;
    private String rawCarta = "", rawScadenza = "", rawCVV = "";

    public ClientPanel(Chiosco chiosco, Runnable onOrderCompleted) {
        this.chiosco = chiosco;
        this.onOrderCompleted = onOrderCompleted;

        setLayout(new BorderLayout(15, 15));
        setBackground(COLOR_BG);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        // --- SIDEBAR ---
        JPanel sidebar = new JPanel(new GridLayout(5, 1, 10, 15));
        sidebar.setBackground(COLOR_BG);
        sidebar.setPreferredSize(new Dimension(250, 0));

        btnMenuCompleto = new RoundedButton("<html><center>MENU' COMBO<br>(PROMOZIONI)</center></html>", COLOR_YELLOW, Color.BLACK, 20);
        btnPanini = new RoundedButton("PANINI", COLOR_YELLOW, Color.BLACK, 20);
        btnSfiziosita = new RoundedButton("SFIZIOSITA'", COLOR_YELLOW, Color.BLACK, 20);
        btnBibite = new RoundedButton("BIBITE", COLOR_YELLOW, Color.BLACK, 20);
        btnDolci = new RoundedButton("DOLCI", COLOR_YELLOW, Color.BLACK, 20);

        btnMenuCompleto.addActionListener(e -> selezionaCategoria(btnMenuCompleto, "CatCombo"));
        btnPanini.addActionListener(e -> selezionaCategoria(btnPanini, "CatPanini"));
        btnSfiziosita.addActionListener(e -> selezionaCategoria(btnSfiziosita, "CatSfiziosita"));
        btnBibite.addActionListener(e -> selezionaCategoria(btnBibite, "CatBibite"));
        btnDolci.addActionListener(e -> selezionaCategoria(btnDolci, "CatDolci"));

        sidebar.add(btnMenuCompleto);
        sidebar.add(btnPanini);
        sidebar.add(btnSfiziosita);
        sidebar.add(btnBibite);
        sidebar.add(btnDolci);

        // --- MAIN CARDS ---
        cardLayout = new CardLayout();
        mainCards = new JPanel(cardLayout);
        mainCards.setBackground(COLOR_BG);
        mainCards.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        String[] comboImages = {
            "immagini/menu_combo/menu_crispy_chicken.png",
            "immagini/menu_combo/menu_american_burger.png",
            "immagini/menu_combo/menu_double_bbq.png",
            "immagini/menu_combo/menu_veggie_burger.png",
            "immagini/menu_combo/menu_crispy_fish.png"
        };
        
        String[] paniniImages = {
            "immagini/panini/crispy_chicken.png",
            "immagini/panini/american_burger.png",
            "immagini/panini/double_bbq.png",
            "immagini/panini/veggie_burger.png",
            "immagini/panini/crispy_fish.png"
        };
        
        String[] sfiziositaImages = {
            "immagini/sfiziosita/patatine_piccole.png",
            "immagini/sfiziosita/patatine_medie.png",
            "immagini/sfiziosita/patatine_grandi.png",
            "immagini/sfiziosita/crocchette_4pz.png",
            "immagini/sfiziosita/crocchette_8pz.png",
            "immagini/sfiziosita/ali_4pz.png",
            "immagini/sfiziosita/ali_8pz.png"
        };

        String[] bibiteImages = {
            "immagini/bevande/acqua_naturale.png",
            "immagini/bevande/acqua_gassata.png",
            "immagini/bevande/coca_cola.png",
            "immagini/bevande/coca_cola_zero.png",
            "immagini/bevande/sprite.png",
            "immagini/bevande/pepsi.png"
        };
        
        String[] dolciImages = {
            "immagini/dolci/coockie.png",
            "immagini/dolci/ciambella.png",
            "immagini/dolci/cono_gelato.png",
            "immagini/dolci/coppetta_piccola.png",
            "immagini/dolci/coppetta_media.png",
            "immagini/dolci/coppetta_grande.png"
        };

        mainCards.add(creaPannelloVuoto(), "Blank");
        mainCards.add(creaGrigliaProdotti(
            new String[]{"Crispy Chicken", "American Burger", "Double BBQ", "Veggie Burger", "Crispy Fish"},
            new String[]{"M1", "M2", "M3", "M4", "M5"}, new double[]{10.0, 12.0, 15.0, 8.0, 12.0}, comboImages, true, 240, 135), "CatCombo");
        
        mainCards.add(creaGrigliaProdotti(
            new String[]{"Crispy Chicken", "American burger", "Double BBQ", "Veggie Burger", "Crispy Fish"},
            new String[]{"P1", "P2", "P3", "P4", "P5"}, new double[]{8.0, 10.0, 12.0, 7.0, 9.0}, paniniImages, false, 240, 135), "CatPanini");
        
        mainCards.add(creaGrigliaProdotti(
            new String[]{"Patatine Piccole", "Patatine Medie", "Patatine Grandi", "Crocchette 4pz", "Crocchette 8pz", "Ali 4pz", "Ali 8pz"},
            new String[]{"S1", "S2", "S3", "S4", "S5", "S6", "S7"}, new double[]{3.0, 4.5, 5.0, 3.5, 6.0, 5.0, 6.0}, sfiziositaImages, false, 240, 135), "CatSfiziosita");
        
        mainCards.add(creaGrigliaProdotti(
            new String[]{"Acqua Naturale", "Acqua Gassata", "Coca Cola", "Coca Zero", "Sprite", "Pepsi"},
            new String[]{"B1", "B2", "B3", "B4", "B5", "B6"}, new double[]{1.5, 1.5, 3.0, 4.0, 2.5, 3.0}, bibiteImages, false, 180, 135), "CatBibite");
        
        mainCards.add(creaGrigliaProdotti(
            new String[]{"Cookie", "Ciambella", "Gelato cono", "Coppetta Picc.", "Coppetta Med.", "Coppetta Gran."},
            new String[]{"D1", "D2", "D3", "D4", "D5", "D6"}, new double[]{1.0, 1.5, 3.5, 1.5, 3.0, 4.0}, dolciImages, false, 240, 135), "CatDolci");

        mainCards.add(creaSceltaBibitaCombo(bibiteImages), "SceltaBibita");
        mainCards.add(creaSceltaPatatineCombo(sfiziositaImages), "SceltaPatatine");
        mainCards.add(creaPannelloRiepilogo(), "Summary");
        mainCards.add(creaPannelloPagamento(), "Payment");
        
        pannelloModificaContainer = new JPanel(new BorderLayout());
        pannelloModificaContainer.setBackground(COLOR_BG);
        mainCards.add(pannelloModificaContainer, "Modifica");

        // --- HEADER E FOOTER ---
        JLabel lblTitolo = new JLabel("Tocca una categoria per iniziare", SwingConstants.CENTER);
        lblTitolo.setFont(new Font("SansSerif", Font.BOLD, 32));
        lblTitolo.setForeground(Color.BLACK);
        lblTitolo.setBorder(new EmptyBorder(10, 0, 20, 0));

        add(lblTitolo, BorderLayout.NORTH);
        add(sidebar, BorderLayout.WEST);
        add(mainCards, BorderLayout.CENTER);
        add(creaFooter(), BorderLayout.SOUTH);

        aggiornaFooter();
    }

    private void selezionaCategoria(RoundedButton selectedBtn, String viewName) {
        RoundedButton[] btns = {btnMenuCompleto, btnPanini, btnSfiziosita, btnBibite, btnDolci};
        for (RoundedButton b : btns) {
            b.setButtonColor(COLOR_YELLOW, Color.BLACK);
        }
        if (selectedBtn != null) {
            selectedBtn.setButtonColor(COLOR_RED, Color.WHITE);
        }
        switchView(viewName);
    }

    private JScrollPane creaTouchScroll(JPanel content) {
        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(25); 
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        MouseAdapter dragAdapter = new MouseAdapter() {
            private Point origin;

            @Override
            public void mousePressed(MouseEvent e) {
                origin = e.getLocationOnScreen();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                origin = null;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (origin != null) {
                    JViewport viewPort = scroll.getViewport();
                    Point viewPosition = viewPort.getViewPosition();
                    
                    int deltaY = origin.y - e.getLocationOnScreen().y;
                    int maxY = content.getHeight() - viewPort.getHeight();
                    
                    int newY = viewPosition.y + deltaY;
                    if (newY < 0) newY = 0;
                    if (newY > maxY) newY = maxY;
                    
                    if (maxY > 0) {
                        viewPort.setViewPosition(new Point(viewPosition.x, newY));
                        origin = e.getLocationOnScreen();
                    }
                }
            }
        };
        content.addMouseListener(dragAdapter);
        content.addMouseMotionListener(dragAdapter);

        return scroll;
    }

    private String getValidImagePath(String path) {
        if (path == null) return null;
        if (new File(path).exists()) return path;

        String fileName = new File(path).getName();
        String[] fallbackDirs = {
            "immagini/menu_combo/dolci/", "immagini/dolci/",
            "immagini/menu_combo/sfiziosità/", "immagini/menu_combo/sfiziosita/",
            "immagini/sfiziosità/", "immagini/sfiziosita/",
            "immagini/menu_combo/bevande/", "immagini/bevande/",
            "immagini/menu_combo/panini/", "immagini/panini/",
            "immagini/menu_combo/", "immagini/"
        };

        for (String dir : fallbackDirs) {
            if (new File(dir + fileName).exists()) {
                return dir + fileName;
            }
        }
        return path;
    }

    private JPanel creaPannelloVuoto() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(COLOR_BG);
        return p;
    }

    private JPanel creaGrigliaProdotti(String[] nomi, String[] ids, double[] prezzi, String[] imgPaths, boolean isCombo, int imgW, int imgH) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(COLOR_BG);
        
        JPanel grid = new JPanel(new GridLayout(0, 3, 20, 20));
        grid.setBackground(COLOR_BG);
        grid.setBorder(new EmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < nomi.length; i++) {
            final String id = ids[i];
            JPanel card = new RoundedPanel(15, Color.WHITE);
            card.setLayout(new BorderLayout(10, 10));
            card.setBorder(new EmptyBorder(15, 15, 15, 15));

            JComponent imgHolder;
            String validPath = (imgPaths != null && i < imgPaths.length) ? getValidImagePath(imgPaths[i]) : null;
            
            if (validPath != null && new File(validPath).exists()) {
                ImageIcon icon = new ImageIcon(validPath);
                Image img = icon.getImage().getScaledInstance(imgW, imgH, Image.SCALE_SMOOTH);
                imgHolder = new JLabel(new ImageIcon(img), SwingConstants.CENTER);
            } else {
                imgHolder = new JPanel();
                imgHolder.setBackground(Color.LIGHT_GRAY);
            }
            imgHolder.setPreferredSize(new Dimension(240, 135));

            JLabel lblNome = new JLabel("<html><center>" + nomi[i] + "<br>€" + String.format("%.2f", prezzi[i]) + "</center></html>", SwingConstants.CENTER);
            lblNome.setFont(new Font("SansSerif", Font.BOLD, 18));
            lblNome.setForeground(Color.BLACK);

            RoundedButton btnAggiungi = new RoundedButton("Aggiungi", COLOR_YELLOW, Color.BLACK, 15);
            btnAggiungi.setPreferredSize(new Dimension(0, 50));
            btnAggiungi.addActionListener(e -> {
                if (isCombo) {
                    tempComboId = id;
                    switchView("SceltaBibita");
                } else {
                    chiosco.aggiungiProdotto(id, 1, null);
                    aggiornaFooter();
                }
            });

            card.add(imgHolder, BorderLayout.NORTH);
            card.add(lblNome, BorderLayout.CENTER);
            card.add(btnAggiungi, BorderLayout.SOUTH);
            grid.add(card);
        }

        p.add(creaTouchScroll(grid), BorderLayout.CENTER);
        return p;
    }

    private JPanel creaSceltaBibitaCombo(String[] imgPaths) {
        String[] nomi = {"Acqua Naturale", "Acqua Gassata", "Coca Cola", "Coca Zero", "Sprite", "Pepsi"};
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(COLOR_BG);
        
        JLabel lblTitolo = new JLabel("Scegli la bibita inclusa nel Menu':", SwingConstants.CENTER);
        lblTitolo.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblTitolo.setForeground(Color.BLACK);
        lblTitolo.setBorder(new EmptyBorder(20, 0, 20, 0));
        p.add(lblTitolo, BorderLayout.NORTH);
        
        JPanel grid = new JPanel(new GridLayout(0, 3, 20, 20));
        grid.setBackground(COLOR_BG);
        grid.setBorder(new EmptyBorder(10, 20, 20, 20));

        for (int i = 0; i < nomi.length; i++) {
            String nome = nomi[i];
            JPanel card = new RoundedPanel(15, Color.WHITE);
            card.setLayout(new BorderLayout(10, 10));
            card.setBorder(new EmptyBorder(15, 15, 15, 15));

            JComponent imgHolder;
            String validPath = getValidImagePath(imgPaths[i]);
            
            if (validPath != null && new File(validPath).exists()) {
                ImageIcon icon = new ImageIcon(validPath);
                Image img = icon.getImage().getScaledInstance(180, 135, Image.SCALE_SMOOTH); 
                imgHolder = new JLabel(new ImageIcon(img), SwingConstants.CENTER);
            } else {
                imgHolder = new JPanel();
                imgHolder.setBackground(Color.LIGHT_GRAY);
            }
            imgHolder.setPreferredSize(new Dimension(240, 135));

            JLabel lblNome = new JLabel("<html><center>" + nome + "</center></html>", SwingConstants.CENTER);
            lblNome.setFont(new Font("SansSerif", Font.BOLD, 18));
            lblNome.setForeground(Color.BLACK);

            RoundedButton btnAggiungi = new RoundedButton("Scegli", COLOR_YELLOW, Color.BLACK, 15);
            btnAggiungi.setPreferredSize(new Dimension(0, 50));
            btnAggiungi.addActionListener(e -> {
                tempComboDrink = nome;
                switchView("SceltaPatatine");
            });

            card.add(imgHolder, BorderLayout.NORTH);
            card.add(lblNome, BorderLayout.CENTER);
            card.add(btnAggiungi, BorderLayout.SOUTH);
            grid.add(card);
        }
        
        p.add(creaTouchScroll(grid), BorderLayout.CENTER);
        return p;
    }

    private JPanel creaSceltaPatatineCombo(String[] imgPaths) {
        String[] nomi = {"Piccole (Incluse)", "Medie (+€1.00)", "Grandi (+€1.60)"};
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(COLOR_BG);
        
        JLabel lblTitolo = new JLabel("Scegli la grandezza delle patatine:", SwingConstants.CENTER);
        lblTitolo.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblTitolo.setForeground(Color.BLACK);
        lblTitolo.setBorder(new EmptyBorder(20, 0, 20, 0));
        p.add(lblTitolo, BorderLayout.NORTH);
        
        JPanel grid = new JPanel(new GridLayout(0, 3, 20, 20));
        grid.setBackground(COLOR_BG);
        grid.setBorder(new EmptyBorder(10, 20, 20, 20));

        for (int i=0; i<nomi.length; i++) {
            final int idx = i;
            JPanel card = new RoundedPanel(15, Color.WHITE);
            card.setLayout(new BorderLayout(10, 10));
            card.setBorder(new EmptyBorder(15, 15, 15, 15));

            JComponent imgHolder;
            String validPath = getValidImagePath(imgPaths[i]);
            
            if (validPath != null && new File(validPath).exists()) {
                ImageIcon icon = new ImageIcon(validPath);
                Image img = icon.getImage().getScaledInstance(240, 135, Image.SCALE_SMOOTH); 
                imgHolder = new JLabel(new ImageIcon(img), SwingConstants.CENTER);
            } else {
                imgHolder = new JPanel();
                imgHolder.setBackground(Color.LIGHT_GRAY);
            }
            imgHolder.setPreferredSize(new Dimension(240, 135));

            JLabel lblNome = new JLabel("<html><center>" + nomi[i] + "</center></html>", SwingConstants.CENTER);
            lblNome.setFont(new Font("SansSerif", Font.BOLD, 18));
            lblNome.setForeground(Color.BLACK);

            RoundedButton btnAggiungi = new RoundedButton("Scegli", COLOR_YELLOW, Color.BLACK, 15);
            btnAggiungi.setPreferredSize(new Dimension(0, 50));
            btnAggiungi.addActionListener(e -> {
                List<String[]> pers = new ArrayList<>();
                pers.add(new String[]{"Bibita Combo", tempComboDrink, "0.0"});
                if (idx == 1) pers.add(new String[]{"Upgrade Patatine", "Medie", "1.00"});
                else if (idx == 2) pers.add(new String[]{"Upgrade Patatine", "Grandi", "1.60"});
                else pers.add(new String[]{"Upgrade Patatine", "Piccole", "0.00"});
                
                chiosco.aggiungiProdotto(tempComboId, 1, pers);
                selezionaCategoria(null, "Blank");
                aggiornaFooter();
            });

            card.add(imgHolder, BorderLayout.NORTH);
            card.add(lblNome, BorderLayout.CENTER);
            card.add(btnAggiungi, BorderLayout.SOUTH);
            grid.add(card);
        }
        
        p.add(creaTouchScroll(grid), BorderLayout.CENTER);
        return p;
    }

    private JPanel creaPannelloRiepilogo() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(COLOR_BG);
        
        JLabel lblTitolo = new JLabel("RIEPILOGO ORDINE", SwingConstants.CENTER);
        lblTitolo.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblTitolo.setForeground(Color.BLACK);
        lblTitolo.setBorder(new EmptyBorder(10, 0, 20, 0));
        p.add(lblTitolo, BorderLayout.NORTH);

        panelSummaryContent = new JPanel();
        panelSummaryContent.setLayout(new BoxLayout(panelSummaryContent, BoxLayout.Y_AXIS));
        panelSummaryContent.setBackground(COLOR_BG);
        panelSummaryContent.setBorder(new EmptyBorder(0, 0, 0, 10));
        
        p.add(creaTouchScroll(panelSummaryContent), BorderLayout.CENTER);
        return p;
    }

    private void popolaRiepilogo() {
        panelSummaryContent.removeAll();
        int idx = 0;
        
        if(chiosco.getNumeroVociCorrenti() > 0) {
            for (VoceOrdine vo : chiosco.getVociCarrello()) {
                JPanel row = new RoundedPanel(10, Color.WHITE);
                row.setLayout(new BorderLayout(15, 10)); 
                row.setBorder(new EmptyBorder(15, 15, 15, 15));
                row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250)); 

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

                StringBuilder desc = new StringBuilder("<html><span style='font-size:18px;'><b>" + vo.getProdotto().getNome() + "</b></span><br>");
                for (String riga : righeDescrittive) {
                    desc.append("<span style='font-size:14px; color:gray;'>- ").append(riga).append("</span><br>");
                }
                desc.append("<span style='font-size:16px;'>Subtotale: €").append(String.format("%.2f", vo.getSubTotale())).append("</span></html>");

                JLabel lblDesc = new JLabel(desc.toString());
                lblDesc.setForeground(Color.BLACK);
                lblDesc.setVerticalAlignment(SwingConstants.CENTER);

                JPanel actionPanel = new JPanel();
                actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
                actionPanel.setOpaque(false);
                actionPanel.setPreferredSize(new Dimension(180, 160));
                actionPanel.setMinimumSize(new Dimension(180, 160));
                actionPanel.setMaximumSize(new Dimension(180, 250));

                JPanel pnlQuantita = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
                pnlQuantita.setOpaque(false);
                pnlQuantita.setAlignmentX(Component.CENTER_ALIGNMENT);

                RoundedButton btnMeno = new RoundedButton("-", COLOR_YELLOW, Color.BLACK, 15);
                btnMeno.setPreferredSize(new Dimension(60, 40));
                btnMeno.setFont(new Font("SansSerif", Font.BOLD, 18));
                btnMeno.setMargin(new Insets(0, 0, 0, 0));

                JLabel lblQta = new JLabel(String.valueOf(vo.getQuantita()), SwingConstants.CENTER);
                lblQta.setFont(new Font("SansSerif", Font.BOLD, 20));
                lblQta.setPreferredSize(new Dimension(30, 40));
                lblQta.setForeground(Color.BLACK);

                RoundedButton btnPiu = new RoundedButton("+", COLOR_YELLOW, Color.BLACK, 15);
                btnPiu.setPreferredSize(new Dimension(60, 40));
                btnPiu.setFont(new Font("SansSerif", Font.BOLD, 18));
                btnPiu.setMargin(new Insets(0, 0, 0, 0));

                final int currentIdx = idx;

                btnMeno.addActionListener(e -> {
                    if (vo.getQuantita() > 1) {
                        chiosco.aggiornaQuantitaVoce(currentIdx, vo.getQuantita() - 1);
                        popolaRiepilogo();
                        aggiornaFooter();
                    }
                });

                btnPiu.addActionListener(e -> {
                    chiosco.aggiornaQuantitaVoce(currentIdx, vo.getQuantita() + 1);
                        popolaRiepilogo();
                        aggiornaFooter();
                });

                pnlQuantita.add(btnMeno);
                pnlQuantita.add(lblQta);
                pnlQuantita.add(btnPiu);

                actionPanel.add(pnlQuantita);
                actionPanel.add(Box.createRigidArea(new Dimension(0, 8))); 

                if (cat.equals("Panino") || cat.equals("Menu Combo")) {
                    RoundedButton btnModifica = new RoundedButton("Modifica", COLOR_GREEN, Color.WHITE, 15);
                    btnModifica.setPreferredSize(new Dimension(140, 40)); 
                    btnModifica.setMaximumSize(new Dimension(140, 40));
                    btnModifica.setFont(new Font("SansSerif", Font.BOLD, 14));
                    btnModifica.setAlignmentX(Component.CENTER_ALIGNMENT);
                    btnModifica.setMargin(new Insets(0, 0, 0, 0));
                    btnModifica.addActionListener(e -> apriSchermataModifica(currentIdx, vo));
                    
                    actionPanel.add(btnModifica);
                    actionPanel.add(Box.createRigidArea(new Dimension(0, 8))); 
                } else {
                    JLabel placeholder = new JLabel();
                    placeholder.setPreferredSize(new Dimension(140, 48));
                    placeholder.setMaximumSize(new Dimension(140, 48));
                    actionPanel.add(placeholder);
                }

                RoundedButton btnElimina = new RoundedButton("Elimina", COLOR_RED, Color.WHITE, 15);
                btnElimina.setPreferredSize(new Dimension(140, 40)); 
                btnElimina.setMaximumSize(new Dimension(140, 40));
                btnElimina.setFont(new Font("SansSerif", Font.BOLD, 14));
                btnElimina.setAlignmentX(Component.CENTER_ALIGNMENT);
                btnElimina.setMargin(new Insets(0, 0, 0, 0));
                
                btnElimina.addActionListener(e -> {
                    JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Conferma", true);
                    dialog.setSize(400, 200);
                    dialog.setLocationRelativeTo(this);
                    dialog.setLayout(new BorderLayout());
                    dialog.getContentPane().setBackground(Color.WHITE);
                    
                    JLabel msg = new JLabel("Vuoi rimuovere questo prodotto?", SwingConstants.CENTER);
                    msg.setFont(new Font("SansSerif", Font.BOLD, 18));
                    msg.setForeground(Color.BLACK);
                    dialog.add(msg, BorderLayout.CENTER);
                    
                    JPanel bot = new JPanel(new GridLayout(1, 2, 10, 10));
                    bot.setBorder(new EmptyBorder(10, 20, 20, 20));
                    bot.setBackground(Color.WHITE);
                    
                    RoundedButton bYes = new RoundedButton("SI", COLOR_RED, Color.WHITE, 15);
                    bYes.addActionListener(ev -> {
                        chiosco.rimuoviVoce(currentIdx);
                        popolaRiepilogo();
                        aggiornaFooter();
                        dialog.dispose();
                        if(chiosco.getNumeroVociCorrenti() == 0) {
                            selezionaCategoria(null, "Blank");
                        }
                    });
                    
                    RoundedButton bNo = new RoundedButton("NO", Color.LIGHT_GRAY, Color.BLACK, 15);
                    bNo.addActionListener(ev -> dialog.dispose());
                    
                    bot.add(bNo); bot.add(bYes);
                    dialog.add(bot, BorderLayout.SOUTH);
                    dialog.setVisible(true);
                });

                actionPanel.add(btnElimina);

                row.add(lblDesc, BorderLayout.CENTER);
                row.add(actionPanel, BorderLayout.EAST);
                
                panelSummaryContent.add(row);
                panelSummaryContent.add(Box.createRigidArea(new Dimension(0, 15)));
                idx++;
            }
        }
        panelSummaryContent.revalidate();
        panelSummaryContent.repaint();
    }

    private void apriSchermataModifica(int index, VoceOrdine vo) {
        pannelloModificaContainer.removeAll();

        String nomeProd = vo.getProdotto().getNome().toLowerCase();
        
        boolean isCrispy = nomeProd.contains("crispy chicken");
        boolean isAmerican = nomeProd.contains("american burger");
        boolean isDoubleBbq = nomeProd.contains("double bbq");
        boolean isVeggie = nomeProd.contains("veggie burger");
        boolean isFish = nomeProd.contains("crispy fish");

        if (!isCrispy && !isAmerican && !isDoubleBbq && !isVeggie && !isFish) {
            mostraErroreTouch("La modifica per questo prodotto\nnon e' ancora disponibile.");
            return;
        }

        JLabel lblTitolo = new JLabel("MODIFICA INGREDIENTI", SwingConstants.CENTER);
        lblTitolo.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblTitolo.setForeground(Color.BLACK);
        lblTitolo.setBorder(new EmptyBorder(20, 0, 10, 0));

        String descText = "";
        String[] ingredienti = null;

        if (isCrispy) {
            descText = "Cotoletta di pollo, cheddar, insalata, salsa barbecue e salsa crispy";
            ingredienti = new String[]{"Insalata", "Cheddar", "Salsa Barbecue", "Salsa Crispy"};
        } else if (isAmerican) {
            descText = "Doppio hamburgher, cheddar, insalata e cetriolini";
            ingredienti = new String[]{"Cheddar", "Insalata", "Cetriolini"};
        } else if (isDoubleBbq) {
            descText = "Doppio hamburgher, doppio cheddar, cetriolini, bacon e salsa crispy";
            ingredienti = new String[]{"Cheddar", "Cetriolini", "Bacon", "Salsa Crispy"};
        } else if (isVeggie) {
            descText = "Hamburgher di ceci, doppia insalata e salsa hummus";
            ingredienti = new String[]{"Doppia Insalata", "Salsa Hummus"};
        } else if (isFish) {
            descText = "Filetto di pesce fritto, cheddar e maionese";
            ingredienti = new String[]{"Cheddar", "Maionese"};
        }

        JLabel lblDesc = new JLabel(descText, SwingConstants.CENTER);
        lblDesc.setFont(new Font("SansSerif", Font.ITALIC, 18));
        lblDesc.setForeground(Color.DARK_GRAY);
        lblDesc.setBorder(new EmptyBorder(0, 0, 30, 0));

        JPanel centerTop = new JPanel(new BorderLayout());
        centerTop.setOpaque(false);
        centerTop.add(lblTitolo, BorderLayout.NORTH);
        centerTop.add(lblDesc, BorderLayout.SOUTH);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        List<String[]> daMantenere = new ArrayList<>(); 
        List<String> rimossiAttualmente = new ArrayList<>();

        for(Personalizzazione p : vo.getPersonalizzazioni()) {
            if (p.getTipo().equals("Rimozione")) {
                rimossiAttualmente.add(p.getIngrediente());
            } else {
                daMantenere.add(new String[]{p.getTipo(), p.getIngrediente(), String.valueOf(p.getSovrapprezzo())});
            }
        }

        List<String> nuoviRimossi = new ArrayList<>(rimossiAttualmente);

        for (String ingr : ingredienti) {
            JPanel row = new RoundedPanel(10, Color.WHITE);
            row.setLayout(new BorderLayout(15, 10));
            row.setBorder(new EmptyBorder(10, 30, 10, 30)); 
            row.setMaximumSize(new Dimension(800, 70));

            JLabel lIngr = new JLabel(ingr);
            lIngr.setFont(new Font("SansSerif", Font.BOLD, 20));
            lIngr.setForeground(Color.BLACK);
            row.add(lIngr, BorderLayout.CENTER);

            boolean isRimosso = nuoviRimossi.contains(ingr);
            RoundedButton btnToggle = new RoundedButton(
                isRimosso ? "Aggiungi" : "Elimina",
                isRimosso ? COLOR_GREEN : COLOR_RED,
                Color.WHITE, 18
            );
            btnToggle.setPreferredSize(new Dimension(140, 45));

            btnToggle.addActionListener(e -> {
                if (btnToggle.getText().equals("Elimina")) {
                    btnToggle.setText("Aggiungi");
                    btnToggle.setButtonColor(COLOR_GREEN, Color.WHITE);
                    nuoviRimossi.add(ingr);
                } else {
                    btnToggle.setText("Elimina");
                    btnToggle.setButtonColor(COLOR_RED, Color.WHITE);
                    nuoviRimossi.remove(ingr);
                }
            });

            row.add(btnToggle, BorderLayout.EAST);
            listPanel.add(row);
            listPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        RoundedButton btnConferma = new RoundedButton("CONFERMA MODIFICHE", COLOR_YELLOW, Color.BLACK, 25);
        btnConferma.setPreferredSize(new Dimension(350, 60));
        btnConferma.addActionListener(e -> {
            List<String[]> nuovePers = new ArrayList<>(daMantenere);
            for(String r : nuoviRimossi) {
                nuovePers.add(new String[]{"Rimozione", r, "0.0"});
            }
            chiosco.aggiornaPersonalizzazioniVoce(index, nuovePers);
            switchView("Summary"); 
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(new EmptyBorder(30, 0, 20, 0));
        bottomPanel.add(btnConferma);

        pannelloModificaContainer.add(centerTop, BorderLayout.NORTH);
        pannelloModificaContainer.add(creaTouchScroll(listPanel), BorderLayout.CENTER);
        pannelloModificaContainer.add(bottomPanel, BorderLayout.SOUTH);

        switchView("Modifica");
    }

    private JPanel creaPannelloPagamento() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(COLOR_BG);
        
        JLabel lblTitolo = new JLabel("INSERISCI I DATI PER IL PAGAMENTO", SwingConstants.CENTER);
        lblTitolo.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblTitolo.setForeground(Color.BLACK);
        p.add(lblTitolo, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(3, 1, 10, 20));
        form.setBackground(COLOR_BG);
        form.setBorder(new EmptyBorder(50, 150, 50, 150));

        txtCarta = creaCampoPagamento("Numero Carta (16 cifre)", 16);
        txtScadenza = creaCampoPagamento("Scadenza (MM/YY)", 4);
        txtCVV = creaCampoPagamento("CVV (3 cifre)", 3);

        form.add(txtCarta);
        form.add(txtScadenza);
        form.add(txtCVV);

        p.add(form, BorderLayout.CENTER);
        return p;
    }

    private JTextField creaCampoPagamento(String placeholder, int maxLen) {
        JTextField tf = new JTextField(placeholder);
        tf.setFont(new Font("SansSerif", Font.BOLD, 28));
        tf.setHorizontalAlignment(JTextField.CENTER);
        tf.setEditable(false);
        tf.setBackground(Color.WHITE);
        tf.setForeground(Color.GRAY);
        tf.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        tf.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                String input = apriTastierinoTouch("Inserisci " + placeholder, maxLen);
                if(input != null && !input.isEmpty()) {
                    tf.setForeground(Color.BLACK);
                    if(maxLen == 16) { rawCarta = input; tf.setText(formattaCarta(input)); }
                    if(maxLen == 4)  { rawScadenza = input; tf.setText(formattaScadenza(input)); }
                    if(maxLen == 3)  { rawCVV = input; tf.setText(input); }
                }
            }
        });
        return tf;
    }

    private JPanel creaFooter() {
        footerPanel = new RoundedPanel(30, Color.WHITE);
        footerPanel.setLayout(new BorderLayout());
        footerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        footerPanel.setPreferredSize(new Dimension(0, 90));

        JPanel leftWrapper = new JPanel(new GridBagLayout());
        leftWrapper.setOpaque(false);

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        leftPanel.setOpaque(false);

        btnIndietro = new RoundedButton("< Indietro", COLOR_BG, COLOR_DARK, 20);
        btnIndietro.setPreferredSize(new Dimension(160, 50)); 
        btnIndietro.setVisible(false);
        btnIndietro.addActionListener(e -> {
            if(currentView.equals("Payment")) switchView("Summary");
            else if (currentView.equals("Modifica")) switchView("Summary"); 
            else {
                selezionaCategoria(null, "Blank");
            }
        });

        lblTestoOrdine = new JLabel("Il tuo ordine è vuoto");
        lblTestoOrdine.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTestoOrdine.setForeground(Color.BLACK);

        badgePanel = new BadgePanel("0");
        badgePanel.setVisible(false);

        lblPrezzoTotale = new JLabel("0,00€");
        lblPrezzoTotale.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblPrezzoTotale.setForeground(Color.BLACK);

        leftPanel.add(btnIndietro);
        leftPanel.add(lblTestoOrdine);
        leftPanel.add(badgePanel);
        leftPanel.add(lblPrezzoTotale);

        leftWrapper.add(leftPanel);

        JPanel rightWrapper = new JPanel(new GridBagLayout());
        rightWrapper.setOpaque(false);

        btnAvantiPaga = new RoundedButton("Avanti >", COLOR_YELLOW, Color.BLACK, 30);
        btnAvantiPaga.setPreferredSize(new Dimension(160, 50));
        btnAvantiPaga.setVisible(false);
        btnAvantiPaga.addActionListener(e -> {
            RoundedButton[] btns = {btnMenuCompleto, btnPanini, btnSfiziosita, btnBibite, btnDolci};
            for (RoundedButton b : btns) b.setButtonColor(COLOR_YELLOW, Color.BLACK);

            if (currentView.equals("Summary")) switchView("Payment");
            else if (currentView.equals("Payment")) eseguiPagamentoDefinitivo();
            else switchView("Summary");
        });

        rightWrapper.add(btnAvantiPaga);

        footerPanel.add(leftWrapper, BorderLayout.WEST);
        footerPanel.add(rightWrapper, BorderLayout.EAST);

        return footerPanel;
    }

    private void switchView(String viewName) {
        currentView = viewName;
        if(viewName.equals("Summary")) popolaRiepilogo();
        cardLayout.show(mainCards, viewName);
        aggiornaFooter();
    }

    private void aggiornaFooter() {
        int count = chiosco.getNumeroVociCorrenti();
        double totale = chiosco.getTotaleOrdineCorrente();

        if (count == 0) {
            lblTestoOrdine.setText("Il tuo ordine è vuoto");
            badgePanel.setVisible(false);
            lblPrezzoTotale.setText("0,00€");
            btnAvantiPaga.setVisible(false);
            btnIndietro.setVisible(false);
        } else {
            lblTestoOrdine.setText("Il tuo ordine");
            badgePanel.setText(String.valueOf(count));
            badgePanel.setVisible(true);
            lblPrezzoTotale.setText(String.format("%.2f€", totale));
            
            if (currentView.equals("Modifica")) {
                btnIndietro.setVisible(true);
                btnAvantiPaga.setVisible(false); 
            } else {
                btnAvantiPaga.setVisible(true);
                if (currentView.equals("Summary")) {
                    btnIndietro.setVisible(true);
                    btnAvantiPaga.setText("Paga");
                    btnAvantiPaga.setButtonColor(COLOR_GREEN, Color.WHITE);
                } else if (currentView.equals("Payment")) {
                    btnIndietro.setVisible(true);
                    btnAvantiPaga.setText("Conferma");
                    btnAvantiPaga.setButtonColor(COLOR_GREEN, Color.WHITE);
                } else {
                    btnIndietro.setVisible(false);
                    btnAvantiPaga.setText("Avanti >");
                    btnAvantiPaga.setButtonColor(COLOR_YELLOW, Color.BLACK);
                }
            }
        }
        footerPanel.revalidate();
        footerPanel.repaint();
    }

    private void eseguiPagamentoDefinitivo() {
        if (rawCarta.length() != 16 || rawScadenza.length() != 4 || rawCVV.length() != 3) {
            mostraErroreTouch("Compila tutti i campi\ncorrettamente!");
            return;
        }

        chiosco.terminaOrdine();
        String esito = chiosco.paga(rawCarta, rawScadenza, rawCVV);
        
        mostraMessaggioTouch("ESITO TRANSAZIONE", esito);
        
        chiosco.iniziaOrdine();
        rawCarta = ""; rawScadenza = ""; rawCVV = "";
        txtCarta.setText("Numero Carta (16 cifre)");
        txtCarta.setForeground(Color.GRAY);
        txtScadenza.setText("Scadenza (MM/YY)");
        txtScadenza.setForeground(Color.GRAY);
        txtCVV.setText("CVV (3 cifre)");
        txtCVV.setForeground(Color.GRAY);
        
        selezionaCategoria(null, "Blank");
        
        if (onOrderCompleted != null) {
            onOrderCompleted.run();
        }
    }

    private String formattaCarta(String raw) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < raw.length(); i++) {
            if (i > 0 && i % 4 == 0) sb.append("-");
            sb.append(raw.charAt(i));
        }
        return sb.toString();
    }

    private String formattaScadenza(String raw) {
        if(raw.length() > 2) return raw.substring(0, 2) + "/" + raw.substring(2);
        return raw;
    }

    private String apriTastierinoTouch(String titolo, int maxLength) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), titolo, true);
        dialog.setSize(450, 600);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.getContentPane().setBackground(COLOR_BG);

        JTextField display = new JTextField();
        display.setFont(new Font("SansSerif", Font.BOLD, 32));
        display.setHorizontalAlignment(JTextField.CENTER);
        display.setForeground(Color.BLACK);
        display.setEditable(false);
        dialog.add(display, BorderLayout.NORTH);

        JPanel tastierino = new JPanel(new GridLayout(4, 3, 10, 10));
        tastierino.setBackground(COLOR_BG);
        tastierino.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        String[] tasti = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "CANC", "0", ""};
        final String[] result = {null};

        for (String t : tasti) {
            if (t.isEmpty()) {
                tastierino.add(new JLabel("")); 
                continue;
            }
            
            Color bg = t.equals("CANC") ? COLOR_RED : Color.WHITE;
            Color fg = t.equals("CANC") ? Color.WHITE : Color.BLACK;
            
            RoundedButton btn = new RoundedButton(t, bg, fg, 15);
            btn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            
            btn.addActionListener(e -> {
                if (t.equals("CANC")) display.setText("");
                else if (display.getText().length() < maxLength) display.setText(display.getText() + t);
            });
            tastierino.add(btn);
        }

        RoundedButton btnOk = new RoundedButton("OK", COLOR_YELLOW, Color.BLACK, 20);
        btnOk.setFont(new Font("SansSerif", Font.BOLD, 24));
        btnOk.setPreferredSize(new Dimension(0, 70));
        btnOk.addActionListener(e -> { result[0] = display.getText(); dialog.dispose(); });

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(COLOR_BG);
        bottomPanel.setBorder(new EmptyBorder(10, 20, 20, 20));
        bottomPanel.add(btnOk, BorderLayout.CENTER);

        dialog.add(tastierino, BorderLayout.CENTER);
        dialog.add(bottomPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);

        return result[0];
    }

    private void mostraMessaggioTouch(String titolo, String messaggio) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), titolo, true);
        dialog.setSize(500, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.getContentPane().setBackground(Color.WHITE);

        JLabel lblMsg = new JLabel("<html><center>" + messaggio.replace("\n", "<br>") + "</center></html>", SwingConstants.CENTER);
        lblMsg.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblMsg.setForeground(Color.BLACK);
        dialog.add(lblMsg, BorderLayout.CENTER);

        RoundedButton btnOk = new RoundedButton("OK", COLOR_YELLOW, Color.BLACK, 20);
        btnOk.setFont(new Font("SansSerif", Font.BOLD, 22));
        btnOk.setPreferredSize(new Dimension(0, 60));
        btnOk.addActionListener(e -> dialog.dispose());
        
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(Color.WHITE);
        bottom.setBorder(new EmptyBorder(10, 50, 20, 50));
        bottom.add(btnOk, BorderLayout.CENTER);

        dialog.add(bottom, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void mostraErroreTouch(String msg) {
        mostraMessaggioTouch("ERRORE", msg);
    }
}