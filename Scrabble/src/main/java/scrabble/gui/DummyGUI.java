package scrabble.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import scrabble.Cell;
import scrabble.Letter;
import scrabble.Player;
import scrabble.ScoreKeeper;
import scrabble.ScrabbleGame;
import scrabble.events.BoardEvent;
import scrabble.events.GameEventListener;
import scrabble.events.LetterEvent;
import scrabble.gui.controllers.BoardGUIController;
import scrabble.gui.controllers.WelcomeGUIController;

/**
 * This GUI class will interface with the front-end GUI of the Scrabble Game
 * 
 * @author CIT-591 Team-35 Project
 *
 */
public class DummyGUI extends Application implements GameEventListener {
    // Open Welcome page after hitting "Run" for "ScrabbleRunner"
    // Welcome Page Shows "Scrabble Game Label", "Click for Instructions button",
    // "Game Options: with "Single Player" and "Multi-Player""
    // Get Player names playing, then start game
    // GameRunner selects first Player for first Turn. Player with Turn is
    // communicated to GUI
    // In game, Player gets filled Letter-Tray from "bagOfLetters"

    private ArrayList<String> playerNames;
    private HashMap<String, Integer> playerScores;
    private int numPlayers;
    private boolean endGame;

    // GUI Variables
    private Stage primaryStage;
    private BoardGUIController boardController;

    private ScrabbleGame scrabble;

    private ArrayList<String> boardPositionIds;
    private ArrayList<Integer> boardPositions;
    private ArrayList<String> boardLettersIds;
    private String currentLetterId;

    /**
     * Constructor for GUI
     */
    public DummyGUI() {

        playerNames = new ArrayList<>();
        playerScores = new HashMap<>();
        boardPositionIds = new ArrayList<>();
        boardPositions = new ArrayList<>();
        boardLettersIds = new ArrayList<>();
        currentLetterId = "";
        endGame = false;
    }

