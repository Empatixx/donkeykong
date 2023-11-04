package cz.krokviak.donkeykong.objects;

import cz.krokviak.donkeykong.drawable.AnimatedSprite;
import cz.krokviak.donkeykong.drawable.Drawable;
import cz.krokviak.donkeykong.input.GameAction;
import cz.krokviak.donkeykong.input.InputHandler;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.nio.file.Path;

public class Player implements Drawable {
    private final static int SPEED = 50;
    private final InputHandler inputHandler;
    private final AnimatedSprite animation;
    private Point2D position;
    private Point2D velocity;
    private boolean rightFacing = true;
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
                .setFrameHeight(64)
                .setFrameWidth(64)
                .setFrameTime(0.1f)
                .positionSupplier(() -> position)
                .isFlippedSupplier(() -> !rightFacing)
                .scale(2.0f)
                .build();
        animation.setCurrentAnimation("walk");
    }
    public void update(float dt){
        position = position.add(velocity.multiply(dt));
        updateVelocity();
        nextPosition(dt);
        animation.update(dt);
    }

    private void nextPosition(final float dt) {
        position = position.add(new Point2D(velocity.getX(), velocity.getY()).multiply(dt));
    }

    private void updateVelocity(){
        final boolean right = inputHandler.isActive(GameAction.MOVE_RIGHT);
        final boolean left = inputHandler.isActive(GameAction.MOVE_LEFT);
        final boolean up = inputHandler.isActive(GameAction.MOVE_UP);
        final boolean down = inputHandler.isActive(GameAction.MOVE_DOWN);

        Point2D newVelocity = Point2D.ZERO;
        if(right){
            newVelocity = newVelocity.add(SPEED, 0);
            rightFacing = true;
        }
        if(left){
            newVelocity = newVelocity.add(-SPEED, 0);
            rightFacing = false;
        }
        if(up){
            newVelocity = newVelocity.add(0, -SPEED);
        }
        if(down){
            newVelocity = newVelocity.add(0, SPEED);
        }
        velocity = newVelocity;
    }
    public void setVelocity(final float x, final float y){
        this.velocity = new Point2D(x, y);
    }
    @Override
    public void drawInternal(final GraphicsContext gc){
        animation.draw(gc);
    }
}
