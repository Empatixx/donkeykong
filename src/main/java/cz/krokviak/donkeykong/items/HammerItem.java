package cz.krokviak.donkeykong.items;

import cz.krokviak.donkeykong.utils.DelayedTask;

public class HammerItem {
    private DelayedTask delayedTask;
    private boolean active;
    public HammerItem() {
        this.delayedTask = new DelayedTask(() -> active = false, 10);
        active = false;
    }
    public void update(final float dt) {
        delayedTask.update(dt);
    }
    public void activate() {
        active = true;
        delayedTask.reset();
    }

    public boolean isActive() {
        return active;
    }
}
