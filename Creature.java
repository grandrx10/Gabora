import java.awt.Graphics;
import java.awt.Color;
import java.io.File;
import java.util.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
// possible exceptions
import java.io.IOException;

public class Creature extends Entity {
    private int hp;
    private int maxHp;
    private double runAccel;
    private double jumpSpeed;
    private boolean canJump = true;

    Creature(int x, int y, int length, int width, String picName, int rows, int columns) {
        super(x, y, length, width, picName, rows, columns);
        super.setType("Creature");
        super.setGravity(0.2);
        hp = 100; // 100 by default
        maxHp = hp;
    }

    @Override
    public void draw(Graphics g, int xRange, int yRange) {
        g.setColor(Color.black);
        g.fillRect((int) super.getX() - 2 - xRange, (int) super.getY() - 22 - yRange, super.getLength() + 4, 14);
        g.setColor(Color.green);
        g.fillRect((int) super.getX() - xRange, (int) super.getY() - 20 - yRange, super.getLength(), 10);

        super.draw(g, xRange, yRange);
    }

    @Override
    public void move(String dir) {
        if (dir.equals("right")) {
            super.setXAccel(runAccel);
            super.setDirection("right");
        } else if (dir.equals("left")) {
            super.setXAccel(-runAccel);
            super.setDirection("left");
        } else {
            super.setXAccel(0);
        }
    }

    @Override
    public void jump() {
        if (this.canJump) {
            this.canJump = false;
            super.setYSpeed(-jumpSpeed);
        }
    }

    @Override
    public void update(ArrayList<Entity> entities) {
        super.setX((super.getX() + super.getXSpeed()));
        for (int i = 0; i < entities.size(); i++) {
            if (!this.equals(entities.get(i)) && super.rectRectDetect(this, entities.get(i))) {
                super.setX((super.getX() - super.getXSpeed()));
                super.setXSpeed(0);
            }
        }

        super.setY((super.getY() + super.getYSpeed()));
        for (int i = 0; i < entities.size(); i++) {
            if (!this.equals(entities.get(i)) && super.rectRectDetect(this, entities.get(i))) {
                super.setY((super.getY() - super.getYSpeed()));
                super.setYSpeed(0);
                if (super.getX() > entities.get(i).getX()) {
                    this.canJump = true;
                }
            }
        }

        // deaccelerate objects due to gravity
        if (super.getXSpeed() > this.runAccel * 10) {
            super.setXSpeed(this.runAccel * 10);
        } else if (super.getXSpeed() < -this.runAccel * 10) {
            super.setXSpeed(-this.runAccel * 10);
        }

        if (super.getXSpeed() > 0.5) {
            super.setXSpeed(super.getXSpeed() - 0.5);
        } else if (super.getXSpeed() < -0.5) {
            super.setXSpeed(super.getXSpeed() + 0.5);
        } else {
            super.setXSpeed(0);
        }

        super.update(entities);
    }

    // getters
    public double getRunAccel() {
        return this.runAccel;
    }

    public double getJumpSpeed() {
        return this.jumpSpeed;
    }

    // setters
    public void setRunAccel(double runAccel) {
        this.runAccel = runAccel;
    }

    public void setJumpSpeed(double jumpSpeed) {
        this.jumpSpeed = jumpSpeed;
    }

}
