import java.util.*;

/**
 * Class Board
 * @author CIT-591 Team-35 Project
 *
 */
public class Board {
    
    private Cell[] r0;    
    private Cell[] r1;
    private Cell[] r2;
    private Cell[] r3;  
    private Cell[] r4;
    private Cell[] r5;
    private Cell[] r6; 
    private Cell[] r7;
    private Cell[] r8;
    private Cell[] r9;  
    private Cell[] r10;
    private Cell[] r11;
    private Cell[] r12;   
    private Cell[] r13;
    private Cell[] r14;    
    
    private ArrayList<Cell[]> scrabbleRows;   
    
    /**
     * Constructor for Board
     */
    public Board() {
        scrabbleRows = new ArrayList<Cell[]>();        
        
        r0  = new Cell[15];        
        r1  = new Cell[15];
        r2  = new Cell[15];
        r3  = new Cell[15];
        r4  = new Cell[15]; 
        r5  = new Cell[15];
        r6  = new Cell[15];
        r7  = new Cell[15];
        r8  = new Cell[15];    
        r9  = new Cell[15];
        r10 = new Cell[15];
        r11 = new Cell[15];
        r12 = new Cell[15]; 
        r13 = new Cell[15];
        r14 = new Cell[15];        
        
        for (int col=0; col < 15; col++) {   
            boardNewCellHelper(col);            
        }
        
        scrabbleRows.add(r0);
        scrabbleRows.add(r1);    
        scrabbleRows.add(r2);
        scrabbleRows.add(r3);    
        scrabbleRows.add(r4);
        scrabbleRows.add(r5);    
        scrabbleRows.add(r6);
        scrabbleRows.add(r7);  
        scrabbleRows.add(r8);
        scrabbleRows.add(r9);    
        scrabbleRows.add(r10);
        scrabbleRows.add(r11);    
        scrabbleRows.add(r12);
        scrabbleRows.add(r13);    
        scrabbleRows.add(r14);        
    }
   
    /**
     * Helper method for assigning cell letters and values in the board
     * @return
     */ 
    public void boardNewCellHelper(int column) {       
        int startPositionValue = 2;
        int i = column;
        int[] thisLetterValues = new int[15];
        int[] thisWordValues   = new int[15];    
        String[] thisValues = new String[15];
        
        /*
        if ((i == 0) || (i == 7) || (i == 14)) {
            if (i == 7) {
                String[] cellValues   = {"3W", "1L", "1L", "2L", "1L", "1L", "1L", "2W", "1L", "1L", "1L", "2L", "1L", "1L","3W"}; 
                thisValues = cellValues;                
            } else {
                String[] cellValues   = {"3W", "1L", "1L", "2L", "1L", "1L", "1L", "3W", "1L", "1L", "1L", "2L", "1L", "1L","3W"}; 
                thisValues = cellValues;                
            }                                                         
        } else if ((i == 1) || (i == 13)) {  
            String[] cellValues   = {"1L", "2W", "1L", "1L", "1L", "3L", "1L", "1L", "1L", "3L", "1L", "1L", "1L", "2W","1L"}; 
            thisValues = cellValues;            
        } else if ((i == 2) || (i == 12)) {
            String[] cellValues   = {"1L", "1L", "2W", "1L", "1L", "1L", "2L", "1L", "2L", "1L", "1L", "1L", "2W", "1L","1L"}; 
            thisValues = cellValues;            
        } else if ((i == 3) || (i == 11)) {
            String[] cellValues   = {"2L", "1L", "1L", "2W", "1L", "1L", "1L", "2L", "1L", "1L", "1L", "2W", "1L", "1L","2L"}; 
            thisValues = cellValues;            
        } else if ((i == 4) || (i == 10)) {
            String[] cellValues   = {"1L", "1L", "1L", "1L", "2W", "1L", "1L", "1L", "1L", "1L", "2W", "1L", "1L", "1L","1L"}; 
            thisValues = cellValues;            
        } else if ((i == 5) || (i == 9)) {
            String[] cellValues   = {"1L", "3L", "1L", "1L", "1L", "3L", "1L", "1L", "1L", "3L", "1L", "1L", "1L", "3L","1L"}; 
            thisValues = cellValues;            
        } else if ((i == 6) || (i == 8)) {
            String[] cellValues   = {"1L", "1L", "2L", "1L", "1L", "1L", "2L", "1L", "2L", "1L", "1L", "1L", "2L", "1L","1L"}; 
            thisValues = cellValues;            
        }  */
        
        if ((i == 0) || (i == 7) || (i == 14)) {
            if (i == 7) {
                String[] cellValues   = {"TW", "BL", "BL", "DL", "BL", "BL", "BL", "ST", "BL", "BL", "BL", "DL", "BL", "BL","TW"}; 
                thisValues = cellValues;                
            } else {
                String[] cellValues   = {"TW", "BL", "BL", "DL", "BL", "BL", "BL", "TW", "BL", "BL", "BL", "DL", "BL", "BL","TW"}; 
                thisValues = cellValues;                
            }                                                         
        } else if ((i == 1) || (i == 13)) {  
            String[] cellValues   = {"BL", "DW", "BL", "BL", "BL", "TL", "BL", "BL", "BL", "TL", "BL", "BL", "BL", "DW","BL"}; 
            thisValues = cellValues;            
        } else if ((i == 2) || (i == 12)) {
            String[] cellValues   = {"BL", "BL", "DW", "BL", "BL", "BL", "DL", "BL", "DL", "BL", "BL", "BL", "DW", "BL","BL"}; 
            thisValues = cellValues;            
        } else if ((i == 3) || (i == 11)) {
            String[] cellValues   = {"DL", "BL", "BL", "DW", "BL", "BL", "BL", "DL", "BL", "BL", "BL", "DW", "BL", "BL","DL"}; 
            thisValues = cellValues;            
        } else if ((i == 4) || (i == 10)) {
            String[] cellValues   = {"BL", "BL", "BL", "BL", "DW", "BL", "BL", "BL", "BL", "BL", "DW", "BL", "BL", "BL","BL"}; 
            thisValues = cellValues;            
        } else if ((i == 5) || (i == 9)) {
            String[] cellValues   = {"BL", "TL", "BL", "BL", "BL", "TL", "BL", "BL", "BL", "TL", "BL", "BL", "BL", "TL","BL"}; 
            thisValues = cellValues;            
        } else if ((i == 6) || (i == 8)) {
            String[] cellValues   = {"BL", "BL", "DL", "BL", "BL", "BL", "DL", "BL", "DL", "BL", "BL", "BL", "DL", "BL","BL"}; 
            thisValues = cellValues;            
        }   
        
        r0[column]   = new Cell(column, thisValues[0]); 
        r1[column]   = new Cell(column, thisValues[1]);      
        r2[column]   = new Cell(column, thisValues[2]); 
        r3[column]   = new Cell(column, thisValues[3]);  
        r4[column]   = new Cell(column, thisValues[4]);         
        r5[column]   = new Cell(column, thisValues[5]); 
        r6[column]   = new Cell(column, thisValues[6]);      
        r7[column]   = new Cell(column, thisValues[7]); 
        r8[column]   = new Cell(column, thisValues[8]); 
        r9[column]   = new Cell(column, thisValues[9]); 
        r10[column]  = new Cell(column, thisValues[10]);      
        r11[column]  = new Cell(column, thisValues[11]); 
        r12[column]  = new Cell(column, thisValues[12]);         
        r13[column]  = new Cell(column, thisValues[13]); 
        r14[column]  = new Cell(column, thisValues[14]);            
    }
    
