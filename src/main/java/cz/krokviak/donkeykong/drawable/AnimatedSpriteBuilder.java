package cz.krokviak.donkeykong.drawable;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class AnimatedSpriteBuilder {
    private String filePath;
    private final Map<String, Integer> animationFrames;
    private Supplier<Point2D> positionSupplier;
    private Supplier<Boolean> isFlippedSupplier;
    private int frameWidth;
    private int frameHeight;
    private float frameTime;
    private float scale;

    public AnimatedSpriteBuilder() {
        this.animationFrames = new LinkedHashMap<>();
        this.isFlippedSupplier = () -> false;
        this.positionSupplier = () -> Point2D.ZERO;
        this.scale = 1.0f;
    }

    public AnimatedSpriteBuilder setFilePath(final String filePath) {
        this.filePath = filePath;
        return this;
    }

    public AnimatedSpriteBuilder addAnimationSequence(final String name, final int totalFrames) {
        this.animationFrames.put(name, totalFrames);
        return this;
    }

    public AnimatedSpriteBuilder setFrameWidth(final int frameWidth) {
        this.frameWidth = frameWidth;
        return this;
    }

    public AnimatedSpriteBuilder setFrameHeight(final int frameHeight) {
        this.frameHeight = frameHeight;
        return this;
    }

    public AnimatedSpriteBuilder setFrameTime(final float frameTime) {
        this.frameTime = frameTime;
        return this;
    }

    public AnimatedSprite build() {
        if (filePath == null || frameWidth <= 0 || frameHeight <= 0 || frameTime <= 0) {
            throw new IllegalStateException("The file path, frame width, frame height, and frame time must be set.");
        }

        final Image sheet = new Image(filePath);
        final PixelReader reader = sheet.getPixelReader();
        final Map<String, SpriteAnimation> animations = new HashMap<>();

        int xOffset = 0;
        for (Map.Entry<String, Integer> entry : animationFrames.entrySet()) {
            final String name = entry.getKey();
            final int totalFrames = entry.getValue();
            final Image[] frames = new Image[totalFrames];

            for (int frame = 0; frame < totalFrames; frame++) {
                final WritableImage imageFrame = new WritableImage(reader, xOffset, 0, frameWidth, frameHeight);
                frames[frame] = imageFrame;
                xOffset += frameWidth;
            }

            animations.put(name, new SpriteAnimation(frames, frameTime));
        }

        return new AnimatedSprite(animations, positionSupplier, isFlippedSupplier, scale);
    }

    public AnimatedSpriteBuilder isFlippedSupplier(final Supplier<Boolean> isFlippedSupplier) {
        this.isFlippedSupplier = isFlippedSupplier;
        return this;
    }

    public AnimatedSpriteBuilder positionSupplier(final Supplier<Point2D> positionSupplier) {
        this.positionSupplier = positionSupplier;
        return this;
    }

    public AnimatedSpriteBuilder scale(final float scale) {
        this.scale = scale;
        return this;
    }
}