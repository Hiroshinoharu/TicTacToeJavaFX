package application;

public class AIPlayer extends Player{

	// Constructor
	public AIPlayer(int playerMark) {
		super(playerMark);
	}
	
	// Find the best move for the AI player
	@Override
	public Move findBestMove(GameBoard board) {
		Minimax minimax = new Minimax();
		return minimax.findBestMove(board,playerMark);
	}
}