    public ArrayList<String> getPlayerNames() {
        return playerNames;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public String getCurrentPlayerName() {
        return this.scrabble.getCurrentPlayer().getName();
    }

    public ArrayList<Letter> getLettersTray() {
        return this.scrabble.getCurrentPlayer().getCurrentLetterTray();
    }

    public ArrayList<Letter> getBagOfLetters() {
        return this.scrabble.getBagOfLetters().getCurrentBagOfLetters();
    }

    public boolean getEndGame() {
        return endGame;
    }

    public String getInstructions() {
        String dummyInstruction = "Click the letter in the rack and then click any tile you want to relocate the letter.\n"+
                "    \n"+
                "First word must be played using the center square with a star. \n"+
                "    \n"+
                "Each time you finish this round, click submit button , it will pass to the other player or your next round. \n"+
                "     \n"+
                "The more words you made, the more score you win. Each letter has its own score and some tiles have bonus effects. \n"+
                "     \n"+
                "You can search a word in dictionary by clicking search button , swap tiles from tile bag by clicking swap button , pass your turn by clicking pass button , and undo your step by clicking undo button  \n"+
                "      \n"+
                " Made with love by Shodhan, Daisy and Mina. Have fun!! \n"+
                "      \n";
        return dummyInstruction;
    }

    // changes the player and UPDATE UI
    public void changePlayer(boolean undoFlag) {

        // If undo then place back letters to tray
        if (undoFlag) {
            for (String bid : boardPositionIds) {
                boardController.updateBoard(bid, null);
            }
        }

        boardLettersIds.clear();
        boardPositionIds.clear();
        boardPositions.clear();
        this.currentLetterId = "";

        if (!undoFlag)
            this.scrabble.changePlayer();

        boardController.higlightCurrentPlayer(getCurrentPlayerName());
        boardController.initializeLetterRack(getLettersTray());
    }

    public void submit() {

        if (boardLettersIds.size() == 0 || boardPositionIds.size() == 0) {
            changePlayer(false);
            return;
        }

        ArrayList<String> boardLetters = new ArrayList<>();

        for (String s : boardLettersIds) {
            boardLetters.add(Character.toString(s.charAt(1)));
        }

        boolean error = scrabble.playEvent(boardPositions, boardLetters);
        System.out.println("Error :" + error);

        if (!error) {

            int newScore = this.scrabble.getCurrentPlayer().getTotalScore();
            playerScores.put(getCurrentPlayerName(), newScore);
            

            // Reflect it in GUI
            this.boardController.updateScores(getCurrentPlayerName(), newScore);

            // Refill the current player tray
            this.scrabble.refillEvent();
            changePlayer(false);          
        } else {

            boardController.showInfoDialog("Error", "Invalid !");
            
            for (String bid : boardPositionIds) {
                boardController.updateBoard(bid, null);
            }
            
            if (this.scrabble.getCurrentPlayer().getErrorChancesPerTurn() >= 2) {  
                this.scrabble.getCurrentPlayer().setErrorChancesPerTurn(0);               
                changePlayer(false);  
            }  else {
                this.scrabble.getCurrentPlayer().setErrorChancesPerTurn(1 + this.scrabble.getCurrentPlayer().getErrorChancesPerTurn());                
                changePlayer(true);                  
            }
            System.out.println("Number of chances already taken by Current Player : " + this.scrabble.getCurrentPlayer().getErrorChancesPerTurn());        
        }

        checkEndStatus();
        //changePlayer(false);

    }


    public boolean searchWord(String word){

      return this.scrabble.checkWordExists(word);
    }


    private void checkEndStatus() {

        if (scrabble.getGameStatus()) {
            
            String gameWinner = maxScorePlayer() ;
            System.out.println(gameWinner);
            System.out.println(scrabble.scoreKeeper.getPlayerScores().toString());
            boardController.showInfoDialog("Game Ended", "Congratulation! " + gameWinner);
            boardController.close();
            boardLettersIds.clear();
            boardPositionIds.clear();
            this.currentLetterId = "";

            this.primaryStage.show();
        }

    }


    // Swap the seleted letters of tray
    public void swapLetters(ArrayList<String> toSwap) {

        if (toSwap.size() == 0)
            return;

        Player currPlayer = scrabble.getCurrentPlayer();

        ArrayList<Letter> lettersToAddBack = new ArrayList<>();
        int i = 0;

        while (i < toSwap.size()) {
            // 1. Remove the letters from currentPlayerTray and add to bag of letters
            for (Letter l : currPlayer.getCurrentLetterTray()) {
                if (toSwap.get(i).equals(l.getLetterName())) {
                    lettersToAddBack.add(l);
                    currPlayer.getCurrentLetterTray().remove(l);
                    break;
                }
            }
            i++;
        }

        // Add the letters back to bag
        scrabble.getBagOfLetters().addLetters(lettersToAddBack);

        scrabble.refillEvent();

        endGame = scrabble.getGameStatus();
        changePlayer(false);

    }

    // ------------------------------------------------------------//

    public void welcomePage(String[] args) {
        launch(args);
    }

    public ArrayList<Cell[]> getBoard() {
        return scrabble.getDisplay();
    }

    // Called by WelcomeGUIController on recieving the names
    public void initializePlayers(ArrayList<String> playerNames) {

        this.playerNames = playerNames;
        this.numPlayers = playerNames.size();

        for (int i = 0; i < playerNames.size(); i++) {
            String newName = playerNames.get(i);
            System.out.println(newName);
            playerScores.put(newName, 0); // Creating new entry and score for Player with name via "playerName"
        }

    }

    // Save Game
    public void saveGame() {

    }

    // End the game and closes the window
    public void endGame(Stage stage) {

        this.scrabble.setEndGame(true);
        boardLettersIds.clear();
        boardPositionIds.clear();
        this.currentLetterId = "";

        checkEndStatus();

        this.primaryStage.show();
        // closes the window
        stage.close();
    }


    //Scrore 
    public String maxScorePlayer(){

        int max = -1;
        String p = "";
        for(Map.Entry<String,Integer> entry : playerScores.entrySet() ){

            if(entry.getValue() > max ){
                max = entry.getValue();
                p = entry.getKey();
            }

        }
        return p;
    }


    // Start the game
    // Display the UI
    public void startGame() {

        // Initialize a new Game (backend)
        scrabble = new ScrabbleGame(playerNames);

        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/board.fxml"));
            Parent root = (Parent) loader.load();
            boardController = loader.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();

            boardController.attachHelper(this, stage);
            boardController.initGUI(); // Setup the UI

        } catch (final IOException e) {
            e.printStackTrace();
        }

        this.primaryStage.hide();
    }

    @Override
    public void start(Stage stage) throws Exception {

        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/welcome.fxml"));
            Parent root = (Parent) loader.load();
            WelcomeGUIController controller = loader.getController();
            controller.attachHelper(this);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
            stage.setScene(scene);
            this.primaryStage = stage;
            stage.show();

            stage.setOnCloseRequest(new javafx.event.EventHandler<WindowEvent>() {

                @Override
                public void handle(WindowEvent event) {
                    stage.close();
                }
            });

        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    // Any cell of board is clicked
    @Override
    public void onClickBoardEvent(BoardEvent event) {

        if (this.currentLetterId.equals(""))
            return;

        int x = event.getX();
        int y = event.getY();
        int pos = x * 15 + y;
        Cell cell = scrabble.getBoard().getCell(pos);
        if (cell.getPositionTaken())
            return;

        for (Integer p : boardPositions) {
            if (p == pos)
                return;
        }

        boardLettersIds.add(this.currentLetterId);
        boardPositionIds.add(event.getId());
        boardPositions.add(pos);

        System.out.println(boardLettersIds.toString());
        System.out.println(boardPositionIds.toString());

        String letterName = Character.toString(currentLetterId.charAt(1));

        System.out.println(letterName);

        // cell.setLetter(new Letter(letterName));
        // cell.setPositionTaken(true);

        boardController.updateBoard(event.getSource(), letterName);
        boardController.updateLetterTray(this.currentLetterId, "case");
        this.currentLetterId = "";

    }

    @Override
    public void onLetterTrayEvent(LetterEvent event) {
        // Add the current letter to selected letter

        String currLetter = event.getLetter();
        String id = event.getSource().getId();

        for (String s : boardLettersIds) {
            if (s.equals(id))
                return;
        }
        System.out.println(currLetter);
        this.currentLetterId = id;
    }

}
