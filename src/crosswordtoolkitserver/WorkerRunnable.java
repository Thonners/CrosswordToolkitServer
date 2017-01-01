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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Runnable class to deal with an incoming socket connection
 * 
 * @author M Thomas
 * @since 30/12/16
 */
public class WorkerRunnable implements Runnable {
    
    protected Socket clientSocket = null;
    
    private DataInputStream dIn = null ;
    private DataOutputStream dOut = null ;
    
    public WorkerRunnable(Socket clientSocket) {
        this.clientSocket = clientSocket ;
    }
    
    @Override
    public void run() {
        if (clientSocket != null) {
            try {
                processClientRequest();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Error: Can't process client requet, since clientSocket == null.");
        }
    }

    /**
     * Method to manage the connection, once a client connects to the server.
     * @throws IOException 
     */
    private void processClientRequest()
    throws IOException {
        // Log the connection
        logConnection();
        System.out.println("This server has been connected to " + " times.");
        // Input / Output data streams
        dIn = new DataInputStream(clientSocket.getInputStream());
        dOut = new DataOutputStream(clientSocket.getOutputStream()) ;
        
        boolean done = false;
        while(!done) {
            byte messageType = dIn.readByte();

            switch(messageType) {
                case SocketIdentifier.CONNECTION_TEST:
                    connectionTest() ;
                    break ;
                case SocketIdentifier.NEW_CROSSWORD_CHECK:
                    checkNewCrossword() ;
                    break ;
                case SocketIdentifier.DOWNLOAD_CROSSWORD_GRID:
                    downloadCrosswordGrid() ;
                    break ;
                case SocketIdentifier.SAVE_NEW_CROSSWORD:
                    saveNewCrossword() ;
                    break ;
                case SocketIdentifier.SAVE_PROGRESS:
                    saveProgress() ;
                    break ;
                case SocketIdentifier.ANAGRAM:
                    anagram() ;
                    break ; 
                case SocketIdentifier.WORD_FIT:
                    wordFit() ;
                    break ; 
                default:
                    done = true;
            }
        }

        System.out.print("Closing sockets... ");
        dOut.close();
        dIn.close();
        clientSocket.close();
        System.out.println("Done.");
        
    }

    /**
     * Method to log the connection.
     * TODO: Implement connection logging
     */
    private void logConnection() {
        System.out.println("NEED TO IMPLEMENT LOGGING.");
    }
    
    /**
     * Method to handle a connection test request from a client connection.
     * 
     * Will send back a connection test successful byte.
     */
    private void connectionTest() throws IOException {
        
    }
    
    /**
     * Method to check whether a crossword already exists in the database.
     */
    private void checkNewCrossword() throws IOException {
        
    }
    
    /**
     * Method to send a crossword grid to the client. For use when they're 
     * creating a new crossword, to save them having to input the grid.
     */
    private void downloadCrosswordGrid() throws IOException {
        
    }
    
    /**
     * Method to add a new grid to the database when a client connects with a 
     * new grid.
     */
    private void saveNewCrossword() throws IOException {
        
    }
    
    /**
     * Method to save a user's progress. Receives the latest crossword save string 
     * and writes it to disk.
     */
    private void saveProgress() throws IOException {
        
    }
    
    /**
     * Process an anagram request. Receives the string to be anagrammed, and 
     * returns the answers which fit.
     */
    private void anagram() throws IOException {
        // Anagram solver instance
        AnagramSolver as = new AnagramSolver() ;
        // Read the input string from the data
        String anagramString = dIn.readUTF() ;
        // Solve the anagrams
        ArrayList<String> answers = as.solveAnagram(anagramString);
        
        // Return the answers:
        if (answers == null) {
            // Identifier byte
            dOut.writeByte(SocketIdentifier.ANAGRAM_SOLUTIONS_FAILED);
        } else {
            // Identifier byte
            dOut.writeByte(SocketIdentifier.ANAGRAM_SOLUTIONS_SUCCESS);
            // Number of answers
            dOut.writeInt(answers.size());
            // Strings
            for (int i = 0 ; i < answers.size(); i++) {
                dOut.writeUTF(answers.get(i));
            }
        }
    }
    
    /**
     * Process a word-fit request. Receives the string to be fitted, and returns
     * all the words which fit.
     */
    private void wordFit() throws IOException {
        
    }

}
