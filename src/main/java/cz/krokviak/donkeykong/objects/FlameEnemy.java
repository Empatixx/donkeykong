package cz.krokviak.donkeykong.objects;

import cz.krokviak.donkeykong.collision.AABB;
import cz.krokviak.donkeykong.collision.CollisionService;
import cz.krokviak.donkeykong.collision.RectangleUtils;
import cz.krokviak.donkeykong.drawable.AnimatedSprite;
import cz.krokviak.donkeykong.drawable.Drawable;
import cz.krokviak.donkeykong.drawable.Updatable;
import cz.krokviak.donkeykong.hud.Score;
import cz.krokviak.donkeykong.main.DonkeyKongApplication;
import cz.krokviak.donkeykong.objects.climb.AntiFallService;
import cz.krokviak.donkeykong.objects.climb.ClimbService;
import cz.krokviak.donkeykong.objects.climb.ClimbServiceCooldown;
import cz.krokviak.donkeykong.objects.ladder.Ladder;
import cz.krokviak.donkeykong.objects.player.Player;
import cz.krokviak.donkeykong.utils.DelayedTask;
import cz.krokviak.donkeykong.utils.ScheduledTask;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public class FlameEnemy implements Drawable, Updatable, AABB, ClimbEntity, Enemy {
    private final static int SCALE = 2;
    private final static int WIDTH = 18;
    private final static int HEIGHT = 18;
    private final static int MOVE_SPEED = 96;
    private final static int LIVENESS = 45;
    private final static int GRAVITY = 90;
    private Point2D position;
    private Point2D velocity;
    private final DelayedTask deadTask;
    private final ScheduledTask changeColorTask;
    private final AnimatedSprite animation;
    private final ClimbService climbService;
    private final AntiFallService antiFallService;
    private boolean dead;
    private boolean spawnFly; // true so enemy can fly without getting handled by AntiFallService

    public FlameEnemy(final CollisionService collisionService) {
        deadTask = new DelayedTask(() -> dead = true, LIVENESS);
        velocity = new Point2D(0, 0);
        animation = AnimatedSprite.builder()
                .setFilePath("flame.png")
                .addAnimationSequence("idle", 4)
                .addAnimationSequence("idle2", 4)
                .setFrameHeight(HEIGHT)
                .setFrameWidth(WIDTH)
                .setFrameTime(0.1f)
                .positionSupplier(() -> position)
                .isFlippedSupplier(() -> velocity.getX() > 0)
                .scale(SCALE)
                .build();
        animation.setCurrentAnimation("idle");
        changeColorTask = new ScheduledTask(() -> {
            if (animation.getCurrentAnimation().equals("idle")) {
                animation.setCurrentAnimation("idle2");
            } else {
                animation.setCurrentAnimation("idle");
            }
        }, 5f);
        climbService = new ClimbServiceCooldown(this, ClimbDirection.UP, ClimbDirection.DOWN);
        antiFallService = new AntiFallService(new AABB() {
            @Override
            public Rectangle2D getBoundingBox() {
                if (velocity.getX() > 0) {
                    return new Rectangle2D(position.getX() + WIDTH * SCALE, position.getY(), WIDTH * SCALE, HEIGHT * SCALE);
                } else {
                    return new Rectangle2D(position.getX() - WIDTH * SCALE, position.getY(), WIDTH * SCALE, HEIGHT * SCALE);
                }
            }

            @Override
            public void onCollision(AABB other) {

            }
        }, collisionService);
        spawnFly = true;
    }
    public void throwToRight(){
        velocity = new Point2D(MOVE_SPEED, -MOVE_SPEED);
    }
    public void throwToLeft(){
        velocity = new Point2D(-MOVE_SPEED, -MOVE_SPEED);
    }

    public void smallThrowToRight() {
        velocity = new Point2D(MOVE_SPEED, -MOVE_SPEED/2f);
    }
    @Override
    public void update(float dt) {
        climbService.update(dt);
        animation.update(dt);
        deadTask.update(dt);
        changeColorTask.update(dt);
        antiFall();
        nextPosition(dt);
        fixBounds();
    }


    private void antiFall() {
        if (spawnFly){
            return;
        }
        if (climbService.isClimbing()) {
            return;
        }
        final boolean nextStepWillFall = antiFallService.nextStepWillFall();
        if (nextStepWillFall) {
            velocity = new Point2D(-velocity.getX(), velocity.getY());
        }
    }

    private void nextPosition(final float dt) {
        velocity = velocity.add(0, dt * GRAVITY);
        position = position.add(new Point2D(velocity.getX(), velocity.getY()).multiply(dt));
    }

    @Override
    public void drawInternal(GraphicsContext gc) {
        animation.drawInternal(gc);
    }

    public void fixBounds() {
        if (position.getX() < 0) {
            position = new Point2D(0, position.getY());
            velocity = new Point2D(-velocity.getX(), velocity.getY());
        }
        if (position.getX() + WIDTH * SCALE > DonkeyKongApplication.WIDTH) {
            position = new Point2D(DonkeyKongApplication.WIDTH - WIDTH * SCALE, position.getY());
            velocity = new Point2D(-velocity.getX(), velocity.getY());
        }
    }

    @Override
    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(position.getX(), position.getY(), WIDTH * SCALE, HEIGHT * SCALE);
    }

    private final static int STAIR_HEIGHT_THRESHOLD = 3; // Maximum height the player can step up automatically

    @Override
    public void onCollision(AABB other) {
        final Rectangle2D intersection = RectangleUtils.intersection(getBoundingBox(), other.getBoundingBox());
        switch (other) {

            case Platform platform -> {
                if (climbService.isClimbing()) {
                    return;
                }
                if (intersection.getWidth() > intersection.getHeight()) {
                    // Collision from top or bottom of the player
                    if (intersection.getMaxY() == getBoundingBox().getMaxY()) {
                        // Player lands on the platform
                        position = new Point2D(position.getX(), position.getY() - intersection.getHeight());
                        if (velocity.getY() > 0) {
                            velocity = new Point2D(velocity.getX(), 0);
                            spawnFly = false;
                        }
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
            case Box box -> {
                if (intersection.getWidth() > intersection.getHeight()) {
                    if (intersection.getMaxY() == getBoundingBox().getMaxY()) {
                        position = new Point2D(position.getX(), position.getY() - intersection.getHeight());
                        velocity = new Point2D(velocity.getX(), 0);
                    } else {
                        position = new Point2D(position.getX(), position.getY() + intersection.getHeight());
                        velocity = new Point2D(velocity.getX(), 0);
                    }
                } else {
                    if (intersection.getMaxX() == getBoundingBox().getMaxX()) {
                        position = new Point2D(position.getX() - intersection.getWidth(), position.getY());
                        velocity = new Point2D(-velocity.getX(), velocity.getY());
                    } else {
                        position = new Point2D(position.getX() + intersection.getWidth(), position.getY());
                        velocity = new Point2D(-velocity.getX(), velocity.getY());
                    }
                }
            }
            case Ladder ladder -> climbService.setLadder(ladder);
            default -> {
            }
        }
    }

    public void setPosition(final float x, final float y) {
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

    public boolean shouldRemove() {
        return dead;
    }

    public Point2D getPosition() {
        return position;
    }

    @Override
    public int deathScore() {
        return Score.MEDIUM_SCORE;
    }

    @Override
    public void kill() {
        dead = true;
    }

}
