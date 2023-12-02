package cz.krokviak.donkeykong.objects;

import cz.krokviak.donkeykong.collision.AABB;
import cz.krokviak.donkeykong.collision.RectangleUtils;
import cz.krokviak.donkeykong.drawable.AnimatedSprite;
import cz.krokviak.donkeykong.drawable.Drawable;
import cz.krokviak.donkeykong.drawable.Updatable;
import cz.krokviak.donkeykong.hud.Score;
import cz.krokviak.donkeykong.hud.Scoreboard;
import cz.krokviak.donkeykong.input.GameAction;
import cz.krokviak.donkeykong.input.InputHandler;
import cz.krokviak.donkeykong.items.Hammer;
import cz.krokviak.donkeykong.main.DonkeyKongApplication;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

import java.time.Instant;

public class Player implements Drawable, AABB, Updatable {
    public final static int SCALE = 2;
    public final static int WIDTH = 64;
    public final static int HEIGHT = 64;
    public final static int COLLISION_WIDTH = 32;
    public final static int COLLISION_HEIGHT = 64;

    private final static int SPEED = 50;
    private final static int JUMP_SPEED = 100;
    private final static int GRAVITY = 90;
    private final static int RESPAWN_TIME = 2;
    private final InputHandler inputHandler;
    private final AnimatedSprite animation;
    private final PlayerLives playerLives;
    private Point2D position;
    private Point2D velocity;
    private boolean rightFacing = true;
    private boolean grounded = true;
    private boolean alive = true;
    private Instant timeOfDeath;
    private HammerItem hammer;
    private PlayerLadderDetector playerLadderDetector;
    private final Scoreboard scoreboard;

    public Player(final InputHandler inputHandler) {
        position = Point2D.ZERO;
        velocity = Point2D.ZERO;
        this.inputHandler = inputHandler;
        animation = AnimatedSprite.builder()
                .setFilePath("player.png")
                .addAnimationSequence("attack", 3)
                .addAnimationSequence("charge", 1)
                .addAnimationSequence("climb", 4)
                .addAnimationSequence("walk", 3)
                .addAnimationSequence("dead", 1)
                .addAnimationSequence("idle", 1)
                .setFrameHeight(HEIGHT)
                .setFrameWidth(WIDTH)
                .setFrameTime(0.1f)
                .positionSupplier(() -> position)
                .isFlippedSupplier(() -> !rightFacing)
                .scale(SCALE)
                .build();
        animation.setCurrentAnimation("walk");
        hammer = new HammerItem();
        playerLives = new PlayerLives();
        playerLadderDetector = new PlayerLadderDetector(this);
        this.scoreboard = new Scoreboard();
    }

    @Override
    public void update(float dt) {
        if (!alive) {
            playerLadderDetector.stopClimbing();
            animation.update(dt);
            inputHandler.setActive(GameAction.MOVE_LEFT, false);
            inputHandler.setActive(GameAction.MOVE_RIGHT, false);
            inputHandler.setActive(GameAction.MOVE_UP, false);
            inputHandler.setActive(GameAction.MOVE_DOWN, false);
            updateVelocity(dt);
            nextPosition(dt);
            fixBounds();
            return;
        }
        animation.update(dt);
        playerLadderDetector.update(dt);
        scoreboard.update(dt);
        if (playerLadderDetector.isClimbing()) {
            return;
        } else if (animation.getCurrentAnimation().equals("climb")) {
            animation.setCurrentAnimation("idle");
        }
        if (hammer.isActive()) {
            if (!animation.getCurrentAnimation().equals("attack")) {
                animation.setCurrentAnimation("attack");
            }
        } else {
            if (animation.getCurrentAnimation().equals("attack")) {
                animation.setCurrentAnimation("idle");
            }
        }
        if (animation.getCurrentAnimation().equals("walk") && velocity.getX() == 0) {
            animation.setCurrentAnimation("idle");
        } else if (animation.getCurrentAnimation().equals("idle") && velocity.getX() != 0) {
            animation.setCurrentAnimation("walk");
        }
        position = position.add(velocity.multiply(dt));
        updateVelocity(dt);
        nextPosition(dt);
        handleClimbing();
        hammer.update(dt);
        fixBounds();
    }

    private void handleClimbing() {
        final boolean down = inputHandler.isActive(GameAction.MOVE_DOWN);
        if (playerLadderDetector.getLadder() != null && down) {
            animation.setCurrentAnimation("climb");
            playerLadderDetector.climb();
        }
    }

    private void nextPosition(final float dt) {
        position = position.add(new Point2D(velocity.getX(), velocity.getY()).multiply(dt));
    }

    private void updateVelocity(final float dt) {
        final boolean right = inputHandler.isActive(GameAction.MOVE_RIGHT);
        final boolean left = inputHandler.isActive(GameAction.MOVE_LEFT);
        final boolean up = inputHandler.isActive(GameAction.MOVE_UP);

        Point2D newVelocity = velocity = new Point2D(0, velocity.getY());
        if (right) {
            newVelocity = newVelocity.add(SPEED, 0);
            rightFacing = true;
        }
        if (left) {
            newVelocity = newVelocity.add(-SPEED, 0);
            rightFacing = false;
        }
        if (up && grounded && !playerLadderDetector.isClimbing()) {
            newVelocity = newVelocity.add(0, -JUMP_SPEED);
            grounded = false;
        }
        // gravity
        newVelocity = newVelocity.add(0, GRAVITY * dt);

        velocity = newVelocity;
    }

