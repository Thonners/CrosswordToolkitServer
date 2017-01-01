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

/**
 * Class to hold static fields to identify connection type requests
 * 
 * @author M Thomas
 * @since 31/12/16
 */
public class SocketIdentifier {
    
    // Connection tests
    public static final byte CONNECTION_TEST = 1 ;
    public static final byte CONNECTION_TEST_SUCCESSFUL = 2 ;
    // New crosswords
    public static final byte NEW_CROSSWORD_CHECK = 10 ;
    public static final byte DOWNLOAD_CROSSWORD_GRID = 20 ;
    public static final byte SAVE_NEW_CROSSWORD = 30 ;
    // Saving progress
    public static final byte SAVE_PROGRESS = 40 ;
    public static final byte SAVE_PROGRESS_SUCCESS = 41 ;
    // Anagrams
    public static final byte ANAGRAM = 100 ;
    public static final byte ANAGRAM_SOLUTIONS_FAILED = 101 ;
    public static final byte ANAGRAM_SOLUTIONS_SUCCESS = 102 ;
    // Word fit
    public static final byte WORD_FIT = 110 ;
    public static final byte WORD_FIT_SOLUTIONS_FAILED = 111 ;
    public static final byte WORD_FIT_SOLUTIONS_SUCCESS = 112 ;
    
}