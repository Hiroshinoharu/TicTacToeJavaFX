package application;

public class Player {
	
	private int playerMark; // 1 for X and -1 for O
	
	public Player(int mark) {
		playerMark = mark;
	}
	
	public int getPlayerMark() {
		return playerMark;
	}
	
	public void setPlayerMark(int mark) {
		playerMark = mark;
	}
	
	public void makeMove(int row, int col, GameBoard board) {
		board.makeMove(row, col, playerMark);
	}
}
