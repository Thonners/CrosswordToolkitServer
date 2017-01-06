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

import java.util.ArrayList;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit testing for WordFitSolver class.
 * 
 * @author M Thomas
 * @since 05/01/17
 */
public class WordFitSolverNGTest {
    
    public WordFitSolverNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of solveWordFit method, of class WordFitSolver.
     */
    @Test
    public void testSolveWordFit() {
        System.out.println("solveWordFit");
        WordFitSolver instance = new WordFitSolver();
        
        // Test results
        ArrayList blankResult = instance.solveWordFit("");
        ArrayList failedResult = instance.solveWordFit(".zzzzzzzz");
        ArrayList applResult = instance.solveWordFit("appl.");
        ArrayList beetrootResult = instance.solveWordFit(".eetroo.");
        
        // Expected results
        ArrayList nullResult = null;
        
        ArrayList applExpResult = new ArrayList<>() ;
        applExpResult.add("apple") ;
        applExpResult.add("apply") ;
        
        ArrayList beetrootExpResult = new ArrayList<>() ;
        beetrootExpResult.add("beetroot") ;
        
        assertEquals(blankResult, nullResult);
        assertEquals(failedResult, nullResult);
        assertEquals(applResult, applExpResult);
        assertEquals(beetrootResult, beetrootExpResult);
    }
    
}
