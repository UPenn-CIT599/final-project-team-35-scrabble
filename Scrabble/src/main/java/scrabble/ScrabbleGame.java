package scrabble;

//import java.util.Scanner;
import java.util.*;

/**
 * This is the ScrabbleGame class
 * 
 * @author CIT-591 Team-35 Project
 *
 */
public class ScrabbleGame {

    private boolean playing;
    private Random rand;
    private int startPosition;
    private int allowedErrorChances;

    // Variables for Dictionary reference, for word check
    private Dictionary dictionary;
    private ArrayList<String> dictionaryWords;

    // Variables for info on all Players: names, wordSelections
    private ArrayList<Player> players;

    private boolean singlePlayerGame;
    private Player currentPlayer;
    private int playerIndex;

    // Variables for Board status
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
    private int minVertWordPosition = 999;
    private int maxVertWordPosition = -1;      
    private int minHorWordPosition = 999;
    private int maxHorWordPosition = -1;    
    //private int minHorPositionOfWord = 999;
    //private int maxHorPositionOfWord = -1;      
    //private int minPositionOfLetters;
    //private int maxPositionOfLetters;    
    // private boolean checkSurroundingPositions;
    StringBuilder newWord;
    private int newLettersOnlyScore;
    private int newWordScore;
    private int newTotalScore;
    private int wordMultiplier;
    private int letterMultiplier;

    // private boolean refillLetterTray;
    private BagOfLetters bagOfLetters;
    public ScoreKeeper scoreKeeper;
    private ArrayList<Letter> tempLetterTray;

    // private Letter currentLetter;
    // private String[] words;

    private boolean endGame;
    // private GUI gui;
    private int currentPlayerWordScore;
    private ArrayList<String> currentPlayerWords;
    private int currentPlayerTotalScore;
    private HashMap<String, Integer> allPlayerScores;
    // private ArrayList<Integer> allPlayerScores;

    /**
     * ScrabbleGame Constructor
     * 
     * @param option
     */
    // public ScrabbleGame(int option, ArrayList<Player> allPlayers) {
    public ScrabbleGame(ArrayList<String> allPlayerNames) {
        playing = true;
        rand = new Random();
        createDictionary(); // Creating Dictionary for exhaustive word reference

        bagOfLetters = new BagOfLetters();
        board = new Board(); // Initializing Board, and sending status of board to GUI

        initializePlayers(allPlayerNames); // Initializing Players playing this game
        playerIndex = 0;
        currentPlayer = players.get(playerIndex);

        // Initializing Score Keeper
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
        minVertWordPosition = 999;
        maxVertWordPosition = -1;   
        minHorWordPosition = 999;
        maxHorWordPosition = -1;         
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
        // Creating reference Dictionary for error-check with word choices.
        // NOTE: Remove or Ignore ln-1 and ln-2 when referencing this
        // "CollinsScrabbleWords2019.txt" dictionary
        dictionary = new Dictionary();
        dictionary.createAndCopyDictionaryToArray();
        dictionaryWords = dictionary.getDictionaryWordsAsList();
        dictionaryWords.remove(0);
        dictionaryWords.remove(0);
    }

    public void initializePlayers(ArrayList<String> playerNames) {

        singlePlayerGame = true; // Initial assumption. To be changed if more than one player is playing
        // Initializing all the players for this game.
        // Initial draft allows 2 Players, but final version may allow upto 4 Players.
        // Show error if more than 4 Players!!!
        players = new ArrayList<Player>();

        if (playerNames.size() > 1)
            singlePlayerGame = false;

        for (int i = 0; i < playerNames.size(); i++) {
            Player player = new Player(playerNames.get(i));
            players.add(player);
            player.setCurrentLetterTray(bagOfLetters.shuffleAndPickLetters(7));
        }

    }


