//import java.util.Scanner;
import java.util.*;

/**
 * This is the ScrabbleGame class 
 * @author CIT-591 Team-35 Project
 *
 */
public class ScrabbleGame {
        private Random rand;
        private ArrayList<Player> players;        
        private BagOfLetters bagOfLetters;
        private ScoreKeeper scoreKeeper;
        private Dictionary dictionary;
        private Letter currentLetter;
        private String[] words;        
        private Board board;
        private boolean playing;
        private boolean singlePlayerGame;
        private boolean endGame;
        //private GUI gui;
        private int CurrentPlayerWordScore;
        private ArrayList<String> CurrentPlayerWords;   
        private int CurrentPlayerTotalScore;   
        private HashMap<String, Integer> allPlayerScores;          
        
        /**
         * ScrabbleGame Constructor
         * @param option
         */
        public ScrabbleGame(int option, ArrayList<Player> allPlayers) {
            rand = new Random();
            dictionary = new Dictionary();
            scoreKeeper = new ScoreKeeper();
            //gui = new GUI(); //GUI is instantiating the game instead of the other way. GUI is acting like ScrabbleRunner!
            singlePlayerGame = false;
            playing = true;
            players = new ArrayList<Player>();
            players.addAll(allPlayers); //NOTE: player list added/instantiated in the GUI class, via inputs from the Players
            
            if (option == 1) {
               singlePlayerGame = true;
            } else {
                singlePlayerGame = false;                
            }
        }
        
        /**
         * This method plays the game of 'Scrabble'
         */
        public void play() {
            String status; //dummy string. May or may not use it
            while (playing) {
                bagOfLetters = new BagOfLetters();
                board = new Board();
                while ((!bagOfLetters.isEmpty()) || !endGame) {
                    status = "Playing";                   
                    /*/TODO: Figure out how to communciate with the GUI via JavaFx
                    playerTurn();
                    display();
                    playerTurn(); 
                    play, playerTurn, getScore, gameResult, playAgain, reStartPlay */
                }
            }
            //gui.setClose();
        }

        /**
         * This method displays in the GUI: the Board, score and the status of the Player with the current Turn.
         */
        private void display() {
            CurrentPlayerWordScore = 0; //dummyWordScore
            CurrentPlayerWords.add("dummyWord1");  //This could be an ArrayList<String>
            CurrentPlayerTotalScore = 0; //dummyTotalScore
            board.currentBoard();   
            gameResult();
        }
        
        private void playerTurn() {
            boolean complete = false;
            while (!complete) {
                String currentPlayer = "dummyPlayer";
            }
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
}