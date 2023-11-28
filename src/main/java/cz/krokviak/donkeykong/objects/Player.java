package cz.krokviak.donkeykong.objects;

import cz.krokviak.donkeykong.collision.AABB;
import cz.krokviak.donkeykong.collision.RectangleUtils;
import cz.krokviak.donkeykong.drawable.AnimatedSprite;
import cz.krokviak.donkeykong.drawable.Drawable;
import cz.krokviak.donkeykong.input.GameAction;
import cz.krokviak.donkeykong.input.InputHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

import java.nio.file.Path;

public class Player implements Drawable, AABB {
    private final static int SCALE = 2;
    private final static int WIDTH = 64;
    private final static int HEIGHT = 64;
    private final static int SPEED = 50;
    private final static int GRAVITY = 5;
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
            newVelocity = newVelocity.add(0, -SPEED);
            isGrounded = false;
        }
        // gravity
        newVelocity = newVelocity.add(0, GRAVITY * dt);

        velocity = newVelocity;
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

    @Override
    public void onCollision(AABB other) {
        if (other instanceof Platform){
            final Rectangle2D intersection = RectangleUtils.intersection(getBoundingBox(), other.getBoundingBox());
            if (intersection.getWidth() > intersection.getHeight()){
                if (intersection.getMaxY() == getBoundingBox().getMaxY()){
                    position = new Point2D(position.getX(), position.getY() - intersection.getHeight());
                } else {
                    position = new Point2D(position.getX(), position.getY() + intersection.getHeight());
                }
            } else {
                if (intersection.getMaxX() == getBoundingBox().getMaxX()){
                    position = new Point2D(position.getX() - intersection.getWidth(), position.getY());
                } else {
                    position = new Point2D(position.getX() + intersection.getWidth(), position.getY());
                }
            }
        }
    }

    public void setPosition(int x, int y) {
        position = new Point2D(x, y);
    }
}
