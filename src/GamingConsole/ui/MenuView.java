package GamingConsole.ui;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;

/**
 * The GamingConsole.ui.MenuView class extends JPanel and serves as the user interface for the game menu.
 * It provides a graphical menu allowing users to select from multiple games, including Snake,
 * Connect 4, Tic-Tac-Toe, and Pong. Each game can be accessed using buttons or keyboard shortcuts.
 * The UI incorporates custom rendering for a visually appealing layout.
 */
class MenuView extends JPanel {
  private final Runnable onSnake, onC4, onTTT, onPong;

  /**
   * Constructs the GamingConsole.ui.MenuView panel that serves as the main menu for the game hub,
   * displaying available game options and handling keyboard shortcuts to trigger game actions.
   *
   * @param onSnake the action to be executed when the "Snake" game is selected
   * @param onC4    the action to be executed when the "Connect 4" game is selected
   * @param onTTT   the action to be executed when the "Tic-Tac-Toe" game is selected
   * @param onPong  the action to be executed when the "Pong" game is selected
   */
  public MenuView(Runnable onSnake, Runnable onC4, Runnable onTTT, Runnable onPong) {
    this.onSnake = onSnake;
    this.onC4 = onC4;
    this.onTTT = onTTT;
    this.onPong = onPong;
    setLayout(new BorderLayout());
    setOpaque(false);

    JPanel titleWrap = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        String title = "Game Hub";
        Font f = getFont().deriveFont(Font.BOLD, 44f);
        g2.setFont(f);
        g2.setColor(new Color(0, 0, 0, 60));
        g2.drawString(title, 41, getHeight() - 16);
        g2.setColor(new Color(245, 245, 250));
        g2.drawString(title, 40, getHeight() - 17);
      }
    };
    titleWrap.setOpaque(false);
    titleWrap.setPreferredSize(new Dimension(100, 90));
    titleWrap.setBorder(BorderFactory.createEmptyBorder(20, 40, 0, 40));
    add(titleWrap, BorderLayout.NORTH);

    JPanel grid = new JPanel(new GridBagLayout()) {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gp = new GradientPaint(0, 0, new Color(18, 22, 30), 0,
                getHeight(), new Color(28, 34, 46));
        g2.setPaint(gp);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setPaint(new RadialGradientPaint(
                new Point(getWidth() / 2, getHeight() / 2),
                Math.max(getWidth(), getHeight()),
                new float[]{0f, 1f},
                new Color[]{new Color(255, 255, 255, 20), new Color(0, 0, 0, 40)}));
        g2.fillRect(0, 0, getWidth(), getHeight());
      }
    };
    grid.setOpaque(false);
    grid.setBorder(BorderFactory.createEmptyBorder(10, 40, 40, 40));
    GridBagConstraints c = new GridBagConstraints();
    c.insets = new Insets(16, 16, 16, 16);

    GameCard snake = new GameCard("Snake", "Arcade classic", GameCard.IconType.SNAKE, onSnake);
    GameCard connect4 = new GameCard("Connect 4", "Drop & align", GameCard.IconType.CONNECT4, onC4);
    GameCard ttt = new GameCard("Tic-Tac-Toe", "Best of 3", GameCard.IconType.TTT, onTTT);
    GameCard pong = new GameCard("Pong", "Beat the AI", GameCard.IconType.PONG, onPong);

    c.gridx = 0;
    c.gridy = 0;
    grid.add(snake, c);
    c.gridx = 1;
    c.gridy = 0;
    grid.add(connect4, c);
    c.gridx = 0;
    c.gridy = 1;
    grid.add(ttt, c);
    c.gridx = 1;
    c.gridy = 1;
    grid.add(pong, c);

    add(grid, BorderLayout.CENTER);

    JTextArea help = new JTextArea(
            "Quick keys: 1=Snake  2=Connect4  3=TTT  4=Pong    •    ESC exits a game to this menu\n"
                    + "Reset inside games with R. Use Arrow keys/WASD where applicable.");
    help.setEditable(false);
    help.setOpaque(false);
    help.setForeground(new Color(220, 225, 230));
    help.setFont(new Font("Monospaced", Font.PLAIN, 13));
    help.setBorder(BorderFactory.createEmptyBorder(0, 40, 16, 40));
    add(help, BorderLayout.SOUTH);

    InputMap im = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    ActionMap am = getActionMap();
    im.put(KeyStroke.getKeyStroke('1'), "s");
    am.put("s", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        onSnake.run();
      }
    });
    im.put(KeyStroke.getKeyStroke('2'), "c4");
    am.put("c4", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        onC4.run();
      }
    });
    im.put(KeyStroke.getKeyStroke('3'), "t");
    am.put("t", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        onTTT.run();
      }
    });
    im.put(KeyStroke.getKeyStroke('4'), "p");
    am.put("p", new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        onPong.run();
      }
    });
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    GradientPaint gp = new GradientPaint(0, 0, new Color(12, 16, 24), 0,
            getHeight(), new Color(18, 22, 32));
    g2.setPaint(gp);
    g2.fillRect(0, 0, getWidth(), getHeight());
  }
}
