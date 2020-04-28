package scrabble;
import java.util.*;

/**
 * Class Player
 * @author CIT-591 Team-35 Project
 *
 */
public class Player {
    private String name;
    private int totalScore = 0;
    private ArrayList<Integer> wordScores;
    private HashMap<Integer, ArrayList<String>> wordSelectionsPerTurn;
    private int numLettersToReplace;
    private ArrayList<Letter> currentLetterTray;    
    private ArrayList<String> currentLetterTrayInString;     
    private ArrayList<Letter> newLetters; 
    private int errorChancesPerTurn;

    /**
     * Constructor for Player
     */
    public Player(String playerName) { 
        name = playerName;
        currentLetterTray = new ArrayList<Letter>();
        currentLetterTrayInString = new ArrayList<String>();        
        errorChancesPerTurn = 0;
        totalScore = 0;
    }
    
    /**
     * method for updating (or replacing) Player's current letter Tray with a new Tray of Letters
     */
    public ArrayList<Letter> trayOfLetters(boolean replaceFullTray, ArrayList<Letter> newLetters) {          
        if (replaceFullTray) {
            numLettersToReplace = 7;
            currentLetterTray.clear();            
        }
        currentLetterTray.addAll(newLetters);  
           
        return currentLetterTray;        
    }       
            
    /**
     * Getter method for returning current Total Score of this Player
     */
    public int getTotalScore() {             
        return totalScore;        
    }   
    
    /**
     * Setter method to update Total Score of this Player
     */
    public void setTotalScore(int tScore) {             
        totalScore = tScore;        
    }     
    
    /**
     * Getter method for returning current LetterTray of this Player
     */
    public ArrayList<Letter> getCurrentLetterTray() {             
        return currentLetterTray;        
    } 

    /**
     * Getter method for returning current LetterTray with ONLY Letter Names (in String) of this Player
     */
    public ArrayList<String> getCurrentLetterTrayInString() { 
        currentLetterTrayInString.clear();
        for (int i = 0; i < this.currentLetterTray.size(); i++) {
            currentLetterTrayInString.add(this.getCurrentLetterTray().get(i).getLetterName());
        }         
        return currentLetterTrayInString;        
    }        
    
    /**
     * Setter method for re-filling current LetterTray of this Player
     */
    public void setCurrentLetterTray(ArrayList<Letter> letterTray) {             
        currentLetterTray = letterTray;        
    }     
    
    /**
     * Getter method for returning name of this Player
     */
    public String getName() {             
        return name;        
    }    
    
    /**
     * Getter method for returning number of Errors already allowed (for current Turn) for this Player
     */
    public int getErrorChancesPerTurn() {             
        return errorChancesPerTurn;        
    }     
    
    /**
     * Setter method for assigning number of Errors given for this Player, for current Turn
     */
    public void setErrorChancesPerTurn(int errors) {             
        errorChancesPerTurn = errors;        
    }       
}
