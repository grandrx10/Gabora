import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class Door extends Wall {
    Door(int x, int y, int length, int width, String picName) {
        super(x, y, length, width, picName);
        super.setType("Door");
    }

    @Override
    public void interact(Entity interactor) {
        this.setYSpeed(-10);
    }

    @Override
    public void update(ArrayList<Entity> entities) {
        this.setX(this.getX() + this.getXSpeed());
        this.setY(this.getY() + this.getYSpeed());
        super.update(entities);
    }

}
