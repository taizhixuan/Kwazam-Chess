# Kwazam Chess

A custom chess variant featuring unique pieces and innovative gameplay mechanics. Kwazam Chess introduces transforming pieces and special movement rules on a compact 8x5 board.

## Table of Contents
- [Overview](#overview)
- [Game Rules](#game-rules)
- [Pieces and Movement](#pieces-and-movement)
- [Special Mechanics](#special-mechanics)
- [How to Run](#how-to-run)
- [Project Structure](#project-structure)
- [Design Patterns](#design-patterns)
- [Features](#features)
- [Authors](#authors)

## Overview

Kwazam Chess is a strategic board game played on an 8-row by 5-column board. Two players (Red and Blue) compete to capture their opponent's Sau piece while managing transforming pieces and unique movement patterns.

## Game Rules

### Board Setup
- **Board Size**: 8 rows × 5 columns
- **Players**: 2 (Red and Blue)
- **Starting Player**: Red moves first

### Objective
Capture your opponent's **Sau** piece to win the game.

### Initial Piece Placement

**Red (Starting at rows 0-1):**
- Row 0: Tor, Biz, Sau, Biz, Xor
- Row 1: Ram, Ram, Ram, Ram, Ram

**Blue (Starting at rows 6-7):**
- Row 6: Ram, Ram, Ram, Ram, Ram
- Row 7: Xor, Biz, Sau, Biz, Tor

## Pieces and Movement

### Sau (King)
- **Movement**: One step in any direction (horizontal, vertical, or diagonal)
- **Special**: Capturing an opponent's Sau ends the game
- **Transforms**: No

### Tor (Rook)
- **Movement**: Any number of squares horizontally or vertically
- **Cannot**: Jump over other pieces
- **Transforms**: Changes to Xor after 2 moves
- **Similar to**: Chess Rook

### Xor (Bishop)
- **Movement**: Any number of squares diagonally
- **Cannot**: Jump over other pieces
- **Transforms**: Changes to Tor after 2 moves
- **Similar to**: Chess Bishop

### Biz (Knight)
- **Movement**: L-shaped (2 squares in one direction, then 1 square perpendicular)
- **Can**: Jump over other pieces
- **Transforms**: No
- **Similar to**: Chess Knight

### Ram (Pawn)
- **Movement**: One step forward
- **Special**: Reverses direction when reaching the opposite edge of the board
  - Red Ram starts moving downward (row+1), reverses to upward at row 7
  - Blue Ram starts moving upward (row-1), reverses to downward at row 0
- **Can**: Capture opponent pieces directly ahead
- **Transforms**: No

## Special Mechanics

### Piece Transformation
- **Tor ↔ Xor**: All Tor and Xor pieces transform into each other every 2 full turns (after both players have completed their moves)
- **Individual Transformation**: Tor transforms to Xor after making 2 moves (and vice versa)

### Ram Direction Reversal
- When a Ram reaches the opposite edge of the board, it automatically reverses its moving direction
- This allows Rams to move back and forth across the board

### Turn System
- Players alternate turns
- Each player's move counts as one turn
- Two turns (Red + Blue) constitute one full turn cycle
- Transformation occurs after every 2 full turn cycles

## How to Run

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Java IDE (IntelliJ IDEA, Eclipse, or NetBeans) or command-line Java compiler

### Running the Game

#### Using an IDE
1. Clone or download this repository
2. Open the project in your preferred Java IDE
3. Locate the main class (typically in `src/controller/` or `src/view/`)
4. Run the main class to start the game

#### Using Command Line
```bash
# Navigate to the project directory
cd Kwazam-Chess

# Compile the Java files
javac -d out src/**/*.java

# Run the game
java -cp out <MainClassName>
```

### Game Modes
- **GUI Mode**: Interactive graphical interface with clickable board and piece images
- **Console Mode**: Text-based interface for testing moves via command-line input

## Project Structure

```
Kwazam-Chess/
├── src/
│   ├── model/              # Game logic and data models
│   │   ├── Board.java      # Game board management
│   │   ├── Game.java       # Game state and rules
│   │   ├── Piece.java      # Abstract piece class
│   │   ├── Sau.java        # Sau piece implementation
│   │   ├── Tor.java        # Tor piece implementation
│   │   ├── Xor.java        # Xor piece implementation
│   │   ├── Biz.java        # Biz piece implementation
│   │   ├── Ram.java        # Ram piece implementation
│   │   ├── MovementStrategy.java      # Strategy interface
│   │   ├── SauMovement.java           # Sau movement logic
│   │   ├── TorMovement.java           # Tor movement logic
│   │   ├── XorMovement.java           # Xor movement logic
│   │   ├── BizMovement.java           # Biz movement logic
│   │   ├── RamMovement.java           # Ram movement logic
│   │   ├── PieceFactory.java          # Factory for creating pieces
│   │   ├── IDGenerator.java           # Singleton ID generator
│   │   ├── GameSaver.java             # Save game functionality
│   │   ├── GameLoader.java            # Load game functionality
│   │   ├── GameObserver.java          # Observer interface
│   │   ├── GameEvent.java             # Game event types
│   │   ├── Position.java              # Board position
│   │   ├── Color.java                 # Player colors
│   │   └── Direction.java             # Movement directions
│   ├── view/               # User interface
│   │   ├── GameView.java   # Console-based view
│   │   ├── GameScreen.java # GUI game screen
│   │   ├── HomeScreen.java # GUI home screen
│   │   ├── BoardView.java  # GUI board display
│   │   └── ViewInterface.java
│   ├── controller/         # Game controllers
│   └── resources/
│       └── images/         # Piece images (PNG files)
│           ├── Sau_red.png
│           ├── Sau_blue.png
│           ├── Tor_red.png
│           ├── Tor_blue.png
│           ├── Xor_red.png
│           ├── Xor_blue.png
│           ├── Biz_red.png
│           ├── Biz_blue.png
│           ├── Ram_red.png
│           └── Ram_blue.png
├── .gitignore
└── README.md
```

## Design Patterns

This project demonstrates several software design patterns:

### 1. Observer Pattern
- **Location**: `Game.java`, `GameObserver.java`
- **Purpose**: Notifies UI components of game state changes (moves, transformations, game over)
- **Implementation**: Game maintains list of observers and notifies them of events

### 2. Singleton Pattern
- **Location**: `IDGenerator.java`
- **Purpose**: Ensures unique ID generation for pieces
- **Implementation**: Single instance provides consistent piece IDs across the game

### 3. Factory Pattern
- **Location**: `PieceFactory.java`
- **Purpose**: Creates piece instances without exposing creation logic
- **Implementation**: Centralized piece creation based on type and color

### 4. Strategy Pattern
- **Location**: `MovementStrategy.java` and concrete movement classes
- **Purpose**: Encapsulates different movement algorithms for each piece type
- **Implementation**: Each piece delegates movement validation to its strategy

### 5. Model-View-Controller (MVC)
- **Model**: Game logic, board state, and piece behavior
- **View**: GUI and console interfaces
- **Controller**: Manages user input and coordinates model-view interactions

## Features

- ✅ **Custom Game Board**: Compact 8×5 grid optimized for strategic gameplay
- ✅ **Unique Pieces**: Five distinct piece types with special abilities
- ✅ **Dynamic Transformations**: Tor and Xor pieces transform periodically
- ✅ **Direction Reversal**: Ram pieces reverse direction at board edges
- ✅ **Save/Load Games**: Persist and restore game state
- ✅ **Multiple Interfaces**: Both GUI and console-based gameplay
- ✅ **Observer Pattern**: Real-time UI updates on game events
- ✅ **Move Validation**: Comprehensive rule enforcement
- ✅ **Graphical Assets**: Custom piece images for both colors

## Game Screenshots

The game includes visual representations for all pieces in both Red and Blue colors, providing an intuitive graphical interface for gameplay.

## Development

### Technologies Used
- **Language**: Java
- **Architecture**: Model-View-Controller (MVC)
- **UI**: Java Swing (GUI), Console I/O (text mode)
- **Version Control**: Git

### Code Quality
- Well-documented with Javadoc comments
- Clean separation of concerns
- Extensive use of design patterns
- Modular and maintainable codebase

## Authors

- **Tai Zhi Xuan**
- **Tiffany Jong Shu Ting**
- **Joyce Ong Pay Teng**

---

**Enjoy playing Kwazam Chess!** May the best strategist win! 🎮♟️
