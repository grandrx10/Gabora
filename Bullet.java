import java.awt.Graphics;
import java.awt.Color;
import java.io.File;
import java.util.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
// possible exceptions
import java.io.IOException;

public class Bullet {
    private double x;
    private double y;
    private double aimX;
    private double aimY;
    private int r;
    private double speed;
    private int team;

    Bullet(double x, double y, double aimX, double aimY, int r, double speed, int team) {
        this.x = x;
        this.y = y;
        this.aimX = aimX - x;
        this.aimY = aimY - y;
        this.r = r;
        this.speed = speed;
        this.team = team;
    }

    public void update(ArrayList<Entity> entities, ArrayList<Bullet> bullets) {
        this.x = this.x + this.speed * aimX
                / Math.sqrt(Math.pow(this.aimX, 2) + Math.pow(this.aimY, 2));
        this.y = this.y + this.speed * this.aimY
                / Math.sqrt(Math.pow(this.aimX, 2) + Math.pow(this.aimY, 2));

        for (int i = 0; i < entities.size() && this != null; i++) {
            if (circRectDetect(this, entities.get(i)) && this.team != entities.get(i).getTeam()) {
                entities.get(i).takeDamage(10);
                bullets.remove(this);
            }
        }

    }

    public void draw(Graphics g, int xRange, int yRange) {
        g.setColor(Color.black);
        g.fillOval((int) this.x - xRange, (int) this.y - yRange, this.r, this.r);
    }

    public boolean circRectDetect(Bullet circle, Entity rect) {
        double leftSide = rect.getX();
        double rightSide = rect.getX() + rect.getLength();
        double topSide = rect.getY();
        double botSide = rect.getY() + rect.getWidth();
        if (circle.x + circle.r / 2 > leftSide && circle.x - circle.r / 2 < rightSide
                && circle.y + circle.r / 2 > topSide
                && circle.y - circle.r / 2 < botSide) {
            return true;
        }
        return false;
    }
}
