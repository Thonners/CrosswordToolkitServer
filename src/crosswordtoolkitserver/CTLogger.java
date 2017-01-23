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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class to handle logging client connections.
 * 
 * In the future might move to a more sophisticated (java.util.logging.)Logger or database solution.
 * For now just chuck it in a text file.
 * 
 * @author M Thomas
 * @since 09/01/17
 */
public class CTLogger {
    
    private static final String LOG_PATH = System.getProperty("user.home") + File.separator + "logfile" ;
    
    private File logFile = new File(LOG_PATH) ;
    
    // Logging levels
    public static final int SEVERE = 1 ;
    public static final int WARNING = 2 ;
    public static final int INFO = 3 ;
    public static final int CONFIG = 4 ;
    public static final int DEBUG = 5 ;
    
    private int logLevel ;
    
    /**
     * Constructor
     */
    public CTLogger(int logLevel) {
        this.logLevel = logLevel ;
        // Create log file if it doesn't already exist
        if (logFile.canWrite()) {
            if (!logFile.exists()) this.i("Log", "Initialising log file...");
        } else {
            System.err.println("Error writing to log file: " + logFile.getAbsolutePath());
        }
    }
    
    public void writeLog(int level, String classType, String logMessage) {
        // Only log the input if the logger's level is lower or equal to that of the importance of this message
        if (level <= logLevel) {
            // Try with resources
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true))) {
                String formattedMessage = getDateTime() + ":\t" + classType + ":\t" + logMessage + "\n" ;
                bw.append(formattedMessage) ;
            } catch (IOException e) {

            }
        }
    }
    
    /**
     * Log info
     * @param logTag Tag associated with class writing the message
     * @param logMessage The message to log
     */
    public void i(String logTag, String logMessage) {
        writeLog(INFO, logTag, logMessage) ;
    }
    
    /**
     * Log debugging info
     * @param logTag Tag associated with class writing the message
     * @param logMessage The message to log
     */
    public void d(String logTag, String logMessage) {
        writeLog(DEBUG, logTag, logMessage) ;        
    }
    
    /**
     * @return The current date and time, in the format: yyyy/MM/dd HH:mm:ss
     */
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return (dateFormat.format(date));
    }
    
}
