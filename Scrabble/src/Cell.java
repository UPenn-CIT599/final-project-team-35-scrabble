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
    //public String[] getCellValues() {        
        /*
        String[] cellValues = new String[3];
        if (letter != null) {
            String name = letter.getLetterName();
            int valueOfLetter = letter.getLetterScore();
            //int letterValueOfPosition = cellLetterValue;
            //int wordValueOfPosition = cellWordValue;            
            cellValues[0] = name + valueOfLetter;
            if (positionTaken) {
                cellValues[1] = Integer.toString(1) + ""; 
                cellValues[2] = Integer.toString(1) + "";                
            } else {
                cellValues[1] = Integer.toString(cellLetterValue) + ""; 
                cellValues[2] = Integer.toString(cellWordValue) + "";                  
            }                     
        } else {            
            cellValues[0] = "00";            
            cellValues[1] = Integer.toString(cellLetterValue) + ""; 
            cellValues[2] = Integer.toString(cellWordValue) + "";   
        }      
        return cellValues; */
        return posValue;
    }
}