package application;

// Move class represents a move on the Tic-Tac-Toe game board
public class Move {

    private int row; // Row index for the move
    private int col; // Column index for the move

    // Constructor to initialize the move with a specific row and column
    public Move(int r, int c) {
        row = r;
        col = c;
    }

    // Getter for the row index
    public int getRow() {
        return row;
    }

    // Setter for the row index
    public void setRow(int row) {
        this.row = row;
    }

    // Getter for the column index
    public int getCol() {
        return col;
    }

    // Setter for the column index
    public void setCol(int col) {
        this.col = col;
    }
}
