package application;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

public class GameBoard extends VBox{
	
	// Create a 2D array to represent the game board
	private int [][] board;
	private final int BOARD_SIZE = 3;
	
	// Create a 2D array of buttons to represent the game board
	private Button[][] buttons = new Button[BOARD_SIZE][BOARD_SIZE];
	
	// Create two players: human and AI
	private Player humanPlayer;
	private AIPlayer aiPlayer;
	
	// Create another human player in case 
	private Player player2;
	
	// Scoreboard to keep track of the score
	private int player1Score = 0;
	private int aiScore = 0;
	private int player2Score = 0;
	private int drawScore = 0;
	
	private Label scoreLabel;
	
	// Create a variable to keep track of the current player
	private int currentPlayer = 1;
	
	// Create a line to represent the winning line
	private Line winningLine;
	
	// Create a GridPane to hold the game board
	private GridPane boardPane;
	
	// Create a constructor to initialize the game board
	public GameBoard() {
	    board = new int[BOARD_SIZE][BOARD_SIZE];
	    boardPane = new GridPane();
	    
	    // Initialize players
	    setHumanPlayer(new Player(1) {
	        @Override
	        public Move findBestMove(GameBoard board) {
	            // Human player does not need to find the best move
	            return null;
	        }
	    });
	    aiPlayer = new AIPlayer(-1); // AI player is player 2 as O

	    // Initialize the winning line (should be done before it's added to the StackPane)
	    winningLine = new Line();
	    winningLine.setVisible(false);
	    winningLine.getStyleClass().add("winning-line");
	    
	    // Initialize the game board and scoreboard
	    intializeBoard();
	    intializeScoreBoard();
	    
	    // Create a StackPane to hold the game board and the winning line
	    StackPane stackPane = new StackPane(boardPane, winningLine);
	    
	    winningLine.toFront(); // Send the winning line to the front

	    // Add the score board and the stack pane to the VBox
	    this.getChildren().addAll(scoreLabel, stackPane);
	    this.setAlignment(javafx.geometry.Pos.CENTER); // Align to the center
	    this.setSpacing(10); // Set spacing between the score board and the game board
	}

	
	private void intializeBoard() {
		// TODO Initialize the game board with buttons and add them to the GridPane
		for(int row = 0; row < BOARD_SIZE; row++) {
			for(int col = 0; col < BOARD_SIZE; col++) {
				Button btn = new Button();
				btn.setPrefSize(150, 150);
				int finalRow = row;
				int finalCol = col;
				btn.setOnAction(e -> handlePlayerMove(finalRow, finalCol));
				buttons[row][col] = btn;
				boardPane.add(btn, col, row);
			}
		}
		boardPane.getStyleClass().add("game-board");
	}
	
	private void intializeScoreBoard() {
		// TODO Initialize the score board
		scoreLabel = new Label("Player: " + player1Score + " AI: " + aiScore + " Draw: " + drawScore);
		scoreLabel.getStyleClass().add("score-label");
	}

	private void handlePlayerMove(int row, int col) {
	    if (currentPlayer == 1) {  // Ensure it's the player's turn
	        System.out.println("Player is making a move...");
	        if (makeMove(row, col, currentPlayer)) {
	            System.out.println("Move successful!");
	            buttons[row][col].setText("X");
	            buttons[row][col].setDisable(true);
	            printBoardState(this);

	            int gameResult = checkWin();  // Call checkWin only once
	            System.out.println("Checking game status...");

	            if (gameResult == 1) {
	                System.out.println("Player wins!");
	                endGame();
	            } else if (gameResult == 0) {
	                System.out.println("It's a draw");
	                endGame();
	            } else {
	                System.out.println("Switching to AI player...");
	                currentPlayer = -1;  // Switch to AI player
	                aiMove();
	            }
	        }
	    }
	}

	
	
