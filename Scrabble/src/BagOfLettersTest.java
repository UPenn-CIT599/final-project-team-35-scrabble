import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

class BagOfLettersTest {

	@Test
	void testAddLetters() {
		BagOfLetters bag = new BagOfLetters();
		int originalSize = bag.getCurrentBagOfLetters().size();
		Letter l1 = new Letter("A");
		Letter l2 = new Letter("B");
		Letter l3 = new Letter("D");
		ArrayList<Letter> lettersToAdd = new ArrayList<Letter>(Arrays.asList(l1,l2,l3));
		bag.addLetters(lettersToAdd);
		assertEquals(bag.getCurrentBagOfLetters().size(),originalSize+3);

	}
	
	@Test
	void testRemoveLetters() {
		BagOfLetters bag = new BagOfLetters();
		int originalSize = bag.getCurrentBagOfLetters().size();
		Letter l1 = new Letter("C");
		bag.removeLetter(l1);
		assertEquals(bag.getCurrentBagOfLetters().size(),originalSize -1);
	}
	
	@Test
	void testIsEmpty() {
		BagOfLetters bag = new BagOfLetters();
		for (int i = 0; i < bag.letterNames.length; i++) {
            for (int j = 0; j < bag.letterDistribution[i]; j++) {
                bag.removeLetter(new Letter(bag.letterNames[i]));
            }
		}
		assertEquals(bag.isEmpty(),true);
	}

}
