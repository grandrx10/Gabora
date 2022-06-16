public class Boots extends Item {
    Boots(int x, int y, int length, int width, int cost, String description) {
        super("Boots", x, y, length, width, cost, description);
    }

    @Override
    public void activateItem(Creature user) {
        user.setRunAccel(user.getRunAccel() + 0.1);
    }
}
