package vue;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controleur.Client;
import controleur.Controleur;
import controleur.Objet;

public class PanelObjets extends PanelPrincipal implements ActionListener {

    private JPanel panelForm = new JPanel();
    private JTextField txtDesignation = new JTextField();
    private JTextField txtDateAchat = new JTextField();
    private JComboBox<String> cbxEtat = new JComboBox<String>();
    private JComboBox<String> cbxIdClient = new JComboBox<String>();

    private JButton btAnnuler = new JButton("Annuler");
    private JButton btValider = new JButton("Valider");
    private JButton btSupprimer = new JButton("Supprimer");
    private JButton btModifier = new JButton("Modifier");

    public PanelObjets(String titre) {
        super(titre);
        this.remplirCBX();

        this.panelForm.setBackground(AppStyle.ICE_BLUE);
        this.panelForm.setLayout(new GridLayout(6, 2, 12, 15));
        this.panelForm.setBounds(60, 120, 340, 280);
        this.panelForm.setBorder(AppStyle.CARD_BORDER);

        addFormLabel("Designation Objet :");
        this.panelForm.add(this.txtDesignation);
        addFormLabel("Date Achat :");
        this.panelForm.add(this.txtDateAchat);
        addFormLabel("Etat de l'objet :");
        this.panelForm.add(this.cbxEtat);
        addFormLabel("Client Proprio");
        this.panelForm.add(this.cbxIdClient);
        this.panelForm.add(this.btAnnuler);
        this.panelForm.add(this.btValider);
        this.panelForm.add(this.btModifier);
        this.panelForm.add(this.btSupprimer);

        styleInput(txtDesignation);
        styleInput(txtDateAchat);
        styleCombo(cbxEtat);
        styleCombo(cbxIdClient);
        styleButton(btAnnuler, AppStyle.TEXT_SECONDARY);
        styleButton(btValider, AppStyle.SUCCESS_GREEN);
        styleButton(btModifier, AppStyle.SKY_BLUE);
        styleButton(btSupprimer, AppStyle.DANGER_RED);

        this.add(this.panelForm);

        this.btModifier.setEnabled(false);
        this.btSupprimer.setEnabled(false);

        this.btAnnuler.addActionListener(this);
        this.btValider.addActionListener(this);
        this.btModifier.addActionListener(this);
        this.btSupprimer.addActionListener(this);
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

    private void styleCombo(JComboBox<String> cbx) {
        cbx.setFont(AppStyle.FONT_INPUT);
        cbx.setForeground(AppStyle.TEXT_PRIMARY);
        cbx.setBackground(AppStyle.SNOW_WHITE);
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

    public void remplirCBX() {
        this.cbxEtat.addItem("Tres Bien");
        this.cbxEtat.addItem("Bien");
        this.cbxEtat.addItem("Neutre");
        this.cbxEtat.addItem("Mauvais");

        ArrayList<Client> lesClients = Controleur.selectAllClients("");
        for (Client unClient : lesClients) {
            this.cbxIdClient.addItem(unClient.getIdclient() + "-" + unClient.getNom());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btAnnuler) {
            this.viderchamps();
        } else if (e.getSource() == this.btValider) {
            this.insertObjet();
        } else if (e.getSource() == this.btModifier) {
            this.updateObjet();
        } else if (e.getSource() == this.btSupprimer) {
            this.deleteObjet();
        }
    }

    public void viderchamps() {
        this.txtDesignation.setText("");
        this.txtDateAchat.setText("");
        this.btModifier.setEnabled(false);
        this.btSupprimer.setEnabled(false);
    }

    private void insertObjet() {
        String designation = this.txtDesignation.getText();
        String dateAchat = this.txtDateAchat.getText();
        String etat = this.cbxEtat.getSelectedItem().toString();
        String chaine = this.cbxIdClient.getSelectedItem().toString();
        String tab[] = chaine.split("-");
        int idClient = Integer.parseInt(tab[0]);

        if (designation.equals("") || dateAchat.equals("")) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs");
        } else {
            Objet unObjet = new Objet(designation, dateAchat, etat, idClient);
            Controleur.insertObjet(unObjet);
            JOptionPane.showMessageDialog(this, "Objet insere avec succes");
            this.viderchamps();
        }
    }

    private void updateObjet() {
        // TODO
    }

    private void deleteObjet() {
        // TODO
    }
}
