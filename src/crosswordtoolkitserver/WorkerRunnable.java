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
    
    private Dictionary dic ;
    
    private DataInputStream dIn = null ;
    private DataOutputStream dOut = null ;
    
    private CTLogger logger = null ;
        
    public WorkerRunnable(Socket clientSocket, Dictionary dic) {
        this.clientSocket = clientSocket ;
        this.dic = dic ;
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
        System.out.println("Connection opened.");
        // Input / Output data streams
        dIn = new DataInputStream(clientSocket.getInputStream());
        dOut = new DataOutputStream(clientSocket.getOutputStream()) ;
        
        boolean done = false;
        while(!done) {
            byte messageTypeByte = dIn.readByte();

            switch(SocketIdentifier.getSocketIdentifierFromByte(messageTypeByte)) {
                case CONNECTION_TEST:
                    System.out.println("Connection test request.");
                    connectionTest() ;
                    done = true;
                    System.out.println("Connection test complete.");
                    break ;
                case NEW_CROSSWORD_CHECK:
                    checkNewCrossword() ;
                    done = true;
                    break ;
                case DOWNLOAD_CROSSWORD_GRID:
                    downloadCrosswordGrid() ;
                    done = true;
                    break ;
                case SAVE_NEW_CROSSWORD:
                    saveNewCrossword() ;
                    done = true;
                    break ;
                case SAVE_PROGRESS:
                    saveProgress() ;
                    done = true;
                    break ;
                case ANAGRAM:
                    System.out.println("Anagram request.");
                    anagram() ;
                    done = true;
                    System.out.println("Anagram complete.");
                    break ; 
                case WORD_FIT:
                    System.out.println("Word-fit request.");
                    wordFit() ;
                    done = true;
                    System.out.println("Anagram complete.");
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
     */
    private void logConnection() {
        logger = new CTLogger(CTLogger.INFO) ;
        String message = "New connection received from: " + clientSocket.getRemoteSocketAddress().toString() ;
        logger.i("WorkerRunnable", message);
    }
   
    /**
     * Method to handle a connection test request from a client connection.
     * 
     * Will send back a connection test successful byte.
     */
    private void connectionTest() throws IOException {
        // Write the successful 
        dOut.writeByte(SocketIdentifier.CONNECTION_TEST_SUCCESSFUL.id());
        // Flush the data
        dOut.flush();
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
        // CrosswordLibraryManager instance
        CrosswordLibraryManager clm = new CrosswordLibraryManager() ;
        // Read the input string from the data
        String gridTitle = dIn.readUTF() ;
        
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
        AnagramSolver as = new AnagramSolver(dic) ;
        // Read the input string from the data
        String anagramString = dIn.readUTF() ;
        // Solve the anagrams
        ArrayList<String> answers = as.solveAnagram(anagramString);
        
        // Return the answers:
        if (answers == null) {
            // Identifier byte
            dOut.writeByte(SocketIdentifier.ANAGRAM_SOLUTIONS_EMPTY.id());
            System.out.println("No answers found for " + anagramString);
        } else {
            // Identifier byte
            dOut.writeByte(SocketIdentifier.ANAGRAM_SOLUTIONS_SUCCESS.id());
            // Number of answers
// dont think this is required            dOut.writeInt(answers.size());
            System.out.println("Found " + answers.size() + " solutions to anagram for " + anagramString) ;
            // Strings
            for (int i = 0 ; i < answers.size(); i++) {
                dOut.writeUTF(answers.get(i));
            }
        }
        
        // Flush the data
        dOut.flush();
    }
    
    /**
     * Process a word-fit request. Receives the string to be fitted, and returns
     * all the words which fit.
     */
    private void wordFit() throws IOException {
        // Word fit instance
        WordFitSolver wfs = new WordFitSolver(dic) ;
        // Read the input string from the data
        String wordFitString = dIn.readUTF() ;
        // Solve the word fit
        ArrayList<String> answers = wfs.solveWordFit(wordFitString) ;
        
        // Return the answers:
        if (answers == null) {
            // Identifier byte
            dOut.writeByte(SocketIdentifier.WORD_FIT_SOLUTIONS_EMPTY.id());
        } else {
            // Identifier byte
            dOut.writeByte(SocketIdentifier.WORD_FIT_SOLUTIONS_SUCCESS.id());
            // Number of answers
//            dOut.writeInt(answers.size());
            // Strings
            for (int i = 0 ; i < answers.size(); i++) {
                dOut.writeUTF(answers.get(i));
            }
        }
        
        // Flush the data
        dOut.flush();
    }

}
