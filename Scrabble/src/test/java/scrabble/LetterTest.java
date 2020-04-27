package scrabble;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class testLetter {

    @Test
    void testConvertNameToValue() {
        //fail("Not yet implemented");
        Letter myLetterA = new Letter("A");
        assertEquals(myLetterA.getLetterScore(), 1);
        Letter myLetterB = new Letter("B");
        assertEquals(myLetterB.getLetterScore(), 3);
        Letter myLetterC = new Letter("C");
        assertEquals(myLetterC.getLetterScore(), 3);
        Letter myLetterD = new Letter("D");
        assertEquals(myLetterD.getLetterScore(), 2);
        Letter myLetterE = new Letter("E");
        assertEquals(myLetterE.getLetterScore(), 1);
        Letter myLetterF = new Letter("F");
        assertEquals(myLetterF.getLetterScore(), 4);
        Letter myLetterG = new Letter("G");
        assertEquals(myLetterG.getLetterScore(), 2);
        Letter myLetterH = new Letter("H");
        assertEquals(myLetterH.getLetterScore(), 4);
        Letter myLetterI = new Letter("I");
        assertEquals(myLetterI.getLetterScore(), 1);
        Letter myLetterJ = new Letter("J");
        assertEquals(myLetterJ.getLetterScore(), 8);
        Letter myLetterK = new Letter("K");
        assertEquals(myLetterK.getLetterScore(), 5);
        Letter myLetterL = new Letter("L");
        assertEquals(myLetterL.getLetterScore(), 1);
        Letter myLetterM = new Letter("M");
        assertEquals(myLetterM.getLetterScore(), 3);
        Letter myLetterN = new Letter("N");
        assertEquals(myLetterN.getLetterScore(), 1);
        Letter myLetterO = new Letter("O");
        assertEquals(myLetterO.getLetterScore(), 1);
        Letter myLetterP = new Letter("P");
        assertEquals(myLetterP.getLetterScore(), 3);
        Letter myLetterQ = new Letter("Q");
        assertEquals(myLetterQ.getLetterScore(), 10);
        Letter myLetterR = new Letter("R");
        assertEquals(myLetterR.getLetterScore(), 1);
        Letter myLetterS = new Letter("S");
        assertEquals(myLetterS.getLetterScore(), 1);
        Letter myLetterT = new Letter("T");
        assertEquals(myLetterT.getLetterScore(), 1);
        Letter myLetterU = new Letter("U");
        assertEquals(myLetterU.getLetterScore(), 1);
        Letter myLetterV = new Letter("V");
        assertEquals(myLetterV.getLetterScore(), 4);
        Letter myLetterW = new Letter("W");
        assertEquals(myLetterW.getLetterScore(), 4);
        Letter myLetterX = new Letter("X");
        assertEquals(myLetterX.getLetterScore(), 8);
        Letter myLetterY = new Letter("Y");
        assertEquals(myLetterY.getLetterScore(), 4);
        Letter myLetterZ = new Letter("Z");
        assertEquals(myLetterZ.getLetterScore(), 10);
        Letter myLetterBlank = new Letter("BLANK");
        assertEquals(myLetterBlank.getLetterScore(), 0);       
    }
}