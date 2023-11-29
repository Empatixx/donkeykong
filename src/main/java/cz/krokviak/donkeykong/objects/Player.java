package cz.krokviak.donkeykong.objects;

import cz.krokviak.donkeykong.collision.AABB;
import cz.krokviak.donkeykong.collision.RectangleUtils;
import cz.krokviak.donkeykong.drawable.AnimatedSprite;
import cz.krokviak.donkeykong.drawable.Drawable;
import cz.krokviak.donkeykong.drawable.Updatable;
import cz.krokviak.donkeykong.input.GameAction;
import cz.krokviak.donkeykong.input.InputHandler;
import cz.krokviak.donkeykong.items.Hammer;
import cz.krokviak.donkeykong.main.DonkeyKongApplication;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public class Player implements Drawable, AABB, Updatable {
    public final static int SCALE = 2;
    public final static int WIDTH = 64;
    public final static int HEIGHT = 64;
    public final static int COLLISION_WIDTH = 32;
    public final static int COLLISION_HEIGHT = 32;
    private final static int SPEED = 50;
    private final static int JUMP_SPEED = 100;
    private final static int GRAVITY = 75;
    private final static int RESPAWN_TIME = 2;
    private final InputHandler inputHandler;
    private final AnimatedSprite animation;
    private final PlayerLives playerLives;
    private Point2D position;
    private Point2D velocity;
    private boolean rightFacing = true;
    private boolean isGrounded = true;
    private boolean isAlive = true;
    private float timeSinceDeath;
    private HammerItem hammer;
    private LadderDetector ladderDetector;
    public Player(final InputHandler inputHandler){
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
        ladderDetector = new LadderDetector(this);
        timeSinceDeath = 0;
    }
    @Override
    public void update(float dt){
        if (!isAlive){
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
        if (hammer.isActive()){
            if (!animation.getCurrentAnimation().equals("attack")){
                animation.setCurrentAnimation("attack");
            }
        } else {
            if (animation.getCurrentAnimation().equals("attack")){
                animation.setCurrentAnimation("walk");
            }
        }
        ladderDetector.update(dt);
        position = position.add(velocity.multiply(dt));
        updateVelocity(dt);
        nextPosition(dt);
        handleClimbing();
        animation.update(dt);
        hammer.update(dt);
        fixBounds();
        System.out.println("Player position: " + position);
    }

    private void handleClimbing() {
        final boolean down = inputHandler.isActive(GameAction.MOVE_DOWN);
        if (ladderDetector.getLadder() != null && down){
            animation.setCurrentAnimation("climb");
            position = ladderDetector.getLadder().getUpPosition();
        }
    }

    private void nextPosition(final float dt) {
        position = position.add(new Point2D(velocity.getX(), velocity.getY()).multiply(dt));
    }

    private void updateVelocity(final  float dt){
        final boolean right = inputHandler.isActive(GameAction.MOVE_RIGHT);
        final boolean left = inputHandler.isActive(GameAction.MOVE_LEFT);
        final boolean up = inputHandler.isActive(GameAction.MOVE_UP);

        Point2D newVelocity = velocity = new Point2D(0, velocity.getY());
        if(right){
            newVelocity = newVelocity.add(SPEED, 0);
            rightFacing = true;
        }
        if(left){
            newVelocity = newVelocity.add(-SPEED, 0);
            rightFacing = false;
        }
        if (up && isGrounded){
            newVelocity = newVelocity.add(0, -JUMP_SPEED);
            isGrounded = false;
        }
        // gravity
        newVelocity = newVelocity.add(0, GRAVITY * dt);

        velocity = newVelocity;
    }
    public void fixBounds(){
        if (position.getX() < 0){
            position = new Point2D(0, position.getY());
            velocity = new Point2D(0, velocity.getY());
        }
        if (position.getX() + WIDTH * SCALE > DonkeyKongApplication.WIDTH){
            position = new Point2D(DonkeyKongApplication.WIDTH, position.getY());
            velocity = new Point2D(0, velocity.getY());
        }

        if (position.getY() < 0){
            position = new Point2D(position.getX(), 0);
            velocity = new Point2D(velocity.getX(), 0);
        }
        if (position.getY() + HEIGHT * SCALE > DonkeyKongApplication.HEIGHT){
            position = new Point2D(position.getX(), DonkeyKongApplication.HEIGHT);
            velocity = new Point2D(velocity.getX(), 0);
        }
    }
    public void setVelocity(final float x, final float y){
        this.velocity = new Point2D(x, y);
    }
    @Override
    public void drawInternal(final GraphicsContext gc){
        animation.draw(gc);
        playerLives.draw(gc);
    }

    @Override
    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(position.getX() + WIDTH, position.getY() + HEIGHT, COLLISION_WIDTH, COLLISION_HEIGHT);
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
                    isGrounded = true; // Player is on the ground
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
                    if (Math.abs(deltaY) <= STAIR_HEIGHT_THRESHOLD && isGrounded) {
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
        else if (other instanceof Hammer){
            hammer.activate();
        } else if (other instanceof Ladder ladder){
            ladderDetector.setLadder(ladder);
        }
    }

    public void setPosition(int x, int y) {
        position = new Point2D(x, y);
    }

    public void kill() {
        isAlive = false;
        animation.setCurrentAnimation("dead");
    }

    public boolean hasHammer() {
        return hammer.isActive();
    }
}
