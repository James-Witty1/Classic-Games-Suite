package GamingConsole.snake;

import java.awt.*;

import javax.swing.*;

/**
 * The SnakeView class represents the graphical view for the Snake game.
 * <p>
 * SnakeView is responsible for rendering the Snake game board, the GamingConsole.Snake,
 * the food, and game-related metadata such as the current length of the GamingConsole.Snake
 * and the game status (e.g., "Game Over"). It makes use of the SnakeModel to
 * retrieve the current game state and updates the display accordingly.
 *
 */
public class SnakeView extends JPanel {
  private final SnakeModel model;

  /**
   * Constructs a SnakeView instance, responsible for rendering the graphical view
   * of the Snake game. It initializes the view's preferred size and background
   * color based on the dimensions specified in the SnakeModel.
   *
   * @param model the SnakeModel instance containing the game's state and board
   *          configuration, including the number of columns, rows, and cell size
   */
  public SnakeView(SnakeModel model) {
    this.model = model;
    setPreferredSize(new Dimension(model.cols * model.cell, model.rows * model.cell + 30));
    setBackground(new Color(20, 20, 20));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    g2.setColor(new Color(40, 40, 40));
    for (int x = 0; x <= model.cols; x++) g2.drawLine(x * model.cell, 0, x * model.cell,
            model.rows * model.cell);
    for (int y = 0; y <= model.rows; y++) g2.drawLine(0, y * model.cell, model.cols * model.cell,
            y * model.cell);

    g2.setColor(new Color(255, 90, 90));
    g2.fillOval(model.food.x * model.cell + 4, model.food.y * model.cell + 4, model.cell - 8,
            model.cell - 8);

    int i = 0;
    for (Point p : model.snake) {
      float t = (float) i / Math.max(1, model.snake.size() - 1);
      Color c = new Color(0,255,156);
      g2.setColor(c);
      g2.fillRoundRect(p.x * model.cell + 2, p.y * model.cell + 2, model.cell - 4,
              model.cell - 4, 10, 10);
      i++;
    }

    g2.setColor(Color.WHITE);
    g2.setFont(new Font("Monospaced", Font.BOLD, 16));
    g2.drawString("Length: " + model.snake.size()
            + (model.alive ? "  (R to reset)" : "  — Game Over! Press R"),
            10, model.rows * model.cell + 20);
  }
}
