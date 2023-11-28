package cz.krokviak.donkeykong.objects;

import cz.krokviak.donkeykong.collision.AABB;
import cz.krokviak.donkeykong.collision.RectangleUtils;
import cz.krokviak.donkeykong.drawable.AnimatedSprite;
import cz.krokviak.donkeykong.drawable.Drawable;
import cz.krokviak.donkeykong.input.GameAction;
import cz.krokviak.donkeykong.input.InputHandler;
import cz.krokviak.donkeykong.main.DonkeyKongApplication;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public class Player implements Drawable, AABB {
    private final static int SCALE = 2;
    private final static int WIDTH = 64;
    private final static int HEIGHT = 64;
    private final static int SPEED = 50;
    private final static int JUMP_SPEED = 100;
    private final static int GRAVITY = 75;
    private final InputHandler inputHandler;
    private final AnimatedSprite animation;
    private Point2D position;
    private Point2D velocity;
    private boolean rightFacing = true;
    private boolean isGrounded = true;
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
    }
    public void update(float dt){
        position = position.add(velocity.multiply(dt));
        updateVelocity(dt);
        nextPosition(dt);
        animation.update(dt);
        fixBounds();
    }

    private void nextPosition(final float dt) {
        position = position.add(new Point2D(velocity.getX(), velocity.getY()).multiply(dt));
    }

    private void updateVelocity(final  float dt){
        final boolean right = inputHandler.isActive(GameAction.MOVE_RIGHT);
        final boolean left = inputHandler.isActive(GameAction.MOVE_LEFT);
        final boolean up = inputHandler.isActive(GameAction.MOVE_UP);
        final boolean down = inputHandler.isActive(GameAction.MOVE_DOWN);

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
        if (position.getX() < -WIDTH / 2){
            position = new Point2D(-WIDTH / 2, position.getY());
        }
        if (position.getX() > DonkeyKongApplication.WIDTH - WIDTH / 2){
            position = new Point2D(DonkeyKongApplication.WIDTH - WIDTH / 2, position.getY());
        }
        if (position.getY() < -HEIGHT / 2){
            position = new Point2D(position.getX(), -HEIGHT / 2);
        }
        if (position.getY() > DonkeyKongApplication.HEIGHT - HEIGHT / 2){
            position = new Point2D(position.getX(), DonkeyKongApplication.HEIGHT - HEIGHT / 2);
        }
    }
    public void setVelocity(final float x, final float y){
        this.velocity = new Point2D(x, y);
    }
    @Override
    public void drawInternal(final GraphicsContext gc){
        animation.draw(gc);
    }

    @Override
    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(position.getX() + WIDTH / 2 + 10, position.getY() + HEIGHT / 2 + 20, WIDTH - 20, HEIGHT - 20);
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
    }

    public void setPosition(int x, int y) {
        position = new Point2D(x, y);
    }
}
