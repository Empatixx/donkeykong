package cz.krokviak.donkeykong.objects.princess;

import cz.krokviak.donkeykong.collision.AABB;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

public class NextLevelBox implements AABB {
    private final Point2D dimensions;
    private final Point2D position;

    public NextLevelBox(final float x, final float y, final float width, final float height){
        this.position = new Point2D(x, y);
        this.dimensions = new Point2D(width, height);
    }

    @Override
    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(position.getX(), position.getY(), dimensions.getX(), dimensions.getY());
    }

    @Override
    public void onCollision(AABB other) {
    }
}
