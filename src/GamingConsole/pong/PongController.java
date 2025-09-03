package GamingConsole.pong;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.*;

/**
 * Controls the Pong game logic and user interaction in a Model-View-Controller (MVC) architecture.
 * <p>
 * PongController acts as the central component that manages the game's interaction, updates,
 * and flow by connecting the PongModel (game state) and PongView (visual representation).
 * It handles user input, game state updates, collisions, and scoring.
 * <p>
 * Key Responsibilities:
 * - Initialize and reset the game state and visuals.
 * - Handle user input through key bindings for controlling the left paddle and other actions.
 * - Implement collision detection and physics for the ball, paddles, and walls.
 * - Manage the scoring system and resetting the game upon scoring.
 * - Provide simple artificial intelligence for the right paddle to follow the ball.
 */
public class PongController {
  private final PongModel m;
  private final PongView v;
  private final Runnable onBack;
  private final Timer timer; // javax.swing.Timer explicitly imported
  private boolean up = false, down = false;

  /**
   * Constructs a PongController which serves as the controller component of the Pong game
   * in a Model-View-Controller (MVC) architecture.
   *
   * @param m      the PongModel instance representing the game state, including the ball, paddles,
   *               scores, and game dimensions
   * @param v      the PongView instance responsible for rendering the visual elements of the game
   * @param onBack a Runnable defining an action to return to the previous menu or screen
   */
  public PongController(PongModel m, PongView v, Runnable onBack) {
    this.m = m;
    this.v = v;
    this.onBack = onBack;
    this.timer = new Timer(12, e -> tick());
    installKeyBindings();
    reset();
    timer.start();
  }

  public void reset() {
    m.ball.setBounds(m.W / 2 - 8, m.H / 2 - 8, 16, 16);
    Random r = new Random();
    m.vx = r.nextBoolean() ? 4 : -4;
    m.vy = r.nextBoolean() ? 3 : -3;
    m.paddleL.setBounds(30, m.H / 2 - 40, 12, 80);
    m.paddleR.setBounds(m.W - 42, m.H / 2 - 50, 12, 100);
    v.repaint();
  }

  private void installKeyBindings() {
    InputMap im = v.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    ActionMap am = v.getActionMap();
    im.put(KeyStroke.getKeyStroke("pressed UP"), "upP");
    am.put("upP", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        up = true;
      }
    });
    im.put(KeyStroke.getKeyStroke("released UP"), "upR");
    am.put("upR", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        up = false;
      }
    });
    im.put(KeyStroke.getKeyStroke("pressed DOWN"), "dnP");
    am.put("dnP", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        down = true;
      }
    });
    im.put(KeyStroke.getKeyStroke("released DOWN"), "dnR");
    am.put("dnR", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        down = false;
      }
    });
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), "reset");
    am.put("reset", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        m.scoreL = m.scoreR = 0;
        reset();
      }
    });
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "menu");
    am.put("menu", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        onBack.run();
      }
    });
  }

  private void tick() {
    if (up) m.paddleL.y = Math.max(10, m.paddleL.y - 6);
    if (down) m.paddleL.y = Math.min(m.H - m.paddleL.height - 10, m.paddleL.y + 6);

    int targetY = m.ball.y - m.paddleR.height / 2 + 8;
    if (m.paddleR.y < targetY) m.paddleR.y += 4;
    else m.paddleR.y -= 4;
    m.paddleR.y = Math.max(10, Math.min(m.H - m.paddleR.height - 10, m.paddleR.y));

    m.ball.x += m.vx;
    m.ball.y += m.vy;

    if (m.ball.y <= 10 || m.ball.y + m.ball.height >= m.H - 10) m.vy = -m.vy;

    if (m.ball.intersects(m.paddleL)) {
      m.ball.x = m.paddleL.x + m.paddleL.width;
      m.vx = Math.abs(m.vx);
      m.vy += (m.ball.y + m.ball.height / 2 - (m.paddleL.y + m.paddleL.height / 2)) / 8;
    }
    if (m.ball.intersects(m.paddleR)) {
      m.ball.x = m.paddleR.x - m.ball.width;
      m.vx = -Math.abs(m.vx);
      m.vy += (m.ball.y + m.ball.height / 2 - (m.paddleR.y + m.paddleR.height / 2)) / 8;
    }

    int leftLine = 10;
    int rightLine = m.W - 10;
    if (m.ball.x + m.ball.width < leftLine) { // AI scores
      m.scoreR++;
      reset();
      v.repaint();
      return;
    } else if (m.ball.x > rightLine) { // You score
      m.scoreL++;
      reset();
      v.repaint();
      return;
    }

    v.repaint();
  }
}
