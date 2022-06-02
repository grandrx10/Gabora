import java.util.*;

public class Guard extends Enemy {
    Guard(int x, int y, int length, int width, String picName, int rows, int columns) {
        super(x, y, length, width, picName, rows, columns);
        super.setGravity(0.2);
        super.setDetectRange(500);
        super.setRunAccel(0.3);
        super.setJumpSpeed(5);
        super.setTeam(1);
    }

    public void updateDestination(ArrayList<Entity> entities) {
        super.setDestinationX(entities.get(0).getX());
        super.setDestinationY(entities.get(0).getY());
    }

    public void search() {
        if (super.getDestinationX() != 0 && super.getDestinationY() != 0) {
            if (super.getX() > super.getDestinationX()) {
                super.setXAccel(-super.getRunAccel());
                super.setDirection("left");
            } else if (super.getX() < super.getDestinationX()) {
                super.setXAccel(super.getRunAccel());
                super.setDirection("right");
            }
        }
    }

    @Override
    public void update(ArrayList<Entity> entities) {
        updateDestination(entities);
        search();
        super.update(entities);
    }
}
