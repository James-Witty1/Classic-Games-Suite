package GamingConsole.ui;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import GamingConsole.connect4.Connect4Controller;
import GamingConsole.connect4.Connect4Model;
import GamingConsole.connect4.Connect4View;
import GamingConsole.pong.PongController;
import GamingConsole.pong.PongModel;
import GamingConsole.pong.PongView;
import GamingConsole.snake.SnakeController;
import GamingConsole.snake.SnakeModel;
import GamingConsole.snake.SnakeView;
import GamingConsole.ttt.TTTController;
import GamingConsole.ttt.TTTModel;
import GamingConsole.ttt.TTTView;


/**
 * GamingConsole.ui.GameHub is the main application class that serves as the entry point
 * and graphical interface for the Game Hub. It extends JFrame and provides
 * a unified interface to play four different games: Snake, Connect 4, Tic‑Tac‑Toe, and Pong.
 *
 * Using a card layout, this class manages the navigation between the main menu
 * and individual game views. Each game is managed using a separate Model-View-Controller
 * (MVC) architecture to ensure separation of concerns and modularity.
 *
 * Features:
 * - Main menu interface for game selection.
 * - Integration of four different games with their corresponding controllers.
 * - Context-aware navigation using the CardLayout.
 * - Automatic game reset when switching between games for a fresh start.
 * - Fixed window size and positioning for a consistent user experience.
 * - Key binding support for seamless menu and game controls.
 */
public class GameHub extends JFrame {
  private final CardLayout cards = new CardLayout();
  private final JPanel root = new JPanel(cards);

  private SnakeController snakeCtl;
  private Connect4Controller c4Ctl;
  private TTTController tttCtl;
  private PongController pongCtl;

  /**
   * Constructs the GamingConsole.ui.GameHub application window.
   *
   * This is the main entry point for the Game Hub which includes four games:
   * Snake, Connect 4, Tic-Tac-Toe, and Pong. The constructor initializes the
   * JFrame properties, the main menu, and the individual game modules using
   * the Model-View-Controller (MVC) pattern. It also sets up the navigation
   * routing between the main menu and the game screens.
   *
   * Components:
   * - Initializes the main menu with options to launch each game.
   * - Generates specific MVC components for each game:
   *   - Snake: Includes a game board, view, and controller.
   *   - Connect 4: Sets up the gameplay grid, view, and controller.
   *   - Tic-Tac-Toe: Prepares the 3x3 game setup, view, and controller.
   *   - Pong: Establishes the game arena, view, and controller.
   * - Adds all components to a card layout for navigation.
   *
   */
  public GameHub() {
    super("Game Hub — Snake • Connect 4 • Tic‑Tac‑Toe • Pong");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(920, 720);
    setLocationRelativeTo(null);
    setResizable(false);

    MenuView menu = new MenuView(
            () -> open("snake"),
            () -> open("c4"),
            () -> open("ttt"),
            () -> open("pong"));


    SnakeModel snakeModel = new SnakeModel(30, 22, 25);
    SnakeView snakeView = new SnakeView(snakeModel);
    snakeCtl = new SnakeController(snakeModel, snakeView, this::openMenu);

    Connect4Model c4Model = new Connect4Model(7, 6, 90);
    Connect4View c4View = new Connect4View(c4Model);
    c4Ctl = new Connect4Controller(c4Model, c4View, this::openMenu);

    TTTModel tttModel = new TTTModel(3, 150);
    TTTView tttView = new TTTView(tttModel);
    tttCtl = new TTTController(tttModel, tttView, this::openMenu);

    PongModel pongModel = new PongModel(800, 520);
    PongView pongView = new PongView(pongModel);
    pongCtl = new PongController(pongModel, pongView, this::openMenu);

    root.add(menu, "menu");
    root.add(wrapWithTopBar("Snake", snakeView, this::openMenu), "snake");
    root.add(wrapWithTopBar("Connect 4", c4View, this::openMenu), "c4");
    root.add(wrapWithTopBar("Tic‑Tac‑Toe", tttView, this::openMenu), "ttt");
    root.add(wrapWithTopBar("Pong", pongView, this::openMenu), "pong");

    add(root);
    openMenu();
  }

  /**
   * Creates a JPanel that wraps the provided center component with a top bar.
   * The top bar includes a back button and a title.
   *
   * @param title the title to be displayed in the top bar
   * @param center the main content component to be wrapped
   * @param onBack a runnable action to be executed when the back button is clicked
   * @return a JPanel containing the top bar and the wrapped center component
   */
  private JPanel wrapWithTopBar(String title, JComponent center, Runnable onBack) {
    JPanel container = new JPanel(new BorderLayout());
    JPanel top = new JPanel(new BorderLayout());
    JButton back = new JButton("◀ Back");
    back.addActionListener(e -> onBack.run());
    JLabel label = new JLabel("  " + title, SwingConstants.LEFT);
    label.setFont(label.getFont().deriveFont(Font.BOLD, 18f));
    top.add(back, BorderLayout.WEST);
    top.add(label, BorderLayout.CENTER);
    top.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
    container.add(top, BorderLayout.NORTH);
    container.add(center, BorderLayout.CENTER);
    return container;
  }

  /**
   * Navigates to the main menu view of the Game Hub application.
   *
   * This method is used to switch the current view to the main menu. It uses
   * the internal `open` method to perform the navigation by showing the
   * card layout associated with the menu key. Additionally, it ensures that
   * the focus is set on the currently displayed view to properly handle
   * key bindings or user interactions.
   */
  private void openMenu() { open("menu"); }

  /**
   * Navigates to the view associated with the specified key and resets the corresponding game
   * controller if applicable. This method switches the displayed view in a card layout while
   * ensuring the focus is set correctly on the active view.
   *
   * @param key the identifier for the view to be displayed. Valid keys include:
   *            - "GamingConsole.snake": Switches to the Snake game and resets its controller.
   *            - "c4": Switches to the Connect 4 game and resets its controller.
   *            - "GamingConsole.ttt": Switches to the Tic-Tac-Toe game and resets its controller.
   *            - "GamingConsole.pong": Switches to the Pong game and resets its controller.
   *            - "menu": Switches to the main menu.
   *            - Any other value does not perform any action.
   */
  private void open(String key) {
    switch (key) {
      case "snake":
        snakeCtl.reset();
        break;
      case "c4":
        c4Ctl.reset();
        break;
      case "ttt":
        tttCtl.reset();
        break;
      case "pong":
        pongCtl.reset();
        break;
      default:
        break;
    }
    cards.show(root, key);
    SwingUtilities.invokeLater(() -> {
      Component c = Arrays.stream(root.getComponents())
              .filter(Component::isVisible)
              .findFirst().orElse(null);
      if (c != null) c.requestFocusInWindow();
    });
  }

  /**
   * The main method serves as the starting point for the GamingConsole.ui.GameHub application.
   * It initializes and launches the GamingConsole.ui.GameHub JFrame on the Event Dispatch Thread.
   *
   * @param args command-line arguments passed to the application; these are not utilized in this
   *            implementation.
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new GameHub().setVisible(true));
  }
}


