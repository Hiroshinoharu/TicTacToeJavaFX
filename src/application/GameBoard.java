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

// GameBoard class extends VBox to create the game board UI
public class GameBoard extends VBox {

    // Game board 2D array representation
    private int[][] board;
    private final int BOARD_SIZE = 3;

    // Buttons for the game board grid
    private Button[][] buttons = new Button[BOARD_SIZE][BOARD_SIZE];

    // Players for the game
    private Player humanPlayer;
    private AIPlayer aiPlayer;

    // Variables for scoring
    private int player1Score = 0;
    private int aiScore = 0;
    private int drawScore = 0;

    // Label to display score
    private Label scoreLabel;

    // Variable to keep track of current player
    private int currentPlayer = 1;

    // GridPane to hold the game board
    private GridPane boardPane;

    // Symbols for the player and AI
    private String player1Symbol;
    private String aiPlayerSymbol;

    // Constructor to initialize the game board
    public GameBoard() {
        board = new int[BOARD_SIZE][BOARD_SIZE];
        boardPane = new GridPane(); // Create a new GridPane for the game board

        selectPlayerSymbol(); // Prompt the player to choose their symbol

        // Initialize human player
        setHumanPlayer(new Player(1) {
            @Override
            public Move findBestMove(GameBoard board) {
                // Human player does not need to find the best move
                return null;
            }
        });

        aiPlayer = new AIPlayer(-1); // Initialize AI player

        intializeBoard(); // Initialize the game board
        intializeScoreBoard(); // Initialize the scoreboard

        // Add score label and game board to the VBox
        this.getChildren().addAll(scoreLabel, boardPane);
        this.setAlignment(javafx.geometry.Pos.CENTER); // Center align the VBox
        this.setSpacing(10); // Add spacing between elements

        determineFirstMove(); // Determine who goes first
    }

    // Determines who makes the first move
    private void determineFirstMove() {
        // If the player chose "O", AI goes first
        if (player1Symbol.equals("O")) {
            currentPlayer = -1; // AI goes first
            aiMove(); // AI makes the first move
        } else {
            currentPlayer = 1; // Player goes first
        }
    }

    // Prompt player to choose their symbol (X or O)
    private void selectPlayerSymbol() {
        List<String> choices = Arrays.asList("X", "O");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("X", choices);
        dialog.setTitle("Select Player Symbol");
        dialog.setHeaderText("Select the symbol for the player:");
        dialog.setContentText("Choose your symbol:");

        // Show the dialog and wait for user input
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(symbol -> {
            player1Symbol = symbol;
            aiPlayerSymbol = symbol.equals("X") ? "O" : "X";
            System.out.println("Player 1 symbol: " + player1Symbol + ", AI symbol: " + aiPlayerSymbol);
        });
    }

    // Initialize the game board with buttons
    private void intializeBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Button btn = new Button();
                btn.setPrefSize(100, 100);

