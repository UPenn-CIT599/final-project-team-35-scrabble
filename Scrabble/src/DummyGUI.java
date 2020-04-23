import java.util.ArrayList;
import java.util.HashMap;

/**
 * This GUI class will interface with the front-end GUI of the Scrabble Game
 * @author CIT-591 Team-35 Project
 *
 */
public class DummyGUI {
    //Open Welcome page after hitting "Run" for "ScrabbleRunner"
    //Welcome Page Shows "Scrabble Game Label", "Click for Instructions button", "Game Options: with "Single Player" and "Multi-Player""
    //Get Player names playing, then start game
    //GameRunner selects first Player for first Turn. Player with Turn is communicated to GUI
    //In game, Player gets filled Letter-Tray from "bagOfLetters"    
    private int eventOptionButton;
    private ArrayList<String> playerNames;
    private HashMap<String, Integer> playerScores;
    private int numPlayers;
    private String name1;
    private String name2;
    private String currentPlayerName;
    private boolean endGame;
    private boolean playerEvent;
    private boolean trayRefillEvent;
    private boolean boardNewWordsEvent;
    private ArrayList<Integer> newBoardPositions;
    private ArrayList<String> newBoardLetters;   
    private boolean wordsError;
       
    /**
     * Constructor for GUI 
     */
    public DummyGUI() {
        //eventOptionButton = 1;  
        playerNames = new ArrayList<>();
        newBoardPositions = new ArrayList<Integer>();
        newBoardLetters = new ArrayList<String>(); 
        wordsError = false;
        endGame = false;
    }
    
    public int getEventOptionButton() {
        return eventOptionButton;
    }
    
    public ArrayList<String> getPlayerNames() {
        return playerNames;
    }    
    
    public int getNumPlayers() {
        return numPlayers;
    }       
    
    public void welcomePage() {
        System.out.println("Welcome to Scrabble Game");
        System.out.println("Click Here for Instructions to the Game");
        System.out.println("Select the Game option to play");
        System.out.println("Click '1' for Single Player Game option, and build your word vocabulary!");
        System.out.println("Click '2' for Two Player Game option, and show who has a better word vocabulary!");   
        eventOptionButton = 2; //1;
        //playerEvent = true;
        
        if (eventOptionButton == 1) {
            //User selects "Single-Player Game
            //instantiate ScrabbleGame with SinglePlayer option selected 
            System.out.println("Single-Player ScrabbleGame Selected");
            name1 = "Player-Name : John";
            playerNames.add(name1);
            numPlayers = this.playerNames.size();                       
        }
        else if (eventOptionButton == 2) {
            //User selects "Multi-Player Game
            //instantiate ScrabbleGame with MultiPlayer option selected    
            System.out.println("Multi-Player ScrabbleGame Selected");
            name1 = "Player-1 : John";   
            System.out.println(name1);
            playerNames.add(name1);
            name2 = "Player-2 : Jill";        
            System.out.println(name2);            
            playerNames.add(name2); 
            numPlayers = playerNames.size();
        } 
        
        for (int i = 0; i < playerNames.size(); i++) {
            String newName = playerNames.get(i);                  
            playerScores.put(newName, 0);   //Creating new entry and score for Player with name via "playerName" 
        }            
        currentPlayerName = playerNames.get(0); 
    }  
    
    public void Player1Steps() {
        
    }
    
    public void displayScrabbleBoard(ArrayList<Cell[]> displayBoard) {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                System.out.print("" + displayBoard.get(i)[j].getCellValues() + "  ");                
            }
            System.out.println();
        }         
    }
    
    public void displayCurrentTray(ArrayList<Letter> displayTrayLetters) {
        for (int i = 0; i < displayTrayLetters.size(); i++) {
                System.out.print("" + displayTrayLetters.get(i).getLetterName() + "  ");                            
        }    
        System.out.println();        
    }    
 
    public String getCurrentPlayer() {
        return currentPlayerName;
    }
    
    public void setCurrentPlayer(String name) {
        currentPlayerName = name;
    }
    
    public ArrayList<Integer> getNewBoardPositions() {
        return newBoardPositions;
    }
    
    public void setNewBoardPositions() { //(ArrayList<String[]> resetNewBoardPositions) {
        newBoardPositions.clear();// = resetNewBoardPositions;
    }   
    
    public ArrayList<String> getNewBoardLetters() {
        return newBoardLetters;
    }
    
    public void setNewBoardLetters() {
        newBoardLetters.clear();;
    }  
    
    public void setPlayerScores(HashMap<String, Integer> updatedScores) {
        playerScores.clear();
        playerScores = updatedScores;
    }      
    
    public boolean getPlayerEvent() {
        return playerEvent;
    }      
    
    public void setPlayerEvent(boolean resetPlayerEvent) {
        playerEvent = resetPlayerEvent;
    }      

    public boolean getTrayRefillEvent() {
        return trayRefillEvent;
    }     
    
    public void setTrayRefillEvent(boolean resetRefillEvent) {
        trayRefillEvent = resetRefillEvent;
    }     
    
    public boolean getBoardNewWordsEvent() {
        return boardNewWordsEvent;
    }   
    
    public void setBoardNewWordsEvent(boolean resetBoardNewWordsEvent) {
        boardNewWordsEvent = resetBoardNewWordsEvent;
    }          
    
    public void setWordsError(boolean wordsSelectionError) {
        wordsError = wordsSelectionError;
    }  
    
    public boolean getEndGame() {
        return endGame;
    }    
    
}
