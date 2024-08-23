package application;

public abstract class Player {
	
	protected int playerMark; // 1 for X and -1 for O
	
	public Player(int mark) {
		playerMark = mark;
	}
	
	public int getPlayerMark() {
		return playerMark;
	}
	
	public void setPlayerMark(int mark) {
		playerMark = mark;
	}
	
	// Abstract method to make a move
	public abstract Move findBestMove(GameBoard board);
	
}
