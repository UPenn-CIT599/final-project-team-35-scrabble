package scrabble;

import java.io.*;
import java.util.*;

/**
 * Class Dictionary
 * @author CIT-591 Team-35 Project
 *
 */
public class Dictionary {
    private ArrayList<String> dictionaryWordsAsList;

    /**
     * Constructor for the Dictionary
     */
    public Dictionary() {
        this.dictionaryWordsAsList = new ArrayList<>();
    }
    
    /**
     * Creates and Copies the Dictionary to an array
     *
     */
    public void createAndCopyDictionaryToArray() {

        // Create object for file
        File dictionaryFile = new File(getClass().getResource("/CollinsScrabbleWords2019.txt").getFile());

        // Read in dictionary from
        try (BufferedReader br = new BufferedReader(
                new FileReader(dictionaryFile))) {

            String wordLine = br.readLine();

            // As long as there's a next line..
            while (wordLine != null) {

                // add word to word list
                this.dictionaryWordsAsList.add(wordLine);
                // System.out.println(wordLine);

                // Read in next line
                wordLine = br.readLine();

            }
        } catch (FileNotFoundException e) {

            System.out.println("This file does not exist");
            e.printStackTrace();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * getDictionaryWordsAsList
     * @return
     */
    public  ArrayList<String> getDictionaryWordsAsList() {
        return this.dictionaryWordsAsList;
    }

    /**
     * setDictionaryWordsAsList
     * @param dictionaryWordsAsList
     */
    public void setDictionaryWordsAsList(ArrayList<String> dictionaryWordsAsList) {
        this.dictionaryWordsAsList = dictionaryWordsAsList;
    }

    /*
    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();
        dictionary.createAndCopyDictionaryToArray();
        System.out.println(dictionary.getDictionaryWordsAsList());
    } */
}
