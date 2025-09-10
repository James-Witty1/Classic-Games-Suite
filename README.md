# Classic Game Suite
This is a Classic Game Suite made using Java Swing that contains four high quality, playable games:

- Snake  
- Connect 4  
- Tic-Tac-Toe  
- Pong  

All games are contained in **GameHub.java**, organized with a Model-View-Controller (MVC) structure.  
The hub uses a `CardLayout` to switch between the main menu and each game.

---

## Features
- Four games in one self-contained `.java` file.
- Menu interface with custom-drawn game cards.
- Simple key bindings and mouse controls for intuitive gameplay.
- Reset (`R`) and back-to-menu (`ESC`) support across all games.
- Clean separation of logic, rendering, and controls using MVC.


## Controls

- Snake: Arrow keys or WASD to move
- Connect 4: Mouse click to drop piece
- Tic-Tac-Toe: Mouse click to place X or O
- Pong: Up/Down arrows to move paddle

---

## How to Run
1. Compile:
   ```bash
   javac GameHub.java

2. Run:
   ```bash
   java GameHub

**Requirements**

Java 8 or higher
Any IDE or terminal with Swing support
