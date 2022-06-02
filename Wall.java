public class Wall extends Entity {
    Wall(int x, int y, int length, int width, String picName) {
        super(x, y, length, width, picName, 0, 0);
        super.setType("Wall");
        super.setTeam(-1);
    }
}
