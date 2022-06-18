import java.security.Timestamp;

public class SlowmoTracker {
    private long timeSlowStart = 0;
    private double timeSlowDuration;
    private double slowAmount;
    private double activeSlowAmount = 1;
    private SlowmoTrackerSound sound = new SlowmoTrackerSound();
    private double gameTime;

    SlowmoTracker(double timeSlowDuration, double slowAmount) {
        this.timeSlowDuration = timeSlowDuration;
        this.slowAmount = slowAmount;
        this.gameTime = 0;
    }

    public void activateSlow() {
        if (timeSlowStart == 0) {
            activeSlowAmount = slowAmount;
            timeSlowStart = System.currentTimeMillis();
            sound.slowSound();
        } else {
            activeSlowAmount = 1;
            timeSlowStart = 0;
            sound.resumeTimeSound();
        }
    }

    public double getActiveSlowAmount() {
        return activeSlowAmount;
    }

    public double getGameTime() {
        return this.gameTime;
    }

    public void increaseGameTime(double amount) {
        gameTime += amount;
    }
}
