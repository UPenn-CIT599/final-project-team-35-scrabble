package scrabble;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BoardTest {

	@Test
	void testContainsLetter() {
		Board board = new Board();
		board.getCell(45).setLetter(new Letter("L"));
		assertEquals(board.containsLetter(45),true);
		assertEquals(board.containsLetter(44),false);
	}

	@Test
	void testContainsVerticalWord() {
		Board board = new Board();
		board.getCell(0).setLetter(new Letter("A"));
		board.getCell(15).setLetter(new Letter("S"));
		assertEquals(board.containsVerticalWord(0), true);
		assertEquals(board.containsVerticalWord(1), false);
	}

	@Test
	void testContainsHorizontalWord() {
		Board board = new Board();
		board.getCell(0).setLetter(new Letter("A"));
		board.getCell(1).setLetter(new Letter("S"));
		assertEquals(board.containsVerticalWord(0), true);
		assertEquals(board.containsVerticalWord(15), false);
	}

}