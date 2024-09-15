# Tic Tac Toe vs AI with JavaFX

## Overview
This project is a Tic Tac Toe game featuring an AI opponent designed to be unbeatable using the Minimax algorithm. The game utilizes JavaFX to create an intuitive and interactive user interface, providing a classic gaming experience with a challenging AI.

## Project Structure
The project is divided into several modules to ensure organization and modularity, with each module responsible for specific functionality:

1. **`Main.java`**
   - The entry point of the application. Integrates all other modules to run the game and manage the user interface.

2. **`GameBoard.java`**
   - Manages the game board and handles the core logic, including player movements, determining the winner, keeping score, resetting the game, and determining player symbols. It interacts with both the human player and the AI to facilitate gameplay.

3. **`Minimax.java`**
   - Implements the Minimax algorithm with alpha-beta pruning for AI decision-making. This algorithm evaluates the best possible move for the AI based on the game's current state, optimizing moves to maximize the chance of winning or minimize the chance of losing. Alpha-beta pruning helps speed up the decision-making process by eliminating branches that do not affect the final decision.

4. **`Move.java`**
   - Defines a `Move` class that encapsulates a single move on the board, including the row and column where the move is made.

5. **`Player.java`**
   - An abstract class representing a player (either human or AI) and defines the player symbol and behavior. It provides the structure for the AIPlayer to implement its logic.

6. **`AIPlayer.java`**
   - Extends the `Player` class to represent an AI player. It utilizes the `Minimax` algorithm to make strategic decisions and ensures the AI plays optimally.

## Object-Oriented Principles
This project adheres to core object-oriented principles, including:

- **Encapsulation**: Each class has clearly defined responsibilities and hides internal details, exposing only necessary functionalities.
- **Inheritance**: The `AIPlayer` class inherits from the `Player` class, allowing code reuse and extension.
- **Polymorphism**: The `Player` class provides a polymorphic interface for both human and AI players.
- **Abstraction**: Abstract classes and methods define the structure for player behavior, which the AI or human player can implement.

## Project Goals
The primary goal of this project is to develop a functional Tic Tac Toe game with basic game mechanics and an intuitive user interface. Additionally, the project aims to implement an AI opponent that challenges players by using advanced algorithms like Minimax. This project is designed to enhance understanding of:
- API programming with JavaFX
- Algorithm implementation in real-world projects
- Object-oriented design and architecture
- Software development best practices

## Core Functionality
- A playable Tic Tac Toe game.
- An AI opponent that makes optimal moves.

## Optional Functionality
- Scoreboard to keep track of wins, losses, and draws.

## Future Improvements
- Enhancing the user interface with better visuals and animations.
- Improving the scoreboard to include more features, such as a historical record of moves or a win-loss ratio display.
- Adding sound effects and game settings (e.g., difficulty levels for the AI).
- Supporting multiplayer modes or online play.

## Conclusion
This project demonstrates the application of object-oriented principles and algorithmic thinking to develop a simple but engaging game. It serves as a foundation for further exploration in software development, artificial intelligence, and user interface design.
