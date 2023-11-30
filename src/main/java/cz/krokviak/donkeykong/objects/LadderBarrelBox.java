package cz.krokviak.donkeykong.objects;

import cz.krokviak.donkeykong.collision.AABB;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

public class LadderBarrelBox implements AABB {
    private final static int WIDTH = 32;
    private final static int HEIGHT = 32;
    private final Ladder ladder;
    public LadderBarrelBox(final Ladder ladder) {
        this.ladder = ladder;
    }

    @Override
    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(ladder.getPosition().getX(), ladder.getPosition().getY() - 20, WIDTH, HEIGHT);
    }

    @Override
    public void onCollision(AABB other) {
    }

    public Ladder getLadder() {
        return ladder;
    }
}
