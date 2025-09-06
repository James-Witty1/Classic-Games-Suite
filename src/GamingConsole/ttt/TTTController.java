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
  private final TTTModel model;
  private final TTTView view;
  private final Runnable onBack;

  /**
   * Constructs a TTTController instance to manage the control logic of the Tic-Tac-Toe game.
   *
   * @param model the {@code TTTModel} instance representing the state of the Tic-Tac-Toe game.
   * @param view the {@code TTTView} instance used for displaying the Tic-Tac-Toe game board.
   * @param onBack a {@code Runnable} callback to be executed when navigating back to the menu.
   */
  public TTTController(TTTModel model, TTTView view, Runnable onBack) {
    this.model = model;
    this.view = view;
    this.onBack = onBack;
    view.addMouseListener(this);
    installKeyBindings();
    reset();
  }

  public void reset() {
    for (int r = 0; r < model.size; r++) Arrays.fill(model.board[r], 0);
    model.turn = 1;
    model.over = false;
    view.repaint();
  }

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

  private boolean win(int p) {
    for (int i = 0; i < model.size; i++) {
      if (model.board[i][0] == p && model.board[i][1] == p && model.board[i][2] == p) {
        return true;
      }
      if (model.board[0][i] == p && model.board[1][i] == p && model.board[2][i] == p) {
        return true;
      }
    }
    if (model.board[0][0] == p && model.board[1][1] == p && model.board[2][2] == p) {
      return true;
    }
    return model.board[0][2] == p && model.board[1][1] == p && model.board[2][0] == p;
  }

  private boolean full() {
    for (int r = 0; r < model.size; r++) {
      for (int c = 0; c < model.size; c++) {
        if (model.board[r][c] == 0) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (model.over) {
      return;
    }
    int c = e.getX() / model.cell, r = e.getY() / model.cell;
    if (r < model.size && c < model.size && model.board[r][c] == 0) {
      model.board[r][c] = model.turn;
      if (win(model.turn) || full()) {
        model.over = true;
      }
      else {
        model.turn = 3 - model.turn;
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
