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

    public static final int BOX_X = 75;
    public static final int BOX_Y = 580;
    public static final int BOX_HEIGHT = BOX_Y-75;
    public static final int BOX_WIDTH = 1224-BOX_X;

    public static final int[] TEXT_OFFSET = {BOX_WIDTH/20, BOX_HEIGHT/20};

    public static final String THINK_BOX = "hatch/src/thinkingbox.png";
    public static final String SPEAK_BOX_L = "hatch/src/speakingboxL.png";
    public static final String SPEAK_BOX_R = "hatch/src/speakingboxR.png";

    public static final int[] BAROM_DIM = {100, STAGE_HEIGHT - 50};
    public static final int[] BAROM_POS = {STAGE_WIDTH - BAROM_DIM[0] - BAROM_DIM[0] - 20, 10, STAGE_WIDTH - BAROM_DIM[0] - 10};
    public static int[] baromVals = {30, 60};
    public static final String[] BAROM_FILES = {"hatch/src/euph", "hatch/src/accept"};
    
    public static boolean girlChar;

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

        //draws start screen
        Canvas canvas = new Canvas(STAGE_WIDTH, STAGE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        try {
            FileInputStream input = new FileInputStream("hatch/src/start.png");
            Image image = new Image(input);
            gc.drawImage(image, 0, 0, STAGE_WIDTH, STAGE_HEIGHT);
            root.getChildren().add(canvas);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        //draws text
        Font font1 = loadFont(35);
        Label label1 = new Label("Press Enter to Start Game.");
        label1.setFont(font1);
        label1.setWrapText(true);
        label1.setMaxWidth(STAGE_WIDTH/2 - 250);
        label1.setTranslateX(10);
        label1.setTranslateY(STAGE_HEIGHT - 150);

        Font font2 = loadFont(25);
        Label label2 = new Label("After the game starts, press enter to move to the next screen if there are no choices to make.");
        label2.setFont(font2);
        label2.setWrapText(true);
        label2.setMaxWidth(STAGE_WIDTH/2 - 250);
        label2.setTranslateX(10);
        label2.setTranslateY(STAGE_HEIGHT - 100);

        root.getChildren().add(label1);
        root.getChildren().add(label2);

        //starts game
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.ENTER) {
                gameStart(stage);
            }
        });
        stage.show();
    }

    /*
     * Starts gameplay by creating first game screen
     */
    public static void gameStart(Stage stage) {
        GameScene start = new GameScene(1, stage);
    }

    /*
     * Loads custom font given size
     */
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
