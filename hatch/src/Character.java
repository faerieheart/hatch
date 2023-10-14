import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javafx.scene.image.Image;

public class Character {
    private String name;
    private int id;
    private Image sprite;
    private int xCoord;
    private int yCoord;

    private final int WIDTH = 50;
    private final int HEIGHT = 100;

    public Character(String name, int id, String img_filepath, int xCoord, int yCoord) throws FileNotFoundException {
        this.name = name;
        this.id = id;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        InputStream in = new FileInputStream(img_filepath);
        this.sprite = new Image(in, WIDTH, HEIGHT, true, true);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Image getSprite() {
        return sprite;
    }

    public void setSprite(String fileName) throws FileNotFoundException{
        InputStream in = new FileInputStream(fileName);
        this.sprite = new Image(in, WIDTH, HEIGHT, true, true);
    }

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }
}
