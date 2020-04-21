import java.util.ArrayList;

//import javafx.application.Application;
//import javafx.stage.Stage;

/**
 * This class tests the game of Scrabble
 * @author CIT-591 Team-35 Project
 *
 */ /*
public class ScrabbleRunner extends Application{
    
    public static void main(String[] args) {
        launch(args); // need to install JavaFx application properly for this to work!
    }

    @Override
    public void start(Stage gameWindow) throws Exception {
        
        GUI gameUI = new GUI();
        gameUI.start(gameWindow);
    }

} */


public class ScrabbleRunner {
    private static boolean continuePlay;
    //private static boolean takeNextStepFlag;
    private static boolean errorFlag;   

    /**
     * The main method
     * @param args
     */ 
    public static void main(String[] args) {
        DummyGUI gameUI = new DummyGUI();
        ArrayList<String> playerNames = new ArrayList<String>();  
        gameUI.welcomePage();
        playerNames = gameUI.getPlayerNames();                    
        ScrabbleGame game = new ScrabbleGame(playerNames); // Until the GUI is completed  
        
        continuePlay = !gameUI.getEndGame(); //true;     
        errorFlag = false;
      
        while(continuePlay) {
            gameUI.displayScrabbleBoard(game.getDisplay());
            gameUI.setCurrentPlayer(game.getCurrentPlayer().getName()); 
            game.setEndGame(gameUI.getEndGame());
            //takeNextStepFlag = gameUI.getPlayerEvent();
            while (gameUI.getPlayerEvent()) {
                if (gameUI.getTrayRefillEvent()) {
                    game.refillEvent();
                    gameUI.setTrayRefillEvent(false);
                } else if (gameUI.getBoardNewWordsEvent()) {
                    game.playEvent(gameUI.getNewBoardPositions(), gameUI.getNewBoardLetters());
                    gameUI.setBoardNewWordsEvent(false);                      
                    //game.checkWords();
                    errorFlag = game.checkError();
                    gameUI.setWordsError(errorFlag);     
                    gameUI.displayScrabbleBoard(game.getDisplay());
                    gameUI.displayCurrentTray(game.getCurrentPlayer().getCurrentLetterTray());
                    gameUI.setNewBoardPositions();
                    gameUI.setNewBoardLetters();    
                    gameUI.setPlayerScores(game.scoreKeeper.getPlayerScores());  
                    
                    if (!errorFlag) {
                        game.changePlayer();
                    } else {
                        //game.changePlayer();    //NOTE: This condition Only if we want the current Player to redo the letters on the Board due to the error!                    
                    }
                    gameUI.setWordsError(false); //resetting errorFlag                      
                    gameUI.setPlayerEvent(false);       
                    gameUI.setCurrentPlayer(game.getCurrentPlayer().getName());                       
                }                               
            }  
            
            if ((gameUI.getEndGame()) || (game.getBagOfLettersIsEmpty())) {
                continuePlay = false;
            }
        }

        
    }
}
