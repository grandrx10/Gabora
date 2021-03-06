
import java.awt.*;
import java.util.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
// possible exceptions
import java.io.IOException;

public class Player extends Creature {
    private int coins;
    private BufferedImage coinImage;
    private Sound purchaseSound = new Sound("audio/purchase.wav");
    private Font font = new Font("TimesRoman", Font.PLAIN, 20);

    Player(int x, int y, int length, int width, String picName) {
        super(x, y, length, width, picName);
        super.setType("Player");
        super.setGravity(0.3);
        super.setRunAccel(0.6);
        super.setJumpSpeed(10);
        super.setTeam(0);
        super.setMaxHp(100);
        super.setHp(100);
        coins = 0;

        try {
            coinImage = ImageIO.read(new File("images/coin.png"));
        } catch (IOException ex) {
            System.out.println(ex);
            System.out.println("failed to load coin image");
        }
    }

    @Override
    public void draw(Graphics g, int xRange, int yRange, SlowmoTracker slowmoTracker) {
        super.draw(g, xRange, yRange, slowmoTracker);

        // draw the coins at the top left of the screen
        g.setColor(Color.black);
        g.setFont(font);
        g.drawString(Integer.toString(coins), 100, 100);
        g.drawImage(coinImage, 120, 83, null);

        // draw the attackcooldown
        if (super.getCanAttack()) {
            g.setColor(Color.black);
            g.fillRect((int) super.getX() - 2 - xRange, (int) super.getY() - 12 - yRange, super.getLength() + 4, 7);
            g.setColor(Color.red);
            g.fillRect((int) super.getX() - xRange, (int) super.getY() - 10 - yRange, super.getLength(), 3);
        } else {
            g.setColor(Color.black);
            g.fillRect((int) super.getX() - 2 - xRange, (int) super.getY() - 12 - yRange, super.getLength() + 4, 7);
            g.setColor(Color.white);
            g.fillRect((int) super.getX() - xRange, (int) super.getY() - 10 - yRange,
                    (int) (1.0 * (slowmoTracker.getGameTime() - getLastAttack())
                            / super.getAttackCooldown()
                            * super.getLength()),
                    3);
        }

        if (super.getInteractingWith() != null) {
            drawShop(g, xRange, yRange);
        }
    }

    public void drawShop(Graphics g, int xRange, int yRange) {
        g.setColor(new Color(51, 62, 181));
        g.fillRect(190, 390, Const.WIDTH - 380, Const.HEIGHT - 430);
        g.setColor(new Color(78, 171, 237));
        g.fillRect(200, 400, Const.WIDTH - 400, Const.HEIGHT - 450);

        Point p = MouseInfo.getPointerInfo().getLocation();

        if (super.getInteractingWith() != null && super.getInteractingWith().getItems() != null) {
            for (int i = 0; i < super.getInteractingWith().getItems().size(); i++) {
                super.getInteractingWith().getItems().get(i).draw(g, (int) p.getX() - 5, (int) p.getY() - 30);
            }
        }
    }

    @Override
    public void mouseInteract(int x, int y) {
        if (getInteractingWith() != null) {
            for (int i = 0; i < super.getInteractingWith().getItems().size(); i++) {
                if (super.getInteractingWith().getItems().get(i).contains(x, y) &&
                        super.getInteractingWith().getItems().get(i).getCost() <= coins) {
                    coins -= super.getInteractingWith().getItems().get(i).getCost();
                    super.getInteractingWith().getItems().get(i).activateItem(this);
                    super.getInteractingWith().getItems().remove(i);
                    purchaseSound();
                }
            }
        }
    }

    @Override
    public void attack(int aimX, int aimY, ArrayList<Entity> entities, ArrayList<Bullet> bullets,
            SlowmoTracker slowmoTracker) {
        if (super.getCanAttack()) {
            bullets.add(new Slash(this.getX() + this.getLength() / 2,
                    this.getY() + this.getWidth() / 2,
                    aimX, aimY, 100, 5,
                    this.getTeam(),
                    10, 100, false, "slashRed", this));
            super.setCanAttack(false);
            super.setLastAttack(slowmoTracker.getGameTime());
        }
    }

    @Override
    public void dash(double travelX, double travelY, ArrayList<Entity> entities, ArrayList<Bullet> bullets) {
        if (super.getCanAttack()) {
            double startX = this.getX() + this.getLength() / 2;
            double startY = this.getY() + this.getWidth() / 2;
            double aimX = travelX - this.getX();
            double aimY = travelY - this.getY();
            boolean stop = false;
            while (super.distance(startX, startY, this.getX() + this.getLength() / 2,
                    this.getY() + this.getWidth() / 2) < 1000
                    && !stop &&
                    super.distance(travelX, travelY, this.getX() + this.getLength() / 2,
                            this.getY() + this.getWidth() / 2) > 20) {
                this.setX(this.getX() + 10 * aimX
                        / Math.sqrt(Math.pow(aimX, 2) + Math.pow(aimY, 2)));
                this.setY(this.getY() + 10 * aimY
                        / Math.sqrt(Math.pow(aimX, 2) + Math.pow(aimY, 2)));

                bullets.add(new Bullet(this.getX() + this.getLength() / 2, this.getY() + this.getWidth() / 2, 10,
                        0, 0, 1, this.getTeam(), 100,
                        1, false, "dash", this));

                for (int i = 0; i < entities.size(); i++) {
                    if (!this.equals(entities.get(i)) && super.rectRectDetect(this, entities.get(i))
                            && (entities.get(i).getType().equals("Wall") ||
                                    entities.get(i).getType().equals("Door"))) {
                        stop = true;
                        this.setX(this.getX() - 10 * aimX
                                / Math.sqrt(Math.pow(aimX, 2) + Math.pow(aimY, 2)));
                        this.setY(this.getY() - 10 * aimY
                                / Math.sqrt(Math.pow(aimX, 2) + Math.pow(aimY, 2)));
                    }
                }
            }

            setX(getX() - getLength() / 2);
            setY(getY() - getWidth() / 2);

            super.setCanAttack(false);
            super.setLastAttack(System.currentTimeMillis());
        }
    }

    public void getKill() {
        coins++;
    }

    public void purchaseSound() {
        purchaseSound.stop();
        purchaseSound.flush();
        purchaseSound.setFramePosition(0);
        purchaseSound.start();
    }
}
