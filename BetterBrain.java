
package sudoku;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 *
 * @author vschwartz
 */
public class BetterBrain {
    static int[][] cells = new int[9][9];
    
//    static int[][] cells = {{0, 0, 0, 0, 0, 0, 0, 0, 0},
//                            {0, 0, 5, 4, 2, 8, 1, 0, 0},
//                            {0, 6, 0, 0, 0, 0, 0, 2, 0},
//
//                            {0, 1, 0, 0, 0, 0, 0, 5, 0}, 
//                            {0, 9, 0, 0, 0, 0, 0, 4, 0}, 
//                            {0, 8, 0, 0, 0, 0, 0, 3, 0}, 
//
//                            {0, 7, 0, 0, 0, 0, 0, 9, 0}, 
//                            {0, 0, 4, 1, 8, 2, 6, 0, 0}, 
//                            {0, 0, 0, 0, 0, 0, 0, 0, 0}};
    
    static void solveIt() {

        boolean found = false;
        int firstI = 0, firstJ = 0;
        for (int i = 0; i < 9 && !found; i++) 
            for (int j = 0; j < 9 && !found; j++)
                if (cells[i][j] == 0) {
                    found = true;
                    firstI = i;
                    firstJ = j;
                }
        
         backtrack(firstI, firstJ);
         
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++) 
                if (cells[i][j] != 0)
                    Sudoku.cells[i][j].setText(cells[i][j] + "");
                else Sudoku.cells[i][j].setText("");
        
    }
  
    static boolean backtrack(int currentI, int currentJ){

        if (currentI == 9 && currentJ == 9){ // Solution found
            return true;
        }
        
        for (int x = 1; x < 10; x++) {
                    // ------------- animation ------------
//            new Thread(new Runnable() {
//                                    @Override
//                public void run() {
//                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
////                    Platform.runLater(() -> {
////                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
////                        Sudoku.cells[currentI][currentJ].setText(cells[currentI][currentJ] + "");
////                                });
////                    try {
////                        Thread.sleep(100);
////                    } catch (InterruptedException ex) {
////                        Logger.getLogger(BetterBrain.class.getName()).log(Level.SEVERE, null, ex);
////                    }
//                }                  
//            }).start();
                
      //      try {

         // --------------------------------
            
            int n = 9;
            boolean gridIsValid = true;
            
            for (int i = 0; i < n && gridIsValid; i++) 
                if (cells[i][currentJ] == x)
                    gridIsValid = false;
            
            for (int j = 0; j < n && gridIsValid; j++) 
                if (cells[currentI][j] == x)
                    gridIsValid = false;
             
            for (int i = currentI - currentI % 3; i < currentI - currentI % 3 + 3 && gridIsValid; i++)
                for (int j = currentJ - currentJ % 3; j < currentJ - currentJ % 3 + 3 && gridIsValid; j++) 
                    if (cells[i][j] == x)
                        gridIsValid = false;
            
            if (gridIsValid){
                cells[currentI][currentJ] = x;
                boolean found = false;
                int nextI = 0, nextJ = 0;
                int i = currentI, j = currentJ + 1;
                
                while (i < n && !found) { 
                    if (i != currentI) j = 0;
                    while ( j < n && !found) {
                        if (cells[i][j] == 0) {
                            found = true;
                            nextI = i;
                            nextJ = j;
                        }
                        j++;
                    }
                    i++;
                }
                
                if (!found) {
                    nextI = nextJ = 9;
                }

                if (backtrack(nextI, nextJ)){ // Move to next empty cell
                    return true; // Empty cells filled. Solution found.
                }
            }
        }
        
        cells[currentI][currentJ] = 0; 
        return false; //Solution not found. 
    }
}
