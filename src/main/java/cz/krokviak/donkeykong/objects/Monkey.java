package cz.krokviak.donkeykong.objects;

import cz.krokviak.donkeykong.drawable.AnimatedSprite;
import cz.krokviak.donkeykong.drawable.Drawable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Monkey implements Drawable {
    private final AnimatedSprite animatedSprite;
    private Point2D position;
    private boolean rightFacing = true;
    public Monkey() {
        this.animatedSprite = AnimatedSprite.builder()
                .setFilePath("monkey.png")
                .positionSupplier(() -> position)
                .isFlippedSupplier(() -> !rightFacing)
                .addAnimationSequence("idle", 1)
                .addAnimationSequence("side", 1)
                .setFrameHeight(64)
                .setFrameWidth(64)
                .setFrameTime(0.1f)
                .scale(3)
                .build();
        animatedSprite.setCurrentAnimation("idle");
    }

    public void setPosition(final float x, final float y) {
        this.position = new Point2D(x, y);
    }

    public void update(float dt){
        animatedSprite.update(dt);
    }
    @Override
    public void drawInternal(GraphicsContext gc) {
        animatedSprite.draw(gc);
    }
}
