package cz.krokviak.donkeykong.objects;

import cz.krokviak.donkeykong.collision.AABB;
import cz.krokviak.donkeykong.collision.RectangleUtils;
import cz.krokviak.donkeykong.drawable.AnimatedSprite;
import cz.krokviak.donkeykong.drawable.Drawable;
import cz.krokviak.donkeykong.main.DonkeyKongApplication;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public class Barrel implements Drawable, AABB {
    public static final int WIDTH = 12;
    public static final int HEIGHT = 10;
    public static final int SCALE = 2;
    private final static int GRAVITY = 150;
    private Point2D position;
    private Point2D velocity;
    private final AnimatedSprite animation;
    private int totalBounces;

    public Barrel(final int totalBounces) {
        this.animation = AnimatedSprite.builder()
                .setFilePath("barrel.png")
                .addAnimationSequence("roll", 4)
                .setFrameHeight(HEIGHT)
                .setFrameWidth(WIDTH)
                .setFrameTime(0.1f)
                .positionSupplier(() -> position)
                .scale(SCALE)
                .build();
        animation.setCurrentAnimation("roll");
        this.totalBounces = totalBounces;
    }

    public void setPosition(final double x, final double y){
        position = new Point2D(x, y);
    }

    public void setVelocity(final double x, final double y){
        velocity = new Point2D(x, y);
    }
    public void update(final float dt){
        position = position.add(velocity.multiply(dt));
        fixBounds();
        velocity = velocity.add(0, GRAVITY * dt);
        animation.update(dt);
    }

    public void fixBounds() {
        if (position.getX() < -WIDTH / 2) {
            position = new Point2D(-WIDTH / 2, position.getY());
            velocity = new Point2D(-velocity.getX(), velocity.getY());
            totalBounces--;
        }
        if (position.getX() > DonkeyKongApplication.WIDTH - 3 / 2f * WIDTH) {
            position = new Point2D(DonkeyKongApplication.WIDTH - 3 / 2f * WIDTH, position.getY());
            velocity = new Point2D(-velocity.getX(), velocity.getY());
            totalBounces--;
        }
    }

    @Override
    public void drawInternal(GraphicsContext gc) {
        animation.draw(gc);
    }

    @Override
    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(position.getX(), position.getY(), WIDTH * SCALE, HEIGHT * SCALE);
    }

    @Override
    public void onCollision(AABB other) {
        if (other instanceof Player player) {
            if (player.hasHammer()){
                totalBounces = 0;
                return;
            }
            player.kill();
        }
        else if (other instanceof Platform){
            final Rectangle2D intersection = RectangleUtils.intersection(getBoundingBox(), other.getBoundingBox());
            if (intersection.getWidth() > intersection.getHeight()) {
                if (intersection.getMaxY() == getBoundingBox().getMaxY()) {
                    position = new Point2D(position.getX(), position.getY() - intersection.getHeight());
                    velocity = new Point2D(velocity.getX(), 0);
                } else {
                    position = new Point2D(position.getX(), position.getY() + intersection.getHeight());
                    velocity = new Point2D(velocity.getX(), 0);
                }
            }
        }

    }

    public boolean shouldRemove() {
        return totalBounces <= 0;
    }
}
