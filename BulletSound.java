public class BulletSound {
    private Sound sound;
    private boolean played = false;

    BulletSound(String bulletSound) {
        sound = new Sound("audio/gunshot.wav");
    }

    public void attackSound() {
        sound.start();
        played = true;
    }

    public void setShotSound(String soundName) {
        this.sound = new Sound(soundName);
    }

    public boolean getPlayed() {
        return played;
    }
}
