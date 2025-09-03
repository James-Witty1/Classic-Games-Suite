package GamingConsole.snake;

import java.awt.*;

import javax.swing.*;

/**
 * The SnakeView class represents the graphical view for the Snake game.
 * <p>
 * SnakeView is responsible for rendering the Snake game board, the GamingConsole.snake,
 * the food, and game-related metadata such as the current length of the GamingConsole.snake
 * and the game status (e.g., "Game Over"). It makes use of the SnakeModel to
 * retrieve the current game state and updates the display accordingly.
 * <p>
 * The rendering uses anti-aliasing for smoother visuals. The grid and
 * game elements are drawn using the dimensions and positions stored
 * in the SnakeModel instance provided during construction.
 *
 */
public class SnakeView extends JPanel {
  private final SnakeModel m;

  /**
   * Constructs a SnakeView instance, responsible for rendering the graphical view
   * of the Snake game. It initializes the view's preferred size and background
   * color based on the dimensions specified in the SnakeModel.
   *
   * @param m the SnakeModel instance containing the game's state and board
   *          configuration, including the number of columns, rows, and cell size
   */
  public SnakeView(SnakeModel m) {
    this.m = m;
    setPreferredSize(new Dimension(m.cols * m.cell, m.rows * m.cell + 30));
    setBackground(new Color(20, 20, 20));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    g2.setColor(new Color(40, 40, 40));
    for (int x = 0; x <= m.cols; x++) g2.drawLine(x * m.cell, 0, x * m.cell,
            m.rows * m.cell);
    for (int y = 0; y <= m.rows; y++) g2.drawLine(0, y * m.cell, m.cols * m.cell,
            y * m.cell);

    g2.setColor(new Color(255, 90, 90));
    g2.fillOval(m.food.x * m.cell + 4, m.food.y * m.cell + 4, m.cell - 8,
            m.cell - 8);

    int i = 0;
    for (Point p : m.snake) {
      float t = (float) i / Math.max(1, m.snake.size() - 1);
      Color c = new Color(80 + (int) (120 * (1 - t)), 200 - (int) (120 * t), 120);
      g2.setColor(c);
      g2.fillRoundRect(p.x * m.cell + 2, p.y * m.cell + 2, m.cell - 4,
              m.cell - 4, 10, 10);
      i++;
    }

    g2.setColor(Color.WHITE);
    g2.setFont(new Font("Monospaced", Font.BOLD, 16));
    g2.drawString("Length: " + m.snake.size()
            + (m.alive ? "  (R to reset)" : "  — Game Over! Press R"), 10, m.rows * m.cell + 20);
  }
}
