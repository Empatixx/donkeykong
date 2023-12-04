package cz.krokviak.donkeykong.objects.princess;

import cz.krokviak.donkeykong.drawable.AnimatedSprite;
import cz.krokviak.donkeykong.drawable.Drawable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class PrincessHelp implements Drawable {
    public static final int WIDTH = 25;
    public static final int HEIGHT = 10;
    public static final int SCALE = 3;
    private final AnimatedSprite animatedSprite;
    private Point2D position;
    public PrincessHelp(){
        this.position = new Point2D(0, 0);
        animatedSprite = AnimatedSprite.builder()
                .setFilePath("help.png")
                .addAnimationSequence("idle", 2)
                .setFrameHeight(HEIGHT)
                .setFrameWidth(WIDTH)
                .setFrameTime(0.1f)
                .positionSupplier(() -> position)
                .scale(SCALE)
                .build();
        animatedSprite.setCurrentAnimation("idle");
    }

    public void setPosition(final float x, final float y) {
        this.position = new Point2D(x, y);
    }

    public void update(float dt){
        animatedSprite.update(dt);
    }
    public void reset(){
        animatedSprite.setCurrentAnimation("idle");
    }

    @Override
    public void drawInternal(GraphicsContext gc) {
        if (animatedSprite.hasFinishedAnimation()){
            return;
        }
        animatedSprite.draw(gc);
    }
}
