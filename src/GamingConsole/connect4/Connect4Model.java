package GamingConsole.connect4;

/**
 * Represents the model for the Connect 4 game, encapsulating the game state
 * and logic.
 * <p>
 * Features:
 * - Defines the dimensions of the game board in terms of rows and columns.
 * - Stores the state of every cell on the board with a 2D integer array.
 * - Tracks the current active player (player 1 or player 2).
 * - Indicates if the game has ended.
 */
public class Connect4Model {
  final int cols;
  final int rows;
  final int cell;
  final int[][] board;
  int current = 1;
  boolean gameOver = false;

  /**
   * Constructs a Connect4Model instance with specified board dimensions and cell size.
   *
   * @param cols the number of columns on the board
   * @param rows the number of rows on the board
   * @param cell the size of each cell in pixels
   */
  public Connect4Model(int cols, int rows, int cell) {
    this.cols = cols;
    this.rows = rows;
    this.cell = cell;
    this.board = new int[rows][cols];
  }
}
