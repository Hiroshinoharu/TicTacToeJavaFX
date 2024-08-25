package application;

public class Move {
	
	private int row;
	private int col;
	
	public Move(int r, int c) {
		row = r;
		col = c;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	
}
