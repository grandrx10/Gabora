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
    private int attackCooldown = 500;
    private boolean canAttack = true;
    private long lastAttack = System.currentTimeMillis();

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
        g.fillRect((int) super.getX() - xRange, (int) super.getY() - 20 - yRange,
                (int) (1.0 * this.hp / this.maxHp * super.getLength()), 10);

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
    public void update(ArrayList<Entity> entities, ArrayList<Bullet> bullets, SlowmoTracker slowmoTracker) {
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

        super.update(entities, bullets, slowmoTracker);

        super.setX((super.getX() + super.getXSpeed() * slowmoTracker.getActiveSlowAmount()));
        for (int i = 0; i < entities.size(); i++) {
            if (!this.equals(entities.get(i)) && super.rectRectDetect(this, entities.get(i))
                    && !entities.get(i).getType().equals("Blood") && !entities.get(i).getType().equals("Platform")) {
                super.setX((super.getX() - super.getXSpeed() * slowmoTracker.getActiveSlowAmount()));
                super.setXSpeed(0);
            }
        }

        super.setY((super.getY() + super.getYSpeed() * slowmoTracker.getActiveSlowAmount()));
        for (int i = 0; i < entities.size(); i++) {
            if (!this.equals(entities.get(i)) && super.rectRectDetect(this, entities.get(i))
                    && !entities.get(i).getType().equals("Blood")) {
                if (entities.get(i).getType().equals("Platform")) {
                    if (this.getY() + this.getWidth() < entities.get(i).getY() + 10) {
                        super.setY(entities.get(i).getY() - super.getWidth());
                        super.setYSpeed(0);
                        if (super.getY() < entities.get(i).getY()) {
                            this.canJump = true;
                        }
                    }
                } else {
                    super.setY((super.getY() - super.getYSpeed() * slowmoTracker.getActiveSlowAmount()));
                    super.setYSpeed(0);
                    if (super.getY() < entities.get(i).getY()) {
                        this.canJump = true;
                    }
                }
            }
        }

        if (this.hp <= 0) {
            for (int i = 0; i < 10; i++) {
                entities.add(new Blood((int) this.getX() + this.getLength() / 2,
                        (int) this.getY() + this.getWidth() / 2,
                        randint(-20, 20), randint(-30, 0)));
            }
            entities.remove(this);
        }
    }

    @Override
    public void takeDamage(int damage) {
        this.hp -= damage;
    }

    // getters
    public double getRunAccel() {
        return this.runAccel;
    }

    public double getJumpSpeed() {
        return this.jumpSpeed;
    }

    public boolean getCanJump() {
        return this.canJump;
    }

    public boolean getCanAttack() {
        return this.canAttack;
    }

    public int getAttackCooldown() {
        return attackCooldown;
    }

    public long getLastAttack() {
        return lastAttack;
    }

    // setters
    public void setRunAccel(double runAccel) {
        this.runAccel = runAccel;
    }

    public void setJumpSpeed(double jumpSpeed) {
        this.jumpSpeed = jumpSpeed;
    }

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    public void setLastAttack(long lastAttack) {
        this.lastAttack = lastAttack;
    }
}
