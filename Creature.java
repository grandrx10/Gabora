import java.awt.Graphics;
import java.awt.Color;
import java.io.File;
import java.util.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
// possible exceptions
import java.io.IOException;

public class Creature extends Entity {
    private double hp;
    private double maxHp;
    private double runAccel;
    private double jumpSpeed;
    private int jumps = 1;
    private int maxJumps = 1;
    private int attackCooldown = 40;
    private boolean canAttack = true;
    private double lastAttack = System.currentTimeMillis();
    private int frameUpdate;
    private CreatureSound sound = new CreatureSound();

    Creature(int x, int y, int length, int width, String picName) {
        super(x, y, length, width, picName);
        super.setType("Creature");
        super.setGravity(0.2);
        hp = 100; // 100 by default
        maxHp = hp;
        frameUpdate = 100;
    }

    @Override
    public void draw(Graphics g, int xRange, int yRange, SlowmoTracker slowmoTracker) {
        g.setColor(Color.black);
        g.fillRect((int) super.getX() - 2 - xRange, (int) super.getY() - 22 - yRange, super.getLength() + 4, 14);
        g.setColor(Color.green);
        g.fillRect((int) super.getX() - xRange, (int) super.getY() - 20 - yRange,
                (int) (1.0 * this.hp / this.maxHp * super.getLength()), 10);

        super.draw(g, xRange, yRange, slowmoTracker);
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
        if (this.jumps > 0) {
            this.jumps--;
            super.setYSpeed(-jumpSpeed);
        }
    }

    @Override
    public void update(ArrayList<Entity> entities, ArrayList<Bullet> bullets, SlowmoTracker slowmoTracker) {
        if ((slowmoTracker.getGameTime() - lastAttack) > attackCooldown) {
            canAttack = true;
        }

        if (super.getXSpeed() > this.runAccel * 10) {
            super.setXSpeed(this.runAccel * 10);
        } else if (super.getXSpeed() < -this.runAccel * 10) {
            super.setXSpeed(-this.runAccel * 10);
        }

        if (super.getXSpeed() > 0.5) {
            sound.walkSound(slowmoTracker);
            super.setXSpeed(super.getXSpeed() - 0.5);
        } else if (super.getXSpeed() < -0.5) {
            sound.walkSound(slowmoTracker);
            super.setXSpeed(super.getXSpeed() + 0.5);
        } else {
            super.setXSpeed(0);
            sound.stopWalkSound();
        }

        if (jumps < maxJumps) {
            sound.stopWalkSound();
        }

        super.update(entities, bullets, slowmoTracker);

        super.setX((super.getX() + super.getXSpeed() * slowmoTracker.getActiveSlowAmount()));
        for (int i = 0; i < entities.size(); i++) {
            if (!this.equals(entities.get(i)) && super.rectRectDetect(this, entities.get(i))
                    && entities.get(i).getTouchable() && !entities.get(i).getType().equals("Platform")) {
                super.setX((super.getX() - super.getXSpeed() * slowmoTracker.getActiveSlowAmount()));
                super.setXSpeed(0);
            }
        }

        super.setY((super.getY() + super.getYSpeed() * slowmoTracker.getActiveSlowAmount()));
        for (int i = 0; i < entities.size(); i++) {
            if (!this.equals(entities.get(i)) && super.rectRectDetect(this, entities.get(i))
                    && entities.get(i).getTouchable()) {
                if (entities.get(i).getType().equals("Platform")) {
                    if (this.getY() + this.getWidth() < entities.get(i).getY() + 10) {
                        super.setY(entities.get(i).getY() - super.getWidth());
                        super.setYSpeed(0);
                        if (super.getY() < entities.get(i).getY()) {
                            this.jumps = this.maxJumps;
                        }
                    }
                } else {
                    super.setY((super.getY() - super.getYSpeed() * slowmoTracker.getActiveSlowAmount()));
                    super.setYSpeed(0);
                    if (super.getY() < entities.get(i).getY()) {
                        this.jumps = this.maxJumps;
                    }
                }
            }
        }

        // Added part
        if (super.getFrames() != null) {
            if (!canAttack) {
                super.setRow(Const.ATTACK);

            } else if (jumps < maxJumps) {
                super.setRow(Const.JUMP);

            } else if (super.getXSpeed() != 0) {
                super.setRow(Const.MOVE);

            } else if (super.getXSpeed() == 0 && super.getYSpeed() == 0) {
                super.setRow(Const.IDLE);

            }
            if (super.getCol() >= super.getFrames().get(super.getRow()).size()) {
                super.setCol(0);
            }
            if ((System.currentTimeMillis() - super.getAnimationTime())
                    * slowmoTracker.getActiveSlowAmount() > frameUpdate) {
                super.setCol((super.getCol() + 1) % super.getFrames().get(super.getRow()).size());
                super.setAnimationTime(System.currentTimeMillis());
            }

        }

        if (this.hp <= 0) {
            for (int i = 0; i < 10; i++) {
                entities.add(new Blood((int) this.getX() + this.getLength() / 2,
                        (int) this.getY() + this.getWidth() / 2,
                        randint(-20, 20), randint(-30, 0), Color.RED));
            }
            sound.deathSound();
            removeThis(entities);
        }
    }

    @Override
    public void takeDamage(double damage) {
        this.hp -= damage;
    }

    @Override
    public void removeThis(ArrayList<Entity> entities) {
        sound.stopWalkSound();
        super.removeThis(entities);
    }

    // getters
    public double getRunAccel() {
        return this.runAccel;
    }

    public double getJumpSpeed() {
        return this.jumpSpeed;
    }

    public int getJumps() {
        return this.jumps;
    }

    public boolean getCanAttack() {
        return this.canAttack;
    }

    public int getAttackCooldown() {
        return attackCooldown;
    }

    public double getLastAttack() {
        return lastAttack;
    }

    public int getMaxJumps() {
        return this.maxJumps;
    }

    @Override
    public double getHp() {
        return this.hp;
    }

    public double getMaxHp() {
        return this.maxHp;
    }

    // setters
    public void setMaxHp(double hp) {
        this.maxHp = hp;
        if (this.hp > this.maxHp) {
            this.hp = this.maxHp;
        }
    }

    public void setRunAccel(double runAccel) {
        this.runAccel = runAccel;
    }

    public void setJumpSpeed(double jumpSpeed) {
        this.jumpSpeed = jumpSpeed;
    }

    public void setJumps(int jumps) {
        this.jumps = jumps;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    public void setAttackCooldown(int attackCooldown) {
        this.attackCooldown = attackCooldown;
    }

    public void setLastAttack(double lastAttack) {
        this.lastAttack = lastAttack;
    }

    public void setHp(double hp) {
        this.hp = hp;
        if (this.hp > maxHp) {
            this.hp = maxHp;
        }
    }

    public void setMaxJumps(int jumps) {
        this.maxJumps = jumps;
    }
}
