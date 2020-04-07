import java.util.ArrayList;
import java.util.Random;

/**
 * Class BagOfletters
 * @author CIT-591 Team-35 Project
 */
public class BagOfLetters {
    private ArrayList<Letter> letters;
    private Random rand;
    String[] letterNames = { "A", "B", "C", "D", "E", "F", "G", "H", "I", 
                             "J", "K", "L", "M", "N", "O", "P", "Q", "R", 
                             "S", "T", "U", "V", "W", "X", "Y", "Z", "BLANK" };
    int[] letterDistribution = { 16, 4, 6, 8, 24, 4, 5, 5, 13, 
                              2, 2, 7, 6, 13, 15, 4, 2, 13, 
                              10, 15, 7, 3, 4, 2, 4, 2, 4 };
    int numLettersToPick;
    ArrayList<Letter> pickOfNewLetters;
    
    /**
     * The constructor method. This creates the bag of Letters
     */
    public BagOfLetters() {
        letters = new ArrayList<Letter>();
        for (int i = 0; i < letterNames.length; i++) {
            for (int j = 0; j < letterDistribution[i]; j++) {
                letters.add(new Letter(letterNames[i]));
            }
        }
        pickOfNewLetters = new ArrayList<Letter>();         
    }
    
    
    /**
     * method for randomly picking Letters from Bag of Letters
     */
    public ArrayList<Letter> shuffleAndPickLetters(int numLetters) {
        numLettersToPick = numLetters;
        pickOfNewLetters.clear();       
        //Shuffle();
        for (int i=0; i < numLetters; i++) {
            int randInt = rand.nextInt(letters.size());
            Letter newLetter = letters.get(randInt);
            pickOfNewLetters.add(newLetter); //TODO : This needs to be a random letter selection. Need to handle "BLANK" too
            removeLetter(newLetter);           
        }
        
        return pickOfNewLetters;        
    }   
    
    /**
     * method for adding Letters back to "Bag of Letters"
     */
    public void addLetters(ArrayList<Letter> lettersToAddBack) {
        letters.addAll(lettersToAddBack);       
    }     
    
    /**
     * This method shuffles the letters in the "Bag of Letters".
     *           
     */
    public void Shuffle() {
        // TODO : Check if needed. 
    }    
    
    /**
     * This method removes the letter/letters from the "Bag of Letters".
     * 
     * @param pickedLetter
     *            the letter to be removed from "bag of letters".
     */
    public void removeLetter(Letter pickedLetter) {
        letters.remove(pickedLetter);
    }
    
    /**
     * This shows the status of whether the "Bag of Letters" is Empty.
     * 
     * @return if "bag of Letters" is empty 
     *        
     */
    public boolean isEmpty() {
        boolean bagEmpty = false;
        return bagEmpty;
    }    
    
    /**
     * This returns the current "Bag of Letters".
     * 
     * @return the current bag of letters
     */
    public ArrayList<Letter> getCurrentBagOfLetters() {
        return letters;
    }
}