    /**
     * This method checks the word selection of the current Player, and returns an
     * error if wrong, else updates the current Players tray, and updates the Board
     * in the game of 'Scrabble'
     */
    public boolean playEvent(ArrayList<Integer> boardPositions, ArrayList<String> boardLetters) {

        newWordsError = false;
        testNewBoard = new ArrayList<Cell[]>();
        testNewBoard = board.getCurrentBoard();
        newBoardPositionCells.clear();
        newBoardLetters.clear();
        minPositionOfBoard = 999;
        maxPositionOfBoard = -1;
        //minPositionOfLetters = 999;
        //maxPositionOfLetters = -1;        
        //ArrayList<Boolean> lettersFlag = new ArrayList<Boolean>();

        singleLetter = false;
        verticalLetters = false;
        horizontalLetters = false;

        //Used for sorting the provided positions and letters
        HashMap<Integer, String> sortingLetters = new HashMap<Integer, String>();

        for (int i = 0; i< boardPositions.size(); i++) {
            sortingLetters.put(boardPositions.get(i), boardLetters.get(i));  
            System.out.println("Before Sorting Board Letters");      
            System.out.print(boardPositions.get(i) + " ");            
            System.out.println(boardLetters.get(i) + " ");            
        }
      
        //Sorting the positions and letters
        Collections.sort(boardPositions);
        boardLetters.clear();
        for (int i = 0; i< boardPositions.size(); i++) {
            boardLetters.add(sortingLetters.get(boardPositions.get(i)));  
            System.out.println("Sorting sent Board Letters");
        }        
        
        for (int i = 0; i< boardPositions.size(); i++) { 
            System.out.print(boardPositions.get(i) + " ");            
            System.out.println(boardLetters.get(i) + " ");           
        }              

        // Finding if the letters are vertical or horizontal or a single letter
        if (boardPositions.size() < 2) {
            singleLetter = true;
        } else if (((boardPositions.get(1) - boardPositions.get(0)) % 15) == 0) {
            verticalLetters = true;
            //Check if all letters are all vertical letters, else invalid (not supported by this game)
            for (int i = 0; i < (boardPositions.size() - 1); i++) { 
                System.out.println("Board Position (i + 1) for Vert Letter : " + boardPositions.get(i + 1));     
                System.out.println("Board Position (i) for Vert Letter : " + boardPositions.get(i));                 
                if (((boardPositions.get(i + 1) - boardPositions.get(i)) % 15) != 0) {  
                    System.out.println("Horizontal Letters included with Vertical Letters : Invalid");                    
                    newWordsError = true; 
                }
            }
        } else {
            horizontalLetters = true;
            //Check if all letters are all horizontal letters, else invalid (not supported by this game)            
            for (int i = 0; i < (boardPositions.size() - 1); i++) { 
                System.out.println("Board Position (i + 1) for Hor Letter : " + boardPositions.get(i + 1));     
                System.out.println("Board Position (i) for Hor Letter : " + boardPositions.get(i));   
                System.out.println("Board Position (i + 1)/15 for Hor Letter : " + (boardPositions.get(i + 1)/15));     
                System.out.println("Board Position (i)/15 for Hor Letter : " + (boardPositions.get(i)/15));                 
                //if ((boardPositions.get(i + 1) - boardPositions.get(i)) > 14) {
                if ((boardPositions.get(i + 1)/15) != (boardPositions.get(i)/15)) {                  
                    System.out.println("Vertical Letters included with Horizontal Letters : Invalid");                    
                    newWordsError = true; 
                }                
            }            
        }

        // Get word selections from current player, and checkError
        // Get word Selections
        // Case-1: 1st word in the Board. Can be handled by GUI itself
        if (!newWordsError) {
            if (!board.getCell(startPosition).getPositionTaken()) {
                for (int i = 0; i < boardPositions.size(); i++) {
                    if (boardPositions.get(i) == startPosition) {
                        firstWordTaken = true;
                    }
                }
                /*
                 * if (firstWordTaken) { board.getCell(startPosition).setPositionTaken(true); }
                 */
            }           
        }


        if (firstWordTaken && (!newWordsError)) {
            // if (board.getCell(startPosition).getPositionTaken()) {
            // Converting to Cell, and getLetters
            for (int i = 0; i < boardPositions.size(); i++) {
                // board.getCell(boardPositions.get(i)).setPositionTaken(true);
                Letter tempLetter = new Letter(boardLetters.get(i));
                // board.getCell(boardPositions.get(i)).setLetter(tempLetter);
                newBoardPositionCells.add(board.getCell(boardPositions.get(i)));
                newBoardLetters.add(tempLetter);
                // find minPosition, and max position
                if (boardPositions.get(i) < minPositionOfBoard) {
                    minPositionOfBoard = boardPositions.get(i);
                }
                if (boardPositions.get(i) > maxPositionOfBoard) {
                    maxPositionOfBoard = boardPositions.get(i);
                }
            }
            
            //Call the Scrabble Algorithm, and get word list
            System.out.println("Calling Scrabble Algorithm : ");
            scrabbleAlgo(boardPositions, boardLetters); //
            // Check words against dictionary for errorCheck
            if (!newWordsError) {
                for (int j = 0; j < currentPlayerWords.size(); j++) {
                    System.out.println("Word-" + j + " : " + currentPlayerWords.get(j));
                }               
            } else {
                for (int i = 0; i < boardPositions.size(); i++) {
                    if (boardPositions.get(i) == startPosition) {
                        firstWordTaken = false;
                    }
                }                
            }
            //newWordsError = checkError(currentPlayerWords); // if error, set newWordsError = true;
        } else {
            newWordsError = true;
        }

        if (newWordsError) { // This happens if the first word selected is NOT in the dictionary
            //currentPlayer.setErrorChancesPerTurn(1 + currentPlayer.getErrorChancesPerTurn());
            // Reset all positions Taken
            for (int i = 0; i < boardPositions.size(); i++) {
                board.getCell(boardPositions.get(i)).setPositionTaken(false);
                //board.getCell(boardPositions.get(i)).setLetter(null);
                //board.getCell(boardPositions.get(i)).setVerticalWord(null);
                board.getCell(boardPositions.get(i)).setVerticalScore(0);
                //board.getCell(boardPositions.get(i)).setHorizontalWord(null);
                board.getCell(boardPositions.get(i)).setHorizontalScore(0);
            }
            // if (currentPlayer.getErrorChancesPerTurn() == allowedErrorChances) {
            //     currentPlayer.setErrorChancesPerTurn(0);
            //     newWordsError = false;
            //     changePlayer();
            // }
        } 
        else {
            // tempLetterTray.clear();
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

            for (int i = 0; i < boardPositions.size(); i++) {
                board.getCell(boardPositions.get(i)).setLetter(newBoardLetters.get(i));
                board.getCell(boardPositions.get(i)).setPositionTaken(true);
                board.getCell(boardPositions.get(i)).setCellValues(); //If position is Taken, sets CellValue to the LetterValue
            }

            int tempTotalScore = currentPlayer.getTotalScore();
            currentPlayer.setTotalScore(newTotalScore + tempTotalScore);
            newWordsError = false;
            // changePlayer();
        }
        System.out.println("New Word Error returned from playEvent : " + newWordsError);    
        return newWordsError;
    }    

