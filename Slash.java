import java.util.*;

public class Slash extends Bullet {
    Slash(double x, double y, double aimX, double aimY, int r, double speed, int team,
            int damage, int distance, boolean isRemovedOnHit, String picName, Entity shooter) {
        super(x, y, aimX, aimY, r, speed, team, damage, distance, isRemovedOnHit, picName, shooter);
        super.setSound("audio/slash.wav");
    }

    public void update(ArrayList<Entity> entities, ArrayList<Bullet> bullets, SlowmoTracker slowmoTracker) {
        for (int i = 0; i < bullets.size(); i++) {
            if (super.circCircDetect(bullets.get(i), this) && this != bullets.get(i)
                    && super.getTeam() != bullets.get(i).getTeam()) {
                bullets.get(i).setAim(super.getAimX(), super.getAimY());
                bullets.get(i).setTeam(super.getTeam());
                bullets.get(i).setShooter(super.getShooter());
            }
        }
        super.update(entities, bullets, slowmoTracker);
    }
}
