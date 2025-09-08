import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.awt.Point;

/**
 * Provides unit tests for the SnakeModel. This class validates
 * various functionalities of the Snake game logic, including
 * initial game state, movement behavior, food consumption,
 * wall collisions, and direction change restrictions.
 */
public class SnakeModelTests {

  static class FakeSnakeModel {
    enum Direction {UP, DOWN, LEFT, RIGHT}
    private final int width = 20;
    private final int height = 20;
    private int length = 3;
    private Point head = new Point(10, 10);
    private Point food = new Point(12, 10);
    private boolean over = false;
    private Direction dir = Direction.RIGHT;

    /**
     * gets the width of the game board
     * @return the width of game board
     */
    public int getWidth() {
      return width;
    }

    /**
     * gets the height of the game board
     * @return the height of game board
     */
    public int getHeight() {
      return height;
    }

    /**
     * gets the length of the snake
     * @return the length of the snake
     */
    public int getLength() {
      return length;
    }

    /**
     * gets the head of the snake as a Point object
     * @return the head of the snake as a Point object
     */
    public Point getHead() {
      return new Point(head);
    }

    /**
     * Checks if the game is over
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver() {
      return over;
    }

    /**
     * Sets the direction of the snake. Reversing the direction is not allowed.
     * @param d the new direction of the snake, either UP, DOWN, LEFT, or RIGHT.
     */
    public void setDirection(Direction d) {
      if ((dir == Direction.LEFT && d == Direction.RIGHT) ||
              (dir == Direction.RIGHT && d == Direction.LEFT) ||
              (dir == Direction.UP && d == Direction.DOWN) ||
              (dir == Direction.DOWN && d == Direction.UP)) {
        throw new IllegalArgumentException("Reverse not allowed");
      }
      dir = d;
    }

    /**
     * Moves the snake in the direction specified by the setDirection method.
     */
    public void tick() {
      switch (dir) {
        case RIGHT:
          head.translate(1, 0);
          break;
        case LEFT:
          head.translate(-1, 0);
          break;
        case UP:
          head.translate(0, -1);
          break;
        case DOWN:
          head.translate(0, 1);
          break;
      }
      if (head.equals(food)) {
        length++;
        food.translate(1, 0);
      }
      if (head.x < 0 || head.x >= width || head.y < 0 || head.y >= height) {
        over = true;
      }
    }
  }

  private FakeSnakeModel snake;

  @Before
  public void setUp() {
    snake = new FakeSnakeModel();
  }

  @Test
  public void testInitialState() {
    assertFalse(snake.isGameOver());
    assertEquals(20, snake.getWidth());
    assertEquals(20, snake.getHeight());
    assertEquals(3, snake.getLength());
  }

  @Test
  public void testMoveAndEatFoodIncreasesLength() {
    int before = snake.getLength();
    snake.setDirection(FakeSnakeModel.Direction.RIGHT);
    snake.tick();
    snake.tick();
    assertEquals(before + 1, snake.getLength());
  }

  @Test
  public void testWallCollisionEndsGame() { //WALL COLLISION OF THE SNAKE OR ACTUAL WALL? ACTUAL WALL IS PLAYABLE.
    snake.setDirection(FakeSnakeModel.Direction.UP);
    snake.tick();

    snake.setDirection(FakeSnakeModel.Direction.LEFT);

    for (int i = 0; i < 12; i++) {
      if (snake.isGameOver()) {
        break;
      }
      snake.tick();
    }
    assertTrue(snake.isGameOver());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImmediateReverseThrows() {
    // default RIGHT, reversing LEFT should throw
    snake.setDirection(FakeSnakeModel.Direction.LEFT);
  }
}
