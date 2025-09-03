package GamingConsole.ui;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import javax.swing.*;

/**
 * GamingConsole.ui.GameCard is a custom JButton component designed to represent a card-styled UI
 * element. Each card contains a title, subtitle, and an icon that visually represents a type of
 * game. The card includes hover and click effects, gradient backgrounds, and rounded corners.
 */
class GameCard extends JButton {
  enum IconType {SNAKE, CONNECT4, TTT, PONG}

  private final String subtitle;
  private final IconType type;

  /**
   * Constructs a GamingConsole.ui.GameCard that represents a selectable card for a specific game.
   * The card includes a title, subtitle, an icon representation, and an associated
   * click action to execute when selected.
   *
   * @param title    the main title text of the card
   * @param subtitle the smaller subtitle text of the card
   * @param type     the type of icon to be displayed on the card, indicating the associated game
   * @param onClick  the action to be executed when the card is clicked
   */
  GameCard(String title, String subtitle, IconType type, Runnable onClick) {
    super(title);
    this.subtitle = subtitle;
    this.type = type;
    setHorizontalAlignment(LEFT);
    setFont(new Font("SansSerif", Font.BOLD, 20));
    setForeground(new Color(240, 242, 248));
    setContentAreaFilled(false);
    setFocusPainted(false);
    setBorderPainted(false);
    setOpaque(false);
    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    setPreferredSize(new Dimension(360, 160));
    setMargin(new Insets(12, 16, 12, 16));
    addActionListener(e -> onClick.run());
  }

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    ButtonModel model = getModel();
    boolean pressed = model.isArmed() && model.isPressed();
    boolean hover = model.isRollover();

    int w = getWidth(), h = getHeight();
    Color top = hover ? new Color(56, 96, 220) : new Color(42, 54, 88);
    Color bottom = hover ? new Color(36, 70, 180) : new Color(32, 42, 70);
    if (pressed) {
      top = top.darker();
      bottom = bottom.darker();
    }
    GradientPaint gp = new GradientPaint(0, 0, top, 0, h, bottom);
    g2.setPaint(gp);
    RoundRectangle2D r = new RoundRectangle2D.Float(0, 0, w - 1, h - 1, 24, 24);
    g2.fill(r);
    g2.setColor(new Color(255, 255, 255, 40));
    g2.draw(r);

    int iconSize = 96;
    int padding = 18;
    int iconX = w - iconSize - padding;
    int iconY = (h - iconSize) / 2;
    paintIcon(g2, iconX, iconY, iconSize, iconSize);

    g2.setColor(new Color(250, 252, 255));
    g2.setFont(getFont().deriveFont(Font.BOLD, 22f));
    g2.drawString(getText(), padding + 4, 54);
    g2.setColor(new Color(210, 218, 230));
    g2.setFont(new Font("SansSerif", Font.PLAIN, 14));
    g2.drawString(subtitle, padding + 4, 80);
  }

  /**
   * Paints an icon on the given graphics context within the specified bounds.
   * The icon is rendered based on its type, which can be one of SNAKE, CONNECT4, TTT, or PONG.
   *
   * @param g2 the Graphics2D object used for rendering
   * @param x  the x-coordinate of the top-left corner of the icon's bounding area
   * @param y  the y-coordinate of the top-left corner of the icon's bounding area
   * @param w  the width of the icon's bounding area
   * @param h  the height of the icon's bounding area
   */
  private void paintIcon(Graphics2D g2, int x, int y, int w, int h) {
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    switch (type) {
      case SNAKE:
        g2.setColor(new Color(120, 220, 140));
        int seg = 14;
        int cx = x + 8, cy = y + h / 2;
        for (int i = 0; i < 8; i++) {
          g2.fillRoundRect(cx + i * seg, cy + (int) (6 * Math.sin(i * 0.7)), 12, 12, 6, 6);
        }
        g2.setColor(new Color(30, 30, 30));
        g2.fillOval(x + w - 24, y + 10, 6, 6);
        g2.fillOval(x + w - 12, y + 18, 6, 6);
        break;
      case CONNECT4:
        g2.setColor(new Color(230, 230, 240));
        g2.fillRoundRect(x + 6, y + 8, w - 12, h - 16, 14, 14);
        for (int r = 0; r < 3; r++)
          for (int c = 0; c < 3; c++) {
            int ox = x + 18 + c * 28, oy = y + 20 + r * 28;
            g2.setColor((r + c) % 2 == 0 ? new Color(220, 60, 60) : new Color(240, 210, 60));
            g2.fillOval(ox, oy, 18, 18);
          }
        break;
      case TTT:
        g2.setStroke(new BasicStroke(4f));
        g2.setColor(new Color(240, 240, 250));
        int s = w - 20;
        int gx = x + 10, gy = y + 10;
        for (int i = 1; i <= 2; i++) {
          g2.drawLine(gx, gy + i * s / 3, gx + s, gy + i * s / 3);
          g2.drawLine(gx + i * s / 3, gy, gx + i * s / 3, gy + s);
        }
        g2.setColor(new Color(200, 40, 40));
        g2.drawLine(gx + 10, gy + 10, gx + s / 3 - 10, gy + s / 3 - 10);
        g2.drawLine(gx + s / 3 - 10, gy + 10, gx + 10, gy + s / 3 - 10);
        g2.setColor(new Color(40, 100, 220));
        g2.drawOval(gx + s / 3 + 10, gy + s / 3 + 10, s / 3 - 20, s / 3 - 20);
        break;
      case PONG:
        g2.setColor(new Color(240, 240, 250));
        g2.drawRect(x + 12, y + 12, w - 24, h - 24);
        g2.fillRect(x + 18, y + h / 2 - 18, 8, 36);
        g2.fillRect(x + w - 26, y + h / 2 - 24, 8, 48);
        g2.fillOval(x + w / 2 - 8, y + h / 2 - 8, 16, 16);
        for (int i = y + 14; i < y + h - 14; i += 10) g2.drawLine(x + w / 2, i, x + w / 2, i + 6);
        break;
    }
  }
}
