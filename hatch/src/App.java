import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    private final int STAGE_HEIGHT;
    private final int STAGE_WIDTH;

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
        
        HatchScene start = new HatchScene(0, STAGE_WIDTH, STAGE_HEIGHT);
        stage.setScene(start.getScene());
        Button btn = new Button();
        btn.setText("Start Game");
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("next scene");
                switchScene(stage);
            }
        });
        stage.show();
    }

    public void startScene(Stage stage, ) {
        Group root = new Group();
        stage.setScene(new Scene(root, STAGE_WIDTH, STAGE_HEIGHT));
        stage.show();
    }
    
}
