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

/**
 * La classe {@code ClientPanel} costruisce l'interfaccia utente (UI) lato cliente, 
 * ovvero lo schermo touch che gli utenti utilizzano per effettuare l'ordinazione.
 * Gestisce la navigazione tra le categorie, la personalizzazione dei prodotti, 
 * il riepilogo del carrello e la procedura finale di pagamento.
 */
public class ClientPanel extends JPanel {
    private Chiosco chiosco;
    private Runnable onOrderCompleted; 
    
    private CardLayout cardLayout;
    private JPanel mainCards;
    
    // Bottoni Sidebar per gestione colori
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
    private JTextField txtCarta, txtScadenza, txtCVV;
    private String rawCarta = "", rawScadenza = "", rawCVV = "";

    /**
     * Costruttore della classe {@code ClientPanel}.
     * Dispone i componenti dell'interfaccia grafica (Sidebar, Pannelli centrali e Footer).
     *
     * @param chiosco          Il controller principale del sistema.
     * @param onOrderCompleted Interfaccia {@link Runnable} per gestire un evento di callback
     *                         (utile ad es. per avvisare la cucina alla chiusura dell'ordine).
     */
    public ClientPanel(Chiosco chiosco, Runnable onOrderCompleted) {
        this.chiosco = chiosco;
        this.onOrderCompleted = onOrderCompleted;

        setLayout(new BorderLayout(15, 15));
        setBackground(COLOR_BG);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        // --- SIDEBAR (Sinistra - Categorie impilate) ---
        JPanel sidebar = new JPanel(new GridLayout(5, 1, 10, 15));
        sidebar.setBackground(COLOR_BG);
        sidebar.setPreferredSize(new Dimension(250, 0));

        // Inizializzati tutti di giallo di default
        btnMenuCompleto = new RoundedButton("MENU' COMBO", COLOR_YELLOW, Color.BLACK, 20);
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

        // --- MAIN CARDS (Centro - Contenuto dinamico) ---
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
            "immagini/menu_combo/panini/crispy_chicken.png",
            "immagini/menu_combo/panini/american_burger.png",
            "immagini/menu_combo/panini/double_bbq.png",
            "immagini/menu_combo/panini/veggie_burger.png",
            "immagini/menu_combo/panini/crispy_fish.png"
        };
        
        String[] sfiziositaImages = {
            "immagini/menu_combo/sfiziosità/patatine_piccole.png",
            "immagini/menu_combo/sfiziosità/patatine_medie.png",
            "immagini/menu_combo/sfiziosità/patatine_grandi.png",
            "immagini/menu_combo/sfiziosità/crocchette_4pz.png",
            "immagini/menu_combo/sfiziosità/crocchette_8pz.png",
            "immagini/menu_combo/sfiziosità/ali_4pz.png",
            "immagini/menu_combo/sfiziosità/ali_8pz.png"
        };

        String[] bibiteImages = {
            "immagini/menu_combo/bevande/acqua_naturale.png",
            "immagini/menu_combo/bevande/acqua_gassata.png",
            "immagini/menu_combo/bevande/coca_cola.png",
            "immagini/menu_combo/bevande/coca_cola_zero.png",
            "immagini/menu_combo/bevande/sprite.png",
            "immagini/menu_combo/bevande/pepsi.png"
        };
        
