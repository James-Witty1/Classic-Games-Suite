package GamingConsole.connect4;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.swing.*;

/**
 * Controls the Connect 4 game by handling user interactions and game logic.
 * <p>
 * Responsibilities:
 * - Listens to mouse and keyboard events.
 * - Validates and processes player moves.
 * - Updates the game model (board state and current player).
 * - Checks for win conditions and handles game-over scenarios.
 * - Notifies the view to repaint based on changes in the model.
 * - Maps specific key bindings for actions such as resetting the game or
 * returning to the menu.
 */
public class Connect4Controller implements MouseListener {
  private final Connect4Model model;
  private final Connect4View view;
  private final Runnable onBack;

  /**
   * Constructs a Connect4Controller instance to manage the Connect 4 game logic,
   * integrating the model, view, and user input.
   *
   * @param model  the Connect4Model representing the game's state and logic
   * @param view   the Connect4View responsible for displaying the game board and visuals
   * @param onBack a Runnable action to be executed when exiting the game
   */
  public Connect4Controller(Connect4Model model, Connect4View view, Runnable onBack) {
    this.model = model;
    this.view = view;
    this.onBack = onBack;
    view.addMouseListener(this);
    installKeyBindings();
    reset();
  }

  /**
   * Resets the Connect 4 game state to its initial conditions.
   */
  public void reset() {
    for (int r = 0; r < model.rows; r++) Arrays.fill(model.board[r], 0);
    model.current = 1;
    model.gameOver = false;
    view.repaint();
  }

  /**
   * Configures key bindings for the Connect4View to handle specific user keyboard actions.
   */
  private void installKeyBindings() {
    InputMap im = view.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    ActionMap am = view.getActionMap();
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), "reset");
    am.put("reset", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
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

  /**
   * Drops a game piece into the specified column of the Connect 4 board. This method finds the
   * lowest unoccupied row in the given column and places the current player's piece there.
   * If the column is invalid or full, it returns -1.
   *
   * @param col the column index where the game piece should be dropped, starting from 0
   * @return the row index where the piece was placed, or -1 if the column is invalid or full
   */
  private int dropIn(int col) {
    if (col < 0 || col >= model.cols) return -1;
    for (int r = model.rows - 1; r >= 0; r--) {
      if (model.board[r][col] == 0) {
        model.board[r][col] = model.current;
        return r;
      }
    }
    return -1;
  }

  /**
   * Checks if the current move at the specified row and column results in a win
   * for the player making the move. A winning condition is satisfied if there are four or more
   * consecutive game pieces (horizontally, vertically, or diagonally) for the player in question.
   *
   * @param r the row index of the recently placed game piece
   * @param c the column index of the recently placed game piece
   * @return true if the move results in a winning condition, false otherwise
   */
  private boolean checkWin(int r, int c) {
    int p = model.board[r][c];
    if (p == 0) return false;
    int[][] dir = {{1, 0}, {0, 1}, {1, 1}, {1, -1}};
    for (int[] d : dir) {
      int cnt = 1;
      cnt += count(r, c, d[0], d[1], p);
      cnt += count(r, c, -d[0], -d[1], p);
      if (cnt >= 4) return true;
    }
    return false;
  }

  /**
   * Counts the consecutive occurrences of a specified player's game piece
   * in a given direction from a starting position on the game board.
   *
   * @param r  the starting row index
   * @param c  the starting column index
   * @param dr the row increment for the direction of traversal
   * @param dc the column increment for the direction of traversal
   * @param p  the player's game piece to count (1 for player 1, 2 for player 2)
   * @return the number of consecutive game pieces for the specified player in the given direction
   */
  private int count(int r, int c, int dr, int dc, int p) {
    int n = 0;
    r += dr;
    c += dc;
    while (r >= 0 && r < model.rows && c >= 0 && c < model.cols && model.board[r][c] == p) {
      n++;
      r += dr;
      c += dc;
    }
    return n;
  }

  /**
   * Checks if the top row of the game board is completely filled (no empty cells).
   *
   * @return true if the top row of the game board is completely filled, false otherwise
   */
  private boolean fullTop() {
    for (int c = 0; c < model.cols; c++) {
      if (model.board[0][c] == 0) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (model.gameOver) {
      return;
    }
    int col = (e.getX() - 20) / model.cell;
    int row = dropIn(col);
    if (row != -1) {
      if (checkWin(row, col) || fullTop()) {
        model.gameOver = true;
      }
      else {
        model.current = 3 - model.current;
      }
      view.repaint();
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
  }

  @Override
  public void mouseReleased(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }
}
