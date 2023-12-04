package cz.krokviak.donkeykong.objects.princess;

import cz.krokviak.donkeykong.drawable.AnimatedSprite;
import cz.krokviak.donkeykong.drawable.Drawable;
import cz.krokviak.donkeykong.drawable.Updatable;
import cz.krokviak.donkeykong.utils.ScheduledTask;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Princess implements Drawable, Updatable {
    private final AnimatedSprite animatedSprite;
    private final ScheduledTask helpTask;
    private final PrincessHelp princessHelp;
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

        princessHelp = new PrincessHelp();
        helpTask = new ScheduledTask(() -> {
            princessHelp.reset();
            animatedSprite.setCurrentAnimation("idle"); // reseting animation
            animatedSprite.setFrameTimeCurrentAnimation(0.1f);
        }, 5);
    }

    @Override
    public void update(float dt){
        animatedSprite.update(dt);
        helpTask.update(dt);
        princessHelp.update(dt);
        if (animatedSprite.hasFinishedAnimation()){
            animatedSprite.setFrameTimeCurrentAnimation(Float.MAX_VALUE);
        }
    }
    @Override
    public void drawInternal(GraphicsContext gc) {
        princessHelp.draw(gc);
        animatedSprite.draw(gc);
    }

    public void setPosition(final float x, final float y) {
        this.position = new Point2D(x, y);
        this.princessHelp.setPosition(x+PrincessHelp.WIDTH*PrincessHelp.SCALE, y+10);
    }
}
