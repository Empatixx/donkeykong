package cz.krokviak.donkeykong.objects;

import cz.krokviak.donkeykong.utils.InterpolatedPoint2D;

import java.util.concurrent.ThreadLocalRandom;

public class FlameLadderDetector {
    private final static float CLIMB_PROBABILITY = 0.25f;
    private InterpolatedPoint2D position;
    private boolean climbing = false;
    private final FlameEnemy flameEnemy;
    private DefaultLadder ladder;

    public FlameLadderDetector(final FlameEnemy enemy) {
        this.flameEnemy = enemy;
    }

    public boolean setLadder(DefaultLadder ladder) {
        if (this.ladder == null || !this.ladder.equals(ladder) ) {
            this.ladder = ladder;
            if (ThreadLocalRandom.current().nextFloat() < CLIMB_PROBABILITY) {
                climb();
                return true;
            }
        }
        return false;
    }

    private void climb() {
        climbing = true;
        position = new InterpolatedPoint2D(flameEnemy.getPosition(), ladder.getDownPosition().subtract(flameEnemy.getWidth() / 2f, flameEnemy.getHeight() / 2f), 0.5f);
    }

    public void update(float dt) {
        if (ladder == null) {
            return;
        }
        if (climbing && !position.isFinished()) {
            position.update(dt);
            flameEnemy.setPosition((float) position.get().getX(), (float) position.get().getY());
            if (position.isFinished()) {
                climbing = false;
            }
        }
    }

    public boolean isClimbing() {
        return climbing;
    }
}