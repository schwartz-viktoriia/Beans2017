
package gradientsfx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author vschwartz
 */
public class GradientsFX extends Application {

    @Override
    public void start(Stage primaryStage) {

        VBox root = new VBox(10);
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setAlignment(Pos.CENTER);
        
        Stop[] stops = new Stop[] { new Stop(0, Color.YELLOWGREEN), new Stop(.5, Color.BLACK), new Stop(1, Color.RED)};
        LinearGradient lg1 = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
        
        Text text = new Text("Color me funny");
        text.setFill(lg1);
        try {
            Font myFont = Font.loadFont(new FileInputStream(new File("sewer.ttf")), 30);
            text.setFont(myFont);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        Rectangle rec = new Rectangle(200, 50);
        rec.setFill(lg1);
        
        TextField tf1 = new TextField();
        tf1.setStyle("-fx-text-fill: blue;");
        
        TextField tf2 = new TextField();
        tf2.setId("tf2");
        tf2.getStyleClass().add("text-input");
        
        root.getChildren().addAll(tf1, tf2, text, rec);

        Scene scene = new Scene(root, 300, 250);
        scene.getStylesheets().add(getClass().getResource("GradientFillTxtField.css").toExternalForm());

        primaryStage.setTitle("Fancy Fills");
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
