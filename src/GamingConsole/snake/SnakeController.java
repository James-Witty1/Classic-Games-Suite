package GamingConsole.snake;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.*;

/**
 * Controls the game loop, user input, and interactions between the model and view
 * in a Snake game.
 * <p>
 * The SnakeController class is responsible for managing the logic of the Snake game,
 * including handling user input, updating the model, and rendering the view. It ensures
 * the game progresses at a steady pace and handles scenarios such as collisions, food spawning,
 * and GamingConsole.Snake growth.
 */
public class SnakeController {
  private final SnakeModel model;
  private final SnakeView view;
  private final Runnable onBack;
  private final Timer timer;
  private boolean turnedThisTick = false;

  /**
   * Constructs a SnakeController to manage the Snake game's core functionality,
   * including interaction between the model and view, game logic updates, and
   * user input bindings.
   *
   * @param model      the SnakeModel instance representing the game's current state
   * @param view      the SnakeView instance responsible for rendering the game to the user
   * @param onBack a Runnable that executes a callback action when the escape key is pressed
   */
  public SnakeController(SnakeModel model, SnakeView view, Runnable onBack) {
    this.model = model;
    this.view = view;
    this.onBack = onBack;
    this.timer = new Timer(90, e -> tick());
    installKeyBindings();
    reset();
    timer.start();
  }

  /**
   * Resets the game state to its initial configuration.
   * <p>
   * This method clears the current state of the GamingConsole.Snake, repositions and recreates
   * the GamingConsole.Snake at the center of the game grid with a default length, resets the
   * direction of movement, ensures the GamingConsole.Snake is marked as alive, spawns new food
   * at a valid random position, and triggers the view to repaint the board
   * reflecting these updates.
   * <p>
   * Postconditions:
   * - The GamingConsole.snake is repositioned and recreated at the center of the grid, moving
   * initially to the right.
   * - The GamingConsole.snake's direction is reset to default (rightward).
   * - The food is placed at a random valid position not occupied by the GamingConsole.snake.
   * - The game state is updated in the model and view.
   */
  public void reset() {
    model.snake.clear();
    int cx = model.cols / 2, cy = model.rows / 2;
    for (int i = 0; i < 5; i++) model.snake.addLast(new Point(cx - i, cy));
    model.dx = 1;
    model.dy = 0;
    model.alive = true;
    spawnFood();
    view.repaint();
  }

  /**
   * Spawns a new food item for the Snake game at a random position on the grid,
   * ensuring that the position does not overlap with the current GamingConsole.snake's body.
   * <p>
   * This method generates a random position within the bounds of the game grid
   * (determined by the number of columns and rows in the SnakeModel instance).
   * If the generated position is already occupied by the GamingConsole.snake, the process is
   * repeated until a valid position is found. The food position is then updated
   * in the model.
   * <p>
   * Preconditions:
   * - The game model must be initialized, including the grid dimensions and
   * the GamingConsole.snake's current state.
   * <p>
   * Postconditions:
   * - The position of the food in the model will be updated to a random grid
   * location that does not overlap with the GamingConsole.snake's body.
   */
  private void spawnFood() {
    Random r = new Random();
    do {
      model.food = new Point(r.nextInt(model.cols), r.nextInt(model.rows));
    }
    while (model.snake.contains(model.food));
  }

