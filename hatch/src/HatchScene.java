import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HatchScene {

    int id;
    private StackPane root;
    private Scene scene;
    private Stage stage;
    private int width;
    private int height;
    private GameScene game;
    
    public HatchScene(int id, Stage stage, int width, int height) {
        this.id = id;
        this.width = width;
        this.height = height;

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
        stage.setScene(this.scene);
        stage.show();
    }

    private void endScene() {
        this.root = new StackPane();
    }

    private void settingScene() {
        this.root = new StackPane();
    }

    private void gameScene() {
        this.game = new GameScene(id, stage, width, height);
        this.root = game.getRoot();
    }

    private void startScene() {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("next scene");
                switchScene(stage, 1);
            }
        });
        this.root = new StackPane();
        root.getChildren().add(btn);
    }

    public Scene getScene() {
        return this.scene;
    }

    public StackPane getRoot() {
        return this.root;
    }

    private void switchScene(Stage stage, int id) {
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
        this.scene = new Scene(root, this.width, this.height);
        stage.setScene(this.scene);
        stage.show();
    }

}
