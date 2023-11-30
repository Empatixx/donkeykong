package cz.krokviak.donkeykong.objects;

import cz.krokviak.donkeykong.utils.InterpolatedPoint2D;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class BarrelLadderDetector {
    private final static float CLIMB_PROBABILITY = 0.25f;
    private InterpolatedPoint2D position;
    private boolean climbing = false;
    private final Barrel barrel;
    private Ladder ladder;

    public BarrelLadderDetector(final Barrel barrel) {
        this.barrel = barrel;
    }

    public boolean setLadder(Ladder ladder) {
        if (this.ladder == null || !this.ladder.equals(ladder) ) {
            this.ladder = ladder;
            System.out.println("Ladder set");
            if (ThreadLocalRandom.current().nextFloat() < CLIMB_PROBABILITY) {
                climb();
                return true;
            }
        }
        return false;
    }

    private void climb() {
        climbing = true;
        position = new InterpolatedPoint2D(barrel.getPosition(), ladder.getDownPosition().subtract(Barrel.WIDTH * Barrel.SCALE / 2f, Barrel.HEIGHT * Barrel.SCALE / 2f), 0.5f);
    }

    public void update(float dt) {
        if (ladder == null) {
            return;
        }
        if (climbing && !position.isFinished()) {
            position.update(dt);
            barrel.setPosition((float) position.get().getX(), (float) position.get().getY());
            if (position.isFinished()) {
                climbing = false;
            }
        }
    }

    public boolean isClimbing() {
        return climbing;
    }
}
