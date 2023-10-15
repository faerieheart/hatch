import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.paint.Color;
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
        if (this.id == 15 && App.girlChar) {
            this.scenePointers = new int[1];
            this.scenePointers[0] = 16;
            return;
        } else if (this.id == 15) {
            this.scenePointers = new int[1];
            this.scenePointers[0] = 21;
            return;
        }

        String[] scenePointStrArr = scenePointersStr.split(", ");
        this.scenePointers = new int[scenePointStrArr.length];

        for(int i = 0; i < this.scenePointers.length; i++) {
            this.scenePointers[i] = Integer.valueOf(scenePointStrArr[i]);
        }
    }

    private void readFromTextFile() {
        File file = new File("hatch/src/Text.txt");
        Scanner sc;
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
        if (this.id != 51) {
            drawBarometers();
            drawBox();
            drawText();
        }

        if (textChoice) {
            this.scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
                if (key.getCode() == KeyCode.DIGIT1 && this.id == 2) {
                    nextScene(this.scenePointers[0]);
                    App.girlChar = false;
                    App.baromVals[0] -= 10;
                } else if (key.getCode() == KeyCode.DIGIT2 && this.id == 2) {
                    nextScene(this.scenePointers[0]);
                    App.girlChar = true;
                    App.baromVals[0] += 10;
                } else if (key.getCode() == KeyCode.DIGIT1) {
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
                if (key.getCode() == KeyCode.ENTER) {
                    nextScene(this.scenePointers[0]);
                }
            });
        }

        this.stage.setScene(this.scene);
        this.stage.show();
    }

    public void nextScene(int newId) {
        checkNewId(newId);
        if (newId == 0) {
            System.exit(0);
        }
        if (endOfSection) {
            ImageView imageView = new ImageView();
            animateUsingTimeline(imageView, newId);

            imageView.setFitWidth(App.STAGE_WIDTH);
            imageView.setFitHeight(App.STAGE_HEIGHT);
            StackPane layout = new StackPane(imageView);
            layout.setMaxSize(App.STAGE_WIDTH, App.STAGE_HEIGHT);
            layout.setMinSize(App.STAGE_WIDTH, App.STAGE_HEIGHT);

            Scene scene2 = new Scene(layout);
            stage.setScene(scene2);
            this.stage.show();
        } else {
            createNextScene(newId);
        }
    }

    private void animateUsingTimeline(ImageView imageView, int newId) {
        List<Image> images = new ArrayList<>();
        try {
            FileInputStream in1 = new FileInputStream("hatch/src/egg1.png");
            FileInputStream in2 = new FileInputStream("hatch/src/egg2.png");
            FileInputStream in3 = new FileInputStream("hatch/src/egg3.png");
            FileInputStream in4 = new FileInputStream("hatch/src/egg4.png");
            images.add(new Image(in1));
            images.add(new Image(in2));
            images.add(new Image(in3));
            images.add(new Image(in4));

            Timeline time = new Timeline(
                new KeyFrame(Duration.ZERO, e -> imageView.setImage(images.get(0))),
                new KeyFrame(Duration.seconds(0.5), e -> imageView.setImage(images.get(1))),
                new KeyFrame(Duration.seconds(1), e -> imageView.setImage(images.get(2))),
                new KeyFrame(Duration.seconds(1.5), e -> imageView.setImage(images.get(3))),
                new KeyFrame(Duration.seconds(2), e -> imageView.setImage(images.get(3)))
            );

            time.setCycleCount(1);
            time.setOnFinished(e -> createNextScene(newId));
            time.play();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
            FileInputStream input3 = new FileInputStream("hatch/src/barometerLabels.png");
            Image image3 = new Image(input3);
            gc.drawImage(image3, 0, 0, App.STAGE_WIDTH, App.STAGE_HEIGHT);

            FileInputStream input1 = new FileInputStream(App.BAROM_FILES[0] + App.baromVals[0] + ".png");
            Image image1 = new Image(input1);
            gc.drawImage(image1, 0, 0, App.STAGE_WIDTH, App.STAGE_HEIGHT);

            FileInputStream input2 = new FileInputStream(App.BAROM_FILES[1] + App.baromVals[1] + ".png");
            Image image2 = new Image(input2);
            gc.drawImage(image2, 0, 0, App.STAGE_WIDTH, App.STAGE_HEIGHT);
        } catch(FileNotFoundException e) {
            //do nothing
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
            gc.drawImage(image, 0, 0, App.STAGE_WIDTH, App.STAGE_HEIGHT);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        this.root.getChildren().add(canvas);
    }

    public void drawText() {
        Label label = new Label(this.text);
        Font font = App.loadFont(23);
        label.setFont(font);
        label.setTextFill(Color.rgb(245, 243, 239));
        label.setWrapText(true);
        label.setMaxWidth(App.BOX_WIDTH - App.TEXT_OFFSET[0] * 2);
        label.setTranslateX(App.BOX_X + App.TEXT_OFFSET[0]);
        label.setTranslateY(App.BOX_Y + App.TEXT_OFFSET[1]);
        this.root.getChildren().add(label);
    }
    
    public void checkNewId(int newId) {
        if (newId == 6) {
            updateBV(10, -10);
        } else if (newId == 8) {
            updateBV(-10, 10);
        } else if (newId == 20) {
            updateBV(0, -10);
        } else if (newId == 19) {
            updateBV(0, -20);
        } else if (newId == 27) {
            updateBV(-10, 0);
        } else if (newId == 28) {
            updateBV(10, 0);
        } else if (newId == 35) {
            updateBV(-10, 10);
        } else if (newId == 40) {
            updateBV(10, -10);
        } else if (newId == 36 && this.id == 38) {
            updateBV(-10, 0);
        } else if (newId == 47) {
            updateBV(10, -10);
        } else if (newId == 48) {
            updateBV(-10, 10);
        }
    }

    public void updateBV(int euph, int accept) {
        App.baromVals[0] += euph;
        App.baromVals[1] += accept;
        if (App.baromVals[0] < 0) {
            App.baromVals[0] = 0;
        } else if (App.baromVals[0] > 90) {
            App.baromVals[0] = 90;
        }
       if (App.baromVals[1] < 10) {
            App.baromVals[1] = 10;
        } else if (App.baromVals[1] > 100) {
            App.baromVals[1] = 100;
        }
        System.out.println("Euph: " + App.baromVals[0] + " Accept: " + App.baromVals[1]);
    }
}
