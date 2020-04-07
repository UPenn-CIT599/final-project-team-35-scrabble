import java.util.*;

/**
 * Class Player
 * @author CIT-591 Team-35 Project
 *
 */
public class Player {
    private int totalScore = 0;
    private ArrayList<Integer> wordScores;
    private HashMap<Integer, ArrayList<String>> wordSelectionsPerTurn;
    private int numLettersToReplace;
    private ArrayList<String> currentLetterTray;    
    private ArrayList<String> newLetterTray;
    private ArrayList<String> newLetters;    
    
    /**
     * Constructor for Player
     */
    public Player(int numTurn, int updatedScore, int wordScore, ArrayList<String> words) {
        wordSelectionsPerTurn = new HashMap<>();
        wordScores = new ArrayList<Integer>();
        totalScore += updatedScore;
        wordScores.add(wordScore);   
        wordSelectionsPerTurn.put(numTurn, words); 
        currentLetterTray = new ArrayList<String>();
        newLetterTray = new ArrayList<String>();        
    }
    
    /**
     * method for updating (or replacing) Player's current letter Tray with a new Tray of Letters
     */
    public ArrayList<String> newTrayOfLetters(boolean replaceFullTray, int numLettersToReplace, ArrayList<String> currentLetters, ArrayList<String> newLetters) {
        numLettersToReplace = numLettersToReplace;
        newLetterTray.clear();
        newLetterTray.addAll(currentLetters);
        if (replaceFullTray) {
            numLettersToReplace = 7;
            newLetterTray.clear();            
        }
        newLetterTray.addAll(newLetters);  
        currentLetterTray.clear();
        currentLetterTray.addAll(newLetterTray);            
        return newLetterTray;        
    }       
            
    /**
     * Getter method for returning current Total Score of this Player
     */
    public int getTotalScore() {             
        return totalScore;        
    }   
    
    /**
     * Getter method for returning current LetterTray of this Player
     */
    public ArrayList<String> getCurrentLetterTray() {             
        return currentLetterTray;        
    }      

}
