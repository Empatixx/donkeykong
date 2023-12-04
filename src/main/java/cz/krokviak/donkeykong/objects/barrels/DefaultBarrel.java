package cz.krokviak.donkeykong.objects.barrels;

import cz.krokviak.donkeykong.collision.AABB;
import cz.krokviak.donkeykong.collision.RectangleUtils;
import cz.krokviak.donkeykong.drawable.AnimatedSprite;
import cz.krokviak.donkeykong.drawable.Drawable;
import cz.krokviak.donkeykong.hud.Score;
import cz.krokviak.donkeykong.main.DonkeyKongApplication;
import cz.krokviak.donkeykong.objects.ClimbDirection;
import cz.krokviak.donkeykong.objects.ClimbEntity;
import cz.krokviak.donkeykong.objects.Enemy;
import cz.krokviak.donkeykong.objects.climb.ClimbService;
import cz.krokviak.donkeykong.objects.climb.ClimbServiceProbability;
import cz.krokviak.donkeykong.objects.Platform;
import cz.krokviak.donkeykong.objects.player.Player;
import cz.krokviak.donkeykong.objects.ladder.Ladder;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public class DefaultBarrel implements Drawable, AABB, Barrel, ClimbEntity, Enemy {
    public static final int WIDTH = 12;
    public static final int HEIGHT = 10;
    public static final int SCALE = 2;
    private final static int GRAVITY = 150;
    private Point2D position;
    private Point2D velocity;
    private final AnimatedSprite animation;
    private int totalBounces;
    private final ClimbService climbService;

    public DefaultBarrel(final int totalBounces) {
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
        this.climbService = new ClimbServiceProbability(this, ClimbDirection.DOWN);
        this.totalBounces = totalBounces;
    }

    @Override
    public void setPosition(final double x, final double y) {
        position = new Point2D(x, y);
    }

    public void setVelocity(final double x, final double y) {
        velocity = new Point2D(x, y);
    }

    @Override
    public void update(final float dt) {
        position = position.add(velocity.multiply(dt));
        fixBounds();
        climbService.update(dt);
        animation.update(dt);
        if (climbService.isClimbing()) {
            return;
        }
        velocity = velocity.add(0, GRAVITY * dt);
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
        switch (other){
            case Platform platform -> {
                if (climbService.isClimbing()) {
                    return;
                }
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
            case Ladder ladder -> {
                if (climbService.isClimbing()) {
                    return;
                }
                climbService.setLadder(ladder);
                final boolean climbing = climbService.isClimbing();
                if (climbing) {
                    totalBounces--;
                    velocity = new Point2D(-velocity.getX(), velocity.getY());
                }
            }
            default -> {}
        }

    }

    @Override
    public boolean shouldRemove() {
        return totalBounces <= 0;
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public void setPosition(float x, float y) {
        position = new Point2D(x, y);
    }

    @Override
    public int getWidth() {
        return WIDTH * SCALE;
    }

    @Override
    public int getHeight() {
        return HEIGHT * SCALE;
    }

    @Override
    public int deathScore() {
        return Score.LOW_SCORE;
    }
}
