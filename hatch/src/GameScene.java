import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameScene {

    private int id;
    private String bgkdFile;
    private int[] scenePointers;
    private boolean textChoice;
    private String text;
    private boolean endOfSection;
    private boolean speaking;
    private boolean charOnLeft;

    private Scene scene;
    private Group root;
    private Stage stage;

    public GameScene(int id, Stage stage) {
        this.id = id;
        this.stage = stage;
        this.root = new Group();
        this.scene = new Scene(this.root);
        readFromSceneFile();
        readFromTextFile();
        drawScene(this.stage);
    }

    private void readFromSceneFile() {
        File file = new File("hatch/src/Scene.txt");
        Scanner sc;
        String scenePointStr;
        try {
            sc = new Scanner(file);
            int i = 1;
            String line;
            while (sc.hasNextLine() && i < 5) {
                line = sc.nextLine();
                if (!line.equals("id: " + this.id) && i == 1) {
                    continue;
                }
                String in = line.substring(line.indexOf(":") + 2);
                switch (i) {
                    case 1:
                        i++;
                        break;
                    case 2:
                        this.bgkdFile = in;
                        i++;
                        break;
                    case 3:
                        scenePointStr = in;
                        createScenePointerArr(scenePointStr);
                        i++;
                        break;
                    case 4:
                        if (in.equals("F")) {
                            this.endOfSection = false;
                        } else {
                            this.endOfSection = true;
                        }
                        i++;
                        break;
                    default:
                        System.out.println("Error in instantiating scene from file");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createScenePointerArr(String scenePointersStr) {
        String[] scenePointStrArr = scenePointersStr.split(", ");
        this.scenePointers = new int[scenePointStrArr.length];

        for(int i = 0; i < this.scenePointers.length; i++) {
            this.scenePointers[i] = Integer.valueOf(scenePointStrArr[i]);
        }
    }

    private void readFromTextFile() {
        File file = new File("hatch/src/Text.txt");
        Scanner sc;
        String scenePointStr;
        try {
            sc = new Scanner(file);
            int i = 1;
            String line;
            while (sc.hasNextLine() && i < 6) {
                line = sc.nextLine();
                if (!line.contains("id: " + this.id) && i == 1) {
                    continue;
                }
                String in = line.substring(line.indexOf(":") + 2);
                switch (i) {
                    case 1:
                        i++;
                        break;
                    case 2:
                        this.text = in;
                        i++;
                        break;
                    case 3:
                        if (in.equals("F")) {
                            this.speaking = false;
                        } else {
                            this.speaking = true;
                        }
                        i++;
                        break;
                    case 4:
                        if (in.equals("F")) {
                            this.textChoice = false;
                        } else {
                            this.textChoice = true;
                        }
                        i++;
                        break;
                    case 5:
                        if (in.equals("F")) {
                            this.charOnLeft = false;
                        } else {
                            this.charOnLeft = true;
                        }
                        i++;
                        break;
                    default:
                        System.out.println("Error in instantiating text from file");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void drawScene(Stage stage) {
        drawBackground();
        drawBarometers();
        drawBox();
        drawText();

        if (id == 15) {
            this.scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
                if (key.getCode() == KeyCode.DIGIT1) {
                    nextScene(this.scenePointers[0]);
                }
            });
        } else if (textChoice) {
            this.scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
                if (key.getCode() == KeyCode.DIGIT1) {
                    nextScene(this.scenePointers[0]);
                } else if (key.getCode() == KeyCode.DIGIT2) {
                    nextScene(this.scenePointers[1]);
                } else if (key.getCode() == KeyCode.DIGIT3) {
                    if (this.scenePointers.length > 2) {
                        nextScene(this.scenePointers[2]);
                    }
                }

            });
        } else {
            this.scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
                if (key.getCode() == KeyCode.DIGIT1) {
                    nextScene(this.scenePointers[0]);
                }
        });
        }

        this.stage.setScene(this.scene);
        this.stage.show();
    }

    public void nextScene(int newId) {
        if (newId == 0) {
            System.exit(0);
        }
        if (endOfSection) {
            FileInputStream input;
            try {
                input = new FileInputStream("hatch/src/egg.png");
                Image img = new Image(input);
                ImageView heart = new ImageView(img);
                heart.setFitWidth(App.STAGE_HEIGHT * 0.75);
                heart.setFitHeight(App.STAGE_HEIGHT);

                animateUsingTimeline(heart, newId);

                StackPane layout = new StackPane(heart);
                layout.setMaxSize(App.STAGE_WIDTH, App.STAGE_HEIGHT);
                layout.setMinSize(App.STAGE_WIDTH, App.STAGE_HEIGHT);

                Scene scene2 = new Scene(layout);
                stage.setScene(scene2);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            this.stage.show();
        } else {
            createNextScene(newId);
        }
    }

    private void animateUsingTimeline(ImageView heart, int newId) {
            DoubleProperty scale = new SimpleDoubleProperty(1);
            heart.scaleXProperty().bind(scale);
            heart.scaleYProperty().bind(scale);

            Timeline beat = new Timeline(
                new KeyFrame(Duration.ZERO,         event -> scale.setValue(0.1)),
                new KeyFrame(Duration.seconds(0.5), event -> scale.setValue(0.15)),
                new KeyFrame(Duration.seconds(1), event -> scale.setValue(0.2)),
                new KeyFrame(Duration.seconds(1.5), event -> scale.setValue(0.25)),
                new KeyFrame(Duration.seconds(2), event -> scale.setValue(0.25))
            );
            beat.setCycleCount(1);
            beat.setOnFinished(e -> createNextScene(newId));
            beat.play();
    }

    public void createNextScene(int newId) {
        GameScene nxt = new GameScene(newId, this.stage);
    }

    public void drawBackground() {
        Canvas canvas = new Canvas(App.STAGE_WIDTH, App.STAGE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        try {
            FileInputStream input = new FileInputStream("hatch/src/" + bgkdFile);
            Image image = new Image(input);
            gc.drawImage(image, 0, 0, App.STAGE_WIDTH, App.STAGE_HEIGHT);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        this.root.getChildren().add(canvas);
    }

    public void drawBarometers() {
        Canvas canvas = new Canvas(App.STAGE_WIDTH, App.STAGE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        try {
            FileInputStream input1 = new FileInputStream(App.BAROM_FILES[0] + App.baromVals[0] + ".png");
            Image image1 = new Image(input1);
            gc.drawImage(image1, App.BAROM_POS[0], App.BAROM_POS[1], App.BAROM_DIM[0], App.BAROM_DIM[1]);

            FileInputStream input2 = new FileInputStream(App.BAROM_FILES[1] + App.baromVals[1] + ".png");
            Image image2 = new Image(input2);
            gc.drawImage(image2, App.BAROM_POS[2], App.BAROM_POS[1], App.BAROM_DIM[0], App.BAROM_DIM[1]);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        this.root.getChildren().add(canvas);
    }

    public void drawBox() {
        Canvas canvas = new Canvas(App.STAGE_WIDTH, App.STAGE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        try {
            FileInputStream input;
            if (speaking && charOnLeft) {
                input = new FileInputStream(App.SPEAK_BOX_L);
            } else if (speaking & !charOnLeft) {
                input = new FileInputStream(App.SPEAK_BOX_R);
            } else {
                input = new FileInputStream(App.THINK_BOX);
            }
            Image image = new Image(input);
            gc.drawImage(image, App.BOX_X, App.BOX_Y, App.BOX_WIDTH, App.BOX_HEIGHT);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        this.root.getChildren().add(canvas);
    }

    public void drawText() {
        Label label = new Label(this.text);
        Font font = App.loadFont(23);
        label.setFont(font);
        label.setWrapText(true);
        label.setMaxWidth(App.BOX_WIDTH - App.TEXT_OFFSET[0] * 2);
        if (!speaking) {
            label.setTranslateX(App.BOX_X + App.TEXT_OFFSET[0]);
            label.setTranslateY(App.BOX_Y + App.TEXT_OFFSET[0]);
        } else {
            label.setTranslateX(App.BOX_X + App.TEXT_OFFSET[0]);
            label.setTranslateY(App.BOX_Y + App.TEXT_OFFSET[1]);
        }
        this.root.getChildren().add(label);
    }
    
}
