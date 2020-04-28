package scrabble;
/**
 * Class Letter 
 * @author CIT-591 Team-35 Project
 *
 */
public class Letter {
    private String letterName;
    private int letterScore;

    /**
     * Constructor for Letter
     * @param letterName
     */
    public Letter(String letterName) {
        this.letterName = letterName;
        this.letterScore = convertNameToValue(letterName);                     
    }
    
    /**
     * Method convertNameToValue: This method gives the score associated with the Letter. So "A" gives a score of 1, "J" gives 8, and so on.
     * @param s : This is the letter used for the letterScore
     * @return : Returns the value used for letterScore
     */
    public int convertNameToValue(String s) {
        int letterValue = 0;
        if (letterName.toUpperCase().contentEquals("A"))  {
            letterValue = 1;
        } else if (letterName.toUpperCase().equals("B"))  {
            letterValue = 3;
        } else if (letterName.toUpperCase().equals("C"))  {
            letterValue = 3;
        } else if (letterName.toUpperCase().equals("D"))  {
            letterValue = 2;
        } else if (letterName.toUpperCase().equals("E"))  {
            letterValue = 1;
        } else if (letterName.toUpperCase().equals("F"))  {
            letterValue = 4;
        } else if (letterName.toUpperCase().equals("G"))  {
            letterValue = 2;
        } else if (letterName.toUpperCase().equals("H"))  {
            letterValue = 4;
        } else if (letterName.toUpperCase().equals("I"))  {
            letterValue = 1;
        } else if (letterName.toUpperCase().equals("J"))  {
            letterValue = 8;
        } else if (letterName.toUpperCase().equals("K"))  {
            letterValue = 5;
        } else if (letterName.toUpperCase().equals("L"))  {
            letterValue = 1;
        } else if (letterName.toUpperCase().equals("M"))  {
            letterValue = 3;
        } else if (letterName.toUpperCase().equals("N"))  {
            letterValue = 1;
        } else if (letterName.toUpperCase().equals("O"))  {
            letterValue = 1;
        } else if (letterName.toUpperCase().equals("P"))  {
            letterValue = 3;
        } else if (letterName.toUpperCase().equals("Q"))  {
            letterValue = 10;
        } else if (letterName.toUpperCase().equals("R"))  {
            letterValue = 1;
        } else if (letterName.toUpperCase().equals("S"))  {
            letterValue = 1;
        } else if (letterName.toUpperCase().equals("T"))  {
            letterValue = 1;
        } else if (letterName.toUpperCase().equals("U"))  {
            letterValue = 1;
        } else if (letterName.toUpperCase().equals("V"))  {
            letterValue = 4;
        } else if (letterName.toUpperCase().equals("W"))  {
            letterValue = 4;
        } else if (letterName.toUpperCase().equals("X"))  {
            letterValue = 8;
        } else if (letterName.toUpperCase().equals("Y"))  {
            letterValue = 4;
        } else if (letterName.toUpperCase().equals("Z"))  {
            letterValue = 10;
        } else if (letterName.toUpperCase().equals("BLANK"))  {
            letterValue = 0;
        }            
        return letterValue;
    }

    /**
     * Getter method for letterName
     * @return
     */
    public String getLetterName() {        
        return letterName;
    }

    /**
     * Getter method for letterScore
     * @return
     */
    public int getLetterScore() {
        return letterScore;
    }   
}
