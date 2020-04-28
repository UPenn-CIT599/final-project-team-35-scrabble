package scrabble;

import java.util.*;

/**
 * This is the ScrabbleGame class, with scrabbleAlgo() which has the main Scrabble Algorithm
 * 
 * @author CIT-591 Team-35 Project
 *
 */
public class ScrabbleGame {
    
    private int startPosition; // Start position for the Game. The first word must include this position in the Scrabble Board

    // Variables for Dictionary reference, for word check
    private Dictionary dictionary;
    private ArrayList<String> dictionaryWords;

    // Variables for info on all Players: names, wordSelections
    private ArrayList<Player> players;

    //private boolean singlePlayerGame;
    private Player currentPlayer;
    private int playerIndex;

    // Variables for Board status
    private Board board;
    private ArrayList<Cell[]> display;
    //private ArrayList<Cell[]> testNewBoard;
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
    StringBuilder newWord;
    private int newLettersOnlyScore;
    private int newWordScore;
    private int newTotalScore;
    private int wordMultiplier;
    private int letterMultiplier;
    private BagOfLetters bagOfLetters;
    public ScoreKeeper scoreKeeper;
    private ArrayList<Letter> tempLetterTray;
    private boolean endGame;
    private ArrayList<String> currentPlayerWords;
    private boolean debugPrint;

    /**
     * ScrabbleGame Constructor
     * @param allPlayerNames : This is the list of the names of the Players playing this Game
     */
    public ScrabbleGame(ArrayList<String> allPlayerNames) {
        createDictionary(); // Creating Dictionary for exhaustive word reference for this Game
        bagOfLetters = new BagOfLetters(); // Initializing the Bag of Letters to shuffle and pick
        board = new Board(); // Initializing Board, and sending status of board to GUI
        initializePlayers(allPlayerNames); // Initializing Players playing this game
        playerIndex = 0; // Needed to keep track of Players playing the game
        currentPlayer = players.get(playerIndex); // Gives the current player in this game.       
        scoreKeeper = new ScoreKeeper(); // Initializing Score Keeper
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
        //allowedErrorChances = 2;
        tempLetterTray = new ArrayList<Letter>();
        newLettersOnlyScore = 0;
        newWordScore = 0;
        endGame = false;
        debugPrint = false;
    }

    /**
     * method to create the Dictionary object and its methods
     */
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

    /**
     * method to instantiate all the Player objects
     * @param playerNames : This is the list of the names of the Players playing this Game
     */
    public void initializePlayers(ArrayList<String> playerNames) {
        // Initializing all the players for this game.
        // Initial draft allows 2 Players, but final version may allow upto 4 Players.
        // Show error if more than 4 Players!!!
        players = new ArrayList<Player>();

        for (int i = 0; i < playerNames.size(); i++) {
            Player player = new Player(playerNames.get(i));
            players.add(player);
            player.setCurrentLetterTray(bagOfLetters.shuffleAndPickLetters(7));
        }
    }

