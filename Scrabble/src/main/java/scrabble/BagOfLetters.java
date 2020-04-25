package scrabble;
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
    /* //Letter Distribution for Super-Scrabble with 21x21 rowsxColumns
     int[] letterDistribution = { 16, 4, 6, 8, 24, 4, 5, 5, 13, 
                              2, 2, 7, 6, 13, 15, 4, 2, 13, 
                              10, 15, 7, 3, 4, 2, 4, 2, 4 }; */
    //Letter Distribution for Regular Scrabble with 15x15 rowsxColumns    
    int[] letterDistribution = { 9, 2, 2, 4, 12, 2, 3, 2, 9, 
            1, 1, 4, 2, 6, 8, 2, 1, 6, 
            4, 6, 4, 2, 2, 1, 2, 1, 2 };    
    int numLettersToPick;
    
    //ArrayList<String> pickOfNewLetters;    
    private boolean bagEmpty;  
    private static Letter newLetter;
    
    /**
     * The constructor method. This creates the bag of Letters
     */
    public BagOfLetters() {
        rand = new Random();
        letters = new ArrayList<Letter>();
        for (int i = 0; i < letterNames.length; i++) {
            for (int j = 0; j < letterDistribution[i]; j++) {
                letters.add(new Letter(letterNames[i]));
            }
        }
        bagEmpty = false;
    }
    
    
    /**
     * method for randomly picking Letters from Bag of Letters
     */
    //public ArrayList<String> shuffleAndPickLetters(int numLetters) {    
    public ArrayList<Letter> shuffleAndPickLetters(int numLetters) {        
        numLettersToPick = numLetters;
        ArrayList<Letter> pickOfNewLetters  = new ArrayList<Letter>();
     /* //Shuffle(); //Already done in Ln-47 using rand fn
        System.out.println("Num Letters in Bag : " + letters.size());
        System.out.println("Letter in position 41 : " + letters.get(40).getLetterName());     
        System.out.println("Letter in position 42 : " + letters.get(41).getLetterName());
        System.out.println(rand.nextInt(10));
        int randInt; */       
        for (int i=0; i < numLetters; i++) {
            if (!bagEmpty) {
                //randInt = rand.nextInt(letters.size());
                //Letter newLetter = letters.get(randInt);
                newLetter = this.getCurrentBagOfLetters().get(rand.nextInt(letters.size()));
                pickOfNewLetters.add(newLetter); //TODO : This needs to be a random letter selection. Need to handle "BLANK" too
                removeLetter(newLetter);        
                //currentCard = deck.getCards().get(rand.nextInt(deck.getCards().size()));
                //deck.removeCard(currentCard);                
            }          
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
        //bagEmpty = false;        
        if (letters.size() < 7) { //== 0) {
            bagEmpty = true;            
        }
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