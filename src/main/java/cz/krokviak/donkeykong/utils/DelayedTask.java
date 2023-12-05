package cz.krokviak.donkeykong.utils;

public class DelayedTask {
    public static final DelayedTask EMPTY = new DelayedTask(() -> {}, Float.MAX_VALUE);
    private final ScheduledTask scheduledTask;
    private boolean didTask;
    public DelayedTask(final Runnable task, final float delayInSeconds) {
        this.scheduledTask = new ScheduledTask(task, delayInSeconds);
        this.didTask = false;
    }
    public void update(final float dt) {
        if (didTask) {
            return;
        }
        didTask = scheduledTask.update(dt);
    }
    public void reset() {
        didTask = false;
        scheduledTask.reset();
    }
}
