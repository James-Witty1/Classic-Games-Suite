package GamingConsole.ttt;

/**
 * Represents the model for a Tic-Tac-Toe game.
 * <p>
 * Responsibilities:
 * - Tracks the dimensions of the game board.
 * - Maintains the board state, indicating empty cells, cells marked with X, or cells marked with O.
 * - Tracks the current player's turn (1 for player X, 2 for player O).
 * - Determines if the game is over.
 */
public class TTTModel {
  final int size;
  final int cell;
  int[][] board; // 0 empty, 1 X, 2 O
  int turn = 1;
  boolean over = false;

  /**
   * Constructs a TTTModel instance for a Tic-Tac-Toe game.
   *
   * @param size the size of the game board, representing the number of rows and columns
   *             (e.g., a standard Tic-Tac-Toe board would have a size of 3).
   * @param cell the dimension of each cell on the board, typically used for graphical or
   *             layout calculations.
   */
  public TTTModel(int size, int cell) {
    this.size = size;
    this.cell = cell;
    this.board = new int[size][size];
  }
}
