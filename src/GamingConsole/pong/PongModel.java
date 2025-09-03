package GamingConsole.pong;

import java.awt.*;

/**
 * Represents the model for the Pong game in a Model-View-Controller (MVC) architecture.
 * <p>
 * This class contains the game's core components, including the game dimensions, the ball,
 * the paddles, and the scores. It is designed to manage the state of the game and provide
 * data to be rendered by the view and manipulated by the controller.
 */
public class PongModel {
  final int W, H;
  Rectangle ball = new Rectangle();
  int vx = 4, vy = 3;
  Rectangle paddleL = new Rectangle();
  Rectangle paddleR = new Rectangle();
  int scoreL = 0, scoreR = 0;

  /**
   * Constructs a PongModel to represent the game state with the specified dimensions.
   *
   * @param W the width of the game arena in pixels
   * @param H the height of the game arena in pixels
   */
  public PongModel(int W, int H) {
    this.W = W;
    this.H = H;
  }
}
