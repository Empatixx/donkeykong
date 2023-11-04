package cz.krokviak.donkeykong.drawable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SpriteAnimation {
    private final ImageView imageView;
    private final Image[] frames;
    private int currentFrame;
    private float frameTime;
    private float currentTime;
    private boolean hasPlayedOnce;

    public SpriteAnimation(final Image[] frames, final float frameTime) {
        this.frames = frames;
        this.frameTime = frameTime;
        this.imageView = new ImageView(frames[0]);
        this.currentFrame = 0;
        this.currentTime = 0.0f;
    }

    public void update(final float deltaTime) {
        currentTime += deltaTime;
        if (currentTime >= frameTime) {
            currentFrame = (currentFrame + 1) % frames.length;
            if (currentFrame == 0) {
                hasPlayedOnce = true;
            }
            imageView.setImage(frames[currentFrame]);
            currentTime -= frameTime;
        }
    }

    public void reset() {
        currentFrame = 0;
        currentTime = 0.0f;
        imageView.setImage(frames[currentFrame]);
        hasPlayedOnce = false;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setFrameTime(final float frameTime) {
        this.frameTime = frameTime;
    }

    public boolean hasPlayedOnce() {
        return hasPlayedOnce;
    }
}
