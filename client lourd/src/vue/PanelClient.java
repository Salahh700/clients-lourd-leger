package vue;

import java.awt.Color;
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

import controleur.Client;
import controleur.Controleur;
import controleur.Tableau;

public class PanelClient extends PanelPrincipal implements ActionListener {

    private JPanel panelForm = new JPanel();
    private JPanel panelfiltre = new JPanel();
    private JTable tablesclient;
    private JScrollPane ScrollClients;
    private Tableau unTableau;

    private JTextField txtNom = new JTextField();
    private JTextField txtPrenom = new JTextField();
    private JTextField txtAdresse = new JTextField();
    private JTextField txtEmail = new JTextField();
    private JPasswordField txtMdp = new JPasswordField();
    private JPasswordField txtConfirmMdp = new JPasswordField();
    private JButton btVoirMdp = new JButton("Voir");
    private JButton btVoirConfirmMdp = new JButton("Voir");
    private char defaultEchoChar;

    private JTextField txtFiltre = new JTextField();
    private JButton btFilter = new JButton("Filtrer");

    private JButton btAnnuler = new JButton("Annuler");
    private JButton btValider = new JButton("Valider");
    private JButton btSupprimer = new JButton("Supprimer");
    private JButton btModifier = new JButton("Modifier");

    private JLabel lbNbClients = new JLabel();
    private ArrayList<Client> cacheClients = new ArrayList<Client>();

    private JComboBox<String> cbxStatut = new JComboBox<String>();

