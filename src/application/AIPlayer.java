package application;

// AIPlayer class extends the Player class to represent an AI player in the game
public class AIPlayer extends Player {

    // Constructor to initialize the AI player with a mark (1 for X, -1 for O)
    public AIPlayer(int playerMark) {
        super(playerMark); // Call the constructor of the superclass (Player)
    }

    // Override the abstract method to find the best move for the AI player
    @Override
    public Move findBestMove(GameBoard board) {
        // Create an instance of the Minimax class to compute the best move
        Minimax minimax = new Minimax();
        
        // Use the Minimax algorithm to find the best move for the AI
        return minimax.findBestMove(board, playerMark);
    }
}