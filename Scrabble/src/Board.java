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
        
        if ((i == 0) || (i == 7) || (i == 14)) {
            if (i == 7) {
                /*int[] letterValues = {1, 1, 1, 2, 1, 1, 1, startPositionValue, 1, 1, 1, 2, 1, 1, 1};
                int[] wordValues   = {3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3}; 
                thisLetterValues = letterValues;
                thisWordValues   = wordValues;  */  
                String[] cellValues   = {"TW", "BL", "BL", "DL", "BL", "BL", "BL", "ST", "BL", "BL", "BL", "DL", "BL", "BL","TW"}; 
                thisValues = cellValues;                
            } else {
                /*int[] letterValues = {1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1};
                int[] wordValues   = {3, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 3}; 
                thisLetterValues = letterValues;
                thisWordValues   = wordValues;  */
                String[] cellValues   = {"TW", "BL", "BL", "DL", "BL", "BL", "BL", "TW", "BL", "BL", "BL", "DL", "BL", "BL","TW"}; 
                thisValues = cellValues;                
            }                                                         
        } else if ((i == 1) || (i == 13)) {  
            /*int[] letterValues = {1, 1, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 1, 1};
            int[] wordValues   = {1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1};  
            thisLetterValues = letterValues;
            thisWordValues   = wordValues; */
            String[] cellValues   = {"BL", "DW", "BL", "BL", "BL", "TL", "BL", "BL", "BL", "TL", "BL", "BL", "BL", "DW","BL"}; 
            thisValues = cellValues;            
        } else if ((i == 2) || (i == 12)) {
            /*int[] letterValues = {1, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 1};
            int[] wordValues   = {1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1};  
            thisLetterValues = letterValues;
            thisWordValues   = wordValues;  */
            String[] cellValues   = {"BL", "BL", "DW", "BL", "BL", "BL", "DL", "BL", "DL", "BL", "BL", "BL", "DW", "BL","BL"}; 
            thisValues = cellValues;            
        } else if ((i == 3) || (i == 11)) {
            /*int[] letterValues = {2, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 2};
            int[] wordValues   = {1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1};   
            thisLetterValues = letterValues;
            thisWordValues   = wordValues;  */
            String[] cellValues   = {"DL", "BL", "BL", "DW", "BL", "BL", "BL", "DL", "BL", "BL", "BL", "DW", "BL", "BL","DL"}; 
            thisValues = cellValues;            
        } else if ((i == 4) || (i == 10)) {
            /*int[] letterValues = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
            int[] wordValues   = {1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1};      
            thisLetterValues = letterValues;
            thisWordValues   = wordValues;  */
            String[] cellValues   = {"BL", "BL", "BL", "BL", "DW", "BL", "BL", "BL", "BL", "BL", "DW", "BL", "BL", "BL","BL"}; 
            thisValues = cellValues;            
        } else if ((i == 5) || (i == 9)) {
            /*int[] letterValues = {1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1};
            int[] wordValues   = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};   
            thisLetterValues = letterValues;
            thisWordValues   = wordValues;    */
            String[] cellValues   = {"BL", "TL", "BL", "BL", "BL", "TL", "BL", "BL", "BL", "TL", "BL", "BL", "BL", "TL","BL"}; 
            thisValues = cellValues;            
        } else if ((i == 6) || (i == 8)) {
            /*int[] letterValues = {1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1};
            int[] wordValues   = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};   
            thisLetterValues = letterValues;
            thisWordValues   = wordValues;   */
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
        
        /*
        r0[column]   = new Cell(column, thisLetterValues[0],  thisWordValues[0]); 
        r1[column]   = new Cell(column, thisLetterValues[1],  thisWordValues[1]);      
        r2[column]   = new Cell(column, thisLetterValues[2],  thisWordValues[2]); 
        r3[column]   = new Cell(column, thisLetterValues[3],  thisWordValues[3]);  
        r4[column]   = new Cell(column, thisLetterValues[4],  thisWordValues[4]);         
        r5[column]   = new Cell(column, thisLetterValues[5],  thisWordValues[5]); 
        r6[column]   = new Cell(column, thisLetterValues[6],  thisWordValues[6]);      
        r7[column]   = new Cell(column, thisLetterValues[7],  thisWordValues[7]); 
        r8[column]   = new Cell(column, thisLetterValues[8],  thisWordValues[8]); 
        r9[column]   = new Cell(column, thisLetterValues[9],  thisWordValues[9]); 
        r10[column]  = new Cell(column, thisLetterValues[10], thisWordValues[10]);      
        r11[column]  = new Cell(column, thisLetterValues[11], thisWordValues[11]); 
        r12[column]  = new Cell(column, thisLetterValues[12], thisWordValues[12]);         
        r13[column]  = new Cell(column, thisLetterValues[13], thisWordValues[13]); 
        r14[column]  = new Cell(column, thisLetterValues[14], thisWordValues[14]);    */     
    }
    
    /**
     * Sends out the data for the current Board
     */
    public ArrayList<Cell[]> getCurrentBoard() {
        return scrabbleRows;           
    }    
    
    /**
     * This returns the cell at a given position in the given row
     * @param position
     * @return
     */ /*
    public Cell getCell(int row, int position) {
        if (row < 0 || row > 14 || position < 0 || position > 14) {
            return null;
        } else if (position < 6) {
            return r1[position - 1];            
        } else if (position < 11) {
            return r2[position - 6];
        } else if (position < 14) {
            return r3[position - 11];
        } else if (position < 17) {
            return r4[position - 14];
        } else {
            return discard[position - 17];
        }
    } */
    
    /**
     * Returns true if the position holds a letter
     * @return
     */ /*
    public boolean contains(int position) {
        return getCell(position).getLetter() != null;
    }   */ 
    
    /**
     * Method for updating Current status of Board
     */
    public void currentBoard() {
        
    }
}
