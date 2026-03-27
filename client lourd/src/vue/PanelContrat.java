package vue;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import controleur.Contrat;
import controleur.Controleur;
import controleur.Gite;
import controleur.Proprietaire;
import controleur.Reservation;
import controleur.Tableau;

public class PanelContrat extends PanelPrincipal implements ActionListener {

    private JPanel panelfiltre = new JPanel();
    private JTable tableReservations;
    private JScrollPane scrollReservations;
    private Tableau unTableau;

    private JTextField txtFiltre = new JTextField();
    private JButton btFilter = new JButton("Filtrer");
    private JButton btCreerContrat = new JButton("Creer contrat");
    private JButton btOuvrirContrat = new JButton("Ouvrir contrat");

    private JLabel lbInfo = new JLabel("Selectionnez une reservation pour creer ou ouvrir un contrat");

    private ArrayList<Reservation> lesReservationsAffichees = new ArrayList<Reservation>();

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public PanelContrat(String titre) {
        super(titre);

        this.panelfiltre.setBackground(AppStyle.ICE_BLUE);
        this.panelfiltre.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 12, 5));
        this.panelfiltre.setBounds(40, 75, 1020, 50);
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
        styleButton(btCreerContrat, AppStyle.SUCCESS_GREEN);
        styleButton(btOuvrirContrat, AppStyle.SKY_BLUE);
        this.panelfiltre.add(this.btCreerContrat);
        this.panelfiltre.add(this.btOuvrirContrat);
        this.add(this.panelfiltre);

        this.btFilter.addActionListener(this);
        this.btCreerContrat.addActionListener(this);
        this.btOuvrirContrat.addActionListener(this);

        String entetes[] = { "ID", "Date debut", "Date fin", "Pers", "Gite", "Proprio", "Contrat" };
        this.unTableau = new Tableau(this.obtenirDonnes(""), entetes);
        this.tableReservations = new JTable(this.unTableau);
        styleTable(tableReservations);
        this.scrollReservations = new JScrollPane(this.tableReservations);
        this.scrollReservations.setBounds(40, 130, 1020, 350);
        this.scrollReservations.setBorder(AppStyle.CARD_BORDER);
        this.scrollReservations.setBackground(AppStyle.SNOW_WHITE);
        this.add(this.scrollReservations);

        this.tableReservations.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mettreAJourBoutons();
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

        this.lbInfo.setFont(AppStyle.FONT_LABEL);
        this.lbInfo.setForeground(AppStyle.TEXT_PRIMARY);
        this.lbInfo.setBounds(40, 490, 600, 25);
        this.add(this.lbInfo);

        this.btCreerContrat.setEnabled(false);
        this.btOuvrirContrat.setEnabled(false);
    }

    private void mettreAJourBoutons() {
        int numLigne = tableReservations.getSelectedRow();
        if (numLigne < 0) {
            btCreerContrat.setEnabled(false);
            btOuvrirContrat.setEnabled(false);
            return;
        }
        int idReservation = Integer.parseInt(unTableau.getValueAt(numLigne, 0).toString());
        boolean hasContrat = Controleur.hasContrat(idReservation);
        btCreerContrat.setEnabled(!hasContrat);
        btOuvrirContrat.setEnabled(hasContrat);
    }

    private void styleInput(JTextField txt) {
        txt.setFont(AppStyle.FONT_INPUT);
        txt.setForeground(AppStyle.TEXT_PRIMARY);
        txt.setBorder(AppStyle.INPUT_BORDER);
        txt.setBackground(AppStyle.SNOW_WHITE);
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
        Object[][] matrice = new Object[lesReservations.size()][7];
        int i = 0;
        for (Reservation uneRes : lesReservations) {
            matrice[i][0] = uneRes.getIdreservation();
            matrice[i][1] = uneRes.getDateDebutReservation() != null ? DATE_FORMAT.format(uneRes.getDateDebutReservation()) : "";
            matrice[i][2] = uneRes.getDateFinReservation() != null ? DATE_FORMAT.format(uneRes.getDateFinReservation()) : "";
            matrice[i][3] = uneRes.getNbPersonnes();
            matrice[i][4] = uneRes.getIdGite() + " - " + getGiteNom(uneRes.getIdGite(), lesGites);
            matrice[i][5] = uneRes.getIdUser() + " - " + getProprietaireNom(uneRes.getIdUser(), lesProprietaires);
            matrice[i][6] = Controleur.hasContrat(uneRes.getIdreservation()) ? "Oui" : "Non";
            i++;
        }
        this.lesReservationsAffichees = lesReservations;
        return matrice;
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

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            this.unTableau.setDonnees(this.obtenirDonnes(this.txtFiltre.getText()));
            mettreAJourBoutons();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btFilter) {
            String filtre = this.txtFiltre.getText();
            this.unTableau.setDonnees(this.obtenirDonnes(filtre));
            mettreAJourBoutons();
        } else if (e.getSource() == this.btCreerContrat) {
            creerContrat();
        } else if (e.getSource() == this.btOuvrirContrat) {
            ouvrirContrat();
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
            JOptionPane.showMessageDialog(this, "Contrat cree avec succes. Il apparaitra dans la colonne Contrat du panneau Reservations.");
            unTableau.setDonnees(obtenirDonnes(txtFiltre.getText()));
            mettreAJourBoutons();
            dialog.dispose();
        });
        btAnnulerDialog.addActionListener(ev -> dialog.dispose());

        dialog.setVisible(true);
    }

    private void ouvrirContrat() {
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
                mettreAJourBoutons();
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
            mettreAJourBoutons();
            dialog.dispose();
        });
        btAnnulerDialog.addActionListener(ev -> dialog.dispose());

        dialog.setVisible(true);
    }
}
