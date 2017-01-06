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

/**
 * Class to manage the storing and recollection of any cloud saves.
 * 
 * From the root directory, crossword grids are saved in a './grids/', 
 * whilst user data is stored in '/username/'.
 *
 * @author M Thomas
 * @since 06/01/17
 */
public class CrosswordLibraryManager {
    
    private final String rootPath = "/crosswordtoolkit" ;
    private final String gridsDir = "grids" ;
    
    public CrosswordLibraryManager() {
        
    }
    
    /**
     * @param crosswordTitleString The title of the crossword grid to be saved.
     * @param crosswordSaveString The saveString of the crossword.
     */
    public void saveNewGrid(String crosswordTitleString, String crosswordSaveString) {
        File gridFile = new File(rootPath + "/" + gridsDir + "/" + crosswordTitleString) ;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(gridFile))) {
            bw.write(crosswordSaveString);
        } catch (Exception e) {
            System.out.println("CLM: Error writing save file for new grid: " + crosswordTitleString + ".");
            e.printStackTrace();
        }        
    }
    
    /**
     * @param crosswordTitleString The save string of the new crossword to check whether it already exists on the system
     * @return Whether the crossword has been saved already
     */
    public boolean gridExists(String crosswordTitleString) {
        File gridFile = new File(rootPath + "/" + gridsDir + "/" + crosswordTitleString) ;
        return gridFile.exists() ;
    }
    
    /**
     * Method to write the received saveString to file for a user to back up their progress in the cloud.
     * 
     * @param username Username of the user saving their progress
     * @param crosswordTitleString The title (and therefore filename) of the crossword for which the progress is to be saved
     * @param crosswordSaveString The saveString of the crossword.
     * @return True if saving completed successfully, false if there was a problem.
     */
    public boolean saveUserProgress(String username, String crosswordTitleString, String crosswordSaveString) {
        String saveFilePath = rootPath + "/" + username + "/" + crosswordTitleString ;
        File saveFile = new File(saveFilePath) ;
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(saveFile))) {
            bw.write(crosswordSaveString);
            return true ;
        } catch (Exception e) {
            System.out.println("CLM: Error writing save file for user: " + username + ", whilst trying to save: " + crosswordTitleString);
            e.printStackTrace();
            return false ;
        }
    }
    
}