    /**
     * This method playEvent() is called when the Player hits submit in the GUI. This method then calls scrabbleAlgo which handles the 
     * algorithm associated with this Scrabble Game
     * @param boardPositions : provides positions in the Board that corresponds to the Letters added in the Board
     * @param boardLetters : provides Letters in the Board that corresponds to the Positions ArrayList
     * @return
     */
    public boolean playEvent(ArrayList<Integer> boardPositions, ArrayList<String> boardLetters) {

        newWordsError = false;
        newBoardPositionCells.clear();
        newBoardLetters.clear();
        minPositionOfBoard = 999;
        maxPositionOfBoard = -1;
        singleLetter = false;
        verticalLetters = false;
        horizontalLetters = false;
        debugPrint = true;

        //Using HashMap for sorting the provided positions and letters
        HashMap<Integer, String> sortingLetters = new HashMap<Integer, String>();
        for (int i = 0; i< boardPositions.size(); i++) {
            sortingLetters.put(boardPositions.get(i), boardLetters.get(i));  
            if (debugPrint) {
                System.out.println("Before Sorting Board Letters");      
                System.out.print(boardPositions.get(i) + " ");            
                System.out.println(boardLetters.get(i) + " "); 
            }           
        }
      
        //Sorting the positions and letters
        Collections.sort(boardPositions);
        boardLetters.clear();
        for (int i = 0; i< boardPositions.size(); i++) {
            boardLetters.add(sortingLetters.get(boardPositions.get(i)));  
            if (debugPrint) {
                System.out.println("Sorting sent Board Letters");
            }
        }        
        
        if (debugPrint) {
            for (int i = 0; i< boardPositions.size(); i++) { 
                System.out.print(boardPositions.get(i) + " ");            
                System.out.println(boardLetters.get(i) + " ");           
            }            
        }           

        // Finding if the letters are vertical or horizontal or a single letter
        // NOTE: Having both Vertical and Horizontal Letters is invalid in this Scrabble Game
        if (boardPositions.size() < 2) {
            singleLetter = true;
        } else if (((boardPositions.get(1) - boardPositions.get(0)) % 15) == 0) {
            verticalLetters = true;
            //Check if all letters are all vertical letters, else invalid (not supported by this game)
            for (int i = 0; i < (boardPositions.size() - 1); i++) { 
                if (debugPrint) {
                    System.out.println("Board Position (i + 1) for Vert Letter : " + boardPositions.get(i + 1));     
                    System.out.println("Board Position (i) for Vert Letter : " + boardPositions.get(i));                     
                }                
                if (((boardPositions.get(i + 1) - boardPositions.get(i)) % 15) != 0) {  
                    if (debugPrint) {
                        System.out.println("Horizontal Letters included with Vertical Letters : Invalid");                           
                    }                
                    newWordsError = true; 
                }
            }
        } else {
            horizontalLetters = true;
            //Check if all letters are all horizontal letters, else invalid (not supported by this game)            
            for (int i = 0; i < (boardPositions.size() - 1); i++) { 
                if (debugPrint) {
                    System.out.println("Board Position (i + 1) for Hor Letter : " + boardPositions.get(i + 1));     
                    System.out.println("Board Position (i) for Hor Letter : " + boardPositions.get(i));   
                    System.out.println("Board Position (i + 1)/15 for Hor Letter : " + (boardPositions.get(i + 1)/15));     
                    System.out.println("Board Position (i)/15 for Hor Letter : " + (boardPositions.get(i)/15));                     
                }               
                if ((boardPositions.get(i + 1)/15) != (boardPositions.get(i)/15)) { 
                    if (debugPrint) {
                        System.out.println("Vertical Letters included with Horizontal Letters : Invalid");                         
                    }                  
                    newWordsError = true; 
                }                
            }            
        }

        // Get word selections from current player, and checkError using the Scrabble Algorithm "scrabbleAlgo()"
        //Check is the very first word on the Board includes the "startPosition" Cell, which is mandatory for this game!
        if (!newWordsError) {
            if (!board.getCell(startPosition).getPositionTaken()) {
                for (int i = 0; i < boardPositions.size(); i++) {
                    if (boardPositions.get(i) == startPosition) {
                        firstWordTaken = true;
                    }
                }
            }           
        }

        //If the first word is already placed in the startPosition, the remaining game goes forward using the scrabbleAlgo() method 
        if (firstWordTaken && (!newWordsError)) {
            // Converting to Cell, and getLetters
            for (int i = 0; i < boardPositions.size(); i++) {
                Letter tempLetter = new Letter(boardLetters.get(i));
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
            
            if (debugPrint) {
                System.out.println("Calling Scrabble Algorithm : ");                
            }
            
            //Calling the Scrabble Algorithm method "scrabbleAlgo()"        
            scrabbleAlgo(boardPositions, boardLetters); 
            
            // Check words against dictionary for errorCheck
            if (!newWordsError) {
                if (debugPrint) {
                    for (int j = 0; j < currentPlayerWords.size(); j++) {
                        System.out.println("Word-" + j + " : " + currentPlayerWords.get(j));
                    }                     
                }             
            } else {
                for (int i = 0; i < boardPositions.size(); i++) {
                    if (boardPositions.get(i) == startPosition) {
                        firstWordTaken = false;
                    }
                }                
            }
        } else {
            newWordsError = true;
        }

        if (newWordsError) { 
            // Reset all positions Taken, since the words used were not in the dictionary, and hence invalid!
            for (int i = 0; i < boardPositions.size(); i++) {
                board.getCell(boardPositions.get(i)).setPositionTaken(false);
                board.getCell(boardPositions.get(i)).setVerticalScore(0);
                board.getCell(boardPositions.get(i)).setHorizontalScore(0);
            }
        } 
        else {
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
        }
        if (debugPrint) {
            System.out.println("New Word Error returned from playEvent : " + newWordsError);             
        } 
        return newWordsError;
    }    

    /**
     * private "scrabbleAlgo()" method which contains the algorithm used to handle the Scrabble Game
     * @param sorted positions : sent by the playEvent() method
     * @param sorted letters : sent by the playEvent() method
     */
    private void scrabbleAlgo(ArrayList<Integer> positions, ArrayList<String> letters) {
        currentPlayerWords.clear();
        ArrayList<Integer> tempPositions = new ArrayList<Integer>();
        ArrayList<String> tempLetters = new ArrayList<String>();
        for (int i = 0; i < positions.size(); i++) {
            tempPositions.add(positions.get(i));
        }
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
            if (debugPrint) {
                System.out.println("Min Position of Board for Single Letter is : " + minPositionOfBoard);
                System.out.println("Max Position of Board for Single Letter is : " + maxPositionOfBoard);     
                System.out.println("Vertical Word for Min Position is : " + board.getCell(minPositionOfBoard - 15).getVerticalWord());
                System.out.println("Vertical Word for Max Position is : " + board.getCell(maxPositionOfBoard + 15).getVerticalWord());
                System.out.println("Horizontal Word for Min Position is : " + board.getCell(minPositionOfBoard - 1).getHorizontalWord());
                System.out.println("Horizontal Word for Max Position is : " + board.getCell(maxPositionOfBoard + 1).getHorizontalWord());                
            }
 
            boolean vertWordExists = false; 
            
            //Calling the "scrabbleAlgo-helper method" for vertical Letters, when at the beginning of the vertical word            
            vertWordExists = scrabbleAlgoVertWordMinPosHelper(vertWordExists, minPositionOfBoard); 
            int len = 1 + ((maxPositionOfBoard - minPositionOfBoard) / 15);
            int position = (positions.get(0) - 15);

            for (int i = 0; i < len; i++) {
                if ((position + 15) == tempPositions.get(i)) {
                    newWord.append(tempLetters.get(i));
                    String tempCellValue = board.getCell(tempPositions.get(i)).getCellValues();
                    
                    //Calling helper method to find the multiplier (Word or Letter) to be used based on the position of the letter                    
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
            
            vertWordExists = true; //Always true at the end of the word, when it has already been decided to have vertical letters
            
            //Calling the "scrabbleAlgo-helper method" for vertical Letters, when at the end of the vertical word
            scrabbleAlgoVertWordMaxPosHelper(vertWordExists, maxPositionOfBoard);

            ///////////////////////////////////////////////////////////////////////////////////////

            /////////// Now finding the remaining horizontal words, that apply to this Vertical word ///////////     
            if (!newWordsError) {
                len = positions.size(); // resizing "len" to only new letters added in board
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

                        //Calling the "scrabbleAlgo-helper method" for horizontal Letters, when at the beginning of the horizontal word                        
                        horWordExists = scrabbleAlgoHorWordMinPosHelper(horWordExists, positions.get(i));       

                        newWord.append(letters.get(i));
                        String tempCellValue = board.getCell(positions.get(i)).getCellValues();
                        
                        //Calling helper method to find the multiplier (Word or Letter) to be used based on the position of the letter                        
                        scrabbleAlgoMultiplierHelper(tempCellValue);
                        
                        Letter tempLetter = new Letter(letters.get(i));
                        newLettersOnlyScore += tempLetter.convertNameToValue(letters.get(i)); 
                        newWordScore += letterMultiplier * tempLetter.convertNameToValue(letters.get(i));

                        //Calling the "scrabbleAlgo-helper method" for horizontal Letters, when at the end of the horizontal word                         
                        scrabbleAlgoHorWordMaxPosHelper(horWordExists, positions.get(i)); 
                    }
                }                
            }
        } else if (horizontalLetters) {
            
            boolean horWordExists = false;
            
            //Calling the "scrabbleAlgo-helper method" for horizontal Letters, when at the beginning of the horizontal word             
            horWordExists = scrabbleAlgoHorWordMinPosHelper(horWordExists, minPositionOfBoard);
            
            int len = 1 + (maxPositionOfBoard - minPositionOfBoard);
            int position = (positions.get(0) - 1);
     
            for (int i = 0; i < len; i++) {
                if ((position + 1) == tempPositions.get(i)) {
                    newWord.append(tempLetters.get(i));
                    String tempCellValue = board.getCell(tempPositions.get(i)).getCellValues();
                    
                    //Calling helper method to find the multiplier (Word or Letter) to be used based on the position of the letter                    
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
            
            //Calling the "scrabbleAlgo-helper method" for horizontal Letters, when at the end of the horizontal word             
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

                        //Calling the "scrabbleAlgo-helper method" for vertical Letters, when at the beginning of the vertical word                        
                        vertWordExists = scrabbleAlgoVertWordMinPosHelper(vertWordExists, positions.get(i));                  

                        newWord.append(letters.get(i));
                        String tempCellValue = board.getCell(positions.get(i)).getCellValues();
                        
                        //Calling helper method to find the multiplier (Word or Letter) to be used based on the position of the letter                         
                        scrabbleAlgoMultiplierHelper(tempCellValue);
                        
                        Letter tempLetter = new Letter(letters.get(i));
                        newLettersOnlyScore += tempLetter.convertNameToValue(letters.get(i)); 
                        newWordScore += letterMultiplier * tempLetter.convertNameToValue(letters.get(i));

                        //Calling the "scrabbleAlgo-helper method" for vertical Letters, when at the end of the vertical word                        
                        scrabbleAlgoVertWordMaxPosHelper(vertWordExists, positions.get(i));  
                    }
                }
            }
        } else if (singleLetter) {
            /////////// Find Vertical Word ///////////            
            boolean vWordExists = false;
            
            if (debugPrint) {
                System.out.println("Min Position of Board for Single Letter is : " + minPositionOfBoard);
                System.out.println("Max Position of Board for Single Letter is : " + maxPositionOfBoard);     
                System.out.println("Vertical Word for Min Position is : " + board.getCell(minPositionOfBoard - 15).getVerticalWord());
                System.out.println("Vertical Word for Max Position is : " + board.getCell(maxPositionOfBoard + 15).getVerticalWord());
                System.out.println("Horizontal Word for Min Position is : " + board.getCell(minPositionOfBoard - 1).getHorizontalWord());
                System.out.println("Horizontal Word for Max Position is : " + board.getCell(maxPositionOfBoard + 1).getHorizontalWord());               
            }
             
            //Calling the "scrabbleAlgo-helper method" for vertical Letters, when at the beginning of the vertical word             
            vWordExists = scrabbleAlgoVertWordMinPosHelper(vWordExists, minPositionOfBoard);             
            
            newWord.append(tempLetters.get(0));
            String tempCellValue = board.getCell(tempPositions.get(0)).getCellValues();
            wordMultiplier = 1;
            letterMultiplier = 1;   
            
            //Calling helper method to find the multiplier (Word or Letter) to be used based on the position of the letter             
            scrabbleAlgoMultiplierHelper(tempCellValue);
            
            Letter tempLetter = new Letter(tempLetters.get(0));
            // The LettersOnlyScore does not include the letter or word multiplier, as it is used for later reference           
            newLettersOnlyScore += tempLetter.convertNameToValue(tempLetters.get(0)); 
            newWordScore += letterMultiplier * tempLetter.convertNameToValue(tempLetters.get(0));
           
            //Calling the "scrabbleAlgo-helper method" for vertical Letters, when at the end of the vertical word             
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

                //Calling the "scrabbleAlgo-helper method" for horizontal Letters, when at the beginning of the horizontal word                 
                hWordExists = scrabbleAlgoHorWordMinPosHelper(hWordExists, minPositionOfBoard);             
                newWord.append(tempLetters.get(0));
                
                //Calling helper method to find the multiplier (Word or Letter) to be used based on the position of the letter                
                scrabbleAlgoMultiplierHelper(tempCellValue);            

                // The LettersOnlyScore does not include the letter or word multiplier
                newLettersOnlyScore += tempLetter.convertNameToValue(tempLetters.get(0));                                                                                       
                newWordScore += letterMultiplier * tempLetter.convertNameToValue(tempLetters.get(0));

                //Calling the "scrabbleAlgo-helper method" for horizontal Letters, when at the end of the horizontal word                 
                scrabbleAlgoHorWordMaxPosHelper(hWordExists, maxPositionOfBoard);   
            }
        }
        if (debugPrint) {
            System.out.println("New Word Error returned from scrabbleAlgo : " + newWordsError);  
        }            
    }
    
    /**
     * This is one of 5 helper methods for "scrabbleAlgo" to implement the DRY principle in scrabbleAlgo. 
     * This method finds the multiplier required for the word Scores
     * @param cellValue : This gives the multiplier required for the word Scores 
     */
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

    /**
     * This is one of 5 helper methods for "scrabbleAlgo" to implement the DRY principle in scrabbleAlgo. 
     * This method finds all the words, letterOnly scores (for later reference), and the word scores, 
     * based on the letters for Vertical min positions chosen by the Player
     * @param vWord (boolean) : scrabbleAlgo() tells the helper method whether atleast a partial/full vertical word exists
     * @param minPosition (int) : scrabbleAlgo() sends the current min Position for this word, which may change during the course of this algorithm
     */
    public boolean scrabbleAlgoVertWordMinPosHelper(boolean vWord, int minPosition) { 
        if (debugPrint) {
            System.out.println("Entering Vertical Word Min Position Helper");
        }
        boolean vWordExists = vWord;              
        if (minPosition > 14) {                 
            if (board.containsVerticalWord(minPosition - 15)) {                   
                System.out.println("vWordExists at minPositionBoard - 15");                     
                minVertWordPosition = board.getCell(minPosition - 15).getMinVertPositionOfWord();                 
                newWord.append(board.getCell(minVertWordPosition).getVerticalWord());                    
                newLettersOnlyScore += board.getCell(minVertWordPosition).getVerticalScore();
                newWordScore += board.getCell(minVertWordPosition).getVerticalScore();
                
                if (debugPrint) {
                    System.out.println("vWordExists at minPositionBoard - 15");                      
                    System.out.println("minVertWordPosition :" + minVertWordPosition); 
                    System.out.println("newWord :" + newWord);                     
                    System.out.println("newLettersOnlyScore :" + newLettersOnlyScore);   
                    System.out.println("newWordScore :" + newWordScore);                    
                }                    
                vWordExists = true;             
            } else if (board.getCell(minPosition - 15).getPositionTaken()) {
                minVertWordPosition = minPosition - 15;                
                newWord.append(board.getCell(minVertWordPosition).getCellValues());
                newLettersOnlyScore += board.getCell(minVertWordPosition).getVerticalScore();
                newWordScore += board.getCell(minVertWordPosition).getVerticalScore();                    
                
                if (debugPrint) {
                    System.out.println("vLetterExists at minPositionBoard - 15");  
                    System.out.println("minVertWordPosition :" + minVertWordPosition);                 
                    System.out.println("newWord :" + newWord); 
                    System.out.println("newLettersOnlyScore :" + newLettersOnlyScore);   
                    System.out.println("newWordScore :" + newWordScore);                      
                }                  
                vWordExists = true;                    
            } else {
                minVertWordPosition = minPosition;                
            }
        } else {
            minVertWordPosition = minPosition;            
        }        
        return vWordExists;
    }
   
    /**
     * This is one of 5 helper methods for "scrabbleAlgo" to implement the DRY principle in scrabbleAlgo. 
     * This method finds all the words, letterOnly scores (for later reference), and the word scores, 
     * based on the letters for Vertical max positions chosen by the Player
     * @param vWord (boolean) : scrabbleAlgo() tells the helper method whether atleast a partial/full vertical word exists
     * @param maxPosition (int) : scrabbleAlgo() sends the current max Position for this word, which may change during the course of this algorithm
     */
    public void scrabbleAlgoVertWordMaxPosHelper(boolean vWord, int maxPosition) { 
        if (debugPrint) {
            System.out.println("Entering Vertical Word Max Position Helper");    
        }    
        boolean vWordExists = vWord;           
        if (maxPosition < 210) { //(210 + 1)) {        
            if (board.containsVerticalWord(maxPosition + 15)) {                               
                maxVertWordPosition = board.getCell(maxPosition + 15).getMaxVertPositionOfWord();                    
                newWord.append(board.getCell(maxVertWordPosition).getVerticalWord());                    
                newLettersOnlyScore += board.getCell(maxVertWordPosition).getVerticalScore();
                newWordScore += board.getCell(maxVertWordPosition).getVerticalScore(); 
                
                if (debugPrint) {
                    System.out.println("vWordExists at maxPositionBoard + 15");                      
                    System.out.println("maxVertWordPosition :" + maxVertWordPosition); 
                    System.out.println("newWord :" + newWord);                     
                    System.out.println("newLettersOnlyScore :" + newLettersOnlyScore);   
                    System.out.println("newWordScore :" + newWordScore); 
                }                      
                vWordExists = true;   
            } else if (board.getCell(maxPosition + 15).getPositionTaken()) {                   
                maxVertWordPosition = maxPosition + 15;                
                newWord.append(board.getCell(maxVertWordPosition).getCellValues()); 
                newLettersOnlyScore += board.getCell(maxVertWordPosition).getVerticalScore();
                newWordScore += board.getCell(maxVertWordPosition).getVerticalScore();                     
                
                if (debugPrint) {
                    System.out.println("vLetterExists at maxPositionBoard + 15");  
                    System.out.println("maxVertWordPosition :" + maxVertWordPosition);                     
                    System.out.println("newWord :" + newWord);  
                    System.out.println("newLettersOnlyScore :" + newLettersOnlyScore);   
                    System.out.println("newWordScore :" + newWordScore); 
                }                   
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
                if (debugPrint) {
                    System.out.println("vWordExists : ");              
                    System.out.println("Setting newLetterOnlyScore : " + newLettersOnlyScore); 
                }                
                board.getCell(minVertWordPosition).setVerticalWord(newWord.toString());                
                board.getCell(maxVertWordPosition).setVerticalWord(newWord.toString());                
                board.getCell(minVertWordPosition).setVerticalScore(newLettersOnlyScore);                
                board.getCell(maxVertWordPosition).setVerticalScore(newLettersOnlyScore);
                board.getCell(minVertWordPosition).setMaxVertPositionOfWord(maxVertWordPosition);                
                board.getCell(maxVertWordPosition).setMinVertPositionOfWord(minVertWordPosition);                 
                currentPlayerWords.add(newWord.toString());              
                newWordScore = newWordScore * wordMultiplier;
                newTotalScore += newWordScore;                                
            }
        }
        if (debugPrint) {
            System.out.println("New Word Error : " + newWordsError);        
            System.out.println("Min Position of Board for Vertical Word is : " + minVertWordPosition);
            System.out.println("Max Position of Board for Vertical Word is : " + maxVertWordPosition);     
            System.out.println("Vertical Word for Min Position is : " + board.getCell(minVertWordPosition).getVerticalWord());
            System.out.println("Vertical Word for Max Position is : " + board.getCell(maxVertWordPosition).getVerticalWord()); 
        }        
    }    
    
    /**
     * This is one of 5 helper methods for "scrabbleAlgo" to implement the DRY principle in scrabbleAlgo. 
     * This method finds all the words, letterOnly scores (for later reference), and the word scores, 
     * based on the letters for Horizontal min positions chosen by the Player
     * @param hWord (boolean) : scrabbleAlgo() tells the helper method whether atleast a partial/full vertical word exists
     * @param minPosition (int) : scrabbleAlgo() sends the current min Position for this word, which may change during the course of this algorithm
     * @return returns true of a Horizontal word exists
     */
    public boolean scrabbleAlgoHorWordMinPosHelper(boolean hWord, int minPosition) {
        if (debugPrint) {
            System.out.println("Entering Horizontal Word Min Position Helper");    
        }    
        boolean hWordExists = hWord; //false;        
        if ((minPosition % 15) > 0) {        
            if (board.containsHorizontalWord(minPosition - 1)) {              
                minHorWordPosition = board.getCell(minPosition - 1).getMinHorPositionOfWord();                  
                newWord.append(board.getCell(minHorWordPosition).getHorizontalWord());
                newLettersOnlyScore += board.getCell(minHorWordPosition).getHorizontalScore();
                newWordScore += board.getCell(minHorWordPosition).getHorizontalScore();
                
                if (debugPrint) {
                    System.out.println("hWordExists at minPositionBoard - 1");                      
                    System.out.println("minHorWordPosition :" + minHorWordPosition); 
                    System.out.println("newWord :" + newWord);                     
                    System.out.println("newLettersOnlyScore :" + newLettersOnlyScore);   
                    System.out.println("newWordScore :" + newWordScore);                    
                }                  
                hWordExists = true;                    
            } else if (board.getCell(minPosition - 1).getPositionTaken()) {                
                minHorWordPosition = minPosition - 1;                
                newWord.append(board.getCell(minHorWordPosition).getCellValues());
                newLettersOnlyScore += board.getCell(minHorWordPosition).getHorizontalScore();
                newWordScore += board.getCell(minHorWordPosition).getHorizontalScore();                    
                
                if (debugPrint) {
                    System.out.println("hLetterExists at minPositionBoard - 1"); 
                    System.out.println("minHorWordPosition :" + minHorWordPosition);                     
                    System.out.println("newWord :" + newWord);    
                    System.out.println("newLettersOnlyScore :" + newLettersOnlyScore);   
                    System.out.println("newWordScore :" + newWordScore);  
                }                  
                hWordExists = true;                      
            } else {
                minHorWordPosition = minPosition;                
            }
        } else {
            minHorWordPosition = minPosition;            
        }      
        
        return hWordExists;
    }
    
    /**
     * This is one of 5 helper methods for "scrabbleAlgo" to implement the DRY principle in scrabbleAlgo. 
     * This method finds all the words, letterOnly scores (for later reference), and the word scores, 
     * based on the letters for Horizontal max positions chosen by the Player
     * @param hWord (boolean) : scrabbleAlgo() tells the helper method whether atleast a partial/full horizontal word exists
     * @param maxPosition (int) : scrabbleAlgo() sends the current max Position for this word, which may change during the course of this algorithm
     */
    public void scrabbleAlgoHorWordMaxPosHelper(boolean hWord, int maxPosition) {
        if (debugPrint) {
            System.out.println("Entering Horizontal Word Max Position Helper"); 
        }       
        boolean hWordExists = hWord;
        if ((maxPosition % 15) < 14) {        
            if (board.containsHorizontalWord(maxPosition + 1)) {            
                maxHorWordPosition = board.getCell(maxPosition + 1).getMaxHorPositionOfWord();                 
                newWord.append(board.getCell(maxHorWordPosition).getHorizontalWord());
                newLettersOnlyScore += board.getCell(maxHorWordPosition).getHorizontalScore();
                newWordScore += board.getCell(maxHorWordPosition).getHorizontalScore();  
                
                if (debugPrint) {
                    System.out.println("hWordExists at maxPositionOfBoard + 1");                      
                    System.out.println("maxHorWordPosition :" + maxHorWordPosition); 
                    System.out.println("newWord :" + newWord);                     
                    System.out.println("newLettersOnlyScore :" + newLettersOnlyScore);   
                    System.out.println("newWordScore :" + newWordScore); 
                }                     
                hWordExists = true;                    
            } else if (board.getCell(maxPosition + 1).getPositionTaken()) {                
                maxHorWordPosition = maxPosition + 1;                    
                newWord.append(board.getCell(maxHorWordPosition).getCellValues()); 
                newLettersOnlyScore += board.getCell(maxHorWordPosition).getHorizontalScore();
                newWordScore += board.getCell(maxHorWordPosition).getHorizontalScore();                     
                
                if (debugPrint) {
                    System.out.println("hLetterExists at maxPositionOfBoard + 1"); 
                    System.out.println("maxHorWordPosition :" + maxHorWordPosition);                     
                    System.out.println("newWord :" + newWord);   
                    System.out.println("newLettersOnlyScore :" + newLettersOnlyScore);   
                    System.out.println("newWordScore :" + newWordScore); 
                }                    
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
                if (debugPrint) {
                    System.out.println("hWordExists : ");              
                    System.out.println("Setting newLetterOnlyScore : " + newLettersOnlyScore); 
                }                    
                board.getCell(minHorWordPosition).setHorizontalWord(newWord.toString());                
                board.getCell(maxHorWordPosition).setHorizontalWord(newWord.toString());                
                board.getCell(minHorWordPosition).setHorizontalScore(newLettersOnlyScore);                
                board.getCell(maxHorWordPosition).setHorizontalScore(newLettersOnlyScore);
                board.getCell(minHorWordPosition).setMaxHorPositionOfWord(maxHorWordPosition);                
                board.getCell(maxHorWordPosition).setMinHorPositionOfWord(minHorWordPosition);                 
                currentPlayerWords.add(newWord.toString());                
                newWordScore = newWordScore * wordMultiplier;
                newTotalScore += newWordScore;
            }
        }
        if (debugPrint) {
            System.out.println("New Word Error : " + newWordsError);          
            System.out.println("Min Horizontal Position of Board for Horizontal Word is : " + minHorWordPosition);
            System.out.println("Max Horizontal Position of Board for Horizontal Word is : " + maxHorWordPosition);     
            System.out.println("Horizontal Word for Min Position is : " + board.getCell(minHorWordPosition).getHorizontalWord());
            System.out.println("Horizontal Word for Max Position is : " + board.getCell(maxHorWordPosition).getHorizontalWord()); 
        }            
    }        

    /**
     * This method refills the LetterTray of the current player playing the game of
     * 'Scrabble'
     */
    public void refillEvent() {
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
     * This method displays in the GUI: the current Board
     * 
     */
    public ArrayList<Cell[]> getDisplay() {
        display = board.getCurrentBoard();
        return display;
    }

    /**
     * This method is used to change the Player, after each turn
     */
    public void changePlayer() {
        if (playerIndex == (players.size() - 1)) {
            playerIndex = 0;
        } else {
            playerIndex += 1;
        }
        currentPlayer = players.get(playerIndex);
    }

    /**
     * This method is used by the scrabbleAlgo(), and the search button in the Scrabble GUI, to check if a word exists in the dictionary
     * @param wordList : List of words to check in the Dictionary
     * @return boolean true of all the words in the list exist in the Dictionary
     */
    public boolean checkError(ArrayList<String> wordList) {
        boolean newWordsError = false;
        boolean dictFileInComplete = true;

        boolean wordInDict;
        for (int i = 0; i < wordList.size(); i++) {
            wordInDict = false;
            System.out.println(wordList.get(i));
            // Check if word is in dictionary
            // for (int j = 0; j < dictionaryWords.size(); j ++) {
            int countDict = 0;
            while (dictFileInComplete && !wordInDict) {
                if (wordList.get(i).equalsIgnoreCase(dictionaryWords.get(countDict))) {
                    wordInDict = true;
                }
                countDict++;
                if (countDict >= dictionaryWords.size()) {
                    dictFileInComplete = false;
                }
            }
            if (!wordInDict) {
                newWordsError = true; // So atleast one word from word list is NOT in the dictionary, and hence an
                                      // error
            }
        }
        return newWordsError;
    }

    /**
     * This method is used by the search button in the Scrabble GUI, to check if a word exists in the dictionary
     * @param wordCheck : List of words to check in the Dictionary
     * @return boolean true of all the words in the list exist in the Dictionary
     */
    public boolean checkWordExists(String wordCheck) {
        boolean wordExists = false;
        ArrayList<String> singleWord = new ArrayList<String>();
        singleWord.add(wordCheck);
        wordExists = !checkError(singleWord);

        // OPTIONAL : If we want the Current Player's score docked a few points each time for
        // checking the dictionary, set dockedPoints to the points that we want docked
        int dockedPoints = 0; // 2; Currently not docking points
        currentPlayer.setTotalScore(currentPlayer.getTotalScore() - dockedPoints);

        return wordExists;
    }

    /**
     * getCurrentPlayer()
     * @return
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * getBagOfLetters() isEmpty()
     * @return
     */
    public boolean getBagOfLettersIsEmpty() {
        return bagOfLetters.isEmpty();
    }

    /**
     * getBagOfLetters
     * @return
     */
    public BagOfLetters getBagOfLetters() {
        return this.bagOfLetters;
    }

    /**
     * getBoard()
     * @return
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * setEndGame()
     * @param gameStatus
     */
    public void setEndGame(boolean gameStatus) {
        endGame = gameStatus;
    }

    /**
     * getGameStatus
     * @return
     */
    public boolean getGameStatus() {
        return endGame;
    }

    /**
     * getNewWordsError : This is the error seen from the scrabbleAlgo()
     * @return true, if there was an error, or no word in the Dictionary
     */
    public boolean getNewWordsError() {
        return newWordsError;
    }
}