    private void scrabbleAlgo(ArrayList<Integer> positions, ArrayList<String> letters) {
        currentPlayerWords.clear();
        ArrayList<Integer> tempPositions = new ArrayList<Integer>();
        ArrayList<String> tempLetters = new ArrayList<String>();
        for (int i = 0; i < positions.size(); i++) {
            tempPositions.add(positions.get(i));
        }
        //tempPositions = positions;
        tempLetters = letters;
        newLettersOnlyScore = 0;
        newWordScore = 0;
        newTotalScore = 0;
        wordMultiplier = 1;
        letterMultiplier = 1;
        if (newWord.length() != 0) {
            newWord.delete(0, newWord.length());
        }
        // Check word from minPosition to maxPosition
        if (verticalLetters) {
            System.out.println("Min Position of Board for Single Letter is : " + minPositionOfBoard);
            System.out.println("Max Position of Board for Single Letter is : " + maxPositionOfBoard);     
            System.out.println("Vertical Word for Min Position is : " + board.getCell(minPositionOfBoard - 15).getVerticalWord());
            System.out.println("Vertical Word for Max Position is : " + board.getCell(maxPositionOfBoard + 15).getVerticalWord());
            System.out.println("Horizontal Word for Min Position is : " + board.getCell(minPositionOfBoard - 1).getHorizontalWord());
            System.out.println("Horizontal Word for Max Position is : " + board.getCell(maxPositionOfBoard + 1).getHorizontalWord());  

            boolean vertWordExists = false;            
            vertWordExists = scrabbleAlgoVertWordMinPosHelper(vertWordExists, minPositionOfBoard); 
            int len = 1 + ((maxPositionOfBoard - minPositionOfBoard) / 15);
            int position = (positions.get(0) - 15);

            for (int i = 0; i < len; i++) {
                if ((position + 15) == tempPositions.get(i)) {
                    newWord.append(tempLetters.get(i));
                    String tempCellValue = board.getCell(tempPositions.get(i)).getCellValues();
                    scrabbleAlgoMultiplierHelper(tempCellValue);
                    Letter tempLetter = new Letter(tempLetters.get(i));
                    newLettersOnlyScore += tempLetter.convertNameToValue(tempLetters.get(i)); 
                    newWordScore += letterMultiplier * tempLetter.convertNameToValue(tempLetters.get(i));
                } else {
                    // This condition applies when there is a letter in the Board between selected
                    // letters
                    tempPositions.add(i, position + 15);
                    tempLetters.add(i, board.getCell(tempPositions.get(i)).getLetter().getLetterName());
                    newWord.append(tempLetters.get(i));
                    Letter tempLetter = new Letter(tempLetters.get(i));
                    newLettersOnlyScore += tempLetter.convertNameToValue(tempLetters.get(i));
                    newWordScore += tempLetter.convertNameToValue(tempLetters.get(i));
                }
                position = position + 15;
            }
            
            vertWordExists = true; //Always true, when it has already been decided to have vertical letters
            scrabbleAlgoVertWordMaxPosHelper(vertWordExists, maxPositionOfBoard);

            ///////////////////////////////////////////////////////////////////////////////////////

            /////////// Now find the remaining horizontal words ///////////     
            if (!newWordsError) {
                len = positions.size(); // resizing len to only new letters added in board
                boolean horWordExists;
                for (int i = 0; i < len; i++) {
                    if (!newWordsError) {                    
                        horWordExists = false;
                        newLettersOnlyScore = 0;
                        newWordScore = 0;
                        wordMultiplier = 1;
                        letterMultiplier = 1;
                        if (newWord.length() != 0) {
                            newWord.delete(0, newWord.length());
                        }

                        horWordExists = scrabbleAlgoHorWordMinPosHelper(horWordExists, positions.get(i));       

                        newWord.append(letters.get(i));
                        String tempCellValue = board.getCell(positions.get(i)).getCellValues();
                        scrabbleAlgoMultiplierHelper(tempCellValue);
                        Letter tempLetter = new Letter(letters.get(i));
                        newLettersOnlyScore += tempLetter.convertNameToValue(letters.get(i)); 
                        newWordScore += letterMultiplier * tempLetter.convertNameToValue(letters.get(i));

                        scrabbleAlgoHorWordMaxPosHelper(horWordExists, positions.get(i)); 
                    }
                }                
            }
        } else if (horizontalLetters) {
            
            boolean horWordExists = false;
            horWordExists = scrabbleAlgoHorWordMinPosHelper(horWordExists, minPositionOfBoard);
            int len = 1 + (maxPositionOfBoard - minPositionOfBoard);
            int position = (positions.get(0) - 1);
     
            for (int i = 0; i < len; i++) {
                if ((position + 1) == tempPositions.get(i)) {
                    newWord.append(tempLetters.get(i));
                    String tempCellValue = board.getCell(tempPositions.get(i)).getCellValues();
                    scrabbleAlgoMultiplierHelper(tempCellValue);
                    Letter tempLetter = new Letter(tempLetters.get(i));
                    newLettersOnlyScore += tempLetter.convertNameToValue(tempLetters.get(i)); 
                    newWordScore += letterMultiplier * tempLetter.convertNameToValue(tempLetters.get(i));
                } else {
                    // This condition applies when there is a letter in the Board between selected
                    // letters. This does not include the letter or word multiplier
                    tempPositions.add(i, position + 1);
                    tempLetters.add(i, board.getCell(tempPositions.get(i)).getLetter().getLetterName());
                    newWord.append(tempLetters.get(i));
                    Letter tempLetter = new Letter(tempLetters.get(i));
                    newLettersOnlyScore += tempLetter.convertNameToValue(tempLetters.get(i));                                                                                               
                    newWordScore += tempLetter.convertNameToValue(tempLetters.get(i));
                }
                position = position + 1;
            }
            
            horWordExists = true; //Always true, when it has already been decided to have vertical letters            
            scrabbleAlgoHorWordMaxPosHelper(horWordExists, maxPositionOfBoard);              
            ///////////////////////////////////////////////////////////////////////////////////////

            /////////// Find remaining Vertical Words /////////// 
            if (!newWordsError) {            
                len = positions.size(); // resizing len to only new letters added in board
                boolean vertWordExists;
                for (int i = 0; i < len; i++) {
                    if (!newWordsError) {                  
                        vertWordExists = false;
                        newLettersOnlyScore = 0;
                        newWordScore = 0;
                        wordMultiplier = 1;
                        letterMultiplier = 1;
                        if (newWord.length() != 0) {
                            newWord.delete(0, newWord.length());
                        }

                        vertWordExists = scrabbleAlgoVertWordMinPosHelper(vertWordExists, positions.get(i));                  

                        newWord.append(letters.get(i));
                        String tempCellValue = board.getCell(positions.get(i)).getCellValues();
                        scrabbleAlgoMultiplierHelper(tempCellValue);
                        Letter tempLetter = new Letter(letters.get(i));
                        newLettersOnlyScore += tempLetter.convertNameToValue(letters.get(i)); 
                        newWordScore += letterMultiplier * tempLetter.convertNameToValue(letters.get(i));

                        scrabbleAlgoVertWordMaxPosHelper(vertWordExists, positions.get(i));  
                    }
                }
            }
        } else if (singleLetter) {
            /////////// Find Vertical Word ///////////            
            boolean vWordExists = false;
            System.out.println("Min Position of Board for Single Letter is : " + minPositionOfBoard);
            System.out.println("Max Position of Board for Single Letter is : " + maxPositionOfBoard);     
            System.out.println("Vertical Word for Min Position is : " + board.getCell(minPositionOfBoard - 15).getVerticalWord());
            System.out.println("Vertical Word for Max Position is : " + board.getCell(maxPositionOfBoard + 15).getVerticalWord());
            System.out.println("Horizontal Word for Min Position is : " + board.getCell(minPositionOfBoard - 1).getHorizontalWord());
            System.out.println("Horizontal Word for Max Position is : " + board.getCell(maxPositionOfBoard + 1).getHorizontalWord());             

            vWordExists = scrabbleAlgoVertWordMinPosHelper(vWordExists, minPositionOfBoard);             
            
            newWord.append(tempLetters.get(0));
            String tempCellValue = board.getCell(tempPositions.get(0)).getCellValues();
            wordMultiplier = 1;
            letterMultiplier = 1;   
            
            scrabbleAlgoMultiplierHelper(tempCellValue);
            
            Letter tempLetter = new Letter(tempLetters.get(0));
            // This does not include the letter or word multiplier            
            newLettersOnlyScore += tempLetter.convertNameToValue(tempLetters.get(0)); 
            newWordScore += letterMultiplier * tempLetter.convertNameToValue(tempLetters.get(0));
           
            scrabbleAlgoVertWordMaxPosHelper(vWordExists, maxPositionOfBoard);

            /////////// Find Horizontal Word ///////////
            if (!newWordsError) {              
                boolean hWordExists = false;
                newLettersOnlyScore = 0;
                newWordScore = 0;
                wordMultiplier = 1;
                letterMultiplier = 1;
                if (newWord.length() != 0) {
                    newWord.delete(0, newWord.length());
                }

                hWordExists = scrabbleAlgoHorWordMinPosHelper(hWordExists, minPositionOfBoard);             
                newWord.append(tempLetters.get(0));
                scrabbleAlgoMultiplierHelper(tempCellValue);            

                // This does not include the letter or word multiplier
                newLettersOnlyScore += tempLetter.convertNameToValue(tempLetters.get(0));                                                                                       
                newWordScore += letterMultiplier * tempLetter.convertNameToValue(tempLetters.get(0));

                scrabbleAlgoHorWordMaxPosHelper(hWordExists, maxPositionOfBoard);   
            }
        }
        System.out.println("New Word Error returned from scrabbleAlgo : " + newWordsError);              
    }
    
