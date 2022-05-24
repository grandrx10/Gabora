public class Wall extends Entity {
    Wall(int x, int y, int length, int width, String picName, int rows, int columns) {
        super(x, y, length, width, picName, rows, columns);
        super.setType("Wall");
    }
}
