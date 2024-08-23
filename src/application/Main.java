package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			// BorderPane is a layout manager that manages all the nodes in 5 areas:
			BorderPane root = new BorderPane();
			
			// Create an instance of the GameBoard class and add it to the center of the BorderPane
			GameBoard gameBoard = new GameBoard();
			root.setCenter(gameBoard);
			
			// Create a scene with the BorderPane as the root node
			Scene scene = new Scene(root,600,600);
			
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
