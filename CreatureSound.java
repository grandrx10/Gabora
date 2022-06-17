public class CreatureSound {
    Sound walkSound = new Sound("audio/walk.wav");
    Sound deathSound = new Sound("audio/deathSound.wav");

    CreatureSound() {

    }

    public void walkSound(SlowmoTracker slowmoTracker) {
        if (slowmoTracker.getActiveSlowAmount() == 1) {
            if (!walkSound.isRunning()) {
                walkSound.stop(); // stop the sound effect if still running
                walkSound.flush(); // clear the buffer with audio data
                walkSound.setFramePosition(0); // prepare to start from the beginning
                walkSound.start();
            }
        }
    }

    public void stopWalkSound() {
        walkSound.stop(); // stop the sound effect if still running
        walkSound.flush(); // clear the buffer with audio data
        walkSound.setFramePosition(0); // prepare to start from the beginning
    }

    public void deathSound() {
        deathSound.start();
    }
}
