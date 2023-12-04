package cz.krokviak.donkeykong.objects.princess;

import cz.krokviak.donkeykong.collision.AABB;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

public class NextLevelBox implements AABB {
    private final static int WIDTH = 64;
    private final static int HEIGHT = 32;
    private final Point2D position;

    public NextLevelBox(final Point2D position) {
        this.position = position;
    }

    @Override
    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(position.getX(), position.getY(), WIDTH, HEIGHT);
    }

    @Override
    public void onCollision(AABB other) {
    }
}
