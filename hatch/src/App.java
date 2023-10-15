import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application {

    public static final int STAGE_HEIGHT = 790;
    public static final int STAGE_WIDTH = 1536;
    public static final int BOX_HEIGHT = 200;
    public static final int BOX_WIDTH = 900;
    public static final int BOX_X = 50;
    public static final int BOX_Y = STAGE_HEIGHT - BOX_HEIGHT - 50;
    public static final int[] TEXT_OFFSET = {35, 75};
    public static final String THINK_BOX = "hatch/src/thinkingbox.png";
    public static final String SPEAK_BOX_L = "hatch/src/speakingboxL.png";
    public static final String SPEAK_BOX_R = "hatch/src/speakingboxR.png";
    public static final int[] BAROM_DIM = {100, STAGE_HEIGHT - 50};
    public static final int[] BAROM_POS = {STAGE_WIDTH - BAROM_DIM[0] - BAROM_DIM[0] - 20, 10, STAGE_WIDTH - BAROM_DIM[0] - 10};
    public static int[] baromVals = {25, 50};
    public static final String[] BAROM_FILES = {"hatch/src/temp", "hatch/src/temp"};

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Start Screen");
        startScene(stage);
    }

    public void startScene(Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        //draws two eggs
        Canvas canvas = new Canvas(STAGE_WIDTH, STAGE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        try {
            FileInputStream input = new FileInputStream("hatch/src/transWallpaper.png");
            Image image = new Image(input);
            gc.drawImage(image, 0, 0, STAGE_WIDTH, STAGE_HEIGHT);
            root.getChildren().add(canvas);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        //draws text
        Label label = new Label("Press Enter to Start Game.");
        Font font = loadFont(45);
        label.setFont(font);
        label.setWrapText(true);
        label.setMaxWidth(STAGE_WIDTH);
        label.setTranslateX(50);
        label.setTranslateY(STAGE_HEIGHT - 100);
        root.getChildren().add(label);

        //waits for enter to be pressed, moves 1st egg right
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.ENTER) {
                gameStart(stage);
            }
        });
        stage.show();
    }

    public static void gameStart(Stage stage) {
        GameScene start = new GameScene(1, stage);
    }

    public static Font loadFont(int size) {
        InputStream fontStream = App.class.getResourceAsStream("fonts/Monday Rain.ttf");
        Font font = Font.loadFont(fontStream, size);
        try {
            fontStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return font;
    }
    
}