	private void aiMove() {
		if(currentPlayer == -1) {
			Move bestMove = aiPlayer.findBestMove(this);  // AI finds the best move
			System.out.println("AI is making a move...");
			if (makeMove(bestMove.getRow(), bestMove.getCol(), currentPlayer)) {
				System.out.println("Move successful!");
				buttons[bestMove.getRow()][bestMove.getCol()].setText("O"); // Update the button text
				buttons[bestMove.getRow()][bestMove.getCol()].setDisable(true); // Disable the button
				// Check the game status
				System.out.println("Checking game status...");
				if (checkWin() == -1) {
					System.out.println("AI wins!");
					endGame();
				} else if (checkWin() == 0) {
					System.out.println("It's a draw!");
					endGame();
				} else {
					System.out.println("Switching to player...");
					currentPlayer = 1; // Switch to player
				}
			}
		}
	}
	
	private void endGame() {
		// TODO Auto-generated method stub
		System.out.println("Game over!");
		
		// Check for a winning line
		int[] winCoordinates = checkWinWithCoordinates();
		
		if(winCoordinates != null) {
			drawWinningLine(winCoordinates[0], winCoordinates[1], winCoordinates[2], winCoordinates[3]);
		}
		
		int result = checkWin();
		
		if (result == 1) {
			player1Score++;
		} else if (result == -1) {
			aiScore++;
		} else {
			drawScore++;
		}
		
		updateScoreBoard();
		
		// Sends a pop-up message to the user
		Alert alert = new Alert(AlertType.INFORMATION);
		
		alert.setTitle("Game Over");
		
		alert.setHeaderText(null);
		
		if (result == 1) {
			alert.setContentText("Player wins!");
		} else if (result == -1) {
			alert.setContentText("AI wins!");
		} else {
			alert.setContentText("It's a draw!");
		}
		
		alert.showAndWait();
		
		resetBoard();
	}
	
	private void drawWinningLine(int startRow, int startCol, int endRow, int endCol) {
	    // Ensure the board pane's CSS is applied and its layout is updated
	    boardPane.applyCss();
	    boardPane.layout();

	    // Get the start and end buttons
	    Button startButton = buttons[startRow][startCol];
	    Button endButton = buttons[endRow][endCol];

	    // Convert the local coordinates of the buttons to the scene's coordinates
	    double startXScene = startButton.localToScene(startButton.getBoundsInLocal()).getMinX() + startButton.getWidth() / 2;
	    double startYScene = startButton.localToScene(startButton.getBoundsInLocal()).getMinY() + startButton.getHeight() / 2;
	    double endXScene = endButton.localToScene(endButton.getBoundsInLocal()).getMinX() + endButton.getWidth() / 2;
	    double endYScene = endButton.localToScene(endButton.getBoundsInLocal()).getMinY() + endButton.getHeight() / 2;

	    // Convert the scene coordinates directly to the StackPane's local coordinates
	    double localStartX = boardPane.getParent().sceneToLocal(startXScene, startYScene).getX();
	    double localStartY = boardPane.getParent().sceneToLocal(startXScene, startYScene).getY();
	    double localEndX = boardPane.getParent().sceneToLocal(endXScene, endYScene).getX();
	    double localEndY = boardPane.getParent().sceneToLocal(endXScene, endYScene).getY();

	    // Debugging logs to confirm coordinates
	    System.out.println("Start button center (X, Y): (" + localStartX + ", " + localStartY + ")");
	    System.out.println("End button center (X, Y): (" + localEndX + ", " + localEndY + ")");

	    // Set the coordinates for the winning line
	    winningLine.setStartX(localStartX);
	    winningLine.setStartY(localStartY);
	    winningLine.setEndX(localEndX);
	    winningLine.setEndY(localEndY);

	    // Make the line visible
	    winningLine.setVisible(true);
	    winningLine.toFront();  // Bring the line to the front
	}




	private void updateScoreBoard() {
		// TODO Auto-generated method stub
		scoreLabel.setText("Player: " + player1Score + " AI: " + aiScore + " Draw: " + drawScore);
	}

	public void resetScoreBoard() {
		player1Score = 0;
		aiScore = 0;
		player2Score = 0;
		drawScore = 0;
		updateScoreBoard();
	}
	
