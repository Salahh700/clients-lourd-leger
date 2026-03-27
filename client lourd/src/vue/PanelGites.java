package vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controleur.Controleur;
import controleur.Gite;
import controleur.Proprietaire;

public class PanelGites extends PanelPrincipal implements ActionListener {

    private JPanel panelForm = new JPanel();
    private JScrollPane scrollForm;
    private JTextField txtNomGite = new JTextField();
    private JTextField txtAdresseGite = new JTextField();
    private JTextField txtVilleGite = new JTextField();
    private JTextField txtCodePostalGite = new JTextField();
    private JTextArea txtDescriptionGite = new JTextArea();
    private JScrollPane scrollDescription = new JScrollPane(txtDescriptionGite);
    private JTextField txtCapaciteGite = new JTextField();
    private JTextField txtPrixNuitGite = new JTextField();
    private JComboBox<String> cbxDisponibilite = new JComboBox<String>();
    private JComboBox<String> cbxIdProprietaire = new JComboBox<String>();

    private JButton btAnnuler = new JButton("Annuler");
    private JButton btValider = new JButton("Valider");
    private JButton btSupprimer = new JButton("Supprimer");
    private JButton btModifier = new JButton("Modifier");

    public PanelGites(String titre) {
        super(titre);
        this.remplirCBX();

        this.panelForm.setBackground(AppStyle.ICE_BLUE);
        this.panelForm.setLayout(null);
        this.panelForm.setPreferredSize(new java.awt.Dimension(520, 520));
        this.panelForm.setBorder(AppStyle.CARD_BORDER);

        int yPos = 25;
        int labelWidth = 140;
        int fieldWidth = 320;
        int fieldHeight = 30;
        int spacing = 38;

        addField("Nom du gite :", txtNomGite, yPos, labelWidth, fieldWidth, fieldHeight);
        yPos += spacing;
        addField("Adresse :", txtAdresseGite, yPos, labelWidth, fieldWidth, fieldHeight);
        yPos += spacing;
        addField("Ville :", txtVilleGite, yPos, labelWidth, fieldWidth, fieldHeight);
        yPos += spacing;
        addField("Code postal :", txtCodePostalGite, yPos, labelWidth, fieldWidth, fieldHeight);
        yPos += spacing;

        JLabel lbDescription = new JLabel("Description :");
        lbDescription.setFont(AppStyle.FONT_LABEL);
        lbDescription.setForeground(AppStyle.TEXT_PRIMARY);
        lbDescription.setBounds(15, yPos, labelWidth, 22);
        this.panelForm.add(lbDescription);
        txtDescriptionGite.setLineWrap(true);
        txtDescriptionGite.setWrapStyleWord(true);
        txtDescriptionGite.setFont(AppStyle.FONT_INPUT);
        txtDescriptionGite.setForeground(AppStyle.TEXT_PRIMARY);
        txtDescriptionGite.setBorder(AppStyle.INPUT_BORDER);
        txtDescriptionGite.setBackground(AppStyle.SNOW_WHITE);
        scrollDescription.setBounds(165, yPos, fieldWidth, 55);
        scrollDescription.setBorder(AppStyle.INPUT_BORDER);
        this.panelForm.add(this.scrollDescription);
        yPos += 65;

        addField("Capacite :", txtCapaciteGite, yPos, labelWidth, fieldWidth, fieldHeight);
        yPos += spacing;
        addField("Prix par nuit :", txtPrixNuitGite, yPos, labelWidth, fieldWidth, fieldHeight);
        yPos += spacing;

        JLabel lbDisponibilite = new JLabel("Disponibilite :");
        lbDisponibilite.setFont(AppStyle.FONT_LABEL);
        lbDisponibilite.setForeground(AppStyle.TEXT_PRIMARY);
        lbDisponibilite.setBounds(15, yPos, labelWidth, 22);
        this.panelForm.add(lbDisponibilite);
        cbxDisponibilite.setFont(AppStyle.FONT_INPUT);
        cbxDisponibilite.setForeground(AppStyle.TEXT_PRIMARY);
        cbxDisponibilite.setBackground(AppStyle.SNOW_WHITE);
        cbxDisponibilite.setBounds(165, yPos, fieldWidth, fieldHeight);
        this.panelForm.add(this.cbxDisponibilite);
        yPos += spacing;

        JLabel lbProprietaire = new JLabel("Proprietaire :");
        lbProprietaire.setFont(AppStyle.FONT_LABEL);
        lbProprietaire.setForeground(AppStyle.TEXT_PRIMARY);
        lbProprietaire.setBounds(15, yPos, labelWidth, 22);
        this.panelForm.add(lbProprietaire);
        cbxIdProprietaire.setFont(AppStyle.FONT_INPUT);
        cbxIdProprietaire.setForeground(AppStyle.TEXT_PRIMARY);
        cbxIdProprietaire.setBackground(AppStyle.SNOW_WHITE);
        cbxIdProprietaire.setBounds(165, yPos, fieldWidth, fieldHeight);
        this.panelForm.add(this.cbxIdProprietaire);
        yPos += spacing + 15;

        styleButton(btAnnuler, AppStyle.TEXT_SECONDARY);
        styleButton(btValider, AppStyle.SUCCESS_GREEN);
        styleButton(btModifier, AppStyle.SKY_BLUE);
        styleButton(btSupprimer, AppStyle.DANGER_RED);

        int btnWidth = 80;
        int btnHeight = 32;
        int btnGap = 10;
        int btnStartX = 165;
        this.btAnnuler.setBounds(btnStartX, yPos, btnWidth, btnHeight);
        this.panelForm.add(this.btAnnuler);
        this.btValider.setBounds(btnStartX + btnWidth + btnGap, yPos, btnWidth, btnHeight);
        this.panelForm.add(this.btValider);
        this.btModifier.setBounds(btnStartX + 2 * (btnWidth + btnGap), yPos, btnWidth, btnHeight);
        this.panelForm.add(this.btModifier);
        this.btSupprimer.setBounds(btnStartX + 3 * (btnWidth + btnGap), yPos, btnWidth, btnHeight);
        this.panelForm.add(this.btSupprimer);

        this.scrollForm = new JScrollPane(this.panelForm);
        this.scrollForm.setBounds(40, 70, 520, 420);
        this.scrollForm.setBorder(null);
        this.scrollForm.getViewport().setBackground(AppStyle.ICE_BLUE);
        this.scrollForm.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.scrollForm.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(this.scrollForm);

        this.btModifier.setEnabled(false);
        this.btSupprimer.setEnabled(false);

        this.btAnnuler.addActionListener(this);
        this.btValider.addActionListener(this);
        this.btModifier.addActionListener(this);
        this.btSupprimer.addActionListener(this);
    }

