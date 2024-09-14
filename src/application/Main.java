package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

// Main class extends Application to create a JavaFX application
public class Main extends Application {
    
    // The start method is the main entry point for any JavaFX application
    @Override
    public void start(Stage primaryStage) {
        try {
            // BorderPane is a layout manager that manages nodes in five regions: top, bottom, left, right, and center
            BorderPane root = new BorderPane();
            
            // Creating a Label to display text at the top of the window
            Label label = new Label("Tic Tac Toe Game VS AI by Max Ceban");
            
            // Adding a CSS class named 'game-label' to the label for styling
            label.getStyleClass().add("game-label");
            
            // Placing the label at the top of the BorderPane
            root.setTop(label);
            
            // Creating an instance of the GameBoard class, which represents the game board
            GameBoard gameBoard = new GameBoard();
            
            // Placing the game board at the center of the BorderPane
            root.setCenter(gameBoard);
            
            // Creating a scene with the BorderPane as the root node and setting the dimensions to 600x600
            Scene scene = new Scene(root, 600, 600);
            
            // Preventing the window from being resized
            primaryStage.setResizable(false);
            
            // Adding a CSS stylesheet to the scene for styling
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            
            // Setting the scene and title of the primary stage (window)
            primaryStage.setScene(scene);
            primaryStage.setTitle("Tic Tac Toe Game");
            
            // Displaying the primary stage
            primaryStage.show();
        } catch (Exception e) {
            // Print any exceptions that occur during execution
            e.printStackTrace();
        }
    }
    
    // The main method to launch the JavaFX application
    public static void main(String[] args) {
        launch(args);
    }
}
