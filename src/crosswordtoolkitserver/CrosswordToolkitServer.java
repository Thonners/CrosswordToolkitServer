/*
 * This code is shared under GPL 3.
 */
package crosswordtoolkitserver;

/**
 * Main code to run the back-end server for Crossword Toolkit.
 * 
 * @author M Thomas
 * @since 30/12/16
 */
public class CrosswordToolkitServer {
    
    private static final int serverPort = 9000 ;    // 9000 if running locally on laptop, 8080 if on Pi
    
    private AnagramSolver anagramSolver = new AnagramSolver() ;

    /**
     * main method to be run when the server is started
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Starting Server");
        
        System.out.println("Starting Server");
        MultiThreadedServer server = new MultiThreadedServer(serverPort) ;
        new Thread(server).start();

        try {
            Thread.sleep(60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();  
        }

        System.out.println("Stopping Server");
        server.stop();
    }
    
}
