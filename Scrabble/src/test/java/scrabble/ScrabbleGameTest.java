package scrabble;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

class ScrabbleGameTest {

	@Test
	void testinitializePlayers() {
		ArrayList<String> nameList = new ArrayList<>(Arrays.asList("Mina", "Daisy"));
		ScrabbleGame game = new ScrabbleGame(nameList);
		game.initializePlayers(nameList);
		assertEquals(game.getCurrentPlayer(), "Mina");
	}
	
	@Test
	void testchangePlayer() {
		ArrayList<String> nameList = new ArrayList<>(Arrays.asList("Mina", "Daisy"));
		ScrabbleGame game = new ScrabbleGame(nameList);
		game.initializePlayers(nameList);
		game.changePlayer();
		assertEquals(game.getCurrentPlayer(), "Daisy");
	}
	
	@Test
	void testcheckWordExists() {
		ArrayList<String> nameList = new ArrayList<>(Arrays.asList("Mina", "Daisy"));
		ScrabbleGame game = new ScrabbleGame(nameList);
		assertEquals(game.checkWordExists("Right"), true);
		assertEquals(game.checkWordExists("shdlkfh"), false);
	}
	
	@Test
	void testplayEventHorizontal() {
		ArrayList<Integer> position1 = new ArrayList<>(Arrays.asList(0,1,2,3,4));
		ArrayList<String> word1 = new ArrayList<>(Arrays.asList("B","O","A","R","D"));
		ArrayList<String> nameList = new ArrayList<>(Arrays.asList("Mina", "Daisy"));
		ScrabbleGame game = new ScrabbleGame(nameList);
		game.playEvent(position1, word1);
		assertEquals(game.getCurrentPlayer().getTotalScore(),30);
	}
	
	@Test
	void testplayEventVertical() {
		ArrayList<Integer> position1 = new ArrayList<>(Arrays.asList(0,15,30,45,60));
		ArrayList<String> word1 = new ArrayList<>(Arrays.asList("B","O","A","R","D"));
		ArrayList<String> nameList = new ArrayList<>(Arrays.asList("Mina", "Daisy"));
		ScrabbleGame game = new ScrabbleGame(nameList);
		game.playEvent(position1, word1);
		assertEquals(game.getCurrentPlayer().getTotalScore(),30);
	}
	
	@Test
	void testplayEventWordError() {
		ArrayList<Integer> position1 = new ArrayList<>(Arrays.asList(0,15,30,45,60));
		ArrayList<String> word1 = new ArrayList<>(Arrays.asList("B","A","A","A","D"));
		ArrayList<String> nameList = new ArrayList<>(Arrays.asList("Mina", "Daisy"));
		ScrabbleGame game = new ScrabbleGame(nameList);
		game.playEvent(position1, word1);
		assertEquals(game.getCurrentPlayer().getTotalScore(),0);
		assertEquals(game.playEvent(position1, word1),true);
	}
	
	@Test
	void testplayEventSecondPlayer() {
		ArrayList<Integer> position1 = new ArrayList<>(Arrays.asList(0,15,30,45,60));
		ArrayList<String> word1 = new ArrayList<>(Arrays.asList("B","O","A","R","D"));
		ArrayList<String> nameList = new ArrayList<>(Arrays.asList("Mina", "Daisy"));
		ScrabbleGame game = new ScrabbleGame(nameList);
		game.playEvent(position1, word1);
		ArrayList<Integer> position2 = new ArrayList<>(Arrays.asList(1));
		ArrayList<String> word2 = new ArrayList<>(Arrays.asList("Y"));
		game.playEvent(position2, word2);
		assertEquals(game.getCurrentPlayer().getTotalScore(),21);
		assertEquals(game.getCurrentPlayer().getName(), "Daisy");
	}
	
	
}