    public PanelClient(String titre) {
        super(titre);

        this.cbxStatut.addItem("Proprietaire");
        this.cbxStatut.addItem("Client");

        // Formulaire stylé
        this.panelForm.setBackground(AppStyle.ICE_BLUE);
        this.panelForm.setLayout(new GridLayout(9, 2, 12, 12));
        this.panelForm.setBounds(40, 70, 360, 380);
        this.panelForm.setBorder(AppStyle.CARD_BORDER);

        addFormLabel("Nom Client");
        this.panelForm.add(this.txtNom);
        addFormLabel("Prenom Client");
        this.panelForm.add(this.txtPrenom);
        addFormLabel("Adresse Postale");
        this.panelForm.add(this.txtAdresse);
        addFormLabel("Email Client");
        this.panelForm.add(this.txtEmail);
        addFormLabel("Mot de passe");
        this.panelForm.add(createPasswordPanel(this.txtMdp, this.btVoirMdp));
        addFormLabel("Confirmation");
        this.panelForm.add(createPasswordPanel(this.txtConfirmMdp, this.btVoirConfirmMdp));
        addFormLabel("Statut");
        this.panelForm.add(this.cbxStatut);

        styleInput(txtNom);
        styleInput(txtPrenom);
        styleInput(txtAdresse);
        styleInput(txtEmail);
        styleInput(txtMdp);
        styleInput(txtConfirmMdp);
        styleCombo(cbxStatut);
        this.defaultEchoChar = this.txtMdp.getEchoChar();

        this.panelForm.add(this.btAnnuler);
        this.panelForm.add(this.btValider);
        this.panelForm.add(this.btModifier);
        this.panelForm.add(this.btSupprimer);

        styleButton(btAnnuler, AppStyle.TEXT_SECONDARY);
        styleButton(btValider, AppStyle.SUCCESS_GREEN);
        styleButton(btModifier, AppStyle.SKY_BLUE);
        styleButton(btSupprimer, AppStyle.DANGER_RED);

        this.add(this.panelForm);

        this.btModifier.setEnabled(false);
        this.btSupprimer.setEnabled(false);

        // Panneau filtre
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
        this.btVoirMdp.addActionListener(this);
        this.btVoirConfirmMdp.addActionListener(this);

        // Tableau stylé
        String entetes[] = {"ID Client", "Nom", "Prenom", "Adresse", "Email", "Statut"};
        this.unTableau = new Tableau(this.obtenirDonnes(""), entetes);
        this.tablesclient = new JTable(this.unTableau);
        styleTable(tablesclient);
        this.ScrollClients = new JScrollPane(this.tablesclient);
        this.ScrollClients.setBounds(430, 130, 600, 320);
        this.ScrollClients.setBorder(AppStyle.CARD_BORDER);
        this.ScrollClients.setBackground(AppStyle.SNOW_WHITE);
        this.add(this.ScrollClients);

        this.tablesclient.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int numLigne = tablesclient.getSelectedRow();
                if (numLigne >= 0) {
                    txtNom.setText(unTableau.getValueAt(numLigne, 1).toString());
                    txtPrenom.setText(unTableau.getValueAt(numLigne, 2).toString());
                    txtAdresse.setText(unTableau.getValueAt(numLigne, 3).toString());
                    txtEmail.setText(unTableau.getValueAt(numLigne, 4).toString());
                    String statut = unTableau.getValueAt(numLigne, 5).toString();
                    cbxStatut.setSelectedItem(statut);
                    if (numLigne < cacheClients.size()) {
                        String mdp = cacheClients.get(numLigne).getMdp();
                        txtMdp.setText(mdp != null ? mdp : "");
                        txtConfirmMdp.setText(mdp != null ? mdp : "");
                    }
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

        this.lbNbClients.setFont(AppStyle.FONT_LABEL);
        this.lbNbClients.setForeground(AppStyle.TEXT_PRIMARY);
        this.lbNbClients.setText("Nombre de clients : " + unTableau.getRowCount());
        this.lbNbClients.setBounds(430, 460, 400, 25);
        this.add(this.lbNbClients);
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

    private JPanel createPasswordPanel(JPasswordField passwordField, JButton toggleButton) {
        JPanel panel = new JPanel(new java.awt.BorderLayout(6, 0));
        panel.setOpaque(false);
        panel.add(passwordField, java.awt.BorderLayout.CENTER);
        toggleButton.setFont(AppStyle.FONT_BUTTON);
        toggleButton.setBackground(AppStyle.SKY_BLUE);
        toggleButton.setForeground(AppStyle.SNOW_WHITE);
        toggleButton.setFocusPainted(false);
        toggleButton.setBorderPainted(false);
        panel.add(toggleButton, java.awt.BorderLayout.EAST);
        return panel;
    }

    private boolean isMotDePasseValide(String mdp) {
        if (mdp == null || mdp.length() < 8) return false;
        int maj = 0;
        int min = 0;
        int chiffres = 0;
        int speciaux = 0;
        for (char c : mdp.toCharArray()) {
            if (Character.isUpperCase(c)) maj++;
            else if (Character.isLowerCase(c)) min++;
            else if (Character.isDigit(c)) chiffres++;
            else speciaux++;
        }
        return maj >= 2 && min >= 2 && chiffres >= 2 && speciaux >= 2;
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
        ArrayList<Client> lesClients = Controleur.selectAllClients(filtre);
        Object[][] matrice = new Object[lesClients.size()][6];
        int i = 0;
        for (Client unClient : lesClients) {
            matrice[i][0] = unClient.getIdclient();
            matrice[i][1] = unClient.getNom();
            matrice[i][2] = unClient.getPrenom();
            matrice[i][3] = unClient.getAdresse();
            matrice[i][4] = unClient.getEmail();
            matrice[i][5] = unClient.getStatut();
            i++;
        }
        this.cacheClients = lesClients;
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
            this.lbNbClients.setText("Nombre de clients : " + unTableau.getRowCount());
        } else if (e.getSource() == this.btModifier) {
            this.updateClient();
        } else if (e.getSource() == this.btSupprimer) {
            this.deleteClient();
        } else if (e.getSource() == this.btVoirMdp) {
            boolean visible = this.txtMdp.getEchoChar() != (char) 0;
            this.txtMdp.setEchoChar(visible ? (char) 0 : this.defaultEchoChar);
            this.btVoirMdp.setText(visible ? "Masquer" : "Voir");
        } else if (e.getSource() == this.btVoirConfirmMdp) {
            boolean visible = this.txtConfirmMdp.getEchoChar() != (char) 0;
            this.txtConfirmMdp.setEchoChar(visible ? (char) 0 : this.defaultEchoChar);
            this.btVoirConfirmMdp.setText(visible ? "Masquer" : "Voir");
        }
    }

    public void viderchamps() {
        this.txtNom.setText("");
        this.txtPrenom.setText("");
        this.txtAdresse.setText("");
        this.txtEmail.setText("");
        this.txtMdp.setText("");
        this.txtConfirmMdp.setText("");
        this.txtMdp.setEchoChar(this.defaultEchoChar);
        this.txtConfirmMdp.setEchoChar(this.defaultEchoChar);
        this.btVoirMdp.setText("Voir");
        this.btVoirConfirmMdp.setText("Voir");
        this.cbxStatut.setSelectedIndex(0);
        this.btModifier.setEnabled(false);
        this.btSupprimer.setEnabled(false);
    }

    public void deleteClient() {
        int numLigne = tablesclient.getSelectedRow();
        int idclient = Integer.parseInt(unTableau.getValueAt(numLigne, 0).toString());
        int retour = JOptionPane.showConfirmDialog(this, "Voulez-vous supprimer ce client ?", "Suppression", JOptionPane.YES_NO_OPTION);

        if (retour == 0) {
            Controleur.deleteClient(idclient);
            JOptionPane.showMessageDialog(this, "Le client a ete supprime avec succes");
            this.viderchamps();
            this.unTableau.setDonnees(this.obtenirDonnes(""));
            this.lbNbClients.setText("Nombre de clients : " + unTableau.getRowCount());
        }
    }

    public void updateClient() {
        int numLigne = tablesclient.getSelectedRow();
        int idclient = Integer.parseInt(unTableau.getValueAt(numLigne, 0).toString());
        String nom = this.txtNom.getText();
        String prenom = this.txtPrenom.getText();
        String adresse = this.txtAdresse.getText();
        String email = this.txtEmail.getText();
        String mdp = new String(this.txtMdp.getPassword());
        String confirmMdp = new String(this.txtConfirmMdp.getPassword());
        String statut = this.cbxStatut.getSelectedItem().toString();

        if (nom.equals("") || prenom.equals("") || adresse.equals("") || email.equals("")) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
        } else if (!mdp.equals(confirmMdp)) {
            JOptionPane.showMessageDialog(this, "La confirmation du mot de passe ne correspond pas.");
        } else if (!isMotDePasseValide(mdp)) {
            JOptionPane.showMessageDialog(this, "Le mot de passe doit contenir au minimum 8 caracteres, 2 majuscules, 2 minuscules, 2 chiffres et 2 caracteres speciaux.");
        } else {
            Client unClient = new Client(idclient, nom, prenom, adresse, email, statut, mdp);
            Controleur.updateClient(unClient);
            JOptionPane.showMessageDialog(this, "Le client a ete modifie avec succes");
            this.viderchamps();
            this.unTableau.setDonnees(this.obtenirDonnes(""));
            this.lbNbClients.setText("Nombre de clients : " + unTableau.getRowCount());
        }
    }

