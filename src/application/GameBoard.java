package application;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class GameBoard extends VBox{
	
	// Create a 2D array to represent the game board
	private int [][] board;
	private final int BOARD_SIZE = 3;
	
	// Create a 2D array of buttons to represent the game board
	private Button[][] buttons = new Button[BOARD_SIZE][BOARD_SIZE];
	
	// Create two players: human and AI
	private Player humanPlayer;
	private AIPlayer aiPlayer;
	
	// Scoreboard to keep track of the score
	private int player1Score = 0;
	private int aiScore = 0;
	private int drawScore = 0;
	
	// Create a label to display the score
	private Label scoreLabel;
	
	// Create a variable to keep track of the current player
	private int currentPlayer = 1;
	
	// Create a GridPane to hold the game board
	private GridPane boardPane;
	
	// Variables to determine the symbol of the player
	private String player1Symbol;
	private String aiPlayerSymbol;
	
	// Create a constructor to initialize the game board
	public GameBoard() {
	    board = new int[BOARD_SIZE][BOARD_SIZE];
	    boardPane = new GridPane(); // Create a new GridPane with a 5 pixel gap
	    
	    selectPlayerSymbol();
	    
	    // Initialize players
	    setHumanPlayer(new Player(1) {
	        @Override
	        public Move findBestMove(GameBoard board) {
	            // Human player does not need to find the best move
	            return null;
	        }
	    });
	    
	    aiPlayer = new AIPlayer(-1); // AI player is player 2 as O
	    
	    // Initialize the game board and scoreboard
	    intializeBoard();
	    intializeScoreBoard();
	        
	    // Add the score board and the stack pane to the VBox
	    this.getChildren().addAll(scoreLabel, boardPane);
	    this.setAlignment(javafx.geometry.Pos.CENTER); // Align to the center
	    this.setSpacing(10); // Set spacing between the score board and the game board
	    
	    determineFirstMove(); // Determine the first move
	}

	
	private void determineFirstMove() {
		// If the player chose "O", AI goes first
        if (player1Symbol.equals("O")) {
            currentPlayer = -1; // AI goes first
            aiMove(); // AI makes the first move
        } else {
            currentPlayer = 1; // Player goes first
        }
	}


	private void selectPlayerSymbol() {
		// TODO Auto-generated method stub
		List<String> choices = Arrays.asList("X", "O");
		ChoiceDialog<String> dialog = new ChoiceDialog<>("X", choices);
		dialog.setTitle("Select Player Symbol");
		dialog.setHeaderText("Select the symbol for the player:");
		dialog.setContentText("Choose your symbol:");
		
		Optional<String> result = dialog.showAndWait();
		result.ifPresent(symbol -> {
			player1Symbol = symbol;
			aiPlayerSymbol = symbol.equals("X") ? "O" : "X";
			System.out.println("Player 1 symbol: " + player1Symbol + ", AI symbol: " + aiPlayerSymbol);
		});
	}


	private void intializeBoard() {
	    // Initialize the game board with buttons and add them to the GridPane
	    for (int row = 0; row < BOARD_SIZE; row++) {
	        for (int col = 0; col < BOARD_SIZE; col++) {
	            Button btn = new Button();
	            btn.setPrefSize(100, 100);
	            
	            // Event handlers for hover effect
	            btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #DADADA; -fx-cursor: hand;"));
	            btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: linear-gradient(to bottom, #FFFFFF, #E6E6E6);"));

	            // Debugging: Print button initialization details
	            System.out.println("Initializing Button at Row: " + row + ", Column: " + col);
	            System.out.println("Button preferred size: " + btn.getPrefWidth() + "x" + btn.getPrefHeight());

	            int finalRow = row;
	            int finalCol = col;
	            btn.setOnAction(e -> handlePlayerMove(finalRow, finalCol));

	            // Store the button in the array and add it to the GridPane
	            buttons[row][col] = btn;
	            boardPane.add(btn, col, row);
	            
	            // Check if the button is stored correctly in the array
	            if (buttons[row][col] != null) {
	                System.out.println("Button [" + row + "][" + col + "] is initialized correctly.");
	            } else {
	                System.out.println("Error: Button [" + row + "][" + col + "] is not initialized!");
	            }


	            // Debugging: Print GridPane layout info after adding button
	            System.out.println("Button added to GridPane at position (" + col + ", " + row + ")");
	        }
	    }
	    
	    // Debugging: Print all children of GridPane and their positions
	    System.out.println("Verifying GridPane Layout:");
	    boardPane.getChildren().forEach(child -> {
	        Integer colIndex = GridPane.getColumnIndex(child);
	        Integer rowIndex = GridPane.getRowIndex(child);
	        System.out.println("Button at GridPane Position -> Column: " + colIndex + ", Row: " + rowIndex);
	    });

	    // Apply CSS class to the game
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
	            buttons[row][col].setText(player1Symbol); // Update the button text
	            buttons[row][col].setDisable(true);
	            printBoardState(this);

	            int winPositions[][] = new int[3][2];
	            int gameResult = checkWin(winPositions);  // Call checkWin only once
	            System.out.println("Checking game status...");

	            if(gameResult == 1 || gameResult == - 1) {
					highlightWinningLine(winPositions);
					endGame(gameResult);
	            } else if (gameResult == 0) {
                    endGame(gameResult);
	            }
	            else {
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
				buttons[bestMove.getRow()][bestMove.getCol()].setText(aiPlayerSymbol); // Update the button text
				buttons[bestMove.getRow()][bestMove.getCol()].setDisable(true); // Disable the button
				
				int winPositions[][] = new int[3][2];
				int gameResult = checkWin(winPositions); // Call checkWin only once
				// Check the game status
				System.out.println("Checking game status...");

				
				if (gameResult == 1 || gameResult == -1) {
					highlightWinningLine(winPositions);
					endGame(gameResult);
				} else if (gameResult == 0) {
					endGame(gameResult);
				}
				else {
					System.out.println("Switching to player...");
					currentPlayer = 1; // Switch to player
				}
			}
		}
	}
	
	private void endGame(int result) {
		// TODO Auto-generated method stub
		System.out.println("Game over!");
								
		// Sends a pop-up message to the user
		Alert alert = new Alert(AlertType.INFORMATION);
		
		alert.setTitle("Game Over");
		
		alert.setHeaderText(null);
		
		if (result == 1) {
			alert.setContentText("Player wins!");
			player1Score++;
		} else if (result == -1) {
			alert.setContentText("AI wins!");
			aiScore++;
		} else {
			alert.setContentText("It's a draw!");
			drawScore++;
		}
		
		updateScoreBoard();
		
		alert.showAndWait();
		
		resetBoard();
	}
	
	private void updateScoreBoard() {
		// TODO Auto-generated method stub
		scoreLabel.setText("Player: " + player1Score + " AI: " + aiScore + " Draw: " + drawScore);
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
	
	public int checkWin(int[][] winPositions) {
	    // Check rows
	    for (int i = 0; i < BOARD_SIZE; i++) {
	        if (board[i][0] != 0 && board[i][0] == board[i][1] && board[i][0] == board[i][2]) {
	            winPositions[0][0] = i; winPositions[0][1] = 0;
	            winPositions[1][0] = i; winPositions[1][1] = 1;
	            winPositions[2][0] = i; winPositions[2][1] = 2;
	            return board[i][0]; // Return the winner immediately
	        }
	    }

	    // Check columns
	    for (int i = 0; i < BOARD_SIZE; i++) {
	        if (board[0][i] != 0 && board[0][i] == board[1][i] && board[0][i] == board[2][i]) {
	            winPositions[0][0] = 0; winPositions[0][1] = i;
	            winPositions[1][0] = 1; winPositions[1][1] = i;
	            winPositions[2][0] = 2; winPositions[2][1] = i;
	            return board[0][i]; // Return the winner immediately
	        }
	    }

	    // Check diagonals
	    if (board[0][0] != 0 && board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
	        winPositions[0][0] = 0; winPositions[0][1] = 0;
	        winPositions[1][0] = 1; winPositions[1][1] = 1;
	        winPositions[2][0] = 2; winPositions[2][1] = 2;
	        return board[0][0]; // Return the winner immediately
	    }

	    if (board[0][2] != 0 && board[0][2] == board[1][1] && board[0][2] == board[2][0]) {
	        winPositions[0][0] = 0; winPositions[0][1] = 2;
	        winPositions[1][0] = 1; winPositions[1][1] = 1;
	        winPositions[2][0] = 2; winPositions[2][1] = 0;
	        return board[0][2]; // Return the winner immediately
	    }

	    // Check for draw only if no winner was found
	    boolean boardFull = true;
	    for (int i = 0; i < BOARD_SIZE; i++) {
	        for (int j = 0; j < BOARD_SIZE; j++) {
	            if (board[i][j] == 0) {
	                boardFull = false; // If there's an empty space, it's not a draw
	                break;
	            }
	        }
	        if (!boardFull) break;
	    }

	    return boardFull ? 0 : -2; // Return 0 for draw, -2 if the game is still in progress
	}

	
	private void highlightWinningLine(int[][] winPositions) {
	    for (int[] pos : winPositions) {
	        int row = pos[0];
	        int col = pos[1];
	        if (row >= 0 && col >= 0) {
	        	// Apply the desired styles directly using setStyle
	            buttons[row][col].setStyle(
	                "-fx-background-color: #FFD700; " + // Gold color for winning line
	                "-fx-text-fill: #000000; " +        // Black text color
	                "-fx-font-weight: bold; " +         // Bold text
	                "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 5, 0, 1, 1);" // Shadow effect
	            );
	        }
	    }
	}
	
	public void resetBoard() {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				board[i][j] = 0;
				buttons[i][j].setText(""); // Clear the text on the buttons
				buttons[i][j].setDisable(false); // Re-enable the buttons
				buttons[i][j].setStyle(null); // Remove any styling
			}
		}
		System.out.println("Board reset.");
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

	public int getDrawScore() {
		return drawScore;
	}

	public void setDrawScore(int drawScore) {
		this.drawScore = drawScore;
	}

}
