import java.io.FileInputStream;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * This is a class dealing with user interface and interactions. 
 * @author CIT-591 Team-35 Project
 */
public class GUI extends Application{
    
	public static final int TILE_SIZE = 50;
	public static final int ROW = 17;
	public static final int COL = 15;
	
	private Group tileGroup = new Group();
	private Group letterGroup = new Group();
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
        
        
        
        //If the player chooses single player mode
        singlePlayerButton.setOnAction(e ->{
        	System.out.println("Single Player Mode");
        	//refer back to ScrabbleGame option
        	option = 1;
            gameWindow.setScene(MainGameSceneSingle());
        });
        
        //If the player chooses multiple players mode
        multiPlayerButton.setOnAction(e ->{
            System.out.println("Multiple Players Mode");
          //refer back to ScrabbleGame option
            option = 2;
            gameWindow.setScene(MainGameSceneSingle());
        });
        
        gameWindow.setScene(startScene);
        gameWindow.show();
        
    }
    
    /*
     * This method creates the main game scene for single player game.
     */
    public Scene MainGameSceneSingle() {
    	
    	//Create a top menu for the main game scene. 

        HBox topMenu = new HBox(30);
        topMenu.setPadding(new Insets(10,10,10,10));
        Button pause = new Button("Pause");
        Button start = new Button("Start");
        
        //Build a timer
        
        
        topMenu.getChildren().addAll(start, pause);
        
//        //Create a right menu for display scores and letters
//        GridPane rightMenu = new GridPane(); 
//        rightMenu.setHgap(10);
//        rightMenu.setVgap(10);
//        rightMenu.setPadding(new Insets(10,10,10,50));
//        rightMenu.setGridLinesVisible(true);
//        Label score = new Label("Score: ");
//        Label letterLabel = new Label("Letters:");
//        rightMenu.add(score, 0, 0);
//        rightMenu.add(letterLabel, 0, 1);
        
 
        BorderPane singleGameLayout = new BorderPane();
        StackPane board = GameBoard();
        HBox letterRack = LetterRack();
        topMenu.setAlignment(Pos.CENTER);
        board.setAlignment(Pos.CENTER);
        letterRack.setAlignment(Pos.CENTER);
        singleGameLayout.setTop(topMenu);
        singleGameLayout.setCenter(board);
        singleGameLayout.setBottom(letterRack);
        Scene singleGameScene = new Scene(singleGameLayout,1000,1000);
        
        return singleGameScene;
    }
    
    
    
    /*
     * This method creates the game board with rows and columns set.
     */
    public StackPane GameBoard() {
    	StackPane gameBoard = new StackPane();
    	gameBoard.setPrefSize(COL * TILE_SIZE, ROW * TILE_SIZE);
    	
    	//make game tiles
    	for (int y = 0; y < ROW; y++) {
    		if (y < ROW-2) {
    			for (int x = 0; x < COL; x++) {
        			
        			//Add code for decide what status this tile is
        			Tile tile = new Tile(0, x, y);
        			tileGroup.getChildren().add(tile);
        		}
    			
    		//make letter rack tiles	
    		}else if (y == ROW-1) {
    			for (int i = 4; i < 11; i++) {
    				Tile tile = new Tile(9, i, y);
        			tileGroup.getChildren().add(tile);
        			Label letter = new Label("A");
        			letter.relocate(i * TILE_SIZE, TILE_SIZE);
//        			LetterLabel l = makeLetter("A", i, y);
//        			tile.setLetter(l);
        			letterGroup.getChildren().add(letter);
    	    	}
    		}
    	}
    	gameBoard.getChildren().addAll(tileGroup, letterGroup);
    	
    	return gameBoard;
    }
    
    public LetterLabel makeLetter(String l, int x, int y) {
    	LetterLabel letter = new LetterLabel(l, x, y);
    	
    	return letter;
    }
    
    /*
     * This method creates a letter rack.
     */
    public HBox LetterRack() {
    	
    	HBox letterRack = new HBox(10);
    	letterRack.setPadding(new Insets(10,10,10,10));
    	Label letter1 = new Label("A");
    	Label letter2 = new Label("A");
    	Label letter3 = new Label("A");
    	Label letter4 = new Label("A");
    	Label letter5 = new Label("A");
    	Label letter6 = new Label("A");
    	Label letter7 = new Label("A");
    	
    	Button refillButton = new Button("Refill");
    	
    	letterRack.getChildren().addAll(letter1, letter2, letter3, letter4, letter5, letter6, letter7, refillButton);
    	
    	return letterRack;
    }
    
    
}