    public void traitement() {
        String nom = this.txtNom.getText();
        String prenom = this.txtPrenom.getText();
        String adresse = this.txtAdresse.getText();
        String email = this.txtEmail.getText();
        String mdp = new String(this.txtMdp.getPassword());
        String confirmMdp = new String(this.txtConfirmMdp.getPassword());
        String statut = this.cbxStatut.getSelectedItem().toString();

        if (nom.equals("") || prenom.equals("") || adresse.equals("") || email.equals("")) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
        } else if (!mdp.equals(confirmMdp)) {
            JOptionPane.showMessageDialog(this, "La confirmation du mot de passe ne correspond pas.");
        } else if (!isMotDePasseValide(mdp)) {
            JOptionPane.showMessageDialog(this, "Le mot de passe doit contenir au minimum 8 caracteres, 2 majuscules, 2 minuscules, 2 chiffres et 2 caracteres speciaux.");
        } else {
            Client unClient = new Client(nom, prenom, adresse, email, statut, mdp);
            Controleur.insertClient(unClient);
            JOptionPane.showMessageDialog(this, "Client insere avec succes");
            this.unTableau.setDonnees(this.obtenirDonnes(""));
            this.viderchamps();
            this.lbNbClients.setText("Nombre de clients : " + unTableau.getRowCount());
        }
    }
}
