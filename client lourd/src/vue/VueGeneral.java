package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controleur.NeigeEtSoleil;

public class VueGeneral extends JFrame implements ActionListener {

    private JPanel panelMenue = new JPanel();
    private JButton btClients = new JButton("Clients");
    private JButton btProprietaire = new JButton("Proprietaires");
    private JButton btGites = new JButton("Gites");
    private JButton btReservations = new JButton("Reservations");
    private JButton btContrats = new JButton("Contrats");
    private JButton btStats = new JButton("Stats");
    private JButton btQuitter = new JButton("Quitter");

    private static PanelClient unPanelClient = new PanelClient("Gestion des clients");
    private static PanelProprietaire unPanelProprietaire = new PanelProprietaire("Gestion des proprietaires");
    private static PanelGites unPanelGites = new PanelGites("Gestion des gites");
    private static PanelReservation unPanelReservation = new PanelReservation("Gestion des reservations");
    private static PanelContrat unPanelContrat = new PanelContrat("Gestion des contrats");
    private static PanelStats unPanelStats = new PanelStats("Statistiques");

    public VueGeneral() {
        this.setTitle("Neige et Soleil - Application de gestion");
        this.setBounds(10, 10, 1100, 680);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);

        this.getContentPane().setBackground(AppStyle.ICE_BLUE);

        // Menu stylé
        this.panelMenue.setBounds(20, 10, 1060, 45);
        this.panelMenue.setLayout(new GridLayout(1, 7, 8, 0));
        this.panelMenue.setBackground(AppStyle.DEEP_BLUE);
        this.panelMenue.setBorder(new EmptyBorder(5, 10, 5, 10));

        styleMenuButton(btClients);
        styleMenuButton(btProprietaire);
        styleMenuButton(btGites);
        styleMenuButton(btReservations);
        styleMenuButton(btContrats);
        styleMenuButton(btStats);
        styleMenuButton(btQuitter);

        this.panelMenue.add(this.btClients);
        this.panelMenue.add(this.btProprietaire);
        this.panelMenue.add(this.btGites);
        this.panelMenue.add(this.btReservations);
        this.panelMenue.add(this.btContrats);
        this.panelMenue.add(this.btStats);
        this.panelMenue.add(this.btQuitter);

        this.add(panelMenue);

        this.btQuitter.addActionListener(this);
        this.btClients.addActionListener(this);
        this.btProprietaire.addActionListener(this);
        this.btGites.addActionListener(this);
        this.btReservations.addActionListener(this);
        this.btContrats.addActionListener(this);
        this.btStats.addActionListener(this);

        this.add(unPanelClient);
        this.add(unPanelReservation);
        this.add(unPanelProprietaire);
        this.add(unPanelGites);
        this.add(unPanelContrat);
        this.add(unPanelStats);

        this.setVisible(true);
    }

    private void styleMenuButton(JButton btn) {
        btn.setFont(AppStyle.FONT_BUTTON);
        btn.setForeground(AppStyle.SNOW_WHITE);
        btn.setBackground(AppStyle.SKY_BLUE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setBorder(new EmptyBorder(8, 5, 8, 5));
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(AppStyle.SUN_ORANGE);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (btn != btQuitter) {
                    btn.setBackground(AppStyle.SKY_BLUE);
                } else {
                    btn.setBackground(new Color(192, 57, 43));
                }
            }
        });
        if (btn == btQuitter) {
            btn.setBackground(new Color(192, 57, 43));
        }
    }

    public void afficherPanel(int choix) {
        unPanelClient.setVisible(false);
        unPanelProprietaire.setVisible(false);
        unPanelGites.setVisible(false);
        unPanelReservation.setVisible(false);
        unPanelContrat.setVisible(false);
        unPanelStats.setVisible(false);
        switch (choix) {
            case 1: unPanelClient.setVisible(true); break;
            case 2: unPanelProprietaire.setVisible(true); break;
            case 3: unPanelGites.setVisible(true); break;
            case 4: unPanelReservation.setVisible(true); break;
            case 5: unPanelContrat.setVisible(true); break;
            case 6: unPanelStats.setVisible(true); break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btQuitter) {
            NeigeEtSoleil.rendreVisibleVueConnexion(true);
            NeigeEtSoleil.creerDetruireVueGeneral(false);
        } else if (e.getSource() == this.btClients) {
            this.afficherPanel(1);
        } else if (e.getSource() == this.btProprietaire) {
            this.afficherPanel(2);
        } else if (e.getSource() == this.btGites) {
            this.afficherPanel(3);
        } else if (e.getSource() == this.btReservations) {
            this.afficherPanel(4);
        } else if (e.getSource() == this.btContrats) {
            this.afficherPanel(5);
        } else if (e.getSource() == this.btStats) {
            this.afficherPanel(6);
        }
    }
}
