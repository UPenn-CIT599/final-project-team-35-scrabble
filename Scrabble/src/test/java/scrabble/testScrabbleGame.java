package scrabble;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

class testScrabbleGame {
    
    @Test
    void testInitializePlayers1() {
        ArrayList<String> nameList = new ArrayList<>(Arrays.asList("Mina", "Daisy"));
        ScrabbleGame game = new ScrabbleGame(nameList);
        assertEquals(game.getCurrentPlayer().getName(), "Mina");
    }
    
    @Test
    void testchangePlayer2() {
        ArrayList<String> nameList = new ArrayList<>(Arrays.asList("Mina", "Daisy", "Shodhan"));
        ScrabbleGame game = new ScrabbleGame(nameList);
        game.changePlayer();
        assertEquals(game.getCurrentPlayer().getName(), "Daisy");
    }
    
    @Test
    void testCheckWordExists3() {
        ArrayList<String> nameList = new ArrayList<>(Arrays.asList("Mina", "Daisy", "Shodhan"));
        ScrabbleGame game = new ScrabbleGame(nameList);
        assertEquals(game.checkWordExists("Right"), true);
        assertEquals(game.checkWordExists("shdlkfh"), false);
    }
    
    @Test
    void testCheckError4() {
        ArrayList<String> nameList1 = new ArrayList<>(Arrays.asList("Messy", "Student", "project"));
        ArrayList<String> nameList2 = new ArrayList<>(Arrays.asList("Mesy", "Stdent", "project"));        
        ScrabbleGame game = new ScrabbleGame(nameList1);
        assertEquals(game.checkError(nameList1), false);
        assertEquals(game.checkError(nameList2), true);
    }    
    
    @Test
    void testPlayEventHorizontal5() {
        ArrayList<Integer> position1 = new ArrayList<>(Arrays.asList(110, 111, 112, 113, 114));
        ArrayList<String> word1 = new ArrayList<>(Arrays.asList("B","O","A","R","D"));
        ArrayList<String> nameList = new ArrayList<>(Arrays.asList("Mina", "Daisy"));
        ScrabbleGame game = new ScrabbleGame(nameList);
        game.playEvent(position1, word1);
        assertEquals(game.getCurrentPlayer().getTotalScore(),16);
    }
    
    @Test
    void testPlayEventVertical6() {
        ArrayList<Integer> position1 = new ArrayList<>(Arrays.asList(82,97,112,127,142));
        ArrayList<String> word1 = new ArrayList<>(Arrays.asList("B","O","A","R","D"));
        ArrayList<String> nameList = new ArrayList<>(Arrays.asList("Mina", "Daisy"));
        ScrabbleGame game = new ScrabbleGame(nameList);
        game.playEvent(position1, word1);
        assertEquals(game.getCurrentPlayer().getTotalScore(),16);
    }
    
    @Test
    void testPlayEventVertical7() {
        ArrayList<Integer> position1 = new ArrayList<>(Arrays.asList(82,97,112,127,142));
        ArrayList<String> word1 = new ArrayList<>(Arrays.asList("B","O","A","R","D"));
        ArrayList<String> nameList = new ArrayList<>(Arrays.asList("Mina", "Daisy"));
        ScrabbleGame game = new ScrabbleGame(nameList);
        game.playEvent(position1, word1);
        assertEquals(game.getCurrentPlayer().getName(),"Mina");
    }    
    
    @Test
    void testplayEventBadStartPosition8() {
        ArrayList<Integer> position1 = new ArrayList<>(Arrays.asList(83,98,113,128,143));
        ArrayList<String> word1 = new ArrayList<>(Arrays.asList("B","O","A","R","D"));
        ArrayList<String> nameList = new ArrayList<>(Arrays.asList("Mina", "Daisy"));
        ScrabbleGame game = new ScrabbleGame(nameList);
        assertEquals(game.playEvent(position1, word1),true);
    }
    
    @Test
    void testPlayEventSecondPlayer9() {
        ArrayList<Integer> position1 = new ArrayList<>(Arrays.asList(82,97,112,127,142));
        ArrayList<String> word1 = new ArrayList<>(Arrays.asList("B","O","A","R","D"));
        ArrayList<String> nameList = new ArrayList<>(Arrays.asList("Mina", "Daisy"));
        ScrabbleGame game = new ScrabbleGame(nameList);
        game.playEvent(position1, word1);
        ArrayList<Integer> position2 = new ArrayList<>(Arrays.asList(143, 144));
        ArrayList<String> word2 = new ArrayList<>(Arrays.asList("A", "Y"));
        assertEquals(game.playEvent(position2, word2),false);        
    }    
    
