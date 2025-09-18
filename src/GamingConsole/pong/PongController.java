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
  private final PongModel model;
  private final PongView view;
  private final Runnable onBack;
  private final Timer timer; // javax.swing.Timer explicitly imported
  private boolean up = false;
  private boolean down = false;

  /**
   * Constructs a PongController which serves as the controller component of the Pong game
   * in a Model-View-Controller (MVC) architecture.
   *
   * @param model  the PongModel instance representing the game state, including the ball, paddles,
   *               scores, and game dimensions
   * @param view   the PongView instance responsible for rendering the visual elements of the game
   * @param onBack a Runnable defining an action to return to the previous menu or screen
   */
  public PongController(PongModel model, PongView view, Runnable onBack) {
    this.model = model;
    this.view = view;
    this.onBack = onBack;
    this.timer = new Timer(12, e -> tick());
    installKeyBindings();
    reset();
    timer.start();
  }

  public void reset() {
    model.ball.setBounds(model.Width / 2 - 8, model.Height / 2 - 8, 16, 16);
    Random r = new Random();
    model.vx = r.nextBoolean() ? 4 : -4;
    model.vy = r.nextBoolean() ? 3 : -3;
    model.paddleL.setBounds(30, model.Height / 2 - 40, 12, 80);
    model.paddleR.setBounds(model.Width - 42, model.Height / 2 - 50, 12, 100);
    view.repaint();
  }

  private void installKeyBindings() {
    InputMap im = view.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    ActionMap am = view.getActionMap();
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
        model.scoreL = model.scoreR = 0;
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
    if (up) {
      model.paddleL.y = Math.max(10, model.paddleL.y - 6);
    }
    if (down) {
      model.paddleL.y = Math.min(model.Height - model.paddleL.height - 10, model.paddleL.y + 6);
    }

    int targetY = model.ball.y - model.paddleR.height / 2 + 8;
    if (model.paddleR.y < targetY) {
      model.paddleR.y += 4;
    }
    else {
      model.paddleR.y -= 4;
    }
    model.paddleR.y = Math.max(
            10, Math.min(model.Height - model.paddleR.height - 10, model.paddleR.y));

    model.ball.x += model.vx;
    model.ball.y += model.vy;

    if (model.ball.y <= 10 || model.ball.y + model.ball.height >= model.Height - 10) {
      model.vy = -model.vy;
    }

    if (model.ball.intersects(model.paddleL)) {
      model.ball.x = model.paddleL.x + model.paddleL.width;
      model.vx = Math.abs(model.vx);
      model.vy += (model.ball.y + model.ball.height / 2 -
              (model.paddleL.y + model.paddleL.height / 2)) / 8;
    }
    if (model.ball.intersects(model.paddleR)) {
      model.ball.x = model.paddleR.x - model.ball.width;
      model.vx = -Math.abs(model.vx);
      model.vy += (model.ball.y + model.ball.height / 2 -
              (model.paddleR.y + model.paddleR.height / 2)) / 8;
    }

    int leftLine = 10;
    int rightLine = model.Width - 10;
    if (model.ball.x + model.ball.width < leftLine) { // AI scores
      model.scoreR++;
      reset();
      view.repaint();
      return;
    } else if (model.ball.x > rightLine) { // You score
      model.scoreL++;
      reset();
      view.repaint();
      return;
    }

    view.repaint();
  }
}
