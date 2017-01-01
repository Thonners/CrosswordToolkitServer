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
 * Unit testing for AnagramSolver class
 *
 * @author M Thomas
 * @since 01/01/17
 */
public class AnagramSolverNGTest {
    
    public AnagramSolverNGTest() {
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
     * Test of solveAnagram method, of class AnagramSolver.
     */
    @Test
    public void testSolveAnagram() {
        System.out.println("solveAnagram");
        AnagramSolver as = new AnagramSolver();
        
        // pat
        ArrayList patAnswer = new ArrayList<>() ;
        patAnswer.add("apt") ;
        patAnswer.add("pat") ;
        patAnswer.add("tap") ;
        
        // part
        ArrayList partAnswer = new ArrayList<>() ;
        partAnswer.add("part") ;
        partAnswer.add("prat") ;
        partAnswer.add("rapt") ;
        partAnswer.add("tarp") ;
        partAnswer.add("trap") ;
        
        // Tests
        assertNull(as.solveAnagram(""));
        assertNull(as.solveAnagram("jjj"));
        assertEquals(as.solveAnagram("tap"), patAnswer);
        assertEquals(as.solveAnagram("pat"), patAnswer);
        assertEquals(as.solveAnagram("tarp"), partAnswer);
        
        System.out.println("solveAnagram testing complete");
        
    }
    
}
