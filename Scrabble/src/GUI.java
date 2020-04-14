import java.io.FileInputStream;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * This is a class dealing with user interface and interactions. 
 * @author CIT-591 Team-35 Project
 */
public class GUI extends Application{
    
    int option; 

    
     /**
      * This is an override method for GUI to run its code.
      */
    @Override
    public void start(Stage gameWindow) throws Exception {
        
    	//Create a window for the game
        gameWindow.setTitle("Scrabble");
        
        //Create a start scene for player to choose from single player mode or multiple player mode
        Button singlePlayerButton = new Button("Single Player");
        Button multiPlayerButton = new Button("Multiple Players");
        Button playButton = new Button("Play");
        FileInputStream welcomeImage = new FileInputStream("Letters.png");
        Image startImage = new Image(welcomeImage);
        Color startBackgroundColor = new Color(1, 0.992, 0.894, 1);
        VBox startLayout = new VBox(20);
        startLayout.getChildren().addAll(new ImageView(startImage), singlePlayerButton, multiPlayerButton);
        startLayout.setAlignment(Pos.CENTER);
        Scene startScene = new Scene(startLayout,800,800,startBackgroundColor);
        
        //Create a top menu for the main game scene. 
        HBox topMenu = new HBox(30);
        topMenu.setPadding(new Insets(10,10,10,10));
        Button pause = new Button("Pause");
        Button resume = new Button("Resume");
        Button restart = new Button("Restart");
        topMenu.getChildren().addAll(pause, resume, restart);
        
        //Create a right menu for display scores and letters
        GridPane rightMenu = new GridPane();
        rightMenu.setHgap(10);
        rightMenu.setVgap(10);
        rightMenu.setPadding(new Insets(10,10,10,50));
        rightMenu.setGridLinesVisible(true);
        Label score = new Label("Score: ");
        Label letterLabel = new Label("Letters:");
        rightMenu.add(score, 0, 0);
        rightMenu.add(letterLabel, 0, 1);
        
        //Create a game board
        GridPane gameBoard = new GridPane();
        
        
        BorderPane singleGameLayout = new BorderPane();
        singleGameLayout.setTop(topMenu);
        singleGameLayout.setRight(rightMenu);
        Scene singleGameScene = new Scene(singleGameLayout,800,800);
        
        
        //If the player chooses single player mode
        singlePlayerButton.setOnAction(e ->{
        	System.out.println("Single Player Mode");
        	//refer back to ScrabbleGame option
        	option = 1;
            gameWindow.setScene(singleGameScene);
        });
        
        //If the player chooses multiple players mode
        multiPlayerButton.setOnAction(e ->{
            System.out.println("Multiple Players Mode");
          //refer back to ScrabbleGame option
            option = 2;
            gameWindow.setScene(singleGameScene);
        });
        
        gameWindow.setScene(startScene);
        gameWindow.show();
        
    }
    
    
}
