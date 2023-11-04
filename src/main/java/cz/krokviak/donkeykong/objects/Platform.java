package cz.krokviak.donkeykong.objects;

import cz.krokviak.donkeykong.collision.AABB;
import cz.krokviak.donkeykong.drawable.Drawable;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.security.interfaces.DSAPrivateKey;

public class Platform implements Drawable, AABB {
    public static final int WIDTH = 16;
    public static final int HEIGHT = 8;
    public static final int SCALE = 3;
    private final Image image;
    private Point2D position;
    public Platform(){
        this(0, 0);
    }
    public Platform(final float x, final float y) {
        image = new Image("platform.png");
        this.position = new Point2D(x, y);
    }

    public void setPosition(final float x, final float y) {
        this.position = new Point2D(x, y);
    }


    @Override
    public void drawInternal(GraphicsContext gc) {
        gc.scale(SCALE, SCALE);
        gc.drawImage(image, position.getX(), position.getY());
    }

    public Point2D getPosition() {
        return position;
    }

    @Override
    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(position.getX(), position.getY(), position.getX() + WIDTH * SCALE,  position.getY()+HEIGHT * SCALE);
    }

    @Override
    public void onCollision(AABB other) {

    }
}
