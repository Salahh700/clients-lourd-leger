package vue;

import java.awt.*;

import javax.swing.*;

public abstract class PanelPrincipal extends JPanel {

    public PanelPrincipal(String titre) {
        this.setBounds(20, 60, 1060, 550);
        this.setBackground(AppStyle.SNOW_WHITE);
        this.setLayout(null);
        this.setBorder(AppStyle.PANEL_BORDER);

        JLabel lbTitre = new JLabel(titre);
        lbTitre.setBounds(300, 15, 460, 35);
        lbTitre.setFont(AppStyle.FONT_TITLE);
        lbTitre.setForeground(AppStyle.DEEP_BLUE);
        lbTitre.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(lbTitre);

        this.setVisible(false);
    }
}
