package scrabble;
/**
 * This is the Cell class, holding all positions and letters from a row
 * The variables define the value of each Cell object 
 * @author CIT-591 Team-35 Project
 *
 */
public class Cell {
    private int position; 
    //private int cellLetterValue;
    //private int cellWordValue;  
    private String posValue;
    private Letter letter;
    private boolean positionTaken;
    private String verticalWord;
    private int verticalScore;
    private String horizontalWord;
    private int horizontalScore;
    private int minHorPositionOfWord;
    private int maxHorPositionOfWord; 
    private int minVertPositionOfWord;
    private int maxVertPositionOfWord;     
    
    /**
     * Constructor
     * @param position
     */
    public Cell(int position, String cellValue) {    
    //public Cell(int position, int letterValue, int wordValue) {
        this.position = position;      
        //cellLetterValue = letterValue;
        //cellWordValue = wordValue;  
        posValue = cellValue;
        positionTaken = false;
        verticalScore = 0;
        horizontalScore = 0;
        /*minHorPositionOfWord = position;
        maxHorPositionOfWord = position; 
        minVertPositionOfWord = position;
        maxVertPositionOfWord = position;  */       
    }
       
    /**
     * Getter method for position
     * @return
     */
    public int getPosition() {
        return position;
    }
    
    /**
     * Getter method for card.
     * @return
     */
    public Letter getLetter() {
        return letter;
    }
    
    /**
     * Setter method for card.
     */
    public void setLetter(Letter L) {
        letter = L;      
        //verticalWord = L.getLetterName(); 
        //horizontalWord = L.getLetterName();    
        verticalScore = L.convertNameToValue(L.getLetterName());
        horizontalScore = L.convertNameToValue(L.getLetterName());;        
    }
    
    /**
     * Getter method for positionTaken.
     */
    public boolean getPositionTaken() {
        return positionTaken;        
    }     
    
    /**
     * Setter method for positionTaken.
     */
    public void setPositionTaken(boolean posTaken) {
        positionTaken = posTaken;        
    }  
    
    /**
     * Getter method for verticalWord.
     */
    public String getVerticalWord() {
        return verticalWord;        
    }     
    
    /**
     * Setter method for verticalWord.
     */
    public void setVerticalWord(String vertWord) {
        verticalWord = vertWord;        
    }  
    
    /**
     * Getter method for horizontalWord.
     */
    public String getHorizontalWord() {
        return horizontalWord;        
    }     
    
    /**
     * Setter method for horizontalWord.
     */
    public void setHorizontalWord(String horWord) {
        horizontalWord = horWord;        
    }      
    
    /**
     * Getter method for verticalScore.
     */
    public int getVerticalScore() {
        return verticalScore;        
    }     
    
    /**
     * Setter method for verticalScore.
     */
    public void setVerticalScore(int vertScore) {
        verticalScore = vertScore;        
    }  
    
    /**
     * Getter method for horizontalScore.
     */
    public int getHorizontalScore() {
        return horizontalScore;        
    }     
    
    /**
     * Setter method for horizontalScore.
     */
    public void setHorizontalScore(int horScore) {
        horizontalScore = horScore;        
    }  
    
    /**
     * Returns a letter if there is one, otherwise returns the position
     * @return
     */
    public String getCellValues() {
        return posValue;
    }
    
    /**
     * Sets the posValue to the assigned letter 
     * @return
     */
    public void setCellValues() {
        if (positionTaken) {
            posValue = letter.getLetterName();
        }
    }   
    
    /**
     * Getter method for min Position of horizontal word
     */
    public int getMinHorPositionOfWord() {
        return minHorPositionOfWord;        
    }     
    
    /**
     * Setter method for min Position of horizontal word.
     */
    public void setMinHorPositionOfWord(int minHorPosOfWord) {
        minHorPositionOfWord = minHorPosOfWord;        
    } 
    
    /**
     * Getter method for max Position of horizontal word
     */
    public int getMaxHorPositionOfWord() {
        return maxHorPositionOfWord;        
    }     
    
    /**
     * Setter method for max Position of horizontal word.
     */
    public void setMaxHorPositionOfWord(int maxHorPosOfWord) {
        maxHorPositionOfWord = maxHorPosOfWord;        
    }   
    
    /**
     * Getter method for min Position of Vertical word
     */
    public int getMinVertPositionOfWord() {
        return minVertPositionOfWord;        
    }     
    
    /**
     * Setter method for min Position of Vertical word.
     */
    public void setMinVertPositionOfWord(int minVertPosOfWord) {
        minVertPositionOfWord = minVertPosOfWord;        
    } 
    
    /**
     * Getter method for max Position of Vertical word
     */
    public int getMaxVertPositionOfWord() {
        return maxVertPositionOfWord;        
    }     
    
    /**
     * Setter method for max Position of Vertical word.
     */
    public void setMaxVertPositionOfWord(int maxVertPosOfWord) {
        maxVertPositionOfWord = maxVertPosOfWord;        
    }     
}