                // Event handlers for button hover effect
                btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #DADADA; -fx-cursor: hand;"));
                btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: linear-gradient(to bottom, #FFFFFF, #E6E6E6);"));

                // Debugging information
                System.out.println("Initializing Button at Row: " + row + ", Column: " + col);

                int finalRow = row;
                int finalCol = col;
                btn.setOnAction(e -> handlePlayerMove(finalRow, finalCol)); // Set action handler for button

                buttons[row][col] = btn; // Store button in array
                boardPane.add(btn, col, row); // Add button to GridPane
            }
        }

        // Apply CSS class to the game board
        boardPane.getStyleClass().add("game-board");
    }

    // Initialize the scoreboard label
    private void intializeScoreBoard() {
        scoreLabel = new Label("Player: " + player1Score + " AI: " + aiScore + " Draw: " + drawScore);
        scoreLabel.getStyleClass().add("score-label");
    }

    // Handle the player's move on the board
    private void handlePlayerMove(int row, int col) {
        if (currentPlayer == 1) {  // Check if it's the player's turn
            if (makeMove(row, col, currentPlayer)) { // Attempt to make the move
                buttons[row][col].setText(player1Symbol); // Set button text
                buttons[row][col].setDisable(true); // Disable the button after move
                printBoardState(this); // Print current board state

                int winPositions[][] = new int[3][2];
                int gameResult = checkWin(winPositions); // Check game result

                if (gameResult == 1 || gameResult == -1) {
                    highlightWinningLine(winPositions); // Highlight the winning line
                    endGame(gameResult); // End the game
                } else if (gameResult == 0) {
                    endGame(gameResult); // Game is a draw
                } else {
                    currentPlayer = -1; // Switch to AI player
                    aiMove(); // AI makes its move
                }
            }
        }
    }

    // AI makes its move
    private void aiMove() {
        if (currentPlayer == -1) {
            Move bestMove = aiPlayer.findBestMove(this); // AI finds the best move
            if (makeMove(bestMove.getRow(), bestMove.getCol(), currentPlayer)) { // Attempt to make the move
                buttons[bestMove.getRow()][bestMove.getCol()].setText(aiPlayerSymbol); // Set button text
                buttons[bestMove.getRow()][bestMove.getCol()].setDisable(true); // Disable the button after move

                int winPositions[][] = new int[3][2];
                int gameResult = checkWin(winPositions); // Check game result

                if (gameResult == 1 || gameResult == -1) {
                    highlightWinningLine(winPositions); // Highlight the winning line
                    endGame(gameResult); // End the game
                } else if (gameResult == 0) {
                    endGame(gameResult); // Game is a draw
                } else {
                    currentPlayer = 1; // Switch back to player
                }
            }
        }
    }

    // End the game and display the result
    private void endGame(int result) {
        Alert alert = new Alert(AlertType.INFORMATION); // Display an alert message
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

        updateScoreBoard(); // Update the scoreboard
        alert.showAndWait(); // Show alert dialog
        resetBoard(); // Reset the board for a new game
    }

    // Update the scoreboard label
    private void updateScoreBoard() {
        scoreLabel.setText("Player: " + player1Score + " AI: " + aiScore + " Draw: " + drawScore);
    }

    // Make a move on the board
    public boolean makeMove(int row, int col, int playerMark) {
        if (row >= 0 && col >= 0 && row < 3 && col < 3) { // Check if within bounds
            if (playerMark == 0 || board[row][col] == 0) { // Check if cell is empty
                board[row][col] = playerMark; // Make the move
                return true; // Valid move
            }
        }
        return false; // Invalid move
    }

    // Check if there is a winner or a draw
    public int checkWin(int[][] winPositions) {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][0] != 0 && board[i][0] == board[i][1] && board[i][0] == board[i][2]) {
                winPositions[0][0] = i; winPositions[0][1] = 0;
                winPositions[1][0] = i; winPositions[1][1] = 1;
                winPositions[2][0] = i; winPositions[2][1] = 2;
                return board[i][0]; // Return the winner
            }
        }

        // Check columns
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[0][i] != 0 && board[0][i] == board[1][i] && board[0][i] == board[2][i]) {
                winPositions[0][0] = 0; winPositions[0][1] = i;
                winPositions[1][0] = 1; winPositions[1][1] = i;
                winPositions[2][0] = 2; winPositions[2][1] = i;
                return board[0][i]; // Return the winner
            }
        }

        // Check diagonals
        if (board[0][0] != 0 && board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
            winPositions[0][0] = 0; winPositions[0][1] = 0;
            winPositions[1][0] = 1; winPositions[1][1] = 1;
            winPositions[2][0] = 2; winPositions[2][1] = 2;
            return board[0][0]; // Return the winner
        }

        if (board[0][2] != 0 && board[0][2] == board[1][1] && board[0][2] == board[2][0]) {
            winPositions[0][0] = 0; winPositions[0][1] = 2;
            winPositions[1][0] = 1; winPositions[1][1] = 1;
            winPositions[2][0] = 2; winPositions[2][1] = 0;
            return board[0][2]; // Return the winner
        }

        // Check if the board is full (draw)
        boolean boardFull = true;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == 0) {
                    boardFull = false;
                    break;
                }
            }
            if (!boardFull) break;
        }

        return boardFull ? 0 : -2; // Return 0 for draw, -2 if game is still in progress
    }

    // Highlight the winning line on the board
    private void highlightWinningLine(int[][] winPositions) {
        for (int[] pos : winPositions) {
            int row = pos[0];
            int col = pos[1];
            if (row >= 0 && col >= 0) {
                buttons[row][col].setStyle(
                    "-fx-background-color: #FFD700; " + // Gold color for winning line
                    "-fx-text-fill: #000000; " +        // Black text color
                    "-fx-font-weight: bold; " +         // Bold text
                    "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 5, 0, 1, 1);" // Shadow effect
                );
            }
        }
    }

    // Reset the game board for a new game
    public void resetBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = 0;
                buttons[i][j].setText(""); // Clear button text
                buttons[i][j].setDisable(false); // Re-enable button
                buttons[i][j].setStyle(null); // Remove styles
            }
        }
        currentPlayer = 1; // Reset to player 1
    }

    // Print the current state of the board (for debugging)
    private void printBoardState(GameBoard board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board.getBoard()[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Getters and setters for game board and player scores
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
