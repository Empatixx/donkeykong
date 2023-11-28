package cz.krokviak.donkeykong.utils;

public class ScheduledTask {

    private final Runnable task;
    private final float interval;
    private float elapsedTime;

    public ScheduledTask(final Runnable task, final float intervalInSeconds) {
        this.task = task;
        this.interval = intervalInSeconds;
        this.elapsedTime = 0;
    }

    public boolean update(float dt) {
        elapsedTime += dt;
        if (elapsedTime >= interval) {
            task.run();
            elapsedTime -= interval;
            return true;
        }
        return false;
    }

    public void reset() {
        elapsedTime = 0;
    }
}