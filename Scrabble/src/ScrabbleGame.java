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
    private int startPosition;
    private int allowedErrorChances;

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
    private ArrayList<Cell[]> testNewBoard;   
    private boolean firstWordTaken;
    private boolean newWordsError;
    private ArrayList<Cell> newBoardPositionCells;
    private ArrayList<Letter> newBoardLetters;
    private boolean verticalLetters;
    private boolean horizontalLetters; 
    private boolean singleLetter;  
    private int minPositionOfBoard;
    private int maxPositionOfBoard;
    //private boolean checkSurroundingPositions;
    StringBuilder newWord;
    private int newLettersOnlyScore;
    private int newWordScore; 
    private int newTotalScore;
    private int wordMultiplier;
    private int letterMultiplier;

    //private boolean refillLetterTray;
    private BagOfLetters bagOfLetters;
    public ScoreKeeper scoreKeeper;
    private ArrayList<Letter> tempLetterTray;     

    //private Letter currentLetter;
    //private String[] words;        

    private boolean endGame;
    //private GUI gui;
    private int currentPlayerWordScore;
    private ArrayList<String> currentPlayerWords;   
    private int currentPlayerTotalScore;   
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
        firstWordTaken = false;
        newWordsError = false;
        newBoardPositionCells = new ArrayList<Cell>();
        newBoardLetters = new ArrayList<Letter>();
        verticalLetters = false;
        horizontalLetters = false;
        singleLetter = false;
        minPositionOfBoard = 999;
        maxPositionOfBoard = -1;    
        currentPlayerWords = new ArrayList<String>();    
        newWord = new StringBuilder();
        startPosition = 112;
        allowedErrorChances = 2;
        tempLetterTray = new ArrayList<Letter>();
        newLettersOnlyScore = 0;
        newWordScore = 0;
        endGame = false;
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
    public void playEvent(ArrayList<Integer> boardPositions, ArrayList<String> boardLetters) {
        testNewBoard = new ArrayList<Cell[]>();  
        testNewBoard = board.getCurrentBoard();
        newBoardPositionCells.clear();
        newBoardLetters.clear();
        minPositionOfBoard = 999;
        maxPositionOfBoard = -1;
        //getSurroundingBoardPositions();
        //Finding if the letters are vertical or horizontal or a single letter
        singleLetter = false;
        verticalLetters = false;
        horizontalLetters = false;        
        
        if (boardPositions.size() < 2) {
            singleLetter = true;
        } else if (((boardPositions.get(1) - boardPositions.get(0)) % 15) == 0) {
            verticalLetters = true;
        } else {
            horizontalLetters = true;
        }
        
        // Get word selections from current player, and checkError
        //Get word Selections
        //Case-1: 1st word in the Board. Can be handled by GUI itself
        if (!board.getCell(startPosition).getPositionTaken()) {
            for (int i = 0; i < boardPositions.size(); i++) {
                if (boardPositions.get(i) == startPosition) {
                    firstWordTaken = true;                   
                }
            }    
            if (firstWordTaken) {
                board.getCell(startPosition).setPositionTaken(true);
            }
        }
        
        if (board.getCell(startPosition).getPositionTaken()) {
            //Converting to Cell, and getLetters
            for (int i = 0; i < boardPositions.size(); i++) {
                newBoardPositionCells.add(board.getCell(boardPositions.get(i)));
                Letter tempLetter = new Letter(boardLetters.get(i));
                newBoardLetters.add(tempLetter);   
                //find minPosition, and max position
                if (boardPositions.get(i) < minPositionOfBoard) {
                    minPositionOfBoard = boardPositions.get(i);
                }
                if (boardPositions.get(i) > maxPositionOfBoard) {
                    maxPositionOfBoard = boardPositions.get(i);
                }                                       
            }    
            getWordList(boardPositions, boardLetters); //
            //Check words against dictionary for errorCheck                
            newWordsError = checkError(currentPlayerWords); //if error, set newWordsError = true;
        } else {
            newWordsError = true;
        }     
        if (newWordsError) { //This happens if the first word selected is NOT in the dictionary
            currentPlayer.setErrorChancesPerTurn(1 + currentPlayer.getErrorChancesPerTurn());
            //Reset all positions Taken
            for (int i = 0; i < boardPositions.size(); i++) {
                board.getCell(boardPositions.get(i)).setPositionTaken(false);  
                board.getCell(boardPositions.get(i)).setLetter(null);   
                board.getCell(boardPositions.get(i)).setVerticalWord(null);  
                board.getCell(boardPositions.get(i)).setVerticalScore(0);  
                board.getCell(boardPositions.get(i)).setHorizontalWord(null);  
                board.getCell(boardPositions.get(i)).setHorizontalScore(0);                 
            }            
            if (currentPlayer.getErrorChancesPerTurn() == allowedErrorChances) {
                currentPlayer.setErrorChancesPerTurn(0);
                newWordsError = false;                
                changePlayer();
            }
        } else {
            tempLetterTray.clear();
            tempLetterTray = currentPlayer.getCurrentLetterTray();           
            for (int i = 0; i < boardLetters.size(); i++) {
                boolean checkedLetter = false;                
                for (int j = 0; j < tempLetterTray.size(); j++) {
                    if (!checkedLetter) {
                        if (boardLetters.get(i).equalsIgnoreCase(tempLetterTray.get(j).getLetterName())) {
                            tempLetterTray.remove(j);
                            checkedLetter = true;
                        }                      
                    }
                }
            }
            currentPlayer.setErrorChancesPerTurn(0);
            currentPlayer.setCurrentLetterTray(tempLetterTray);  
            int tempTotalScore = currentPlayer.getTotalScore();             
            currentPlayer.setTotalScore(newTotalScore + tempTotalScore);             
            newWordsError = false;            
            changePlayer();         
        }
    }
    
    private void getWordList(ArrayList<Integer> positions, ArrayList<String> letters) {
        currentPlayerWords.clear(); 
        ArrayList<Integer> tempPositions = new ArrayList<Integer>();
        ArrayList<String> tempLetters = new ArrayList<String>();  
        tempPositions = positions;
        tempLetters = letters;
        newLettersOnlyScore = 0;
        newWordScore = 0;
        newTotalScore = 0;
        wordMultiplier = 1;
        letterMultiplier = 1;
        if (newWord.length() != 0) {
            newWord.delete(0, newWord.length());            
        }
        //Check word from minPosition to maxPosition
        if (verticalLetters) {
            if (board.containsVerticalWord(minPositionOfBoard - 15)) {
                newWord.append(board.getCell(minPositionOfBoard - 15).getVerticalWord());    
                newLettersOnlyScore += board.getCell(minPositionOfBoard - 15).getVerticalScore();
                newWordScore += board.getCell(minPositionOfBoard - 15).getVerticalScore(); 
            }
            int len = 1 + ((maxPositionOfBoard - minPositionOfBoard) / 15);
            int position = (positions.get(0) - 15);
            
            for (int i = 0; i < len; i++) {
                if ((position + 15) == tempPositions.get(i)) {
                    newWord.append(tempLetters.get(i)); 
                    String tempCellValue = board.getCell(tempPositions.get(i)).getCellValues();
                    if (tempCellValue.equalsIgnoreCase("TW")) {
                        wordMultiplier = 3;
                    } else if (tempCellValue.equalsIgnoreCase("DW")) {
                        wordMultiplier = 2;
                    } else if (tempCellValue.equalsIgnoreCase("TL")) {
                        letterMultiplier = 3;
                    } else if (tempCellValue.equalsIgnoreCase("DL")) {
                        letterMultiplier = 2;
                    } 
                    Letter tempLetter = new Letter(tempLetters.get(i));                    
                    newLettersOnlyScore += tempLetter.convertNameToValue(tempLetters.get(i)); //This does not include the letter or word multiplier
                    newWordScore += letterMultiplier * tempLetter.convertNameToValue(tempLetters.get(i));
                } else {
                    //This condition applies when there is a letter in the Board between selected letters
                    tempPositions.add(i, position + 15);
                    tempLetters.add(i, board.getCell(tempPositions.get(i)).getLetter().getLetterName());
                    newWord.append(tempLetters.get(i)); 
                    Letter tempLetter = new Letter(tempLetters.get(i));                    
                    newLettersOnlyScore += tempLetter.convertNameToValue(tempLetters.get(i)); //This does not include the letter or word multiplier
                    newWordScore += tempLetter.convertNameToValue(tempLetters.get(i));                    
                }
                position = position + 15;               
            }
            if (maxPositionOfBoard < (210 + 1)) {
                if (board.containsVerticalWord(maxPositionOfBoard + 15)) {
                    newWord.append(board.getCell(maxPositionOfBoard + 15).getVerticalWord());  
                    newLettersOnlyScore += board.getCell(maxPositionOfBoard + 15).getVerticalScore();
                    newWordScore += board.getCell(maxPositionOfBoard + 15).getVerticalScore();                     
                }                 
            }
            //Added Vertical Word
            currentPlayerWords.add(newWord.toString()); 
            //Setting VerticalWords for future reference
            board.getCell(minPositionOfBoard).setVerticalWord(newWord.toString());               
            board.getCell(maxPositionOfBoard).setVerticalWord(newWord.toString());             
            board.getCell(minPositionOfBoard).setVerticalScore(newLettersOnlyScore);               
            board.getCell(maxPositionOfBoard).setVerticalScore(newLettersOnlyScore);  
            newWordScore = newWordScore * wordMultiplier;
            newTotalScore += newWordScore;            
            
            //Now find the remaining horizontal words
            len = positions.size(); //resizing len to only new letters added in board
            for (int i = 0; i < len; i++) {
                newLettersOnlyScore = 0;
                newWordScore = 0;
                wordMultiplier = 1;
                letterMultiplier = 1;
                if (newWord.length() != 0) {
                    newWord.delete(0, newWord.length());            
                }    
                if (((positions.get(i) % 15) - 1) >= 0) {                
                    if (board.containsHorizontalWord(positions.get(i) - 1)) {
                        newWord.append(board.getCell(positions.get(i) - 1).getHorizontalWord());   
                        newLettersOnlyScore += board.getCell(positions.get(i) - 1).getHorizontalScore();
                        newWordScore += board.getCell(positions.get(i) - 1).getHorizontalScore();                         
                    }
                }
                newWord.append(letters.get(i)); 
                String tempCellValue = board.getCell(positions.get(i)).getCellValues();                
                if (tempCellValue.equalsIgnoreCase("TW")) {
                    wordMultiplier = 3;
                } else if (tempCellValue.equalsIgnoreCase("DW")) {
                    wordMultiplier = 2;
                } else if (tempCellValue.equalsIgnoreCase("TL")) {
                    letterMultiplier = 3;
                } else if (tempCellValue.equalsIgnoreCase("DL")) {
                    letterMultiplier = 2;
                } 
                Letter tempLetter = new Letter(letters.get(i));                    
                newLettersOnlyScore += tempLetter.convertNameToValue(letters.get(i)); //This does not include the letter or word multiplier
                newWordScore += letterMultiplier * tempLetter.convertNameToValue(letters.get(i));
                
                if (((positions.get(i) % 15) + 1) <= 14) {                   
                    if (board.containsHorizontalWord(positions.get(i) + 1)) {
                        newWord.append(board.getCell(positions.get(i) + 1).getHorizontalWord());   
                        newLettersOnlyScore += board.getCell(positions.get(i) + 1).getHorizontalScore();
                        newWordScore += board.getCell(positions.get(i) + 1).getHorizontalScore();                         
                    }   
                }
                currentPlayerWords.add(newWord.toString());   
                //Setting HorizintalWords for future reference                
                board.getCell(positions.get(i)).setHorizontalWord(newWord.toString());   
                board.getCell(positions.get(i)).setHorizontalScore(newLettersOnlyScore);  
                newWordScore = newWordScore * wordMultiplier;
                newTotalScore += newWordScore;
            }                                            
        } else if (horizontalLetters) {
            if (board.containsHorizontalWord(minPositionOfBoard - 1)) {
                newWord.append(board.getCell(minPositionOfBoard - 1).getHorizontalWord()); 
                newLettersOnlyScore += board.getCell(minPositionOfBoard - 1).getHorizontalScore();
                newWordScore += board.getCell(minPositionOfBoard - 1).getHorizontalScore();                 
            }
            int len = 1 + (maxPositionOfBoard - minPositionOfBoard);
            int position = (positions.get(0) - 1);
            
            for (int i = 0; i < len; i++) {
                if ((position + 1) == tempPositions.get(i)) {
                    newWord.append(tempLetters.get(i));  
                    String tempCellValue = board.getCell(tempPositions.get(i)).getCellValues();
                    if (tempCellValue.equalsIgnoreCase("TW")) {
                        wordMultiplier = 3;
                    } else if (tempCellValue.equalsIgnoreCase("DW")) {
                        wordMultiplier = 2;
                    } else if (tempCellValue.equalsIgnoreCase("TL")) {
                        letterMultiplier = 3;
                    } else if (tempCellValue.equalsIgnoreCase("DL")) {
                        letterMultiplier = 2;
                    } 
                    Letter tempLetter = new Letter(tempLetters.get(i));                    
                    newLettersOnlyScore += tempLetter.convertNameToValue(tempLetters.get(i)); //This does not include the letter or word multiplier
                    newWordScore += letterMultiplier * tempLetter.convertNameToValue(tempLetters.get(i));                    
                } else {
                    //This condition applies when there is a letter in the Board between selected letters
                    tempPositions.add(i, position + 1);
                    tempLetters.add(i, board.getCell(tempPositions.get(i)).getLetter().getLetterName());
                    newWord.append(tempLetters.get(i));  
                    Letter tempLetter = new Letter(tempLetters.get(i));                    
                    newLettersOnlyScore += tempLetter.convertNameToValue(tempLetters.get(i)); //This does not include the letter or word multiplier
                    newWordScore += tempLetter.convertNameToValue(tempLetters.get(i));                      
                }
                position = position + 1;               
            }
            if ((maxPositionOfBoard %15) < 14) {
                if (board.containsHorizontalWord(maxPositionOfBoard + 1)) {
                    newWord.append(board.getCell(maxPositionOfBoard + 1).getHorizontalWord());   
                    newLettersOnlyScore += board.getCell(maxPositionOfBoard + 1).getHorizontalScore();
                    newWordScore += board.getCell(maxPositionOfBoard + 1).getHorizontalScore();                     
                }                 
            }
            //Added Horizontal Word
            currentPlayerWords.add(newWord.toString()); 
            newWordScore = newWordScore * wordMultiplier;
            newTotalScore += newWordScore;            
            //Setting HorizintalWords for future reference
            board.getCell(minPositionOfBoard).setHorizontalWord(newWord.toString());               
            board.getCell(maxPositionOfBoard).setHorizontalWord(newWord.toString());  
            board.getCell(minPositionOfBoard).setHorizontalScore(newLettersOnlyScore);               
            board.getCell(maxPositionOfBoard).setHorizontalScore(newLettersOnlyScore);             
            
            //Now find the remaining vertical words
            len = positions.size(); //resizing len to only new letters added in board            
            for (int i = 0; i < len; i++) {
                newLettersOnlyScore = 0;
                newWordScore = 0;
                wordMultiplier = 1;
                letterMultiplier = 1;                
                if (newWord.length() != 0) {
                    newWord.delete(0, newWord.length());            
                }     
                if ((positions.get(i) - 15) >= 0) {
                    if (board.containsVerticalWord(positions.get(i) - 15)) {
                        newWord.append(board.getCell(positions.get(i) - 15).getVerticalWord());     
                        newLettersOnlyScore += board.getCell(positions.get(i) - 15).getVerticalScore();
                        newWordScore += board.getCell(positions.get(i) - 15).getVerticalScore();                         
                    }                    
                }
                newWord.append(letters.get(i)); 
                String tempCellValue = board.getCell(positions.get(i)).getCellValues();                
                if (tempCellValue.equalsIgnoreCase("TW")) {
                    wordMultiplier = 3;
                } else if (tempCellValue.equalsIgnoreCase("DW")) {
                    wordMultiplier = 2;
                } else if (tempCellValue.equalsIgnoreCase("TL")) {
                    letterMultiplier = 3;
                } else if (tempCellValue.equalsIgnoreCase("DL")) {
                    letterMultiplier = 2;
                } 
                Letter tempLetter = new Letter(letters.get(i));                    
                newLettersOnlyScore += tempLetter.convertNameToValue(letters.get(i)); //This does not include the letter or word multiplier
                newWordScore += letterMultiplier * tempLetter.convertNameToValue(letters.get(i));
                
                if ((positions.get(i) + 15) <= 225) {                
                    if (board.containsVerticalWord(positions.get(i) + 15)) {
                        newWord.append(board.getCell(positions.get(i) + 15).getVerticalWord());  
                        newLettersOnlyScore += board.getCell(positions.get(i) + 15).getVerticalScore();
                        newWordScore += board.getCell(positions.get(i) + 15).getVerticalScore();                         
                    }   
                }
                currentPlayerWords.add(newWord.toString());  
                //Setting VerticalWords for future reference                
                board.getCell(positions.get(i)).setVerticalWord(newWord.toString());  
                board.getCell(positions.get(i)).setVerticalScore(newLettersOnlyScore);  
                newWordScore = newWordScore * wordMultiplier;
                newTotalScore += newWordScore;                
            }  
        } else if (singleLetter) {
            //Find any Vertical or Horizontal words
            //Find vertical word
            if (minPositionOfBoard > (14 + 1)) {            
                if (board.containsVerticalWord(minPositionOfBoard - 15)) {
                    newWord.append(board.getCell(minPositionOfBoard - 15).getVerticalWord());   
                    newLettersOnlyScore += board.getCell(minPositionOfBoard - 15).getVerticalScore();
                    newWordScore += board.getCell(minPositionOfBoard - 15).getVerticalScore();                     
                }
            }
            newWord.append(tempLetters.get(0)); 
            String tempCellValue = board.getCell(tempPositions.get(0)).getCellValues();
            if (tempCellValue.equalsIgnoreCase("TW")) {
                wordMultiplier = 3;
            } else if (tempCellValue.equalsIgnoreCase("DW")) {
                wordMultiplier = 2;
            } else if (tempCellValue.equalsIgnoreCase("TL")) {
                letterMultiplier = 3;
            } else if (tempCellValue.equalsIgnoreCase("DL")) {
                letterMultiplier = 2;
            } 
            Letter tempLetter = new Letter(tempLetters.get(0));                    
            newLettersOnlyScore += tempLetter.convertNameToValue(tempLetters.get(0)); //This does not include the letter or word multiplier
            newWordScore += letterMultiplier * tempLetter.convertNameToValue(tempLetters.get(0));            
            if (maxPositionOfBoard < (210 + 1)) {
                if (board.containsVerticalWord(maxPositionOfBoard + 15)) {
                    newWord.append(board.getCell(maxPositionOfBoard + 15).getVerticalWord());   
                    newLettersOnlyScore += board.getCell(maxPositionOfBoard + 15).getVerticalScore();
                    newWordScore += board.getCell(maxPositionOfBoard + 15).getVerticalScore();                     
                }                 
            }  
            currentPlayerWords.add(newWord.toString());            
            //Setting Vertical Words to the concerned cells for later use
            //board.getCell(minPositionOfBoard).setVerticalWord(newWord.toString());   //Not needed as min and max positions are the same, as it is a single letter           
            board.getCell(maxPositionOfBoard).setVerticalWord(newWord.toString());
            //board.getCell(minPositionOfBoard).setVerticalScore(newLettersOnlyScore);               
            board.getCell(maxPositionOfBoard).setVerticalScore(newLettersOnlyScore);  
            newWordScore = newWordScore * wordMultiplier;
            newTotalScore += newWordScore;            
                                    
            //Find Horizontal Word
            newLettersOnlyScore = 0;
            newWordScore = 0;
            //wordMultiplier = 1;
            //letterMultiplier = 1;            
            if (newWord.length() != 0) {
                newWord.delete(0, newWord.length());            
            }                
            if ((maxPositionOfBoard %15) > 0) {            
                if (board.containsHorizontalWord(minPositionOfBoard - 1)) {
                    newWord.append(board.getCell(minPositionOfBoard - 1).getHorizontalWord());  
                    newLettersOnlyScore += board.getCell(minPositionOfBoard - 1).getHorizontalScore();
                    newWordScore += board.getCell(minPositionOfBoard - 1).getHorizontalScore();                      
                }
            }
            newWord.append(tempLetters.get(0));
            /* String tempCellValue = board.getCell(tempPositions.get(0)).getCellValues();
            if (tempCellValue.equalsIgnoreCase("TW")) {
                wordMultiplier = 3;
            } else if (tempCellValue.equalsIgnoreCase("DW")) {
                wordMultiplier = 2;
            } else if (tempCellValue.equalsIgnoreCase("TL")) {
                letterMultiplier = 3;
            } else if (tempCellValue.equalsIgnoreCase("DL")) {
                letterMultiplier = 2;
            } 
            //Letter tempLetter = new Letter(tempLetters.get(0));  */                  
            newLettersOnlyScore += tempLetter.convertNameToValue(tempLetters.get(0)); //This does not include the letter or word multiplier
            newWordScore += letterMultiplier * tempLetter.convertNameToValue(tempLetters.get(0));  
            
            if ((maxPositionOfBoard %15) < 14) {
                if (board.containsHorizontalWord(maxPositionOfBoard + 1)) {
                    newWord.append(board.getCell(maxPositionOfBoard + 1).getHorizontalWord()); 
                    newLettersOnlyScore += board.getCell(maxPositionOfBoard + 1).getHorizontalScore();
                    newWordScore += board.getCell(maxPositionOfBoard + 1).getHorizontalScore();                    
                }                 
            }  
            currentPlayerWords.add(newWord.toString());            
            //Setting Horizontal Words to the concerned cells for later use
            //board.getCell(minPositionOfBoard).setHorizontalWord(newWord.toString());   //Not needed as min and max positions are the same, as it is a single letter           
            board.getCell(maxPositionOfBoard).setHorizontalWord(newWord.toString());     
            //board.getCell(minPositionOfBoard).setHorizontalScore(newLettersOnlyScore);               
            board.getCell(maxPositionOfBoard).setHorizontalScore(newLettersOnlyScore);  
            newWordScore = newWordScore * wordMultiplier;
            newTotalScore += newWordScore;             
        }   
        
        //return currentPlayerWords;
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
        if (!bagOfLetters.isEmpty()) {
            newLetters = bagOfLetters.shuffleAndPickLetters(7 - sizeOfCurrentTray);
            currentPlayer.trayOfLetters(refillFullTray, newLetters);             
        } else {
            endGame = true;
        }
        
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
    
    public boolean checkError(ArrayList<String> wordList) {      
        boolean newWordsError = false;
        boolean tempError = true;
        //To do: check new words here
        while (!newWordsError) {
            for (int i = 0; i < wordList.size(); i++) {
                //Check if word is in dictionary
                for (int j = 0; j < dictionaryWords.size(); j ++) {
                    if (wordList.get(i).equalsIgnoreCase(dictionaryWords.get(j))) {
                        tempError = false;
                    }
                }
                if (tempError) {
                    newWordsError = true; //So atleast one word from word list is NOT in the dictionary, and hence an error
                }
            }   
        }

        
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
    
    public boolean getNewWordsError() {
        return newWordsError;
    }       
}