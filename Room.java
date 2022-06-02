import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class Room {
    private int id;
    private int x, y, buildX, buildY;
    private int length, width;
    private ArrayList<Connector> connectors;

    Room(int id, int x, int y, int length, int width) {
        this.x = x;
        this.y = y;
        this.buildX = x;
        this.buildY = y;
        this.length = length;
        this.width = width;
        this.id = id;
    }

    // public void createContents(ArrayList<Entity> entities) {
    // String[] roomNames = { "flatFloor", "rectStarter", "secondFloor" };
    // String randomRoom = roomNames[randint(0, roomNames.length - 1)];
    // if (randomRoom.equals("rectStarter")) {
    // this.length = 600;
    // this.width = 400;
    // } else if (randomRoom.equals("flatFloor")) {
    // this.length = 1200;
    // this.width = 400;
    // } else if (randomRoom.equals("secondFloor")) {
    // this.length = 1200;
    // this.width = 600;
    // this.buildY = buildY - 200;
    // }

    // // MAKE OBJECTS OVER HERE DO NOT DO THIS EVER!!!!
    // if (randomRoom.equals("rectStarter")) {
    // entities.add(new Wall(x, y + 350, 600, 50, ""));
    // entities.add(new Wall(x, y, 600, 50, ""));
    // entities.add(new Wall(x, y, 50, 250, ""));
    // entities.add(new Wall(x + 550, y, 50, 250, ""));
    // entities.add(new Door(x + 580, y + 250, 20, 100, ""));
    // entities.add(new Door(x, y + 250, 20, 100, ""));
    // connectors.add(new Connector(x + 600, y));
    // } else if (randomRoom.equals("flatFloor")) {
    // entities.add(new Wall(x, y + 350, 1200, 50, ""));
    // entities.add(new Wall(x, y, 1200, 50, ""));
    // entities.add(new Wall(x, y, 50, 250, ""));
    // entities.add(new Wall(x + 1150, y, 50, 250, ""));
    // entities.add(new Door(x + 1180, y + 250, 20, 100, ""));
    // entities.add(new Door(x, y + 250, 20, 100, ""));
    // connectors.add(new Connector(x + 1200, y));
    // } else if (randomRoom.equals("secondFloor")) {
    // entities.add(new Wall(x, y + 350, 1200, 50, ""));
    // entities.add(new Wall(x, y - 200, 1200, 50, ""));
    // entities.add(new Wall(x, y - 200, 50, 450, ""));

    // entities.add(new Wall(x + 500, y + 150, 700, 250, ""));
    // entities.add(new Wall(x + 450, y + 200, 50, 150, ""));
    // entities.add(new Wall(x + 400, y + 250, 50, 100, ""));
    // entities.add(new Wall(x + 350, y + 300, 50, 50, ""));

    // entities.add(new Wall(x + 1150, y - 200, 50, 250, ""));
    // entities.add(new Wall(x + 1150, y + 200, 50, 200, ""));
    // entities.add(new Door(x + 1180, y + 50, 20, 100, ""));
    // entities.add(new Door(x, y + 250, 20, 100, ""));
    // connectors.add(new Connector(x + 1200, y - 200));
    // }
    // }

    public boolean rectRectDetect(Room rect, Room rect2) {
        double leftSide = rect.x;
        double rightSide = rect.x + rect.length;
        double topSide = rect.y;
        double botSide = rect.y + rect.width;
        if (rect2.x + rect2.length > leftSide && rect2.x < rightSide && rect2.y + rect2.width > topSide
                && rect2.y < botSide) {
            return true;
        }
        return false;
    }

    public static int randint(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }

    // getters
    public int getLength() {
        return this.length;
    }

    public Connector getConnector() {
        return this.connectors.get(0);
    }

}
