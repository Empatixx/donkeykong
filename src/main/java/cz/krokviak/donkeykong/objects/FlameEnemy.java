package cz.krokviak.donkeykong.objects;

import cz.krokviak.donkeykong.collision.AABB;
import cz.krokviak.donkeykong.collision.RectangleUtils;
import cz.krokviak.donkeykong.drawable.Drawable;
import cz.krokviak.donkeykong.drawable.Updatable;
import cz.krokviak.donkeykong.main.DonkeyKongApplication;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public class FlameEnemy implements Drawable, Updatable, AABB {
    private final static int WIDTH = 32;
    private final static int HEIGHT = 32;
    private final static int SCALE = 1;
    private final static int MOVE_SPEED = 96;
    private Point2D position;
    private Point2D velocity;

    public FlameEnemy(){
        velocity = new Point2D(MOVE_SPEED, 0);
    }
    @Override
    public void update(float dt) {
        nextPosition(dt);
        fixBounds();
    }

    private void nextPosition(final float dt) {
        position = position.add(new Point2D(velocity.getX(), velocity.getY()).multiply(dt));
    }

    @Override
    public void drawInternal(GraphicsContext gc) {
        gc.fillRect(position.getX(), position.getY(), WIDTH, HEIGHT);
    }

    public void fixBounds(){
        if (position.getX() < 0){
            position = new Point2D(0, position.getY());
            velocity = new Point2D(-velocity.getY(), velocity.getY());
        }
        if (position.getX() + WIDTH * SCALE > DonkeyKongApplication.WIDTH){
            position = new Point2D(DonkeyKongApplication.WIDTH, position.getY());
            velocity = new Point2D(-velocity.getY(), velocity.getY());
        }
    }

    @Override
    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(position.getX(), position.getY(), WIDTH, HEIGHT);
    }
    private final static int STAIR_HEIGHT_THRESHOLD = 3; // Maximum height the player can step up automatically
    @Override
    public void onCollision(AABB other) {
        final Rectangle2D intersection = RectangleUtils.intersection(getBoundingBox(), other.getBoundingBox());
        if (other instanceof Platform){
            if (intersection.getWidth() > intersection.getHeight()) {
                // Collision from top or bottom of the player
                if (intersection.getMaxY() == getBoundingBox().getMaxY()) {
                    // Player lands on the platform
                    position = new Point2D(position.getX(), position.getY() - intersection.getHeight());
                } else if (intersection.getMinY() == getBoundingBox().getMinY()) {
                    // Player hits the ceiling
                    position = new Point2D(position.getX(), position.getY() + intersection.getHeight());
                }
            } else {
                if (intersection.getMaxX() == getBoundingBox().getMaxX() || intersection.getMinX() == getBoundingBox().getMinX()) {
                    // Check if the player can step up a stair
                    double deltaY = other.getBoundingBox().getMinY() - getBoundingBox().getMaxY();
                    if (Math.abs(deltaY) <= STAIR_HEIGHT_THRESHOLD) {
                        // Adjust position to step up the stair
                        position = new Point2D(position.getX(), position.getY() + deltaY);
                    } else {
                        // Standard side collision response
                        if (intersection.getMaxX() == getBoundingBox().getMaxX()) {
                            position = new Point2D(position.getX() - intersection.getWidth(), position.getY());
                        } else {
                            position = new Point2D(position.getX() + intersection.getWidth(), position.getY());
                        }
                    }
                }
            }
        }
    }
}
