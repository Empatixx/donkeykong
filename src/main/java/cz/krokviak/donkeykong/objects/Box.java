package cz.krokviak.donkeykong.objects;

import cz.krokviak.donkeykong.collision.AABB;
import cz.krokviak.donkeykong.drawable.Drawable;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Box implements Drawable, AABB {
    private final static float SCALE = 3f;
    private final Image image;
    private Point2D position;
    public Box(){
        image = new Image("box.png");
        position = Point2D.ZERO;
    }

    public void setPosition(final float x, final float y) {
        this.position = new Point2D(x, y);
    }

    @Override
    public void drawInternal(GraphicsContext gc) {
        gc.drawImage(image, position.getX(), position.getY(), image.getWidth() * SCALE, image.getHeight() * SCALE);
    }

    @Override
    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(position.getX(), position.getY(), image.getWidth() * SCALE, image.getHeight() * SCALE);
    }

    @Override
    public void onCollision(AABB other) {

    }
}
