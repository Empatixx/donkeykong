package cz.krokviak.donkeykong.objects;

import cz.krokviak.donkeykong.drawable.Drawable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Barrels implements Drawable {
    private final static float SCALE = 1f;
    private final Image image;
    private Point2D position;
    public Barrels(){
        image = new Image("barrels.png");
        position = Point2D.ZERO;
    }

    public void setPosition(final float x, final float y) {
        this.position = new Point2D(x, y);
    }

    @Override
    public void drawInternal(GraphicsContext gc) {
        gc.drawImage(image, position.getX(), position.getY(), image.getWidth() * SCALE, image.getHeight() * SCALE);
    }
}
