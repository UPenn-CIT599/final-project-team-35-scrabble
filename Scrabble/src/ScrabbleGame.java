//import java.util.Scanner;
import java.util.*;

/**
 * This is the ScrabbleGame class 
 * @author CIT-591 Team-35 Project
 *
 */
public class ScrabbleGame {
    private boolean playing;    
    private Random rand;

    //Variables for Dictionary reference, for word check 
    private Dictionary dictionary;
    private ArrayList<String> dictionaryWords; 

    //Variables for info on all Players: names, wordSelections      
    private ArrayList<Player> players;  
    private boolean singlePlayerGame; 
    private Player currentPlayer;  
    private int playerIndex;

    //Variables for Board status        
    private Board board;
    private ArrayList<Cell[]> display;

    private boolean refillLetterTray;
    private BagOfLetters bagOfLetters;
    public ScoreKeeper scoreKeeper;

    private Letter currentLetter;
    private String[] words;        

    private boolean endGame;
    //private GUI gui;
    private int CurrentPlayerWordScore;
    private ArrayList<String> CurrentPlayerWords;   
    private int CurrentPlayerTotalScore;   
    private HashMap<String, Integer> allPlayerScores;  
    //private ArrayList<Integer> allPlayerScores;         


    /**
     * ScrabbleGame Constructor
     * @param option
     */
    //public ScrabbleGame(int option, ArrayList<Player> allPlayers) {
    public ScrabbleGame(ArrayList<String> allPlayerNames) {   
        playing = true;               
        rand = new Random(); 
        createDictionary(); //Creating Dictionary for exhaustive word reference
        initializePlayers(allPlayerNames); //Initializing Players playing this game       
        board = new Board(); //Initializing Board, and sending status of board to GUI   
        playerIndex = 0;
        currentPlayer = players.get(playerIndex);         
        bagOfLetters = new BagOfLetters();
        //Initializing Score Keeper           
        scoreKeeper = new ScoreKeeper();            
    }

    public void createDictionary() {
        //Creating reference Dictionary for error-check with word choices. 
        //NOTE: Remove or Ignore ln-1 and ln-2 when referencing this "CollinsScrabbleWords2019.txt" dictionary
        dictionary = new Dictionary();
        dictionary.createAndCopyDictionaryToArray();
        dictionaryWords = dictionary.getDictionaryWordsAsList();
        dictionaryWords.remove(0);
        dictionaryWords.remove(1);             
    }

    public void initializePlayers(ArrayList<String> playerNames) {
        singlePlayerGame = true; //Initial assumption. To be changed if more than one player is playing              
        //Initializing all the players for this game.
        //Initial draft allows 2 Players, but final version may allow upto 4 Players. Show error if more than 4 Players!!!
        players = new ArrayList<Player>();            
        for (int i=0; i < playerNames.size(); i++) {                    
            Player player1 = new Player(playerNames.get(0));
            players.add(player1);   
            if (playerNames.size() > 1) {
                singlePlayerGame = false;                          
                Player player2 = new Player(playerNames.get(1));                    
                players.add(player2);     
                if (playerNames.size() > 2) {
                    Player player3 = new Player(playerNames.get(2));                    
                    players.add(player3);                     
                    if (playerNames.size() > 3) {  
                        Player player4 = new Player(playerNames.get(3));                    
                        players.add(player4); 
                    }
                }                          
            }
        }            
    }

    /**
     * This method checks the word selection of the current Player, and returns an error if wrong, else updates the current Players tray, and updates the Board in the game of 'Scrabble'
     */
    public void playEvent(ArrayList<String[]> boardPositions, ArrayList<String> boardLetters) {
        //String status; //dummy string. May or may not use it
        ArrayList<Letter> trayOfLetters = player.getCurrentLetterTray();
        int sizeOfCurrentTray = player.getCurrentLetterTray().size();
        boolean refillFullTray = false;
        
        if (refillLetterTray) {
            ArrayList<Letter> newLetters = new ArrayList<Letter>();
            newLetters = bagOfLetters.shuffleAndPickLetters(7 - sizeOfCurrentTray);
            currentPlayer.trayOfLetters(refillFullTray, newLetters);          
        }
        
        while (playing) {
            //bagOfLetters = new BagOfLetters();
            //board = new Board();
            if ((!bagOfLetters.isEmpty()) || (!endGame)) {
                if ()
                
                //status = "Playing";                   
                /*/TODO: Figure out how to communicate with the GUI via JavaFx
                    playerTurn();
                    display();
                    playerTurn(); 
                    play, playerTurn, getScore, gameResult, playAgain, reStartPlay */
            }
            else {
                playing = false;
            }
        }
        //gui.setClose();
    }
    
    /**
     * This method refills the LetterTray of the current player playing the game of 'Scrabble'
     */
    public void refillEvent() {
        //String status; //dummy string. May or may not use it
        //ArrayList<Letter> trayOfLetters = currentPlayer.getCurrentLetterTray();
        int sizeOfCurrentTray = currentPlayer.getCurrentLetterTray().size();
        boolean refillFullTray = false;
        ArrayList<Letter> newLetters = new ArrayList<Letter>();
        newLetters = bagOfLetters.shuffleAndPickLetters(7 - sizeOfCurrentTray);
        currentPlayer.trayOfLetters(refillFullTray, newLetters);          
    }    

    /**
     * This method displays in the GUI: the Board, score and the status of the Player with the current Turn.
     */
    public ArrayList<Cell[]> getDisplay() {
        //CurrentPlayerWordScore = 0; //dummyWordScore
        //CurrentPlayerWords.add("dummyWord1");  //This could be an ArrayList<String>
        //CurrentPlayerTotalScore = 0; //dummyTotalScore
        display = new ArrayList<Cell[]>();        
        display = board.getCurrentBoard();   
        //gameResult();
        return display;
    } 

    public void changePlayer() {
        //boolean newPlayersTurn = changePlayer;
        if (playerIndex == (players.size() - 1)) {
            playerIndex = 0;
        } else {
            playerIndex += 1;
        }
        currentPlayer = players.get(playerIndex);                  
    }
    
    public boolean checkError() {
        boolean newWordsError = false;
        return newWordsError;
    }
    
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * This method sends out the scores of each Player from the game to the GUI.
     * @return
     */
    private void gameResult() {
        allPlayerScores = scoreKeeper.getPlayerScores();
    }

    /**
     * This method determines whether the user will play again.
     */
    private void playAgain() {
        String inputFromGUI = "Play Again"; //dummy String. Needs input from GUI
        if (inputFromGUI.toLowerCase().equals("play again")) {
            playing = true;
        }
    }

    /**
     * This method determines whether the user will restart play.
     * Scrabble is a long game, and is the Players want to restart play, this method can be called to restart the Scrabble Game
     */
    private void restartPlay() {
        String inputFromGUI = "Restart Play"; //dummy String. Needs input from GUI
        if (inputFromGUI.toLowerCase().equals("restart play")) {
            playing = true;
        }
    }    
    
    public boolean getBagOfLettersIsEmpty() {
        return bagOfLetters.isEmpty();
    }
    
    public void setEndGame(boolean gameStatus) {
        endGame = gameStatus;
    }    
}