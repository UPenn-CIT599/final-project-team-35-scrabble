/**
 * Class Letter 
 * @author CIT-591 Team-35 Project
 *
 */
public class Letter {
    private String letterName;
    private int letterValue;

    /**
     * Constructor for Letter
     * @param letterName
     */
    public Letter(String letterName) {
        this.letterName = letterName;
        if (letterName.toUpperCase().equals("A"))  {
            this.letterValue = 1;
        }
        if (letterName.toUpperCase().equals("B"))  {
            this.letterValue = 3;
        }
        if (letterName.toUpperCase().equals("C"))  {
            this.letterValue = 3;
        }
        if (letterName.toUpperCase().equals("D"))  {
            this.letterValue = 2;
        }
        if (letterName.toUpperCase().equals("E"))  {
            this.letterValue = 1;
        }
        if (letterName.toUpperCase().equals("F"))  {
            this.letterValue = 4;
        }
        if (letterName.toUpperCase().equals("G"))  {
            this.letterValue = 2;
        }
        if (letterName.toUpperCase().equals("H"))  {
            this.letterValue = 4;
        }
        if (letterName.toUpperCase().equals("I"))  {
            this.letterValue = 1;
        }
        if (letterName.toUpperCase().equals("J"))  {
            this.letterValue = 8;
        }
        if (letterName.toUpperCase().equals("K"))  {
            this.letterValue = 5;
        }
        if (letterName.toUpperCase().equals("L"))  {
            this.letterValue = 1;
        }
        if (letterName.toUpperCase().equals("M"))  {
            this.letterValue = 3;
        }
        if (letterName.toUpperCase().equals("N"))  {
            this.letterValue = 1;
        }
        if (letterName.toUpperCase().equals("O"))  {
            this.letterValue = 1;
        }
        if (letterName.toUpperCase().equals("P"))  {
            this.letterValue = 3;
        }
        if (letterName.toUpperCase().equals("Q"))  {
            this.letterValue = 10;
        }
        if (letterName.toUpperCase().equals("R"))  {
            this.letterValue = 1;
        }
        if (letterName.toUpperCase().equals("S"))  {
            this.letterValue = 1;
        }
        if (letterName.toUpperCase().equals("T"))  {
            this.letterValue = 1;
        }
        if (letterName.toUpperCase().equals("U"))  {
            this.letterValue = 1;
        }
        if (letterName.toUpperCase().equals("V"))  {
            this.letterValue = 4;
        }
        if (letterName.toUpperCase().equals("W"))  {
            this.letterValue = 4;
        }
        if (letterName.toUpperCase().equals("X"))  {
            this.letterValue = 8;
        }
        if (letterName.toUpperCase().equals("Y"))  {
            this.letterValue = 4;
        }
        if (letterName.toUpperCase().equals("Z"))  {
            this.letterValue = 10;
        }    
        if (letterName.toUpperCase().equals("BLANK"))  {
            this.letterValue = 0;
        }                 
    }

    /**
     * Getter method for letterName
     * @return
     */
    public String getLetterName() {
        return letterName;
    }

    /**
     * Getter method for letterValue
     * @return
     */
    public int getLetterValue() {
        return letterValue;
    }
    
    
}
