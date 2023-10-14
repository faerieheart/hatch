import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class HatchScene {

    int id;
    private StackPane root;
    private Scene scene;
    
    public HatchScene(int id, int width, int height) {
        this.id = id;
        if (id == 0) {
            startScene();
        } else if (id == 1) {
            gameScene();
        } else if (id == 2) {
            settingScene();
        } else if (id == 3) {
            endScene();
        }
        this.scene = new Scene(root, width, height);
    }

    private void endScene() {
        this.root = new StackPane();
    }

    private void settingScene() {
        this.root = new StackPane();
    }

    private void gameScene() {
        this.root = new StackPane();
    }

    private void startScene() {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("next scene");
                switchScene();
            }
        });
        this.root = new StackPane();
        root.getChildren().add(btn);
    }

    public Scene getScene() {
        return this.scene;
    }
}
