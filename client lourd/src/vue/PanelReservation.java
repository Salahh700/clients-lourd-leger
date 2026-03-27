package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import controleur.Contrat;
import controleur.Client;
import controleur.Controleur;
import controleur.Gite;
import controleur.Proprietaire;
import controleur.Reservation;
import controleur.Tableau;

public class PanelReservation extends PanelPrincipal implements ActionListener {

    private JPanel panelForm = new JPanel();
    private JPanel panelfiltre = new JPanel();
    private JTable tableReservations;
    private JScrollPane scrollReservations;
    private Tableau unTableau;

    private JSpinner spnDateDebut;
    private JSpinner spnDateFin;
    private JComboBox<String> cbxClient = new JComboBox<String>();
    private JTextField txtPrixNuit = new JTextField();
    private JTextField txtNbPersonnes = new JTextField();
    private JComboBox<String> cbxGite = new JComboBox<String>();
    private JTextField txtProprietaire = new JTextField();
    private ArrayList<Client> lesClientsCache = new ArrayList<Client>();
    private ArrayList<Gite> lesGitesCache = new ArrayList<Gite>();
    private ArrayList<Proprietaire> lesProprietairesCache = new ArrayList<Proprietaire>();
    /** Alignee sur les lignes du tableau (meme ordre que selectAllReservations). */
    private ArrayList<Reservation> lesReservationsAffichees = new ArrayList<Reservation>();

    private JTextField txtFiltre = new JTextField();
    private JButton btFilter = new JButton("Filtrer");

    private JButton btAnnuler = new JButton("Annuler");
    private JButton btValider = new JButton("Valider");
    private JButton btSupprimer = new JButton("Supprimer");
    private JButton btModifier = new JButton("Modifier");
    private JButton btContratCreer = new JButton("Creer contrat");
    private JButton btContratVoir = new JButton("Voir contrat");

