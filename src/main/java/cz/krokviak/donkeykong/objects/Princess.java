package cz.krokviak.donkeykong.objects;

import cz.krokviak.donkeykong.drawable.AnimatedSprite;
import cz.krokviak.donkeykong.drawable.Drawable;
import cz.krokviak.donkeykong.utils.ScheduledTask;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Princess implements Drawable {
    private final AnimatedSprite animatedSprite;
    private final ScheduledTask helpTask;
    private Point2D position;

    public Princess(){
        animatedSprite = AnimatedSprite.builder()
                .setFilePath("princess.png")
                .addAnimationSequence("idle", 3)
                .setFrameHeight(32)
                .setFrameWidth(32)
                .setFrameTime(0.1f)
                .positionSupplier(() -> position)
                .scale(3)
                .build();
        animatedSprite.setCurrentAnimation("idle");
        helpTask = new ScheduledTask(() -> {
            animatedSprite.setCurrentAnimation("idle"); // reseting animation
            animatedSprite.setFrameTimeCurrentAnimation(0.1f);
        }, 5);
    }
    public void update(float dt){
        animatedSprite.update(dt);
        helpTask.update(dt);
        if (animatedSprite.hasFinishedAnimation()){
            animatedSprite.setFrameTimeCurrentAnimation(Float.MAX_VALUE);
        }
    }
    @Override
    public void drawInternal(GraphicsContext gc) {
        animatedSprite.draw(gc);
    }

    public void setPosition(final float x, final float y) {
        this.position = new Point2D(x, y);
    }
}