        String[] dolciImages = {
            "immagini/menu_combo/dolci/coockie.png",
            "immagini/menu_combo/dolci/ciambella.png",
            "immagini/menu_combo/dolci/cono_gelato.png",
            "immagini/menu_combo/dolci/coppetta_piccola.png",
            "immagini/menu_combo/dolci/coppetta_media.png",
            "immagini/menu_combo/dolci/coppetta_grande.png"
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

    /**
     * Gestisce lo stato e la colorazione visiva del pulsante selezionato nella sidebar
     * e cambia la vista mostrata al centro.
     *
     * @param selectedBtn Il bottone che l'utente ha premuto.
     * @param viewName    Il nome della carta (vista) da mostrare nel {@link CardLayout}.
     */
    private void selezionaCategoria(RoundedButton selectedBtn, String viewName) {
        // Riporta tutti i pulsanti al giallo di default
        RoundedButton[] btns = {btnMenuCompleto, btnPanini, btnSfiziosita, btnBibite, btnDolci};
        for (RoundedButton b : btns) {
            b.setButtonColor(COLOR_YELLOW, Color.BLACK);
        }
        // Il pulsante selezionato diventa rosso con testo bianco
        if (selectedBtn != null) {
            selectedBtn.setButtonColor(COLOR_RED, Color.WHITE);
        }
        switchView(viewName);
    }

    /**
     * Aggiunge funzionalità di scorrimento tramite "drag and drop" (come uno smartphone)
     * a un dato pannello, inglobandolo in uno {@code JScrollPane}.
     *
     * @param content Il pannello di cui si vuole abilitare lo scorrimento.
     * @return Uno {@code JScrollPane} configurato.
     */
    private JScrollPane creaTouchScroll(JPanel content) {
        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(25); // Velocizza la rotellina del mouse

        // Ascoltatore per simulare lo scrolling touch tramite trascinamento
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

        // Aggiunge la funzionalità di drag al pannello contenuto
        content.addMouseListener(dragAdapter);
        content.addMouseMotionListener(dragAdapter);

        return scroll;
    }

    /**
     * Verifica l'esistenza di un'immagine nel percorso specificato o tenta di cercarla
     * in cartelle di fallback predefinite (spesso utile per aggirare differenze di path OS).
     *
     * @param path Il percorso iniziale del file.
     * @return Un percorso valido per l'immagine o il percorso originale se non trovata in fallback.
     */
    private String getValidImagePath(String path) {
        if (path == null) return null;
        if (new File(path).exists()) return path;

        String fileName = new File(path).getName();
        String[] fallbackDirs = {
            "immagini/menu_combo/dolci/",
            "immagini/dolci/",
            "immagini/menu_combo/sfiziosità/",
            "immagini/menu_combo/sfiziosita/",
            "immagini/sfiziosità/",
            "immagini/sfiziosita/",
            "immagini/menu_combo/bevande/",
            "immagini/bevande/",
            "immagini/menu_combo/panini/",
            "immagini/panini/",
            "immagini/menu_combo/",
            "immagini/"
        };

        for (String dir : fallbackDirs) {
            if (new File(dir + fileName).exists()) {
                return dir + fileName;
            }
        }
        return path;
    }

    /**
     * Costruisce un pannello vuoto utile come schermata di benvenuto o segnaposto.
     *
     * @return Il pannello vuoto.
     */
    private JPanel creaPannelloVuoto() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(COLOR_BG);
        return p;
    }

    /**
     * Costruisce dinamicamente un pannello con una griglia di prodotti acquistabili.
     * Genera card visive per ogni prodotto passato come parametro.
     *
     * @param nomi     Gli array di nomi dei prodotti.
     * @param ids      Gli array di ID corrispondenti nel catalogo.
     * @param prezzi   Gli array dei prezzi di base.
     * @param imgPaths Gli array dei percorsi per le immagini.
     * @param isCombo  Variabile booleana che definisce se i prodotti della griglia sono dei menu (comportamento extra).
     * @param imgW     Larghezza in pixel per scalare le immagini.
     * @param imgH     Altezza in pixel per scalare le immagini.
     * @return Il pannello grid scrollabile.
     */
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