    public void scrabbleAlgoMultiplierHelper(String cellValue) {
        System.out.println("Entering Multiplier Helper");        
        String tempCellValue = cellValue;
        if (tempCellValue.equalsIgnoreCase("TW")) {
            wordMultiplier = 3;
        } else if ((tempCellValue.equalsIgnoreCase("DW")) || (tempCellValue.equalsIgnoreCase("ST"))) {
            wordMultiplier = 2;
        } else if (tempCellValue.equalsIgnoreCase("TL")) {
            letterMultiplier = 3;
        } else if (tempCellValue.equalsIgnoreCase("DL")) {
            letterMultiplier = 2;
        }        
    }

    public boolean scrabbleAlgoVertWordMinPosHelper(boolean vWord, int minPosition) {  
        System.out.println("Entering Vertical Word Min Position Helper");
        boolean vWordExists = vWord; //false;              
        if (minPosition > 14) { //(14 + 1)) {                  
            if (board.containsVerticalWord(minPosition - 15)) {                   
                System.out.println("vWordExists at minPositionBoard - 15");                     
                minVertWordPosition = board.getCell(minPosition - 15).getMinVertPositionOfWord();                 
                newWord.append(board.getCell(minVertWordPosition).getVerticalWord());                    
                newLettersOnlyScore += board.getCell(minVertWordPosition).getVerticalScore();
                newWordScore += board.getCell(minVertWordPosition).getVerticalScore();
                
                System.out.println("vWordExists at minPositionBoard - 15");                      
                System.out.println("minVertWordPosition :" + minVertWordPosition); 
                System.out.println("newWord :" + newWord);                     
                System.out.println("newLettersOnlyScore :" + newLettersOnlyScore);   
                System.out.println("newWordScore :" + newWordScore);                      
                vWordExists = true;             
            } else if (board.getCell(minPosition - 15).getPositionTaken()) {
                minVertWordPosition = minPosition - 15;                
                newWord.append(board.getCell(minVertWordPosition).getCellValues());
                newLettersOnlyScore += board.getCell(minVertWordPosition).getVerticalScore();
                newWordScore += board.getCell(minVertWordPosition).getVerticalScore();                    
                
                System.out.println("vLetterExists at minPositionBoard - 15");  
                System.out.println("minVertWordPosition :" + minVertWordPosition);                 
                System.out.println("newWord :" + newWord); 
                System.out.println("newLettersOnlyScore :" + newLettersOnlyScore);   
                System.out.println("newWordScore :" + newWordScore);                      
                vWordExists = true;                    
            } else {
                minVertWordPosition = minPosition;                
            }
        } else {
            minVertWordPosition = minPosition;            
        }        
        return vWordExists;
    }
    
