public class Player extends Creature {
    Player(int x, int y, int length, int width, String picName, int rows, int columns) {
        super(x, y, length, width, picName, rows, columns);
        super.setType("Player");
        super.setGravity(0.3);
        super.setRunAccel(0.6);
        super.setJumpSpeed(10);
        super.setTeam(0);
    }
}
