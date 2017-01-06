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
 * Enum to identify the type of connection requested from a client, when connecting to the server.
 * 
 * Switch to enum over class with static final byte fields to get better compile-time checking.
 * 
 * @author M Thomas
 * @since 31/12/16
 */
public enum SocketIdentifier {
    // Connection tests
    CONNECTION_TEST((byte) 1),
    CONNECTION_TEST_SUCCESSFUL((byte) 2),
    // New crosswords
    NEW_CROSSWORD_CHECK((byte) 10),
    DOWNLOAD_CROSSWORD_GRID((byte) 20),
    SAVE_NEW_CROSSWORD((byte) 30),
    // Saving progress
    SAVE_PROGRESS((byte) 40),
    SAVE_PROGRESS_SUCCESS((byte) 41),
    // Anagrams
    ANAGRAM((byte) 100),
    ANAGRAM_SOLUTIONS_EMPTY((byte) 101),
    ANAGRAM_SOLUTIONS_SUCCESS((byte) 102),
    // Word fit
    WORD_FIT((byte) 110),
    WORD_FIT_SOLUTIONS_EMPTY((byte) 111),
    WORD_FIT_SOLUTIONS_SUCCESS((byte) 112);

    private byte id ;

    SocketIdentifier(byte id) {
        this.id = id ;
    }

    public byte id() {
        return id ;
    }
    
    /**
     * Method to turn a byte into a SocketIdentifier
     * @param input The byte received from the connected client
     * @return The SocketIdentifier related to the received byte
     */
    public static SocketIdentifier getSocketIdentifierFromByte(byte input) {
        for (SocketIdentifier si : SocketIdentifier.values()) {
            if (si.id == input) {
                return si ;
            }
        }
        // If it doesn't match...
        throw new IllegalArgumentException();
    }

}
