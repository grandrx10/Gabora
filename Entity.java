import java.awt.Graphics;
import java.awt.Color;
import java.io.File;
import java.util.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
// possible exceptions
import java.io.IOException;

public class Entity {
    private int team;
    private double x;
    private double y;
    private double xSpeed = 0;
    private double ySpeed = 0;
    private double xAccel = 0;
    private double yAccel = 0;
    private int length;
    private int width;
    private double gravity;
    private String direction;
    private String picName;
    private String type = "Entity";
    private boolean touchable;
    private Entity interactingWith;
    private BufferedImage[][] frames;
    private int row;
    private int col;
    private long animationTime = System.currentTimeMillis();
    private Color color = new Color(randint(0, 255), randint(0, 255), randint(0, 255));

    Entity(int x, int y, int length, int width, String picName, int rows, int columns) {
        this.x = x;
        this.y = y;
        this.length = length;
        this.width = width;
        this.xSpeed = 0;
        this.ySpeed = 0;
        this.direction = "right";
        this.gravity = 0;
        this.picName = picName;
        this.row = 0;
        this.col = 0;
        this.touchable = true;
        loadImages(rows, columns);
    }

    public void loadImages(int rows, int columns) {
        if (!picName.equals("")) {
            frames = new BufferedImage[rows][columns];
            try {
                for (int row = 0; row < rows; row++) {
                    for (int col = 0; col < columns; col++) {
                        frames[row][col] = ImageIO.read(new File("images/" + picName + row + col + ".png"));
                    }
                }
            } catch (IOException ex) {
                System.out.println(ex);
            }
            row = 2;
            col = 0;
        }
    }

    public void draw(Graphics g, int xRange, int yRange) {

        if (checkInRange(xRange, yRange)) {
            if (picName.equals("")) {
                g.setColor(this.color);
                g.fillRect((int) this.x - xRange, (int) this.y - yRange, this.length, this.width);
            } else {
                if (this.direction.equals("right")) {
                    g.drawImage(this.frames[this.row][this.col], (int) this.x - xRange, (int) this.y - yRange, null);
                } else {
                    g.drawImage(this.frames[this.row][this.col], (int) this.x + this.length - xRange,
                            (int) this.y - yRange,
                            -length, width, null);
                }
            }
        }
    }

    public void update(ArrayList<Entity> entities, ArrayList<Bullet> bullets, SlowmoTracker slowmoTracker) {
        this.xSpeed += this.xAccel;
        this.ySpeed += this.yAccel + this.gravity * slowmoTracker.getActiveSlowAmount();

        if (this.frames != null) {
            if (this.xSpeed != 0) {
                this.row = 2;
                if ((System.currentTimeMillis() - animationTime) * slowmoTracker.getActiveSlowAmount() > 100) {
                    this.col = (this.col + 1) % frames[row].length;
                    animationTime = System.currentTimeMillis();
                }
            } else if (this.ySpeed != 0) {
                this.col = 3;
                this.row = 0;
            }
        }
    }

    public boolean checkInRange(int xRange, int yRange) {
        int centerEntityX = xRange + Const.WIDTH / 2;
        int centerEntityY = yRange + Const.HEIGHT / 2;

        if (distance(centerEntityX, centerEntityY, this.x + length / 2,
                this.y + width / 2) < length / 2 + Const.WIDTH / 2 + 100) {
            return true;
        }
        return false;
    }

    public boolean rectRectDetect(Entity rect, Entity rect2) {
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

    public boolean rectRectDetect(Entity rect, Entity rect2, int range) {
        double leftSide = rect.x - range;
        double rightSide = rect.x + rect.length + range;
        double topSide = rect.y - range;
        double botSide = rect.y + rect.width + range;
        if (rect2.x + rect2.length > leftSide && rect2.x < rightSide && rect2.y + rect2.width > topSide
                && rect2.y < botSide) {
            return true;
        }
        return false;
    }

    public void checkInteract(ArrayList<Entity> entities) {
        for (int i = 0; i < entities.size(); i++) {
            if (rectRectDetect(this, entities.get(i), 50)) {
                entities.get(i).interact(this);
            }
        }
    }

    public double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }

    public int randint(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }

    public void interact(Entity interactor) {
    }

    public void takeDamage(int damage) { // make abstract later.
    }

    public void jump() {
    }

    public void move(String dir) {
    }

    public void attack(int aimX, int aimY, ArrayList<Entity> entities, ArrayList<Bullet> bullets) {

    }

    public void dash(double aimX, double aimY, ArrayList<Entity> entities, ArrayList<Bullet> bullets) {

    }

    // getters
    public String getType() {
        return type;
    }

    public double getGravity() {
        return gravity;
    }

    public String getPicName() {
        return this.picName;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public int getLength() {
        return this.length;
    }

    public int getWidth() {
        return this.width;
    }

    public BufferedImage[][] getFrames() {
        return frames;
    }

    public String getDirection() {
        return direction;
    }

    public double getXSpeed() {
        return this.xSpeed;
    }

    public double getYSpeed() {
        return this.ySpeed;
    }

    public int getTeam() {
        return this.team;
    }

    public Color getColor() {
        return this.color;
    }

    public boolean getTouchable() {
        return touchable;
    }

    public Entity getInteractingWith() {
        return this.interactingWith;
    }

    public ArrayList<Item> getItems() {
        return null;
    }

    // setters
    public void setType(String type) {
        this.type = type;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public void setXAccel(double accel) {
        this.xAccel = accel;
    }

    public void setYAccel(double accel) {
        this.yAccel = accel;
    }

    public void setYSpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    public void setXSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setFrames(BufferedImage[][] frames) {
        this.frames = frames;
    }

    public void setFrames(int row, int col, BufferedImage image) {
        this.frames[row][col] = image;
    }

    public void setTouchable(boolean touchable) {
        this.touchable = touchable;
    }

    public void setInteractingWith(Entity entity) {
        interactingWith = entity;
    }
}