	public boolean makeMove(int row, int col, int playerMark) {
	    System.out.println("Attempting to make move at: (" + row + ", " + col + ") for player: " + playerMark);
		if (row >= 0 && col >= 0 && row < 3 && col < 3) { // Check bounds
			// Make the move if the cell is empty
			if (playerMark == 0 || board[row][col] == 0) {
				board[row][col] = playerMark; // Make the move
				System.out.println("Move successful!");
				return true; // If the move is valid
			}
		}
		System.out.println("Move failed. Cell is already taken or out of bounds.");
		return false; // If the cell is not empty
	}
	
	public int checkWin() {
		// Implement logic to check rows, columns, and diagonals for a win
		// Return 1 if player 1 wins, -1 if player 2 wins, -2 if no one wins
		
		// Check rows
		for (int i = 0; i < BOARD_SIZE; i++) {
			if (board[i][0] != 0 && board[i][0] == board[i][1] && board[i][0] == board[i][2]) {
				return board[i][0]; // return the start and end of the row
			}
		}
		
		// Check columns
		for (int i = 0; i < BOARD_SIZE; i++) {
			if (board[0][i] != 0 && board[0][i] == board[1][i] && board[0][i] == board[2][i]) {
				return board[0][i]; // return the start and end of the column
			}
		}
		
		// Check diagonals
		if (board[0][0] != 0 && board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
			return board[0][0]; // top left to bottom right diagonal
		}
		
		if (board[0][2] != 0 && board[0][2] == board[1][1] && board[0][2] == board[2][0]) {
			return board[0][2]; // top right to bottom left diagonal
		}
		
		// Check for a draw
		boolean boardFull = true;
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				if (board[i][j] == 0) {
					boardFull = false;
					break;
				}
			}
			if (!boardFull) {
				break;
			}
		}
		if (boardFull) {
			return 0;
		} 
		return -2;// Return -2 if the game is still in progress
	}
	
	public int[] checkWinWithCoordinates() {
	    // Check rows
	    for (int i = 0; i < BOARD_SIZE; i++) {
	        if (board[i][0] != 0 && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
	            System.out.println("Row " + i + " win detected.");
	            return new int[] {i, 0, i, 2}; 
	        }
	    }

	    // Check columns
	    for (int i = 0; i < BOARD_SIZE; i++) {
	        if (board[0][i] != 0 && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
	            System.out.println("Column " + i + " win detected.");
	            return new int[] {0, i, 2, i}; 
	        }
	    }

	    // Check diagonals
	    if (board[0][0] != 0 && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
	        System.out.println("Diagonal top-left to bottom-right win detected.");
	        return new int[] {0, 0, 2, 2}; 
	    }

	    if (board[0][2] != 0 && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
	        System.out.println("Diagonal top-right to bottom-left win detected.");
	        return new int[] {0, 2, 2, 0}; 
	    }

	    System.out.println("No win detected.");
	    return null; 
	}

	
	public void resetBoard() {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				board[i][j] = 0;
				buttons[i][j].setText(""); // Clear the text on the buttons
				buttons[i][j].setDisable(false); // Re-enable the buttons
			}
		}
		System.out.println("Board reset.");
		winningLine.setVisible(false); // Hide the winning line
		currentPlayer = 1; // Reset the current player to player 1
	}
	
	private void printBoardState(GameBoard board) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(board.getBoard()[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	

	public int[][] getBoard() {
		return board;
	}

	public Player getHumanPlayer() {
		return humanPlayer;
	}

	public void setHumanPlayer(Player humanPlayer) {
		this.humanPlayer = humanPlayer;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public int getPlayer1Score() {
		return player1Score;
	}

	public void setPlayer1Score(int player1Score) {
		this.player1Score = player1Score;
	}

	public int getAiScore() {
		return aiScore;
	}

	public void setAiScore(int aiScore) {
		this.aiScore = aiScore;
	}

	public int getPlayer2Score() {
		return player2Score;
	}

	public void setPlayer2Score(int player2Score) {
		this.player2Score = player2Score;
	}

	public int getDrawScore() {
		return drawScore;
	}

	public void setDrawScore(int drawScore) {
		this.drawScore = drawScore;
	}

}
