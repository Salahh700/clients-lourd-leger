package vue;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import controleur.Controleur;
import controleur.Proprietaire;
import controleur.Tableau;

public class PanelProprietaire extends PanelPrincipal implements ActionListener {

    private JPanel panelForm = new JPanel();
    private JPanel panelfiltre = new JPanel();
    private JTable tableProprietaires;
    private JScrollPane ScrollProprietaires;
    private Tableau unTableau;

    private JTextField txtNom = new JTextField();
    private JTextField txtPrenom = new JTextField();
    private JTextField txtEmail = new JTextField();
    private JPasswordField txtMdp = new JPasswordField();

    private JTextField txtFiltre = new JTextField();
    private JButton btFilter = new JButton("Filtrer");

    private JButton btAnnuler = new JButton("Annuler");
    private JButton btValider = new JButton("Valider");
    private JButton btSupprimer = new JButton("Supprimer");
    private JButton btModifier = new JButton("Modifier");

    private JLabel lbNbProprietaires = new JLabel();

    public PanelProprietaire(String titre) {
        super(titre);

        this.panelForm.setBackground(AppStyle.ICE_BLUE);
        this.panelForm.setLayout(new GridLayout(6, 2, 12, 12));
        this.panelForm.setBounds(40, 70, 360, 360);
        this.panelForm.setBorder(AppStyle.CARD_BORDER);

        addFormLabel("Nom Proprietaire");
        this.panelForm.add(this.txtNom);
        addFormLabel("Prenom Proprietaire");
        this.panelForm.add(this.txtPrenom);
        addFormLabel("Email");
        this.panelForm.add(this.txtEmail);
        addFormLabel("Mot de passe");
        this.panelForm.add(this.txtMdp);
        this.panelForm.add(this.btAnnuler);
        this.panelForm.add(this.btValider);
        this.panelForm.add(this.btModifier);
        this.panelForm.add(this.btSupprimer);

        styleInput(txtNom);
        styleInput(txtPrenom);
        styleInput(txtEmail);
        styleInput(txtMdp);

        styleButton(btAnnuler, AppStyle.TEXT_SECONDARY);
        styleButton(btValider, AppStyle.SUCCESS_GREEN);
        styleButton(btModifier, AppStyle.SKY_BLUE);
        styleButton(btSupprimer, AppStyle.DANGER_RED);

        this.add(this.panelForm);

        this.btModifier.setEnabled(false);
        this.btSupprimer.setEnabled(false);

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

        String entetes[] = {"ID", "Nom", "Prenom", "Email", "Role"};
        this.unTableau = new Tableau(this.obtenirDonnes(""), entetes);
        this.tableProprietaires = new JTable(this.unTableau);
        styleTable(tableProprietaires);
        this.ScrollProprietaires = new JScrollPane(this.tableProprietaires);
        this.ScrollProprietaires.setBounds(430, 130, 600, 320);
        this.ScrollProprietaires.setBorder(AppStyle.CARD_BORDER);
        this.ScrollProprietaires.setBackground(AppStyle.SNOW_WHITE);
        this.add(this.ScrollProprietaires);

        this.tableProprietaires.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int numLigne = tableProprietaires.getSelectedRow();
                if (numLigne >= 0) {
                    txtNom.setText(unTableau.getValueAt(numLigne, 1).toString());
                    txtPrenom.setText(unTableau.getValueAt(numLigne, 2).toString());
                    txtEmail.setText(unTableau.getValueAt(numLigne, 3).toString());
                    btModifier.setEnabled(true);
                    btSupprimer.setEnabled(true);
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

        this.lbNbProprietaires.setFont(AppStyle.FONT_LABEL);
        this.lbNbProprietaires.setForeground(AppStyle.TEXT_PRIMARY);
        this.lbNbProprietaires.setText("Nombre de proprietaires : " + unTableau.getRowCount());
        this.lbNbProprietaires.setBounds(430, 460, 400, 25);
        this.add(this.lbNbProprietaires);
    }

    private void addFormLabel(String text) {
        JLabel lb = new JLabel(text);
        lb.setFont(AppStyle.FONT_LABEL);
        lb.setForeground(AppStyle.TEXT_PRIMARY);
        this.panelForm.add(lb);
    }

    private void styleInput(JTextField txt) {
        txt.setFont(AppStyle.FONT_INPUT);
        txt.setForeground(AppStyle.TEXT_PRIMARY);
        txt.setBorder(AppStyle.INPUT_BORDER);
        txt.setBackground(AppStyle.SNOW_WHITE);
        txt.setMinimumSize(new java.awt.Dimension(120, 28));
        txt.setPreferredSize(new java.awt.Dimension(120, 28));
    }

    private void styleInput(JPasswordField txt) {
        txt.setFont(AppStyle.FONT_INPUT);
        txt.setForeground(AppStyle.TEXT_PRIMARY);
        txt.setBorder(AppStyle.INPUT_BORDER);
        txt.setBackground(AppStyle.SNOW_WHITE);
        txt.setMinimumSize(new java.awt.Dimension(120, 28));
        txt.setPreferredSize(new java.awt.Dimension(120, 28));
    }

    private void styleButton(JButton btn, java.awt.Color bg) {
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
        ArrayList<Proprietaire> lesProprietaires = Controleur.selectAllProprietaires(filtre);
        Object[][] matrice = new Object[lesProprietaires.size()][5];
        int i = 0;
        for (Proprietaire unProprietaire : lesProprietaires) {
            matrice[i][0] = unProprietaire.getIdproprietaire();
            matrice[i][1] = unProprietaire.getNom();
            matrice[i][2] = unProprietaire.getPrenom();
            matrice[i][3] = unProprietaire.getEmail();
            matrice[i][4] = unProprietaire.getRole();
            i++;
        }
        return matrice;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btAnnuler) {
            this.viderchamps();
        } else if (e.getSource() == this.btValider) {
            this.traitement();
        } else if (e.getSource() == this.btFilter) {
            String filtre = this.txtFiltre.getText();
            this.unTableau.setDonnees(this.obtenirDonnes(filtre));
            this.lbNbProprietaires.setText("Nombre de proprietaires : " + unTableau.getRowCount());
        } else if (e.getSource() == this.btModifier) {
            this.updateProprietaire();
        } else if (e.getSource() == this.btSupprimer) {
            this.deleteProprietaire();
        }
    }

