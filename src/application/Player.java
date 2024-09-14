package application;

// Abstract Player class to represent a player in the game (either human or AI)
public abstract class Player {
    
    protected int playerMark; // Player mark: 1 for X, -1 for O
    
    // Constructor to initialize the player with a mark (1 for X, -1 for O)
    public Player(int mark) {
        playerMark = mark;
    }

    // Getter for playerMark
    public int getPlayerMark() {
        return playerMark;
    }

    // Setter for playerMark
    public void setPlayerMark(int mark) {
        playerMark = mark;
    }

    // Abstract method to find the best move on the board
    // This method will be implemented by subclasses (e.g., AIPlayer)
    public abstract Move findBestMove(GameBoard board);
}