        // Ora usiamo creaTouchScroll invece di new JScrollPane
        p.add(creaTouchScroll(grid), BorderLayout.CENTER);
        return p;
    }

    /**
     * Genera la prima vista di selezione guidata per i menu: scelta della bibita.
     *
     * @param imgPaths Percorsi per le immagini delle bevande.
     * @return Il pannello popolato con la griglia bevande.
     */
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

    /**
     * Genera la seconda vista di selezione guidata per i menu: scelta delle patatine
     * (con possibilità di applicare personalizzazioni con sovrapprezzo).
     *
     * @param imgPaths Percorsi per le immagini delle patatine.
     * @return Il pannello popolato con le scelte.
     */
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
                selezionaCategoria(null, "Blank"); // Resetta le categorie e torna alla home
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

    /**
     * Struttura la base vuota del riepilogo dell'ordine in formato grafico.
     * @return Il pannello che verrà poi popolato dal metodo {@link #popolaRiepilogo()}.
     */
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
        
        p.add(creaTouchScroll(panelSummaryContent), BorderLayout.CENTER);
        return p;
    }

    /**
     * Svuota e ri-scrive l'elenco testuale di riepilogo con i prodotti contenuti 
     * nel carrello attuale interfacciandosi con il controller.
     */
    private void popolaRiepilogo() {
        panelSummaryContent.removeAll();
        int idx = 0;
        
        if(chiosco.getNumeroVociCorrenti() > 0) {
            for (VoceOrdine vo : chiosco.getVociCarrello()) {
                JPanel row = new RoundedPanel(10, Color.WHITE);
                row.setLayout(new BorderLayout(15, 10));
                row.setBorder(new EmptyBorder(15, 15, 15, 15));
                row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

                StringBuilder desc = new StringBuilder("<html><span style='font-size:16px;'><b>" + vo.getQuantita() + "x " + vo.getProdotto().getNome() + "</b></span><br>");
                for(Personalizzazione pers: vo.getPersonalizzazioni()){
                    desc.append("<span style='font-size:12px; color:gray;'>+ ").append(pers.getIngrediente()).append("</span><br>");
                }
                desc.append("<span style='font-size:14px;'>Subtotale: €").append(String.format("%.2f", vo.getSubTotale())).append("</span></html>");

                JLabel lblDesc = new JLabel(desc.toString());
                lblDesc.setForeground(Color.BLACK);

                RoundedButton btnElimina = new RoundedButton("Elimina", COLOR_RED, Color.WHITE, 15);
                btnElimina.setPreferredSize(new Dimension(120, 50));
                
                final int currentIdx = idx;
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

                row.add(lblDesc, BorderLayout.CENTER);
                row.add(btnElimina, BorderLayout.EAST);
                
                panelSummaryContent.add(row);
                panelSummaryContent.add(Box.createRigidArea(new Dimension(0, 15)));
                idx++;
            }
        }
        panelSummaryContent.revalidate();
        panelSummaryContent.repaint();
    }

    /**
     * Crea il form per l'inserimento dei dati di pagamento.
     * @return Il pannello che contiene i campi della carta di credito.
     */
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

    /**
     * Genera un singolo campo di testo configurato per simulare una tastiera virtuale touch
     * al click dell'utente, mascherando e validando la lunghezza massima di inserimento.
     *
     * @param placeholder Messaggio mostrato a vuoto.
     * @param maxLen      Lunghezza massima di caratteri ammessi per il campo.
     * @return L'oggetto {@code JTextField} predisposto.
     */
    private JTextField creaCampoPagamento(String placeholder, int maxLen) {
        JTextField tf = new JTextField(placeholder);
        tf.setFont(new Font("SansSerif", Font.BOLD, 28));
        tf.setHorizontalAlignment(JTextField.CENTER);
        tf.setEditable(false);
        tf.setBackground(Color.WHITE);
        tf.setForeground(Color.GRAY);
        // Cursore touch per input testo
        tf.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        tf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
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

    // ==========================================
    // FOOTER E TRANSIZIONI 
    // ==========================================

    /**
     * Crea il footer persistente presente nella schermata. 
     * Contiene tasti di navigazione (indietro/avanti), riassunto prodotti (tramite badge)
     * e totale temporaneo.
     *
     * @return Il pannello Footer.
     */
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
            // Deseleziona le categorie di sinistra se naviga nel riepilogo
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

    /**
     * Alterna la view (carta) del pannello principale per gestire la navigazione utente.
     *
     * @param viewName Nome della carta da portare in foreground (es. "Summary" o "CatPanini").
     */
    private void switchView(String viewName) {
        currentView = viewName;
        if(viewName.equals("Summary")) popolaRiepilogo();
        cardLayout.show(mainCards, viewName);
        aggiornaFooter();
    }

    /**
     * Analizza lo stato dell'ordine e modifica coerentemente i testi, la visibilità
     * e il colore dei bottoni del footer.
     */
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
        footerPanel.revalidate();
        footerPanel.repaint();
    }

    // ==========================================
    // LOGICA PAGAMENTO E FORMATTAZIONE
    // ==========================================
    
    /**
     * Processa le informazioni inserite, valida la lunghezza, interroga il controller
     * per chiudere e pagare l'ordine e se andato a buon fine, resetta la UI per il prossimo cliente.
     */
    private void eseguiPagamentoDefinitivo() {
        if (rawCarta.length() != 16 || rawScadenza.length() != 4 || rawCVV.length() != 3) {
            mostraErroreTouch("Compila tutti i campi\ncorrettamente!");
            return;
        }

        chiosco.terminaOrdine();
        String esito = chiosco.paga(rawCarta, rawScadenza, rawCVV);
        
        mostraMessaggioTouch("ESITO TRANSAZIONE", esito);
        
        // Reset totale
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

    /**
     * Formatta il valore inserito con la spaziatura tipica delle carte (blocchi da 4).
     * @param raw La stringa da formattare.
     * @return La stringa separata da trattini.
     */
    private String formattaCarta(String raw) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < raw.length(); i++) {
            if (i > 0 && i % 4 == 0) sb.append("-");
            sb.append(raw.charAt(i));
        }
        return sb.toString();
    }

    /**
     * Formatta una data di scadenza (es. "1224" diventa "12/24").
     * @param raw La stringa a 4 caratteri.
     * @return La stringa con lo slash intermedio.
     */
    private String formattaScadenza(String raw) {
        if(raw.length() > 2) return raw.substring(0, 2) + "/" + raw.substring(2);
        return raw;
    }

    /**
     * Lancia la finestra modale contenente il tastierino numerico a schermo, utile 
     * per dispositivi senza tastiera fisica.
     *
     * @param titolo    Titolo della Dialog.
     * @param maxLength Lunghezza massima del dato per fermare l'input visivo.
     * @return Il contenuto della stringa al momento della pressione di 'OK'.
     */
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

    /**
     * Mostra una finestra di avviso modale con uno stile coerente a tutto il progetto.
     *
     * @param titolo    Titolo dell'alert.
     * @param messaggio Testo dell'alert. Supporta newline `\n`.
     */
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

    /**
     * Wrapper semplificato per esporre a schermo errori in fase di pagamento o simili.
     *
     * @param msg Il messaggio di errore.
     */
    private void mostraErroreTouch(String msg) {
        mostraMessaggioTouch("ERRORE", msg);
    }
}
