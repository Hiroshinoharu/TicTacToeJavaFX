package application;

public class AIPlayer extends Player{

	public AIPlayer(int playerMark) {
		super(playerMark);
	}
	
	public Move findBestMove(GameBoard board) {
		Minimax minimax = new Minimax();
		return minimax.findBestMove(board);
	}

}