    @Test
    void testPlayEventSecondPlayer10() {
        ArrayList<Integer> position1 = new ArrayList<>(Arrays.asList(82,97,112,127,142));
        ArrayList<String> word1 = new ArrayList<>(Arrays.asList("B","O","A","R","D"));
        ArrayList<String> nameList = new ArrayList<>(Arrays.asList("Mina", "Daisy"));
        ScrabbleGame game = new ScrabbleGame(nameList);
        game.playEvent(position1, word1);
        game.changePlayer();
        ArrayList<Integer> position2 = new ArrayList<>(Arrays.asList(143, 144));
        ArrayList<String> word2 = new ArrayList<>(Arrays.asList("A", "Y")); //Using Triple Letter
        game.playEvent(position2, word2);
        assertEquals(game.getCurrentPlayer().getTotalScore(),15);
        assertEquals(game.getCurrentPlayer().getName(), "Daisy");
    }    
    
    @Test
    void testPlayEventSecondPlayer11() {
        ArrayList<Integer> position1 = new ArrayList<>(Arrays.asList(82,97,112,127,142));
        ArrayList<String> word1 = new ArrayList<>(Arrays.asList("B","O","A","R","D"));
        ArrayList<String> nameList = new ArrayList<>(Arrays.asList("Mina", "Daisy"));
        ScrabbleGame game = new ScrabbleGame(nameList);
        game.playEvent(position1, word1);
        game.changePlayer();        
        ArrayList<Integer> position2 = new ArrayList<>(Arrays.asList(144, 143));
        ArrayList<String> word2 = new ArrayList<>(Arrays.asList("Y", "A")); //Sorting needed here, and using Triple Letter
        game.playEvent(position2, word2);
        assertEquals(game.getCurrentPlayer().getTotalScore(),15);
        assertEquals(game.getCurrentPlayer().getName(), "Daisy");
    }    
    
    @Test
    void testPlayEventSecondPlayer12() {
        ArrayList<Integer> position1 = new ArrayList<>(Arrays.asList(82,97,112,127,142));
        ArrayList<String> word1 = new ArrayList<>(Arrays.asList("B","O","A","R","D"));
        ArrayList<String> nameList = new ArrayList<>(Arrays.asList("Mina", "Daisy"));
        ScrabbleGame game = new ScrabbleGame(nameList);
        game.playEvent(position1, word1);
        game.changePlayer();        
        ArrayList<Integer> position2 = new ArrayList<>(Arrays.asList(141, 140, 139));
        ArrayList<String> word2 = new ArrayList<>(Arrays.asList("A", "L", "G")); //Other side of Letter 'D', and using Triple Letter
        game.playEvent(position2, word2);
        assertEquals(game.getCurrentPlayer().getTotalScore(),10);
        assertEquals(game.getCurrentPlayer().getName(), "Daisy");
    }  
    
    @Test
    void testPlayEventSecondPlayer13() {
        ArrayList<Integer> position1 = new ArrayList<>(Arrays.asList(82,97,112,127,142, 143, 144)); //Both Vertical and Horizontal Letters not allowed in game
        ArrayList<String> word1 = new ArrayList<>(Arrays.asList("B","O","A","R","D", "A", "Y")); 
        ArrayList<String> nameList = new ArrayList<>(Arrays.asList("Mina", "Daisy"));
        ScrabbleGame game = new ScrabbleGame(nameList);
        assertEquals(game.playEvent(position1, word1), true);
    }    
    
    @Test
    void testPlayEventSecondPlayer14() {
        ArrayList<Integer> position1 = new ArrayList<>(Arrays.asList(82,97,112,127,142));
        ArrayList<String> word1 = new ArrayList<>(Arrays.asList("B","O","A","R","D"));
        ArrayList<String> nameList = new ArrayList<>(Arrays.asList("Mina", "Daisy"));
        ScrabbleGame game = new ScrabbleGame(nameList);
        game.playEvent(position1, word1);
        game.changePlayer();        
        ArrayList<Integer> position2 = new ArrayList<>(Arrays.asList(128, 129));
        ArrayList<String> word2 = new ArrayList<>(Arrays.asList("A", "Y")); //Using Double Letter
        assertEquals(game.playEvent(position2, word2), false);
    }    
    
    @Test
    void testPlayEventSecondPlayer15() {
        ArrayList<Integer> position1 = new ArrayList<>(Arrays.asList(82,97,112,127,142));
        ArrayList<String> word1 = new ArrayList<>(Arrays.asList("B","O","A","R","D"));
        ArrayList<String> nameList = new ArrayList<>(Arrays.asList("Mina", "Daisy"));
        ScrabbleGame game = new ScrabbleGame(nameList);
        game.playEvent(position1, word1);
        game.changePlayer();        
        ArrayList<Integer> position2 = new ArrayList<>(Arrays.asList(124, 125, 126));
        ArrayList<String> word2 = new ArrayList<>(Arrays.asList("B","O","A")); //Using Double Letter on other side
        game.playEvent(position2, word2);
        assertEquals(game.getCurrentPlayer().getTotalScore(),7);
    }          
}
