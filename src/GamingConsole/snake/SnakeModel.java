package GamingConsole.snake;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Represents the model for the Snake game.
 * <p>
 * The SnakeModel class maintains the state of the Snake game, including the
 * GamingConsole.Snake's position, the position of the food, the board dimensions, and the
 * current direction of movement. It also tracks whether the GamingConsole.Snake is alive.
 * <p>
 */
public class SnakeModel {
  final int cols, rows, cell;
  Deque<Point> snake = new ArrayDeque<>();
  Point food = new Point(0, 0);
  int dx = 1, dy = 0;
  boolean alive = true;

  /**
   * Constructs a SnakeModel for the Snake game.
   *
   * @param cols the number of columns in the game grid
   * @param rows the number of rows in the game grid
   * @param cell the size of each grid cell
   */
  public SnakeModel(int cols, int rows, int cell) {
    this.cols = cols;
    this.rows = rows;
    this.cell = cell;
  }
}
