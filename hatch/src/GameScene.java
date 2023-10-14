import javafx.event.EventHandler;

import java.io.FileInputStream;

import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GameScene extends HatchScene {

    private Image background;

    public GameScene(int id, Stage stage, int width, int height) {
        super(id, stage, width, height);
        InputStream in = new FileInputStream("null")
        this.background = new Image(null, width, height, false, false, false)
    }

    public GameScene(int id, Stage stage, int width, int height, Image background) {
        super(id, stage, width, height);
        this.background = background;
    }

    public
    


    
}
