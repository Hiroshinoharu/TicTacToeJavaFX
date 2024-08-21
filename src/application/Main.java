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
			Scene scene = new Scene(root,400,400);
			
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
