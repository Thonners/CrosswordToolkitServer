/*
 * This code is shared under GPL 3.
 */
package crosswordtoolkitserver;

import java.io.File;

/**
 * Main code to run the back-end server for Crossword Toolkit.
 * 
 * Main method will start a MultiThreadedServer, and then check, every 10 seconds
 * for the existence of the ABORT file. If found, the process will exit, removing
 * the ABORT file in the process, so that the server may start again in the future.
 * 
 * The server will not start if the abort file is present, and will return with
 * a value of 1 to indicate an error.
 * 
 * @author M Thomas
 * @since 30/12/16
 */
public class CrosswordToolkitServer {
    
    private static final int serverPort = 9000 ;    // 9000 if running locally on laptop, something else if on Pi
    
    private static final String ABORT_FILE_PATH = "/srv/java/CrosswordToolkitServer/ABORT" ;
    /**
     * main method to be run when the server is started
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // ABORT file
        File abortFile = new File(ABORT_FILE_PATH) ;
        
        // Only launch server if file doesn't exist
        if (!abortFile.exists()) {
        
            System.out.println("Starting Server");
            MultiThreadedServer server = new MultiThreadedServer(serverPort) ;
            new Thread(server).start();

            try {
                while (!abortFile.exists()) {
                    Thread.sleep(10 * 1000);
                }
                System.out.println("ABORT file detected.");                
            } catch (InterruptedException e) {
                e.printStackTrace();  
            }

            System.out.println("Stopping Server");
            // Stop the server
            server.stop();
            // Delete the abort file
            abortFile.delete() ;
        } else {
            System.out.println("ABORT file detected, so not starting server");
            System.exit(1);
        }
    }
    
}