    public void viderchamps() {
        this.txtNom.setText("");
        this.txtPrenom.setText("");
        this.txtEmail.setText("");
        this.txtMdp.setText("");
        this.btModifier.setEnabled(false);
        this.btSupprimer.setEnabled(false);
    }

    public void deleteProprietaire() {
        int numLigne = tableProprietaires.getSelectedRow();
        int idproprietaire = Integer.parseInt(unTableau.getValueAt(numLigne, 0).toString());
        int retour = JOptionPane.showConfirmDialog(this, "Voulez-vous supprimer ce proprietaire ?", "Suppression", JOptionPane.YES_NO_OPTION);

        if (retour == 0) {
            Controleur.deleteProprietaire(idproprietaire);
            JOptionPane.showMessageDialog(this, "Le proprietaire a ete supprime avec succes");
            this.viderchamps();
            this.unTableau.setDonnees(this.obtenirDonnes(""));
            this.lbNbProprietaires.setText("Nombre de proprietaires : " + unTableau.getRowCount());
        }
    }

    public void updateProprietaire() {
        int numLigne = tableProprietaires.getSelectedRow();
        int idproprietaire = Integer.parseInt(unTableau.getValueAt(numLigne, 0).toString());
        String nom = this.txtNom.getText();
        String prenom = this.txtPrenom.getText();
        String email = this.txtEmail.getText();
        String mdp = new String(this.txtMdp.getPassword());

        if (nom.equals("") || prenom.equals("") || email.equals("") || mdp.equals("")) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
        } else {
            Proprietaire unProprietaire = new Proprietaire(idproprietaire, nom, prenom, email, mdp, "proprietaire");
            Controleur.updateProprietaire(unProprietaire);
            JOptionPane.showMessageDialog(this, "Le proprietaire a ete modifie avec succes");
            this.viderchamps();
            this.unTableau.setDonnees(this.obtenirDonnes(""));
            this.lbNbProprietaires.setText("Nombre de proprietaires : " + unTableau.getRowCount());
        }
    }

    public void traitement() {
        String nom = this.txtNom.getText();
        String prenom = this.txtPrenom.getText();
        String email = this.txtEmail.getText();
        String mdp = new String(this.txtMdp.getPassword());

        if (nom.equals("") || prenom.equals("") || email.equals("") || mdp.equals("")) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs !");
        } else {
            Proprietaire unProprietaire = new Proprietaire(nom, prenom, email, mdp, "proprietaire");
            Controleur.insertProprietaire(unProprietaire);
            JOptionPane.showMessageDialog(this, "Proprietaire insere avec succes");
            this.unTableau.setDonnees(this.obtenirDonnes(""));
            this.viderchamps();
            this.lbNbProprietaires.setText("Nombre de proprietaires : " + unTableau.getRowCount());
        }
    }
}
