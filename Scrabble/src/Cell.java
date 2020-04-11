/**
 * This is the Cell class, holding a position and potentially a letter
 * The variables define the value of each Cell object 
 * @author CIT-591 Team-35 Project
 *
 */
public class Cell {
    private int cellPosition; 
    private String cellValue;
    /*
    private boolean start; //if start = true, this is the STAR cell used to start Scrabble Play      
    private boolean doubleLetter;
    private boolean tripleLetter;      
    private boolean quadrupleLetter;     
    private boolean doubleWord;
    private boolean tripleWord;   
    private boolean quadrupleWord;  
    */ 

    //private Letter letter;
    
    /**
     * Constructor
     * @param position
     */
    public Cell(int position, boolean start, boolean doubleLetter, boolean tripleLetter, boolean quadrupleLetter,
            boolean doubleWord, boolean tripleWord, boolean quadrupleWord) {
        /*
        this.cellPosition = position;
        this.start = start;
        this.doubleLetter = doubleLetter;
        this.tripleLetter = tripleLetter;
        this.quadrupleLetter = quadrupleLetter;
        this.doubleWord = doubleWord;
        this.tripleWord = tripleWord;
        this.quadrupleWord = quadrupleWord;   
        */
        
        if (start) {
            cellValue = "StartCell";
        }
        if (doubleLetter) {
            cellValue = "DoubleLetter";
        }
        if (tripleLetter) {
            cellValue = "TripleLetter";
        }
        if (quadrupleLetter) {
            cellValue = "QuadrupleLetter";
        }
        if (doubleWord) {
            cellValue = "DoubleWord";
        }
        if (tripleWord) {
            cellValue = "TripleWord";
        }
        if (quadrupleWord) {
            cellValue = "QuadrupleWord";
        }        
    }
    
    /**
     * Getter method for Cell position.
     * @return
     */
    public int getCellPosition() {
        return cellPosition;
    }
    
    /**
     * Getter method for letter value.
     * @return
     */
    public String getCellValue() {
        return cellValue;
    }     
}