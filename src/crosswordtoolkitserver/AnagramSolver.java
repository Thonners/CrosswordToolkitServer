/*
 * Copyright (C) 2016 M Thomas
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.Executors;

/**
 * Class to solve an anagram, using the same word-list and algorithm as the app
 * version.
 * 
 * @author M Thomas
 * @since 30/12/16
 */
public class AnagramSolver {
    
    private final String WORDS_LIST_PATH = "/res/sopwads" ;
    
    private ClassLoader objClassLoader = null ;
    private HashMap<String, ArrayList<String>> dictionary = new HashMap<>() ;
   
    public AnagramSolver() {
        // Get the class loader
        objClassLoader = getClass().getClassLoader() ;
        
        readWordList() ;
        
    }
    
    /**
     * Method to load the wordlist into memory, specifically, storing it as entries to the HashMap
     */
    private void readWordList() {  
        System.out.print("Loading dictionary...");
        
        /* try-with-resource to load the words*/
        try(BufferedReader sopwadsReader = new BufferedReader(new FileReader(objClassLoader.getResource(WORDS_LIST_PATH).getFile()))){
            String word = null ;
            while((word = sopwadsReader.readLine()) != null) {
                String sortedLetters = sortLetters(word) ;
                if (dictionary.containsKey(sortedLetters)) {
                    // If the dictionary already has a key with the sorted letters, add the new word to the list of words for that set of letters
                    dictionary.get(sortedLetters).add(word);
                } else {
                    // Otherwise, create an entry
                    ArrayList<String> newWord = new ArrayList<>() ;
                    newWord.add(word);
                    dictionary.put(sortedLetters, newWord);
                }
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        System.out.print("Done.");        
    }

    /**
     * Method to sort the letters of a word alphabetically, and to return them as a String
     * @param word The word whose letters are to be sorted
     * @return The sorted letters, recompiled as a String
     */
    private String sortLetters(String word){
        char[] letters = word.toCharArray() ;
        Arrays.sort(letters);
        return String.valueOf(letters);
    }
    
    private void solveAnagrams(String inputString){
        String sortedInputString = sortLetters(inputString) ;
        
    }
}