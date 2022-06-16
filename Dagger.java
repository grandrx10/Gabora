public class Dagger extends Item {
    Dagger(int x, int y, int length, int width, int cost, String description) {
        super("Dagger", x, y, length, width, cost, description);
    }

    @Override
    public void activateItem(Creature user) {
        user.setAttackCooldown(user.getAttackCooldown() - 100);
    }
}