    private JLabel lbNbReservations = new JLabel();

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public PanelReservation(String titre) {
        super(titre);
        this.remplirCBX();

        this.panelForm.setBackground(AppStyle.ICE_BLUE);
        this.panelForm.setLayout(new GridLayout(8, 2, 12, 12));
        this.panelForm.setBounds(40, 70, 380, 380);
        this.panelForm.setBorder(AppStyle.CARD_BORDER);

        this.spnDateDebut = createDateSpinner();
        this.spnDateFin = createDateSpinner();
        addFormLabel("Date debut :");
        this.panelForm.add(this.spnDateDebut);
        addFormLabel("Date fin :");
        this.panelForm.add(this.spnDateFin);
        addFormLabel("Client :");
        this.panelForm.add(this.cbxClient);
        addFormLabel("Nb personnes :");
        this.panelForm.add(this.txtNbPersonnes);
        addFormLabel("Gite :");
        this.panelForm.add(this.cbxGite);
        addFormLabel("Prix par nuit :");
        this.panelForm.add(this.txtPrixNuit);
        addFormLabel("Proprietaire :");
        this.panelForm.add(this.txtProprietaire);

        styleDateSpinner(spnDateDebut);
        styleDateSpinner(spnDateFin);
        styleCombo(cbxClient);
        styleInput(txtNbPersonnes);
        styleCombo(cbxGite);
        styleInput(txtPrixNuit);
        styleInput(txtProprietaire);
        this.txtPrixNuit.setEnabled(false);
        this.txtProprietaire.setEnabled(false);

        styleButton(btAnnuler, AppStyle.TEXT_SECONDARY);
        styleButton(btValider, AppStyle.SUCCESS_GREEN);
        styleButton(btModifier, AppStyle.SKY_BLUE);
        styleButton(btSupprimer, AppStyle.DANGER_RED);

        styleButton(btContratCreer, AppStyle.SUN_GOLD);
        styleButton(btContratVoir, AppStyle.SKY_BLUE);

        JPanel panelBoutons = new JPanel(new GridLayout(2, 3, 8, 8));
        panelBoutons.setBackground(AppStyle.ICE_BLUE);
        panelBoutons.add(this.btAnnuler);
        panelBoutons.add(this.btValider);
        panelBoutons.add(this.btModifier);
        panelBoutons.add(this.btSupprimer);
        panelBoutons.add(this.btContratCreer);
        panelBoutons.add(this.btContratVoir);
        /* 2 lignes + vgap : 62px coupait les boutons (bordures + police). */
        panelBoutons.setBounds(40, 455, 380, 92);

        this.add(this.panelForm);
        this.add(panelBoutons);

        this.btModifier.setEnabled(false);
        this.btSupprimer.setEnabled(false);
        this.btContratCreer.setEnabled(false);
        this.btContratVoir.setEnabled(false);

        this.btContratCreer.addActionListener(this);
        this.btContratVoir.addActionListener(this);

        this.panelfiltre.setBackground(AppStyle.ICE_BLUE);
        this.panelfiltre.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 12, 5));
        this.panelfiltre.setBounds(430, 75, 600, 50);
        this.panelfiltre.setBorder(AppStyle.CARD_BORDER);

        JLabel lbFiltre = new JLabel("Filtrer par :");
        lbFiltre.setFont(AppStyle.FONT_LABEL);
        lbFiltre.setForeground(AppStyle.TEXT_PRIMARY);
        this.panelfiltre.add(lbFiltre);
        styleInput(txtFiltre);
        this.txtFiltre.setPreferredSize(new java.awt.Dimension(280, 28));
        this.panelfiltre.add(this.txtFiltre);
        styleButton(btFilter, AppStyle.SKY_BLUE);
        this.panelfiltre.add(this.btFilter);
        this.add(this.panelfiltre);

        this.btAnnuler.addActionListener(this);
        this.btValider.addActionListener(this);
        this.btFilter.addActionListener(this);
        this.btModifier.addActionListener(this);
        this.btSupprimer.addActionListener(this);
        this.cbxClient.addActionListener(this);
        this.cbxGite.addActionListener(this);

        String entetes[] = { "ID", "Date debut", "Date fin", "Pers", "Gite", "Proprio", "Totale", "Contrat" };
        this.unTableau = new Tableau(this.obtenirDonnes(""), entetes);
        this.tableReservations = new JTable(this.unTableau);
        styleTable(tableReservations);
        this.scrollReservations = new JScrollPane(this.tableReservations);
        this.scrollReservations.setBounds(430, 130, 600, 320);
        this.scrollReservations.setBorder(AppStyle.CARD_BORDER);
        this.scrollReservations.setBackground(AppStyle.SNOW_WHITE);
        this.add(this.scrollReservations);

        this.tableReservations.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int numLigne = tableReservations.getSelectedRow();
                if (numLigne >= 0 && numLigne < lesReservationsAffichees.size()) {
                    Reservation r = lesReservationsAffichees.get(numLigne);
                    setSpinnerDate(spnDateDebut, unTableau.getValueAt(numLigne, 1).toString());
                    setSpinnerDate(spnDateFin, unTableau.getValueAt(numLigne, 2).toString());
                    setClientSelectionByNomPrenom(r.getNomClient(), r.getPrenomClient());
                    txtNbPersonnes.setText(String.valueOf(r.getNbPersonnes()));
                    for (Gite g : lesGitesCache) {
                        if (g.getIdgite() == r.getIdGite()) {
                            cbxGite.setSelectedItem(g.getIdgite() + " - " + g.getNomGite());
                            break;
                        }
                    }
                    btModifier.setEnabled(true);
                    btSupprimer.setEnabled(true);
                    btContratCreer.setEnabled(true);
                    btContratVoir.setEnabled(true);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });

        this.lbNbReservations.setFont(AppStyle.FONT_LABEL);
        this.lbNbReservations.setForeground(AppStyle.TEXT_PRIMARY);
        this.lbNbReservations.setText("Nombre de reservations : " + unTableau.getRowCount());
        this.lbNbReservations.setBounds(430, 460, 400, 25);
        this.add(this.lbNbReservations);
    }

    private void remplirCBX() {
        String selectedClient = this.cbxClient.getSelectedItem() != null ? this.cbxClient.getSelectedItem().toString() : null;
        String selectedGite = this.cbxGite.getSelectedItem() != null ? this.cbxGite.getSelectedItem().toString() : null;

        this.cbxClient.removeAllItems();
        this.cbxGite.removeAllItems();

        this.lesClientsCache = Controleur.selectAllClients("");
        for (Client unClient : this.lesClientsCache) {
            this.cbxClient.addItem(unClient.getIdclient() + " - " + unClient.getNom() + " " + unClient.getPrenom());
        }
        this.lesGitesCache = Controleur.selectAllGites("");
        for (Gite unGite : this.lesGitesCache) {
            this.cbxGite.addItem(unGite.getIdgite() + " - " + unGite.getNomGite());
        }
        this.lesProprietairesCache = Controleur.selectAllProprietaires("");

        if (selectedClient != null && isItemInCombo(cbxClient, selectedClient)) {
            this.cbxClient.setSelectedItem(selectedClient);
        } else if (cbxClient.getItemCount() > 0) {
            this.cbxClient.setSelectedIndex(0);
        }
        if (selectedGite != null && isItemInCombo(cbxGite, selectedGite)) {
            this.cbxGite.setSelectedItem(selectedGite);
        } else if (cbxGite.getItemCount() > 0) {
            this.cbxGite.setSelectedIndex(0);
        }
        updateClientFieldsFromSelection();
        updateProprietaireForSelectedGite();
        updatePrixNuitForSelectedGite();
    }

    private boolean isItemInCombo(JComboBox<String> cbx, String item) {
        for (int i = 0; i < cbx.getItemCount(); i++) {
            if (cbx.getItemAt(i).equals(item)) return true;
        }
        return false;
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            remplirCBX();
            this.unTableau.setDonnees(this.obtenirDonnes(this.txtFiltre.getText()));
            this.lbNbReservations.setText("Nombre de reservations : " + unTableau.getRowCount());
        }
    }

    private void addFormLabel(String text) {
        JLabel lb = new JLabel(text);
        lb.setFont(AppStyle.FONT_LABEL);
        lb.setForeground(AppStyle.TEXT_PRIMARY);
        this.panelForm.add(lb);
    }

    private JSpinner createDateSpinner() {
        Calendar cal = Calendar.getInstance();
        SpinnerDateModel model = new SpinnerDateModel(cal.getTime(), null, null, Calendar.DAY_OF_MONTH);
        JSpinner spinner = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "dd/MM/yyyy");
        spinner.setEditor(editor);
        return spinner;
    }

    private void styleDateSpinner(JSpinner spinner) {
        spinner.setFont(AppStyle.FONT_INPUT);
        spinner.setBackground(AppStyle.SNOW_WHITE);
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            ((JSpinner.DefaultEditor) editor).getTextField().setBorder(AppStyle.INPUT_BORDER);
            ((JSpinner.DefaultEditor) editor).getTextField().setForeground(AppStyle.TEXT_PRIMARY);
        }
    }

    private void setSpinnerDate(JSpinner spinner, String dateStr) {
        try {
            if (dateStr != null && !dateStr.isEmpty()) {
                java.util.Date d = DATE_FORMAT.parse(dateStr);
                spinner.setValue(d);
            }
        } catch (java.text.ParseException e) {
            // Garder la valeur actuelle en cas d'erreur
        }
    }

    private Date getSpinnerDateAsSql(JSpinner spinner) {
        java.util.Date d = (java.util.Date) spinner.getValue();
        return new Date(d.getTime());
    }

    private void styleInput(JTextField txt) {
        txt.setFont(AppStyle.FONT_INPUT);
        txt.setForeground(AppStyle.TEXT_PRIMARY);
        txt.setBorder(AppStyle.INPUT_BORDER);
        txt.setBackground(AppStyle.SNOW_WHITE);
        txt.setMinimumSize(new java.awt.Dimension(120, 28));
        txt.setPreferredSize(new java.awt.Dimension(120, 28));
    }

    private void styleCombo(JComboBox<String> cbx) {
        cbx.setFont(AppStyle.FONT_INPUT);
        cbx.setBackground(AppStyle.SNOW_WHITE);
        cbx.setForeground(AppStyle.TEXT_PRIMARY);
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setFont(AppStyle.FONT_BUTTON);
        btn.setForeground(AppStyle.SNOW_WHITE);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setBorder(new EmptyBorder(8, 15, 8, 15));
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    private void styleTable(JTable table) {
        table.setFont(AppStyle.FONT_INPUT);
        table.setForeground(AppStyle.TEXT_PRIMARY);
        table.setRowHeight(28);
        table.setShowGrid(true);
        table.setGridColor(AppStyle.FROST_BLUE);
        table.setSelectionBackground(AppStyle.FROST_BLUE);
        table.setSelectionForeground(AppStyle.DEEP_BLUE);
        table.setBackground(AppStyle.SNOW_WHITE);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setForeground(AppStyle.TEXT_PRIMARY);
        centerRenderer.setBackground(AppStyle.SNOW_WHITE);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JTableHeader header = table.getTableHeader();
        header.setFont(AppStyle.FONT_BUTTON);
        header.setBackground(AppStyle.DEEP_BLUE);
        header.setForeground(AppStyle.SNOW_WHITE);
        header.setReorderingAllowed(false);
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        headerRenderer.setBackground(AppStyle.DEEP_BLUE);
        headerRenderer.setForeground(AppStyle.SNOW_WHITE);
        headerRenderer.setFont(AppStyle.FONT_BUTTON);
        headerRenderer.setOpaque(true);
        header.setDefaultRenderer(headerRenderer);
    }

    private Object[][] obtenirDonnes(String filtre) {
        ArrayList<Reservation> lesReservations = Controleur.selectAllReservations(filtre);
        ArrayList<Gite> lesGites = Controleur.selectAllGites("");
        ArrayList<Proprietaire> lesProprietaires = Controleur.selectAllProprietaires("");
        Object[][] matrice = new Object[lesReservations.size()][8];
        int i = 0;
        for (Reservation uneRes : lesReservations) {
            matrice[i][0] = uneRes.getIdreservation();
            matrice[i][1] = uneRes.getDateDebutReservation() != null ? DATE_FORMAT.format(uneRes.getDateDebutReservation()) : "";
            matrice[i][2] = uneRes.getDateFinReservation() != null ? DATE_FORMAT.format(uneRes.getDateFinReservation()) : "";
            matrice[i][3] = uneRes.getNbPersonnes();
            matrice[i][4] = uneRes.getIdGite() + " - " + getGiteNom(uneRes.getIdGite(), lesGites);
            matrice[i][5] = uneRes.getIdUser() + " - " + getProprietaireNom(uneRes.getIdUser(), lesProprietaires);
            matrice[i][6] = String.format("%.2f", calculerTotalReservation(uneRes, lesGites));
            matrice[i][7] = Controleur.hasContrat(uneRes.getIdreservation()) ? "Oui" : "Non";
            i++;
        }
        this.lesReservationsAffichees = lesReservations;
        return matrice;
    }

    private double calculerTotalReservation(Reservation uneRes, ArrayList<Gite> lesGites) {
        double prixNuit = getPrixNuitByIdGite(uneRes.getIdGite(), lesGites);
        long nbJours = getNbJours(uneRes.getDateDebutReservation(), uneRes.getDateFinReservation());
        double base = prixNuit * nbJours * uneRes.getNbPersonnes();
        double ajustement = getAjustementSaisonnier(uneRes.getDateDebutReservation());
        return base * ajustement;
    }

    private double getPrixNuitByIdGite(int idGite, ArrayList<Gite> lesGites) {
        for (Gite g : lesGites) {
            if (g.getIdgite() == idGite) return g.getPrixNuitGite();
        }
        return 0.0;
    }

    private long getNbJours(Date dateDebut, Date dateFin) {
        if (dateDebut == null || dateFin == null) return 1;
        LocalDate debut = dateDebut.toLocalDate();
        LocalDate fin = dateFin.toLocalDate();
        long jours = ChronoUnit.DAYS.between(debut, fin);
        return Math.max(1, jours);
    }

    private double getAjustementSaisonnier(Date dateDebut) {
        if (dateDebut == null) return 1.0;
        int mois = dateDebut.toLocalDate().getMonthValue();
        if (mois >= 3 && mois <= 9) {
            return 1.10; // +10% mars -> septembre
        }
        return 0.90; // -10% octobre -> fevrier
    }

    private String getGiteNom(int idGite, ArrayList<Gite> lesGites) {
        for (Gite g : lesGites) {
            if (g.getIdgite() == idGite) return g.getNomGite();
        }
        return "";
    }

    private String getProprietaireNom(int idUser, ArrayList<Proprietaire> lesProprietaires) {
        for (Proprietaire p : lesProprietaires) {
            if (p.getIdproprietaire() == idUser) return p.getNom();
        }
        return "";
    }

    private void updateProprietaireForSelectedGite() {
        if (this.cbxGite.getSelectedItem() == null) {
            this.txtProprietaire.setText("");
            return;
        }
        int idGite = Integer.parseInt(this.cbxGite.getSelectedItem().toString().split(" - ")[0]);
        for (Gite g : this.lesGitesCache) {
            if (g.getIdgite() == idGite) {
                for (Proprietaire p : this.lesProprietairesCache) {
                    if (p.getIdproprietaire() == g.getIdUser()) {
                        this.txtProprietaire.setText(p.getIdproprietaire() + " - " + p.getNom());
                        return;
                    }
                }
            }
        }
        this.txtProprietaire.setText("");
        this.txtPrixNuit.setText("");
    }

    private void updatePrixNuitForSelectedGite() {
        if (this.cbxGite.getSelectedItem() == null) {
            this.txtPrixNuit.setText("");
            return;
        }
        int idGite = Integer.parseInt(this.cbxGite.getSelectedItem().toString().split(" - ")[0]);
        for (Gite g : this.lesGitesCache) {
            if (g.getIdgite() == idGite) {
                this.txtPrixNuit.setText(String.format("%.2f", g.getPrixNuitGite()));
                return;
            }
        }
        this.txtPrixNuit.setText("");
    }

    private void updateClientFieldsFromSelection() {
        // Champs nom/prenom supprimes visuellement, conserve seulement la selection client.
    }

    private void setClientSelectionByNomPrenom(String nom, String prenom) {
        for (int i = 0; i < this.cbxClient.getItemCount(); i++) {
            String item = this.cbxClient.getItemAt(i);
            int idClient = Integer.parseInt(item.split(" - ")[0]);
            for (Client c : this.lesClientsCache) {
                if (c.getIdclient() == idClient && c.getNom().equals(nom) && c.getPrenom().equals(prenom)) {
                    this.cbxClient.setSelectedIndex(i);
                    return;
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btAnnuler) {
            this.viderchamps();
        } else if (e.getSource() == this.btValider) {
            this.insertReservation();
        } else if (e.getSource() == this.btFilter) {
            String filtre = this.txtFiltre.getText();
            this.unTableau.setDonnees(this.obtenirDonnes(filtre));
            this.lbNbReservations.setText("Nombre de reservations : " + unTableau.getRowCount());
        } else if (e.getSource() == this.cbxClient) {
            updateClientFieldsFromSelection();
        } else if (e.getSource() == this.cbxGite) {
            updateProprietaireForSelectedGite();
            updatePrixNuitForSelectedGite();
        } else if (e.getSource() == this.btModifier) {
            this.updateReservation();
        } else         if (e.getSource() == this.btSupprimer) {
            this.deleteReservation();
        } else if (e.getSource() == this.btContratCreer) {
            this.creerContrat();
        } else if (e.getSource() == this.btContratVoir) {
            this.voirContrat();
        }
    }

    private void creerContrat() {
        int numLigne = tableReservations.getSelectedRow();
        if (numLigne < 0) return;
        int idReservation = Integer.parseInt(unTableau.getValueAt(numLigne, 0).toString());

        if (Controleur.hasContrat(idReservation)) {
            JOptionPane.showMessageDialog(this, "Un contrat existe deja pour cette reservation.");
            return;
        }

        JDialog dialog = new JDialog((java.awt.Frame) SwingUtilities.getWindowAncestor(this), "Creer un contrat", true);
        dialog.setLayout(new java.awt.BorderLayout(10, 10));
        dialog.getContentPane().setBackground(AppStyle.SNOW_WHITE);

        JPanel panel = new JPanel(new java.awt.GridLayout(3, 1, 5, 5));
        panel.setBackground(AppStyle.SNOW_WHITE);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JTextField txtTitre = new JTextField(30);
        txtTitre.setFont(AppStyle.FONT_INPUT);
        txtTitre.setText("Contrat - Reservation n" + idReservation);
        JTextArea txtContenu = new JTextArea(10, 40);
        txtContenu.setFont(AppStyle.FONT_INPUT);
        txtContenu.setLineWrap(true);
        txtContenu.setWrapStyleWord(true);
        String nomClientContrat = "";
        if (numLigne >= 0 && numLigne < lesReservationsAffichees.size()) {
            Reservation r = lesReservationsAffichees.get(numLigne);
            nomClientContrat = r.getNomClient() + " " + r.getPrenomClient();
        }
        String contenuDefaut = "CONTRAT DE LOCATION SAISONNIERE\n\n"
                + "Entre les parties :\n"
                + "- Le proprietaire du gite\n"
                + "- Le client : " + nomClientContrat.trim() + "\n\n"
                + "Periode : du " + unTableau.getValueAt(numLigne, 1) + " au " + unTableau.getValueAt(numLigne, 2) + "\n\n"
                + "Conditions generales : ...";
        txtContenu.setText(contenuDefaut);

        JPanel pTitre = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        pTitre.setBackground(AppStyle.SNOW_WHITE);
        pTitre.add(new JLabel("Titre :"));
        pTitre.add(txtTitre);
        panel.add(pTitre);
        panel.add(new JLabel("Contenu :"));
        panel.add(new JScrollPane(txtContenu));

        JButton btOk = new JButton("Enregistrer");
        JButton btAnnulerDialog = new JButton("Annuler");
        styleButton(btOk, AppStyle.SUCCESS_GREEN);
        styleButton(btAnnulerDialog, AppStyle.TEXT_SECONDARY);
        JPanel panelBtns = new JPanel(new java.awt.FlowLayout());
        panelBtns.setBackground(AppStyle.SNOW_WHITE);
        panelBtns.add(btOk);
        panelBtns.add(btAnnulerDialog);

        dialog.add(panel, java.awt.BorderLayout.CENTER);
        dialog.add(panelBtns, java.awt.BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(this);

        btOk.addActionListener(ev -> {
            String titre = txtTitre.getText().trim();
            String contenu = txtContenu.getText().trim();
            if (titre.isEmpty() || contenu.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Veuillez remplir le titre et le contenu.");
                return;
            }
            Date dateCreation = new Date(System.currentTimeMillis());
            Contrat unContrat = new Contrat(idReservation, dateCreation, titre, contenu);
            Controleur.insertContrat(unContrat);
            JOptionPane.showMessageDialog(this, "Contrat cree avec succes");
            unTableau.setDonnees(obtenirDonnes(txtFiltre.getText()));
            dialog.dispose();
        });
        btAnnulerDialog.addActionListener(ev -> dialog.dispose());

        dialog.setVisible(true);
    }

    private void voirContrat() {
        int numLigne = tableReservations.getSelectedRow();
        if (numLigne < 0) return;
        int idReservation = Integer.parseInt(unTableau.getValueAt(numLigne, 0).toString());

        Contrat unContrat = Controleur.selectContratByReservation(idReservation);
        if (unContrat == null) {
            JOptionPane.showMessageDialog(this, "Aucun contrat pour cette reservation.");
            return;
        }

        JDialog dialog = new JDialog((java.awt.Frame) SwingUtilities.getWindowAncestor(this), "Contrat - Reservation n" + idReservation, true);
        dialog.setLayout(new java.awt.BorderLayout(10, 10));
        dialog.getContentPane().setBackground(AppStyle.SNOW_WHITE);

        JTextArea txtAffichage = new JTextArea(15, 50);
        txtAffichage.setFont(AppStyle.FONT_INPUT);
        txtAffichage.setEditable(false);
        txtAffichage.setLineWrap(true);
        txtAffichage.setWrapStyleWord(true);
        txtAffichage.setText("Titre : " + (unContrat.getTitre() != null ? unContrat.getTitre() : "") + "\n"
                + "Date de creation : " + (unContrat.getDateCreation() != null ? DATE_FORMAT.format(unContrat.getDateCreation()) : "") + "\n\n"
                + "--- CONTENU ---\n\n" + (unContrat.getContenu() != null ? unContrat.getContenu() : ""));

        JScrollPane scroll = new JScrollPane(txtAffichage);
        scroll.setBorder(new EmptyBorder(15, 15, 15, 15));
        dialog.add(scroll, java.awt.BorderLayout.CENTER);

        JButton btFermer = new JButton("Fermer");
        JButton btModifier = new JButton("Modifier");
        JButton btSupprimer = new JButton("Supprimer");
        styleButton(btFermer, AppStyle.SKY_BLUE);
        styleButton(btModifier, AppStyle.SUN_ORANGE);
        styleButton(btSupprimer, AppStyle.DANGER_RED);
        JPanel panelBtn = new JPanel(new java.awt.FlowLayout());
        panelBtn.setBackground(AppStyle.SNOW_WHITE);
        panelBtn.add(btModifier);
        panelBtn.add(btSupprimer);
        panelBtn.add(btFermer);
        dialog.add(panelBtn, java.awt.BorderLayout.SOUTH);

        btFermer.addActionListener(ev -> dialog.dispose());
        btModifier.addActionListener(ev -> {
            dialog.dispose();
            modifierContrat(idReservation, unContrat);
        });
        btSupprimer.addActionListener(ev -> {
            int retour = JOptionPane.showConfirmDialog(dialog, "Voulez-vous supprimer ce contrat ?", "Suppression",
                    JOptionPane.YES_NO_OPTION);
            if (retour == JOptionPane.YES_OPTION) {
                Controleur.deleteContrat(unContrat.getIdContrat());
                JOptionPane.showMessageDialog(this, "Contrat supprime avec succes");
                unTableau.setDonnees(obtenirDonnes(txtFiltre.getText()));
                btContratCreer.setEnabled(true);
                btContratVoir.setEnabled(false);
                dialog.dispose();
            }
        });

        dialog.pack();
        dialog.setSize(550, 450);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void modifierContrat(int idReservation, Contrat unContrat) {
        JDialog dialog = new JDialog((java.awt.Frame) SwingUtilities.getWindowAncestor(this), "Modifier le contrat", true);
        dialog.setLayout(new java.awt.BorderLayout(10, 10));
        dialog.getContentPane().setBackground(AppStyle.SNOW_WHITE);

        JPanel panel = new JPanel(new java.awt.GridLayout(3, 1, 5, 5));
        panel.setBackground(AppStyle.SNOW_WHITE);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JTextField txtTitre = new JTextField(30);
        txtTitre.setFont(AppStyle.FONT_INPUT);
        txtTitre.setText(unContrat.getTitre() != null ? unContrat.getTitre() : "");
        JTextArea txtContenu = new JTextArea(10, 40);
        txtContenu.setFont(AppStyle.FONT_INPUT);
        txtContenu.setLineWrap(true);
        txtContenu.setWrapStyleWord(true);
        txtContenu.setText(unContrat.getContenu() != null ? unContrat.getContenu() : "");

        JPanel pTitre = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        pTitre.setBackground(AppStyle.SNOW_WHITE);
        pTitre.add(new JLabel("Titre :"));
        pTitre.add(txtTitre);
        panel.add(pTitre);
        panel.add(new JLabel("Contenu :"));
        panel.add(new JScrollPane(txtContenu));

        JButton btOk = new JButton("Enregistrer");
        JButton btAnnulerDialog = new JButton("Annuler");
        styleButton(btOk, AppStyle.SUCCESS_GREEN);
        styleButton(btAnnulerDialog, AppStyle.TEXT_SECONDARY);
        JPanel panelBtns = new JPanel(new java.awt.FlowLayout());
        panelBtns.setBackground(AppStyle.SNOW_WHITE);
        panelBtns.add(btOk);
        panelBtns.add(btAnnulerDialog);

        dialog.add(panel, java.awt.BorderLayout.CENTER);
        dialog.add(panelBtns, java.awt.BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(this);

        btOk.addActionListener(ev -> {
            String titre = txtTitre.getText().trim();
            String contenu = txtContenu.getText().trim();
            if (titre.isEmpty() || contenu.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Veuillez remplir le titre et le contenu.");
                return;
            }
            unContrat.setTitre(titre);
            unContrat.setContenu(contenu);
            Controleur.updateContrat(unContrat);
            JOptionPane.showMessageDialog(this, "Contrat modifie avec succes");
            unTableau.setDonnees(obtenirDonnes(txtFiltre.getText()));
            dialog.dispose();
        });
        btAnnulerDialog.addActionListener(ev -> dialog.dispose());

        dialog.setVisible(true);
    }

    public void viderchamps() {
        Calendar aujourdhui = Calendar.getInstance();
        this.spnDateDebut.setValue(aujourdhui.getTime());
        this.spnDateFin.setValue(aujourdhui.getTime());
        this.txtNbPersonnes.setText("1");
        if (cbxClient.getItemCount() > 0) this.cbxClient.setSelectedIndex(0);
        updateClientFieldsFromSelection();
        if (cbxGite.getItemCount() > 0) this.cbxGite.setSelectedIndex(0);
        updateProprietaireForSelectedGite();
        updatePrixNuitForSelectedGite();
        this.btModifier.setEnabled(false);
        this.btSupprimer.setEnabled(false);
        this.btContratCreer.setEnabled(false);
        this.btContratVoir.setEnabled(false);
    }

    private void insertReservation() {
        try {
            Date dateDebut = getSpinnerDateAsSql(this.spnDateDebut);
            Date dateFin = getSpinnerDateAsSql(this.spnDateFin);
            int nbPersonnes = Integer.parseInt(this.txtNbPersonnes.getText().trim());
            int idGite = Integer.parseInt(this.cbxGite.getSelectedItem().toString().split(" - ")[0]);
            int idClient = Integer.parseInt(this.cbxClient.getSelectedItem().toString().split(" - ")[0]);
            String nom = "";
            String prenom = "";
            String mail = "";
            int tel = 0;
            for (Client c : this.lesClientsCache) {
                if (c.getIdclient() == idClient) {
                    nom = c.getNom();
                    prenom = c.getPrenom();
                    mail = c.getEmail();
                    break;
                }
            }
            int idUser = -1;
            for (Gite g : this.lesGitesCache) {
                if (g.getIdgite() == idGite) {
                    idUser = g.getIdUser();
                    break;
                }
            }

            if (nom.isEmpty() || prenom.isEmpty() || mail.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs obligatoires.");
                return;
            }
            if (nbPersonnes <= 0) {
                JOptionPane.showMessageDialog(this, "Le nombre de personnes doit etre superieur a 0.");
                return;
            }
            if (idUser <= 0) {
                JOptionPane.showMessageDialog(this, "Aucun proprietaire trouve pour le gite selectionne.");
                return;
            }

            if (dateDebut.after(dateFin)) {
                JOptionPane.showMessageDialog(this, "La date de debut ne peut pas etre superieure a la date de fin.");
                return;
            }

            if (Controleur.reservationChevauche(idGite, dateDebut, dateFin, 0)) {
                JOptionPane.showMessageDialog(this, "Ce gite est deja reserve pour ces dates. Veuillez choisir d'autres dates.");
                return;
            }

            Reservation uneRes = new Reservation(dateDebut, dateFin, nom, prenom, mail, tel, nbPersonnes, idGite, idUser);
            Controleur.insertReservation(uneRes);
            JOptionPane.showMessageDialog(this, "Reservation creee avec succes");
            this.unTableau.setDonnees(this.obtenirDonnes(""));
            this.viderchamps();
            this.lbNbReservations.setText("Nombre de reservations : " + unTableau.getRowCount());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Le telephone doit etre un nombre.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Format de date invalide. Utilisez AAAA-MM-JJ (ex: 2025-02-15)");
        }
    }

    private void updateReservation() {
        try {
            int numLigne = tableReservations.getSelectedRow();
            int id = Integer.parseInt(unTableau.getValueAt(numLigne, 0).toString());
            Date dateDebut = getSpinnerDateAsSql(this.spnDateDebut);
            Date dateFin = getSpinnerDateAsSql(this.spnDateFin);
            int nbPersonnes = Integer.parseInt(this.txtNbPersonnes.getText().trim());
            int idGite = Integer.parseInt(this.cbxGite.getSelectedItem().toString().split(" - ")[0]);
            int idClient = Integer.parseInt(this.cbxClient.getSelectedItem().toString().split(" - ")[0]);
            String nom = "";
            String prenom = "";
            String mail = "";
            int tel = 0;
            for (Client c : this.lesClientsCache) {
                if (c.getIdclient() == idClient) {
                    nom = c.getNom();
                    prenom = c.getPrenom();
                    mail = c.getEmail();
                    break;
                }
            }
            int idUser = -1;
            for (Gite g : this.lesGitesCache) {
                if (g.getIdgite() == idGite) {
                    idUser = g.getIdUser();
                    break;
                }
            }

            if (nom.isEmpty() || prenom.isEmpty() || mail.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs obligatoires.");
                return;
            }
            if (nbPersonnes <= 0) {
                JOptionPane.showMessageDialog(this, "Le nombre de personnes doit etre superieur a 0.");
                return;
            }
            if (idUser <= 0) {
                JOptionPane.showMessageDialog(this, "Aucun proprietaire trouve pour le gite selectionne.");
                return;
            }

            if (dateDebut.after(dateFin)) {
                JOptionPane.showMessageDialog(this, "La date de debut ne peut pas etre superieure a la date de fin.");
                return;
            }

            if (Controleur.reservationChevauche(idGite, dateDebut, dateFin, id)) {
                JOptionPane.showMessageDialog(this, "Ce gite est deja reserve pour ces dates. Veuillez choisir d'autres dates.");
                return;
            }

            Reservation uneRes = new Reservation(id, dateDebut, dateFin, nom, prenom, mail, tel, nbPersonnes, idGite, idUser);
            Controleur.updateReservation(uneRes);
            JOptionPane.showMessageDialog(this, "Reservation modifiee avec succes");
            this.unTableau.setDonnees(this.obtenirDonnes(""));
            this.viderchamps();
            this.lbNbReservations.setText("Nombre de reservations : " + unTableau.getRowCount());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Le telephone doit etre un nombre.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Format de date invalide. Utilisez AAAA-MM-JJ");
        }
    }

    private void deleteReservation() {
        int numLigne = tableReservations.getSelectedRow();
        int id = Integer.parseInt(unTableau.getValueAt(numLigne, 0).toString());
        int retour = JOptionPane.showConfirmDialog(this, "Voulez-vous supprimer cette reservation ?", "Suppression",
                JOptionPane.YES_NO_OPTION);
        if (retour == JOptionPane.YES_OPTION) {
            Controleur.deleteReservation(id);
            JOptionPane.showMessageDialog(this, "Reservation supprimee avec succes");
            this.viderchamps();
            this.unTableau.setDonnees(this.obtenirDonnes(""));
            this.lbNbReservations.setText("Nombre de reservations : " + unTableau.getRowCount());
        }
    }
}
