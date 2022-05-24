import java.awt.Graphics;
import java.awt.Color;
import java.io.File;
import java.util.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
// possible exceptions
import java.io.IOException;

public class Entity {
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
    private BufferedImage[][] frames;
    private int row;
    private int col;
    private long animationTime = System.currentTimeMillis();

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
        if (picName.equals("")) {
            g.setColor(Color.gray);
            g.fillRect((int) this.x - xRange, (int) this.y - yRange, this.length, this.width);
        } else {
            if (this.direction.equals("right")) {
                g.drawImage(this.frames[this.row][this.col], (int) this.x - xRange, (int) this.y - yRange, null);
            } else {
                g.drawImage(this.frames[this.row][this.col], (int) this.x + this.length - xRange, (int) this.y - yRange,
                        -length, width, null);
            }
        }
    }

    public void update(ArrayList<Entity> entities) {
        this.xSpeed += this.xAccel;
        this.ySpeed += this.yAccel + this.gravity;

        if (this.xSpeed != 0) {
            this.row = 2;
            if (System.currentTimeMillis() - animationTime > 100) {
                this.col = (this.col + 1) % frames[row].length;
                animationTime = System.currentTimeMillis();
            }
        } else if (this.ySpeed != 0) {
            this.col = 3;
            this.row = 0;
        }
    }

    public boolean rectRectDetect(Entity rect, Entity rect2) {
        double leftSide = rect.x;
        double rightSide = rect.x + rect.length;
        double topSide = rect.y;
        double botSide = rect.y + rect.width;
        if (rect2.x + rect2.length > leftSide && rect2.x < rightSide && rect2.y + rect2.width > topSide
                && rect2.y < botSide) {
            return true;
        } else {
            return false;
        }
    }

    public void jump() {
    }

    public void move(String dir) {
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

}