    public void scrabbleAlgoVertWordMaxPosHelper(boolean vWord, int maxPosition) { 
        System.out.println("Entering Vertical Word Max Position Helper");        
        boolean vWordExists = vWord;           
        if (maxPosition < 210) { //(210 + 1)) {        
            if (board.containsVerticalWord(maxPosition + 15)) {                               
                maxVertWordPosition = board.getCell(maxPosition + 15).getMaxVertPositionOfWord();                    
                newWord.append(board.getCell(maxVertWordPosition).getVerticalWord());                    
                newLettersOnlyScore += board.getCell(maxVertWordPosition).getVerticalScore();
                newWordScore += board.getCell(maxVertWordPosition).getVerticalScore(); 
                
                System.out.println("vWordExists at maxPositionBoard + 15");                      
                System.out.println("maxVertWordPosition :" + maxVertWordPosition); 
                System.out.println("newWord :" + newWord);                     
                System.out.println("newLettersOnlyScore :" + newLettersOnlyScore);   
                System.out.println("newWordScore :" + newWordScore);                       
                vWordExists = true;   
            } else if (board.getCell(maxPosition + 15).getPositionTaken()) {                   
                maxVertWordPosition = maxPosition + 15;                
                newWord.append(board.getCell(maxVertWordPosition).getCellValues()); 
                newLettersOnlyScore += board.getCell(maxVertWordPosition).getVerticalScore();
                newWordScore += board.getCell(maxVertWordPosition).getVerticalScore();                     
                
                System.out.println("vLetterExists at maxPositionBoard + 15");  
                System.out.println("maxVertWordPosition :" + maxVertWordPosition);                     
                System.out.println("newWord :" + newWord);  
                System.out.println("newLettersOnlyScore :" + newLettersOnlyScore);   
                System.out.println("newWordScore :" + newWordScore);                    
                vWordExists = true;                    
            } else {
                maxVertWordPosition = maxPosition;                
            }
        } else {
            maxVertWordPosition = maxPosition;            
        }  

        if (vWordExists) {        
            String checkWordVert = newWord.toString();
            newWordsError = !checkWordExists(checkWordVert); // if error, set newWordsError = true;        
            if (!newWordsError) {
                //if (vWordExists) {
                board.getCell(minVertWordPosition).setVerticalWord(newWord.toString());                
                board.getCell(maxVertWordPosition).setVerticalWord(newWord.toString());                
                board.getCell(minVertWordPosition).setVerticalScore(newLettersOnlyScore);                
                board.getCell(maxVertWordPosition).setVerticalScore(newLettersOnlyScore);
                board.getCell(minVertWordPosition).setMaxVertPositionOfWord(maxVertWordPosition);                
                board.getCell(maxVertWordPosition).setMinVertPositionOfWord(minVertWordPosition);                 
                currentPlayerWords.add(newWord.toString());              
                newWordScore = newWordScore * wordMultiplier;
                newTotalScore += newWordScore;                                
                //} 
            }
        }
        System.out.println("New Word Error : " + newWordsError);        
        System.out.println("Min Position of Board for Vertical Word is : " + minVertWordPosition);
        System.out.println("Max Position of Board for Vertical Word is : " + maxVertWordPosition);     
        System.out.println("Vertical Word for Min Position is : " + board.getCell(minVertWordPosition).getVerticalWord());
        System.out.println("Vertical Word for Max Position is : " + board.getCell(maxVertWordPosition).getVerticalWord());         
    }    
    