    private void addField(String labelText, JTextField field, int yPos, int labelWidth, int fieldWidth, int fieldHeight) {
        JLabel lb = new JLabel(labelText);
        lb.setFont(AppStyle.FONT_LABEL);
        lb.setForeground(AppStyle.TEXT_PRIMARY);
        lb.setBounds(15, yPos, labelWidth, 22);
        this.panelForm.add(lb);
        field.setFont(AppStyle.FONT_INPUT);
        field.setForeground(AppStyle.TEXT_PRIMARY);
        field.setBorder(AppStyle.INPUT_BORDER);
        field.setBackground(AppStyle.SNOW_WHITE);
        field.setBounds(165, yPos, fieldWidth, fieldHeight);
        this.panelForm.add(field);
    }

    private void styleButton(JButton btn, java.awt.Color bg) {
        btn.setFont(AppStyle.FONT_BUTTON);
        btn.setForeground(AppStyle.SNOW_WHITE);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setBorder(new EmptyBorder(6, 12, 6, 12));
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    public void remplirCBX() {
        this.cbxDisponibilite.addItem("Disponible");
        this.cbxDisponibilite.addItem("Indisponible");
        remplirProprietaires();
    }

    private void remplirProprietaires() {
        String selected = this.cbxIdProprietaire.getSelectedItem() != null ? this.cbxIdProprietaire.getSelectedItem().toString() : null;
        this.cbxIdProprietaire.removeAllItems();
        ArrayList<Proprietaire> lesProprietaires = Controleur.selectAllProprietaires("");
        for (Proprietaire unProprietaire : lesProprietaires) {
            this.cbxIdProprietaire.addItem(unProprietaire.getIdproprietaire() + "-" + unProprietaire.getNom());
        }
        if (selected != null && isItemInCombo(selected)) {
            this.cbxIdProprietaire.setSelectedItem(selected);
        } else if (this.cbxIdProprietaire.getItemCount() > 0) {
            this.cbxIdProprietaire.setSelectedIndex(0);
        }
    }

    private boolean isItemInCombo(String item) {
        for (int i = 0; i < cbxIdProprietaire.getItemCount(); i++) {
            if (cbxIdProprietaire.getItemAt(i).equals(item)) return true;
        }
        return false;
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            remplirProprietaires();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btAnnuler) {
            this.viderchamps();
        } else if (e.getSource() == this.btValider) {
            this.insertGite();
        } else if (e.getSource() == this.btModifier) {
            this.updateGite();
        } else if (e.getSource() == this.btSupprimer) {
            this.deleteGite();
        }
    }