    /**
     * Sends out the data for the current Board
     */
    public ArrayList<Cell[]> getCurrentBoard() {
        return scrabbleRows;           
    }    
    
    /**
     * Setter to update the data for the current Board
     */
    public void setCurrentBoard(ArrayList<Cell[]> updatedBoard) {
        scrabbleRows = updatedBoard;           
    }       
    
    /**
     * This returns the cell at a given position in the given row
     * @param position
     * @return
     */ 
    public Cell getCell(int position) {
        if (position >= 0 && position < 15) { 
            return r0[position];
        } else if (position < 30) {
            return r1[position - 15];            
        } else if (position < 45) {
            return r2[position - 30];
        } else if (position < 60) {
            return r3[position - 45];
        } else if (position < 75) {
            return r4[position - 60];
        } else if (position < 90) {
            return r5[position - 75];
        } else if (position < 105) {
            return r6[position - 90];
        } else if (position < 120) {
            return r7[position - 105];
        } else if (position < 135) {
            return r8[position - 120];
        } else if (position < 150) {
            return r9[position - 135];
        } else if (position < 165) {
            return r10[position - 150];
        } else if (position < 180) {
            return r11[position - 165];
        } else if (position < 195) {
            return r12[position - 180];
        } else if (position < 210) {
            return r13[position - 195];
        } else if (position < 225) {
            return r14[position - 210];            
        } else {
            return null;
        }
    } 
    
    /**
     * Returns true if the position holds a vertical Word associated with this Cell
     * @return
     */ 
    public boolean containsLetter(int position) {
        return getCell(position).getLetter() != null;
    }   
    
    /**
     * Returns true if the position holds a vertical word
     * @return
     */ 
    public boolean containsVerticalWord(int position) {
        return getCell(position).getVerticalWord() != null;
    }     
    
    /**
     * Returns true if the position holds a horizontal word
     * @return
     */ 
    public boolean containsHorizontalWord(int position) {
        return getCell(position).getHorizontalWord() != null;
    }     
}
