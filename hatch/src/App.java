import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    private final int STAGE_HEIGHT = 400;
    private final int STAGE_WIDTH = 800;

    private Scene startScene;
    private Scene gameScene;
    private Scene settingScene;
    private Scene endScene;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Start Screen");
        startScene(stage);
    }

    public void startScene(Stage stage) {
        /*
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("next scene");
                gameStart(stage);
            }
        });
        */
        StackPane root = new StackPane();
        //root.getChildren().add(btn);
        stage.setScene(new Scene(root, STAGE_WIDTH, STAGE_HEIGHT));
        stage.show();
    }

    public void gameStart(Stage stage) {
    }
    
}
