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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class to solve an anagram, using the same word-list and algorithm as the app
 * version.
 * 
 * @author M Thomas
 * @since 30/12/16
 */
public class AnagramSolver {
    
    private Dictionary dic ;
   
    public AnagramSolver(Dictionary dic) {
        // Get a dictionary instance
        //dic = new Dictionary(Dictionary.ANAGRAM) ;
        this.dic = dic ;
    }
        
    /**
     * Method to solve the anagram. 
     * @param inputString The string to be anagrammed
     * @return An ArrayList of the solutions to the anagram, shoud any exist, otherwise returns null.
     */
    public ArrayList<String> solveAnagram(String inputString){
        if (inputString.matches("")) return null ;
        
        String sortedInputString = dic.sortLetters(inputString) ;
        HashMap<String, ArrayList<String>> dictionary = dic.getAnagramDic() ;
        if (dictionary.containsKey(sortedInputString)) {
            System.out.println("Solution found for " + inputString) ;
            return dictionary.get(sortedInputString) ;
        } else {
            System.out.println("AnagramSolver: No anagram matches for: " + inputString);
            return null ;
        }
    }
}