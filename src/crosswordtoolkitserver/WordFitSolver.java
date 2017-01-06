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

import java.util.ArrayList;

/**
 * Class to manage handling request for word-fit.
 * 
 * @author M Thomas
 * @since 04/01/17
 */
public class WordFitSolver {
    
    private Dictionary dic ;
    
    /**
     * Constructor. Creates a word-fit specific dictionary instance.
     */
    public WordFitSolver() {
        // Get a dictionary instance
        dic = new Dictionary(Dictionary.WORD_FIT) ;
    }
    
    public ArrayList<String> solveWordFit(String inputString) {
        // Get the length of the unknown word
        int wordLengthIndex = (inputString.length() - 1) ;
        // Exit if there's a problem
        if (wordLengthIndex < 0) return null ;
        
        // Start solving
        System.out.println("WF: Solving word-fit for: " + inputString) ;
        char firstChar = inputString.charAt(0) ;
        
        ArrayList<String> answers = new ArrayList<>() ;
        ArrayList<String> wordList = new ArrayList<>() ;
        
        // If the first letter is known, get that word-list, otherwise get all word lists of the correct length
        if (firstChar == '.') {
            //System.out.println("WF: First letter is unknown.") ;
            for (int i = 0 ; i < 26 ; i++) {
                wordList.addAll(dic.getPossibleWords(wordLengthIndex, i)) ;
            }
        } else {
            // Only add the relevant word list
            //System.out.println("WF: First letter is " + firstChar + " and the word length = " + wordLengthIndex) ;
            wordList.addAll(dic.getPossibleWords(wordLengthIndex, firstChar)) ;
        }
        
        for (String word : wordList) {
            if (word.toLowerCase().matches(inputString)) {
                answers.add(word) ;
            }
        }
        
        System.out.println("WF: There are " + answers.size() + " answers; " + answers.toString()) ;        
        
        if (answers.size() > 0 ) {
            return answers ;
        } else {
            return null ;
        }
        
    }
}
