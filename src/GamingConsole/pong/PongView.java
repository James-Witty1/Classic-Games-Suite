package GamingConsole.pong;

import java.awt.*;

import javax.swing.*;

/**
 * Represents the view component of the Pong game in a Model-View-Controller (MVC) architecture.
 * <p>
 * Key Responsibilities:
 * - Render the game's main elements (ball, paddles, walls, and center divider).
 * - Display the score and control instructions using a Heads-Up Display.
 * - Apply anti-aliasing for smoother graphics rendering.
 */
public class PongView extends JPanel {
  private final PongModel m;

  /**
   * Constructs a PongView object, which is responsible for rendering the visual elements
   * of the Pong game based on the state provided by the PongModel.
   *
   * @param m the PongModel instance representing the state of the game, including the
   *          game dimensions, ball, paddles, and scores
   */
  public PongView(PongModel m) {
    this.m = m;
    setPreferredSize(new Dimension(m.W, m.H + 40));
    setBackground(new Color(15, 15, 20));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setColor(Color.WHITE);
    for (int y = 0; y < m.H; y += 20) g2.drawLine(m.W / 2, y, m.W / 2, y + 10);
    g2.drawRect(10, 10, m.W - 20, m.H - 20);
    g2.fill(m.paddleL);
    g2.fill(m.paddleR);
    g2.fillOval(m.ball.x, m.ball.y, m.ball.width, m.ball.height);
    g2.setFont(new Font("Monospaced", Font.BOLD, 24));
    String hud = String.format("You %d  :  %d AI   (Up/Down move, R reset, ESC Menu)",
            m.scoreL, m.scoreR);
    g2.drawString(hud, 20, m.H + 30);
  }
}