    public void fixBounds() {
        if (position.getX() + WIDTH * SCALE / 2f - COLLISION_WIDTH / 2f < 0) {
            position = new Point2D(-WIDTH * SCALE / 2f + COLLISION_WIDTH / 2f, position.getY());
            velocity = new Point2D(0, velocity.getY());
        }
        if (position.getX() + WIDTH * SCALE / 2f + COLLISION_WIDTH / 2f > DonkeyKongApplication.WIDTH) {
            position = new Point2D(DonkeyKongApplication.WIDTH - WIDTH * SCALE / 2f - COLLISION_WIDTH / 2f, position.getY());
            velocity = new Point2D(0, velocity.getY());
        }

        if (position.getY() + HEIGHT * SCALE / 2f - COLLISION_HEIGHT / 2f + 32 < 0) {
            position = new Point2D(position.getX(), -HEIGHT * SCALE / 2f + COLLISION_HEIGHT / 2f - 32);
            velocity = new Point2D(velocity.getX(), 0);
        }
        if (position.getY() + HEIGHT * SCALE / 2f + COLLISION_HEIGHT / 2f - 32 > DonkeyKongApplication.HEIGHT) {
            position = new Point2D(position.getX(), DonkeyKongApplication.HEIGHT - HEIGHT * SCALE / 2f - COLLISION_HEIGHT / 2f + 32);
            velocity = new Point2D(velocity.getX(), 0);
        }
    }

    public void setVelocity(final float x, final float y) {
        this.velocity = new Point2D(x, y);
    }

    @Override
    public void drawInternal(final GraphicsContext gc) {
        animation.draw(gc);
        playerLives.draw(gc);
        scoreboard.draw(gc);
        /*gc.setFill(Color.GREEN);
        gc.fillRect(position.getX(), position.getY(), WIDTH * SCALE, HEIGHT * SCALE);*/

    }

    @Override
    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(position.getX() + WIDTH * SCALE / 2f - COLLISION_WIDTH / 2f,
                position.getY() + HEIGHT * SCALE / 2f - COLLISION_HEIGHT / 2f + 32,
                COLLISION_WIDTH, COLLISION_HEIGHT - 32);
    }

    private final static int STAIR_HEIGHT_THRESHOLD = 3; // Maximum height the player can step up automatically

    @Override
    public void onCollision(AABB other) {
        final Rectangle2D intersection = RectangleUtils.intersection(getBoundingBox(), other.getBoundingBox());
        if (other instanceof Platform) {
            if (playerLadderDetector.isClimbing()) {
                return;
            }
            if (intersection.getWidth() > intersection.getHeight()) {
                // Collision from top or bottom of the player
                if (intersection.getMaxY() == getBoundingBox().getMaxY()) {
                    // Player lands on the platform
                    position = new Point2D(position.getX(), position.getY() - intersection.getHeight());
                    grounded = true; // Player is on the ground
                    if (velocity.getY() > 0) {
                        velocity = new Point2D(velocity.getX(), 0);
                    }
                } else if (intersection.getMinY() == getBoundingBox().getMinY()) {
                    // Player hits the ceiling
                    position = new Point2D(position.getX(), position.getY() + intersection.getHeight());
                    velocity = new Point2D(velocity.getX(), 0);
                }
            } else {
                if (intersection.getMaxX() == getBoundingBox().getMaxX() || intersection.getMinX() == getBoundingBox().getMinX()) {
                    // Check if the player can step up a stair
                    double deltaY = other.getBoundingBox().getMinY() - getBoundingBox().getMaxY();
                    if (Math.abs(deltaY) <= STAIR_HEIGHT_THRESHOLD && grounded) {
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
        } else if (other instanceof Hammer) {
            hammer.activate();
        } else if (other instanceof Ladder ladder) {
            playerLadderDetector.setLadder(ladder);
        }
    }

    public void setPosition(float x, float y) {
        position = new Point2D(x, y);
    }

    public void kill() {
        alive = false;
        animation.setCurrentAnimation("dead");
        timeOfDeath = Instant.now();
    }

    public boolean hasHammer() {
        return hammer.isActive();
    }

    public Point2D getPosition() {
        return position;
    }

    public boolean hasLives() {
        return playerLives.canRespawn();
    }

    public boolean canRespawn() {
        return Instant.now().isAfter(timeOfDeath.plusSeconds(RESPAWN_TIME));
    }

    public boolean isAlive() {
        return alive;
    }

    public void setLives(int lives) {
        playerLives.setLives(lives);
    }

    public int getLives() {
        return playerLives.getLives();
    }

    public void addScore(final int score) {
        final Score scoreObj = new Score(score);
        scoreObj.setPosition((float) position.getX(), (float) position.getY()+20);
        scoreboard.addScore(scoreObj);
    }

    public int getTotalScore() {
        return scoreboard.getTotalScore();
    }
}
