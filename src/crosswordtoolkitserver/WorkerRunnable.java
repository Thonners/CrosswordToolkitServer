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

/**
 * Runnable class to deal with an incoming socket connection
 * 
 * @author M Thomas
 * @since 30/12/16
 */
public class WorkerRunnable implements Runnable {
    
    protected Socket clientSocket = null;
    
    public WorkerRunnable(Socket clientSocket) {
        this.clientSocket = clientSocket ;
    }
    
    @Override
    public void run() {
        try {
            processClientRequest();
        } catch (IOException e) {
            e.printStackTrace();
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
        DataInputStream dIn = new DataInputStream(clientSocket.getInputStream());
        DataOutputStream dOut = new DataOutputStream(clientSocket.getOutputStream()) ;
        
        boolean done = false;
        while(!done) {
            byte messageType = dIn.readByte();

            switch(messageType) {
                // TODO: Implement framework to deal with incoming connections
                case -1: //Client is a receive type client, and is therefore only after data
                    dOut.writeByte(-1);
                    dOut.writeUTF("This server session has received: " + " send-type client connections."); // Reduce connection count, as we're only interested in reporting send type client connections
                    dOut.flush();
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

}
