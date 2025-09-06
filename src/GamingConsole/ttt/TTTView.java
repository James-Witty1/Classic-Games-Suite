package GamingConsole.ttt;

import java.awt.*;

import javax.swing.*;

/**
 * Represents the view component for a Tic-Tac-Toe game.
 * <p>
 * This class is a graphical representation of the Tic-Tac-Toe game board,
 * responsible for drawing the game grid, players' moves (X and O), and
 * overlaying status messages such as the current player's turn or game over indication.
 * <p>
 * Responsibilities:
 * - Renders the game grid with dividing lines.
 * - Draws players' moves based on the game state in the model (X as red lines, O as blue circles).
 * - Displays the current status of the game, such as the active player's turn or game over prompt.
 * - Handles changes in the game state by redrawing the board when necessary.
 */
public class TTTView extends JPanel {
  private final TTTModel model;

  /**
   * Constructs a TTTView instance for the Tic-Tac-Toe game.
   *
   * @param model the {@code TTTModel} instance representing the state and dimensions of the
   *              Tic-Tac-Toe game.
   */
  public TTTView(TTTModel model) {
    this.model = model;
    setPreferredSize(new Dimension(model.size * model.cell, model.size * model.cell + 40));
    setBackground(Color.WHITE);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.setStroke(new BasicStroke(4));
    g2.setColor(Color.BLACK);
    for (int i = 1; i < model.size; i++) {
      g2.drawLine(0, i * model.cell, model.size * model.cell, i * model.cell);
      g2.drawLine(i * model.cell, 0, i * model.cell, model.size * model.cell);
    }
    for (int r = 0; r < model.size; r++) {
      for (int c = 0; c < model.size; c++) {
        int x = c * model.cell, y = r * model.cell;
        if (model.board[r][c] == 1) {
          g2.setColor(new Color(200, 40, 40));
          g2.drawLine(x + 20, y + 20, x + model.cell - 20, y + model.cell - 20);
          g2.drawLine(x + model.cell - 20, y + 20, x + 20, y + model.cell - 20);
        } else if (model.board[r][c] == 2) {
          g2.setColor(new Color(40, 100, 220));
          g2.drawOval(x + 20, y + 20, model.cell - 40, model.cell - 40);
        }
      }
    }
    g2.setColor(Color.DARK_GRAY);
    g2.setFont(new Font("SansSerif", Font.BOLD, 18));
    String msg = model.over ? "Game Over — R to reset" : (model.turn == 1 ? "X's turn" : "O's turn");
    g2.drawString(msg + "   (ESC to Menu)", 10, model.size * model.cell + 28);
  }
}
