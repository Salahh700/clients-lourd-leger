package vue;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controleur.Controleur;
import controleur.NeigeEtSoleil;
import controleur.Proprietaire;

public class VueConnexion extends JFrame implements ActionListener, KeyListener {

    private JLabel lbTitre = new JLabel("Neige et Soleil");

    private JTextField txtemail = new JTextField("c@gmail.com");
    private JPasswordField txtmdpField = new JPasswordField("789");
    private JButton btCancel = new JButton("Annuler");
    private JButton btValider = new JButton("Connexion");

    private JPanel panelForm = new JPanel();

    public VueConnexion() {
        this.setTitle("Neige et Soleil - Connexion");
        this.setBounds(400, 20, 800, 360);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);

        this.getContentPane().setBackground(AppStyle.SKY_BLUE);

        // Titre principal
        lbTitre.setBounds(250, 25, 300, 40);
        lbTitre.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lbTitre.setForeground(AppStyle.SNOW_WHITE);
        lbTitre.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(lbTitre);

        // Panel formulaire stylé
        this.panelForm.setBounds(400, 80, 350, 250);
        this.panelForm.setLayout(new GridLayout(3, 2, 15, 20));
        this.panelForm.setBackground(AppStyle.SNOW_WHITE);
        this.panelForm.setBorder(AppStyle.CARD_BORDER);

        JLabel lbEmail = new JLabel("Email :");
        lbEmail.setFont(AppStyle.FONT_LABEL);
        lbEmail.setForeground(AppStyle.TEXT_PRIMARY);
        this.panelForm.add(lbEmail);

        styleInput(txtemail);
        this.panelForm.add(this.txtemail);

        JLabel lbMdp = new JLabel("Mot de passe :");
        lbMdp.setFont(AppStyle.FONT_LABEL);
        lbMdp.setForeground(AppStyle.TEXT_PRIMARY);
        this.panelForm.add(lbMdp);

        styleInput(txtmdpField);
        this.panelForm.add(this.txtmdpField);

        this.panelForm.add(new JLabel());
        JPanel panelBtns = new JPanel(new GridLayout(1, 2, 10, 0));
        panelBtns.setOpaque(false);
        styleButton(btCancel, AppStyle.TEXT_SECONDARY);
        styleButton(btValider, AppStyle.SUCCESS_GREEN);
        panelBtns.add(this.btCancel);
        panelBtns.add(this.btValider);
        this.panelForm.add(panelBtns);

        this.add(this.panelForm);

        ImageIcon uneImage = new ImageIcon("src/images/logo.png");
        JLabel lbImage = new JLabel(uneImage);
        lbImage.setBounds(30, 70, 320, 260);
        this.add(lbImage);

        this.btCancel.addActionListener(this);
        this.btValider.addActionListener(this);

        this.txtemail.addKeyListener(this);
        this.txtmdpField.addKeyListener(this);

        this.setVisible(true);
    }

    private void styleInput(JComponent comp) {
        comp.setFont(AppStyle.FONT_INPUT);
        comp.setForeground(AppStyle.TEXT_PRIMARY);
        comp.setBorder(AppStyle.INPUT_BORDER);
        comp.setBackground(AppStyle.SNOW_WHITE);
        comp.setPreferredSize(new java.awt.Dimension(200, 30));
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setFont(AppStyle.FONT_BUTTON);
        btn.setForeground(AppStyle.SNOW_WHITE);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setBorder(new EmptyBorder(10, 15, 10, 15));
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    public static void main(String args[]) {
        VueConnexion unevueConnexion = new VueConnexion();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btCancel) {
            this.viderchamp();
        }
        if (e.getSource() == this.btValider) {
            this.traitement();
        }
    }

    public void viderchamp() {
        this.txtemail.setText("");
        this.txtmdpField.setText("");
    }

    public void traitement() {
        String email = this.txtemail.getText();
        String mdp = new String(this.txtmdpField.getPassword());

        if (email.equals("") || mdp.equals("")) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs !");
        } else {
            Proprietaire unProprietaire = Controleur.selectWhereProprietaire(email, mdp);
            if (unProprietaire == null) {
                JOptionPane.showConfirmDialog(this, "Veuillez verifier vos identifiants");
            } else {
                JOptionPane.showConfirmDialog(this, "Bienvenue " + unProprietaire.getNom() + " " + unProprietaire.getPrenom());
                NeigeEtSoleil.rendreVisibleVueConnexion(false);
                NeigeEtSoleil.creerDetruireVueGeneral(true);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            traitement();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
