package cz.krokviak.donkeykong.items;

import cz.krokviak.donkeykong.collision.AABB;
import cz.krokviak.donkeykong.objects.player.Player;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Hammer implements Item {
    private final static int WIDTH = 16;
    private final static int HEIGHT = 16;
    private final static int SCALE = 2;
    private final Image image;
    private Point2D position;
    private boolean isPicked = false;
    public Hammer(){
        image = new Image("hammer.png");
    }
    @Override
    public void drawInternal(GraphicsContext gc) {
        if (isPicked){
            return;
        }
        gc.drawImage(image, position.getX(), position.getY(), WIDTH * SCALE, HEIGHT * SCALE);
    }

    @Override
    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(position.getX(), position.getY(), WIDTH * SCALE, HEIGHT * SCALE);
    }

    @Override
    public void onCollision(AABB other) {
        if (other instanceof Player){
            isPicked = true;
        }
    }

    public void setPosition(final float x, final float y) {
        this.position = new Point2D(x, y);
    }

    public boolean shouldRemove(){
        return isPicked;
    }

    @Override
    public void update(float dt) {
    }
}