  /**
   * Updates the game state for each tick of the game loop.
   * <p>
   * This method is responsible for progressing the Snake game logic. It performs
   * various tasks, including checking if the GamingConsole.Snake is alive, updating the
   * GamingConsole.Snake's
   * position, handling collisions (with itself or boundaries via wrapping), and
   * checking for food consumption. After processing these events, it triggers the
   * view to repaint the board to reflect the updated state.
   * <p>
   * Preconditions:
   * - The SnakeModel and SnakeView instances must be properly initialized.
   * - `m` holds the current state of the game, including the GamingConsole.snake's position,
   * direction, game boundaries, and whether the game is still active.
   * <p>
   * Postconditions:
   * - The GamingConsole.Snake's position is updated (and wrapped if necessary).
   * - If the GamingConsole.Snake consumes the food, its length is increased and new food is placed
   * at a random valid position.
   * - The game ends if the GamingConsole.Snake collides with itself.
   * - The view is repainted to reflect changes in the game state.
   */
  private void tick() {
    if (!model.alive) {
      view.repaint();
      return;
    }
    turnedThisTick = false;
    Point head = model.snake.peekFirst();
    Point next = new Point(head.x + model.dx, head.y + model.dy);
    if (next.x < 0) next.x = model.cols - 1;
    if (next.x >= model.cols) next.x = 0;
    if (next.y < 0) next.y = model.rows - 1;
    if (next.y >= model.rows) next.y = 0;

    if (model.snake.contains(next)) {
      model.alive = false;
      view.repaint();
      return;
    }
    model.snake.addFirst(next);
    if (next.equals(model.food)) {
      spawnFood();
    } else {
      model.snake.removeLast();
    }
    view.repaint();
  }

  /**
   * Configures key bindings for controlling the Snake game and managing specific actions.
   * <p>
   * This method establishes bindings between key strokes and corresponding actions
   * in the game. It includes the following functionalities:
   * - Pressing the Escape key triggers the `onBack` Runnable to handle exiting or returning.
   * - Pressing the 'R' key resets the game state by invoking the `reset` method.
   * - Configures directional movement controls for the GamingConsole.Snake, responding to both
   * arrow keys and WASD keys. These bindings update the GamingConsole.Snake's movement
   * direction based on key inputs.
   * <p>
   * Preconditions:
   * - The `SnakeController` instance must be initialized with a `SnakeView` (`v`) and
   * a valid `Runnable` (`onBack`).
   * - The `bindDir` method is utilized to handle directional input bindings.
   * <p>
   * Postconditions:
   * - Key inputs are bound to respective game actions, enabling users to control gameplay.
   * - The game recognizes directional inputs and resets or exit triggers as appropriate.
   */
  private void installKeyBindings() {
    InputMap im = view.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    ActionMap am = view.getActionMap();
    Runnable esc = onBack;
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "menu");
    am.put("menu", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        esc.run();
      }
    });

    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), "reset");
    am.put("reset", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        reset();
      }
    });

    bindDir(im, am, KeyEvent.VK_LEFT, -1, 0);
    bindDir(im, am, KeyEvent.VK_A, -1, 0);
    bindDir(im, am, KeyEvent.VK_RIGHT, 1, 0);
    bindDir(im, am, KeyEvent.VK_D, 1, 0);
    bindDir(im, am, KeyEvent.VK_UP, 0, -1);
    bindDir(im, am, KeyEvent.VK_W, 0, -1);
    bindDir(im, am, KeyEvent.VK_DOWN, 0, 1);
    bindDir(im, am, KeyEvent.VK_S, 0, 1);
  }

  /**
   * Binds directional input keys to corresponding movement actions in the Snake game.
   *
   * @param im  the InputMap to associate key strokes with action keys
   * @param am  the ActionMap to map action keys to functionality
   * @param key the key code of the directional input being bound
   * @param ddx the change in the horizontal direction
   * @param ddy the change in the vertical direction
   */
  private void bindDir(InputMap im, ActionMap am, int key, int ddx, int ddy) {
    im.put(KeyStroke.getKeyStroke(key, 0), "dir" + key);
    am.put("dir" + key, new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (turnedThisTick) {
          return;
        }
        if (ddx == -model.dx && ddy == -model.dy) {
          return; // no instant reverse
        }
        if (ddx != 0 || ddy != 0) {
          model.dx = ddx;
          model.dy = ddy;
          turnedThisTick = true;
        }
      }
    });
  }
}
