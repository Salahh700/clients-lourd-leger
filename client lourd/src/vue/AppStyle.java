package vue;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

public class AppStyle {

    // ── Couleurs ──────────────────────────────────────────────────────────────
    public static final Color DEEP_BLUE      = new Color(25,  60,  110);
    public static final Color SKY_BLUE       = new Color(70,  130, 200);
    public static final Color FROST_BLUE     = new Color(180, 210, 240);
    public static final Color ICE_BLUE       = new Color(230, 242, 255);
    public static final Color SNOW_WHITE     = new Color(255, 255, 255);

    public static final Color SUCCESS_GREEN  = new Color(60,  160,  80);
    public static final Color DANGER_RED     = new Color(200,  50,  50);
    public static final Color SUN_GOLD       = new Color(210, 160,  20);
    public static final Color SUN_ORANGE     = new Color(220, 120,  30);
    public static final Color TEXT_PRIMARY   = new Color(30,   30,  50);
    public static final Color TEXT_SECONDARY = new Color(120, 130, 145);

    // ── Polices ───────────────────────────────────────────────────────────────
    public static final Font FONT_LABEL  = new Font("Segoe UI", Font.BOLD,  13);
    public static final Font FONT_INPUT  = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD,  13);
    public static final Font FONT_TITLE  = new Font("Segoe UI", Font.BOLD,  20);

    // ── Bordures ──────────────────────────────────────────────────────────────
    public static final Border INPUT_BORDER = new CompoundBorder(
        BorderFactory.createLineBorder(FROST_BLUE, 1),
        BorderFactory.createEmptyBorder(3, 6, 3, 6)
    );

    public static final Border CARD_BORDER = new CompoundBorder(
        BorderFactory.createLineBorder(FROST_BLUE, 1),
        BorderFactory.createEmptyBorder(8, 8, 8, 8)
    );

    public static final Border PANEL_BORDER = new CompoundBorder(
        BorderFactory.createLineBorder(FROST_BLUE, 2),
        BorderFactory.createEmptyBorder(10, 10, 10, 10)
    );
}