    public boolean scrabbleAlgoHorWordMinPosHelper(boolean hWord, int minPosition) {
        System.out.println("Entering Horizontal Word Min Position Helper");        
        boolean hWordExists = hWord; //false;        
        if ((minPosition % 15) > 0) {        
            if (board.containsHorizontalWord(minPosition - 1)) {              
                minHorWordPosition = board.getCell(minPosition - 1).getMinHorPositionOfWord();                  
                newWord.append(board.getCell(minHorWordPosition).getHorizontalWord());
                newLettersOnlyScore += board.getCell(minHorWordPosition).getHorizontalScore();
                newWordScore += board.getCell(minHorWordPosition).getHorizontalScore();
                
                System.out.println("hWordExists at minPositionBoard - 1");                      
                System.out.println("minHorWordPosition :" + minHorWordPosition); 
                System.out.println("newWord :" + newWord);                     
                System.out.println("newLettersOnlyScore :" + newLettersOnlyScore);   
                System.out.println("newWordScore :" + newWordScore);                     
                hWordExists = true;                    
            } else if (board.getCell(minPosition - 1).getPositionTaken()) {                
                minHorWordPosition = minPosition - 1;                
                newWord.append(board.getCell(minHorWordPosition).getCellValues());
                newLettersOnlyScore += board.getCell(minHorWordPosition).getHorizontalScore();
                newWordScore += board.getCell(minHorWordPosition).getHorizontalScore();                    
                
                System.out.println("hLetterExists at minPositionBoard - 1"); 
                System.out.println("minHorWordPosition :" + minHorWordPosition);                     
                System.out.println("newWord :" + newWord);    
                System.out.println("newLettersOnlyScore :" + newLettersOnlyScore);   
                System.out.println("newWordScore :" + newWordScore);                    
                hWordExists = true;                      
            } else {
                minHorWordPosition = minPosition;                
            }
        } else {
            minHorWordPosition = minPosition;            
        }      
        
        return hWordExists;
    }
    
