import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class Door extends Wall {
    private Sound doorOpenSound = new Sound("audio/doorOpenSound.wav");

    Door(int x, int y, int length, int width, String picName) {
        super(x, y, length, width, picName);
        super.setType("Door");
    }

    @Override
    public void interact(Entity interactor, Map map, ArrayList<Entity> entities, Music music) {
        this.setYSpeed(-10);
        doorOpenSound.start();
    }

    @Override
    public void update(ArrayList<Entity> entities, ArrayList<Bullet> bullets, SlowmoTracker slowmoTracker) {
        this.setX(this.getX() + this.getXSpeed() * slowmoTracker.getActiveSlowAmount());
        this.setY(this.getY() + this.getYSpeed() * slowmoTracker.getActiveSlowAmount());
        super.update(entities, bullets, slowmoTracker);
    }

}
