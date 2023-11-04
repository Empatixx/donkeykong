package cz.krokviak.donkeykong.drawable;

import javafx.scene.canvas.GraphicsContext;

public interface Drawable {
    default void draw(final GraphicsContext gc) {
        gc.save();
        drawInternal(gc);
        gc.restore();
    }
    void drawInternal(final GraphicsContext gc);
}