    public void viderchamps() {
        this.txtNomGite.setText("");
        this.txtAdresseGite.setText("");
        this.txtVilleGite.setText("");
        this.txtCodePostalGite.setText("");
        this.txtDescriptionGite.setText("");
        this.txtCapaciteGite.setText("");
        this.txtPrixNuitGite.setText("");
        this.btModifier.setEnabled(false);
        this.btSupprimer.setEnabled(false);
    }

    private void insertGite() {
        try {
            String nomGite = this.txtNomGite.getText();
            String adresseGite = this.txtAdresseGite.getText();
            String villeGite = this.txtVilleGite.getText();
            int codePostalGite = Integer.parseInt(this.txtCodePostalGite.getText());
            String descriptionGite = this.txtDescriptionGite.getText();
            int capaciteGite = Integer.parseInt(this.txtCapaciteGite.getText());
            double prixNuitGite = Double.parseDouble(this.txtPrixNuitGite.getText());
            boolean disponibiliteGite = this.cbxDisponibilite.getSelectedItem().toString().equals("Disponible");
            String chaine = this.cbxIdProprietaire.getSelectedItem().toString();
            String tab[] = chaine.split("-");
            int idProprietaire = Integer.parseInt(tab[0]);

            if (nomGite.equals("") || adresseGite.equals("") || villeGite.equals("") || descriptionGite.equals("")) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs");
            } else {
                int nbAvant = Controleur.selectAllGites("").size();
                Gite unGite = new Gite(nomGite, adresseGite, villeGite, codePostalGite, descriptionGite, capaciteGite, prixNuitGite, disponibiliteGite, idProprietaire);
                Controleur.insertGite(unGite);
                int nbApres = Controleur.selectAllGites("").size();
                if (nbApres > nbAvant) {
                    JOptionPane.showMessageDialog(this, "Gite insere avec succes");
                    this.viderchamps();
                } else {
                    JOptionPane.showMessageDialog(this, "Le gite n'a pas pu etre insere. Verifie la console (erreur SQL).");
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Veuillez verifier le format des champs numeriques (code postal, capacite, prix).");
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(this, "Aucun proprietaire selectionne. Cree d'abord un proprietaire.");
        }
    }

    private void updateGite() {
        // TODO
    }

    private void deleteGite() {
        // TODO
    }
}
