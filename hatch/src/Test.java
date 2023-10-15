import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.animation.KeyFrame;
import javafx.animation.*;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
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
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javafx.util.Duration;

public class Test extends Application {

    private final int STAGE_HEIGHT = 790;
    private final int STAGE_WIDTH = 1536;

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
        Group root = new Group();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        //draws two eggs
        Canvas canvas1 = new Canvas(STAGE_WIDTH, STAGE_HEIGHT);
        Canvas canvas2 = new Canvas(STAGE_WIDTH, STAGE_HEIGHT);
        GraphicsContext gc1 = canvas1.getGraphicsContext2D();
        GraphicsContext gc2 = canvas2.getGraphicsContext2D();
        root.getChildren().add(canvas1);
        root.getChildren().add(canvas2);
        /*
        try {
            FileInputStream input = new FileInputStream("hatch/src/egg.png");
            Image image = new Image(input);
            gc1.drawImage(image, STAGE_WIDTH/2, STAGE_HEIGHT/2, 30, 40);
            gc2.drawImage(image, STAGE_WIDTH/2 + 100, STAGE_HEIGHT/2, 30, 40);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        //draws text
        Label label = new Label("Press Enter to Start Game.");
        label.setWrapText(true);
        label.setMaxWidth(200);
        label.setTranslateX(STAGE_WIDTH/2);
        label.setTranslateY(STAGE_HEIGHT/2);
        root.getChildren().add(label);

        //waits for enter to be pressed, moves 1st egg right
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.ENTER) {
                gc1.clearRect(0, 0, STAGE_WIDTH, STAGE_HEIGHT);
                try {
                    FileInputStream in = new FileInputStream("hatch/src/egg.png");
                    Image image = new Image(in);
                    gc1.drawImage(image, STAGE_WIDTH/2 + 200, STAGE_HEIGHT/2, 30, 40);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        stage.show();

        //waits 1 sec, then shows egg
        try {
            FileInputStream in = new FileInputStream("hatch/src/egg.png");
            Image image = new Image(in);
            delay(1000, () -> gc2.drawImage(image, 300, STAGE_HEIGHT/2, 30, 40));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        */

        /*
        FileInputStream input;
        try {
            input = new FileInputStream("hatch/src/egg.png");
            Image img = new Image(input);
            ImageView heart = new ImageView(img);
            heart.setFitWidth(App.STAGE_HEIGHT * 0.75);
            heart.setFitHeight(App.STAGE_HEIGHT);

            animateUsingTimeline(heart);

            StackPane layout = new StackPane(heart);
            layout.setMaxSize(App.STAGE_WIDTH, App.STAGE_HEIGHT);
            layout.setMinSize(App.STAGE_WIDTH, App.STAGE_HEIGHT);

            Scene scene2 = new Scene(layout);
            stage.setScene(scene2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        stage.show();
        */

        ImageView imageView = new ImageView();
        List<Image> images = new ArrayList<>();
        try {
            FileInputStream in1 = new FileInputStream("hatch/src/egg1.png");
            FileInputStream in2 = new FileInputStream("hatch/src/egg2.png");
            FileInputStream in3 = new FileInputStream("hatch/src/egg3.png");
            images.add(new Image(in1));
            images.add(new Image(in2));
            images.add(new Image(in3));


            Timeline time = new Timeline(
                new KeyFrame(Duration.ZERO, e -> imageView.setImage(images.get(0))),
                new KeyFrame(Duration.seconds(0.5), e -> imageView.setImage(images.get(1))),
                new KeyFrame(Duration.seconds(1), e -> imageView.setImage(images.get(2))),
                new KeyFrame(Duration.seconds(1.5), e -> imageView.setImage(images.get(2)))
            );

            time.setCycleCount(1);
            time.play();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        imageView.setFitWidth(App.STAGE_HEIGHT * 0.75);
        imageView.setFitHeight(App.STAGE_HEIGHT);
        StackPane layout = new StackPane(imageView);
        layout.setMaxSize(App.STAGE_WIDTH, App.STAGE_HEIGHT);
        layout.setMinSize(App.STAGE_WIDTH, App.STAGE_HEIGHT);

        Scene scene2 = new Scene(layout);
        stage.setScene(scene2);
        stage.show();

    }

    private void animateUsingTimeline(ImageView heart) {
        DoubleProperty scale = new SimpleDoubleProperty(1);
        heart.scaleXProperty().bind(scale);
        heart.scaleYProperty().bind(scale);

        Timeline beat = new Timeline(
            new KeyFrame(Duration.ZERO,         event -> scale.setValue(0.1)),
            new KeyFrame(Duration.seconds(0.5), event -> scale.setValue(0.11)),
            new KeyFrame(Duration.seconds(1), event -> scale.setValue(0.12))
        );
        beat.setAutoReverse(true);
        beat.setCycleCount(1);
        beat.play();
    }

    public static void delay(long millis, Runnable continuation) {
      Task<Void> sleeper = new Task<Void>() {
          @Override
          protected Void call() throws Exception {
              try { Thread.sleep(millis); }
              catch (InterruptedException e) { }
              return null;
          }
      };
      sleeper.setOnSucceeded(event -> continuation.run());
      new Thread(sleeper).start();
    }
}
