package scrabble;


import scrabble.gui.DummyGUI;

/**
 * This class tests the game of Scrabble
 * @author CIT-591 Team-35 Project
 *
 */
public class ScrabbleRunner {
    /**
     * The main method
     * @param args
     */ 
    public static void main(String[] args) {

        DummyGUI gameUI = new DummyGUI();
        gameUI.welcomePage(args);

    }
}
