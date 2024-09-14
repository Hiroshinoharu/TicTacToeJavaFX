package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			// BorderPane is a layout manager that manages all the nodes in 5 areas:
			BorderPane root = new BorderPane();
			
			// Text to be set above the game board
			Label label = new Label("Tic Tac Toe Game");
			
			// Apply a CSS class to the label
			label.getStyleClass().add("game-label");
			
			// Align the text to the center of the window
			root.setTop(label);
			
			// Create an instance of the GameBoard class and add it to the center of the BorderPane
			GameBoard gameBoard = new GameBoard();
			
			// Align the game board to the center of the window
			root.setCenter(gameBoard);
			
			// Create a scene with the BorderPane as the root node
			Scene scene = new Scene(root,600,600);
			
			// Makes the window unresizable
			primaryStage.setResizable(false);
			
			// Add a CSS file to the scene
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			// Set the title of the window
			primaryStage.setScene(scene);
			primaryStage.setTitle("Tic Tac Toe Game");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
