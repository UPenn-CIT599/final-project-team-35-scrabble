package scrabble;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ScoreKeeperTest {

	@Test
	void testScoreUpdate1() {
		ScoreKeeper score = new ScoreKeeper();
		score.scoreUpdate("Mina",10);
		score.scoreUpdate("Mina",6);
		score.scoreUpdate("Daisy",2);
		assertEquals(score.getPlayerScores().get("Mina"), 16);
		assertEquals(score.getPlayerScores().get("Daisy"), 2);
	}
	
}