    public void scrabbleAlgoHorWordMaxPosHelper(boolean hWord, int maxPosition) {
        System.out.println("Entering Horizontal Word Max Position Helper");        
        boolean hWordExists = hWord;
        if ((maxPosition % 15) < 14) {        
            if (board.containsHorizontalWord(maxPosition + 1)) {            
                maxHorWordPosition = board.getCell(maxPosition + 1).getMaxHorPositionOfWord();                 
                newWord.append(board.getCell(maxHorWordPosition).getHorizontalWord());
                newLettersOnlyScore += board.getCell(maxHorWordPosition).getHorizontalScore();
                newWordScore += board.getCell(maxHorWordPosition).getHorizontalScore();  
                
                System.out.println("hWordExists at maxPositionOfBoard + 1");                      
                System.out.println("maxHorWordPosition :" + maxHorWordPosition); 
                System.out.println("newWord :" + newWord);                     
                System.out.println("newLettersOnlyScore :" + newLettersOnlyScore);   
                System.out.println("newWordScore :" + newWordScore);                      
                hWordExists = true;                    
            } else if (board.getCell(maxPosition + 1).getPositionTaken()) {                
                maxHorWordPosition = maxPosition + 1;                    
                newWord.append(board.getCell(maxHorWordPosition).getCellValues()); 
                newLettersOnlyScore += board.getCell(maxHorWordPosition).getHorizontalScore();
                newWordScore += board.getCell(maxHorWordPosition).getHorizontalScore();                     
                
                System.out.println("hLetterExists at maxPositionOfBoard + 1"); 
                System.out.println("maxHorWordPosition :" + maxHorWordPosition);                     
                System.out.println("newWord :" + newWord);   
                System.out.println("newLettersOnlyScore :" + newLettersOnlyScore);   
                System.out.println("newWordScore :" + newWordScore);                     
                hWordExists = true;                    
            } else {
                maxHorWordPosition = maxPosition;                
            }
        } else {
            maxHorWordPosition = maxPosition;            
        }                                         

        if (hWordExists) {        
            String checkWordHor = newWord.toString();
            newWordsError = !checkWordExists(checkWordHor); // if error, set newWordsError = true;        
            if (!newWordsError) {
                //if (hWordExists) {
                board.getCell(minHorWordPosition).setHorizontalWord(newWord.toString());                
                board.getCell(maxHorWordPosition).setHorizontalWord(newWord.toString());                
                board.getCell(minHorWordPosition).setHorizontalScore(newLettersOnlyScore);                
                board.getCell(maxHorWordPosition).setHorizontalScore(newLettersOnlyScore);
                board.getCell(minHorWordPosition).setMaxHorPositionOfWord(maxHorWordPosition);                
                board.getCell(maxHorWordPosition).setMinHorPositionOfWord(minHorWordPosition);                 
                currentPlayerWords.add(newWord.toString());                
                newWordScore = newWordScore * wordMultiplier;
                newTotalScore += newWordScore;
                //}
            }
        }
        System.out.println("New Word Error : " + newWordsError);          
        System.out.println("Min Horizontal Position of Board for Horizontal Word is : " + minHorWordPosition);
        System.out.println("Max Horizontal Position of Board for Horizontal Word is : " + maxHorWordPosition);     
        System.out.println("Horizontal Word for Min Position is : " + board.getCell(minHorWordPosition).getHorizontalWord());
        System.out.println("Horizontal Word for Max Position is : " + board.getCell(maxHorWordPosition).getHorizontalWord());             
    }        

