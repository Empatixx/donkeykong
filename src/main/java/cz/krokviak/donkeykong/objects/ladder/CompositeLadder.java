package cz.krokviak.donkeykong.objects.ladder;

import cz.krokviak.donkeykong.collision.AABB;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

public class CompositeLadder implements AABB, Ladder{
    private final static int WIDTH = 32;
    private final static int HEIGHT = 32;
    private final DefaultLadder ladder;
    public CompositeLadder(final DefaultLadder ladder) {
        this.ladder = ladder;
    }

    @Override
    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(ladder.getPosition().getX(), ladder.getPosition().getY() - 20, WIDTH, HEIGHT);
    }

    @Override
    public void onCollision(AABB other) {
    }

    public DefaultLadder getLadder() {
        return ladder;
    }

    @Override
    public Point2D getDownPosition() {
        return ladder.getDownPosition();
    }

    @Override
    public Point2D getUpPosition() {
        return ladder.getUpPosition();
    }
}
