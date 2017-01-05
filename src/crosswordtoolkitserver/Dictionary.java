/*
 * Copyright (C) 2017 M Thomas
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package crosswordtoolkitserver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Class to load and distribute the word-list dictionary as required.
 * 
 * @author M Thomas
 * @since 03/01/17
 */
public class Dictionary {
    
    // Public static fields to define the type of dictionary
    public final static int ALL = 0 ;
    public final static int ANAGRAM = 1 ;
    public final static int WORD_FIT = -1 ;    
    
    // Member variables
    private final String WORDS_LIST_PATH = "sopwads" ;    
    private ClassLoader objClassLoader = null ;
    private int type ;
    private HashMap<String, ArrayList<String>> anagramDic = null ;
    private ArrayList<ArrayList<ArrayList<String>>> wordFitDic = null ; // Wrapper > Word-length > Letter
    
    /**
     * Basic Constructor - load all types of dictionary.
     */
    public Dictionary() {
        this(ALL) ;
    }
    
    /**
     * Constructor. Loads the specified type of dictionary.
     * 
     * @param dictionaryType The type of dictionary to load, as specified by the static fields in this class.
     */
    public Dictionary(int dictionaryType) {
        // Set the type
        this.type = dictionaryType ;
        
        // Get the class loader
        objClassLoader = getClass().getClassLoader() ;
        
        // Create instances of apropriate dictionaries
        if (type >= 0) anagramDic = new HashMap<>() ;
        if (type <= 0) {
            initialiseWordFitDic() ;
        }
        
        // Read the word list
        readWordList() ;
    }
    
    /**
     * Method to initialise both the wrapper ArrayList and each individual ArrayList for each letter
     */
    private void initialiseWordFitDic() {
        //  Initialise instance
        wordFitDic = new ArrayList<>();
        
        // Initialise the ArrayList for each length word
        for (int i = 0 ; i < 10 ; i++) {
        ArrayList<ArrayList<String>> wordLengthDic = new ArrayList<>();
            // Initialise the ArrayList for each letter
            for (int j = 0 ; j < 26 ; j++) {
                ArrayList<String> letterDic = new ArrayList<>();
                wordLengthDic.add(letterDic) ;
            }
        // Add the ArrayLists to the wrapper ArrayList
        wordFitDic.add(wordLengthDic) ;
        }
    }
    
    /**
     * Method to load the wordlist into memory, specifically, storing it as entries to the HashMap
     */
    private void readWordList() {  
        System.out.print("Loading dictionary...");
        
        /* try-with-resource to load the words*/
        try(BufferedReader sopwadsReader = new BufferedReader(new FileReader(objClassLoader.getResource(WORDS_LIST_PATH).getFile()))){
            String word ;
            while((word = sopwadsReader.readLine()) != null) {
                
                // if anagram dictionary:
                if (type >= 0) {
                    String sortedLetters = sortLetters(word) ;
                    if (anagramDic.containsKey(sortedLetters)) {
                        // If the dictionary already has a key with the sorted letters, add the new word to the list of words for that set of letters
                        anagramDic.get(sortedLetters).add(word);
                    } else {
                        // Otherwise, create an entry
                        ArrayList<String> newWord = new ArrayList<>() ;
                        newWord.add(word);
                        anagramDic.put(sortedLetters, newWord);
                    }
                }
                
                // if word-fit dictionary
                if (type <= 0) {
                    int wordLengthIndex = Math.min(9,getWordLengthIndex(word)) ;
                    wordFitDic.get(wordLengthIndex).get(getLetterIndex(word.charAt(0))).add(word) ;
                }
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        System.out.println("Done.");        
    }

    /**
     * Method to sort the letters of a word alphabetically, and to return them as a String
     * @param word The word whose letters are to be sorted
     * @return The sorted letters, recompiled as a String
     */
    public String sortLetters(String word){
        char[] letters = word.toCharArray() ;
        Arrays.sort(letters);
        return String.valueOf(letters);
    }
    
    /**
     * @param letter The letter to be indexed
     * @return The index in the alphabet (base 0) of the letter, e.g. c = 2, z = 25.
     */
    private int getLetterIndex(char letter) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz" ;
        return (alphabet.indexOf(letter)) ;
    }
    
    /**
     * Method to calculate the index of the relevant ArrayList of word-lengths in the word-fit dictionary. 
     * @param word The word to be length-indexed
     * @return The (length of the word) - 1 (Always returns >= 0)
     */
    private int getWordLengthIndex(String word) {
        return Math.max(0,(word.length() - 1)) ; // Make sure it's never less than 0, and take 1 off the length of the word
    }
    
    /**
     * @return The instance of the anagram dictionary HashMap
     */
    public HashMap<String, ArrayList<String>> getAnagramDic() {
        return anagramDic ;
    }
    
    /**
     * Method for use in word-fitting
     * @param wordLength The length of the unknown word
     * @param firstLetter The first letter of the unknown word
     * @return The ArrayList containing all the words of the specified length, beginning with the specified first letter.
     */
    public ArrayList<String> getPossibleWords(int wordLength, char firstLetter) {
        return getPossibleWords(wordLength, getLetterIndex(firstLetter)) ;
    }
    
    /**
     * Method for use in word-fitting
     * @param wordLength The length of the unknown word
     * @param firstLetterIndex The letter index of the first letter of the unknown word
     * @return The ArrayList containing all the words of the specified length, beginning with the specified first letter.
     */
    public ArrayList<String> getPossibleWords(int wordLength, int firstLetterIndex) {
        int limitedWordLength = Math.min(9, wordLength) ;
        return wordFitDic.get(limitedWordLength).get(firstLetterIndex) ;
    }
}
