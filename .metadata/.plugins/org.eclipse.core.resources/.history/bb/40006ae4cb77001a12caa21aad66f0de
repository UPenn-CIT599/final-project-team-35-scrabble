import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/*
 * This is a class dealing with user interface and interactions. 
 */
public class GUI extends Application{
	
	 Button playButton;
	 /*
	  * This is an override method for GUI to run its code.
	  */
	@Override
	public void start(Stage gameWindow) throws Exception {
		
		gameWindow.setTitle("Scrabble");
		
		//Create a button for players to start play.
		playButton = new Button("Play");
		ScrabbleGame game = new ScrabbleGame();
		
		Image startImage = new Image("Letters.png");
	
		Color startBackgroundColor = new Color(255, 253, 228, 1);
		StackPane startLayout = new StackPane();
		startLayout.setStyle("-fx-background-color: startBackgroundColor;");
		startLayout.getChildren().add(playButton);
		startLayout.getChildren().addAll(playButton, new ImageView(startImage));
		Scene startScene = new Scene(startLayout,300,300);
		
		
		Label statusLabel = new Label(game.status);
		StackPane gameLayout = new StackPane();
		gameLayout.getChildren().add(statusLabel);
		Scene gameScene = new Scene(gameLayout,800,800);
		
		playButton.setOnAction(e ->{
			 System.out.println("Playing!");
			 game.Play();
			 gameWindow.setScene(gameScene);
		});
		
		gameWindow.setScene(startScene);
		gameWindow.show();
		
	}
	
	
}
