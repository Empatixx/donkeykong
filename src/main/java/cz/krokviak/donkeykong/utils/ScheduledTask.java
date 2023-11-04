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

    public void update(float dt) {
        elapsedTime += dt;
        if (elapsedTime >= interval) {
            task.run();
            elapsedTime -= interval;
        }
    }

    public void reset() {
        elapsedTime = 0;
    }
}