    /**
     * This method refills the LetterTray of the current player playing the game of
     * 'Scrabble'
     */
    public void refillEvent() {
        // String status; //dummy string. May or may not use it
        // ArrayList<Letter> trayOfLetters = currentPlayer.getCurrentLetterTray();
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
     * This method displays in the GUI: the Board, score and the status of the
     * Player with the current Turn.
     */
    public ArrayList<Cell[]> getDisplay() {
        // CurrentPlayerWordScore = 0; //dummyWordScore
        // CurrentPlayerWords.add("dummyWord1"); //This could be an ArrayList<String>
        // CurrentPlayerTotalScore = 0; //dummyTotalScore
        display = board.getCurrentBoard();
        // gameResult();
        return display;
    }

    public void changePlayer() {
        // boolean newPlayersTurn = changePlayer;
        if (playerIndex == (players.size() - 1)) {
            playerIndex = 0;
        } else {
            playerIndex += 1;
        }
        currentPlayer = players.get(playerIndex);
    }

    public boolean checkError(ArrayList<String> wordList) {
        boolean newWordsError = false;
        boolean tempError;
        boolean dictFileInComplete = true;

        // To do: check new words here
        // while (!newWordsError || dictFileChecked) {
        // tempError = true;
        boolean wordInDict;
        for (int i = 0; i < wordList.size(); i++) {
            wordInDict = false;
            System.out.println(wordList.get(i));
            // Check if word is in dictionary
            // for (int j = 0; j < dictionaryWords.size(); j ++) {
            int countDict = 0;
            // while(dictFileInComplete || !tempError) {
            while (dictFileInComplete && !wordInDict) {
                if (wordList.get(i).equalsIgnoreCase(dictionaryWords.get(countDict))) {
                    // tempError = false;
                    wordInDict = true;
                }
                countDict++;
                if (countDict >= dictionaryWords.size()) {
                    dictFileInComplete = false;
                }
            }
            // }
            if (!wordInDict) {
                // if (tempError) {
                newWordsError = true; // So atleast one word from word list is NOT in the dictionary, and hence an
                                      // error
            }
        }
        // dictFileChecked = true;
        return newWordsError;
    }

    public boolean checkWordExists(String wordCheck) {
        boolean wordExists = false;
        ArrayList<String> singleWord = new ArrayList<String>();
        singleWord.add(wordCheck);
        wordExists = !checkError(singleWord);

        // If we want the Current Player's score docked a few points each time for
        // checking the dictionary, use this
        int dockedPoints = 0; // 2; Currently not docking points
        currentPlayer.setTotalScore(currentPlayer.getTotalScore() - dockedPoints);

        return wordExists;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * This method sends out the scores of each Player from the game to the GUI.
     * 
     * @return
     */
    private void gameResult() {
        allPlayerScores = scoreKeeper.getPlayerScores();
    }

    /**
     * This method determines whether the user will play again.
     */
    private void playAgain() {
        String inputFromGUI = "Play Again"; // dummy String. Needs input from GUI
        if (inputFromGUI.toLowerCase().equals("play again")) {
            playing = true;
        }
    }

    /**
     * This method determines whether the user will restart play. Scrabble is a long
     * game, and is the Players want to restart play, this method can be called to
     * restart the Scrabble Game
     */
    private void restartPlay() {
        String inputFromGUI = "Restart Play"; // dummy String. Needs input from GUI
        if (inputFromGUI.toLowerCase().equals("restart play")) {
            playing = true;
        }
    }

    public boolean getBagOfLettersIsEmpty() {
        return bagOfLetters.isEmpty();
    }

    public BagOfLetters getBagOfLetters() {
        return this.bagOfLetters;
    }

    public Board getBoard() {
        return this.board;
    }

    public void setEndGame(boolean gameStatus) {
        endGame = gameStatus;
    }

    public boolean getGameStatus() {
        return endGame;
    }

    public boolean getNewWordsError() {
        return newWordsError;
    }
}