package cz.krokviak.donkeykong.objects;

import cz.krokviak.donkeykong.collision.AABB;
import cz.krokviak.donkeykong.drawable.Drawable;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Platform implements Drawable, AABB {
    private static String DEFAULT_PATH = "platform.png";

    public static final int SCALE = 3;
    public static final int WIDTH = 16 * SCALE;
    public static final int HEIGHT = 8 * SCALE;
    private final Image image;
    private Point2D position;
    public Platform(){
        this(0, 0);
    }
    public Platform(final float x, final float y) {
        this(x, y, DEFAULT_PATH);
    }
    public Platform(final float x, final float y, final String path) {
        image = new Image(path);
        this.position = new Point2D(x, y);
    }
    public void setPosition(final float x, final float y) {
        this.position = new Point2D(x, y);
    }


    @Override
    public void drawInternal(GraphicsContext gc) {
        gc.drawImage(image, position.getX(), position.getY(), WIDTH, HEIGHT);
    }

    public Point2D getPosition() {
        return position;
    }

    @Override
    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(position.getX(), position.getY(), WIDTH, HEIGHT);
    }

    @Override
    public void onCollision(AABB other) {

    }
}
