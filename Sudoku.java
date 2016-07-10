
package sudoku;

import javafx.application.Application;
import javafx.event.ActionEvent;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 *
 * @author vschwartz
 */
public class Sudoku extends Application {

    final static TextField[][] cells = new TextField[9][9];
    
    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(15, 15, 15, 15));

        GridPane pane = new GridPane();
        GridPane[][]gp = new GridPane[3][3];
        for (int i = 0; i < 3; i++) 
            for (int j = 0; j < 3; j++) {
                gp[i][j] = new GridPane();
                gp[i][j].setPadding(new Insets(2, 2, 2, 2));
                gp[i][j].setStyle("-fx-border-color: purple");
                pane.add(gp[i][j], j, i);
            }
        
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new TextField();
                cells[i][j].setPrefColumnCount(1);
                gp[i / 3][j / 3].add(cells[i][j], j % 3, i % 3);
            }
        }
                      
        Button solveBtn = new Button();
        solveBtn.setText("Solve!");
        
        solveBtn.setOnAction(e -> {
            for (int i = 0; i < cells.length; i++)
                for (int j = 0; j < cells[i].length; j++) 
                    if (!cells[i][j].getText().isEmpty())
                        BetterBrain.cells[i][j] = Integer.parseInt(cells[i][j].getText());            
            
            BetterBrain.solveIt();
        });

        Button resetBtn = new Button("Clear");
        resetBtn.setOnAction((ActionEvent e) -> {
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    cells[i][j].setText("");
                    BetterBrain.cells[i][j] = 0;
                }
            }
        });
             
        StackPane sPane = new StackPane();
        sPane.getChildren().add(solveBtn);
        
        StackPane sPane2 = new StackPane();
        sPane2.getChildren().add(resetBtn);
        
        root.getChildren().addAll(pane, sPane, sPane2);
        
        Scene scene = new Scene(root);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Sudoku Solver");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
