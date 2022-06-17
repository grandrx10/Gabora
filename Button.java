import java.util.*;

public class Button extends Wall {
    private Sound leverSound = new Sound("audio/leverSound.wav");

    Button(int x, int y, int length, int width, String picName) {
        super(x, y, length, width, picName);
        super.setType("Button");
        super.setTouchable(false);
    }

    @Override
    public void interact(Entity interactor, Map map, ArrayList<Entity> entities, Music music) {
        if (map.getMapLoading()) {
            for (int i = entities.size() - 1; i >= 0; i--) {
                entities.get(i).removeThis(entities);
            }
            entities.add(interactor);
            interactor.setX(100);
            interactor.setY(100);
            map.setMapLoading(false);
            map = new Map(0, 0, entities, 10);
            music.loadNewSong("audio/soundtracks/BreathOfASerpent.wav");
            music.start();
        } else {
            map.setMapLoading(true);
            leverSound.start();
            music.stop();
        }
    }

}
