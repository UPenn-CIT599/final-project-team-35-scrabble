package scrabble;
import java.util.*;
/**
 * Class ScoreKeeper
 * Keeps total score for each Player
 * @author CIT-591 Team-35 Project
 *
 */
public class ScoreKeeper {
    private HashMap<String, Integer> playerScores;
    //private String name;
    //private int score;
    
    /**
     * Constructor for Score Keeper
     * 
     */
    public ScoreKeeper() {
        playerScores = new HashMap<>();        
    }
    
    /**
     * Update the Player scores using the scoreUpdate method
     * @param playerName
     * @param currentTurnScore
     * 
     */
    public void scoreUpdate(String playerName, int currentTurnScore) {
        //String name = playerName;
        boolean newPlayer = true;
        for (String key : playerScores.keySet()) {
            if (key.equals(playerName)) { 
                newPlayer = false;              
                int updateScore = currentTurnScore + playerScores.get(key);                    
                playerScores.replace(key, updateScore);   //Updating previous score for Player with name via "playerName"                 
            } 
        }        
        if (newPlayer) {
            String newName = playerName;  
            int newScore = currentTurnScore;                 
            playerScores.put(newName, newScore);   //Creating new entry and score for Player with name via "playerName"
        }
    }
    
    /**
     * Getter method to get the scores of All Players 
     * @return
     */
    //public ArrayList<Integer> getPlayerScores() {    
    public HashMap<String, Integer> getPlayerScores() {
        return playerScores;
    }   




}
