package GamingConsole.connect4;

import java.awt.*;

import javax.swing.*;

/**
 * Represents the view component for the Connect 4 game, responsible for rendering the
 * game board and updating the visuals based on the game model state.
 * <p>
 * Features:
 * - Displays the Connect 4 game board with customizable dimensions and cell sizes.
 * - Represents empty cells, as well as cells occupied by the two players (red for player 1,
 * yellow for player 2), and outlines the placement of game pieces.
 * - Provides a visual indicator for the current player's turn or game-over status through
 * a HUD.
 */
public class Connect4View extends JPanel {
  private final Connect4Model model;
  private final int margin = 20;

  /**
   * Constructs a Connect4View instance configured to display the Connect 4 game board.
   *
   * @param model the Connect4Model containing the game board configuration and state
   */
  public Connect4View(Connect4Model model) {
    this.model = model;
    setPreferredSize(new Dimension(
            model.cols * model.cell + margin * 2, model.rows * model.cell + margin * 2 + 60));
    setBackground(new Color(10, 60, 120));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    int boardX = margin;
    int boardY = margin;
    int boardW = model.cols * model.cell;
    int boardH = model.rows * model.cell;

    g2.setColor(new Color(20, 90, 170));
    g2.fillRoundRect(boardX, boardY, boardW, boardH, 25, 25);

    int hole = model.cell - 20;
    int offset = (model.cell - hole) / 2;

    for (int r = 0; r < model.rows; r++) {
      for (int c = 0; c < model.cols; c++) {
        int x = boardX + c * model.cell + offset;
        int y = boardY + r * model.cell + offset;
        g2.setColor(Color.WHITE);
        g2.fillOval(x, y, hole, hole);
        int v = model.board[r][c];
        if (v != 0) {
          g2.setColor(v == 1 ? new Color(220, 60, 60) : new Color(240, 210, 60));
          g2.fillOval(x + 3, y + 3, hole - 6, hole - 6);
        }
      }
    }

    g2.setColor(Color.BLACK);
    g2.setFont(new Font("SansSerif", Font.BOLD, 18));
    String status = model.gameOver ? "Game Over — Press R" :
            (model.current == 1 ? "Red's turn" : "Yellow's turn");
    g2.drawString(status + "   (ESC to Menu)", margin, boardY + boardH + 40);
  }
}
