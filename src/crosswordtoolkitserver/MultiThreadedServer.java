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

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Class to run the server, spawning a new thread for every incoming connection
 * 
 * @author M Thomas
 * @since 30/12/16
 */
public class MultiThreadedServer implements Runnable {
    
    protected int          serverPort   = 8080; // Seems to be overwritten on instance creation - default must be for overridden classes
    protected ServerSocket serverSocket = null;
    protected boolean      isStopped    = false;
    protected Thread       runningThread= null;
    
    /**
     * Constructor
     * @param port The port on which to listen for incoming connections
     */
    public MultiThreadedServer(int port){
        this.serverPort = port;
    }

    /**
     * The main method to be run once connected.
     * 
     * Sets the thread, and opens the ServerSocket to listen on the serverPort.
     * Then runs until closed, trying to open a connection when a client connects.
     */
    public void run(){
        synchronized(this){
            this.runningThread = Thread.currentThread();
        }
        openServerSocket();
        
        while(! isStopped()){
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if(isStopped()) {
                    System.out.println("Server Stopped.") ;
                    return;
                }
                throw new RuntimeException(
                    "Error accepting client connection", e);
            }
            // Start a new thread to deal with the connection
            new Thread(
                new WorkerRunnable(
                    clientSocket)
            ).start();
            
        }
        
        System.out.println("Server Stopped.");
    }

    /**
     * @return Whether the server is still listening, or is stopped
     */
    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    /**
     * Method to stop listening on the serverPort and to close the socket.
     */
    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    /**
     * Method to try creating an instance of a ServerSocket, attached to the
     * serverPort.
     */
    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port " + this.serverPort, e);
        }
    }
    
}
