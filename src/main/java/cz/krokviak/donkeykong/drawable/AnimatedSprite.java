package cz.krokviak.donkeykong.drawable;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

import java.util.Map;
import java.util.function.Supplier;

public class AnimatedSprite implements Drawable{
    private final Map<String, SpriteAnimation> animations;
    private SpriteAnimation currentAnimation;
    private String currentAnimationName;
    private final Supplier<Point2D> positionSupplier;
    private final Supplier<Boolean> isFlippedSupplier;
    private float scale;

    public AnimatedSprite(final Map<String, SpriteAnimation> animations,
                          final Supplier<Point2D> positionSupplier,
                          final Supplier<Boolean> isFlippedSupplier,
                          final float scale) {
        this.animations = animations;
        this.positionSupplier = positionSupplier;
        this.isFlippedSupplier = isFlippedSupplier;
        this.scale = scale;
    }

    public void setCurrentAnimation(final String name) {
        if (!animations.containsKey(name)) {
            throw new IllegalArgumentException("No animation with name: " + name);
        }
        this.currentAnimationName = name;
        this.currentAnimation = animations.get(name);
        this.currentAnimation.reset();
    }

    public void update(final float deltaTime) {
        if (currentAnimation != null) {
            currentAnimation.update(deltaTime);
        }
    }

    public void setFrameTimeCurrentAnimation(final float frameTime){
        if (currentAnimation != null) {
            currentAnimation.setFrameTime(frameTime);
        }
    }

    public ImageView getImageView() {
        return currentAnimation != null ? currentAnimation.getImageView() : null;
    }

    public static AnimatedSpriteBuilder builder(){
        return new AnimatedSpriteBuilder();
    }

    @Override
    public void drawInternal(GraphicsContext gc) {
        if (currentAnimation != null) {
            final Point2D position = positionSupplier.get();
            final boolean isFlipped = isFlippedSupplier.get();
            final Image image = currentAnimation.getImageView().getImage();
            gc.scale(scale, scale);
            if (isFlipped) {
                gc.translate(position.getX() + image.getWidth() / 2, 0);
                gc.scale(-1, 1);
                gc.translate(-position.getX() - image.getWidth() / 2, 0);
            }

            gc.drawImage(image, position.getX(), position.getY());
        }
    }

    public boolean hasFinishedAnimation() {
        return currentAnimation != null && currentAnimation.hasPlayedOnce();
    }
}