package GamingConsole.ttt;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.swing.*;

/**
 * The TTTController class manages the control logic for a Tic Tac Toe game.
 * <p>
 * Responsibilities:
 * - Handles user input, including mouse clicks to make moves and keyboard shortcuts for resetting
 *   the game or returning to the menu.
 * - Updates the game state based on user actions using the model.
 * - Triggers updates to the view to reflect game state changes.
 * - Ensures the game rules are followed, such as checking for win conditions or tied games.
 */
public class TTTController implements MouseListener {
  private final TTTModel m;
  private final TTTView v;
  private final Runnable onBack;

  /**
   * Constructs a TTTController instance to manage the control logic of the Tic-Tac-Toe game.
   *
   * @param m the {@code TTTModel} instance representing the state of the Tic-Tac-Toe game.
   * @param v the {@code TTTView} instance used for displaying the Tic-Tac-Toe game board.
   * @param onBack a {@code Runnable} callback to be executed when navigating back to the menu.
   */
  public TTTController(TTTModel m, TTTView v, Runnable onBack) {
    this.m = m;
    this.v = v;
    this.onBack = onBack;
    v.addMouseListener(this);
    installKeyBindings();
    reset();
  }

  public void reset() {
    for (int r = 0; r < m.size; r++) Arrays.fill(m.board[r], 0);
    m.turn = 1;
    m.over = false;
    v.repaint();
  }

  private void installKeyBindings() {
    InputMap im = v.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    ActionMap am = v.getActionMap();
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

  private boolean win(int p) {
    for (int i = 0; i < m.size; i++) {
      if (m.board[i][0] == p && m.board[i][1] == p && m.board[i][2] == p) {
        return true;
      }
      if (m.board[0][i] == p && m.board[1][i] == p && m.board[2][i] == p) {
        return true;
      }
    }
    if (m.board[0][0] == p && m.board[1][1] == p && m.board[2][2] == p) {
      return true;
    }
    return m.board[0][2] == p && m.board[1][1] == p && m.board[2][0] == p;
  }

  private boolean full() {
    for (int r = 0; r < m.size; r++) {
      for (int c = 0; c < m.size; c++) {
        if (m.board[r][c] == 0) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (m.over) return;
    int c = e.getX() / m.cell, r = e.getY() / m.cell;
    if (r < m.size && c < m.size && m.board[r][c] == 0) {
      m.board[r][c] = m.turn;
      if (win(m.turn) || full()) m.over = true;
      else m.turn = 3 - m.turn;
      v.repaint();
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
